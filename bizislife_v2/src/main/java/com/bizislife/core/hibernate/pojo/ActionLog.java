package com.bizislife.core.hibernate.pojo;

import java.util.Date;

public interface ActionLog {
	public Date getDateCreated();
	public void setDateCreated(Date dateCreated);
	public Account getAccount();
	public void setAccount(Account account);
}
