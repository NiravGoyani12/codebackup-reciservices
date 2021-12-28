package com.rcibanque.rcidirect.services.core.rest.helper.impl;

import java.io.IOException;
import java.net.ConnectException;
import java.net.URI;
import java.util.Arrays;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.core.type.TypeReference;
import com.rcibanque.rcidirect.services.core.domain.IContext;
import com.rcibanque.rcidirect.services.core.exception.ExceptionUtils;
import com.rcibanque.rcidirect.services.core.response.APIMessage;
import com.rcibanque.rcidirect.services.core.response.APIResponse;
import com.rcibanque.rcidirect.services.core.response.APIResponseEntity;
import com.rcibanque.rcidirect.services.core.rest.context.IRestContext;
import com.rcibanque.rcidirect.services.core.rest.helper.IRestHelper;
import com.rcibanque.rcidirect.services.core.rest.utils.CoreRestMessages;
import com.rcibanque.rcidirect.services.core.utils.IteratorUtils;
import com.rcibanque.rcidirect.services.core.utils.RestUtils;

public class RestHelper implements IRestHelper {


	private static final Logger LOGGER = LoggerFactory.getLogger(RestHelper.class);


	private static final IRestHelper INSTANCE = new RestHelper();

	public static final IRestHelper get() {
		return INSTANCE;
	}


	private final RestTemplate _restTemplate = new RestTemplate(Arrays.asList(RestUtils.getJSONMessageConverter(), RestUtils.getJPGImageMessageConverter()));

	protected RestTemplate getRestTemplate() { // Can be overridden in sub-classes
		return _restTemplate;
	}


	public static RestParams newParams() {
		return new RestParams();
	}


	@Override
	public <T> APIResponseEntity<T> get(IRestContext pContext, String pServiceUrl, RestParams pParams, RestType<T> pResponseType) {

		return exchange(pContext, HttpMethod.GET, pServiceUrl, pParams, null, pResponseType);
	}

	@Override
	public <T> APIResponseEntity<T> get(IContext pContext, String pServiceUrl, RestParams pParams, RestType<T> pResponseType, boolean pProcessResponse) {

		return exchange(pContext, HttpMethod.GET, pServiceUrl, pParams, null, pResponseType, pProcessResponse);
	}


	@Override
	public <T> APIResponseEntity<T> post(IRestContext pContext, String pServiceUrl, RestParams pParams, Object pBody, RestType<T> pResponseType) {

		return exchange(pContext, HttpMethod.POST, pServiceUrl, pParams, pBody, pResponseType);
	}

	@Override
	public <T> APIResponseEntity<T> post(IContext pContext, String pServiceUrl, RestParams pParams, Object pBody, RestType<T> pResponseType, boolean pProcessResponse) {

		return exchange(pContext, HttpMethod.POST, pServiceUrl, pParams, pBody, pResponseType, pProcessResponse);
	}


	@Override
	public <T> APIResponseEntity<T> put(IRestContext pContext, String pServiceUrl, RestParams pParams, Object pBody, RestType<T> pResponseType) {

		return exchange(pContext, HttpMethod.PUT, pServiceUrl, pParams, pBody, pResponseType);
	}

	@Override
	public <T> APIResponseEntity<T> put(IContext pContext, String pServiceUrl, RestParams pParams, Object pBody, RestType<T> pResponseType, boolean pProcessResponse) {

		return exchange(pContext, HttpMethod.PUT, pServiceUrl, pParams, pBody, pResponseType, pProcessResponse);
	}


	@Override
	public <T> APIResponseEntity<T> delete(IRestContext pContext, String pServiceUrl, RestParams pParams, RestType<T> pResponseType) {

		return exchange(pContext, HttpMethod.DELETE, pServiceUrl, pParams, null, pResponseType);
	}

	@Override
	public <T> APIResponseEntity<T> delete(IContext pContext, String pServiceUrl, RestParams pParams, RestType<T> pResponseType, boolean pProcessResponse) {

		return exchange(pContext, HttpMethod.DELETE, pServiceUrl, pParams, null, pResponseType, pProcessResponse);
	}


