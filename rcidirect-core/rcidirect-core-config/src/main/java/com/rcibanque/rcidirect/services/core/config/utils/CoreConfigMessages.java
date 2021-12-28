package com.rcibanque.rcidirect.services.core.config.utils;

import com.rcibanque.rcidirect.services.core.utils.MessageBundle;

public class CoreConfigMessages extends MessageBundle {


	private static final MessageBundle INSTANCE = new CoreConfigMessages();

	public static final MessageBundle getInstance() {

		return INSTANCE;
	}

	private CoreConfigMessages() {
		super("messages-core-config");
	}

}

