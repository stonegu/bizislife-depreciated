package com.bizislife.core.service;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

@Service
public class FakeServiceImpl implements FakeService{

	public User getFakeUser(String userName) {
		
		Collection<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
		grantedAuthorities.add(new GrantedAuthorityImpl("ROLE_USERrr"));

		User user = new User("stone", "", grantedAuthorities);
		return user;
	}

	public boolean delFakeUser(String userName) {
		
		if (StringUtils.isBlank(userName)) {
			return false;
		} else {
			return true;
		}
		
	}

}
