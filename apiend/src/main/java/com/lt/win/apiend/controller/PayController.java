package com.lt.win.apiend.controller;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSort;
import com.lt.win.apiend.io.dto.wallet.WalletParams.*;
import com.lt.win.apiend.service.PayService;
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
 * @Auther: wells
 * @Date: 2022/7/31 12:23
 * @Description:
 */
@Slf4j
@RestController
@RequestMapping("/v1/wallet")
@Api(tags = "钱包")
@ApiSort(2)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PayController {
    private final PayService payServiceImpl;

    @ApiOperationSupport(author = "wells", order = 1)
    @ApiOperation(value = "提款地址列表", notes = "提款地址列表")
    @PostMapping("/withdrawalAddressList")
    public Result<List<WithdrawalAddressListRes>> withdrawalAddressList() {
        return Result.ok(payServiceImpl.getWithdrawalAddressList());
    }

    @ApiOperationSupport(author = "wells", order = 1)
    @ApiOperation(value = "获取提款地址类型", notes = "获取提款地址类型")
    @PostMapping("/getWithdrawalAddressType")
    public Result<List<WithdrawalAddressTypeRes>> getWithdrawalAddressType() {
        return Result.ok(payServiceImpl.getWithdrawalAddressType());
    }

    @ApiOperationSupport(author = "wells", order = 2)
    @ApiOperation(value = "新增提款地址", notes = "新增提款地址")
    @PostMapping("/addWithdrawalAddress")
    public Result<Boolean> addWithdrawalAddress(@Valid @RequestBody AddWithdrawalAddressReq addWithdrawalAddressReq) {
        return Result.ok(payServiceImpl.addWithdrawalAddress(addWithdrawalAddressReq));
    }

    @ApiOperationSupport(author = "wells", order = 3)
    @ApiOperation(value = "设置默认提款地址", notes = "设置默认提款地址")
    @PostMapping("/updateWithdrawalAddress")
    public Result<Boolean> updateWithdrawalAddress(@Valid @RequestBody UpdateWithdrawalAddressReq updateWithdrawalAddressReq) {
        return Result.ok(payServiceImpl.updateWithdrawalAddress(updateWithdrawalAddressReq));
    }

    @ApiOperationSupport(author = "wells", order = 4)
    @ApiOperation(value = "删除提款地址", notes = "删除提款地址")
    @PostMapping("/deleteWithdrawalAddress")
    public Result<Boolean> deleteWithdrawalAddress(@Valid @RequestBody DeleteWithdrawalAddressReq deleteWithdrawalAddressReq) {
        return Result.ok(payServiceImpl.deleteWithdrawalAddress(deleteWithdrawalAddressReq));
    }

    @ApiOperationSupport(author = "wells", order = 5)
    @ApiOperation(value = "获取支付通道列表", notes = "获取支付通道列表")
    @PostMapping("/getChannelList")
    public Result<List<PayChannelRes>> getChannelList(@Valid @RequestBody PayChannelReq req) {
        return Result.ok(payServiceImpl.getChannelList(req));
    }

    @ApiOperationSupport(author = "wells", order = 6)
    @ApiOperation(value = "获取钱包余额", notes = "获取钱包余额")
    @PostMapping("/getCurrencyCoin")
    public Result<CurrencyCoinRes> getCurrencyCoin() {
        return Result.ok(payServiceImpl.getCurrencyCoin());
    }

    @ApiOperationSupport(author = "wells", order = 9)
    @ApiOperation(value = "获取充值通道配置列表", notes = "获取充值通道配置列表")
    @PostMapping("/getDepositChannelList")
    public Result<List<DepositChannelListRes>> getDepositChannelList() {
        return Result.ok(payServiceImpl.getDepositChannelList());
    }

    @ApiOperationSupport(author = "wells", order = 9)
    @ApiOperation(value = "获取提款通道配置列表", notes = "获取提款通道配置列表")
    @PostMapping("/getWithdrawalChannelList")
    public Result<List<WithdrawalChannelListRes>> getWithdrawalChannelList(@Valid @RequestBody WithdrawalChannelListReq req) {
        return Result.ok(payServiceImpl.getWithdrawalChannelList(req));
    }

//    @ApiOperationSupport(author = "wells", order = 8)
//    @ApiOperation(value = "获取虚拟币充值通道", notes = "获取虚拟币充值通道")
//    @PostMapping("/getDepositPlat")
//    public Result<List<DepositPlatRes>> getDepositPlat(@Valid @RequestBody DepositPlatReq req) {
//        return Result.ok(payServiceImpl.getDepositPlat(req));
//    }
//
    @ApiOperationSupport(author = "wells", order = 9)
    @ApiOperation(value = "获取虚拟币提款通道", notes = "获取虚拟币提款通道")
    @PostMapping("/getWithdrawalPlat")
    public Result<List<WithdrawalPlatRes>> getWithdrawalPlat(@Valid @RequestBody WithdrawalPlatReq req) {
        return Result.ok(payServiceImpl.getWithdrawalPlat(req));
    }

    @ApiOperationSupport(author = "wells", order = 10)
    @ApiOperation(value = "在线提款", notes = "在线提款")
    @PostMapping("/withdrawal")
    public Result<Boolean> withdrawal(@Valid @RequestBody WithdrawalRes withdrawalRes) {
        return Result.ok(payServiceImpl.withdrawal(withdrawalRes));
    }

    @ApiOperationSupport(author = "wells", order = 11)
    @ApiOperation(value = "获取充值记录", notes = "获取充值记录")
    @PostMapping("/getDepositRecord")
    public Result<ResPage<DepositRecordRes>> getDepositRecord(@Valid @RequestBody ReqPage<DepositRecordReq> req) {
        return Result.ok(payServiceImpl.getDepositRecord(req));
    }

    @ApiOperationSupport(author = "wells", order = 11)
    @ApiOperation(value = "获取提款记录", notes = "获取提款记录")
    @PostMapping("/getWithdrawalRecord")
    public Result<ResPage<WithdrawalRecordRes>> getWithdrawalRecord(@Valid @RequestBody ReqPage<WithdrawalRecordReq> req) {
        return Result.ok(payServiceImpl.getWithdrawalRecord(req));
    }

    @ApiOperationSupport(author = "wells", order = 12)
    @ApiOperation(value = "获取帐变记录", notes = "获取帐变记录")
    @PostMapping("/getCoinLogList")
    public Result<ResPage<CoinLogListRes>> getCoinLogList(@Valid @RequestBody ReqPage<CoinLogListReq> req) {
        return Result.ok(payServiceImpl.getCoinLogList(req));
    }

    @ApiOperationSupport(author = "wells", order = 13)
    @ApiOperation(value = "检测充值地址", notes = "检测充值地址")
    @PostMapping("/checkDeposit")
    public Result<CheckDepositAddressRes> checkDeposit(@Valid @RequestBody CheckDepositAddressReq req) {
        return Result.ok(payServiceImpl.checkDeposit(req));
    }

}
