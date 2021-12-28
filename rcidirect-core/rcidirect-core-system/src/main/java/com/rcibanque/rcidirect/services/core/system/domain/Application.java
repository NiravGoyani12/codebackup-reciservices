package com.rcibanque.rcidirect.services.core.system.domain;

import java.util.Date;

import com.rcibanque.rcidirect.services.core.domain.DTO;

public class Application extends DTO {

	private static final long serialVersionUID = 8838462662878610436L;


	private String _applicationName;

	private String _applicationVersion;

	private Date _applicationDate;

	private Language _defaultLanguage;


	public String getApplicationName() {
		return _applicationName;
	}

	public void setApplicationName(String pApplicationName) {
		_applicationName = pApplicationName;
	}

	public String getApplicationVersion() {
		return _applicationVersion;
	}

	public void setApplicationVersion(String pApplicationVersion) {
		_applicationVersion = pApplicationVersion;
	}

	public Date getApplicationDate() {
		return _applicationDate;
	}

	public void setApplicationDate(Date pApplicationDate) {
		_applicationDate = pApplicationDate;
	}

	public Language getDefaultLanguage() {
		return _defaultLanguage;
	}

	public void setDefaultLanguage(Language pDefaultLanguage) {
		_defaultLanguage = pDefaultLanguage;
	}


}
