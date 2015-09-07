package com.bizislife.core.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bizislife.core.hibernate.pojo.Role;


@Service("userDetailService")
@Transactional
public class UserDetailService {
	
	public List<GrantedAuthority> getGrantedAuthorities(List<Role> roles) {
        final List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        if (roles!=null) {
            for (Role role : roles) {
                authorities.add(new SimpleGrantedAuthority(role.getName()));
            }
        }
        return authorities;
	}
	
	public List<GrantedAuthority> getGrantedAuthorities(Role role) {
        final List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        if (role!=null) {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        }
        return authorities;
	}
	
}
