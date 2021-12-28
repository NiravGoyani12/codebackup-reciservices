package com.rcibanque.rcidirect.services.core.user.utils;

import java.io.Serializable;
import java.util.Locale;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;

public class CoreUserUtils {

	private CoreUserUtils() {}


	@SuppressWarnings("unused")
	@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
	private static class Response implements Serializable {
		private static final long serialVersionUID = 1L;
		private String _title;
		private String _text;
	}


	/**
	 * @param pLocale Locale
	 * @return Bad credentials response
	 */
	public static Serializable getBadCredentialsResponse(Locale pLocale) {

		return getResponse(pLocale, "user.bad.credentials.exception.title", "user.bad.credentials.exception.text");
	}

	/**
	 * @param pLocale Locale
	 * @return Invalid token response
	 */
	public static Serializable getInvalidTokenResponse(Locale pLocale) {

		return getResponse(pLocale, "user.invalid.token.exception.title", "user.invalid.token.exception.text");
	}

	private static Serializable getResponse(Locale pLocale, String pTitleMessageKey, String pTextMessageKey) {

		Response res = new Response();

		res._title = CoreUserMessages.getInstance().getMessage(pLocale, pTitleMessageKey);
		res._text = CoreUserMessages.getInstance().getMessage(pLocale, pTextMessageKey);

		return res;
	}


}
