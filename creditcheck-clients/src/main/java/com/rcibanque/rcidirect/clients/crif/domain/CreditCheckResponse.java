package com.rcibanque.rcidirect.clients.crif.domain;

import com.rcibanque.rcidirect.services.core.domain.DTO;

public class CreditCheckResponse extends DTO {

	private static final long serialVersionUID = 7184724604384776577L;

	private WebServiceData _webServiceData;

	public WebServiceData getWebServiceData() {
		return _webServiceData;
	}

	public void setWebServiceData(WebServiceData pWebServiceData) {
		_webServiceData = pWebServiceData;
	}


}
