package com.rcibanque.rcidirect.services.agreementgroups.dao;

import java.util.List;

import com.rcibanque.rcidirect.services.agreementgroups.dao.criteria.AgreementGroupCriteria;
import com.rcibanque.rcidirect.services.agreementgroups.domain.AgreementGroup;
import com.rcibanque.rcidirect.services.agreementgroups.domain.AgreementGroupAgreement;
import com.rcibanque.rcidirect.services.core.domain.IContext;

public interface IAgreementGroupsDao {

	List<AgreementGroup> getAgreementGroups(IContext pContext, AgreementGroupCriteria pCriteria);

	boolean checkCustomerActorAccess(IContext pContext, String pActorCode);

	void createAgreementGroup(IContext pContext, AgreementGroup pAgreementGroup);

	void updateAgreementGroup(IContext pContext, AgreementGroup pAgreementGroup);

	void deleteAgreementGroup(IContext pContext, Long pGroupId);


	List<AgreementGroupAgreement> getAgreementGroupAgreements(IContext pContext, AgreementGroupCriteria pCriteria);

	void addAgreementToAgreementGroup(IContext pContext, Long pGroupId, Long pAgreementId);

	void removeAgreementFromAgreementGroup(IContext pContext, Long pGroupId, Long pAgreementId);

	void updateAgreementWithAgreementGroup(IContext pContext, AgreementGroup pGroup, List<Long> pAgreementIds);

}
