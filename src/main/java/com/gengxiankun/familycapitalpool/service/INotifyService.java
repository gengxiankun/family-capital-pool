package com.gengxiankun.familycapitalpool.service;

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

}
