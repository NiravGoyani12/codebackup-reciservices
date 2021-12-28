package com.rcibanque.rcidirect.clients.crif.domain;

public enum ActorTypes {

	PRIVATE_INDIVIDUAL(1),
	LIMITED_COMPANY(2),
	SOLE_TRADER(3),
	PARTNERSHIP(4),
	INDIVIDUAL_COMPANY(5);

	private final Integer _typeCode;

	private ActorTypes(Integer pTypeCode) {
		_typeCode = pTypeCode;
	}

	public Integer getTypeCode() {
		return _typeCode;
	}
}
