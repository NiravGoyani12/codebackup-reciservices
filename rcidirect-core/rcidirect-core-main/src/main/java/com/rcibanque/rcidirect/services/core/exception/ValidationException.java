package com.rcibanque.rcidirect.services.core.exception;

import com.rcibanque.rcidirect.services.core.response.APIError;

/**
 * Runtime exception raised when:
 * <ul>
 * <li>validation fails (input object, search criteria, etc.)</li>
 * </ul>
 */
public final class ValidationException extends AbstractException {

	private static final long serialVersionUID = 1L;


	ValidationException(APIError pError) {
		super(pError);
	}

}
