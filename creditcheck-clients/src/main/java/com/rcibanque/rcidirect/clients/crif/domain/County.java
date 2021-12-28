package com.rcibanque.rcidirect.clients.crif.domain;

public enum County {

	DUBLIN1("01", "DUBLIN"),
	DUBLIN2("02", "DUBLIN"),
	DUBLIN3("03", "DUBLIN"),
	DUBLIN4("04", "DUBLIN"),
	DUBLIN5("05", "DUBLIN"),
	DUBLIN6("06", "DUBLIN"),
	DUBLIN7("07", "DUBLIN"),
	DUBLIN8("08", "DUBLIN"),
	DUBLIN9("09", "DUBLIN"),
	DUBLIN10("10", "DUBLIN"),
	DUBLIN11("11", "DUBLIN"),
	DUBLIN12("12", "DUBLIN"),
	DUBLIN13("13", "DUBLIN"),
	DUBLIN14("14", "DUBLIN"),
	DUBLIN15("15", "DUBLIN"),
	DUBLIN16("16", "DUBLIN"),
	DUBLIN17("17", "DUBLIN"),
	DUBLIN18("18", "DUBLIN"),
	DUBLIN19("19", "DUBLIN"),
	DUBLIN20("20", "DUBLIN"),
	DUBLIN21("21", "DUBLIN"),
	DUBLIN22("22", "DUBLIN"),
	DUBLIN23("23", "DUBLIN"),
	DUBLIN24("24", "DUBLIN"),
	CLARE("CL","CLARE"),
	CAVAN("CN", "CAVAN"),
	CORK("CO", "CORK"),
	CARLOW("CW", "CARLOW"),
	DONEGAL("DL", "DONEGAL"),
	DUBLIN("DU","DUBLIN"),
	GALWAY("GA", "GALWAY"),
	KILDARE("KE", "KILDARE"),
	KERRY("KR", "KERRY"),
	KILKENNY("KY", "KILKENNY"),
	LAOIS("LA", "LAOIS"),
	LONGFORD("LD", "LONGFORD"),
	LEITRIM("LE", "LEITRIM"),
	LOUTH("LH", "LOUTH"),
	LIMERICK("LI", "LIMERICK"),
	MAYO("MA", "MAYO"),
	MEATH("ME", "MEATH"),
	MONAGHAN("MO", "MONAGHAN"),
	OFFALY("OF", "OFFALY"),
	ROSCOMMON("RO", "ROSCOMMON"),
	SLIGO("SL", "SLIGO"),
	TIPPERARY("TI", "TIPPERARY"),
	WATERFORD("WA", "WATERFORD"),
	WEXFORD("WD", "WEXFORD"),
	WESTMEATH("WH", "WESTMEATH"),
	WICKLOW("WI", "WICKLOW");

	private final String _countyCode;

	private final String _county;

	private County(String pCountyCode, String pCounty) {
		_countyCode = pCountyCode;
		_county = pCounty;
	}

	public String getCountyCode() {
		return _countyCode;
	}

	public String getCounty() {
		return _county;
	}

	public static County getCounty(String pCountyCode) {
		County res = null;
		for (County p : values()) {
			if (p.getCountyCode().equals(pCountyCode)) {
				res = p;
				break;
			}
		}
		return res;
	}

}
