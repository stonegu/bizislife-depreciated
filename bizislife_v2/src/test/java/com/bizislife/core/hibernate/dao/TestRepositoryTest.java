package com.bizislife.core.hibernate.dao;

import static org.junit.Assert.*;

import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.bizislife.core.hibernate.pojo.Account;
import com.bizislife.core.hibernate.pojo.Organization;

@TransactionConfiguration(defaultRollback=false)
@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:root-context.xml", "classpath:servlet-context.xml"})
public class TestRepositoryTest {
	
	@Autowired
	private TestRepository testRepository;

	@Test
	public void insertOrgTest() {
		Organization org = new Organization();
		org.setOname("org2");
		org.setUid(UUID.randomUUID().toString());
		
		Account account = new Account();
		account.setName("account2");
		account.setPwd("pwd");
		account.setUid(UUID.randomUUID().toString());
		
		org.addAccount(account);
		
		testRepository.save(org);
		
		Organization orgFound = testRepository.findOne(org.getId());
		assertNotNull(orgFound);
		
		
	}

}
