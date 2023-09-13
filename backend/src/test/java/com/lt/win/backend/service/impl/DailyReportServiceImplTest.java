package com.lt.win.backend.service.impl;

import com.lt.win.backend.BackendApplication;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BackendApplication.class)
class DailyReportServiceImplTest {
    @Resource
    DailyReportServiceImpl commissionServiceImpl;

    @Test
    void agentCommission() {
        commissionServiceImpl.agentCommission();
        log.info("aaaaa");

    }
}