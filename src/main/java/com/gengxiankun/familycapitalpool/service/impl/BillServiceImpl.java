package com.gengxiankun.familycapitalpool.service.impl;

import com.gengxiankun.familycapitalpool.dao.BillDao;
import com.gengxiankun.familycapitalpool.entity.Bill;
import com.gengxiankun.familycapitalpool.service.IBillService;
import org.springframework.stereotype.Service;

/**
 * 账单业务逻辑类
 * @author xiankun.geng
 */
@Service
public class BillServiceImpl implements IBillService {

    private final BillDao billDao;

    public BillServiceImpl(BillDao billDao) {
        this.billDao = billDao;
    }

    @Override
    public void save(Bill bill) {
        this.billDao.save(bill);
    }
}
