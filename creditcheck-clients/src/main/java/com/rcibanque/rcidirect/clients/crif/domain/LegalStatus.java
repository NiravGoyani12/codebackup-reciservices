package com.rcibanque.rcidirect.clients.crif.domain;

public enum LegalStatus {

	CHARITY("CH", 2, "21", "RT", "5"),
	GOVERNMENT_AGENCY("GA", 2, "16", "RT", "5"),
	GENERAL_PARTNERSHIP("GP", 3, "18", "MT", "5"),
	HOLDING_COMPANY("HC", 2, "1", "RT", "5"),
	LIMITED_PARTNERSHIP("LP", 3, "18", "MT", "8"),
	PUBLIC_LIMITED_COMPANY("PULC", 2, "5", "RT", "5"),
	PRIVATE_LIMITED_COMPANY("RC", 2, "1", "RT", "5"),
	SOLE_PROPRIETORSHIP("SP", 3, "6", "RT", "5"),
	SOLE_TRADER_VAT_REGISTERED("ST1", 3, "6", "RT", "5"),
	SOLE_TRADER_NOT_VAT_REGISTERED("ST2", 3, "6", "RT", "5"),
	SUBSIDIARY("SUB", 2, "7", "RT", "5"),
	PRIVATE_UNLIMITED_CO("UPC", 2, "7", "RT", "5");

	private final String _legalNameCode;

	private final Integer _actorTypeCode;

	private final String _entityFormCode;

	private final String _addressTypeCode;

	private final String _identificationCode;

	public String getLegalNameCode() {
		return _legalNameCode;
	}

	public Integer getActorTypeCode() {
		return _actorTypeCode;
	}

	public String getEntityFormCode() {
		return _entityFormCode;
	}

	public String getAddressTypeCode() {
		return _addressTypeCode;
	}

	public String getIdentificationCode() {
		return _identificationCode;
	}

	private LegalStatus(String pLegalNameCode, Integer pActorTypeCode, String pEntityFormCode, String pAddressTypeCode,
			String pIdentificationCode) {
		_legalNameCode = pLegalNameCode;
		_actorTypeCode = pActorTypeCode;
		_entityFormCode = pEntityFormCode;
		_addressTypeCode = pAddressTypeCode;
		_identificationCode = pIdentificationCode;
	}

	public static LegalStatus getLegalStatus(String pLegalNameCode) {
		LegalStatus res = null;
		for (LegalStatus p : values()) {
			if (p.getLegalNameCode().equals(pLegalNameCode)) {
				res = p;
				break;
			}
		}
		return res;
	}

}
