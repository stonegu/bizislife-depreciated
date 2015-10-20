package com.bizislife.core.hibernate.pojo;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("email")
public class EmailLog extends BizActionLog{
	
	@Column(name="emailtype")
	private String emailtype;

	@Column(name="sendto")
	private String sendto;

	@Column(name="ccto")
	private String ccto;

	@Column(name="template")
	private String template;

	public String getEmailtype() {
		return emailtype;
	}

	public void setEmailtype(String emailtype) {
		this.emailtype = emailtype;
	}

	public String getSendto() {
		return sendto;
	}

	public void setSendto(String sendto) {
		this.sendto = sendto;
	}

	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}

}
