package com.bizislife.core.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bizislife.core.controller.component.SignupForm;
import com.bizislife.core.hibernate.dao.AccountJpaRepository;
import com.bizislife.core.hibernate.dao.EContactJpaRepository;
import com.bizislife.core.hibernate.pojo.Account;
import com.bizislife.core.hibernate.pojo.EContact;
import com.bizislife.core.hibernate.pojo.EContact.ContactType;
import com.bizislife.core.service.component.BizUser;

@Service
public class AccountServiceImpl implements AccountService{
	
	@Autowired
	AccountJpaRepository accountJpaRepository;
	
	@Autowired
	EContactJpaRepository eContactJpaRepository;

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
	public User signup(SignupForm signupForm) {
		User bizUser = null;
		
		if (signupForm!=null) {
			Account accountRegist = new Account();
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
				bizUser = new BizUser(accountRegist.getLoginname(), null, null, null, null);
			}
			
		}
		
		return bizUser;
	}

}
