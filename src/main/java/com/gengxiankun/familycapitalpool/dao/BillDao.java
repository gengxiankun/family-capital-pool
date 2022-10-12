package com.gengxiankun.familycapitalpool.dao;

import com.gengxiankun.familycapitalpool.entity.Bill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.io.Serializable;

/**
 * 账单持久层
 * @author xiankun.geng
 */
public interface BillDao extends JpaRepository<Bill, Long>, Serializable {
}
