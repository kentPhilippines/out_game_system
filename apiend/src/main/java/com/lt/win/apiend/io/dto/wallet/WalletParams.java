package com.lt.win.apiend.io.dto.wallet;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Auther: wells
 * @Date: 2022/7/31 18:18
 * @Description:
 */
public interface WalletParams {
    @Data
    @ApiModel(value = "WithdrawalAddressListRes", description = "提款地址列表响应实体")
    class WithdrawalAddressListRes {
        @ApiModelProperty(name = "id", value = "提款地址ID", example = "1")
        private Integer id;
        @ApiModelProperty(name = "categoryCurrency", value = "货币类型:0-数字货币 1-法币", example = "1")
        private Integer categoryCurrency;
        @ApiModelProperty(name = "categoryTransfer", value = "转账类型：1-TRC,2-ERC,3-BANK,4-PIX,5-GCASH", example = "PIX")
        private Integer categoryTransfer;
        @ApiModelProperty(name = "categoryTransferName", value = "转账类型：1-TRC,2-ERC,3-BANK,4-PIX,5-GCASH", example = "PIX")
        private String categoryTransferName;
        @ApiModelProperty(name = "address", value = "提款地址", example = "TEV6MRrbcQAGwzkb6pqMoHHb9b22xXb3zf")
        private String address;
        @ApiModelProperty(name = "status", value = "状态:1-默认地址(启用) 2-正常启用 3-删除", example = "2")
        private Integer status;
    }


    @Data
    @ApiModel(value = "WithdrawalAddressTypeRes", description = "获取提款地址类型请求实体")
    class WithdrawalAddressTypeRes {
        @ApiModelProperty(name = "categoryCurrency", value = "货币类型:0-数字货币 1-法币", example = "1")
        private Integer categoryCurrency;
        @ApiModelProperty(name = "categoryTransfer", value = "转账类型：1-TRC,2-ERC,3-BANK,4-PIX,5-GCASH", example = "PIX")
        private Integer categoryTransfer;
        @ApiModelProperty(name = "categoryTransferName", value = "转账类型：1-TRC,2-ERC,3-BANK,4-PIX,5-GCASH", example = "PIX")
        private String categoryTransferName;
        @ApiModelProperty(name = "typeList", value = "提款地址子类型", example = "")
        private List<AddressType> typeList;
    }

    @Data
    class AddressType {
        @ApiModelProperty(name = "name", value = "子类型名称", example = "")
        private String name;
        @ApiModelProperty(name = "type", value = "子类型值", example = "")
        private String type;

    }

    @Data
    @ApiModel(value = "AddWithdrawalAddressReq", description = "新增提款地址请求实体")
    class AddWithdrawalAddressReq {
        @ApiModelProperty(name = "categoryCurrency", value = "货币类型:0-数字货币 1-法币", example = "1")
        private Integer categoryCurrency;
        @ApiModelProperty(name = "categoryTransfer", value = "转账类型：1-TRC,2-ERC,3-BANK,4-PIX,5-GCASH", example = "1")
        private Integer categoryTransfer;
        @ApiModelProperty(name = "address", value = "提款地址； TRC直接字符串：PIX字段accountType，accountNo;GCAS字段accountNumber,bankOwner", example = "TEV6MRrbcQAGwzkb6pqMoHHb9b22xXb3zf")
        private String address;
//        @ApiModelProperty(name = "accountType", value = "账户类型;字典：dic_withdrawal_address_account_type", example = "1")
//        private String accountType;
//        @ApiModelProperty(name = "accountNo", value = "账户号", example = "646180110400000007")
//        private String accountNo;
//        @ApiModelProperty(name = "账户号", value = "", example = "1")
//        private String accountNumber;
//        @ApiModelProperty(name = "bankOwner", value = "账户名", example = "646180110400000007")
//        private String bankOwner;

    }

    @Data
    @ApiModel(value = "UpdateWithdrawalAddressReq", description = "设置默认提款地址请求实体")
    class UpdateWithdrawalAddressReq {
        @ApiModelProperty(name = "id", value = "提款地址ID", example = "1")
        private Integer id;
    }

