package com.rcibanque.rcidirect.services.core.validation.detail;

import java.util.regex.Pattern;

public interface IValidationFormat {


	/**
	 * @return Format regular expression compiled pattern to validate value
	 */
	Pattern getRegExp();

}
