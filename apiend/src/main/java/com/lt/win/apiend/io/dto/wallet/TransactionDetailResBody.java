package com.lt.win.apiend.io.dto.wallet;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * <p>
 * 钱包-交易记录-交易详情 响应body
 * </p>
 *
 * @author andy
 * @since 2020/4/15
 */
public interface TransactionDetailResBody {

    /**
     * 充值详情
     */
    @Data
    class DePosit {
        @ApiModelProperty(name = "coin", value = "充值金额", example = "100.00")
        private BigDecimal coin;

        @ApiModelProperty(name = "coin", value = "到账金额", example = "100.00")
        private BigDecimal payCoin;

        @ApiModelProperty(name = "category", value = "类型:1-银联 2-微信 3-支付宝 4-QQ 5-QR扫码", example = "1")
        private Integer category;

        @ApiModelProperty(name = "payType", value = "支付类型:0-线下 1-线上", example = "1")
        private Integer payType;

        @ApiModelProperty(name = "bankName", value = "银行名称")
        private String bankName;

        @ApiModelProperty(name = "bankAccount", value = "账号")
        private String bankAccount;

        @ApiModelProperty(name = "accountName", value = "姓名")
        private String accountName;

        @ApiModelProperty(name = "id", value = "订单号码")
        private Long id;

        @ApiModelProperty(name = "createdAt", value = "提交时间", example = "1586334785")
        private Integer createdAt;

        @ApiModelProperty(name = "updatedAt", value = "到账时间:当status=2时,展示该字段", example = "1586334785")
        private Integer updatedAt;

        @ApiModelProperty(name = "status", value = "上分状态:0-申请中 1-手动到账 2-自动到账 3-充值失败 8-充值锁定 9-管理员充值", example = "2")
        private Integer status;

        @ApiModelProperty(name = "type", value = "类型:1-存款 2-提款 3-上分 4-下分 5-返水 6-佣金 7-活动", example = "1")
        private Integer type;

    }

    /**
     * 提现详情
     */
    @Data
    class Withdrawal {
        @ApiModelProperty(name = "bankName", value = "银行名称")
        private String bankName;

        @ApiModelProperty(name = "bankAccount", value = "银行账号")
        private String bankAccount;

        @ApiModelProperty(name = "coin", value = "充值金额", example = "100.00")
        private BigDecimal coin;

        @ApiModelProperty(name = "id", value = "订单号码")
        private Long id;

        @ApiModelProperty(name = "createdAt", value = "提交时间", example = "1586334785")
        private Integer createdAt;

        @ApiModelProperty(name = "updatedAt", value = "到账时间:当status=1时,展示该字段", example = "1586334785")
        private Integer updatedAt;

        @ApiModelProperty(name = "status", value = "状态：0-申请中 1-成功 2-失败", example = "1")
        private Integer status;

        @ApiModelProperty(name = "type", value = "类型:1-存款 2-提款 3-上分 4-下分 5-返水 6-佣金 7-活动", example = "1")
        private Integer type;

        @ApiModelProperty(name = "mark", value = "备注", example = "稽核失败")
        private String mark;

    }

    @Data
    @Builder
    class Detail {
        @ApiModelProperty(name = "status", value = "[上下分状态]:0-提交申请 1-成功 2-失败;[活动或返水状态]:1-正常 0-撤销;[佣金状态]:0-未发放 1-已发放", example = "0")
        private Integer status;

        @ApiModelProperty(name = "name", value = "平台名称|游戏名称|佣金日期|活动名称|", example = "主账户->泛亚电竞")
        private String name;

        @ApiModelProperty(name = "coin", value = "金额", example = "100.00")
        private BigDecimal coin;

        @ApiModelProperty(name = "createdAt", value = "转账时间", example = "1586334785")
        private Integer createdAt;

        @ApiModelProperty(name = "type", value = "类型:1-存款 2-提款 3-上分 4-下分 5-返水 6-佣金 7-活动", example = "1")
        private Integer type;

        @ApiModelProperty(name = "fCreateAt", value = "好友注册时间", example = "1586334785")
        private Integer fCreateAt;

        @ApiModelProperty(name = "id", value = "订单号码")
        private Long id;

    }

    /**
     * 邀请详情
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    class InviteDetail {
        @ApiModelProperty(name = "status", value = "状态:1-正常 0-撤销", example = "1")
        private Integer status;

        @ApiModelProperty(name = "fUsername", value = "好友姓名", example = "")
        private String fUsername;

        @ApiModelProperty(name = "fCreateAt", value = "好友注册时间", example = "1586334785")
        private Integer fCreateAt;

        @ApiModelProperty(name = "coin", value = "金额", example = "100.00")
        private BigDecimal coin;

        @ApiModelProperty(name = "createdAt", value = "时间", example = "1586334785")
        private Integer createdAt;
    }

}
