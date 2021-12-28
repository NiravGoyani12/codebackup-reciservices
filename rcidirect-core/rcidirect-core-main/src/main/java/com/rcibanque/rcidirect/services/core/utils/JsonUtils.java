package com.rcibanque.rcidirect.services.core.utils;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtils {

	private static final Logger LOGGER = LoggerFactory.getLogger(JsonUtils.class);


	private JsonUtils() {}


	/**
	 * Converts a java bean into its JSON representation
	 *
	 * @param pMapper The mapper to use
	 * @param pObject The java bean to be converted
	 * @return A JSON representation of the java bean, or null if the object cannot be converted
	 * @see RestUtils#getObjectMapper()
	 */
	public static <T> String toJSON(ObjectMapper pMapper, T pObject) {

		String retval = null;

		if (pObject != null) {

			try {
				retval = pMapper.writeValueAsString(pObject);
			}
			catch (JsonProcessingException e) {
				LOGGER.warn("Object > Json conversion failure: {}", e.getMessage());
			}
		}

		return retval;
	}

	/**
	 * Converts a JSON string representation of a java bean into an instance of the bean class
	 *
	 * @param pMapper The mapper to use
	 * @param pJSON The JSON representation of the java bean
	 * @param pClass The class of the java bean to be instantiated
	 * @return An instance of the java bean, or null if the JSON cannot be converted
	 * @see RestUtils#getObjectMapper()
	 */
	public static <T> T fromJSON(ObjectMapper pMapper, String pJSON, Class<T> pClass) {

		T retval = null;

		if (StringUtils.isNotBlank(pJSON)) {

			try {
				retval = pMapper.readValue(pJSON, pClass);
			}
			catch (IOException e) {
				LOGGER.warn("Json > Object conversion failure: {}", e.getMessage());
			}
		}

		return retval;
	}

}
