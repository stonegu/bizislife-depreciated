package com.bizislife.core.service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bizislife.core.appservice.ActionLogService;
import com.bizislife.core.controller.component.SignupForm;
import com.bizislife.core.exception.BizisLifeBaseException;
import com.bizislife.core.exception.NoUserCreate;
import com.bizislife.core.hibernate.dao.AccountJpaRepository;
import com.bizislife.core.hibernate.dao.EContactJpaRepository;
import com.bizislife.core.hibernate.dao.RoleJpaRepository;
import com.bizislife.core.hibernate.pojo.Account;
import com.bizislife.core.hibernate.pojo.EContact;
import com.bizislife.core.hibernate.pojo.EContact.ContactType;
import com.bizislife.core.hibernate.pojo.Role;
import com.bizislife.core.service.component.BizUser;
import com.bizislife.util.WebUtil;

@Service
public class AccountServiceImpl implements AccountService{
	
	@Autowired
	AccountJpaRepository accountJpaRepository;
	
	@Autowired
	ActionLogService actionLogService;
	
	@Autowired
	EContactJpaRepository eContactJpaRepository;
	
	@Autowired
	private MessageFromPropertiesService messageService;
	
	@Autowired
	private RoleJpaRepository roleJpaRepository;
	
	@Autowired
	private UserDetailService userDetailService;


	@Transactional(readOnly=true)
	public User getLoginAccount() {
		return null;
	}

	@Override
	@Transactional
	public void removeEContact(String accountUid, ContactType contactType, String contactValue) {
		if (accountUid!=null && contactType!=null && contactValue!=null) {
			List<Account> accounts = accountJpaRepository.findByUid(accountUid);
			if (accounts!=null && accounts.size()>0) {
				Account account = accounts.get(0);
				Long delId = account.detachEContactFromAll(contactType, contactValue);
				accountJpaRepository.save(account);
				eContactJpaRepository.delete(delId);
			}
		}
	}

	@Override
	@Transactional
	public User signup(SignupForm signupForm, HttpServletRequest req) throws BizisLifeBaseException {
		User bizUser = null;
		
		if (signupForm!=null) {
			
			// check username existence
			List<Account> accountsWithSameLoginName = accountJpaRepository.findByLoginname(signupForm.getUsername());
			
			if (accountsWithSameLoginName==null || accountsWithSameLoginName.size()==0) {
				// get 'general' role
				List<Role> generalRoles = roleJpaRepository.findByNameAndOrgUid(Role.PredefinedRoles.GENERAL.name(), null);
				if (generalRoles==null || generalRoles.size()==0)
					throw new BizisLifeBaseException(BizisLifeBaseException.NO_GENERAL_ROLE, messageService.getMessageByLocale("message.error.role.general.nofound", null, null));
				
				Account accountRegist = new Account();
				accountRegist.addRole(generalRoles.get(0));
				
				accountRegist.setLoginname(signupForm.getUsername());
				BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
				String hashedPassword = passwordEncoder.encode(signupForm.getPwd());
				accountRegist.setPwd(hashedPassword);
				
				EContact eContact = new EContact();
				eContact.setAccount(accountRegist);
				eContact.setContactType(ContactType.Email);
				eContact.setContactValue(signupForm.getEmail());
				
				accountRegist.addEContact(eContact);
				
				accountRegist = accountJpaRepository.save(accountRegist);
				
				if (accountRegist!=null && accountRegist.getId()!=null) {
					bizUser = new BizUser(accountRegist.getLoginname(), hashedPassword, userDetailService.getGrantedAuthorities(generalRoles.get(0)), null, null);
				}
			} else {
				throw new BizisLifeBaseException(BizisLifeBaseException.SIGNUP_USERNAME_EXIST, messageService.getMessageByLocale("message.error.signup.username.exist", new String[]{signupForm.getUsername()}, null));
			}
		}
		
		if (bizUser!=null) {
			//create action log!
			actionLogService.createSignupLog(bizUser.getUsername(), WebUtil.getClientIpAddress(req));
			return bizUser;
		}
		else throw new BizisLifeBaseException(BizisLifeBaseException.SIGNUP_USER_CREATION_ERROR, 
				messageService.getMessageByLocale("message.error.signup.nouser.created", null, null));
	}

}
