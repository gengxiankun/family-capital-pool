package com.gengxiankun.familycapitalpool.dao;

import com.gengxiankun.familycapitalpool.entity.Bill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.io.Serializable;
import java.util.List;

/**
 * 账单持久层
 * @author xiankun.geng
 */
public interface BillDao extends JpaRepository<Bill, Long>, Serializable {

    /**
     * 通过资金池 ID 列表获取匹配的账单列表
     * @param ids 资金池 ID 列表
     * @return 账单列表
     */
    @Query("select b from Bill b where b.capitalPoolId in (?1)")
    List<Bill> findAllByCapitalPoolIDs(List<Long> ids);

}
