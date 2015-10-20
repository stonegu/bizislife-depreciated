package com.bizislife.core.hibernate.pojo;

import java.util.Date;
import java.util.UUID;

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
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="datecreated")
	private Date dateCreated;

	@PrePersist
	protected void onCreate() {
		this.dateCreated = new Date();
		this.uid = UUID.randomUUID().toString();
	}

	@Override
	public Date getDateCreated() {
		return this.dateCreated;
	}
	
	@Override
	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	@Override
	public Account getAccount() {
		return account;
	}

	@Override
	public void setAccount(Account account) {
		this.account = account;
	}

}
