package com.rcibanque.rcidirect.clients.crif.utils;

import com.rcibanque.rcidirect.services.core.utils.MessageBundle;

public class CrifMessages extends MessageBundle {

	private static final MessageBundle INSTANCE = new CrifMessages();

	public static final MessageBundle getInstance() {

		return INSTANCE;
	}

	private CrifMessages() {
		super("messages-crif");
	}

}
