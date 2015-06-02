package com.bizislife.core.hibernate.pojo;

import java.util.Collection;
import java.util.HashSet;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
