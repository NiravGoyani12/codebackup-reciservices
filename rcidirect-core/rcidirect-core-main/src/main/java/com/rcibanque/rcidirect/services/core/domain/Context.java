package com.rcibanque.rcidirect.services.core.domain;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import com.rcibanque.rcidirect.services.core.response.APIMessages;
import com.rcibanque.rcidirect.services.core.response.IMessages;
import com.rcibanque.rcidirect.services.core.user.IUser;

public final class Context implements IContext, Serializable {

	private static final long serialVersionUID = -2123918753358691199L;


	private final Locale _locale;

	private final IUser _user;

	private final IMessages _messages;

	private final Map<String, Object> _data;


	Context(IUser pUser) {
		super();

		_user = pUser;

		if(_user != null
				&& _user.getPreferences() != null
				&& _user.getPreferences().getLocaleLanguage() != null
				&& _user.getPreferences().getLocaleCountry() != null) {

			_locale = new Locale(
					_user.getPreferences().getLocaleLanguage(),
					_user.getPreferences().getLocaleCountry());
		}
		else {
			_locale = Locale.UK; // Default
		}

		_messages = APIMessages.getInstance(_locale);

		_data = new HashMap<>();
	}


	@Override
	public IUser getUser() {
		return _user;
	}

	@Override
	public Integer getLanguageCode() {
		Integer res;
		if(_user != null && _user.getPreferences() != null) {
			res = _user.getPreferences().getLanguageCode();
		}
		else {
			res = Integer.valueOf(2); // Default
		}
		return res;
	}

	@Override
	public Locale getLocale() {
		return _locale;
	}

	@Override
	public IMessages getMessages() {
		return _messages;
	}


	/**
	 * <b>DO NOT USE UNLESS ABSOLUTELY NECESSARY</b>
	 *
	 * @return Data map
	 */
	public Map<String, Object> getData() {
		return _data;
	}

}
