package com.bizislife.core.hibernate.dao;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import java.util.ArrayList;
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
import com.bizislife.core.service.AccountService;

@TransactionConfiguration(defaultRollback=true)
@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:root-context.xml", "classpath:servlet-context.xml"})
public class TableCreationTest {
	
	@Autowired
	private AccountJpaRepository accountJpaRepository;
	
	@Autowired
	private AccountService accountService;
	
	@Autowired
	private ContactlocationJpaRepository contactlocationJpaRepository;
	
	@Autowired
	private EContactJpaRepository eContactJpaRepository;

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
		
		// add role to group
		group1.addRole(role1);
		group2.addRole(role1);
		group2.addRole(role2);
		group2.addRole(role3);
		
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
		
		
		
		// ************ account registration ************** 
		Account accountRegist1 = new Account();
		accountRegist1.setUid(UUID.randomUUID().toString());
		accountRegist1.setLoginname("stonegu");
		accountRegist1.setPwd("pwd");
		EContact eContact1ForAccountRegist1 = new EContact();
		eContact1ForAccountRegist1.setUid(UUID.randomUUID().toString());
		eContact1ForAccountRegist1.setAccount(accountRegist1);
		eContact1ForAccountRegist1.setContactType(ContactType.Email);
		eContact1ForAccountRegist1.setContactValue("stonegu@hotmail.com");
		accountRegist1.addEContact(eContact1ForAccountRegist1);
		accountJpaRepository.save(accountRegist1);
		
		// *********** add contactLocation to stonegu account ************
		ContactLocation contactLocation1ForAccountRegist1 = new ContactLocation();
		contactLocation1ForAccountRegist1.setAccount(accountRegist1);
		contactLocation1ForAccountRegist1.setUid(UUID.randomUUID().toString());
		Address addressForContactLocation1ForAccountRegist1 = new Address();
		addressForContactLocation1ForAccountRegist1.setCity("Toronto");
		addressForContactLocation1ForAccountRegist1.setPostalCode("l3p3t3");
		contactLocation1ForAccountRegist1.setAddress(addressForContactLocation1ForAccountRegist1);
		accountRegist1.addContactLocation(contactLocation1ForAccountRegist1);
		accountJpaRepository.save(accountRegist1);
		
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
		assertThat(4, is(savedAccounts.size()));
		
		for (Account account : savedAccounts) {
//			assertNotNull(account.getOrganizations());
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
//			assertNotNull(account.getRoles());
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
		assertThat(4, is(savedAccounts.size()));
		
		List<Group> savedGroups = groupJpaRepository.findAll();
		assertNotNull(savedGroups);
		assertThat(2, is(savedGroups.size()));
		
		List<Role> savedRoles = roleJpaRepository.findAll();
		assertNotNull(savedRoles);
		assertThat(3, is(savedRoles.size()));
		
		List<ContactLocation> savedContactLocations = contactlocationJpaRepository.findAll();
		assertNotNull(savedContactLocations);
		assertThat(5, is(savedContactLocations.size()));
		
	}
	
	/*	
	 * test relationship for Account, ContactLocation, EContact 
	*/	
	@Test
	public void accountRegistration() {
		List<Account> accounts = accountJpaRepository.findByLoginname("stonegu");
		assertThat(1, is(accounts.size()));
		assertThat(1, is(accounts.get(0).getEContacts().size()));
	}
	
	@Test
	public void addSameEmailToAccount_eContact() {
		List<Account> accounts = accountJpaRepository.findByLoginname("stonegu");
		assertThat(1, is(accounts.size()));
		
		if (accounts!=null && accounts.size()==1) {
			Account stoneguAccount = accounts.get(0);
			
			EContact eContact1ForAccountRegist = new EContact();
			eContact1ForAccountRegist.setUid(UUID.randomUUID().toString());
			eContact1ForAccountRegist.setAccount(stoneguAccount);
			eContact1ForAccountRegist.setContactType(ContactType.Email);
			eContact1ForAccountRegist.setContactValue("stonegu@hotmail.com");
			stoneguAccount.addEContact(eContact1ForAccountRegist);
			accountJpaRepository.save(stoneguAccount);
			assertThat(1,  is(stoneguAccount.getEContacts().size()));
		}
	}
	
