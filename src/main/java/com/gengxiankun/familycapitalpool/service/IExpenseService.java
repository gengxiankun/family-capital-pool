package com.gengxiankun.familycapitalpool.service;

/**
 * @author xiankun.geng
 */
public interface IExpenseService {

    /**
     * 支出
     *
     * @param capitalTypeId 资金类型
     * @param amount 金额
     * @param isBorrow 是否借调
     */
    void expense(Long capitalTypeId, Double amount, Short isBorrow);

}
