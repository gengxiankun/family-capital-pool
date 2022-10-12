package com.gengxiankun.familycapitalpool.dao;

import com.gengxiankun.familycapitalpool.dto.CapitalPoolWithTypeDto;
import com.gengxiankun.familycapitalpool.entity.CapitalPool;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 资金池持久层
 * @author xiankun.geng
 */
public interface CapitalPoolDao extends JpaRepository<CapitalPool, Long>, Serializable {

    /**
     * 获取指定时间节点的资金池
     * @param time 时间节点
     * @return 资金池列表
     */
    @Query("select p from CapitalPool p where p.time = ?1")
    List<CapitalPool> findByTime(LocalDateTime time);

    /**
     * 获取指定时间节点和类型的资金池
     * @param time 时间节点
     * @param capitalTypeId 资金类型
     * @return 资金池
     */
    @Query(value = "select * from fcp_capital_pool where time = ?1 and capital_type_id = ?2 limit 1", nativeQuery = true)
    CapitalPool findTimeAndType(LocalDateTime time, Long capitalTypeId);

    /**
     * 获取指定时间节点的资金池（含类型信息）
     * @param time 时间节点
     * @return 资金池（含类型信息）
     */
    @Query("select new com.gengxiankun.familycapitalpool.dto.CapitalPoolWithTypeDto(p.id, p.capitalTypeId, p.time, p.balance, p.borrowingAmount, p.initialAmount, t.name) from CapitalPool p left join CapitalType t on t.id = p.capitalTypeId where p.time = ?1")
    List<CapitalPoolWithTypeDto> findByTimeWithType(LocalDateTime time);

}
