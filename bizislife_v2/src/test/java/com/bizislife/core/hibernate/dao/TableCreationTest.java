package com.bizislife.core.hibernate.dao;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import java.util.List;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.bizislife.core.hibernate.pojo.Account;
import com.bizislife.core.hibernate.pojo.Group;
import com.bizislife.core.hibernate.pojo.Organization;

@TransactionConfiguration(defaultRollback=true)
@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:root-context.xml", "classpath:servlet-context.xml"})
public class TableCreationTest {
	
	@Autowired
	private AccountJpaRepository accountJpaRepository;

	@Autowired
	private GroupJpaRepository groupJpaRepository;
	
	@Autowired
	private OrgJpaRepository orgJpaRepository;
	
	@Before
	public void setup() {
		
		// create 2 organization
		Organization org1 = new Organization();
		org1.setName("org1");
		org1.setUid(UUID.randomUUID().toString());
		
		Organization org2 = new Organization();
		org2.setName("org2");
		org2.setUid(UUID.randomUUID().toString());
		
		// create 3 account
		Account account1 = new Account();
		account1.setName("account1");
		account1.setPwd("pwd1");
		account1.setUid(UUID.randomUUID().toString());
		
		Account account2 = new Account();
		account2.setName("account2");
		account2.setPwd("pwd2");
		account2.setUid(UUID.randomUUID().toString());
		
		Account account3 = new Account();
		account3.setName("account3");
		account3.setPwd("pwd3");
		account3.setUid(UUID.randomUUID().toString());
		
		// add account to organization
		org1.addAccount(account1);
		org1.addAccount(account3);
		org2.addAccount(account2);
		
		// add organization to account
		account1.addOrganization(org1);
		account3.addOrganization(org1);
		account2.addOrganization(org2);
		
		orgJpaRepository.save(org1);
		orgJpaRepository.save(org2);
		
		// create 2 groups
		Group group1 = new Group();
		group1.setName("group1");
		group1.setUid(UUID.randomUUID().toString());
		
		Group group2 = new Group();
		group2.setName("group2");
		group2.setUid(UUID.randomUUID().toString());
		
		// add account to group
		group1.addAccount(account1);
		group1.addAccount(account3);
		group2.addAccount(account2);
		
		// add group to account;
		account1.addGroup(group1);
		account3.addGroup(group1);
		account2.addGroup(group2);
		
	}

	@Test
	public void organizationAccountBidirectionalManyToMany() {
		
		List<Organization> savedOrgs = orgJpaRepository.findAll();
		assertNotNull(savedOrgs);
		assertThat(2, is(savedOrgs.size()));
		
		for (Organization org : savedOrgs) {
			assertNotNull(org.getAccounts());
			if (org.getName().equals("org1")) {
				assertThat(2, is(org.getAccounts().size()));
			} else if (org.getName().equals("org2")) {
				assertThat(1, is(org.getAccounts().size()));
			}
		}
		
		List<Account> savedAccounts = accountJpaRepository.findAll();
		assertNotNull(savedAccounts);
		assertThat(3, is(savedAccounts.size()));
		
		for (Account account : savedAccounts) {
			assertNotNull(account.getOrganizations());
			if(account.getName().equals("account1")) {
				assertThat(1, is(account.getOrganizations().size()));
			} else if(account.getName().equals("account2")) {
				assertThat(1, is(account.getOrganizations().size()));
			} else if(account.getName().equals("account3")) {
				assertThat(1, is(account.getOrganizations().size()));
			}
		}
	}
	
	@Test
	public void groupAccountBidirectionalManyToMany() {
		List<Group> savedGroups = groupJpaRepository.findAll();
		assertNotNull(savedGroups);
		assertThat(2, is(savedGroups.size()));
		
		for (Group group : savedGroups) {
			assertNotNull(group.getAccounts());
			if (group.getName().equals("group1")) {
				assertThat(2, is(group.getAccounts().size()));
			} else if (group.getName().equals("group2")) {
				assertThat(1, is(group.getAccounts().size()));
			}
		}
	}
	
	
}
