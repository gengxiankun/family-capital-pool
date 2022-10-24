package com.gengxiankun.familycapitalpool.service;

import com.gengxiankun.familycapitalpool.entity.Bill;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author xiankun.geng
 */
public interface IBillService {

    /**
     * 生成账单
     * @param bill 账单信息
     */
    void save(Bill bill);

    /**
     * 通过资金池 ID 列表获取匹配的账单列表
     * @param ids 资金池 ID 列表
     * @return 账单列表
     */
    List<Bill> findAllByCapitalPoolIDs(List<Long> ids);

    /**
     * 获取指定期次的账单列表
     * @param time 期次时间节点
     * @return 账单列表
     */
    List<Bill> findByPeriod(LocalDateTime time);

}
