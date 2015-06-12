package com.bizislife.core.hibernate.dao;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
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
import com.bizislife.core.hibernate.pojo.Address;
import com.bizislife.core.hibernate.pojo.ContactLocation;
import com.bizislife.core.hibernate.pojo.EContact;
import com.bizislife.core.hibernate.pojo.EContact.ContactType;
import com.bizislife.core.hibernate.pojo.GpsLocation;
import com.bizislife.core.hibernate.pojo.Group;
import com.bizislife.core.hibernate.pojo.Organization;
import com.bizislife.core.hibernate.pojo.Role;

@TransactionConfiguration(defaultRollback=true)
@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:root-context.xml", "classpath:servlet-context.xml"})
public class TableCreationTest {
	
	@Autowired
	private AccountJpaRepository accountJpaRepository;
	
	@Autowired
	private ContactlocationJpaRepository contactlocationJpaRepository;

	@Autowired
	private GroupJpaRepository groupJpaRepository;
	
	@Autowired
	private OrgJpaRepository orgJpaRepository;

	@Autowired
	private RoleJpaRepository roleJpaRepository;
	
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
		account1.setLoginname("account1");
		account1.setPwd("pwd1");
		account1.setUid(UUID.randomUUID().toString());
		
		Account account2 = new Account();
		account2.setLoginname("account2");
		account2.setPwd("pwd2");
		account2.setUid(UUID.randomUUID().toString());
		
		Account account3 = new Account();
		account3.setLoginname("account3");
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
		
		groupJpaRepository.save(group1);
		groupJpaRepository.save(group2);
		
		// create 3 roles
		Role role1 = new Role();
		role1.setName("role1");
		role1.setUid(UUID.randomUUID().toString());
		Role role2 = new Role();
		role2.setName("role2");
		role2.setUid(UUID.randomUUID().toString());
		Role role3 = new Role();
		role3.setName("role3");
		role3.setUid(UUID.randomUUID().toString());
		
		// add role to account
		account1.addRole(role1);
		account2.addRole(role1);
		account2.addRole(role2);
		account3.addRole(role1);
		account3.addRole(role2);
		account3.addRole(role3);
		
//		accountJpaRepository.save(account1);
//		accountJpaRepository.save(account2);
//		accountJpaRepository.save(account3);
		
		// add role to group
		group1.addRole(role1);
		group2.addRole(role1);
		group2.addRole(role2);
		group2.addRole(role3);
		
//		groupJpaRepository.save(group1);
//		groupJpaRepository.save(group2);
		
		// create contactLocations for org1
		ContactLocation con1 = new ContactLocation();
		con1.setUid(UUID.randomUUID().toString());
		Address address1 = new Address();
		address1.setPostalCode("111");
		con1.setAddress(address1);
		GpsLocation gps1 = new GpsLocation();
		gps1.setLatitude(1D);
		con1.setGpsLocation(gps1);
		con1.setOrganization(org1);
		
		ContactLocation con2 = new ContactLocation();
		con2.setUid(UUID.randomUUID().toString());
		Address address2 = new Address();
		address2.setPostalCode("222");
		con2.setAddress(address2);
		GpsLocation gps2 = new GpsLocation();
		gps2.setLatitude(2D);
		con2.setGpsLocation(gps2);
		con2.setOrganization(org1);
		
		org1.addContactLocation(con1);
		org1.addContactLocation(con2);
		
//		orgJpaRepository.save(org1);
		
		// create contactLocations for account2
		ContactLocation con3 = new ContactLocation();
		con3.setUid(UUID.randomUUID().toString());
		Address address3 = new Address();
		address3.setPostalCode("333");
		con3.setAddress(address3);
		GpsLocation gps3 = new GpsLocation();
		gps3.setLatitude(3D);
		con3.setGpsLocation(gps3);
		con3.setAccount(account2);
		
		ContactLocation con4 = new ContactLocation();
		con4.setUid(UUID.randomUUID().toString());
		Address address4 = new Address();
		address4.setPostalCode("444");
		con4.setAddress(address4);
		GpsLocation gps4 = new GpsLocation();
		gps4.setLatitude(4D);
		con4.setGpsLocation(gps4);
		con4.setAccount(account2);
		
		account2.addContactLocation(con3);
		account2.addContactLocation(con4);
		
//		accountJpaRepository.save(account2);
		
		// add eContacts to contactLocation
		EContact eContact1 = new EContact();
		eContact1.setUid(UUID.randomUUID().toString());
		eContact1.setContactType(ContactType.Email);
		eContact1.setContactValue("abc@bizislife.com");
		eContact1.setContactLocation(con1);

		EContact eContact2 = new EContact();
		eContact2.setUid(UUID.randomUUID().toString());
		eContact2.setContactType(ContactType.Email);
		eContact2.setContactValue("edf@bizislife.com");
		eContact2.setContactLocation(con1);
		
