package com.gengxiankun.familycapitalpool.service;

import com.gengxiankun.familycapitalpool.entity.CapitalType;

import java.util.List;
import java.util.Optional;

/**
 * @author xiankun.geng
 */
public interface ICapitalTypeService {

    /**
     * 获取全部资金类型
     * @return 资金类型列表
     */
    List<CapitalType> findAll();

    /**
     * 根据 ID 获取资金类型信息
     * @param id 资金类型 ID
     * @return 资金类型信息
     */
    Optional<CapitalType> findById(Long id);

    /**
     * 通过 ID 判断类型是否存在
     * @param id 类型 ID
     * @return 是否存在
     */
    boolean existsById(Long id);

    /**
     * 新增资金类型
     * @param capitalType 资金类型
     */
    void save(CapitalType capitalType);

}
