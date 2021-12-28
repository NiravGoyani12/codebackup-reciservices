package com.rcibanque.rcidirect.services.core.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.rcibanque.rcidirect.services.core.domain.DTO;

public final class UserPreferences extends DTO implements IUserPreferences {

	private static final long serialVersionUID = -5512578296076710885L;


	private transient String _userActorCode;

	@JsonProperty("new_message_email")
	private Boolean _newMessageEmail;

	@JsonProperty("proposal_expiry_email")
	private Boolean _proposalExpiryEmail;

	@JsonProperty("language_code")
	private Integer _languageCode;

	@JsonProperty("locale_language")
	private String _localeLanguage;

	@JsonProperty("locale_country")
	private String _localeCountry;


	public String getUserActorCode() {
		return _userActorCode;
	}

	public void setUserActorCode(String pUserActorCode) {
		_userActorCode = pUserActorCode;
	}


	@Override
	public Boolean getNewMessageEmail() {
		return _newMessageEmail;
	}

	@Override
	public void setNewMessageEmail(Boolean pNewMessageEmail) {
		_newMessageEmail = pNewMessageEmail;
	}


	@Override
	public Boolean getProposalExpiryEmail() {
		return _proposalExpiryEmail;
	}

	@Override
	public void setProposalExpiryEmail(Boolean pProposalExpiryEmail) {
		_proposalExpiryEmail = pProposalExpiryEmail;
	}


	@Override
	public Integer getLanguageCode() {
		return _languageCode;
	}

	@Override
	public void setLanguageCode(Integer pLanguageCode) {
		_languageCode = pLanguageCode;
	}


	@Override
	public String getLocaleLanguage() {
		return _localeLanguage;
	}

	@Override
	public void setLocaleLanguage(String pLocaleLanguage) {
		_localeLanguage = pLocaleLanguage;
	}

	@Override
	public String getLocaleCountry() {
		return _localeCountry;
	}

	@Override
	public void setLocaleCountry(String pLocaleCountry) {
		_localeCountry = pLocaleCountry;
	}


	@Override
	public void apply(IUserPreferences pPreferences) {

		// Preferences are mandatory (but might not have been filled), we do not want to erase any data

		if(pPreferences.getLanguageCode() != null) {
			setLanguageCode(pPreferences.getLanguageCode());
		}
		if(pPreferences.getLocaleLanguage() != null) {
			setLocaleLanguage(pPreferences.getLocaleLanguage());
		}
		if(pPreferences.getLocaleCountry() != null) {
			setLocaleCountry(pPreferences.getLocaleCountry());
		}
		if(pPreferences.getNewMessageEmail() != null) {
			setNewMessageEmail(pPreferences.getNewMessageEmail());
		}
		if(pPreferences.getProposalExpiryEmail() != null) {
			setProposalExpiryEmail(pPreferences.getProposalExpiryEmail());
		}
	}


}
