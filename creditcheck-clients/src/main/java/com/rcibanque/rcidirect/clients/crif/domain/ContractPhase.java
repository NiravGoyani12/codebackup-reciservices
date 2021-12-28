package com.rcibanque.rcidirect.clients.crif.domain;

public enum ContractPhase {

	WORKING_QUOTE(1, "RQ"),
	SAVED_QUOTE(2, "RQ"),
	ADDITIONAL_INFO(3, "RQ"),
	SUBMITTED(4, "RQ"),
	REFERRED(5, "RQ"),
	PROCESSSING(6,"RQ"),
	REJECTED(7, "RQ"),
	CUSTOMER_CANCELLED(8, "RQ"),
	APPROVED(9, "RQ");

	private final Integer _agreementStatusCode;

	private final String _contractPhaseCode;

	private ContractPhase(Integer pAgreementStatusCode, String pContractPhaseCode) {
		_agreementStatusCode = pAgreementStatusCode;
		_contractPhaseCode = pContractPhaseCode;
	}

	public Integer getAgreementStatusCode() {
		return _agreementStatusCode;
	}

	public String getContractPhaseCode() {
		return _contractPhaseCode;
	}

	public static ContractPhase valueOf(Integer pAgreementStatusCode) {
		ContractPhase res = null;
		for (ContractPhase p : values()) {
			if (p.getAgreementStatusCode().equals(pAgreementStatusCode)) {
				res = p;
				break;
			}
		}
		return res;
	}

}
