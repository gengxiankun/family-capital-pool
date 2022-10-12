package com.gengxiankun.familycapitalpool.service.impl;

import com.gengxiankun.familycapitalpool.dao.CapitalPoolDao;
import com.gengxiankun.familycapitalpool.dto.CapitalPoolWithTypeDto;
import com.gengxiankun.familycapitalpool.entity.CapitalPool;
import com.gengxiankun.familycapitalpool.service.ICapitalPoolService;
import com.gengxiankun.familycapitalpool.utils.TimeUtils;
import com.gengxiankun.familycapitalpool.vo.CapitalPoolSummaryInfoVo;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

/**
 * 资金池业务实现类
 * @author xiankun.geng
 */
@Service
public class CapitalPoolServiceImpl implements ICapitalPoolService {

    private final CapitalPoolDao capitalPoolDao;

    public CapitalPoolServiceImpl(CapitalPoolDao capitalPoolDao) {
        this.capitalPoolDao = capitalPoolDao;
    }

    @Override
    public void save(CapitalPool capitalPool) {
        this.capitalPoolDao.save(capitalPool);
    }

    @Override
    public void saveAll(List<CapitalPool> capitalPools) {
        this.capitalPoolDao.saveAll(capitalPools);
    }

    @Override
    public List<CapitalPool> findByTime(LocalDateTime time) {
        return this.capitalPoolDao.findByTime(time);
    }

    @Override
    public List<CapitalPool> findByLastTime() {
        return this.findByTime(TimeUtils.getFirstDayOfTheLastMonth());
    }

    @Override
    public boolean existsByTimeAndType(LocalDateTime time, Long capitalTypeId) {
        CapitalPool capitalPool = this.capitalPoolDao.findTimeAndType(time, capitalTypeId);
        return Optional.ofNullable(capitalPool).isPresent();
    }

    @Override
    public CapitalPool findByTimeAndType(LocalDateTime time, Long capitalTypeId) {
        return this.capitalPoolDao.findTimeAndType(time, capitalTypeId);
    }

    @Override
    public void update(CapitalPool capitalPool) {
        this.capitalPoolDao.save(capitalPool);
    }

    @Override
    public CapitalPoolSummaryInfoVo summary() {
        // 获取当前时间的所有类型资金池
        List<CapitalPoolWithTypeDto> capitalPools = this.capitalPoolDao.findByTimeWithType(TimeUtils.getFirstDayOfTheMonth());
        // 循环计算汇总信息
        Iterator<CapitalPoolWithTypeDto> capitalPoolIterator = capitalPools.iterator();
        double initialAmount = 0D;
        double borrowingAmount = 0D;
        double balance = 0D;
        while (capitalPoolIterator.hasNext()) {
            CapitalPool capitalPool = capitalPoolIterator.next();
            initialAmount += Optional.ofNullable(capitalPool.getInitialAmount()).orElse(0D);
            borrowingAmount += Optional.ofNullable(capitalPool.getBorrowingAmount()).orElse(0D);
            balance += Optional.ofNullable(capitalPool.getBalance()).orElse(0D);
        }
        // 注入 VO 模型
        return CapitalPoolSummaryInfoVo.builder()
                .initialAmount(initialAmount)
                .borrowingAmount(borrowingAmount)
                .balance(balance)
                .capitalPools(capitalPools)
                .build();
    }

    @Override
    public List<CapitalPoolWithTypeDto> findByTimeWithType(LocalDateTime time) {
        return this.capitalPoolDao.findByTimeWithType(time);
    }

}
