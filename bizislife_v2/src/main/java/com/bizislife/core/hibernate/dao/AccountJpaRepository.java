package com.bizislife.core.hibernate.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bizislife.core.hibernate.pojo.Account;

public interface AccountJpaRepository extends JpaRepository<Account, Long>{
	List<Account> findByLoginname(String loginname);
	List<Account> findByUid(String uid);
	
}
