package com.lt.win.backend.job;

import com.lt.win.backend.service.DailyReportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 代理佣金发放
 *
 * @author: David
 * @date: 2022/10/29
 */
@Slf4j
@Component
@EnableScheduling
public class DailyReportJob {
    @Resource
    private DailyReportService dailyReportServiceImpl;

    /**
     * 代理佣金发放
     */
    @Scheduled(cron = "0 0 1 * * ?")
    public void agentCommission() {
        dailyReportServiceImpl.agentCommission();
    }
}
