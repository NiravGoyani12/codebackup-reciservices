package com.rcibanque.rcidirect.services.core.utils;

public class CoreMessages extends MessageBundle {


	private static final MessageBundle INSTANCE = new CoreMessages();

	public static final MessageBundle getInstance() {

		return INSTANCE;
	}

	private CoreMessages() {
		super("messages-core");
	}

}
