package com.rcibanque.rcidirect.services.core.property.utils;

import com.rcibanque.rcidirect.services.core.utils.MessageBundle;

public class CorePropertyMessages extends MessageBundle {


	private static final MessageBundle INSTANCE = new CorePropertyMessages();

	public static final MessageBundle getInstance() {

		return INSTANCE;
	}

	private CorePropertyMessages() {
		super("messages-core-property");
	}

}

