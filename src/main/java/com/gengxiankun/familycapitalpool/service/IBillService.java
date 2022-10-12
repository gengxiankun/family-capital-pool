package com.gengxiankun.familycapitalpool.service;

import com.gengxiankun.familycapitalpool.entity.Bill;

/**
 * @author xiankun.geng
 */
public interface IBillService {

    /**
     * 生成账单
     * @param bill 账单信息
     */
    void save(Bill bill);

}
