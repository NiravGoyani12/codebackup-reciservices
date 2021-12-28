package com.rcibanque.rcidirect.clients.crif.domain;

public enum ContractType {

	LEASE_HIRE_PRIVATE(401, 1, "14"),
	LEASE_HIRE_PROF(401, 2, "17"),
	LEASE_PURCHASE_PRIVATE(402, 1, "14"),
	LEASE_PURCHASE_PROF(402, 2, "17"),
	PCP_PRIVATE(403, 1,  "23"),
	PCP_PROF(403, 2, "18"),
	HIRE_PURCHASE_PRIVATE(404, 1, "13"),
	HIRE_PURCHASE_PROF(404, 2, "18"),
	HIRE_DRIVE_PRIVATE(405, 1, "14"),
	HIRE_DRIVE_PROF(405, 2, "17");

	private final Integer _financeTypeCode;

	private final Integer _actorCategoryCode;

	private final String _contractTypeCode;

	private ContractType(Integer pFinanceTypeCode, Integer pActorCategoryCode, String pContractTypeCode) {
		_financeTypeCode = pFinanceTypeCode;
		_actorCategoryCode = pActorCategoryCode;
		_contractTypeCode = pContractTypeCode;
	}

	public Integer getFinanceTypeCode() {
		return _financeTypeCode;
	}

	public Integer getActorCategoryCode() {
		return _actorCategoryCode;
	}

	public String getContractTypeCode() {
		return _contractTypeCode;
	}

	public static ContractType valueOf(Integer pFinanceTypeCode, Integer pActorCategoryCode) {
		ContractType res = null;
		for (ContractType p : values()) {
			if (p.getFinanceTypeCode().equals(pFinanceTypeCode) && p.getActorCategoryCode().equals(pActorCategoryCode)) {
				res = p;
				break;
			}
		}
		return res;
	}

}
