package com.rcibanque.rcidirect.services.core.validation.detail;

import java.util.List;

import com.rcibanque.rcidirect.services.core.domain.ValidityPeriod;
import com.rcibanque.rcidirect.services.core.utils.MessageBundle;
import com.rcibanque.rcidirect.services.core.validation.config.rule.ValidationRuleRef;

public final class ValidationDetails {


	private MessageBundle _messageBundle;

	private String _messageKey;

	private IValidationFormat _validationFormat;

	private ValidityPeriod _validityPeriod;

	private String _expectation;

	private List<ValidationRuleRef> _refs;

	private ReferencesType _refsType;


	public MessageBundle getMessageBundle() {
		return _messageBundle;
	}

	public void setMessageBundle(MessageBundle pMessageBundle) {
		_messageBundle = pMessageBundle;
	}


	public String getMessageKey() {
		return _messageKey;
	}

	public void setMessageKey(String pMessageKey) {
		_messageKey = pMessageKey;
	}

	public IValidationFormat getFormat() {
		return _validationFormat;
	}

	public void setFormat(IValidationFormat pValidationFormat) {
		_validationFormat = pValidationFormat;
	}


	public ValidityPeriod getValidityPeriod() {
		return _validityPeriod;
	}

	public void setValidityPeriod(ValidityPeriod pValidityPeriod) {
		_validityPeriod = pValidityPeriod;
	}


	public String getExpectation() {
		return _expectation;
	}

	public void setExpectation(String pExpectation) {
		_expectation = pExpectation;
	}

	public List<ValidationRuleRef> getRefs() {
		return _refs;
	}

	public void setRefs(List<ValidationRuleRef> pRefs) {
		_refs = pRefs;
	}

	public ReferencesType getRefsType() {
		return _refsType;
	}

	public void setRefsType(ReferencesType pRefsType) {
		_refsType = pRefsType;
	}


}
