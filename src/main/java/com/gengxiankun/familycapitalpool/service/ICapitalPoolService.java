package com.gengxiankun.familycapitalpool.service;

import com.gengxiankun.familycapitalpool.dto.CapitalPoolWithTypeDto;
import com.gengxiankun.familycapitalpool.entity.CapitalPool;
import com.gengxiankun.familycapitalpool.vo.CapitalPoolSummaryInfoVo;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author xiankun.geng
 */
public interface ICapitalPoolService {

    /**
     * 获取指定时间节点的资金池
     * @param time 时间节点
     * @return 资金池
     */
    List<CapitalPool> findByTime(LocalDateTime time);

    /**
     * 获取上次的资金池
     * @return 资金池
     */
    List<CapitalPool> findByLastTime();

    /**
     * 指定时间节点和资金类型的资金池是否存在
     * @param time 时间节点
     * @param type 资金类型
     * @return 是否存在
     */
    boolean existsByTimeAndType(LocalDateTime time, Long type);

    /**
     * 获取指定时间节点和资金类型的资金池
     * @param time 时间节点
     * @param capitalTypeId 资金类型 ID
     * @return 资金池
     */
    CapitalPool findByTimeAndType(LocalDateTime time, Long capitalTypeId);

    /**
     * 生成新的资金池
     * @param capitalPool 资金池
     */
    void save(CapitalPool capitalPool);

    /**
     * 批量生成资金池
     * @param capitalPools 资金池列表
     */
    void saveAll(List<CapitalPool> capitalPools);

    /**
     * 更新资金池
     * @param capitalPool 更新内容
     */
    void update(CapitalPool capitalPool);

    /**
     * 汇总当前时间的资金池信息
     * @return 资金池汇总信息
     */
    CapitalPoolSummaryInfoVo summary();

    /**
     * 获取指定时间节点的资金池（含类型信息）
     * @param time 时间节点
     * @return 资金池（含类型信息）
     */
    List<CapitalPoolWithTypeDto> findByTimeWithType(LocalDateTime time);

}
