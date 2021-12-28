package com.rcibanque.rcidirect.services.core.validation.tests.detail;

import java.util.regex.Pattern;

import com.rcibanque.rcidirect.services.core.validation.detail.IValidationFormat;
import com.rcibanque.rcidirect.services.core.validation.utils.ValidationRuleUtils;

public enum TestValidationFormat implements IValidationFormat {

	ALPHABETIC("[a-zA-Z]*");

	private Pattern _pattern;

	private TestValidationFormat(String pRegExp) {
		_pattern = ValidationRuleUtils.compileRegularExpression(pRegExp);
	}

	@Override
	public Pattern getRegExp() {
		return _pattern;
	}

}
