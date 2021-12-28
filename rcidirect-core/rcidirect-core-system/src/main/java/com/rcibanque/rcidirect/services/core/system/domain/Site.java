package com.rcibanque.rcidirect.services.core.system.domain;

import com.rcibanque.rcidirect.services.core.domain.DTO;

public class Site extends DTO {

	private static final long serialVersionUID = 1L;


	private String _siteCode;

	private String _siteLabel;

	private String _countryCode;


	public String getSiteCode() {
		return _siteCode;
	}

	public void setSiteCode(String pSiteCode) {
		this._siteCode = pSiteCode;
	}

	public String getSiteLabel() {
		return _siteLabel;
	}

	public void setSiteLabel(String pSiteLabel) {
		this._siteLabel = pSiteLabel;
	}

	public String getCountryCode() {
		return _countryCode;
	}

	public void setCountryCode(String pCountryCode) {
		_countryCode = pCountryCode;
	}


}
