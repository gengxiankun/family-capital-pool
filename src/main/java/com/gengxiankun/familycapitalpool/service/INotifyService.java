package com.gengxiankun.familycapitalpool.service;

import com.gengxiankun.familycapitalpool.entity.Bill;
import com.gengxiankun.familycapitalpool.vo.CapitalPoolSummaryInfoVo;

import java.util.List;

/**
 * @author xiankun.geng
 */
public interface INotifyService {

    /**
     * 记录 notify
     * @param notify notify
     */
    void save(String notify);

    /**
     * 获取 notify 列表
     * @return notify 列表
     */
    List<String> findAll();

    /**
     * 构建通知消息
     * @param summary 汇总信息
     * @param bills 账单列表
     * @return 通知消息
     */
    String buildMessage(CapitalPoolSummaryInfoVo summary, List<Bill> bills);

}
