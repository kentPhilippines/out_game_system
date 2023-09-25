package com.lt.win.apiend.task;


import com.lt.win.service.base.LotteryCommBase;
import com.lt.win.service.cache.redis.LotteryCache;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @Auther: Jess
 * @Date: 2023/9/15 12:17
 * @Description:彩票定时任务
 */
@Slf4j
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class LotteryTask {
    private final LotteryCache lotteryCache;
    private final LotteryCommBase lotteryCommBase;


    /**
     * @Description 生成随机的开奖号码
     * @Param []
     **/
    @Scheduled(cron = "51 */5 * * * ?")
    public void initFirst() {
        String periodsNo = lotteryCache.getCurrPeriodsNo();
        log.info("开始首次生产开奖号码：{}", periodsNo);
        lotteryCommBase.initLotteryOpen();
    }

    /**
     * @Description 生成随机的开奖号码
     * @Param []
     **/
    @Scheduled(cron = "55 */5 * * * ?")
    public void initSecond() {
        String periodsNo = lotteryCache.getCurrPeriodsNo();
        log.info("开始二次生成开奖号码：{}", periodsNo);
        lotteryCommBase.initLotteryOpen();
    }



    /**
     * @Description 结算
     * @Param []
     **/
    @Scheduled(cron = "1 */5 * * * ?")
    public void settleFirst() {
        String periodsNo = lotteryCache.getUpPeriodsNo();
        log.info("开始首次结算：{}", periodsNo);
        lotteryCommBase.settle();
    }


    /**
     * @Description 二次结算，防止第一次未结算完
     * @Param []
     **/
    @Scheduled(cron = "5 */5 * * * ?")
    public void settleMore() {
        String periodsNo = lotteryCache.getUpPeriodsNo();
        log.info("开始二次结算：{}", periodsNo);
        lotteryCommBase.settle();
    }


}
