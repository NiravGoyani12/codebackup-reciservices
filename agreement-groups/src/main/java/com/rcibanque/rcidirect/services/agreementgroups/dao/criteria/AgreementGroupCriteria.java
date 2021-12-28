package com.rcibanque.rcidirect.services.agreementgroups.dao.criteria;

import com.rcibanque.rcidirect.services.core.dao.criteria.Criteria;
import com.rcibanque.rcidirect.services.dealerselection.core.dao.criteria.IDealerSelectionCriteria;

public class AgreementGroupCriteria extends Criteria implements IDealerSelectionCriteria {

	private static final long serialVersionUID = -5762175621924181275L;


	private Long _groupId;

	private String _customerActorCode;

	private String _financialCompanyCode;

	private Boolean _masterAgreement;


	private String _dealerUserActorCode;

	private String _salesExecutiveActorCode;

	private String _dealershipActorCode;


	private Long _candidateAgreementId;


	public Long getGroupId() {
		return _groupId;
	}

	public void setGroupId(Long pGroupId) {
		_groupId = pGroupId;
	}

	public String getCustomerActorCode() {
		return _customerActorCode;
	}

	public void setCustomerActorCode(String pCustomerActorCode) {
		_customerActorCode = pCustomerActorCode;
	}

	public String getFinancialCompanyCode() {
		return _financialCompanyCode;
	}

	public void setFinancialCompanyCode(String pFinancialCompanyCode) {
		_financialCompanyCode = pFinancialCompanyCode;
	}

	public Boolean getMasterAgreement() {
		return _masterAgreement;
	}

	public void setMasterAgreement(Boolean pMasterAgreement) {
		_masterAgreement = pMasterAgreement;
	}


	@Override
	public String getDealerUserActorCode() {
		return _dealerUserActorCode;
	}

	@Override
	public void setDealerUserActorCode(String pUserActorCode) {
		_dealerUserActorCode = pUserActorCode;
	}

	@Override
	public String getSalesExecutiveActorCode() {
		return _salesExecutiveActorCode;
	}

	@Override
	public void setSalesExecutiveActorCode(String pSalesExecutiveActorCode) {
		_salesExecutiveActorCode = pSalesExecutiveActorCode;
	}

	@Override
	public String getDealershipActorCode() {
		return _dealershipActorCode;
	}

	@Override
	public void setDealershipActorCode(String pDealershipActorCode) {
		_dealershipActorCode = pDealershipActorCode;
	}


	public Long getCandidateAgreementId() {
		return _candidateAgreementId;
	}

	public void setCandidateAgreementId(Long pCandidateAgreementId) {
		_candidateAgreementId = pCandidateAgreementId;
	}

}