    @Data
    @ApiModel(value = "CurrencyCoinRes", description = "获取钱包余额响应实体")
    class CurrencyCoinRes {
        @ApiModelProperty(name = "bonus", value = "奖金金额", example = "10.88")
        private BigDecimal bonus;
        @ApiModelProperty(name = "reelCoin", value = "币种真实金额", example = "10.88")
        private BigDecimal reelCoin;
        @ApiModelProperty(name = "allWithdrawalCoin", value = "可提款金额", example = "10.88")
        private BigDecimal allWithdrawalCoin;
        @ApiModelProperty(name = "needCodeCoin", value = "还需打码量", example = "10.88")
        private BigDecimal needCodeCoin;
        @ApiModelProperty(name = "mainCurrency", value = "主币种", example = "USD")
        private String mainCurrency;
    }

    @Data
    @ApiModel(value = "DeleteWithdrawalAddressReq", description = "删除提款地址请求实体")
    class DeleteWithdrawalAddressReq {
        @ApiModelProperty(name = "id", value = "提款地址ID", example = "1")
        private Integer id;
    }

    @Data
    @ApiModel(value = "PayChannelRes", description = "通道配置响应实体")
    class PayChannelRes {
        @ApiModelProperty(name = "code", value = "支付平台编码", example = "TRC-20")
        private String code;
        @ApiModelProperty(name = "name", value = "支付通道名称", example = "TRC-20")
        private String name;
    }

    @Data
    @ApiModel(value = "PayChannelReq", description = "通道配置请求实体")
    class PayChannelReq {
        @ApiModelProperty(name = "category", value = "通道类型；1:代收 2:代付;充值为代收，提款为代付", example = "1")
        private Integer category;
    }

    @Data
    @ApiModel(value = "WithdrawalRes", description = "在线提款请求实体")
    class WithdrawalRes {
        @ApiModelProperty(name = "categoryCurrency", value = "货币类型:0-数字货币 1-法币", example = "1")
        private Integer categoryCurrency;
        @ApiModelProperty(name = "categoryTransfer", value = "转账类型：1-TRC,2-ERC,3-BANK,4-PIX,5-GCASH", example = "1")
        private Integer categoryTransfer;
        @ApiModelProperty(name = "withdrawalAddress", value = "提款地址", example = "EV6MRrbcQAGwzkb6pqMoHHb9b22xXb3zf")
        private String withdrawalAddress;
        @ApiModelProperty(name = "withdrawalAmount", value = "提款金额", example = "99.99")
        private BigDecimal withdrawalAmount;
        @ApiModelProperty(name = "category", value = "类型:佣金提现是0 正常提现是1", example = "")
        private Integer category = 1;

    }


    @Data
    @ApiModel(value = "DepositPlatReq", description = "充值平台请求实体")
    class DepositPlatReq {
        @ApiModelProperty(name = "currency", value = "币种", example = "USD")
        private String currency;
    }

    @Data
    @ApiModel(value = "WithdrawalPlatReq", description = "提款平台请求实体")
    class WithdrawalPlatReq {
        @ApiModelProperty(name = "currency", value = "币种", example = "USD")
        private String currency;
    }

    @Data
    @ApiModel(value = "WithdrawalPlatRes", description = "提款平台响应实体")
    class WithdrawalPlatRes {
        @ApiModelProperty(name = "code", value = "支付平台编码", example = "TRC-20")
        private String code;
        @ApiModelProperty(name = "name", value = "支付通道名称", example = "TRC-20")
        private String name;
    }

