package com.lt.win.apiend.service.impl;

import com.lt.win.apiend.io.dto.lottery.LotteryParams;
import com.lt.win.apiend.service.LotteryService;
import org.springframework.stereotype.Service;

/**
 * @Auther: Jess
 * @Date: 2023/9/14 20:42
 * @Description:
 */
@Service
public class LotteryServiceImpl implements LotteryService {
    /**
     * @return com.lt.win.apiend.io.dto.lottery.LotteryParams.LotteryInfoRep
     * @Description 查询期数信息
     * @Param []
     **/
    @Override
    public LotteryParams.LotteryInfoRep queryLotteryInfo() {
        return null;
    }

    /**
     * @return com.lt.win.apiend.io.dto.lottery.LotteryParams.LotteryResultRep
     * @Description 查询开奖结果
     * @Param []
     **/
    @Override
    public LotteryParams.LotteryResultRep queryLotteryResult() {
        return null;
    }

    /**
     * @param res
     * @return com.lt.win.apiend.io.dto.lottery.LotteryParams.BetRep
     * @Description 投注
     * @Param [res]
     */
    @Override
    public LotteryParams.BetRep bet(LotteryParams.BetRes res) {
        return null;
    }
}