	@Test
	public void addDiffEmailToAccount_eContact() {
		List<Account> accounts = accountJpaRepository.findByLoginname("stonegu");
		assertThat(1, is(accounts.size()));
		
		if (accounts!=null && accounts.size()==1) {
			Account stoneguAccount = accounts.get(0);
			
			EContact eContact1ForAccountRegist = new EContact();
			eContact1ForAccountRegist.setUid(UUID.randomUUID().toString());
			eContact1ForAccountRegist.setAccount(stoneguAccount);
			eContact1ForAccountRegist.setContactType(ContactType.Email);
			eContact1ForAccountRegist.setContactValue("stonegu2@hotmail.com");
			stoneguAccount.addEContact(eContact1ForAccountRegist);
			accountJpaRepository.save(stoneguAccount);
			assertThat(2,  is(stoneguAccount.getEContacts().size()));
		}
	}
	
	@Test
	public void addSameEmailToAccount_contactLocation() {
		List<Account> accounts = accountJpaRepository.findByLoginname("stonegu");
		assertThat(1, is(accounts.size()));
		
		if (accounts!=null && accounts.size()==1) {
			Account stoneguAccount = accounts.get(0);
			
			Collection<ContactLocation> contactLocations = stoneguAccount.getContactLocations();
			assertThat(1, is(contactLocations.size()));
			
			ContactLocation contactLocation = contactLocations.iterator().next();
			EContact eContact1ForAccountRegist = new EContact();
			eContact1ForAccountRegist.setUid(UUID.randomUUID().toString());
			eContact1ForAccountRegist.setContactType(ContactType.Email);
			eContact1ForAccountRegist.setContactValue("stonegu@hotmail.com");
			eContact1ForAccountRegist.setContactLocation(contactLocation);
			contactLocation.addEContact(eContact1ForAccountRegist);
			contactlocationJpaRepository.save(contactLocation);
			assertThat(1, is(stoneguAccount.getEContacts().size()));
		}
	}
	
	@Test
	public void addDiffEmailToAccount_contactLocation() {
		List<Account> accounts = accountJpaRepository.findByLoginname("stonegu");
		assertThat(1, is(accounts.size()));
		
		if (accounts!=null && accounts.size()==1) {
			Account stoneguAccount = accounts.get(0);
			
			Collection<ContactLocation> contactLocations = stoneguAccount.getContactLocations();
			assertThat(1, is(contactLocations.size()));
			
			ContactLocation contactLocation = contactLocations.iterator().next();
			EContact eContact1ForAccountRegist = new EContact();
			eContact1ForAccountRegist.setUid(UUID.randomUUID().toString());
			eContact1ForAccountRegist.setContactType(ContactType.Email);
			eContact1ForAccountRegist.setContactValue("stonegu2@hotmail.com");
			eContact1ForAccountRegist.setContactLocation(contactLocation);
			contactLocation.addEContact(eContact1ForAccountRegist);
			contactlocationJpaRepository.save(contactLocation);
			assertThat(2, is(stoneguAccount.getEContacts().size()));
		}
	}
	