    @Data
    @ApiModel(value = "WithdrawalChannelListRes", description = "提款通道列表响应实体")
    class WithdrawalChannelListRes {
        @ApiModelProperty(name = "categoryCurrency", value = "货币类型:0-数字货币 1-法币", example = "1")
        private Integer categoryCurrency;
        @ApiModelProperty(name = "categoryTransfer", value = "转账类型：1-TRC,2-ERC,3-BANK,4-PIX,5-GCASH", example = "1")
        private Integer categoryTransfer;
        @ApiModelProperty(name = "withdrawalRate", value = "提款汇率", example = "0.95")
        private BigDecimal withdrawalRate;
        @ApiModelProperty(name = "mainNetFees", value = "主网费", example = "10")
        private BigDecimal mainNetFees;
        @ApiModelProperty(name = "coin", value = "用户余额", example = "89.99")
        private BigDecimal coin;
        @ApiModelProperty(name = "allWithdrawalCoin", value = "可提款金额", example = "10.88")
        private BigDecimal allWithdrawalCoin;
        @ApiModelProperty(name = "minWithdrawalCoin", value = "最小提款金额", example = "10")
        private BigDecimal minWithdrawalCoin;
        @ApiModelProperty(name = "maxWithdrawalCoin", value = "最大提款金额", example = "10000")
        private BigDecimal maxWithdrawalCoin;
        @ApiModelProperty(name = "currency", value = " 币种", example = "USDT")
        private String currency;
    }

    @Data
    @ApiModel(value = "WithdrawalChannelListReq", description = "提款通道列表请求参数实体")
    class WithdrawalChannelListReq {
        @ApiModelProperty(name = "withdrawalType", value = "提款类型 0:抽佣提款;1:正常流程提款", example = "1")
        private Integer withdrawalType;
    }


    @Data
    @ApiModel(value = "DepositRecordReq", description = "充值记录请求实体")
    class DepositRecordReq {
        @ApiModelProperty(name = "status", value = "状态: 0-申请中 1-成功;字典:dic_deposit_record_status", example = "1")
        private Integer status;
        @ApiModelProperty(name = "startTime", value = "开始时间", example = "1600448057")
        private Integer startTime;
        @ApiModelProperty(name = "endTime", value = "结束时间", example = "1600559067")
        private Integer endTime;
    }

    @Data
    @ApiModel(value = "DepositRecordRes", description = "充值记录响应实体")
    class DepositRecordRes {
        @ApiModelProperty(name = "platName", value = "支付通道名称", example = "TRC-20")
        private String platName;
        @ApiModelProperty(name = "orderId", value = "订单号", example = "O2022081102422478706")
        private String orderId;
        @ApiModelProperty(name = "payAmount", value = "充值金额", example = "99.99")
        private String payAmount;
        @ApiModelProperty(name = "payAddress", value = "加密地址", example = "TEV6MRrbcQAGwzkb6pqMoHHb9b22xXb3zf")
        private String payAddress;
        @ApiModelProperty(name = "exchangeRate", value = "汇率", example = "0.9")
        private String exchangeRate;
        @ApiModelProperty(name = "realAmount", value = "到账金额", example = "99.99")
        private String realAmount;
        @ApiModelProperty(name = "status", value = "状态: 0-申请中 1-成功;字典:dic_deposit_record_status", example = "1")
        private Integer status;
        @ApiModelProperty(name = "createdAt", value = "创建时间", example = "1600448057")
        private Integer createdAt;
    }

    @Data
    @ApiModel(value = "WithdrawalRecordReq", description = "提款记录请求实体")
    class WithdrawalRecordReq {
        @ApiModelProperty(name = "platName", value = "支付通道名称", example = "TRC-20")
        private String platName;
        @ApiModelProperty(name = "status", value = "状态: 0-申请中，1-成功，2-失败;字典:dic_withdrawal_record_status", example = "1")
        private Integer status;
        @ApiModelProperty(name = "startTime", value = "开始时间", example = "1600448057")
        private Integer startTime;
        @ApiModelProperty(name = "endTime", value = "结束时间", example = "1600559067")
        private Integer endTime;
    }

