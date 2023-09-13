package com.lt.win.backend.controller;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.lt.win.backend.io.dto.PayPlatParams.*;;
import com.lt.win.backend.service.PayPlatManageService;
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
 * @Date: 2022/8/29 02:25
 * @Description:
 */
@RestController
@ApiSupport(author = "wells")
@RequestMapping("/v1/payPlatManage")
@Api(tags = "财务管理-支付平台", value = "PayPlatManageController")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PayPlatManageController {
    private final PayPlatManageService payPlatManageServiceImpl;

    @ApiOperationSupport(author = "Wells", order = 1)
    @ApiOperation(value = "支付平台-列表", notes = "支付平台-列表")
    @PostMapping("/payPlatList")
    public Result<ResPage<PayPlatListRes>> payPlatList(@Valid @RequestBody ReqPage<PayPlatListReq> reqBody) {
        return Result.ok(payPlatManageServiceImpl.payPlatList(reqBody));
    }


    @ApiOperationSupport(author = "Wells", order = 2)
    @ApiOperation(value = "支付平台-修改", notes = "支付平台-修改")
    @PostMapping("/updatePlatList")
    public Result<Boolean> updatePlatList(@Valid @RequestBody UpdatePayPlatReq reqBody) {
        return Result.ok(payPlatManageServiceImpl.updatePlatList(reqBody));
    }

    @ApiOperationSupport(author = "Wells", order = 3)
    @ApiOperation(value = "汇率-列表", notes = "汇率-列表")
    @PostMapping("/coinListList")
    public Result<ResPage<CoinRateListRes>> coinListList(@Valid @RequestBody ReqPage<CoinRateListReq> reqBody) {
        return Result.ok(payPlatManageServiceImpl.coinListList(reqBody));
    }

    @ApiOperationSupport(author = "Wells", order = 4)
    @ApiOperation(value = "汇率-新增", notes = "汇率-新增")
    @PostMapping("/addCoinRate")
    public Result<Boolean> addCoinRate(@Valid @RequestBody AddCoinRateReq reqBody) {
        return Result.ok(payPlatManageServiceImpl.addCoinRate(reqBody));
    }

    @ApiOperationSupport(author = "Wells", order = 5)
    @ApiOperation(value = "汇率-修改", notes = "汇率-修改")
    @PostMapping("/updateCoinRate")
    public Result<Boolean> updateCoinRate(@Valid @RequestBody UpdateCoinRateReq reqBody) {
        return Result.ok(payPlatManageServiceImpl.updateCoinRate(reqBody));
    }

    @ApiOperationSupport(author = "Wells", order = 6)
    @ApiOperation(value = "汇率-删除", notes = "汇率-删除")
    @PostMapping("/deleteCoinRate")
    public Result<Boolean> deleteCoinRate(@Valid @RequestBody DeleteCoinRateReq reqBody) {
        return Result.ok(payPlatManageServiceImpl.deleteCoinRate(reqBody));
    }

    @ApiOperationSupport(author = "Wells", order = 7)
    @ApiOperation(value = "支付通道-列表", notes = "支付通道-列表")
    @PostMapping("/payChannelList")
    public Result<ResPage<PayChannelListRes>> payChannelList(@Valid @RequestBody ReqPage<PayChannelListReq> reqBody) {
        return Result.ok(payPlatManageServiceImpl.payChannelList(reqBody));
    }


    @ApiOperationSupport(author = "Wells", order = 7)
    @ApiOperation(value = "支付通道-修改", notes = "支付通道-修改")
    @PostMapping("/updateChannel")
    public Result<Boolean> updateChannel(@Valid @RequestBody UpdateChannelReq reqBody) {
        return Result.ok(payPlatManageServiceImpl.updateChannel(reqBody));
    }


}
