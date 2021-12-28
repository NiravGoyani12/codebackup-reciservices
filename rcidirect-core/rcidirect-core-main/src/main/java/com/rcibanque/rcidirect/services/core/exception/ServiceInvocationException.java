package com.rcibanque.rcidirect.services.core.exception;

import com.rcibanque.rcidirect.services.core.response.APIError;

/**
 * Runtime exception raised when:
 * <ul>
 * <li>a third-party web-service call fails</li>
 * </ul>
 */
public final class ServiceInvocationException extends AbstractException {

	private static final long serialVersionUID = 1L;


	ServiceInvocationException(APIError pError) {
		super(pError);
	}


}
