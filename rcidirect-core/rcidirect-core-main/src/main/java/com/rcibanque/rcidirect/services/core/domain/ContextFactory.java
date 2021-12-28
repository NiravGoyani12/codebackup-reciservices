package com.rcibanque.rcidirect.services.core.domain;

import com.rcibanque.rcidirect.services.core.user.IUser;
import com.rcibanque.rcidirect.services.core.user.UserFactory;

/**
 * Creates a context
 */
public class ContextFactory {


	private ContextFactory() {}


	public static IContext getContext() {

		return getContext(UserFactory.getUser());
	}

	public static IContext getContext(IUser pUser) {

		return new Context(pUser);
	}


}
