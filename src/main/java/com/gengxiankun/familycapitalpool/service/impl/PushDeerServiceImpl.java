package com.gengxiankun.familycapitalpool.service.impl;

import com.gengxiankun.familycapitalpool.dto.CapitalPoolWithTypeDto;
import com.gengxiankun.familycapitalpool.entity.Bill;
import com.gengxiankun.familycapitalpool.service.INotifyService;
import com.gengxiankun.familycapitalpool.vo.CapitalPoolSummaryInfoVo;
import com.github.benmanes.caffeine.cache.Cache;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * @author xiankun.geng
 */
@Service
public class PushDeerServiceImpl implements INotifyService {

    private final Cache<String, List<String>> notifyCache;

    private static final String LINE_SEPARATOR = System.getProperty("line.separator");

    public PushDeerServiceImpl(Cache notifyCache) {
        this.notifyCache = notifyCache;
    }

    @Override
    public void save(String key) {
        List<String> keys = notifyCache.asMap().get("push-deer-keys");
        if (keys == null) {
            keys = new ArrayList<>();
        }
        keys.add(key);
        notifyCache.put("push-deer-keys", keys);
    }

    @Override
    public List<String> findAll() {
        return notifyCache.asMap().get("push-deer-keys");
    }

    @Override
    public String buildMessage(CapitalPoolSummaryInfoVo summary, List<Bill> bills) {
        StringBuilder sb = new StringBuilder();
        // 构建总览数据
        sb.append("## 总览").append(LINE_SEPARATOR);
        sb.append(summary.getInitialAmount()).append("/").append(summary.getBalance());
        sb.append(" 借调: ").append(summary.getBorrowingAmount());
        sb.append(LINE_SEPARATOR).append(LINE_SEPARATOR);
        // 构建个类型的总览数据
        List<CapitalPoolWithTypeDto> capitalPools = summary.getCapitalPools();
        if (!capitalPools.isEmpty()) {
            for (CapitalPoolWithTypeDto capitalPool : capitalPools) {
                sb.append("- ").append("**" + capitalPool.getTypeName() + "：** ");
                sb.append(capitalPool.getInitialAmount()).append("/").append(capitalPool.getBalance()).append(" ");
                sb.append("借调: ").append(Optional.ofNullable(capitalPool.getBorrowingAmount())
                        .map(Objects::toString).orElse("0.0"));
                sb.append(LINE_SEPARATOR);
            }
        }
        sb.append(LINE_SEPARATOR);
        // 构建账单列表
        sb.append("## 近期账单").append(LINE_SEPARATOR);
        Iterator<Bill> billsIterator = bills.iterator();
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        while (billsIterator.hasNext()) {
            Bill bill = billsIterator.next();
            sb.append("- ").append(df.format(bill.getCreatedAt())).append(" 花费 ").append(bill.getAmount()).append("元 用于").append(bill.getCapitalType().getName()).append(LINE_SEPARATOR);
        }
        return sb.toString();
    }

}
