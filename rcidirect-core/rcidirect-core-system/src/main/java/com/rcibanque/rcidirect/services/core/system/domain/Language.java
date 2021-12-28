package com.rcibanque.rcidirect.services.core.system.domain;

import java.util.Locale;

import com.rcibanque.rcidirect.services.core.domain.DTO;

public class Language extends DTO {

	private static final long serialVersionUID = 1L;


	private Integer _languageCode;

	private String _languageLabel;

	private String _languageCodeISO; // ISO 639

	private String _countryCodeISO; // ISO 3166

	private Locale _locale;


	public Integer getLanguageCode() {
		return _languageCode;
	}

	public void setLanguageCode(Integer pLanguageCode) {
		_languageCode = pLanguageCode;
	}

	public String getLanguageLabel() {
		return _languageLabel;
	}

	public void setLanguageLabel(String pLanguageLabel) {
		_languageLabel = pLanguageLabel;
	}

	public String getLanguageCodeISO() {
		return _languageCodeISO;
	}

	public void setLanguageCodeISO(String pLanguageCodeISO) {
		_languageCodeISO = pLanguageCodeISO;
	}

	public String getCountryCodeISO() {
		return _countryCodeISO;
	}

	public void setCountryCodeISO(String pCountryCodeISO) {
		_countryCodeISO = pCountryCodeISO;
	}

	public Locale getLocale() {
		return _locale;
	}

	public void setLocale(Locale pLocale) {
		_locale = pLocale;
	}

}
