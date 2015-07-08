package com.bizislife.core.event;

import java.util.Locale;

import org.springframework.context.ApplicationEvent;

import com.bizislife.core.hibernate.pojo.Account;

public class OnRegistrationCompleteEvent extends ApplicationEvent{

    private final String appUrl;
    private final Locale locale;
    private final Account account;	
	
	public OnRegistrationCompleteEvent(Account account, Locale locale, String appUrl) {
		super(account);
		this.account = account;
		this.locale = locale;
		this.appUrl = appUrl;
	}

	public String getAppUrl() {
		return appUrl;
	}

	public Locale getLocale() {
		return locale;
	}

	public Account getAccount() {
		return account;
	}

}