    @Data
    @ApiModel(value = "WithdrawalRecordRes", description = "提款记录响应实体")
    class WithdrawalRecordRes {
        @ApiModelProperty(name = "orderId", value = "订单号", example = "O2022081900464568308")
        private String orderId;
        @ApiModelProperty(name = "platName", value = "支付通道名称", example = "TRC-20")
        private String platName;
        @ApiModelProperty(name = "code", value = "支付通道编码", example = "TRC-20")
        private String code;
        @ApiModelProperty(name = "withdrawalAddress", value = "提款地址", example = "TEV6MRrbcQAGwzkb6pqMoHHb9b22xXb3zf")
        private String withdrawalAddress;
        @ApiModelProperty(name = "withdrawalAmount", value = "提款金额", example = "99.99")
        private String withdrawalAmount;
        @ApiModelProperty(name = "exchangeRate", value = "汇率", example = "0.9")
        private String exchangeRate;
        @ApiModelProperty(name = "mainNetFees", value = "主网费", example = "5.00")
        private BigDecimal mainNetFees;
        @ApiModelProperty(name = "realAmount", value = "到账金额", example = "99.99")
        private String realAmount;
        @ApiModelProperty(name = "status", value = "状态: 0-申请中，1-成功，2-失败;字典:dic_withdrawal_record_status", example = "1")
        private Integer status;
        @ApiModelProperty(name = "createdAt", value = "创建时间", example = "1600448057")
        private Integer createdAt;
    }

    @Data
    @ApiModel(value = "CoinLogListReq", description = "帐变记录请求实体")
    class CoinLogListReq {
        @ApiModelProperty(name = "outIn", value = "收支类型:0-支出 1-收入;字典:dic_coin_log_out_in", example = "0")
        private Integer outIn;
        @ApiModelProperty(name = "category", value = "类型:1-存款 2-提款 3-投注 4-派彩 5-返水 6-佣金 7-活动(奖励) 8-系统调账;字典:dic_coin_log_category", example = "1")
        private Integer category;
        @ApiModelProperty(name = "startTime", value = "开始时间", example = "1600448057")
        private Integer startTime;
        @ApiModelProperty(name = "endTime", value = "结束时间", example = "1600559067")
        private Integer endTime;
    }

    @Data
    @ApiModel(value = "CoinLogListRes", description = "帐变记录响应实体")
    class CoinLogListRes {
        @ApiModelProperty(name = "outIn", value = "收支类型:0-支出 1-收入;字典:dic_coin_log_out_in", example = "0")
        private Integer outIn;
        @ApiModelProperty(name = "category", value = "类型:1-存款 2-提款 3-投注 4-派彩 5-返水 6-佣金 7-活动(奖励) 8-系统调账;字典:dic_coin_log_category", example = "1")
        private Integer category;
        @ApiModelProperty(name = "createdAt", value = "创建时间", example = "1600448057")
        private Integer createdAt;
        @ApiModelProperty(name = "coin", value = "帐变金额", example = "10.00")
        private BigDecimal coin;
        @ApiModelProperty(name = "coinBefore", value = "帐变前金额", example = "89.99")
        private BigDecimal coinBefore;
        @ApiModelProperty(name = "coinAfter", value = "帐变后金额", example = "99.99")
        private BigDecimal coinAfter;
    }

    @Data
    @ApiModel(value = "CheckDepositAddressReq", description = "充值验证请求实体")
    class CheckDepositAddressReq {
        @ApiModelProperty(name = "code", value = "通道code", example = " TRC-20")
        private String code;
    }

    @Data
    @Builder
    @ApiModel(value = "CheckDepositAddressRes", description = "充值验证响应实体")
    class CheckDepositAddressRes {
        @ApiModelProperty(name = "address", value = "充值地址", example = "http://")
        private String address;
    }

    @Data
    @ApiModel(value = "DepositChannelListRes", description = "获取充值通道配置响应实体类")
    class DepositChannelListRes {
        @ApiModelProperty(name = "code", value = "支付平台编码", example = "TRC-20")
        private String code;
        @ApiModelProperty(name = "name", value = "支付通道名称", example = "TRC-20")
        private String name;
        @ApiModelProperty(name = "rate", value = "汇率", example = "1")
        private BigDecimal rate;
        @ApiModelProperty(name = "minCoin", value = "最小充值金额", example = "10")
        private Integer minCoin;
        @ApiModelProperty(name = "maxCoin", value = "最小充值金额", example = "10")
        private Integer maxCoin;
        @ApiModelProperty(name = "chips", value = "快捷支付金额", example = "10,20,30")
        private String chips;
        @ApiModelProperty(name = "skipCategory", value = "跳转类型 1-跳转 2-扫码", example = "1")
        private Integer skipCategory;
        @ApiModelProperty(name = "currency", value = " 币种", example = "USDT")
        private String currency;
    }

}

