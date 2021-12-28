package com.rcibanque.rcidirect.services.core.response;

import java.io.Serializable;
import java.util.List;

import com.rcibanque.rcidirect.services.core.utils.MessageBundle;

/**
 * Context object that holds messages
 */
public interface IMessages extends Serializable {


	/**
	 * Return messages
	 *
	 * @param pUserMessagesOnly Filter on user message or also include technical messages
	 * @return Messages (in a new list object)
	 */
	List<APIMessage> get(boolean pUserMessagesOnly);


	/**
	 * Adds a {@link APIMessageStatus#LOG} message
	 *
	 * @param pMessage Message
	 * @param pParams Message parameters
	 * @return Instance
	 */
	IMessage log(String pMessage, Object... pParams);

	/**
	 * Adds a {@link APIMessageStatus#LOG} message containing the provided exception's stack trace
	 *
	 * @param pException Exception
	 * @return Instance
	 */
	IMessage log(Exception pException);

	/**
	 * Adds a {@link APIMessageStatus#ERROR} message
	 *
	 * @param pBundle Message bundle
	 * @param pKey Message key
	 * @param pParams Message parameters
	 * @return Instance
	 */
	IMessage error(MessageBundle pBundle, String pKey, Object... pParams);

	/**
	 * Adds a {@link APIMessageStatus#WARNING} message
	 *
	 * @param pBundle Message bundle
	 * @param pKey Message key
	 * @param pParams Message parameters
	 * @return Instance
	 */
	IMessage warning(MessageBundle pBundle, String pKey, Object... pParams);

	/**
	 * Adds a {@link APIMessageStatus#INFO} message
	 *
	 * @param pBundle Message bundle
	 * @param pKey Message key
	 * @param pParams Message parameters
	 * @return Instance
	 */
	IMessage info(MessageBundle pBundle, String pKey, Object... pParams);

	/**
	 * Adds a message
	 *
	 * @param pStatus Message status
	 * @param pBundle Message bundle
	 * @param pKey Message key
	 * @param pParams Message parameters
	 * @return Instance
	 * @see {@link #info(MessageBundle, String, Object...)}
	 * @see {@link #warning(MessageBundle, String, Object...)}
	 * @see {@link #error(MessageBundle, String, Object...)}
	 */
	IMessage message(APIMessageStatus pStatus, MessageBundle pBundle, String pKey, Object... pParams);

	/**
	 * Adds a message
	 *
	 * @param pStatus Message status
	 * @param pMessage Message
	 * @return Instance
	 * @see {@link #info(MessageBundle, String, Object...)}
	 * @see {@link #warning(MessageBundle, String, Object...)}
	 * @see {@link #error(MessageBundle, String, Object...)}
	 */
	IMessage message(APIMessageStatus pStatus, String pMessage);

	/**
	 * Adds a message, by copying the parameter message and its details
	 *
	 * @param pMessage Message
	 * @return Instance
	 */
	IMessage message(APIMessage pMessage);


	/**
	 * @return True if there are {@link APIMessageStatus#ERROR} messages
	 */
	boolean hasError();

	/**
	 * @return True if there are {@link APIMessageStatus#WARNING} messages
	 */
	boolean hasWarning();

	/**
	 * @return True if there are {@link APIMessageStatus#INFO} messages
	 */
	boolean hasInfo();


}