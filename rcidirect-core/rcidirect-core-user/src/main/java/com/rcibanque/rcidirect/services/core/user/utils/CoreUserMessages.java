package com.rcibanque.rcidirect.services.core.user.utils;

import com.rcibanque.rcidirect.services.core.utils.MessageBundle;

public class CoreUserMessages extends MessageBundle {


	private static final MessageBundle INSTANCE = new CoreUserMessages();

	public static final MessageBundle getInstance() {

		return INSTANCE;
	}

	private CoreUserMessages() {
		super("messages-core-users");
	}

}
