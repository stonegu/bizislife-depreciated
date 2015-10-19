package com.bizislife.core.hibernate.pojo;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.JoinColumn;
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
	
	@OneToOne(cascade=CascadeType.ALL)
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
