package com.bizislife.core.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.jms.JMSException;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.activemq.command.ActiveMQMapMessage;
import org.apache.activemq.command.ActiveMQQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bizislife.core.controller.component.SignupForm;
import com.bizislife.core.exception.BizisLifeBaseException;
import com.bizislife.core.service.AccountService;
import com.bizislife.core.service.JmsMessageSender;
import com.bizislife.core.service.JmsService;
import com.bizislife.core.service.MessageFromPropertiesService;
import com.bizislife.util.annotation.PublicPage;

@PublicPage
@Controller
@RequestMapping(value = "/sign")
public class SignController {
	private static final Logger logger = LoggerFactory.getLogger(SignController.class);
	
	@Autowired
	private AccountService accountService;
	
    @Autowired
    private ActiveMQQueue emailQueue;

	@Autowired
	private ApplicationEventPublisher eventPublisher;
	
	@Autowired
	private MessageFromPropertiesService messageService;

    @Autowired
    private JmsService jmsService;
    
    @RequestMapping(value="/signup", method=RequestMethod.POST, consumes = "application/json")
    public @ResponseBody ResponseEntity<User> signup(HttpSession session,
            @Valid @RequestBody final SignupForm signupForm, BindingResult result
            ) throws BizisLifeBaseException, JMSException {
    	
    	logger.info("--- signup - " + signupForm);
    	
    	if (!result.hasErrors()) {
    		
    		User signupUser = accountService.signup(signupForm);
    		
    		if (signupUser!=null) {
    			// TODO: add to session
//    			session.setAttribute(ConstantKey.SessionAttributeKey.CONTEXT, signupUser);
    			
    			
    			jmsService.signUpEmailSend(signupUser);
    			
    			
    			
    			return new ResponseEntity<User>(signupUser, HttpStatus.OK);
    		} else {
    			throw new BizisLifeBaseException(BizisLifeBaseException.SIGNUP_USER_CREATION_ERROR, 
    					messageService.getMessageByLocale("message.error.signup.nouser.created", null, Locale.ENGLISH));
    		}
    		
    	} else {
    		List<String> errors = new ArrayList<>();
    		for (ObjectError oe : result.getAllErrors()) {
    			errors.add(messageService.getMessageByLocale(oe.getDefaultMessage(), null, Locale.ENGLISH));
    		}
    		throw new BizisLifeBaseException(BizisLifeBaseException.SIGNUP_FORM_ERROR, 
    				errors);
    	}
    	
    	// test publish event:
//    	Account account = new Account();
//    	account.setFirstname("StoneGu");
//    	eventPublisher.publishEvent(new OnRegistrationCompleteEvent(account, null, null));
    	
    }


}
