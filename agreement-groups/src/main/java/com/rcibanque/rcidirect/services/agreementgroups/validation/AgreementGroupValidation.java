package com.rcibanque.rcidirect.services.agreementgroups.validation;

import com.rcibanque.rcidirect.services.agreementgroups.util.AgreementGroupMessages;
import com.rcibanque.rcidirect.services.core.response.APIMessageStatus;
import com.rcibanque.rcidirect.services.core.utils.MessageBundle;
import com.rcibanque.rcidirect.services.core.validation.IValidation;

public enum AgreementGroupValidation implements IValidation {

	ERROR_AGREEMENT_GROUP(APIMessageStatus.ERROR, "validation.error.agreement.group"),
	;


	private final APIMessageStatus _status;

	private final String _messageKey;


	private AgreementGroupValidation(APIMessageStatus pStatus, String pMessageKey) {
		_status = pStatus;
		_messageKey = pMessageKey;
	}


	@Override
	public APIMessageStatus getStatus() {
		return _status;
	}

	@Override
	public String getMessageKey() {
		return _messageKey;
	}

	@Override
	public MessageBundle getMessageBundle() {
		return AgreementGroupMessages.getInstance();
	}

}
