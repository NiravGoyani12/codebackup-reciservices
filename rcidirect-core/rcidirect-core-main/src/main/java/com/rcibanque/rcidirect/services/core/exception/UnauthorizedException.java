package com.rcibanque.rcidirect.services.core.exception;

import java.io.Serializable;

public final class UnauthorizedException extends RuntimeException {

	private static final long serialVersionUID = 1L;


	private final Serializable _response;


	UnauthorizedException(Serializable pResponse) {
		super();
		_response = pResponse;
	}


	Serializable getResponse() {
		return _response;
	}


}
