package com.rcibanque.rcidirect.services.core.response;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;

import com.rcibanque.rcidirect.services.core.utils.IteratorUtils;
import com.rcibanque.rcidirect.services.core.utils.MessageBundle;

public final class APIMessages implements IMessages, Iterable<APIMessage> {

	private static final long serialVersionUID = -1L;


	public static APIMessages getInstance(Locale pLocale) {
		return new APIMessages(pLocale);
	}

	private final List<APIMessage> _messages;

	private final Locale _locale;


	private APIMessages(Locale pLocale) {
		_messages = new ArrayList<>();
		_locale = pLocale;
	}


	@Override
	public List<APIMessage> get(boolean pUserMessagesOnly) {
		List<APIMessage> res = new ArrayList<>();
		for(APIMessage message : this) {
			if(! pUserMessagesOnly || ! message.getStatus().equals(APIMessageStatus.LOG)) {
				res.add(message);
			}
		}
		return res;
	}


	@Override
	public IMessage log(Exception pException) {

		return addMessage(APIMessageStatus.LOG, null, ExceptionUtils.getStackTrace(pException));
	}

	@Override
	public IMessage log(String pMessage, Object... pParams) {

		String message = MessageBundle.getMessage(pMessage, pParams);

		return addMessage(APIMessageStatus.LOG, null, message);
	}

	@Override
	public IMessage error(MessageBundle pBundle, String pKey, Object... pParams) {

		return message(APIMessageStatus.ERROR, pBundle, pKey, pParams);
	}

	@Override
	public IMessage warning(MessageBundle pBundle, String pKey, Object... pParams) {

		return message(APIMessageStatus.WARNING, pBundle, pKey, pParams);
	}

	@Override
	public IMessage info(MessageBundle pBundle, String pKey, Object... pParams) {

		return message(APIMessageStatus.INFO, pBundle, pKey, pParams);
	}


	@Override
	public boolean hasError() {
		return _messages.stream().
				anyMatch(message -> APIMessageStatus.ERROR.equals(message.getStatus()));
	}

	@Override
	public boolean hasWarning() {
		return _messages.stream().
				anyMatch(message -> APIMessageStatus.WARNING.equals(message.getStatus()));
	}

	@Override
	public boolean hasInfo() {
		return _messages.stream().
				anyMatch(message -> APIMessageStatus.INFO.equals(message.getStatus()));
	}

	@Override
	public Iterator<APIMessage> iterator() {
		return _messages.iterator();
	}

	@Override
	public IMessage message(APIMessageStatus pStatus, MessageBundle pBundle, String pKey, Object... pParams) {

		String message = pBundle.getMessage(_locale, pKey, pParams);

		return addMessage(pStatus, pKey, message);
	}

	@Override
	public IMessage message(APIMessageStatus pStatus, String pMessage) {

		return addMessage(pStatus, null, pMessage);
	}

	@Override
	public IMessage message(APIMessage pMessage) {

		APIMessage res = addMessage(pMessage.getStatus(), pMessage.getMessageCode(), pMessage.getDescription());

		for(APIMessageDetail detail : IteratorUtils.nullableIterable(pMessage.getDetails())) {
			res.addDetail(detail.getDomain(), detail.getField(), detail.getReferenceKey(), detail.getDescription());
		}

		return res;
	}

	private APIMessage addMessage(APIMessageStatus pStatus, String pKey, String pMessage) {

		String messageDescription = StringUtils.trim(pMessage);

		// Only add message if it does not already exist
		// We compare the actual message and not the message key in case there are message parameters
		// Validation might be applied to several records of the same entity, and the message should contain information saying on which record the validation applies
		for(APIMessage message: _messages){
			if(messageDescription.equals(message.getDescription()) && pStatus.equals(message.getStatus())) {
				return message;
			}
		}

		APIMessage message = new APIMessage(pStatus, messageDescription);
		message.setMessageCode(pKey);
		_messages.add(message);
		return message;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for(APIMessage message : this) {
			if(sb.length() > 0) {
				sb.append(StringUtils.LF);
			}
			sb.append(message);
		}
		return sb.toString();
	}
}