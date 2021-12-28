package com.rcibanque.rcidirect.services.core.messaging.jms;

import javax.jms.JMSException;
import javax.jms.Message;

import org.springframework.jms.core.MessagePostProcessor;

/**
 * JMS Post processor with message priority awareness.
 * 
 * @author mkowalski
 *
 */
public class PriorityAwareMessagePostProcessor implements MessagePostProcessor{

	private Integer priority;
	
	public PriorityAwareMessagePostProcessor(Integer priority) {
		this.priority = priority;
	}
	
	@Override
	public Message postProcessMessage(Message message) throws JMSException {
		message.setJMSPriority(priority); 
		
		return message;
	}

}
