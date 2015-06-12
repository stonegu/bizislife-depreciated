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
@Table(name="account")
public class Account extends UIDPojo{
	@Column(name="loginname")
	private String loginname;
	
	@Column(name="pwd")
	private String pwd;
	
	@Column(name="firstname")
	private String firstname;
	
	@Column(name="lastname")
	private String lastname;
	
	@ManyToMany(mappedBy="accounts", cascade=CascadeType.ALL)
	private Collection<Organization> organizations;

	@ManyToMany(mappedBy="accounts", cascade=CascadeType.ALL)
	private Collection<Group> groups;

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name="role_account", joinColumns=@JoinColumn(name="aid"), inverseJoinColumns=@JoinColumn(name="rid"))
	private Collection<Role> roles;
	
	@OneToMany(mappedBy="account", cascade=CascadeType.ALL)
	private Collection<ContactLocation> contactLocations;
	
	public String getLoginname() {
		return loginname;
	}

	public void setLoginname(String loginname) {
		this.loginname = loginname;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public Collection<Organization> getOrganizations() {
		return organizations;
	}

	public void setOrganizations(Collection<Organization> organizations) {
		this.organizations = organizations;
	}
	
	public void addOrganization(Organization organization) {
		if (organization!=null) {
			if (organizations==null) {
				organizations = new HashSet<Organization>();
			}
			organizations.add(organization);
		}
	}

	public Collection<Group> getGroups() {
		return groups;
	}

	public void setGroups(Collection<Group> groups) {
		this.groups = groups;
	}
	
	public void addGroup(Group group) {
		if (group!=null) {
			if (this.groups==null) this.groups = new HashSet<Group>();
			this.groups.add(group);
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
		result = prime * result
				+ ((firstname == null) ? 0 : firstname.hashCode());
		result = prime * result
				+ ((lastname == null) ? 0 : lastname.hashCode());
		result = prime * result
				+ ((loginname == null) ? 0 : loginname.hashCode());
		result = prime * result + ((pwd == null) ? 0 : pwd.hashCode());
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
		Account other = (Account) obj;
		if (firstname == null) {
			if (other.firstname != null)
				return false;
		} else if (!firstname.equals(other.firstname))
			return false;
		if (lastname == null) {
			if (other.lastname != null)
				return false;
		} else if (!lastname.equals(other.lastname))
			return false;
		if (loginname == null) {
			if (other.loginname != null)
				return false;
		} else if (!loginname.equals(other.loginname))
			return false;
		if (pwd == null) {
			if (other.pwd != null)
				return false;
		} else if (!pwd.equals(other.pwd))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Account [loginname=" + loginname + ", pwd=" + pwd
				+ ", firstname=" + firstname + ", lastname=" + lastname
				+ ", organizations=" + organizations + ", groups=" + groups
				+ ", roles=" + roles + ", contactLocations=" + contactLocations
				+ "]";
	}

}
