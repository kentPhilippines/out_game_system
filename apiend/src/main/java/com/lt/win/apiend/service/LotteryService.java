package com.lt.win.apiend.service;

import com.lt.win.apiend.io.dto.lottery.LotteryParams;
import com.lt.win.utils.components.response.Result;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

/**
 * @Auther: Jess
 * @Date: 2023/9/14 20:41
 * @Description:
 */
public interface LotteryService {

    /**
     * @return com.lt.win.apiend.io.dto.lottery.LotteryParams.LotteryInfoRep
     * @Description 查询期数信息
     * @Param []
     **/
    LotteryParams.LotteryInfoRep queryLotteryInfo();

    /**
     * @return com.lt.win.apiend.io.dto.lottery.LotteryParams.LotteryResultRep
     * @Description 查询开奖结果
     * @Param []
     **/
    LotteryParams.LotteryResultRep queryLotteryResult();

    /**
     * @return com.lt.win.apiend.io.dto.lottery.LotteryParams.BetRep
     * @Description 投注
     * @Param [res]
     **/
    LotteryParams.BetRep bet(LotteryParams.BetRes res);
}
