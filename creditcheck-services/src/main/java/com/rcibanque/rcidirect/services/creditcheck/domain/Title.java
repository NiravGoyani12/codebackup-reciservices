package com.rcibanque.rcidirect.services.creditcheck.domain;

public enum Title {

	MISS(1, 2),
	MR(3, 1),
	FR(4, 1),
	DR(5, 1),
	MRS(6, 2),
	MS(7, 2),
	REV(8, 1);

	private final Integer _titleCode;

	private final Integer _genderCode;

	private Title(Integer pTitleCode, Integer pGenderCode) {
		_titleCode = pTitleCode;
		_genderCode = pGenderCode;
	}

	public Integer getTitleCode() {
		return _titleCode;
	}

	public Integer getGenderCode() {
		return _genderCode;
	}

	public static Title valueOf(Integer pTitleCode) {
		Title res = null;
		for (Title p : values()) {
			if (p.getTitleCode().equals(pTitleCode)) {
				res = p;
				break;
			}
		}
		return res;
	}

}
