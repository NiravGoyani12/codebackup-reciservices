package com.rcibanque.rcidirect.services.core.system.utils;

import com.rcibanque.rcidirect.services.core.utils.MessageBundle;

public class CoreSystemMessages extends MessageBundle {


	private static final MessageBundle INSTANCE = new CoreSystemMessages();

	public static final MessageBundle getInstance() {

		return INSTANCE;
	}

	private CoreSystemMessages() {
		super("messages-core-system");
	}

}
