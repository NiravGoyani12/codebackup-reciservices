package com.rcibanque.rcidirect.services.core.system.domain;

public enum ParamEnum {


	MAX_OCCURENCES(1, ParamType.INTEGER),
	CURRENCY_CODE(7, ParamType.STRING),
	PAYMENT_DELAY(44, ParamType.INTEGER),
	PAYMENT_DAY(46, ParamType.INTEGER),
	DEFAULT_ACTOR_LANGUAGE(1100, ParamType.INTEGER),
	ANNUAL_INCOME_GREEN_BAND(3070, ParamType.INTEGER),
	ANNUAL_INCOME_YELLOW_BAND(3071, ParamType.INTEGER),
	SETTLEMENT_VALIDITY_DAYS(1305, ParamType.INTEGER),
	MAXIMUM_RATE_APR(1103, ParamType.PERCENTAGE_RATE),
	MAXIMUM_RATE_FLAT_RATE(1104, ParamType.PERCENTAGE_RATE),
	FIRST_REG_FEE(1303, ParamType.AMOUNT),
	NEW_VEHICLE_AVAILABILITY_PERIOD(1004, ParamType.INTEGER),
	USED_VEHICLE_VEHICLE_CLASS(1035, ParamType.STRING),
	USED_VEHICLE_MAINTENANCE_CLASS(1036, ParamType.STRING),
	COMMISSION_DEPOSIT_CONTRIBUTION_LABEL(1314, ParamType.STRING),
	BALLOON_MIN_AMOUNT_PCP(3060, ParamType.AMOUNT),
	BALLOON_MIN_AMOUNT_LH_LP(3061, ParamType.AMOUNT),
	BALLOON_MIN_PERCENTAGE(1042, ParamType.PERCENTAGE_RATE),
	BALLOON_MAX_PERCENTAGE(1043, ParamType.PERCENTAGE_RATE),
	VAT_DEFAULT_CODE(1015, ParamType.STRING),
	MAX_AGREEMENT_COPIES(1358, ParamType.INTEGER),
	CODE_PARAM_DEFAULT_AUTO_DECLINE(1505, ParamType.INTEGER),
	CREDIT_CHECK_REUSE_AGE_DAYS(1309, ParamType.INTEGER), // We reuse the result of a credit check as long as it was not done more than this many days ago
	RV_BALLOON_PERCENTAGE_CALCULATION_INCL_DEALER_ITEMS(1272, ParamType.BOOLEAN),
	RV_BALLOON_PERCENTAGE_CALCULATION_INCL_FFO(1273, ParamType.BOOLEAN),
	ESIGN_DAYS_TO_LIVE(4005, ParamType.INTEGER),
	WITHDRAWAL_PERIOD(154, ParamType.INTEGER),
	ACCESSORIES_MAX_TOTAL_AMOUNT(1260, ParamType.AMOUNT),
	FIRST_PAYMENT_DATE_MIN_DAYS(251, ParamType.INTEGER),
	FIRST_PAYMENT_DATE_MAX_DAYS(250, ParamType.INTEGER),
	;


	private final Integer _paramCode;

	private final ParamType _paramType;


	private ParamEnum(Integer pParamCode, ParamType pParamType) {
		_paramCode = pParamCode;
		_paramType = pParamType;
	}


	public Integer getParamCode() {
		return _paramCode;
	}

	public ParamType getParamType() {
		return _paramType;
	}


	public static ParamEnum valueOf(Integer pParamCode) {
		ParamEnum res = null;
		for(ParamEnum p : values()) {
			if(p.getParamCode().equals(pParamCode)) {
				res = p;
				break;
			}
		}
		return res;
	}

}
