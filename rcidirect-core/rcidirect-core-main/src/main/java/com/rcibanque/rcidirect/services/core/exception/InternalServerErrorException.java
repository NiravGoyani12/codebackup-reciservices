package com.rcibanque.rcidirect.services.core.exception;

import com.rcibanque.rcidirect.services.core.response.APIError;

/**
 * Runtime exception raised when:
 * <ul>
 * <li>something went wrong</li>
 * </ul>
 */
public final class InternalServerErrorException extends AbstractException {

	private static final long serialVersionUID = 1L;


	InternalServerErrorException(APIError pError) {
		super(pError);
	}


}
