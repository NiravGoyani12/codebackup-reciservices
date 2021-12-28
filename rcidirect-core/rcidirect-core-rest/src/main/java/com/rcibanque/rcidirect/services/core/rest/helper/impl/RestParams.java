package com.rcibanque.rcidirect.services.core.rest.helper.impl;

import java.util.HashMap;
import java.util.Map;

public final class RestParams {


	private final Map<String, String> _pathParams = new HashMap<>(4);

	private final Map<String, String> _queryParams = new HashMap<>(4);


	RestParams() {
		super();
	}


	/**
	 * Defines a path parameter to add to a URL, encoded if needed
	 * </br></br>
	 * <u>Example:</u>
	 * <ul>
	 * <li>URL = http://address/{param_name}/path</li>
	 * <li>Name = param_name</li>
	 * <li>Value = param_value</li>
	 * <li>Result = http://address/param_value/path</li>
	 * </ul>
	 *
	 * @param pName Parameter name
	 * @param pValue Parameter value
	 * @return Current instance
	 */
	public RestParams path(String pName, String pValue) {
		_pathParams.put(pName, pValue);
		return this;
	}

	/**
	 * Defines a query parameter to add to a URL, encoded if needed
	 * </br></br>
	 * <u>Example:</u>
	 * <ul>
	 * <li>URL = http://address/path</li>
	 * <li>Name = param_name</li>
	 * <li>Value = param_value</li>
	 * <li>Result = http://address/path?param_name=param_value</li>
	 * </ul>
	 *
	 * @param pName Parameter name
	 * @param pValue Parameter value
	 * @return Current instance
	 */
	public RestParams query(String pName, String pValue) {
		_queryParams.put(pName, pValue);
		return this;
	}


	Map<String, String> getPathParams() {
		return _pathParams;
	}


	Map<String, String> getQueryParams() {
		return _queryParams;
	}

}
