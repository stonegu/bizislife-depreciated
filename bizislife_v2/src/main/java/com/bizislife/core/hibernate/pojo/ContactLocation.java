package com.bizislife.core.hibernate.pojo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.bizislife.core.hibernate.pojo.EContact.ContactType;

@Entity
@Table(name="contactlocation")
public class ContactLocation extends UIDPojo{
	
	@Embedded
	private Address address;
	
	@Embedded
	private GpsLocation gpsLocation;
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="accountid")
	private Account account;

	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="orgid")
	private Organization organization;
	
	@OneToMany(mappedBy="contactLocation", cascade=CascadeType.ALL)
	private Collection<EContact> eContacts;
	
	public ContactLocation() {
		this.uid = UUID.randomUUID().toString();
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public GpsLocation getGpsLocation() {
		return gpsLocation;
	}

	public void setGpsLocation(GpsLocation gpsLocation) {
		this.gpsLocation = gpsLocation;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public Organization getOrganization() {
		return organization;
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
	}

	public Collection<EContact> getEContacts() {
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
	
	public Long detachEcontact(ContactType contactType, String contactValue) {
		Long detachedId = null;
		if (contactType!=null && contactValue!=null) {
			if (this.eContacts!=null) {
				EContact eContact = new EContact();
				eContact.setContactType(contactType);
				eContact.setContactValue(contactValue);
				
				Iterator<EContact> eContactIterator = this.eContacts.iterator();
				EContact detachItem = null;
				while (eContactIterator.hasNext()) {
					EContact ec = (EContact) eContactIterator.next();
					if (ec.equals(eContact)) {
						detachItem = ec;
						break;
					}
				}
				if (detachItem!=null) {
					if (detachItem.getContactLocation()!=null) {
						detachedId = detachItem.getId();
						detachItem.setContactLocation(null);
						this.eContacts.remove(detachItem);
					}
				}
			}
		}
		return detachedId;
		
	}
	
//	public void setEContacts(Collection<EContact> eContacts) {
//		this.eContacts = eContacts;
//	}
	
	/**
	 * @param eContact
	 * @return null if param eContact is null, <br/>
	 * 		true if eContact added or created relationship with ContactLocation, <br/> 
	 * 		false if same eContact exist and already has relationship with ContactLocation.
	 */
	public Boolean addEContact(EContact eContact) {
		if (eContact!=null) {
			Collection<EContact> eContacts = null;
			if (this.account!=null) {
				eContacts = this.account.getEContacts();
			} else if (this.organization!=null) {
				eContacts = this.organization.getEContacts();
			}
			
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
				eContact.setContactLocation(this);
				this.eContacts.add(eContact);
				return true;
			} else {
				if (exist.getContactLocation()==null) {
					if (this.eContacts==null) this.eContacts = new HashSet<>();
					exist.setContactLocation(this);
					this.eContacts.add(exist);
					return true;
				} else {
					return false;
				}
			}
		}
		return null;
	}

	public boolean belongToUuid(String uuid) {
		if (uuid!=null) {
			return this.account!=null?this.account.getUid().equals(uuid):false
					|| this.organization!=null?this.organization.getUid().equals(uuid):false;
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((address == null) ? 0 : address.hashCode());
//		result = prime * result
//				+ ((gpsLocation == null) ? 0 : gpsLocation.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
//		if (!super.equals(obj))
//			return false;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ContactLocation other = (ContactLocation) obj;
		if (address == null) {
			if (other.address != null)
				return false;
		} else if (!address.equals(other.address))
			return false;
//		if (gpsLocation == null) {
//			if (other.gpsLocation != null)
//				return false;
//		} else if (!gpsLocation.equals(other.gpsLocation))
//			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ContactLocation [address=" + address + ", gpsLocation="
				+ gpsLocation + ", eContacts=" + eContacts + "]";
	}

}
