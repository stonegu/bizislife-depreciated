package com.bizislife.core.service.component;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class BizUser extends User{
	private String firstname;
	private String lastname;
	
	public BizUser(String username, String password,
			Collection<? extends GrantedAuthority> authorities,
			String firstname, String lastname) {
		super(username, password, authorities);
		this.firstname = firstname;
		this.lastname = lastname;
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

	@Override
	public String toString() {
		return "BizUser [firstname=" + firstname + ", lastname=" + lastname
				+ ", User =" + super.toString() + "]";
	}
	
}
