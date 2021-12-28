package com.rcibanque.rcidirect.services.core.response;

import java.io.Serializable;
import java.util.UUID;

/**
 * REST service call error
 */
public final class APIError implements Serializable {

	private static final long serialVersionUID = -1L;


	private final UUID _errorTrackingID;

	private final IMessages _messages;


	public APIError(IMessages pMessages) {
		super();
		_errorTrackingID = UUID.randomUUID();
		_messages = pMessages;
	}


	public IMessages getMessages() {
		return _messages;
	}

	public String getErrorTrackingID(){
		return _errorTrackingID.toString();
	}

}
