package com.bizislife.core.hibernate.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bizislife.core.hibernate.pojo.Organization;

public interface OrgJpaRepository extends JpaRepository<Organization, Long>{

}
