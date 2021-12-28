package com.rcibanque.rcidirect.services.agreementgroups.service;

import java.util.List;

import com.rcibanque.rcidirect.services.agreementgroups.dao.criteria.AgreementGroupCriteria;
import com.rcibanque.rcidirect.services.agreementgroups.domain.AgreementGroup;
import com.rcibanque.rcidirect.services.agreementgroups.domain.AgreementGroupAgreement;
import com.rcibanque.rcidirect.services.core.domain.IContext;

public interface IAgreementGroupsService {

	List<AgreementGroup> getAgreementGroups(IContext pContext, AgreementGroupCriteria pCriteria);

	void createAgreementGroup(IContext pContext, AgreementGroup pAgreementGroup);

	void updateAgreementGroup(IContext pContext, AgreementGroup pAgreementGroup);

	void deleteAgreementGroup(IContext pContext, Long pGroupId);


	List<AgreementGroupAgreement> getAgreementGroupAgreements(IContext pContext, Long pGroupId, Boolean pGrouped);

	void addAgreementToAgreementGroup(IContext pContext, Long pGroupId, Long pAgreementId);

	void removeAgreementFromAgreementGroup(IContext pContext, Long pGroupId, Long pAgreementId);

}
