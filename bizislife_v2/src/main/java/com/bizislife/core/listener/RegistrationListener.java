package com.bizislife.core.listener;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.bizislife.core.event.OnRegistrationCompleteEvent;
import com.bizislife.core.hibernate.pojo.Account;

@Component
public class RegistrationListener implements ApplicationListener<OnRegistrationCompleteEvent>{

	@Override
	public void onApplicationEvent(OnRegistrationCompleteEvent event) {
		
		Account account = event.getAccount();
		System.out.println("..................... OnRegistrationCompleteEvent: ...............");
		System.out.println(account);
		
	}

}
