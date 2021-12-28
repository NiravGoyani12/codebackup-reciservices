package com.rcibanque.rcidirect.clients.crif.domain;

public enum CreditCheckRole {

	BORROWER(4, "B"),
	CO_BORROWER(5, "C");

	private final Integer _roleCode;

	private final String _role;

	private CreditCheckRole(Integer pRoleCode, String pRole) {
		_roleCode = pRoleCode;
		_role = pRole;
	}

	public Integer getRoleCode() {
		return _roleCode;
	}

	public String getRole() {
		return _role;
	}

	public static CreditCheckRole valueOf(Integer pRoleCode) {
		CreditCheckRole res = null;
		for (CreditCheckRole p : values()) {
			if (p.getRoleCode().equals(pRoleCode)) {
				res = p;
				break;
			}
		}
		return res;
	}

}
