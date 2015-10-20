package com.bizislife.core.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.jms.JMSException;

import org.apache.activemq.command.ActiveMQMapMessage;
import org.apache.activemq.command.ActiveMQQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bizislife.core.controller.SignController;
import com.bizislife.core.hibernate.dao.AccountJpaRepository;
import com.bizislife.core.hibernate.pojo.Account;
import com.bizislife.core.hibernate.pojo.EContact;
import com.bizislife.core.hibernate.pojo.EContact.ContactType;
import com.bizislife.core.listener.EmailMessageListener;
import com.bizislife.core.service.component.BizUser;

@Service
public class JmsServiceImpl implements JmsService {
	
	private static final Logger logger = LoggerFactory.getLogger(JmsService.class);
	
	@Autowired
	AccountJpaRepository accountJpaRepository;
	
    @Autowired
    private ActiveMQQueue mqQueue;
    
    @Autowired
    private JmsMessageSender jmsMessageSender;
	
	@Autowired
	TokenService tokenService;

	@Override
	@Transactional(readOnly=true)
	public void signUpEmailSend(User user) throws JMSException {
		String token = tokenService.createVerificationTokenforUser(user);
		if (token!=null) {
			List<Account> accounts = accountJpaRepository.findByLoginname(user.getUsername());
			if (accounts!=null && accounts.size()>0) {
				EContact sendToEmail = getSendtoEmail(accounts.get(0).getEmails());
				
				if (sendToEmail!=null) {
//					Map<ContactType, Set<EContact>> contacts = accounts.get(0).getEmails();
					
					// send email out!!
					ActiveMQMapMessage msg = new ActiveMQMapMessage();
					msg.setString("type", EmailMessageListener.EmailType.signup.name());
					msg.setString("token", token);
					msg.setString("sendTo", sendToEmail.getContactValue());
					msg.setString("username", user.getUsername());
//					msg.setString("firstname", ((BizUser)user).getFirstname());
//					msg.setString("lastname", ((BizUser)user).getLastname());
					
					jmsMessageSender.send(mqQueue, msg);
				} else {
					logger.error(user.getUsername() + "'s contact email doese not find.");
				}
				
			} else {
				logger.error(user.getUsername() + " does not find.");
			}
		} else {
			logger.error("No token created for signupEmailSend for user: " + user.getUsername());
		}
	}
	
	/**
	 * @param econtacts
	 * @return email based on logic: get Email first, if not, get HomeEmail the second, if not, get WorkEmail the last
	 */
	private EContact getSendtoEmail(Map<ContactType, Set<EContact>> econtacts) {
		if (econtacts!=null && econtacts.size()>0) {
			if (econtacts.get(ContactType.Email)!=null && econtacts.get(ContactType.Email).size()>0) {
				return econtacts.get(ContactType.Email).iterator().next();
			} else if (econtacts.get(ContactType.HomeEmail)!=null && econtacts.get(ContactType.HomeEmail).size()>0) {
				return econtacts.get(ContactType.HomeEmail).iterator().next();
			} else if (econtacts.get(ContactType.WorkEmail)!=null && econtacts.get(ContactType.WorkEmail).size()>0) {
				return econtacts.get(ContactType.WorkEmail).iterator().next();
			}
		}
		return null;
	}

}
