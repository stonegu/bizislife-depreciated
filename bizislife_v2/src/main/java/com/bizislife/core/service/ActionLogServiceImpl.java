package com.bizislife.core.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bizislife.core.hibernate.dao.AccountJpaRepository;
import com.bizislife.core.hibernate.dao.ActionlogJpaRepository;
import com.bizislife.core.hibernate.pojo.Account;
import com.bizislife.core.hibernate.pojo.ActionLog;
import com.bizislife.core.hibernate.pojo.BizActionLog;
import com.bizislife.core.hibernate.pojo.SigninLog;
import com.bizislife.core.hibernate.pojo.SignupLog;
import com.bizislife.util.WebUtil;

@Service
public class ActionLogServiceImpl implements ActionLogService {
	
	@Autowired
	private AccountJpaRepository accountJpaRepository;
	
	@Autowired
	private ActionlogJpaRepository actionlogJpaRepository;

	@Transactional
	private ActionLog addActionLog(String userName, ActionLog actionLog) {
		List<Account> accounts = accountJpaRepository.findByLoginname(userName);
		
		if (actionLog!=null && accounts!=null && accounts.size()>0) {
			Class theClass = actionLog.getClass();
			// signinLog
			if (theClass.equals(SigninLog.class)) {
				SigninLog signinLog = (SigninLog)actionLog;
				if (signinLog.getId()!=null) { // could be signout
					BizActionLog existingLog = actionlogJpaRepository.findOne(signinLog.getId());
					if (signinLog.getLogoutType()!=null) {
						((SigninLog)existingLog).setDateLogout(signinLog.getDateLogout());
						((SigninLog)existingLog).setLogoutType(signinLog.getLogoutType());
						actionlogJpaRepository.save(existingLog);
						actionLog = existingLog;
					}
				} else {
					signinLog.setAccount(accounts.get(0));
					actionlogJpaRepository.save(signinLog);
				}
				
			} else
			// signupLog
			if (theClass.equals(SignupLog.class)) {
				actionLog.setAccount(accounts.get(0));
				actionlogJpaRepository.save((SignupLog)actionLog);
			}
		}
		return actionLog;
	}

	@Override
	public void createSignupLog(String username, String ip) {
		SignupLog signupLog = new SignupLog();
		signupLog.setIp(ip);
		addActionLog(username, signupLog);
		
	}

}
