package com.rcibanque.rcidirect.services.core.validation;

import com.rcibanque.rcidirect.services.core.response.APIMessageStatus;
import com.rcibanque.rcidirect.services.core.utils.MessageBundle;

public interface IValidation {

	/**
	 * @return Validation message status
	 */
	APIMessageStatus getStatus();

	/**
	 * @return Validation message key
	 */
	String getMessageKey();

	/**
	 * @return Validation message bundle
	 */
	MessageBundle getMessageBundle();

}
