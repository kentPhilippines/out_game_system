package com.lt.win.backend.controller;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.lt.win.backend.io.dto.record.TradingManageParams;
import com.lt.win.backend.io.dto.record.TradingManageParams.*;
import com.lt.win.backend.service.TradingManageService;
import com.lt.win.utils.components.pagination.ReqPage;
import com.lt.win.utils.components.pagination.ResPage;
import com.lt.win.utils.components.response.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @Auther: wells
 * @Date: 2022/8/30 23:20
 * @Description:
 */
@RestController
@ApiSupport(author = "wells")
@RequestMapping("/v1/trading")
@Api(tags = "交易管理", value = "TradingManageController")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TradingManageController {
    private final TradingManageService tradingManageServiceImpl;


    @ApiOperationSupport(author = "wells", order = 1)
    @ApiOperation(value = "交易记录-列表查询", notes = "交易记录-列表查询")
    @PostMapping("/tradingManageList")
    public Result<ResPage<CoinLogListRes>> tradingManageList(@Valid @RequestBody ReqPage<CoinLogListReq> reqBody) {
        return Result.ok(tradingManageServiceImpl.tradingManageList(reqBody));
    }

    @ApiOperationSupport(author = "wells", order = 2)
    @ApiOperation(value = "交易记录-统计", notes = "交易记录-统计")
    @PostMapping("/statisticsTrading")
    public Result<StatisticsTrading> statisticsTrading(@Valid @RequestBody CoinLogListReq reqBody) {
        return Result.ok(tradingManageServiceImpl.statisticsTrading(reqBody));
    }
}
