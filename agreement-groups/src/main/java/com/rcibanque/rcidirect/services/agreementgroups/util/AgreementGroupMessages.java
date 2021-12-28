package com.rcibanque.rcidirect.services.agreementgroups.util;

import com.rcibanque.rcidirect.services.core.utils.MessageBundle;

public class AgreementGroupMessages extends MessageBundle {


	private static final MessageBundle INSTANCE = new AgreementGroupMessages();

	public static final MessageBundle getInstance() {

		return INSTANCE;
	}

	private AgreementGroupMessages() {
		super("messages-agreementgroups");
	}

}
