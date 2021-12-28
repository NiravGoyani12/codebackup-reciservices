package com.rcibanque.rcidirect.services.core.exception;

import com.rcibanque.rcidirect.services.core.response.APIError;

/**
 * Root exception
 * <ul>
 * <li>holds a {@link APIError}</li>
 * </ul>
 */
@SuppressWarnings("serial")
abstract class AbstractException extends RuntimeException {

	private final APIError _error;


	AbstractException(APIError pError) {
		super();
		_error = pError;
	}


	final APIError getError() {
		return _error;
	}


}
