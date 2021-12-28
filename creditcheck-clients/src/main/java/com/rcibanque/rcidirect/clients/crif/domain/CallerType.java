package com.rcibanque.rcidirect.clients.crif.domain;

public enum CallerType {

	SUBMIT("SUBMIT"),
	STATEMENT("STATEMENT"),
	CANCEL("CANCEL");

	private final String _callerType;

	public String getCallerType() {
		return _callerType;
	}

	private CallerType(String pCallerType) {
		_callerType = pCallerType;
	}

	public static CallerType getCallerType(String pCallerType) {
		CallerType res = null;
		for (CallerType p : values()) {
			if (p.getCallerType().equals(pCallerType)) {
				res = p;
				break;
			}
		}
		return res;
	}

}
