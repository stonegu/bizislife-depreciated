package com.bizislife.core.hibernate.pojo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("sgin")
public class SigninLog extends BizActionLog{
	
	@Column(name="ip")
	private String ip;
	
	@Column(name="dateLogout")
	private Date dateLogout;

	@Column(name="logoutType")
	private String logoutType;
	
	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Date getDateLogout() {
		return dateLogout;
	}

	public void setDateLogout(Date dateLogout) {
		this.dateLogout = dateLogout;
	}

	public LogoutType getLogoutType() {
		return LogoutType.valueOf(this.logoutType);
	}

	public void setLogoutType(String logoutType) {
		this.logoutType = logoutType;
	}

	public void setLogoutType(LogoutType logoutType) {
		this.logoutType = logoutType.name();
	}
	
	public static enum LogoutType {
		signout, timeout, other
	}
	
}
