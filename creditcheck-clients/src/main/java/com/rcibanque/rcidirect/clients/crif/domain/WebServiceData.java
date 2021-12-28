package com.rcibanque.rcidirect.clients.crif.domain;

import java.util.Date;

import com.rcibanque.rcidirect.services.core.domain.DTO;

public class WebServiceData extends DTO {

	private static final long serialVersionUID = 7930333983622428104L;

	private Long _webServiceId;

	private String _request;

	private Date _requestTime;

	private String _response;

	private Date _responseTime;

	private Integer _responseCode;

	public Long getWebServiceId() {
		return _webServiceId;
	}

	public void setWebServiceId(Long pWebServiceId) {
		_webServiceId = pWebServiceId;
	}

	public String getRequest() {
		return _request;
	}

	public void setRequest(String pRequest) {
		_request = pRequest;
	}

	public Date getRequestTime() {
		return _requestTime;
	}

	public void setRequestTime(Date pRequestTime) {
		_requestTime = pRequestTime;
	}

	public String getResponse() {
		return _response;
	}

	public void setResponse(String pResponse) {
		_response = pResponse;
	}

	public Date getResponseTime() {
		return _responseTime;
	}

	public void setResponseTime(Date pResponseTime) {
		_responseTime = pResponseTime;
	}

	public Integer getResponseCode() {
		return _responseCode;
	}

	public void setResponseCode(Integer pResponseCode) {
		_responseCode = pResponseCode;
	}

}
