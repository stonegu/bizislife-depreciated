package com.bizislife.core.hibernate.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bizislife.core.hibernate.pojo.Role;

public interface RoleJpaRepository extends JpaRepository<Role, Long>{
	List<Role> findByNameAndOrgUid(String rname, String orguid);

}
