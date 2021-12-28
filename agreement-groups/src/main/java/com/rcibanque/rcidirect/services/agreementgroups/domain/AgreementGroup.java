package com.rcibanque.rcidirect.services.agreementgroups.domain;

import com.rcibanque.rcidirect.services.core.domain.DTO;

public class AgreementGroup extends DTO {

	private static final long serialVersionUID = -8227107671457626453L;


	private Long _groupId;

	private String _customerActorCode;

	private String _financialCompanyCode;

	private Boolean _masterAgreement;

	private String _groupName;

	private Boolean _invoicesGrouped;

	private Boolean _directDebitsGrouped;


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

	public String getGroupName() {
		return _groupName;
	}

	public void setGroupName(String pGroupName) {
		_groupName = pGroupName;
	}

	public Boolean getInvoicesGrouped() {
		return _invoicesGrouped;
	}

	public void setInvoicesGrouped(Boolean pInvoicesGrouped) {
		_invoicesGrouped = pInvoicesGrouped;
	}

	public Boolean getDirectDebitsGrouped() {
		return _directDebitsGrouped;
	}

	public void setDirectDebitsGrouped(Boolean pDirectDebitsGrouped) {
		_directDebitsGrouped = pDirectDebitsGrouped;
	}
}
