package com.rcibanque.rcidirect.services.core.user;

public interface IUserPreferences {


	Boolean getNewMessageEmail();

	void setNewMessageEmail(Boolean pNewMessageEmail);


	Boolean getProposalExpiryEmail();

	void setProposalExpiryEmail(Boolean pProposalExpiryEmail);


	Integer getLanguageCode();

	void setLanguageCode(Integer pLanguageCode);


	String getLocaleLanguage();

	void setLocaleLanguage(String pLocaleLanguage);

	String getLocaleCountry();

	void setLocaleCountry(String pLocaleCountry);


	void apply(IUserPreferences pPreferences);
}