	@Test
	public void createRelationshipForAccountAndExistingEcontact() {
		List<Account> accounts = accountJpaRepository.findByLoginname("stonegu");
		assertThat(1, is(accounts.size()));
		
		if (accounts!=null && accounts.size()==1) {
			Account stoneguAccount = accounts.get(0);
			
			Collection<ContactLocation> contactLocations = stoneguAccount.getContactLocations();
			assertThat(1, is(contactLocations.size()));
			
			ContactLocation contactLocation = contactLocations.iterator().next();
			EContact eContact1ForAccountRegist = new EContact();
			eContact1ForAccountRegist.setUid(UUID.randomUUID().toString());
			eContact1ForAccountRegist.setContactType(ContactType.Email);
			eContact1ForAccountRegist.setContactValue("stonegu2@hotmail.com");
			contactLocation.addEContact(eContact1ForAccountRegist);
			contactlocationJpaRepository.save(contactLocation);
			assertThat(2, is(stoneguAccount.getEContacts().size()));
			
			// before: one econtact has account relationship, and another one doesn't have account relationship
			for (EContact eContact: stoneguAccount.getEContacts()) {
				if (eContact.getContactValue().equals("stonegu@hotmail.com")) {
					assertNotNull(eContact.getAccount());
				} else if (eContact.getContactValue().equals("stonegu2@hotmail.com")) {
					assertNull(eContact.getAccount());
				}
			}
			
			// after create relationship for account and existing econtact
			EContact newEContact = new EContact();
			newEContact.setContactType(ContactType.Email);
			newEContact.setContactValue("stonegu2@hotmail.com");
			newEContact.setUid(UUID.randomUUID().toString());
			stoneguAccount.addEContact(newEContact);
			accountJpaRepository.save(stoneguAccount);
			for (EContact eContact: stoneguAccount.getEContacts()) {
				if (eContact.getContactValue().equals("stonegu@hotmail.com")) {
					assertNotNull(eContact.getAccount());
				} else if (eContact.getContactValue().equals("stonegu2@hotmail.com")) {
					assertNotNull(eContact.getAccount());
					assertThat(stoneguAccount.getContactLocations().iterator().next().getEContacts().iterator().next().getId(), is(eContact.getId()));
				}
			}
		}
	}
	
	@Test
	public void createRelationshipForContactlocationAndExistingEcontact() {
		List<Account> accounts = accountJpaRepository.findByLoginname("stonegu");
		assertThat(1, is(accounts.size()));
		assertThat("stonegu@hotmail.com", is(accounts.get(0).getEContacts().iterator().next().getContactValue()));
		assertThat(1, is(accounts.get(0).getContactLocations().size()));
		ContactLocation contactLocation = accounts.get(0).getContactLocations().iterator().next();
		assertThat(null, is(contactLocation.getEContacts()));
		
		EContact eContact = new EContact();
		eContact.setContactLocation(contactLocation);
		eContact.setContactType(ContactType.Email);
		eContact.setContactValue("stonegu@hotmail.com");
		contactLocation.addEContact(eContact);
		contactlocationJpaRepository.save(contactLocation);
		
		assertThat(1, is(contactLocation.getEContacts().size()));
		assertThat(1, is(accounts.get(0).getEContacts().size()));
		assertThat(contactLocation.getEContacts().iterator().next().getId(), is(accounts.get(0).getEContacts().iterator().next().getId()));
		
		assertNotNull(accounts.get(0).getEContacts().iterator().next().getAccount());
		assertThat("stonegu", is(accounts.get(0).getEContacts().iterator().next().getAccount().getLoginname()));
		assertNotNull(accounts.get(0).getEContacts().iterator().next().getContactLocation());
		assertThat("l3p3t3", is(accounts.get(0).getEContacts().iterator().next().getContactLocation().getAddress().getPostalCode()));
	}
	
