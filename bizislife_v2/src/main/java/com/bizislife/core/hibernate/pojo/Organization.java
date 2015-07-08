package com.bizislife.core.hibernate.pojo;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.bizislife.core.hibernate.pojo.EContact.ContactType;

@Entity
@Table(name="organization")
public class Organization extends UIDPojo{
	
	@Column(name="oname")
	private String name;
	
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name="organization_account", joinColumns=@JoinColumn(name="oid"), inverseJoinColumns=@JoinColumn(name="aid"))
	private Collection<Account> accounts;

	@OneToMany(mappedBy="organization", cascade=CascadeType.ALL)
	private Collection<ContactLocation> contactLocations;
	
	@OneToMany(mappedBy="organization", cascade=CascadeType.ALL)
	private Collection<EContact> eContacts;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Collection<Account> getAccounts() {
		return accounts;
	}

	public void setAccounts(Collection<Account> accounts) {
		this.accounts = accounts;
	}
	
	public void addAccount(Account account) {
		if (account!=null) {
			if (accounts==null) accounts = new HashSet<Account>();
			accounts.add(account);
		}
	}

	public Collection<ContactLocation> getContactLocations() {
		return contactLocations;
	}

	public void setContactLocations(Collection<ContactLocation> contactLocations) {
		this.contactLocations = contactLocations;
	}
	
	public void addContactLocation(ContactLocation contactLocation) {
		if (contactLocation!=null) {
			if (this.contactLocations==null) this.contactLocations = new HashSet<ContactLocation>();
			this.contactLocations.add(contactLocation);
		}
	}

	public Collection<EContact> getEContacts() {
		
		HashSet<EContact> eContacts = new HashSet<>();
		if (this.eContacts!=null) {
			eContacts.addAll(this.eContacts);
		}
		
		if (this.contactLocations!=null) {
			for (ContactLocation contactLocation : this.contactLocations) {
				if (contactLocation.getEContacts()!=null) {
					eContacts.addAll(contactLocation.getEContacts());
				}
			}
		}
		
		return eContacts;
	}

	public EContact getEContact(ContactType contactType, String contactValue) {
		if (contactType!=null && contactValue!=null) {
			Collection<EContact> contacts = getEContacts();
			if (contacts!=null) {
				EContact eContact = new EContact();
				eContact.setContactType(contactType);
				eContact.setContactValue(contactValue);
				Iterator<EContact> econtactIterator = contacts.iterator();
				while (econtactIterator.hasNext()) {
					EContact ec = (EContact) econtactIterator.next();
					if (ec.equals(eContact)) {
						return ec;
					}
				}
			}
		}
		return null;
	}
	
	/**
	 * @param eContact
	 * @return null if param eContact is null, <br/>
	 * 		true if eContact added or created relationship with Organization, <br/> 
	 * 		false if same eContact exist and already has relationship with Organization.
	 */
	public Boolean addEContact(EContact eContact) {
		
		if (eContact!=null) {
			// check eContact exist:
			Collection<EContact> eContacts = getEContacts();
			EContact exist = null;
			if (eContacts!=null && eContacts.size()>0) {
				for (EContact ec : eContacts) {
					if (ec.equals(eContact)) {
						exist = ec;
						break;
					}
				}
			}
			
			if (exist==null) {
				if (this.eContacts==null) this.eContacts = new HashSet<>();
				eContact.setOrganization(this);
				this.eContacts.add(eContact);
				return true;
			} else {
				if (exist.getOrganization()==null) { // create relationship only
					if (this.eContacts==null) this.eContacts = new HashSet<>();
					exist.setOrganization(this);
					this.eContacts.add(exist);
					return true;
				} else {
					return false;
				}
			}
		}
		
		return null;
	}
	
	public void removeEContact(ContactType contactType, String contactValue) {
		if (contactType!=null && contactValue!=null) {
			Collection<EContact> eContacts = getEContacts();
			if (eContacts!=null) {
				EContact eContact = new EContact();
				eContact.setContactType(contactType);
				eContact.setContactValue(contactValue);
				
				Iterator<EContact> eContactIterator = eContacts.iterator();
				EContact delItem = null;
				while (eContactIterator.hasNext()) {
					EContact ec = (EContact) eContactIterator.next();
					if (ec.equals(eContact)) {
						delItem = ec;
						break;
					}
				}
				if (delItem!=null) {
					if (delItem.getOrganization()!=null) {
						delItem.setOrganization(null);
						this.eContacts.remove(delItem);
					}
					if (delItem.getContactLocation()!=null) {
						delItem.getContactLocation().getEContacts().remove(delItem);
						delItem.setContactLocation(null);
					}
				}
			}
		}
	}
	
	public void detachEcontact(ContactType contactType, String contactValue) {
		if (contactType!=null && contactValue!=null) {
			Collection<EContact> eContacts = getEContacts();
			if (eContacts!=null) {
				EContact eContact = new EContact();
				eContact.setContactType(contactType);
				eContact.setContactValue(contactValue);
				
				Iterator<EContact> eContactIterator = eContacts.iterator();
				EContact detachItem = null;
				while (eContactIterator.hasNext()) {
					EContact ec = (EContact) eContactIterator.next();
					if (ec.equals(eContact)) {
						detachItem = ec;
						break;
					}
				}
				if (detachItem!=null) {
					if (detachItem.getOrganization()!=null) {
						detachItem.setOrganization(null);
						this.eContacts.remove(detachItem);
					}
				}
			}
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Organization other = (Organization) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Organization [name=" + name + ", accounts=" + accounts
				+ ", contactLocations=" + contactLocations + "]";
	}

}
