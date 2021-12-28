package com.rcibanque.rcidirect.services.creditcheck.domain;

import java.util.Arrays;
import java.util.List;

public enum CallType {

	NEW("NEW", Arrays.asList("NAE")),
	UPDATE("UPDATE", Arrays.asList("NAE")),
	ADDITIONAL("ADDITIONAL", Arrays.asList("NAE", "ICB"));

	private final String _callType;

	private final List<String> _enquiryTypeCheckCriteria;

	private CallType(String pCallType, List<String> pEnquiryTypeCheckCriteria) {
		_callType = pCallType;
		_enquiryTypeCheckCriteria = pEnquiryTypeCheckCriteria;
	}

	public String getCallType() {
		return _callType;
	}

	public List<String> getEnquiryTypeCheckCriteria() {
		return _enquiryTypeCheckCriteria;
	}

	public static CallType getCallType(String pCallType) {
		CallType res = null;
		for (CallType p : values()) {
			if (p.getCallType().equals(pCallType)) {
				res = p;
				break;
			}
		}
		return res;
	}


}
