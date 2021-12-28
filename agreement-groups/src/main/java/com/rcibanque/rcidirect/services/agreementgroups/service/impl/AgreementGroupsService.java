package com.rcibanque.rcidirect.services.agreementgroups.service.impl;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rcibanque.rcidirect.services.agreementgroups.dao.IAgreementGroupsDao;
import com.rcibanque.rcidirect.services.agreementgroups.dao.criteria.AgreementGroupCriteria;
import com.rcibanque.rcidirect.services.agreementgroups.domain.AgreementGroup;
import com.rcibanque.rcidirect.services.agreementgroups.domain.AgreementGroupAgreement;
import com.rcibanque.rcidirect.services.agreementgroups.service.IAgreementGroupsService;
import com.rcibanque.rcidirect.services.agreementgroups.util.AgreementGroupMessages;
import com.rcibanque.rcidirect.services.core.domain.IContext;
import com.rcibanque.rcidirect.services.core.exception.ExceptionUtils;
import com.rcibanque.rcidirect.services.core.utils.CoreUtils;
import com.rcibanque.rcidirect.services.dealerselection.core.utils.DealerSelectionCriteriaUtils;
import com.rcibanque.rcidirect.services.general.parameters.service.impl.CommonService;

@Service
public class AgreementGroupsService extends CommonService implements IAgreementGroupsService {


	private static final Integer[] WORKING_QUOTE_OR_CANCELLED_AGREEMENT_STATUSES = new Integer[] {1, 8, 30, 31};

	private static final Integer WORKING_QUOTE_AGREEMENT_STATUS = 1;


	@Autowired
	private IAgreementGroupsDao _agreementGroupsDao;


	@Override
	public List<AgreementGroup> getAgreementGroups(IContext pContext, AgreementGroupCriteria pCriteria) {

		DealerSelectionCriteriaUtils.setCriteria(pContext, pCriteria);

		List<AgreementGroup> res = _agreementGroupsDao.getAgreementGroups(pContext, pCriteria);

		if(pCriteria.getCandidateAgreementId() != null) {

			// Only return groups in which can be added the candidate (i.e. groups that contain the candidate in their ungrouped agreements)
			res = res.stream()
					.filter(g -> getAgreementGroupAgreements(pContext, g.getGroupId(), Boolean.FALSE).stream().anyMatch(a -> a.getAgreementId().equals(pCriteria.getCandidateAgreementId())))
					.collect(Collectors.toList());
		}

		return res;
	}

	private AgreementGroup getAgreementGroup(IContext pContext, Long pGroupId) {

		AgreementGroupCriteria criteria = new AgreementGroupCriteria();
		criteria.setLanguageCode(pContext.getLanguageCode());
		criteria.setGroupId(pGroupId);

		List<AgreementGroup> res = getAgreementGroups(pContext, criteria);

		if(CollectionUtils.isEmpty(res)) {
			pContext.getMessages().error(AgreementGroupMessages.getInstance(), "agreement.group.not.found");
			ExceptionUtils.throwEntityNotFoundException(pContext);
		}

		return res.get(0);
	}

	private List<AgreementGroup> getCustomerAgreementGroups(IContext pContext, String pCustomerActorCode) {

		AgreementGroupCriteria criteria = new AgreementGroupCriteria();
		criteria.setLanguageCode(pContext.getLanguageCode());
		criteria.setCustomerActorCode(pCustomerActorCode);

		return getAgreementGroups(pContext, criteria);
	}


	@Override
	public void createAgreementGroup(IContext pContext, AgreementGroup pAgreementGroup) {

		// User must have access to customer
		if(! _agreementGroupsDao.checkCustomerActorAccess(pContext, pAgreementGroup.getCustomerActorCode())) {
			pContext.getMessages().error(AgreementGroupMessages.getInstance(), "customer.not.found");
			ExceptionUtils.throwEntityNotFoundException(pContext);
		}

		// Customer should not have two groups with the same name
		checkCustomerGroupNameDoesntAlreadyExist(pContext, pAgreementGroup);

		_agreementGroupsDao.createAgreementGroup(pContext, pAgreementGroup);
	}


