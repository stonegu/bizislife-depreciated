package com.bizislife.core.service;

import org.springframework.security.core.userdetails.User;

public interface TokenService {
	public  String createVerificationTokenforUser(User user);
}
