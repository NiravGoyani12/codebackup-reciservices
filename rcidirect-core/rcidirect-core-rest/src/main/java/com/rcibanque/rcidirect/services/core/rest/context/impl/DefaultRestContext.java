package com.rcibanque.rcidirect.services.core.rest.context.impl;

import java.util.Base64;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;

/**
 * Rest context:
 * <ul>
 * <li>Basic authentication (login end-point) and authentication token (any other end-point)</li>
 * <li>XSRF token and cookie</li>
 * </ul>
 */
public final class DefaultRestContext extends RestContext {

	private static final Logger LOGGER = LoggerFactory.getLogger(DefaultRestContext.class);


	@Override
	public void updateHttpHeadersBeforeLogin(String pLogin, String pPassword) {

		LOGGER.debug("Updating HTTP headers before login");

		getHttpHeaders().clear();

		if(StringUtils.isNotBlank(pLogin) && StringUtils.isNotBlank(pPassword)) {

			String encodedLoginDetails = Base64.getEncoder().encodeToString((pLogin + ":" + pPassword).getBytes());
			String authorizationHeaderValue = "Basic " + encodedLoginDetails;

			setHttpHeader("authorization", authorizationHeaderValue);
		}
	}


	@Override
	public void updateHttpHeadersAfterCall(HttpHeaders pHttpHeaders) {

		LOGGER.debug("Updating HTTP headers after REST call");

		// XSRF token
		String xsrfToken = null;
		List<String> cookies = pHttpHeaders.get("Set-Cookie");
		if(cookies != null) {
			for(String cookie : cookies) {
				for(String cookieData : StringUtils.split(cookie, ";")) {
					String[] cookieDataParts = StringUtils.split(cookieData, "=");
					if(cookieDataParts[0].equals("XSRF-TOKEN")) {
						xsrfToken = cookieDataParts[1];
						break;
					}
				}
				if(xsrfToken != null) break;
			}
		}
		if(xsrfToken == null) {
			xsrfToken = getHttpHeaders().getFirst("x-xsrf-token");
		}

		// AUTH token
		List<String> sessionTokenHeader = pHttpHeaders.get("X-AUTH-TOKEN");
		String sessionToken = CollectionUtils.isNotEmpty(sessionTokenHeader) ? sessionTokenHeader.get(0) : null;
		if(sessionToken == null) {
			sessionToken = getHttpHeaders().getFirst("x-auth-token");
		}

		getHttpHeaders().clear();
		setHttpHeader("x-auth-token", sessionToken);
		setHttpHeader("x-xsrf-token", xsrfToken);
	}


}
