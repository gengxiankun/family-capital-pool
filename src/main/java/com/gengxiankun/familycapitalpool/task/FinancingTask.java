package com.gengxiankun.familycapitalpool.task;

import com.gengxiankun.familycapitalpool.service.ICapitalPoolService;
import com.gengxiankun.familycapitalpool.service.ICapitalTypeService;
import com.gengxiankun.familycapitalpool.utils.TimeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 融资定时任务
 * @author xiankun.geng
 */
@Component
public class FinancingTask {

    private static final Logger logger = LoggerFactory.getLogger(FinancingTask.class);

    private final ICapitalPoolService capitalPoolService;

    public FinancingTask(ICapitalPoolService capitalPoolService) {
        this.capitalPoolService = capitalPoolService;
    }

    /**
     * 融资任务
     */
    @Scheduled(cron = "0 0 2 1 1/1 ?")
    private void financing() {
        this.capitalPoolService.financing();
        logger.info("{} 期融资成功！", TimeUtils.getFirstDayOfTheMonth());
    }

}
