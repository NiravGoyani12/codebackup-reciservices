package com.rcibanque.rcidirect.services.creditcheck.domain;

import com.rcibanque.rcidirect.services.core.domain.DTO;
import com.rcibanque.rcidirect.services.webservice.domain.WebServiceData;

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
