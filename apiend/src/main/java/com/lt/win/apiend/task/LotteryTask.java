package com.lt.win.apiend.task;

import com.lt.win.dao.generator.po.LotteryOpen;
import com.lt.win.dao.generator.po.LotteryPlate;
import com.lt.win.dao.generator.service.LotteryOpenService;
import com.lt.win.dao.generator.service.LotteryPlateService;
import com.lt.win.utils.DateUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @Auther: Jess
 * @Date: 2023/9/15 12:17
 * @Description:彩票定时任务
 */
@Slf4j
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class LotteryTask {
    private final LotteryPlateService lotteryPlateServiceImpl;
    private final LotteryOpenService lotteryOpenServiceImpl;

    /**
     * 每期间隔时间(分钟)
     **/
    private static final int INTERVAL = 5;


    public void initLotteryOpen() {
        int totalMin = 24 * 60;
        int num = totalMin / INTERVAL;
        List<Integer> list = IntStream.range(1, 11)
                .boxed()
                .collect(Collectors.toList());
        Map<Integer, String> plateMap = lotteryPlateServiceImpl.list().stream()
                .collect(Collectors.toMap(LotteryPlate::getCode, LotteryPlate::getName));
        Integer now = DateUtils.getCurrentTime();
        List<LotteryOpen> openList = new ArrayList<>();
        for (int i = 1; i <= num; i++) {
            Collections.shuffle(list);
            //生成期号
            String periodsNo = DateUtils.yyyyMMdd2(DateUtils.getCurrentTime()) + String.format("%03d", i);
            Integer openCode = list.get(0);
            LotteryOpen lotteryOpen = new LotteryOpen();
            lotteryOpen.setPeriodsNo(periodsNo);
            lotteryOpen.setLotteryCode("pk10");
            lotteryOpen.setLotteryName("pk10");
            lotteryOpen.setOpenCode(openCode);
            lotteryOpen.setOpenName(plateMap.get(openCode));
            lotteryOpen.setOpenAllCode(StringUtils.join(list, ","));
            lotteryOpen.setCreatedAt(now);
            lotteryOpen.setUpdatedAt(now);
            openList.add(lotteryOpen);
        }
        lotteryOpenServiceImpl.saveBatch(openList);
    }
}
