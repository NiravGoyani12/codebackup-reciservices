package com.rcibanque.rcidirect.services.core.system.domain;

public enum ParamExEnum {


	LOGIN_ENCRYPTION_SALT("LOGIN_PROCESS", "LOGIN_PASSWORD_ENCRYPTION_SALT", ParamType.STRING),
	LOGIN_PASSWORD_FORGOTTEN_EXPIRY_TIME("LOGIN_PROCESS", "FORGOTTEN_PASSWORD_EXPIRY_TIME", ParamType.INTEGER),
	LOGIN_PASSWORD_EXPIRY_DAYS("LOGIN_PROCESS", "PASSWORD_EXPIRY_IN_DAYS", ParamType.INTEGER),

	PROPOSAL_LIFECYCLE__MIN_ALLOWED_IDENTIFICATION_IMAGE_SIZE("PROPOSAL_LIFECYCLE", "MIN_ALLOWED_IDENTIFICATION_IMAGE_SIZE", ParamType.INTEGER),

	REGISTRATION_BRAND_R1("SEDRE_NORDIS_BRAND_CHECK", "R1", ParamType.INTEGER),
	REGISTRATION_BRAND_IF("SEDRE_NORDIS_BRAND_CHECK", "IF", ParamType.INTEGER),
	REGISTRATION_BRAND_DA("SEDRE_NORDIS_BRAND_CHECK", "DA", ParamType.INTEGER),
	REGISTRATION_BRAND_NI("SEDRE_NORDIS_BRAND_CHECK", "NI", ParamType.INTEGER),
	REGISTRATION_BRAND_RCI("SEDRE_NORDIS_BRAND_CHECK", "RCI", ParamType.INTEGER),

	DOCUMENT_UPLOAD_NOTIFICATION_RECIPIENT("PAPERLESS", "DOC_UPLOAD_NOTIFICATION_EMAIL", ParamType.STRING),

	AUTOPAYOUT_VERIFY_CODE_LIFE_MINS("PROPOSAL_LIFECYCLE", "AUTOPAYOUT_VERIFY_CODE_LIFE_MINS", ParamType.INTEGER),
	EXCESS_MILEAGE("INVOICE", "EXCESS_MILEAGE", ParamType.AMOUNT),
	DEFAULT_INSURANCE_SERVICE_CODE("TERMINATION","DEFAULT_INSURANCE_SERVICE_CODE", ParamType.INTEGER),
	PHONE_NUMBER_PREFIX("ACTOR_DATAS", "PHONE_NUMBER_PREFIX", ParamType.STRING),
	VALID_TO_NB_DAYS_BEFORE_NEXT_RENTAL("SETTLEMENT", "VALID_TO_NB_DAYS_BEFORE_NEXT_RENTAL", ParamType.INTEGER),
	ACTIVATE_SETTLEMENT_CALCULATOR("SETTLEMENT", "ACTIVATE_SETTLEMENT_CALCULATOR", ParamType.INTEGER),
	SETTLEMENT_CHARGE_PERCENT("SETTLEMENT", "SETTLEMENT_CHARGE_PERCENT", ParamType.PERCENTAGE_RATE),
	;


	private final String _paramScope;

	private final String _paramCode;

	private final ParamType _paramType;


	private ParamExEnum(String pParamScope, String pParamCode, ParamType pParamType) {
		_paramScope = pParamScope;
		_paramCode = pParamCode;
		_paramType = pParamType;
	}


	public String getParamCode() {
		return _paramCode;
	}

	public String getParamScope() {
		return _paramScope;
	}

	public ParamType getParamType() {
		return _paramType;
	}


	public static ParamExEnum valueOf(String pParamScope, String pParamCode) {
		ParamExEnum res = null;
		for(ParamExEnum p : values()) {
			if(p.getParamScope().equals(pParamScope) && p.getParamCode().equals(pParamCode)) {
				res = p;
				break;
			}
		}
		return res;
	}


}
