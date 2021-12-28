package com.rcibanque.rcidirect.services.core.test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.junit.jupiter.api.Assertions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rcibanque.rcidirect.services.core.domain.IContext;
import com.rcibanque.rcidirect.services.core.response.APIMessage;
import com.rcibanque.rcidirect.services.core.response.APIMessageStatus;
import com.rcibanque.rcidirect.services.core.response.APIResponse;
import com.rcibanque.rcidirect.services.core.utils.IteratorUtils;
import com.rcibanque.rcidirect.services.core.utils.RestUtils;

public abstract class AbstractTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(AbstractTest.class);


	protected abstract MockMvc getMockMvc();


	protected static final TypeReference<APIResponse<Void>> getVoidTypeReference() {

		return new TypeReference<APIResponse<Void>>() { };
	}

	protected static final TypeReference<APIResponse<Boolean>> getBooleanTypeReference() {

		return new TypeReference<APIResponse<Boolean>>() { };
	}


	protected final <T> APIResponse<T> performGet(String url, Map<String, String> pParams, HttpStatus pStatus, TypeReference<APIResponse<T>> pTypeReference) throws Exception {

		return perform(restGet(url, pParams), pStatus, pTypeReference);
	}

	protected final <T> APIResponse<T> performGet(String url, HttpStatus pStatus, TypeReference<APIResponse<T>> pTypeReference) throws Exception {

		return performGet(url, null, pStatus, pTypeReference);
	}

	protected final <T> APIResponse<T> performPut(String url, Object pObject, HttpStatus pStatus, TypeReference<APIResponse<T>> pTypeReference) throws Exception {

		return perform(restPut(url, getJSONString(pObject)), pStatus, pTypeReference);
	}

	protected final <T> APIResponse<T> performPost(String url, Object pObject, HttpStatus pStatus, TypeReference<APIResponse<T>> pTypeReference) throws Exception {

		return perform(restPost(url, getJSONString(pObject)), pStatus, pTypeReference);
	}

	protected final <T> APIResponse<T> performDelete(String url, HttpStatus pStatus, TypeReference<APIResponse<T>> pTypeReference) throws Exception {

		return perform(restDelete(url), pStatus, pTypeReference);
	}


	private final <T> APIResponse<T> perform(RequestBuilder pRequestBuilder, HttpStatus pStatus, TypeReference<APIResponse<T>> pTypeReference) throws Exception {

		APIResponse<T> res;

		ResultActions resultActions = getMockMvc().perform(pRequestBuilder)
				.andExpect(status().is(pStatus.value()))
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE));

		if(LOGGER.isDebugEnabled()) {
			resultActions.andDo(print());
		}

		MvcResult result = resultActions.andReturn();

		res = getAPIResponse(result, pTypeReference);

		assertThat(res).isNotNull();

		return res;
	}


	private final MockHttpServletRequestBuilder restPost(String url, String jsonString) {
		return post(url)
				.with(csrf())
				.accept(MediaType.APPLICATION_JSON_VALUE)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(jsonString);
	}

	private final MockHttpServletRequestBuilder restPut(String url, String jsonString) {
		return put(url)
				.with(csrf())
				.accept(MediaType.APPLICATION_JSON_VALUE)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(jsonString);
	}

	private final MockHttpServletRequestBuilder restGet(String url, Map<String, String> pParameters) {
		MockHttpServletRequestBuilder res = get(url)
				.accept(MediaType.APPLICATION_JSON_VALUE)
				.contentType(MediaType.APPLICATION_JSON_VALUE);
		addParameters(res, pParameters);
		return res;
	}

	private final MockHttpServletRequestBuilder restDelete(String url) {
		return delete(url)
				.with(csrf())
				.accept(MediaType.APPLICATION_JSON_VALUE)
				.contentType(MediaType.APPLICATION_JSON_VALUE);
	}

	private final void addParameters(MockHttpServletRequestBuilder pBuilder, Map<String, String> pParameters) {
		for(Entry<String, String> param : IteratorUtils.nullableIterable(pParameters)) {
			pBuilder.param(param.getKey(), param.getValue());
		}
	}


	private static final String getJSONString(Object object) {

		String res = null;
		try {
			res = getObjectMapper().writeValueAsString(object);
		}
		catch (JsonProcessingException e) {
			fail(e);
		}
		return res;
	}

	private static final <T> APIResponse<T> getAPIResponse(MvcResult pResult, TypeReference<APIResponse<T>> pTypeReference) {

		APIResponse<T> res = null;
		try {
			res = getObjectMapper().readValue(pResult.getResponse().getContentAsString(), pTypeReference);
		}
		catch (IOException e) {
			fail(e);
		}

		return res;
	}

	protected static final ObjectMapper getObjectMapper() {

		return RestUtils.getObjectMapper();
	}


	/**
	 * Tests that there is no data in the response, an error tracking ID, and at least one error message
	 *
	 * @param pResponse API response
	 */
	protected static final void testError(APIResponse<?> pResponse) {

		assertThat(pResponse).isNotNull();
		assertThat(pResponse.getData()).isNull();

		assertThat(pResponse.getErrorTrackingID()).isNotBlank();

		assertThat(pResponse.getMessages()).isNotEmpty();
		assertThat(pResponse.getMessages().stream().anyMatch(m -> APIMessageStatus.ERROR.equals(m.getStatus()) && StringUtils.isNotBlank(m.getDescription())));
	}

	/**
	 * Tests that there is at least one error message that contains (validation) details
	 *
	 * @param pResponse API response
	 * @see {@link #testError(APIResponse)}
	 */
	protected static final void testDetailedValidationError(APIResponse<?> pResponse) {

		testError(pResponse);

		assertThat(getValidationErrors(pResponse.getMessages())).isNotEmpty();
	}

	/**
	 * @param pContext Context
	 * @return Errors that contain (validation) details
	 */
	protected static final List<APIMessage> getValidationErrors(IContext pContext) {

		return getValidationErrors(pContext.getMessages().get(true));
	}

	private static final List<APIMessage> getValidationErrors(List<APIMessage> pMessages) {

		List<APIMessage> res = new ArrayList<>();

		for(APIMessage message : pMessages) {

			if(message.getStatus() == APIMessageStatus.ERROR && CollectionUtils.isNotEmpty(message.getDetails())) {
				res.add(message);
			}
		}

		return res;
	}


	/**
	 * Stops the test which ends in failure (with the exception stack trace as message)
	 *
	 * @param pEx Exception
	 */
	protected static final void fail(Exception pEx) {
		LOGGER.error(ExceptionUtils.getStackTrace(pEx));
		Assertions.fail(pEx);
	}

}