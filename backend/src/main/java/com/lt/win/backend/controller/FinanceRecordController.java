package com.lt.win.backend.controller;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.lt.win.backend.io.dto.FinanceRecordsParams.*;
import com.lt.win.backend.service.FinanceRecordService;
import com.lt.win.service.base.DepositOrWithdrawalBase;
import com.lt.win.service.base.websocket.MessageBo;
import com.lt.win.service.io.enums.MessageDeviceEnum;
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
 * @Date: 2022/8/28 23:42
 * @Description:
 */
@RestController
@ApiSupport(author = "wells")
@RequestMapping("/v1/financeRecord")
@Api(tags = "财务管理-财务记录", value = "FinanceRecordController")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class FinanceRecordController {
    private final FinanceRecordService financeRecordServiceImpl;
    private final DepositOrWithdrawalBase depositOrWithdrawalBase;

    @ApiOperationSupport(author = "Wells", order = 1)
    @ApiOperation(value = "出款记录-列表", notes = "出款记录-列表")
    @PostMapping("/withdrawalList")
    public Result<ResPage<WithdrawalListResBody>> withdrawalList(@Valid @RequestBody ReqPage<WithdrawalListReqBody> reqBody) {
        return Result.ok(financeRecordServiceImpl.withdrawalList(reqBody));
    }

    @ApiOperationSupport(author = "Wells", order = 2)
    @ApiOperation(value = "出款记录-统计", notes = "出款记录-统计")
    @PostMapping("/withdrawalStatistics")
    public Result<CommonCoinResBody> withdrawalStatistics(@Valid @RequestBody WithdrawalListReqBody reqBody) {
        return Result.ok(financeRecordServiceImpl.withdrawalStatistics(reqBody));
    }

    @ApiOperationSupport(author = "Wells", order = 3)
    @ApiOperation(value = "入款记录-列表", notes = "入款记录-列表")
    @PostMapping("/depositList")
    public Result<ResPage<DepositListResBody>> depositList(@Valid @RequestBody ReqPage<DepositListReqBody> reqBody) {
        return Result.ok(financeRecordServiceImpl.depositList(reqBody));
    }

    @ApiOperationSupport(author = "Wells", order = 4)
    @ApiOperation(value = "入款记录-统计", notes = "入款记录-统计")
    @PostMapping("/depositStatistics")
    public Result<CommonCoinResBody> depositStatistics(@Valid @RequestBody DepositListReqBody reqBody) {
        return Result.ok(financeRecordServiceImpl.depositStatistics(reqBody));
    }

    @ApiOperationSupport(author = "Wells", order = 5)
    @ApiOperation(value = "调账记录-列表", notes = "调账记录-列表")
    @PostMapping("/adminTransferList")
    public Result<ResPage<AdminTransferListResBody>> adminTransferList(@Valid @RequestBody ReqPage<AdminTransferListReqBody> reqBody) {
        return Result.ok(financeRecordServiceImpl.adminTransferList(reqBody));
    }

    @ApiOperationSupport(author = "Wells", order = 6)
    @ApiOperation(value = "调账记录-统计", notes = "调账记录-统计")
    @PostMapping("/adminTransferStatistics")
    public Result<CommonCoinResBody> adminTransferStatistics(@Valid @RequestBody AdminTransferListReqBody reqBody) {
        return Result.ok(financeRecordServiceImpl.adminTransferStatistics(reqBody));
    }

    @ApiOperationSupport(author = "wells", order = 7)
    @ApiOperation(value = "获取推送笔数的数据->提款", notes = "获取推送笔数的数据->提款")
    @PostMapping("/getPushWnData")
    public Result<MessageBo> getPushWnData() {
        return Result.ok(depositOrWithdrawalBase.getPushWnData(MessageDeviceEnum.B.getCode()));
    }

    @ApiOperationSupport(author = "wells", order = 8)
    @ApiOperation(value = "获取推送笔数的数据->存款", notes = "获取推送笔数的数据->存款")
    @PostMapping("/getPushDnData")
    public Result<MessageBo> getPushDnData() {
        return Result.ok(depositOrWithdrawalBase.getPushDnData(MessageDeviceEnum.B.getCode()));
    }
}
