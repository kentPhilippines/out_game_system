package com.lt.win.apiend.io.dto.wallet;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Author : Wells
 * @Date : 2020-12-25 7:11 下午
 * @Description :  提款提示信息
 */
public interface WithdrawalHint {
    String MIN_DRAW_COIN = "MinDrawCoin";
    String MIN_COIN = "minCoin";
    String MAX_COIN = "maxCoin";

    @Data
    @ApiModel(value = "HintReqBody", description = "提款提示信息请求实体")
    class HintReqBody {
        @ApiModelProperty(name = "messageType", value = "消息类型：0-提示文本，1-提示金额", example = "1")
        private Integer messageType;
    }

    @Data
    @Builder
    @ApiModel(value = "HintResBody", description = "提款提示信息响应实体")
    class HintResBody {
        @ApiModelProperty(name = "codeRequire", value = "还需打码量", example = "80.00")
        private BigDecimal codeRequire;
        @ApiModelProperty(name = "withdrawalCount", value = " 可提款次数", example = "5")
        private Integer withdrawalCount;
        @ApiModelProperty(name = "coin", value = "最大金额", example = "100.05")
        private BigDecimal coin;
        @ApiModelProperty(name = "maxCoin", value = "单笔最大金额", example = "100.05")
        private BigDecimal maxCoin;
        @ApiModelProperty(name = "minCoin", value = "单笔最低额度", example = "10.00")
        private BigDecimal minCoin;
        @ApiModelProperty(name = "surplusCoin", value = "剩余额度", example = "10.00")
        private BigDecimal surplusCoin;
        @ApiModelProperty(name = "fastCoin", value = "快捷金额", example = "100，200")
        private List<Integer> fastCoin;
    }
}
