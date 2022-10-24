package com.gengxiankun.familycapitalpool.service.impl;

import com.gengxiankun.familycapitalpool.dao.BillDao;
import com.gengxiankun.familycapitalpool.entity.Bill;
import com.gengxiankun.familycapitalpool.service.IBillService;
import com.gengxiankun.familycapitalpool.service.ICapitalPoolService;
import com.gengxiankun.familycapitalpool.utils.TimeUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

/**
 * 账单业务逻辑类
 * @author xiankun.geng
 */
@Service
public class BillServiceImpl implements IBillService {

    private final BillDao billDao;

    private final ICapitalPoolService capitalPoolService;

    public BillServiceImpl(BillDao billDao, @Lazy ICapitalPoolService capitalPoolService) {
        this.billDao = billDao;
        this.capitalPoolService = capitalPoolService;
    }

    @Override
    public void save(Bill bill) {
        this.billDao.save(bill);
    }

    @Override
    public List<Bill> findAllByCapitalPoolIDs(List<Long> ids) {
        return this.billDao.findAllByCapitalPoolIDs(ids);
    }

    @Override
    public List<Bill> findByPeriod(LocalDateTime time) {
        List<Long> capitalPoolIds = this.capitalPoolService.findIdByTime(TimeUtils.getFirstDayOfTheMonth());
        if (capitalPoolIds.isEmpty()) {
            return Collections.emptyList();
        }
        return this.findAllByCapitalPoolIDs(capitalPoolIds);
    }

}
