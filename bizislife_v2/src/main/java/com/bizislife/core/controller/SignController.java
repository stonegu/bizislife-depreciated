package com.bizislife.core.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bizislife.core.controller.component.ApiResponse;
import com.bizislife.core.controller.component.ConstantKey;
import com.bizislife.core.controller.component.SignupForm;
import com.bizislife.core.event.OnRegistrationCompleteEvent;
import com.bizislife.core.hibernate.pojo.Account;
import com.bizislife.core.service.AccountService;
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
	private ApplicationEventPublisher eventPublisher;
	
	@Autowired
	private MessageFromPropertiesService messageService;
	
    @RequestMapping(value="/signup", method=RequestMethod.POST, consumes = "application/json")
    public @ResponseBody ApiResponse signup(HttpSession session,
            @Valid @RequestBody final SignupForm signupForm, BindingResult result
            ) {
    	
    	logger.info("--- signup - " + signupForm);
    	
    	ApiResponse apires = new ApiResponse();
    	if (!result.hasErrors()) {
    		
    		User signupUser = accountService.signup(signupForm);
    		
    		if (signupUser!=null) {
    			// TODO: add to session
//    			session.setAttribute(ConstantKey.SessionAttributeKey.CONTEXT, signupUser);
    			apires.setResponse1(signupUser);
        		apires.setSuccess(true);
        		
        		logger.info("--- signup - signupUser: " + signupUser);
    		} else {
        		apires.setSuccess(false);
        		apires.setResponse1(messageService.getMessageByLocale("message.error.user.signup", null, Locale.US));
    		}
    	} else {
    		apires.setSuccess(false);
    		List<String> errors = new ArrayList<>();
    		for (ObjectError oe : result.getAllErrors()) {
    			errors.add(messageService.getMessageByLocale(oe.getDefaultMessage(), null, Locale.US));
    		}
    		apires.setResponse1(errors);
    		logger.error("--- signup - " + errors.toString());
    	}
    	
    	// test publish event:
    	Account account = new Account();
    	account.setFirstname("StoneGu");
    	eventPublisher.publishEvent(new OnRegistrationCompleteEvent(account, null, null));
    	
    	return apires;
    	
    }


}
