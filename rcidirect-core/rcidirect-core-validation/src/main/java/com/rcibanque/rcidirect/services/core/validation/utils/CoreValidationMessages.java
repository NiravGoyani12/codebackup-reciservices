package com.rcibanque.rcidirect.services.core.validation.utils;

import com.rcibanque.rcidirect.services.core.utils.MessageBundle;

public class CoreValidationMessages extends MessageBundle {


	private static final MessageBundle INSTANCE = new CoreValidationMessages();

	public static final MessageBundle getInstance() {

		return INSTANCE;
	}

	private CoreValidationMessages() {
		super("messages-core-validation");
	}

}

