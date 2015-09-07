package com.bizislife.core.service;

import org.springframework.security.core.userdetails.User;

import com.bizislife.core.controller.component.SignupForm;
import com.bizislife.core.exception.BizisLifeBaseException;
import com.bizislife.core.exception.NoUserCreate;
import com.bizislife.core.hibernate.pojo.EContact.ContactType;

public interface AccountService {
	
	public User getLoginAccount();
	
	public User signup(SignupForm signupForm) throws BizisLifeBaseException;
	
	public void removeEContact(String accountUid, ContactType contactType, String contactValue);

}
