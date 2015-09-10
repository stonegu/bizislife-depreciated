package com.bizislife.core.hibernate.pojo;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Inheritance
@DiscriminatorColumn(name="logType", discriminatorType=DiscriminatorType.STRING)
@Table(name="bizactionlog")
public class BizActionLog extends UIDPojo implements ActionLog{
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="accountid")
	private Account account;
	
	@Column(name="route")
	private String route;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="datecreated")
	private Date dateCreated;

	@Override
	public Date getDateCreated() {
		return this.dateCreated;
	}
	
	@PrePersist
	protected void onCreate() {
		this.dateCreated = new Date();
	}

	@Override
	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	@Override
	public String getRoute() {
		return this.route;
	}

	@Override
	public void setRoute(String route) {
		this.route = route;
	}

}
