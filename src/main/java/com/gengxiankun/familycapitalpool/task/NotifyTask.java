package com.gengxiankun.familycapitalpool.task;

import com.gengxiankun.familycapitalpool.entity.Bill;
import com.gengxiankun.familycapitalpool.service.IBillService;
import com.gengxiankun.familycapitalpool.service.ICapitalPoolService;
import com.gengxiankun.familycapitalpool.service.INotifyService;
import com.gengxiankun.familycapitalpool.utils.PushDeerUtils;
import com.gengxiankun.familycapitalpool.utils.TimeUtils;
import com.gengxiankun.familycapitalpool.vo.CapitalPoolSummaryInfoVo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 通知任务
 * @author xiankun.geng
 */
@Component
@RequiredArgsConstructor
public class NotifyTask {

    private final IBillService billService;

    private final ICapitalPoolService capitalPoolService;

    private final INotifyService notifyService;

    @Value("${notify.key}")
    private String notifyKey;

    @Scheduled(cron = "0 30 8 ? * *")
    public void daily() throws Exception {
        // 获取通知密钥
        // 获取总览信息
        CapitalPoolSummaryInfoVo summary = this.capitalPoolService.summary();
        // 获取近期账单列表
        List<Bill> bills = this.billService.findByPeriod(TimeUtils.getFirstDayOfTheMonth());
        if (bills.isEmpty()) {
            return;
        }
        // 组装消息
        String msg = this.notifyService.buildMessage(summary, bills);
        // 发送消息
        if (this.notifyKey.isEmpty()) {
            return;
        }
        PushDeerUtils.sendMarkDownMessage(notifyKey, "每日账单", msg);
    }

}
