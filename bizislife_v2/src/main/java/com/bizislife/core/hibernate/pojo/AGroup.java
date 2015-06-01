package com.bizislife.core.hibernate.pojo;

import java.util.Collection;
import java.util.HashSet;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name="agroup")
public class AGroup extends UIDPojo{

	@Column(name="gname")
	private String gname;
	
	@Column(name="description")
	private String description;

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name="account_group", joinColumns=@JoinColumn(name="gid"), inverseJoinColumns=@JoinColumn(name="aid"))
	private Collection<Account> accounts;

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name="role_group", joinColumns=@JoinColumn(name="gid"), inverseJoinColumns=@JoinColumn(name="rid"))
	private Collection<Role> roles;

	public String getGname() {
		return gname;
	}

	public void setGname(String gname) {
		this.gname = gname;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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
	
	public Collection<Role> getRoles() {
		return roles;
	}

	public void setRoles(Collection<Role> roles) {
		this.roles = roles;
	}
	
	public void addRole(Role role) {
		if (role!=null) {
			if (this.roles==null) this.roles = new HashSet<Role>();
			this.roles.add(role);
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((gname == null) ? 0 : gname.hashCode());
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
		AGroup other = (AGroup) obj;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (gname == null) {
			if (other.gname != null)
				return false;
		} else if (!gname.equals(other.gname))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "AGroup [gname=" + gname + ", description=" + description
				+ ", accounts=" + accounts + ", roles=" + roles + "]";
	}

}
