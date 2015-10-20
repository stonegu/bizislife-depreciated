package com.bizislife.core.hibernate.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bizislife.core.hibernate.pojo.BizActionLog;

public interface ActionlogJpaRepository extends JpaRepository<BizActionLog, Long>{

}
