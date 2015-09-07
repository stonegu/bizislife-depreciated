package com.bizislife.core.exception;

import java.util.List;

public class BizisLifeBaseException extends Exception{
	private static final long serialVersionUID = -1165783567822194651L;
	
	public static final int NO_REASON = -1;
	public static final int SIGNUP_USER_CREATION_ERROR = 1;
	public static final int SIGNUP_FORM_ERROR = 2;
	public static final int SIGNUP_USERNAME_EXIST = 3;
	public static final int NO_GENERAL_ROLE = 4;

	private int reason;
	private List<String> messages;
	
	
	public BizisLifeBaseException(int reason, String message) {
		super(message);
		this.reason = reason;
	}

	public BizisLifeBaseException(int reason, String message, Throwable cause) {
		super(message, cause);
		this.reason = reason;
	}

	public BizisLifeBaseException(int reason, List<String> messages) {
		super();
		this.reason = reason;
		this.messages = messages;
	}

	public int getReason() {
		return reason;
	}
	
	public List<String> getMessages() {
		return messages;
	}

	@Override
	public String toString() {
		if (this.messages!=null && this.messages.size()>0) {
			return "BizisLifeBaseException [reason=" + reason + ", messages="
					+ messages + "]";
		} else {
			return "BizisLifeBaseException [reason=" + reason + ", message="
					+ super.getMessage() + "]";
		}
	}
}
