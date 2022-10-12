package com.gengxiankun.familycapitalpool.utils;

import java.time.*;
import java.time.temporal.TemporalAdjusters;

/**
 * 时间处理工具
 * @author xiankun.geng
 */
public class TimeUtils {

    private TimeUtils() {
    }

    /**
     * 获取本月第一天
     */
    public static LocalDateTime getFirstDayOfTheMonth() {
        return LocalDateTime.of(LocalDate.from(LocalDateTime.now().with(TemporalAdjusters.firstDayOfMonth())), LocalTime.MIN);
    }

    /**
     * 获取上个月第一天
     */
    public static LocalDateTime getFirstDayOfTheLastMonth() {
        return LocalDateTime.of(LocalDate.from(LocalDateTime.now().minusMonths(1).with(TemporalAdjusters.firstDayOfMonth())), LocalTime.MIN);
    }

}
