package com.lt.win.apiend.io.dto.wallet;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * 钱包-提现-首页
 * </p>
 *
 * @author andy
 * @since 2020/4/8
 */
@Data
public class WithdrawalIndexResBody {

    @ApiModelProperty(name = "coin", value = "中心钱包(用户余额)", example = "123456.00")
    private BigDecimal coin;

    @ApiModelProperty(name = "thirdTotalCoin", value = "锁定钱包(第三方汇总余额)", example = "10000.00")
    private BigDecimal thirdTotalCoin;

    @ApiModelProperty(name = "bankList", value = "银行列表")
    private List<UserBankList> bankList;

    @ApiModelProperty(name = "thirdCoinList", value = "第三方平台余额列表")
    private List<ThirdCoin> thirdCoinList;
}
