package com.rcibanque.rcidirect.services.core.rest.context;

import org.springframework.http.HttpHeaders;
import org.springframework.util.MultiValueMap;

public interface IRestContext {


	MultiValueMap<String, String> getHttpHeaders();


	default void updateHttpHeadersBeforeLogin(String pUsername, String pPassword) {
		// Do nothing
	}

	default void updateHttpHeadersAfterCall(HttpHeaders pHttpHeaders) {
		// Do nothing
	}


}