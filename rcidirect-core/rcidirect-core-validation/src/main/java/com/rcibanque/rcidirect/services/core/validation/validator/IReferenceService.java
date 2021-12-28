package com.rcibanque.rcidirect.services.core.validation.validator;

import com.rcibanque.rcidirect.services.core.domain.IContext;

public interface IReferenceService {

	/**
	 * @param pContext Context
	 * @param pReferenceKey The ID of the validated object
	 * @return Reference object
	 */
	Object getObject(IContext pContext, String pReferenceKey);

}
