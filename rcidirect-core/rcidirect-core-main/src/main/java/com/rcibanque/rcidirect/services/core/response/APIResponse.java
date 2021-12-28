package com.rcibanque.rcidirect.services.core.response;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * REST service call response body
 * <ul>
 * <li>Data</li>
 * <li>Messages</li>
 * <li>Tracking ID</li>
 * </ul>
 */
@JsonPropertyOrder({ "data", "messages", "error_tracking_id" })
@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
public final class APIResponse<T> {


	private T _data;

	private List<APIMessage> _messages;

	private String _errorTrackingID;


	public APIResponse() {
		_messages = new ArrayList<>();
	}

	public APIResponse(T pData, IMessages pMessages) {
		super();
		_data = pData;
		_messages = pMessages == null ? new ArrayList<>() : pMessages.get(true);
	}

	public APIResponse(APIError pError) {
		super();
		_messages = pError.getMessages().get(true);
		_errorTrackingID = pError.getErrorTrackingID();
	}


	public T getData() {
		return _data;
	}

	public void setData(T pData) {
		_data = pData;
	}

	public List<APIMessage> getMessages() {
		return _messages;
	}

	public void setMessages(List<APIMessage> pMessages) {
		_messages = pMessages;
	}

	public String getErrorTrackingID() {
		return _errorTrackingID;
	}

	public void setErrorTrackingID(String pErrorTrackingID) {
		_errorTrackingID = pErrorTrackingID;
	}

}
