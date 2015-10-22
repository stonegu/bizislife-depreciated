package com.bizislife.core.hibernate.pojo;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Inheritance
@DiscriminatorColumn(name="tokenType", discriminatorType=DiscriminatorType.STRING)
@Table(name="usertoken")
public class UserToken extends UIDPojo {
	
	@Column(name="token")
	private String token;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="expirydate")
	private Date expiryDate;
	
	/*
	 * don't use CascadeType.PERSIST (or CascadeType.ALL, which includes CascadeType.PERSIST) here! 
	 * because it will try to save the child again while saving the parent and updating it with references of the child.
	 * 
	*/	
	@OneToOne(cascade={CascadeType.MERGE, CascadeType.REFRESH}, fetch=FetchType.EAGER)
	@JoinColumn(name="accountid")
	private Account account;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Date getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

}
