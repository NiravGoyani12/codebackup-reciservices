package com.rcibanque.rcidirect.clients.crif.domain;

public enum EnquiryType {

	NAE("NAE", "NEW"),
	AUE("AUE", "UPDATE"),
	ICB("ICB", "ADDITIONAL");

	private final String _enquiryType;

	private final String _callType;

	public String getEnquiryType() {
		return _enquiryType;
	}

	public String getCallType() {
		return _callType;
	}

	private EnquiryType(String pEnquiryType, String pCallType) {
		_enquiryType = pEnquiryType;
		_callType = pCallType;
	}

	public static EnquiryType getEnquiryType(String pEnquiryType) {
		EnquiryType res = null;
		for (EnquiryType p : values()) {
			if (p.getEnquiryType().equals(pEnquiryType)) {
				res = p;
				break;
			}
		}
		return res;
	}

}
