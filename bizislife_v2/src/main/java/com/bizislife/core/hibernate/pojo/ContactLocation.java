package com.bizislife.core.hibernate.pojo;

import java.util.Collection;
import java.util.HashSet;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

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

	public void setEContacts(Collection<EContact> eContacts) {
		this.eContacts = eContacts;
	}
	
	public void addEContact(EContact eContact) {
		if (eContact!=null) {
			if (this.eContacts==null) this.eContacts = new HashSet<EContact>();
			this.eContacts.add(eContact);
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		result = prime * result
				+ ((gpsLocation == null) ? 0 : gpsLocation.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		ContactLocation other = (ContactLocation) obj;
		if (address == null) {
			if (other.address != null)
				return false;
		} else if (!address.equals(other.address))
			return false;
		if (gpsLocation == null) {
			if (other.gpsLocation != null)
				return false;
		} else if (!gpsLocation.equals(other.gpsLocation))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ContactLocation [address=" + address + ", gpsLocation="
				+ gpsLocation + ", account=" + account + ", organization="
				+ organization + ", eContacts=" + eContacts + "]";
	}

}
