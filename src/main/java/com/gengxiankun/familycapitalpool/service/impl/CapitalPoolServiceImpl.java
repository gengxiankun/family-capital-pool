package com.gengxiankun.familycapitalpool.service.impl;

import com.gengxiankun.familycapitalpool.dao.CapitalPoolDao;
import com.gengxiankun.familycapitalpool.dto.CapitalPoolWithTypeDto;
import com.gengxiankun.familycapitalpool.entity.Bill;
import com.gengxiankun.familycapitalpool.entity.CapitalPool;
import com.gengxiankun.familycapitalpool.entity.CapitalType;
import com.gengxiankun.familycapitalpool.service.IBillService;
import com.gengxiankun.familycapitalpool.service.ICapitalPoolService;
import com.gengxiankun.familycapitalpool.service.ICapitalTypeService;
import com.gengxiankun.familycapitalpool.utils.TimeUtils;
import com.gengxiankun.familycapitalpool.vo.CapitalPoolSummaryInfoVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
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

    private final ICapitalTypeService capitalTypeService;

    private final IBillService billService;

    public CapitalPoolServiceImpl(CapitalPoolDao capitalPoolDao, ICapitalTypeService capitalTypeService,
                                  IBillService billService) {
        this.capitalPoolDao = capitalPoolDao;
        this.capitalTypeService = capitalTypeService;
        this.billService = billService;
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
    @Transactional(rollbackFor = Exception.class)
    public void expense(Long capitalTypeId, Double amount) {
        // 匹配当期资金池
        CapitalPool capitalPool = this.findByTimeAndType(TimeUtils.getFirstDayOfTheMonth(), capitalTypeId);
        if (Optional.ofNullable(capitalPool).isEmpty()) {
            return;
        }

        // 更新资金池
        double balance = BigDecimal.valueOf(capitalPool.getBalance()).subtract(BigDecimal.valueOf(amount)).doubleValue();
        double borrowingAmount = balance < 0 ? Math.abs(balance) : 0D;
        short isBorrow = 0;
        if (balance < 0) {
            isBorrow = 1;
            capitalPool.setBalance(0D);
            capitalPool.setBorrowingAmount(BigDecimal.valueOf(Optional.ofNullable(capitalPool.getBorrowingAmount())
                    .orElse(0D)).add(BigDecimal.valueOf(borrowingAmount)).doubleValue());
        } else {
            capitalPool.setBalance(balance);
        }
        this.update(capitalPool);

        // 生成账单
        Bill bill = Bill.builder().amount(amount).borrowingAmount(borrowingAmount).isBorrow(isBorrow)
                .capitalPoolId(capitalPool.getId())
                .capitalTypeId(capitalTypeId)
                .build();
        this.billService.save(bill);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void financing() {
        // 初始化资金池
        List<CapitalType> capitalTypes = this.capitalTypeService.findAll();
        List<CapitalPool> capitalPools = capitalTypes.stream().map(t -> CapitalPool.builder()
                .capitalTypeId(t.getId())
                .balance(t.getAmount())
                .initialAmount(t.getAmount())
                .time(TimeUtils.getFirstDayOfTheMonth())
                .build()
        ).toList();
        // 移除本期已存在的资金池
        capitalPools = capitalPools.stream().filter(p ->
                !this.existsByTimeAndType(p.getTime(), p.getCapitalTypeId())).toList();

        // 获取上次资金池数据
        List<CapitalPool> lastTimeCapitalPools = this.findByLastTime();
        if (!lastTimeCapitalPools.isEmpty()) {
            capitalPools.forEach(p -> {
                // 获取上期借调金额和余额
                Optional<CapitalPool> capitalPoolByTypeOption = this.findByType(lastTimeCapitalPools, p.getCapitalTypeId());
                double lastBorrowingAmount = 0D;
                double lastBalance = 0D;
                if (capitalPoolByTypeOption.isPresent()) {
                    lastBorrowingAmount = Optional.ofNullable(capitalPoolByTypeOption.get().getBorrowingAmount()).orElse(0D);
                    lastBalance = Optional.ofNullable(capitalPoolByTypeOption.get().getBalance()).orElse(0D);
                }
                // 上期余额汇总到本月
                p.setBalance(BigDecimal.valueOf(p.getBalance()).add(BigDecimal.valueOf(lastBalance)).doubleValue());
                p.setInitialAmount(BigDecimal.valueOf(p.getInitialAmount()).add(BigDecimal.valueOf(lastBalance)).doubleValue());
                // 上期借调金额处理
                double balance = BigDecimal.valueOf(p.getBalance()).subtract(BigDecimal.valueOf(lastBorrowingAmount)).doubleValue();
                if (balance < 0) {
                    p.setBorrowingAmount(Math.abs(balance));
                    p.setInitialAmount(0D);
                    p.setBalance(0D);
                } else {
                    p.setInitialAmount(balance);
                    p.setBalance(balance);
                }
            });
        }

        // 生成资金池
        this.saveAll(capitalPools);
    }

    /**
     * 匹配指定资金类型的资金池
     * @param capitalPools 资金池列表
     * @param capitalTypeId 资金类型 ID
     * @return 资金池信息
     */
    private Optional<CapitalPool> findByType(List<CapitalPool> capitalPools, Long capitalTypeId) {
        return capitalPools.stream().filter(p -> p.getCapitalTypeId().equals(capitalTypeId)).findFirst();
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