		con1.addEContact(eContact1);
		con1.addEContact(eContact2);
		
//		contactlocationJpaRepository.save(con1);
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
			if(account.getLoginname().equals("account1")) {
				assertThat(1, is(account.getOrganizations().size()));
			} else if(account.getLoginname().equals("account2")) {
				assertThat(1, is(account.getOrganizations().size()));
			} else if(account.getLoginname().equals("account3")) {
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
	
	@Test
	public void roleForAccountOrGroupUnidirectionalManyToMany() {
		List<Account> savedAccounts = accountJpaRepository.findAll();
		assertNotNull(savedAccounts);
		
		for (Account account : savedAccounts) {
			assertNotNull(account.getRoles());
			if (account.getLoginname().equals("account1")) {
				assertThat(1, is(account.getRoles().size()));
			} else if (account.getLoginname().equals("account2")) {
				assertThat(2, is(account.getRoles().size()));
			} else if (account.getLoginname().equals("account3")) {
				assertThat(3, is(account.getRoles().size()));
			}
		}
		
		List<Group> savedGroups = groupJpaRepository.findAll();
		assertNotNull(savedGroups);
		
		for (Group group : savedGroups) {
			assertNotNull(group.getRoles());
			if (group.getName().equals("group1")) {
				assertThat(1, is(group.getRoles().size()));
			} else if (group.getName().equals("group2")) {
				assertThat(3, is(group.getRoles().size()));
			}
		}
	}
	
	@Test
	public void contactLocationForOrgOrAccountBidirectionalOneToMany() {
		List<Organization> savedOrgs = orgJpaRepository.findAll();
		assertNotNull(savedOrgs);
		
		for (Organization org : savedOrgs) {
			if (org.getName().equals("org1")) {
				assertNotNull(org.getContactLocations());
				assertThat(2, is(org.getContactLocations().size()));
				
				Collection<ContactLocation> contactLocations = org.getContactLocations();
				Iterator<ContactLocation> iterator = contactLocations.iterator();
				while (iterator.hasNext()) {
					ContactLocation contactLocation = (ContactLocation) iterator.next();
					assertNotNull(contactLocation.getAddress());
					assertNotNull(contactLocation.getAddress().getPostalCode());
					assertNotNull(contactLocation.getGpsLocation());
					assertNotNull(contactLocation.getGpsLocation().getLatitude());
					assertThat("org1", is(contactLocation.getOrganization().getName()));
					assertNull(contactLocation.getAccount());
				}
				
				
			} else {
				assertNull(org.getContactLocations());
			}
		}
		
		List<Account> savedAccounts = accountJpaRepository.findAll();
		assertNotNull(savedAccounts);
		
		for (Account account : savedAccounts) {
			if (account.getLoginname().equals("account2")) {
				assertNotNull(account.getContactLocations());
				assertThat(2, is(account.getContactLocations().size()));
				
				Collection<ContactLocation> contactLocations = account.getContactLocations();
				Iterator<ContactLocation> iterator = contactLocations.iterator();
				while (iterator.hasNext()) {
					ContactLocation contactLocation = (ContactLocation) iterator.next();
					assertNotNull(contactLocation.getAddress());
					assertNotNull(contactLocation.getAddress().getPostalCode());
					assertNotNull(contactLocation.getGpsLocation());
					assertNotNull(contactLocation.getGpsLocation().getLatitude());
					assertThat("account2", is(contactLocation.getAccount().getLoginname()));
					assertNull(contactLocation.getOrganization());
				}
			} else {
				assertNull(account.getContactLocations());
			}
		}
		
	}
	
	@Test
	public void econtactForContactLocationBidirectionalOneToMany() {
		List<ContactLocation> savedContactLocations = contactlocationJpaRepository.findAll();
		assertNotNull(savedContactLocations);
		
		for (ContactLocation contactLocation: savedContactLocations) {
			if (contactLocation.getAddress().getPostalCode().equals("111")) {
				assertNotNull(contactLocation.getEContacts());
				
				Iterator<EContact> iterator = contactLocation.getEContacts().iterator();
				while (iterator.hasNext()) {
					EContact eContact = (EContact) iterator.next();
					assertThat(ContactType.Email, is(eContact.getContactType()));
					
					assertThat(1D, is(eContact.getContactLocation().getGpsLocation().getLatitude()));
					
					assertThat("org1", is(eContact.getContactLocation().getOrganization().getName()));
				}
				
				
			} else {
				assertNull(contactLocation.getEContacts());
			}
		}
		
	}
	
	@Test
	public void checkTotalNumberOfRecords() {
		List<Organization> savedOrgs = orgJpaRepository.findAll();
		assertNotNull(savedOrgs);
		assertThat(2, is(savedOrgs.size()));
		
		List<Account> savedAccounts = accountJpaRepository.findAll();
		assertNotNull(savedAccounts);
		assertThat(3, is(savedAccounts.size()));
		
		List<Group> savedGroups = groupJpaRepository.findAll();
		assertNotNull(savedGroups);
		assertThat(2, is(savedGroups.size()));
		
		List<Role> savedRoles = roleJpaRepository.findAll();
		assertNotNull(savedRoles);
		assertThat(3, is(savedRoles.size()));
		
		List<ContactLocation> savedContactLocations = contactlocationJpaRepository.findAll();
		assertNotNull(savedContactLocations);
		assertThat(4, is(savedContactLocations.size()));
		
	}
	
	
}
