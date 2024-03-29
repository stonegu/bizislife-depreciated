package com.bizislife.core.hibernate.pojo;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="role")
public class Role extends UIDPojo{
	
	public static enum PredefinedRoles {
		GENERAL, // this is the role for all users
	}

	@Column(name="rname")
	private String name;
	
	@Column(name="description")
	private String description;
	
	@Column(name="orguid")
	private String orgUid;

	public Role() {
		this.uid = UUID.randomUUID().toString();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getOrgUid() {
		return orgUid;
	}

	public void setOrgUid(String orgUid) {
		this.orgUid = orgUid;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((description == null) ? 0 : description.hashCode());
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
		Role other = (Role) obj;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Role [name=" + name + ", description=" + description + "]";
	}
	
}
