package com.bizislife.core.controller.component;

import java.io.Serializable;

import com.bizislife.util.annotation.ValidEmail;
import com.bizislife.util.annotation.ValidPassword;

public class SignupForm implements Serializable{
	
	private static final long serialVersionUID = 3621755407542333513L;
	
	private String username;
	@ValidEmail
	private String email;
	@ValidPassword
	private String pwd;
	private String confirmpwd;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getConfirmpwd() {
		return confirmpwd;
	}
	public void setConfirmpwd(String confirmpwd) {
		this.confirmpwd = confirmpwd;
	}
	
}
