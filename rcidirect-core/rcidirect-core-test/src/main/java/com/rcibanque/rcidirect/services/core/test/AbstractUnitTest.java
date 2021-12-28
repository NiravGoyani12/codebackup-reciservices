package com.rcibanque.rcidirect.services.core.test;

import static org.mockito.BDDMockito.given;

import java.util.HashMap;
import java.util.Locale;

import org.mockito.Mock;

import com.rcibanque.rcidirect.services.core.domain.IContext;
import com.rcibanque.rcidirect.services.core.response.APIMessages;
import com.rcibanque.rcidirect.services.core.response.IMessages;
import com.rcibanque.rcidirect.services.core.user.IUser;
import com.rcibanque.rcidirect.services.core.user.IUserPreferences;

public abstract class AbstractUnitTest {

	@Mock
	protected IContext _context;

	private IMessages _messages;

	@Mock
	private IUser _user;

	@Mock
	private IUserPreferences _userPreferences;


	protected final void givenContext() {

		_messages = APIMessages.getInstance(Locale.UK);

		given(_context.getMessages()).willReturn(_messages);
		given(_context.getUser()).willReturn(_user);
		given(_context.getLocale()).willReturn(Locale.UK);

		given(_user.getPreferences()).willReturn(_userPreferences);
		given(_user.getExtraAttributes()).willReturn(new HashMap<>());
	}

}