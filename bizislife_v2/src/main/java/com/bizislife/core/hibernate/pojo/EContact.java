package com.bizislife.core.hibernate.pojo;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="econtact")
public class EContact extends UIDPojo{
	
	@Column(name="contacttype")
	private String contactType;

	@Column(name="contactvalue")
	private String contactValue;
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="contactlocationid")
	private ContactLocation contactLocation;
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="accountid")
	private Account account;
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="organizationid")
	private Organization organization;

	public ContactType getContactType() {
		if (this.contactType!=null) {
			return ContactType.getContactType(this.contactType);
		}
		return null;
	}

	public void setContactType(ContactType contactType) {
		this.contactType = contactType.code;
	}

	public String getContactValue() {
		return contactValue;
	}

	public void setContactValue(String contactValue) {
		this.contactValue = contactValue;
	}

	public ContactLocation getContactLocation() {
		return contactLocation;
	}

	public void setContactLocation(ContactLocation contactLocation) {
		this.contactLocation = contactLocation;
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
	
	public boolean belongToUuid(String uuid) {
		if (uuid!=null) {
			return (this.account!=null?this.account.getUid().equals(uuid):false)
					|| (this.organization!=null?this.organization.getUid().equals(uuid):false)
							|| (this.contactLocation!=null?this.contactLocation.getUid().equals(uuid):false)
									|| ((this.contactLocation!=null && this.contactLocation.getAccount()!=null)?
											this.contactLocation.getAccount().getUid().equals(uuid):false)
											|| ((this.contactLocation!=null && this.contactLocation.getOrganization()!=null)?
													this.contactLocation.getOrganization().getUid().equals(uuid):false);
		}
		return false;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((contactType == null) ? 0 : contactType.hashCode());
		result = prime * result
				+ ((contactValue == null) ? 0 : contactValue.hashCode());
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
		EContact other = (EContact) obj;
		if (contactType == null) {
			if (other.contactType != null)
				return false;
		} else if (!contactType.equals(other.contactType))
			return false;
		if (contactValue == null) {
			if (other.contactValue != null)
				return false;
		} else if (!contactValue.equals(other.contactValue))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "EContact [contactType=" + contactType + ", contactValue="
				+ contactValue + ", contactLocation=" + contactLocation + "]";
	}

	public static enum ContactType {
		Email("email"),
		HomeEmail("email_home"),
		WorkEmail("email_work"),
		Phone("phone"),
		Mobile("phone_mobile"),
		HomePhone("phone_home"),
		WorkPhone("phone_work"),
		Fax("fax"),
		WorkFax("fax_work"),
		HomeFax("fax_home"),
		;
		
		private String code;

		private ContactType(String code) {
			this.code = code;
		}

		public String getCode() {
			return code;
		}
		
		public static ContactType getContactType(String code) {
			if (code!=null) {
				for (ContactType contactType : ContactType.values()) {
					if (contactType.getCode().equals(code)) {
						return contactType;
					}
				}
			}
			return null;
		}
	}

}
