package com.bizislife.core.hibernate.pojo;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.bizislife.core.controller.component.ApiResponse;
import com.bizislife.core.hibernate.pojo.EContact.ContactType;

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
	
	@Column(name="preferlocale")
	private String preferlocale;
	
	@ManyToMany(mappedBy="accounts", cascade=CascadeType.ALL)
	private Collection<Organization> organizations;

	@ManyToMany(mappedBy="accounts", cascade=CascadeType.ALL)
	private Collection<Group> groups;

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name="role_account", joinColumns=@JoinColumn(name="aid"), inverseJoinColumns=@JoinColumn(name="rid"))
	private Collection<Role> roles;
	
	@OneToMany(mappedBy="account", cascade=CascadeType.ALL, orphanRemoval=true)
	private Collection<ContactLocation> contactLocations;
	
	@OneToMany(mappedBy="account", cascade=CascadeType.ALL)
	private Collection<EContact> eContacts;
	
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

	public Locale getPreferlocale() {
		Locale locale = null;
		if (this.preferlocale!=null) {
			locale = new Locale(this.preferlocale);
		}
		return locale;
	}

	public void setPreferlocale(String preferlocale) {
		this.preferlocale = preferlocale;
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
	 * 		true if eContact added or created relationship with Account, <br/> 
	 * 		false if same eContact exist and already has relationship with Account.
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
				eContact.setAccount(this);
				this.eContacts.add(eContact);
				return true;
			} else {
				if (exist.getAccount()==null) { // create relationship only
					if (this.eContacts==null) this.eContacts = new HashSet<>();
					exist.setAccount(this);
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
					if (delItem.getAccount()!=null) {
						delItem.setAccount(null);
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
					if (detachItem.getAccount()!=null) {
						detachItem.setAccount(null);
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
//		if (!super.equals(obj))
//			return false;
		if (obj == null)
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
				+ ", preferlocale=" + preferlocale + ", organizations="
				+ organizations + ", groups=" + groups + ", roles=" + roles
				+ ", contactLocations=" + contactLocations + ", eContacts="
				+ eContacts + "]";
	}

}
