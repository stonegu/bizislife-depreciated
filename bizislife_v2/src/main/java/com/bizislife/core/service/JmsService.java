package com.bizislife.core.service;

import javax.jms.JMSException;

import org.springframework.security.core.userdetails.User;

import com.bizislife.core.service.component.BizUser;

public interface JmsService {
	
	/**
	 * create token, and send email out
	 * @throws JMSException 
	 */
	public void signUpEmailSend(User user) throws JMSException;

}
