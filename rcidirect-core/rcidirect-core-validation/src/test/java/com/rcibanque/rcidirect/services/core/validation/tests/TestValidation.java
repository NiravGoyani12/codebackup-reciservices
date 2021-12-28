package com.rcibanque.rcidirect.services.core.validation.tests;

import com.rcibanque.rcidirect.services.core.response.APIMessageStatus;
import com.rcibanque.rcidirect.services.core.utils.MessageBundle;
import com.rcibanque.rcidirect.services.core.validation.IValidation;
import com.rcibanque.rcidirect.services.core.validation.utils.CoreValidationTestMessages;

public class TestValidation implements IValidation {

	public static final IValidation TEST_VALIDATION = new TestValidation();

	@Override
	public APIMessageStatus getStatus() {
		return APIMessageStatus.ERROR;
	}
	@Override
	public String getMessageKey() {
		return "validation.error";
	}
	@Override
	public MessageBundle getMessageBundle() {
		return CoreValidationTestMessages.getInstance();
	}
}

