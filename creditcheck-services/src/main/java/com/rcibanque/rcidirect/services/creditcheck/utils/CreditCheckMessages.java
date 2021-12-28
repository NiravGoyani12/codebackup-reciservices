package com.rcibanque.rcidirect.services.creditcheck.utils;

import com.rcibanque.rcidirect.services.core.utils.MessageBundle;

public class CreditCheckMessages extends MessageBundle {

	private static final MessageBundle INSTANCE = new CreditCheckMessages();

	public static final MessageBundle getInstance() {

		return INSTANCE;
	}

	private CreditCheckMessages() {
		super("messages-creditcheck");
	}

}
