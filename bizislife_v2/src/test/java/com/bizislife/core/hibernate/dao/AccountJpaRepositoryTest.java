package com.bizislife.core.hibernate.dao;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import java.util.Collection;
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
import com.bizislife.core.hibernate.pojo.Address;
import com.bizislife.core.hibernate.pojo.ContactLocation;
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
		// account with 2 econtacts, both econtacts have relationship with account and account's contactlocation
		Address address = new Address();
		address.setPostalCode("l3p3t3");
		ContactLocation contactLocation = new ContactLocation();
		contactLocation.setAddress(address);
		
		EContact eContact1 = new EContact();
		eContact1.setContactType(ContactType.Email);
		eContact1.setContactValue("stonegu@hotmail.com");
		eContact1.setUid(UUID.randomUUID().toString());
		
		eContact1.setContactLocation(contactLocation);
		contactLocation.addEContact(eContact1);
		
		EContact eContact2 = new EContact();
		eContact2.setContactType(ContactType.Email);
		eContact2.setContactValue("stonegu@gmail.com");
		eContact2.setUid(UUID.randomUUID().toString());
		
		eContact2.setContactLocation(contactLocation);
		contactLocation.addEContact(eContact2);
		
		Account account = new Account();
		account.setLoginname("stonegu");
		account.setPwd("pwd");
		account.setUid(ACCOUNT_STONE_UUID);
		
		account.addContactLocation(contactLocation);
		contactLocation.setAccount(account);
		
		account.addEContact(eContact1);
		eContact1.setAccount(account);
		
		account.addEContact(eContact2);
		eContact2.setAccount(account);
		
		accountJpaRepository.save(account);
		
	}
	
	@Test
	public void testSetup() {
		List<Account> accounts = accountJpaRepository.findByLoginname("stonegu");
		// test account creation
		assertThat(1, is(accounts.size()));
		assertNotNull(accounts.get(0).getId());
		
		Account stoneguAccount = accounts.get(0);
		
		Collection<ContactLocation> contactLocations = stoneguAccount.getContactLocations();
		
		// test contactlocation creation
		assertThat(1, is(contactLocations.size()));
		assertNotNull(contactLocations.iterator().next().getId());
		assertThat(2, is(contactLocations.iterator().next().getEContacts().size()));
		assertTrue(stoneguAccount==contactLocations.iterator().next().getAccount());
		
		Collection<EContact> eContacts = stoneguAccount.getEContacts();

		// test econtacts creation
		assertThat(2, is(eContacts.size()));
		for (EContact eContact: eContacts) {
			// test id generation
			assertNotNull(eContact.getId());
			// test the relationship with contactLocation
			assertNotNull(eContact.getContactLocation());
			assertTrue(eContact.getContactLocation()==contactLocations.iterator().next());
			
			// test the relationship with account
			assertNotNull(eContact.getAccount());
			assertTrue(eContact.getAccount()==stoneguAccount);
		}
		
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
