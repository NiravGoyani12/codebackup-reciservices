package com.rcibanque.rcidirect.services.core.exception;

import com.rcibanque.rcidirect.services.core.response.APIError;

/**
 * Runtime exception raised when:
 * <ul>
 * <li>the request is incorrect (invalid parameters, etc.)</li>
 * <li>the request cannot be processed (the element to create already exists, etc.)</li>
 * </ul>
 */
public final class InvalidRequestException extends AbstractException {

	private static final long serialVersionUID = 1L;


	InvalidRequestException(APIError pError) {
		super(pError);
	}


}
