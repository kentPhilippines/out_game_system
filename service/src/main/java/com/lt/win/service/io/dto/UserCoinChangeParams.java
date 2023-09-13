package com.lt.win.service.io.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * @Auther: wells
 * @Date: 2022/8/9 00:32
 * @Description:
 */
public interface UserCoinChangeParams {

    @Getter
    enum FlowCategoryTypeEnum {
        //        类型:1-存款 2-提款 3-注单 4-开彩 5-返水 6-佣金 7-活动(奖励) 8-系统调账 9-退款
        BANK_SAVINGS(1, "存款"),
        WITHDRAWAL(2, "提款"),
        BET(3, "注单"),
        DRAW(4, "开彩"),
        REBATE(5, "返水"),
        BROKERAGE(6, "佣金"),
        AWARD(7, "活动(奖励)"),
        SYSTEM_RECONCILIATION(8, "系统调账"),
        REFUND(9, "退款"),
        COMM_COIN_TRANSFER(10, "佣金钱包转主账户余额"),
        WITHDRAWAL_REFUND(12, "提现退款");
        private final int code;
        private final String desc;

        FlowCategoryTypeEnum(int code, String desc) {
            this.code = code;
            this.desc = desc;
        }
    }

    @Data
    @Builder
    @AllArgsConstructor
    @Accessors(chain = true)
    @NoArgsConstructor
    class UserCoinChangeReq {
        @ApiModelProperty(name = "uid", value = "会员ID", example = "1")
        public Integer uid;
        @ApiModelProperty(name = "outIn", value = "收支类型:0-支出 1-收入")
        public Integer outIn = 0;
        @ApiModelProperty(name = "coin", value = "金额")
        public BigDecimal coin;
        @ApiModelProperty(name = "coinReal", value = "实际到账金额")
        public BigDecimal coinReal = BigDecimal.ZERO;
        @ApiModelProperty(name = "referId", value = " 对应的业务记录ID")
        public Long referId;
        @ApiModelProperty(name = "category", value = "日志类型 FlowCategoryTypeEnum")
        public Integer category;
        @ApiModelProperty(name = "platId", value = "三方游戏平台ID")
        public Integer platId = 0;
        @ApiModelProperty(name = "gameId", value = "三方游戏ID")
        public Integer gameId = 0;
        @ApiModelProperty(name = "subCategory", value = "category为活动时，子类型有值，否则取默认值0")
        public Integer subCategory = 0;
        @ApiModelProperty(name = "externalTxId", value = "外部交易id")
        public String externalTxId;
    }

}
