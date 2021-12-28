package com.rcibanque.rcidirect.clients.crif.domain;

public enum Contact {

	LANDLINE(1),
	MOBILE(2),
	PRIMARY_BUSINESS_LANDLINE(3),
	SECONDARY_BUSINESS_MOBILE(4),
	EMAIL_ADDRESS(5);

	private final Integer _type;

	private Contact(Integer pType) {
		_type = pType;
	}

	public Integer getType() {
		return _type;
	}

}
