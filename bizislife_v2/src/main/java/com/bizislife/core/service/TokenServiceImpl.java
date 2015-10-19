package com.bizislife.core.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bizislife.core.hibernate.dao.AccountJpaRepository;
import com.bizislife.core.hibernate.dao.UserTokenJpaRepository;
import com.bizislife.core.hibernate.pojo.Account;
import com.bizislife.core.hibernate.pojo.VerificationToken;
import com.bizislife.core.service.component.BizUser;

@Service
public class TokenServiceImpl implements TokenService {
	
	private static final int EXPIRATION = 60 * 24;

	@Autowired
	AccountJpaRepository accountJpaRepository;
	
	@Autowired
	UserTokenJpaRepository userTokenJpaRepository;
	
	@Override
	@Transactional
	public String createVerificationTokenforUser(User user) {
		if (user!=null) {
			List<Account> accounts = accountJpaRepository.findByLoginname(user.getUsername());
			if (accounts!=null && accounts.size()>0) {
				final String token = UUID.randomUUID().toString();
				VerificationToken verificationToken = new VerificationToken();
				verificationToken.setAccount(accounts.get(0));
				verificationToken.setExpiryDate(calculateExpiryDate(EXPIRATION));
				verificationToken.setToken(token);
				verificationToken.setUid(UUID.randomUUID().toString());
				
				verificationToken = userTokenJpaRepository.save(verificationToken);
				if (verificationToken.getId()!=null) {
					return token;
				}
			}
		}
		return null;
	}
	
	
	
	private Date calculateExpiryDate(final int expiryTimeInMinutes) {
		final Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(new Date().getTime());
		cal.add(Calendar.MINUTE, expiryTimeInMinutes);
		return new Date(cal.getTime().getTime());
	}

}
