package com.rcibanque.rcidirect.services.core.user.domain;

public enum UserProfileGroup {


	DEALER(1),
	HEAD_OFFICE(2);


	private final Integer _profileGroupCode; // paprofgrp.kcd_profgrp


	private UserProfileGroup(Integer pProfileCode) {
		_profileGroupCode = pProfileCode;
	}


	public Integer getProfileGroupCode() {
		return _profileGroupCode;
	}


	public boolean equalCodes(Integer pProfileCode) {
		return getProfileGroupCode().equals(pProfileCode);
	}

}
