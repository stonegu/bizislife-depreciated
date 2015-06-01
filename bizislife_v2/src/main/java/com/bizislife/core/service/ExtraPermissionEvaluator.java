package com.bizislife.core.service;

import java.io.Serializable;

import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.util.Assert;

public class ExtraPermissionEvaluator implements PermissionEvaluator{

	public boolean hasPermission(Authentication authentication,
			Object targetDomainObject, Object permission) {
		// TODO Auto-generated method stub
//		Assert.isTrue(authentication.getName().equals("aaa"));
//		Assert.notEmpty(authentication.getAuthorities());
//		Assert.isTrue(authentication.getAuthorities().iterator().next().getAuthority().equals("ROLE_USERrr"));
//		Assert.isTrue(authentication.getCredentials().getClass().equals(String.class));
//		Assert.isTrue(((String)authentication.getCredentials()).equals("N/A"));
//		Assert.isTrue(authentication.getPrincipal().getClass().equals(User.class));
//		Assert.isTrue(((User)authentication.getPrincipal()).getUsername().equals("aaa"));
		
		// targetDomainObject is "loginUserName" from hasPermission(#loginUserName, 'owner')
		//Assert.isTrue(((String)targetDomainObject).equals("aaa"));
		// permission is owner from hasPermission(#loginUserName, 'owner')
		
		
		
		return true;
	}

	public boolean hasPermission(Authentication authentication,
			Serializable targetId, String targetType, Object permission) {
		// TODO Auto-generated method stub
		return false;
	}

}
