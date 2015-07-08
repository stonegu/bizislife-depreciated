package com.bizislife.core.service;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import java.util.List;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.bizislife.core.hibernate.dao.AccountJpaRepository;
import com.bizislife.core.hibernate.pojo.Account;
import com.bizislife.core.hibernate.pojo.Address;
import com.bizislife.core.hibernate.pojo.ContactLocation;
import com.bizislife.core.hibernate.pojo.EContact;
import com.bizislife.core.hibernate.pojo.EContact.ContactType;

@TransactionConfiguration(defaultRollback=true)
@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:root-context.xml", "classpath:servlet-context.xml"})
public class AccountServiceImplTest {
	
	@Mock
	AccountJpaRepository accountJpaRepository;
	
	@InjectMocks
	@Autowired
	AccountService accountService;
	
	private final String ACCOUNT_STONE_UUID = "0E4CCD6C-A546-4BF4-A3A7-16E8A33433F6";

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void testRemoveEContact() {
	}

}
