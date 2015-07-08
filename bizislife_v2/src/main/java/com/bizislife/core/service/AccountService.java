package com.bizislife.core.service;

import org.springframework.security.core.userdetails.User;

import com.bizislife.core.hibernate.pojo.EContact.ContactType;

public interface AccountService {
	
	public User getLoginAccount();
	
	public void removeEContact(String accountUid, ContactType contactType, String contactValue);

}
