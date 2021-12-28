package com.rcibanque.rcidirect.services.core.rest.context.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.rcibanque.rcidirect.services.core.rest.context.IRestContext;

public class RestContext implements IRestContext {

	private static final Logger LOGGER = LoggerFactory.getLogger(RestContext.class);


	private final MultiValueMap<String, String> _httpHaders;


	public RestContext() {
		super();
		_httpHaders = new LinkedMultiValueMap<>();
	}


	@Override
	public final MultiValueMap<String, String> getHttpHeaders() {

		return _httpHaders;
	}


	protected void setHttpHeader(String pName, String pValue) {
		LOGGER.debug("Setting HTTP header: {} = {}", pName, pValue);
		_httpHaders.set(pName, pValue);
	}

}
