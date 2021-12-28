package com.rcibanque.rcidirect.services.core.exception;

import com.rcibanque.rcidirect.services.core.response.APIError;

/**
 * Runtime exception raised when:
 * <ul>
 * <li>an element to process does not exist</li>
 * </ul>
 */
public final class EntityNotFoundException extends AbstractException {

	private static final long serialVersionUID = -1L;


	EntityNotFoundException(APIError pError) {
		super(pError);
	}


}