package com.rcibanque.rcidirect.services.core.exception;

import com.rcibanque.rcidirect.services.core.response.APIError;

/**
 * Runtime exception raised when:
 * <ul>
 * <li>an element is not locked or cannot be locked</li>
 * </ul>
 */
public final class ForbiddenException extends AbstractException {

	private static final long serialVersionUID = 1L;


	ForbiddenException(APIError pError) {
		super(pError);
	}


}