	@Test
	public void detachEcontact_fromAccount() {
		// create/update two econtact for user 'stonegu', these two econtacts have relationship with account and contactlocation
		List<Account> accounts = accountJpaRepository.findByLoginname("stonegu");
		assertThat(1, is(accounts.size()));
		Account stoneguAccount = accounts.get(0);
		assertThat("stonegu@hotmail.com", is(stoneguAccount.getEContacts().iterator().next().getContactValue()));
		assertNotNull(stoneguAccount.getEContacts().iterator().next().getAccount());
		assertNull(stoneguAccount.getEContacts().iterator().next().getContactLocation());
		assertThat(1, is(stoneguAccount.getContactLocations().size()));
		ContactLocation contactLocation = stoneguAccount.getContactLocations().iterator().next();
		assertThat(null, is(contactLocation.getEContacts()));
		// create relationship for 'stonegu@hotmail.com' with contactlocation
		EContact eContact1 = new EContact();
		eContact1.setContactType(ContactType.Email);
		eContact1.setContactValue("stonegu@hotmail.com");
		contactLocation.addEContact(eContact1);
		contactlocationJpaRepository.save(contactLocation);
		// make sure 'stonegu@hotmail.com' has relationship with contactlocation too
		assertThat(1, is(stoneguAccount.getEContacts().size()));
		assertNotNull(stoneguAccount.getEContacts().iterator().next().getContactLocation());
		assertThat(true, is(stoneguAccount.getEContacts().iterator().next()==stoneguAccount.getContactLocations().iterator().next().getEContacts().iterator().next()));
		
		// add another econtact
		EContact eContact2 = new EContact();
		eContact2.setContactType(ContactType.Email);
		eContact2.setContactValue("stonegu@gmail.com");
		eContact2.setUid(UUID.randomUUID().toString());
		contactLocation.addEContact(eContact2);
		stoneguAccount.addEContact(eContact2);
		accountJpaRepository.save(stoneguAccount);
		assertThat(2, is(stoneguAccount.getEContacts().size()));
		
		// double check two econtacts have relationship with account and contactlocation
		for (EContact eContact : stoneguAccount.getEContacts()) {
			assertNotNull(eContact.getAccount());
			assertNotNull(eContact.getContactLocation());
			assertNotNull(eContact.getId());
		}
		
		// ***** time to detach ***************
		stoneguAccount.detachEcontactFromAccount(ContactType.Email, "stonegu@gmail.com");
		assertThat(2, is(stoneguAccount.getEContacts().size()));
		EContact hotmailEconContact = stoneguAccount.getEContact(ContactType.Email, "stonegu@hotmail.com");
		assertNotNull(hotmailEconContact);
		EContact gmailEcoContact = stoneguAccount.getEContact(ContactType.Email, "stonegu@gmail.com");
		assertNotNull(gmailEcoContact);
		assertNull(gmailEcoContact.getAccount());
		assertNotNull(gmailEcoContact.getContactLocation());
		assertNotNull(hotmailEconContact.getAccount());
		assertNotNull(hotmailEconContact.getContactLocation());
		
		// check detach
		List<EContact> totalEContacts_beforeDel = eContactJpaRepository.findAll();
		EContact stoneguAtgmailDOTcom = null;
		if (totalEContacts_beforeDel!=null) {
			for (EContact ec : totalEContacts_beforeDel) {
				if (ec.getContactValue().equals("stonegu@gmail.com")) {
					stoneguAtgmailDOTcom = ec;
				}
			}
		}
		assertNotNull(stoneguAtgmailDOTcom);
		assertNotNull(stoneguAtgmailDOTcom.getContactLocation());
		assertNull(stoneguAtgmailDOTcom.getAccount());
		
		// ***** time to detach from all *************
		stoneguAccount.detachEContactFromAll(ContactType.Email, "stonegu@gmail.com");
		assertThat(1, is(stoneguAccount.getEContacts().size()));
		assertNotNull(stoneguAccount.getEContact(ContactType.Email, "stonegu@hotmail.com"));
		assertNull(stoneguAccount.getEContact(ContactType.Email, "stonegu@gmail.com"));
		assertNotNull(stoneguAccount.getEContact(ContactType.Email, "stonegu@hotmail.com").getAccount());
		assertNotNull(stoneguAccount.getEContact(ContactType.Email, "stonegu@hotmail.com").getContactLocation());

		// check detach
		List<EContact> totalEContacts_afterDel = eContactJpaRepository.findAll();
		stoneguAtgmailDOTcom = null;
		if (totalEContacts_afterDel!=null) {
			for (EContact ec : totalEContacts_afterDel) {
				if (ec.getContactValue().equals("stonegu@gmail.com")) {
					stoneguAtgmailDOTcom = ec;
				}
			}
		}
		assertNotNull(stoneguAtgmailDOTcom);
		assertNull(stoneguAtgmailDOTcom.getContactLocation());
		assertNull(stoneguAtgmailDOTcom.getAccount());
		
	}
	
