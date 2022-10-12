package com.gengxiankun.familycapitalpool.service.impl;

import com.gengxiankun.familycapitalpool.entity.Bill;
import com.gengxiankun.familycapitalpool.entity.CapitalPool;
import com.gengxiankun.familycapitalpool.entity.CapitalType;
import com.gengxiankun.familycapitalpool.service.IBillService;
import com.gengxiankun.familycapitalpool.service.ICapitalPoolService;
import com.gengxiankun.familycapitalpool.service.ICapitalTypeService;
import com.gengxiankun.familycapitalpool.service.IExpenseService;
import com.gengxiankun.familycapitalpool.utils.TimeUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

/**
 * @author xiankun.geng
 */
@Service
public class ExpenseServiceImpl implements IExpenseService {

    private final ICapitalTypeService capitalTypeService;

    private final ICapitalPoolService capitalPoolService;

    private final IBillService billService;

    public ExpenseServiceImpl(ICapitalTypeService capitalTypeService, ICapitalPoolService capitalPoolService,
            IBillService billService) {
        this.capitalTypeService = capitalTypeService;
        this.capitalPoolService = capitalPoolService;
        this.billService = billService;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void expense(Long capitalTypeId, Double amount, Short isBorrow) {
        // 资金类型是否存在
        Optional<CapitalType> capitalTypeOptional = this.capitalTypeService.findById(capitalTypeId);
        if (!capitalTypeOptional.isPresent()) {
            return;
        }

        // 资金金额是否超出
        CapitalPool capitalPool = this.capitalPoolService.findByTimeAndType(TimeUtils.getFirstDayOfTheMonth(), capitalTypeId);
        Double availableBalance = capitalPool.getBalance();
        if (isBorrow != null && isBorrow > 0) {
            availableBalance = BigDecimal.valueOf(availableBalance).add(BigDecimal.valueOf(capitalTypeOptional.get().getAmount())).doubleValue();
        }
        if (amount.compareTo(availableBalance) > 0) {
            return;
        }

        // 更新资金池
        boolean isNeedBorrow = amount.compareTo(capitalPool.getBalance()) > 0;
        double borrowingAmount = 0D;
        if (isNeedBorrow) {
            capitalPool.setBalance(0D);
            borrowingAmount = BigDecimal.valueOf(amount).subtract(BigDecimal.valueOf(capitalPool.getBalance())).doubleValue();
            capitalPool.setBorrowingAmount(BigDecimal.valueOf(Optional.ofNullable(capitalPool.getBorrowingAmount())
                    .orElse(0D)).add(BigDecimal.valueOf(borrowingAmount)).doubleValue());
        } else {
            capitalPool.setBalance(BigDecimal.valueOf(capitalPool.getBalance()).subtract(BigDecimal.valueOf(amount))
                    .doubleValue());
        }
        this.capitalPoolService.update(capitalPool);

        // 生成账单
        Bill bill = Bill.builder().amount(amount).borrowingAmount(borrowingAmount).isBorrow(isBorrow)
                .capitalPoolId(capitalPool.getId())
                .capitalTypeId(capitalTypeId)
                .build();
        this.billService.save(bill);
    }

}
