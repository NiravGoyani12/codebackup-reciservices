package com.rcibanque.rcidirect.services.core.validation.key;

public interface IValidationField {

	/**
	 * @return The validated field <i>domain</i> name
	 */
	String getDomainName();

	/**
	 * @return The validated field name (inside the <i>domain</i>)
	 */
	String getFieldName();

}
