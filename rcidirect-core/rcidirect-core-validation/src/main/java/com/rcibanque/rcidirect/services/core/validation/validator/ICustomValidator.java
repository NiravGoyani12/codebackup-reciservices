package com.rcibanque.rcidirect.services.core.validation.validator;

import com.rcibanque.rcidirect.services.core.domain.IContext;

public interface ICustomValidator {

	/**
	 * @param pContext Context
	 * @param pReference Reference
	 * @param pValue Value
	 * @return True if value is valid
	 */
	boolean isValid(IContext pContext, Object pReference, Object pValue);

}
