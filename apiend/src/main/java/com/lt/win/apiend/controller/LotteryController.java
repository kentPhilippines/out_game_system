package com.lt.win.apiend.controller;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSort;
import com.lt.win.apiend.aop.annotation.UnCheckToken;
import com.lt.win.apiend.io.dto.lottery.LotteryParams;
import com.lt.win.apiend.service.LotteryService;
import com.lt.win.utils.components.pagination.ReqPage;
import com.lt.win.utils.components.pagination.ResPage;
import com.lt.win.utils.components.response.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;


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
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class LotteryController {
    private final LotteryService lotteryServiceImpl;

    @UnCheckToken
    @PostMapping(value = "/queryLotteryInfo")
    @ApiOperation(value = "查询期数信息", notes = "查询期数信息")
    @ApiOperationSupport(author = "jess", order = 1)
    public Result<LotteryParams.LotteryInfoRes> queryLotteryInfo(@Valid @RequestBody LotteryParams.LotteryInfoReq res) {
        return Result.ok(lotteryServiceImpl.queryLotteryInfo(res));
    }

    @UnCheckToken
    @PostMapping(value = "/queryLotteryResult")
    @ApiOperation(value = "查询开奖结果", notes = "查询开奖结果")
    @ApiOperationSupport(author = "jess", order = 2)
    public Result<ResPage<LotteryParams.LotteryResultRes>> queryLotteryResult(@Valid @RequestBody ReqPage<LotteryParams.LotteryResultReq> reqPage ) {
        return Result.ok(lotteryServiceImpl.queryLotteryResult(reqPage));
    }


    @PostMapping(value = "/bet")
    @ApiOperation(value = "投注", notes = "投注")
    @ApiOperationSupport(author = "jess", order = 3)
    public Result<LotteryParams.BetRep> bet(@Valid @RequestBody LotteryParams.BetRes req) {
        return Result.ok(lotteryServiceImpl.bet(req));
    }

    @PostMapping(value = "/queryBetRecord")
    @ApiOperation(value = "查询注单记录", notes = "查询注单记录")
    @ApiOperationSupport(author = "jess", order = 4)
    public Result<ResPage<LotteryParams.BetRecordRes>> queryBetRecord(@Valid @RequestBody ReqPage<LotteryParams.BetRecordReq> reqPage ) {
        return Result.ok(lotteryServiceImpl.queryBetRecord(reqPage));
    }

    @PostMapping(value = "/queryMainPlateList")
    @ApiOperation(value = "查询主板记录", notes = "查询主板记录")
    @ApiOperationSupport(author = "jess", order = 5)
    public Result<List<LotteryParams.MainPlateRes>> queryMainPlateList() {
        return Result.ok(lotteryServiceImpl.queryMainPlateList());
    }

    @PostMapping(value = "/queryPlateList")
    @ApiOperation(value = "查询板块记录", notes = "查询板块记录")
    @ApiOperationSupport(author = "jess", order = 6)
    public Result<List<LotteryParams.PlateRes>> queryPlateList(@Valid @RequestBody LotteryParams.PlateReq req) {
        return Result.ok(lotteryServiceImpl.queryPlateList(req));
    }
}
