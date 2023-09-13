package com.lt.win.backend.service;

/**
 * @author david
 */
public interface DailyReportService {
    /**
     * 代理佣金发放
     */
    void agentCommission();

    /**
     * 代理佣金发放
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     */
    void agentCommission(Integer startTime, Integer endTime);
}
