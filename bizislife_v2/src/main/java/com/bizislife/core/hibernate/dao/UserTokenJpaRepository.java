package com.bizislife.core.hibernate.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bizislife.core.hibernate.pojo.UserToken;

public interface UserTokenJpaRepository extends JpaRepository<UserToken, Long>{

}
