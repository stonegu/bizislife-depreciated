package com.bizislife.core.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bizislife.core.hibernate.dao.AccountJpaRepository;
import com.bizislife.core.hibernate.pojo.Account;
import com.bizislife.core.hibernate.pojo.EContact.ContactType;

@Service
public class AccountServiceImpl implements AccountService{
	
	@Autowired
	AccountJpaRepository accountJpaRepository;

	@Transactional(readOnly=true)
	public User getLoginAccount() {
		return null;
	}

	@Override
	@Transactional
	public void removeEContact(String accountUid, ContactType contactType, String contactValue) {
		if (accountUid!=null && contactType!=null && contactValue!=null) {
			List<Account> accounts = accountJpaRepository.findByUid(accountUid);
		}
	}

}
