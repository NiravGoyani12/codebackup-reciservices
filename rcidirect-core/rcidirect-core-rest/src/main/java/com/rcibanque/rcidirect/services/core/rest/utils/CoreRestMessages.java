package com.rcibanque.rcidirect.services.core.rest.utils;

import com.rcibanque.rcidirect.services.core.utils.MessageBundle;

public class CoreRestMessages extends MessageBundle {


	private static final MessageBundle INSTANCE = new CoreRestMessages();

	public static final MessageBundle getInstance() {

		return INSTANCE;
	}

	private CoreRestMessages() {
		super("messages-core-rest");
	}

}

