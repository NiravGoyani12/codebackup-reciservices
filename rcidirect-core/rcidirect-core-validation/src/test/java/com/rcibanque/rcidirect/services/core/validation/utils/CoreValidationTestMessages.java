package com.rcibanque.rcidirect.services.core.validation.utils;

import com.rcibanque.rcidirect.services.core.utils.MessageBundle;

public class CoreValidationTestMessages extends MessageBundle {


	private static final MessageBundle INSTANCE = new CoreValidationTestMessages();

	public static final MessageBundle getInstance() {

		return INSTANCE;
	}

	private CoreValidationTestMessages() {
		super("messages-core-validation-test");
	}

}

