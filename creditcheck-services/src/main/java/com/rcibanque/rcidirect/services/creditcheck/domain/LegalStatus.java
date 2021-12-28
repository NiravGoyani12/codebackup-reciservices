package com.rcibanque.rcidirect.services.creditcheck.domain;

/**
 * Enum to map Legal Status to ActorType
 * Added to set the correct ActorType of an actor
 * based on the LegalStatus
 * This is Ireland specific mapping
 *
 */
public enum LegalStatus {

	CHARITY("CH", 2),
	GOVERNMENT_AGENCY("GA", 2),
	GENERAL_PARTNERSHIP("GP", 3),
	HOLDING_COMPANY("HC", 2),
	LIMITED_PARTNERSHIP("LP", 3),
	PUBLIC_LIMITED_COMPANY("PULC", 2),
	PRIVATE_LIMITED_COMPANY("RC", 2),
	SOLE_PROPRIETORSHIP("SP", 3),
	SOLE_TRADER_VAT_REGISTERED("ST1", 3),
	SOLE_TRADER_NOT_VAT_REGISTERED("ST2", 3),
	SUBSIDIARY("SUB", 2),
	PRIVATE_UNLIMITED_CO("UPC", 2);

	private final String _legalNameCode;

	private final Integer _actorTypeCode;

	private LegalStatus(String pLegalNameCode, Integer pActorTypeCode) {
		_legalNameCode = pLegalNameCode;
		_actorTypeCode = pActorTypeCode;
	}

	public String getLegalNameCode() {
		return _legalNameCode;
	}

	public Integer getActorTypeCode() {
		return _actorTypeCode;
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
