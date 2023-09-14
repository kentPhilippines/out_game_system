package com.lt.win.apiend.controller;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSort;
import com.lt.win.apiend.aop.annotation.UnCheckToken;
import com.lt.win.apiend.io.dto.lottery.LotteryParams;
import com.lt.win.apiend.service.LotteryService;
import com.lt.win.utils.components.response.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


/**
 * @Auther: Jess
 * @Date: 2023/9/14 20:14
 * @Description: 彩票
 */
@Slf4j
@RestController
@Api(tags = " 彩票")
@ApiSort(2)
@RequestMapping("/v1/lottery")
public class LotteryController {
    private LotteryService lotteryServiceImpl;

    @UnCheckToken
    @PostMapping(value = "/queryLotteryInfo")
    @ApiOperation(value = "查询期数信息", notes = "查询期数信息")
    @ApiOperationSupport(author = "jess", order = 1)
    public Result<LotteryParams.LotteryInfoRep> queryLotteryInfo() {
        return Result.ok(lotteryServiceImpl.queryLotteryInfo());
    }

    @UnCheckToken
    @PostMapping(value = "/queryLotteryResult")
    @ApiOperation(value = "查询开奖结果", notes = "查询开奖结果")
    @ApiOperationSupport(author = "jess", order = 2)
    public Result<LotteryParams.LotteryResultRep> queryLotteryResult() {
        return Result.ok(lotteryServiceImpl.queryLotteryResult());
    }


    @PostMapping(value = "/bet")
    @ApiOperation(value = "投注", notes = "投注")
    @ApiOperationSupport(author = "jess", order = 3)
    public Result<LotteryParams.BetRep> bet(@Valid @RequestBody LotteryParams.BetRes res) {
        return Result.ok(lotteryServiceImpl.bet(res));
    }

}
