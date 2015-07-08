package com.bizislife.core.hibernate.dao;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import java.util.List;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.bizislife.core.hibernate.pojo.Account;
import com.bizislife.core.hibernate.pojo.EContact;
import com.bizislife.core.hibernate.pojo.EContact.ContactType;

@TransactionConfiguration(defaultRollback=true)
@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:root-context.xml", "classpath:servlet-context.xml"})
public class AccountJpaRepositoryTest {

	@InjectMocks
	@Autowired
	AccountJpaRepository accountJpaRepository;
	
	private final String ACCOUNT_STONE_UUID = "0E4CCD6C-A546-4BF4-A3A7-16E8A33433F6";
	
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		EContact eContact = new EContact();
		eContact.setContactType(ContactType.Email);
		eContact.setContactValue("stonegu@hotmail.com");
		eContact.setUid(UUID.randomUUID().toString());
		Account account = new Account();
		account.setLoginname("stonegu");
		account.setPwd("pwd");
		account.setUid(ACCOUNT_STONE_UUID);
		account.addEContact(eContact);
		eContact.setAccount(account);
		accountJpaRepository.save(account);
		
	}

	@Test
	public void testFindByLoginname() {
		List<Account> accounts = accountJpaRepository.findByLoginname("stonegu");
		assertThat(1, is(accounts.size()));
		
		List<Account> accounts_notfound = accountJpaRepository.findByLoginname("caoman");
		assertThat(0, is(accounts_notfound.size()));
	}

	@Test
	public void testFindByUid() {
		List<Account> accounts = accountJpaRepository.findByUid(ACCOUNT_STONE_UUID);
		assertThat(1, is(accounts.size()));
		List<Account> accounts_notfound = accountJpaRepository.findByUid(ACCOUNT_STONE_UUID+"1");
		assertThat(0, is(accounts_notfound.size()));
	}

}