	@Test
	public void delEcontactFromAccount() {
		// create/update two econtact for user 'stonegu', these two econtacts have relationship with account and contactlocation
		List<Account> accounts = accountJpaRepository.findByLoginname("stonegu");
		assertThat(1, is(accounts.size()));
		Account stoneguAccount = accounts.get(0);
		assertThat("stonegu@hotmail.com", is(stoneguAccount.getEContacts().iterator().next().getContactValue()));
		assertNotNull(stoneguAccount.getEContacts().iterator().next().getAccount());
		assertNull(stoneguAccount.getEContacts().iterator().next().getContactLocation());
		assertThat(1, is(stoneguAccount.getContactLocations().size()));
		ContactLocation contactLocation = stoneguAccount.getContactLocations().iterator().next();
		assertThat(null, is(contactLocation.getEContacts()));
		// create relationship for 'stonegu@hotmail.com' with contactlocation
		EContact eContact1 = new EContact();
		eContact1.setContactType(ContactType.Email);
		eContact1.setContactValue("stonegu@hotmail.com");
		contactLocation.addEContact(eContact1);
		contactlocationJpaRepository.save(contactLocation);
		// make sure 'stonegu@hotmail.com' has relationship with contactlocation too
		assertThat(1, is(stoneguAccount.getEContacts().size()));
		assertNotNull(stoneguAccount.getEContacts().iterator().next().getContactLocation());
		assertThat(true, is(stoneguAccount.getEContacts().iterator().next()==stoneguAccount.getContactLocations().iterator().next().getEContacts().iterator().next()));
		
		// add another econtact
		EContact eContact2 = new EContact();
		eContact2.setContactType(ContactType.Email);
		eContact2.setContactValue("stonegu@gmail.com");
		eContact2.setUid(UUID.randomUUID().toString());
		contactLocation.addEContact(eContact2);
		stoneguAccount.addEContact(eContact2);
		accountJpaRepository.save(stoneguAccount);
		assertThat(2, is(stoneguAccount.getEContacts().size()));
		
		// double check two econtacts have relationship with account and contactlocation
		for (EContact eContact : stoneguAccount.getEContacts()) {
			assertNotNull(eContact.getAccount());
			assertNotNull(eContact.getContactLocation());
			assertNotNull(eContact.getId());
		}
		
		// check stonegu@gmail still there:
		List<EContact> totalEContacts_beforeDel = eContactJpaRepository.findAll();
		EContact stoneguAtgmailDOTcom = null;
		if (totalEContacts_beforeDel!=null) {
			for (EContact ec : totalEContacts_beforeDel) {
				if (ec.getContactValue().equals("stonegu@gmail.com")) {
					stoneguAtgmailDOTcom = ec;
				}
			}
		}
		assertNotNull(stoneguAtgmailDOTcom);
		assertNotNull(stoneguAtgmailDOTcom.getContactLocation());
		assertNotNull(stoneguAtgmailDOTcom.getAccount());
		
		
		// ***** time to delete ***************
		accountService.removeEContact(stoneguAccount.getUid(), ContactType.Email, "stonegu@gmail.com");
		
		assertThat(1, is(stoneguAccount.getEContacts().size()));
		assertNotNull(stoneguAccount.getEContact(ContactType.Email, "stonegu@hotmail.com"));
		assertNull(stoneguAccount.getEContact(ContactType.Email, "stonegu@gmail.com"));
		assertNotNull(stoneguAccount.getEContact(ContactType.Email, "stonegu@hotmail.com").getAccount());
		assertNotNull(stoneguAccount.getEContact(ContactType.Email, "stonegu@hotmail.com").getContactLocation());

		// check detach
		List<EContact> totalEContacts_afterDel = eContactJpaRepository.findAll();
		stoneguAtgmailDOTcom = null;
		if (totalEContacts_afterDel!=null) {
			for (EContact ec : totalEContacts_afterDel) {
				if (ec.getContactValue().equals("stonegu@gmail.com")) {
					stoneguAtgmailDOTcom = ec;
				}
			}
		}
		assertNull(stoneguAtgmailDOTcom);

	}
	
}
