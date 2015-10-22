package com.bizislife.core.appservice;

import javax.servlet.http.HttpServletRequest;

import com.bizislife.core.hibernate.pojo.ActionLog;
import com.bizislife.core.listener.EmailMessageListener.EmailType;

public interface ActionLogService {
	
	public void createSignupLog(String username, String ip);
	public void createEmailLog(String username, EmailType emailType, String sendto, String cc, String template);
}
