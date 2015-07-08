package com.bizislife.core.hibernate.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bizislife.core.hibernate.pojo.EContact;

public interface EContactJpaRepository extends JpaRepository<EContact, Long>{

}
