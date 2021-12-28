package com.rcibanque.rcidirect.services.core.rest.helper;

import com.rcibanque.rcidirect.services.core.domain.IContext;
import com.rcibanque.rcidirect.services.core.response.APIResponseEntity;
import com.rcibanque.rcidirect.services.core.rest.context.IRestContext;
import com.rcibanque.rcidirect.services.core.rest.helper.impl.RestParams;
import com.rcibanque.rcidirect.services.core.rest.helper.impl.RestType;

public interface IRestHelper {


	<T> APIResponseEntity<T> get(IRestContext pContext, String pServiceUrl, RestParams pParams, RestType<T> pResponseType);

	<T> APIResponseEntity<T> get(IContext pContext, String pServiceUrl, RestParams pParams, RestType<T> pResponseType, boolean pProcessResponse);


	<T> APIResponseEntity<T> post(IRestContext pContext, String pServiceUrl, RestParams pParams, Object pBody, RestType<T> pResponseType);

	<T> APIResponseEntity<T> post(IContext pContext, String pServiceUrl, RestParams pParams, Object pBody, RestType<T> pResponseType, boolean pProcessResponse);


	<T> APIResponseEntity<T> put(IRestContext pContext,String pServiceUrl, RestParams pParams, Object pBody, RestType<T> pResponseType);

	<T> APIResponseEntity<T> put(IContext pContext,String pServiceUrl, RestParams pParams, Object pBody, RestType<T> pResponseType, boolean pProcessResponse);


	<T> APIResponseEntity<T> delete(IRestContext pContext, String pServiceUrl, RestParams pParams, RestType<T> pResponseType);

	<T> APIResponseEntity<T> delete(IContext pContext, String pServiceUrl, RestParams pParams, RestType<T> pResponseType, boolean pProcessResponse);

}