package com.rcibanque.rcidirect.services.core.messaging.jms;

import javax.jms.JMSException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsOperations;
import org.springframework.stereotype.Component;

/**
 * Common component used for sending any kind of message to the asynchronous queue.
 *
 * @author mkowalski
 *
 */
@Component
public class QueueSender {

	@Autowired
	private JmsOperations _jmsTemplate;

	@Value("${jms.default.priority}")
	private int _defaultPriority;

	/**
	 * Send the message with a certain priority to the
	 * default asynchronous queue.
	 *
	 * @param priority
	 * @throws JMSException
	 */
	public void sendToQueue(String destination, Object message, int priority) throws JMSException{
		_jmsTemplate.convertAndSend(destination, message, new PriorityAwareMessagePostProcessor(priority));
	}

	/**
	 * Send the message with a default priority to the
	 * default asynchronous queue.
	 *
	 * @throws JMSException
	 */
	public void sendToQueue(String destination, Object message) throws JMSException{
		_jmsTemplate.convertAndSend(destination, message, new PriorityAwareMessagePostProcessor(_defaultPriority));
	}
}
