package com.rcibanque.rcidirect.services.creditcheck.domain;

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

}
