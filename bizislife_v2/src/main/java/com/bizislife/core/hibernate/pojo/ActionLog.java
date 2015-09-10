package com.bizislife.core.hibernate.pojo;

import java.util.Date;

public interface ActionLog {
	public Date getDateCreated();
	public void setDateCreated(Date dateCreated);
	public String getRoute();
	public void setRoute(String route);
}
