package com.rcibanque.rcidirect.services.creditcheck.domain;

import java.util.List;

public class IECreditCheckCriteria extends CreditCheckCriteria {

	private static final long serialVersionUID = -2513865750454061074L;

	private List<String> _providers;

	private String _enquiryType;

	private CallerType _callerType;

	private CallType _callType;

	public List<String> getProviders() {
		return _providers;
	}

	public void setProviders(List<String> pProviders) {
		_providers = pProviders;
	}

	public String getEnquiryType() {
		return _enquiryType;
	}

	public void setEnquiryType(String pEnquiryType) {
		_enquiryType = pEnquiryType;
	}

	public CallerType getCallerType() {
		return _callerType;
	}

	public void setCallerType(CallerType pCallerType) {
		_callerType = pCallerType;
	}

	public CallType getCallType() {
		return _callType;
	}

	public void setCallType(CallType pCallType) {
		_callType = pCallType;
	}

}
