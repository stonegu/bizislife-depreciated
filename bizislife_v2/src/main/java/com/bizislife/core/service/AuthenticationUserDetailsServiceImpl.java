package com.bizislife.core.service;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

public class AuthenticationUserDetailsServiceImpl implements AuthenticationUserDetailsService<PreAuthenticatedAuthenticationToken>, InitializingBean{

	public UserDetails loadUserDetails(PreAuthenticatedAuthenticationToken token)
			throws UsernameNotFoundException {
		
		
		Object loginUser = token.getPrincipal();
//		return null;
		
		
//		System.out.println("username recieved :: " + username);
		
		Collection<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
		grantedAuthorities.add(new GrantedAuthorityImpl("ROLE_NORMALr"));
		
//		@SuppressWarnings("deprecation")
		UserDetails user = new User("aaa", "password", true, true, true, true,
				grantedAuthorities);
		
		UserDetails user1 = new User("aaa", "", true, true, true, true, grantedAuthorities);
		
		return user1;
		
		
		
	}

	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub
		
	}

}
