package com.rcibanque.rcidirect.clients.crif.domain;

public enum Gender {

	MALE(1, "M"),
	FEMALE(2, "F");

	private final Integer _genderCode;

	private final String _gender;

	private Gender(Integer pGenderCode, String pGender) {
		_genderCode = pGenderCode;
		_gender = pGender;
	}

	public Integer getGenderCode() {
		return _genderCode;
	}

	public String getGender() {
		return _gender;
	}

	public static Gender valueOf(Integer pGenderCode) {
		Gender res = null;
		for (Gender p : values()) {
			if (p.getGenderCode().equals(pGenderCode)) {
				res = p;
				break;
			}
		}
		return res;
	}

}
