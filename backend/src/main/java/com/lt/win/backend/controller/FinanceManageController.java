package com.lt.win.backend.controller;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.lt.win.backend.io.dto.FinanceManageParams.*;
import com.lt.win.backend.io.dto.FinancialWithdrawalParameter;
import com.lt.win.backend.service.FinanceManageService;
import com.lt.win.service.io.dto.AuditBaseParams;
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
import java.util.List;

/**
 * @Auther: wells
 * @Date: 2022/8/24 01:42
 * @Description:
 */
@RestController
@ApiSupport(author = "wells")
@RequestMapping("/v1/financeManage")
@Api(tags = "财务管理-财务管理", value = "FinanceManageController")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class FinanceManageController {
    private final FinanceManageService financeManageServiceImpl;

    @ApiOperationSupport(author = "wells", order = 1)
    @ApiOperation(value = "充值记录", notes = "充值记录")
    @PostMapping("/depositRecordList")
    public Result<ResPage<DepositRecordRes>> depositRecordList(@Valid @RequestBody ReqPage<DepositRecordReq> req) {
        return Result.ok(financeManageServiceImpl.depositRecordList(req));
    }
    
    @PostMapping(value = "/depositSum")
    @ApiOperation(value = "充值记录汇总", notes = "充值记录汇总")
    @ApiOperationSupport(order = 2)
    public Result<DepositSumResDto> depositSum(@Valid @RequestBody DepositRecordReq reqDto) {
        return Result.ok(financeManageServiceImpl.depositSum(reqDto));
    }

    @PostMapping(value = "/depositDetail")
    @ApiOperation(value = "充值记录详情", notes = "充值记录详情")
    @ApiOperationSupport(order = 3)
    public Result<DepositDetailResDto> depositDetail(@Valid @RequestBody DepositDetailReqDto reqDto) {
        return Result.ok(financeManageServiceImpl.depositDetail(reqDto));
    }

    @ApiOperationSupport(author = "wells", order =4 )
    @ApiOperation(value = "提款记录", notes = "提款记录")
    @PostMapping("/getWithdrawalRecord")
    public Result<ResPage<WithdrawalRecordRes>> withdrawalRecordList(@Valid @RequestBody ReqPage<WithdrawalRecordReq> req) {
        return Result.ok(financeManageServiceImpl.withdrawalRecordList(req));
    }

    @PostMapping(value = "/WithdrawalSum")
    @ApiOperation(value = "提款记录汇总", notes = "提款记录汇总")
    @ApiOperationSupport(order = 5)
    public Result<WithdrawalSumResDto> withdrawalSum(@Valid @RequestBody WithdrawalRecordReq reqDto) {
        return Result.ok(financeManageServiceImpl.withdrawalSum(reqDto));
    }

    @PostMapping(value = "/updateWithdrawalStatus")
    @ApiOperation(value = "修改提款状态", notes = "修改提款状态")
    @ApiOperationSupport(order = 7)
    public Result<UpdateWithdrawalStatusResDto> updateWithdrawalStatus(@Valid @RequestBody UpdateWithdrawalStatusReqDto reqDto) {
        return Result.ok(financeManageServiceImpl.updateWithdrawalStatus(reqDto));
    }

    @PostMapping(value = "/WithdrawalDetail")
    @ApiOperation(value = "提款记录详情", notes = "提款记录详情")
    @ApiOperationSupport(order = 6)
    public Result<WithdrawalDetailResDto> withdrawalDetail(@Valid @RequestBody WithdrawalDetailReqDto reqDto) {
        return Result.ok(financeManageServiceImpl.withdrawalDetail(reqDto));
    }


    @PostMapping(value = "/isAudit")
    @ApiOperation(value = "稽核", notes = "稽核")
    @ApiOperationSupport(order = 7)
    public Result<UpdateWithdrawalStatusResDto> isAudit(@Valid @RequestBody AuditReqDto reqDto) {
        return Result.ok(financeManageServiceImpl.isAudit(reqDto));
    }
    @PostMapping(value = "/auditDetail")
    @ApiOperation(value = "稽核明细", notes = "稽核明细")
    @ApiOperationSupport(order = 8)
    public Result<AuditDetailResDto> auditDetail(@Valid @RequestBody ReqPage<AuditReqDto> reqDto) {
        return Result.ok(financeManageServiceImpl.auditDetail(reqDto));
    }

    @PostMapping(value = "/getUserCoin")
    @ApiOperation(value = "获取用户余额", notes = "获取用户余额")
    @ApiOperationSupport(order = 7)
    public Result<UserCoinRes> getUserCoin(@Valid @RequestBody UserCoinReq reqDto) {
        return Result.ok(financeManageServiceImpl.getUserCoin(reqDto));
    }


    @PostMapping(value = "/adminTransfer")
    @ApiOperation(value = "人工调账", notes = "人工调账")
    @ApiOperationSupport(order = 8)
    public Result<Boolean> adminTransfer(@Valid @RequestBody AdminTransferReqDto reqDto) {
        return Result.ok(financeManageServiceImpl.adminTransfer(reqDto));
    }


    @ApiOperationSupport(author = "wells", order = 9)
    @ApiOperation(value = "获取支付通道列表", notes = "获取支付通道列表")
    @PostMapping("/getChannelList")
    public Result<List<PayChannelRes>> getChannelList(@Valid @RequestBody PayChannelReq req) {
        return Result.ok(financeManageServiceImpl.getChannelList(req));
    }

    @ApiOperationSupport(author = "wells", order = 10)
    @ApiOperation(value = "补单", notes = "补单")
    @PostMapping("/replenishmentOrder")
    public Result<Boolean> replenishmentOrder(@Valid @RequestBody ReplenishmentOrderReq req) {
        return Result.ok(financeManageServiceImpl.replenishmentOrder(req));
    }

}