	@Override
	public void updateAgreementGroup(IContext pContext, AgreementGroup pAgreementGroup) {

		// Group must exist
		AgreementGroup group = getAgreementGroup(pContext, pAgreementGroup.getGroupId());

		// Customer should not have two groups with the same name
		checkCustomerGroupNameDoesntAlreadyExist(pContext, pAgreementGroup);

		_agreementGroupsDao.updateAgreementGroup(pContext, pAgreementGroup);

		List<AgreementGroupAgreement> groupedAgreements = getAgreementGroupAgreements(pContext, group, Boolean.TRUE);
		List<Long> groupedAgreementIDs = groupedAgreements.stream().map(AgreementGroupAgreement::getAgreementId).collect(Collectors.toList());

		_agreementGroupsDao.updateAgreementWithAgreementGroup(pContext, pAgreementGroup, groupedAgreementIDs);
	}

	private void checkCustomerGroupNameDoesntAlreadyExist(IContext pContext, AgreementGroup pAgreementGroup) {

		List<AgreementGroup> groups = getCustomerAgreementGroups(pContext, pAgreementGroup.getCustomerActorCode());

		boolean groupNameExist = groups.stream().anyMatch(g -> g.getGroupName().equalsIgnoreCase(pAgreementGroup.getGroupName()) && ! g.getGroupId().equals(pAgreementGroup.getGroupId()));
		if(groupNameExist) {
			pContext.getMessages().error(AgreementGroupMessages.getInstance(), "agreement.group.already.exist");
			ExceptionUtils.throwInvalidRequestException(pContext);
		}
	}


	@Override
	public void deleteAgreementGroup(IContext pContext, Long pGroupId) {

		// Group must exist
		AgreementGroup group = getAgreementGroup(pContext, pGroupId);

		// Group must be empty
		List<AgreementGroupAgreement> groupedAgreements = getAgreementGroupAgreements(pContext, group, Boolean.TRUE);
		if(CollectionUtils.isNotEmpty(groupedAgreements)) {
			pContext.getMessages().error(AgreementGroupMessages.getInstance(), "agreement.group.can.not.be.deleted.as.not.empty");
			ExceptionUtils.throwInvalidRequestException(pContext);
		}

		_agreementGroupsDao.deleteAgreementGroup(pContext, pGroupId);
	}


	@Override
	public List<AgreementGroupAgreement> getAgreementGroupAgreements(IContext pContext, Long pGroupId, Boolean pGrouped) {

		AgreementGroup group = getAgreementGroup(pContext, pGroupId);

		List<AgreementGroupAgreement> agreements = getAgreementGroupAgreements(pContext, group, pGrouped);

		if(BooleanUtils.isFalse(pGrouped)) {

			List<AgreementGroupAgreement> groupedAgreements = getAgreementGroupAgreements(pContext, group, Boolean.TRUE);

			// Only return ungrouped agreements that are valid candidates
			agreements = agreements.stream()
					.filter(a -> isAgreementGroupModificationValid(pContext, groupedAgreements, a, true))
					.collect(Collectors.toList());
		}

		return agreements;
	}

	private List<AgreementGroupAgreement> getAgreementGroupAgreements(IContext pContext, AgreementGroup pGroup, Boolean pGrouped) {

		AgreementGroupCriteria criteria = new AgreementGroupCriteria();
		criteria.setLanguageCode(pContext.getLanguageCode());
		criteria.setCustomerActorCode(pGroup.getCustomerActorCode());
		criteria.setFinancialCompanyCode(pGroup.getFinancialCompanyCode());

		// Agreements IN the group or agreements NOT IN ANY group?
		if(BooleanUtils.isTrue(pGrouped)) {
			criteria.setGroupId(pGroup.getGroupId());
		}

		return _agreementGroupsDao.getAgreementGroupAgreements(pContext, criteria);
	}


