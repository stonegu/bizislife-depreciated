package com.bizislife.core.service;

import javax.servlet.http.HttpServletRequest;

import com.bizislife.core.hibernate.pojo.ActionLog;

public interface ActionLogService {
	
	public void createSignupLog(String username, String ip);

}