	private <T> APIResponseEntity<T> exchange(IRestContext pContext,
			HttpMethod pHttpMethod, String pServiceUrl,
			RestParams pParams,
			Object pBody,
			RestType<T> pResponseType) {

		APIResponseEntity<T> result = null;

		LOGGER.debug("Calling {} method on {}", pHttpMethod, pServiceUrl);

		// Build complete URI, with path and query parameters
		UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(processServiceURL(pServiceUrl));

		if(pParams != null) {
			for(Entry<String, String> param : IteratorUtils.nullableIterable(pParams.getQueryParams())) {
				uriBuilder.queryParam(param.getKey(), param.getValue());
			}
		}

		UriComponents uriComponents = uriBuilder.build();
		if(pParams != null) {
			uriComponents = uriComponents.expand(pParams.getPathParams());
		}
		URI uri = uriComponents.toUri();

		// Call REST end-point
		try {

			ResponseEntity<APIResponse<T>> response = getRestTemplate().exchange(
					uri,
					pHttpMethod,
					new HttpEntity<>(pBody, pContext != null ? pContext.getHttpHeaders() : null), // HTTP headers can contain login details (login end-point) or authorisation details (any other end-point)
					pResponseType);

			result = new APIResponseEntity<>(response.getStatusCode(), response.getBody());

			// Update HTTP headers with authentication details, used on next calls above
			if(pContext != null) {
				pContext.updateHttpHeadersAfterCall(response.getHeaders());
			}

		}
		catch(ResourceAccessException ex) {
			LOGGER.error("Call failure : {}", ex.getMessage());

			throw new RestClientException("Could not connect to service", ex);
		}
		catch(HttpStatusCodeException ex) {
			LOGGER.error("Call failure ({}) : {}", ex.getStatusCode(), ex.getResponseBodyAsString());

			try {
				APIResponse<T> body = RestUtils.getObjectMapper().readValue(ex.getResponseBodyAsString(), new TypeReference<APIResponse<T>>() {});

				result = new APIResponseEntity<>(ex.getStatusCode(), body);
			}
			catch (IOException e) {
				if(ex.getMessage().contains(String.valueOf(HttpStatus.NOT_FOUND.value()))) {
					throw new RestClientException("Could not connect to service", ex);
				}
				else{
					throw new RestClientException("Could not read service response", e);
				}
			}
		}

		return result;
	}

	protected String processServiceURL(String pServiceURL) {
		return pServiceURL;
	}


	private <T> APIResponseEntity<T> exchange(IContext pContext,
			HttpMethod pHttpMethod, String pServiceUrl,
			RestParams pParams, Object pBody,
			RestType<T> pResponseType,
			boolean pProcessResponse) {

		APIResponseEntity<T> result = null;

		try {

			result = exchange(createRestContext(pContext), pHttpMethod, pServiceUrl, pParams, pBody, pResponseType);

			if(pProcessResponse) {

				// Process response body
				if(result.getBody() != null) {

					// Add response body messages to context
					for(APIMessage message : IteratorUtils.nullableIterable(result.getBody().getMessages())) {
						pContext.getMessages().message(message);
					}
				}

				// Process response status
				if(result.getStatusCode().is4xxClientError()) {
					if(result.getStatusCode().equals(HttpStatus.NOT_FOUND)) {
						ExceptionUtils.throwEntityNotFoundException(pContext);
					}
					else {
						ExceptionUtils.throwInvalidRequestException(pContext);
					}
				}
				else if(result.getStatusCode().is5xxServerError()) {
					ExceptionUtils.throwInternalServerErrorException(pContext);
				}
			}
		}
		catch(RestClientException e) {

			if(e.getCause() != null
					&& e.getCause().getClass().equals(ResourceAccessException.class)
					&& e.getCause().getCause() != null
					&& e.getCause().getCause().getClass().equals(ConnectException.class)) {

				pContext.getMessages().error(CoreRestMessages.getInstance(), "error.rest.failure.connection");
			}
			else {
				pContext.getMessages().error(CoreRestMessages.getInstance(), "error.rest.failure");
			}
			pContext.getMessages().log(e.getMessage());
			ExceptionUtils.throwServiceInvocationException(pContext);
		}

		return result;
	}


	protected IRestContext createRestContext(IContext pContext) {
		return null;
	}


}
