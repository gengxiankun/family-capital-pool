package com.gengxiankun.familycapitalpool.task;

import com.gengxiankun.familycapitalpool.entity.CapitalPool;
import com.gengxiankun.familycapitalpool.entity.CapitalType;
import com.gengxiankun.familycapitalpool.service.ICapitalPoolService;
import com.gengxiankun.familycapitalpool.service.ICapitalTypeService;
import com.gengxiankun.familycapitalpool.utils.TimeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 融资定时任务
 * @author xiankun.geng
 */
@Component
public class FinancingTask {

    private static final Logger logger = LoggerFactory.getLogger(FinancingTask.class);

    private final ICapitalPoolService capitalPoolService;

    private final ICapitalTypeService capitalTypeService;

    public FinancingTask(ICapitalPoolService capitalPoolService, ICapitalTypeService capitalTypeService) {
        this.capitalPoolService = capitalPoolService;
        this.capitalTypeService = capitalTypeService;
    }

    /**
     * 融资任务
     */
    @Scheduled(cron = "0 0 2 1 * ?")
    private void financing() {
        // 初始化资金池
        List<CapitalType> capitalTypes = this.capitalTypeService.findAll();
        List<CapitalPool> capitalPools = capitalTypes.stream().map(t -> CapitalPool.builder()
                .capitalTypeId(t.getId())
                .balance(t.getAmount())
                .initialAmount(t.getAmount())
                .time(TimeUtils.getFirstDayOfTheMonth())
                .build()
        ).collect(Collectors.toList());
        // 移除本期已存在的资金池
        capitalPools = capitalPools.stream().filter(p ->
                !this.capitalPoolService.existsByTimeAndType(p.getTime(), p.getCapitalTypeId())).collect(Collectors.toList());

        // 获取上次资金池数据
        List<CapitalPool> lastTimeCapitalPools = this.capitalPoolService.findByLastTime();
        // 计算上次是否存在借调本月资金池的情况
        if (!lastTimeCapitalPools.isEmpty()) {
            capitalPools.forEach(p -> {
                Double borrowingAmount = findBorrowingAmountByType(lastTimeCapitalPools, p.getCapitalTypeId());
                if (borrowingAmount > 0) {
                    p.setInitialAmount(p.getInitialAmount() - borrowingAmount);
                    p.setBalance(p.getBalance() - borrowingAmount);
                }
            });
        }

        // 生成资金池
        this.capitalPoolService.saveAll(capitalPools);
        logger.info("[{}] 本期资金池融资完成！", TimeUtils.getFirstDayOfTheMonth());
    }

    /**
     * 匹配指定资金类型的借调金额
     * @param capitalPools  资金池列表
     * @param capitalTypeId 资金类型 ID
     * @return 借调金额
     */
    private Double findBorrowingAmountByType(List<CapitalPool> capitalPools, Long capitalTypeId) {
        Optional<CapitalPool> optional = capitalPools.stream().filter(p -> p.getCapitalTypeId().equals(capitalTypeId)).findFirst();
        if (!optional.isPresent()) {
            return 0D;
        }
        return optional.get().getBorrowingAmount();
    }

}
