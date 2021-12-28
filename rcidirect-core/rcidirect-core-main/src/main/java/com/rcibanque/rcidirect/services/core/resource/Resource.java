package com.rcibanque.rcidirect.services.core.resource;

import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.web.context.request.WebRequest;

import com.rcibanque.rcidirect.services.core.domain.ContextFactory;
import com.rcibanque.rcidirect.services.core.domain.IContext;
import com.rcibanque.rcidirect.services.core.response.APIResponse;
import com.rcibanque.rcidirect.services.core.response.APIResponseEntity;
import com.rcibanque.rcidirect.services.core.user.UserFactory;

/**
 * REST resource
 * <ul>
 * <li>provides access to the context (user details and messages)</li>
 * <li>provides methods to build HTTP response</li>
 * </ul>
 */
public abstract class Resource {


	/**
	 * @return A new {@link IContext context}
	 */
	protected IContext initializeContext() {

		return ContextFactory.getContext();
	}


	/**
	 * Update session user
	 */
	protected static final void updateSessionUser(WebRequest pWebRequest) {

		UserFactory.updateSessionUser(pWebRequest);
	}


	private static <T> APIResponseEntity<T> response(HttpStatus pHttpStatus, IContext pContext, T pOutput) {
		return new APIResponseEntity<>(pHttpStatus, new APIResponse<T>(pOutput, pContext.getMessages()));
	}

	/**
	 * @param pContext Context
	 * @param pHttpStatus HTTP status
	 *
	 * @return A response with the provided HTTP status and no data
	 */
	protected static final <T> APIResponseEntity<T> response(IContext pContext, HttpStatus pHttpStatus) {
		return response(pHttpStatus, pContext, null);
	}

	/**
	 * @param pContext Context
	 * @param pOutput Data returned
	 *
	 * @return A response with {@link HttpStatus#OK} and provided data
	 */
	protected static final <T> APIResponseEntity<T> responseOK(IContext pContext, T pOutput) {
		return response(HttpStatus.OK, pContext, pOutput);
	}

	/**
	 * @param pContext Context
	 *
	 * @return A response with {@link HttpStatus#OK} and no data
	 */
	protected static final APIResponseEntity<Void> responseOK(IContext pContext) {
		return response(HttpStatus.OK, pContext, null);
	}

	/**
	 * @param pContext Context
	 *
	 * @return A response with {@link HttpStatus#NO_CONTENT} and no data
	 */
	protected static final <T> APIResponseEntity<T> responseNoContent(IContext pContext) {
		return response(HttpStatus.NO_CONTENT, pContext, null);
	}

	/**
	 * @param pRequest Request
	 * @return Client IP address
	 */
	protected static final String getClientIpAddress(HttpServletRequest pRequest) {

		String xForwardedForHeader = pRequest.getHeader("X-Forwarded-For");
		if (xForwardedForHeader == null) {
			return pRequest.getRemoteAddr();
		} else {
			// As of https://en.wikipedia.org/wiki/X-Forwarded-For
			// The general format of the field is: X-Forwarded-For: client, proxy1, proxy2 ...
			// we only want the client
			return new StringTokenizer(xForwardedForHeader, ",").nextToken().trim();
		}
	}
}
