package com.rcibanque.rcidirect.services.core.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * REST service call response entity
 * <ul>
 * <li>Introduced mainly for typing, to simplify code a bit</li>
 * <li>New Constructors could be introduced, based on super class</li>
 * </ul>
 */
public final class APIResponseEntity<T> extends ResponseEntity<APIResponse<T>> {


	public APIResponseEntity(HttpStatus pStatusCode, APIResponse<T> pBody) {
		super(pBody, pStatusCode);
	}


}
