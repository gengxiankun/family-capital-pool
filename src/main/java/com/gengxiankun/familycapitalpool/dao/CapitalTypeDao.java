package com.gengxiankun.familycapitalpool.dao;

import com.gengxiankun.familycapitalpool.entity.CapitalType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.io.Serializable;

/**
 * 资金类型持久层
 * @author xiankun.geng
 */
public interface CapitalTypeDao extends JpaRepository<CapitalType, Long>, Serializable {
}