	@Override
	public void addAgreementToAgreementGroup(IContext pContext, Long pGroupId, Long pAgreementId) {

		// Group must exist
		AgreementGroup group = getAgreementGroup(pContext, pGroupId);
		List<AgreementGroupAgreement> groupedAgreements = getAgreementGroupAgreements(pContext, group, Boolean.TRUE);

		// Agreement must be (a valid candidate) IN NO group
		List<AgreementGroupAgreement> ungroupedAgreements = getAgreementGroupAgreements(pContext, group, Boolean.FALSE);
		AgreementGroupAgreement agreement = ungroupedAgreements.stream().filter(a -> a.getAgreementId().equals(pAgreementId)).findFirst().orElse(null);
		if(agreement == null) {
			pContext.getMessages().error(AgreementGroupMessages.getInstance(), "agreement.group.agreement.can.not.be.added.not.compatible.or.already.grouped");
			ExceptionUtils.throwInvalidRequestException(pContext);
		}

		// Modification must be valid
		if(! isAgreementGroupModificationValid(pContext, groupedAgreements, agreement, true)) {
			pContext.getMessages().error(AgreementGroupMessages.getInstance(), "agreement.group.agreement.can.not.be.added.agreement.status");
			ExceptionUtils.throwInvalidRequestException(pContext);
		}

		_agreementGroupsDao.addAgreementToAgreementGroup(pContext, pGroupId, pAgreementId);

		_agreementGroupsDao.updateAgreementWithAgreementGroup(pContext, group, Arrays.asList(pAgreementId));
	}


	@Override
	public void removeAgreementFromAgreementGroup(IContext pContext, Long pGroupId, Long pAgreementId) {

		// Group must exist
		AgreementGroup group = getAgreementGroup(pContext, pGroupId);
		List<AgreementGroupAgreement> groupedAgreements = getAgreementGroupAgreements(pContext, group, Boolean.TRUE);

		// Agreement must be IN the group
		AgreementGroupAgreement agreement = groupedAgreements.stream().filter(a -> a.getAgreementId().equals(pAgreementId)).findFirst().orElse(null);
		if(agreement == null) {
			pContext.getMessages().error(AgreementGroupMessages.getInstance(), "agreement.group.agreement.can.not.be.removed.not.in.group");
			ExceptionUtils.throwInvalidRequestException(pContext);
		}

		// Modification must be valid
		if(! isAgreementGroupModificationValid(pContext, groupedAgreements, agreement, false)) {
			pContext.getMessages().error(AgreementGroupMessages.getInstance(), "agreement.group.agreement.can.not.be.removed.agreement.status");
			ExceptionUtils.throwInvalidRequestException(pContext);
		}

		_agreementGroupsDao.removeAgreementFromAgreementGroup(pContext, pGroupId, pAgreementId);

		_agreementGroupsDao.updateAgreementWithAgreementGroup(pContext, null, Arrays.asList(pAgreementId));
	}


	private boolean isAgreementGroupModificationValid(IContext pContext, List<AgreementGroupAgreement> pGroupedAgreements, AgreementGroupAgreement pAgreement, boolean pAdded) {

		boolean res = false;

		// Only allow:
		//	    -> removing agreements
		//	    -> adding a working quote to a group of working quote / cancelled agreements

		boolean onlyWorkingQuotes = pGroupedAgreements.stream().allMatch(agreement -> CoreUtils.isIn(agreement.getAgreementStatusCode(), WORKING_QUOTE_OR_CANCELLED_AGREEMENT_STATUSES));

		res = res || (! pAdded);
		res = res || (pAdded && onlyWorkingQuotes && WORKING_QUOTE_AGREEMENT_STATUS.equals(pAgreement.getAgreementStatusCode()));

		return res;
	}

}
