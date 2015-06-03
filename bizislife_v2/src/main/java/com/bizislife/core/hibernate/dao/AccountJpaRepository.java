package com.bizislife.core.hibernate.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bizislife.core.hibernate.pojo.Account;

public interface AccountJpaRepository extends JpaRepository<Account, Long>{

}
