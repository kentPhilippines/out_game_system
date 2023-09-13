package com.lt.win.backend.io.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @author: wells
 * @date: 2020/8/13
 * @description:
 */

public interface FinancialAdminParameter {

    /**
     * 人工调整-操作类型
     */
    @Getter
    @AllArgsConstructor
    enum OperatorTypeEnum {
        SYSTEM_TRANSFER(1, "系统调账"),
        ACTIVITY(2, "活动派彩"),
        BACKEND_DEPOSIT(3, "后台充值"),
        BACKEND_WITHDRAWAL(4, "后台提现");
        Integer code;
        String msg;
    }

    @Data
    @ApiModel(value = "AdminTransferReqDto", description = "人工调整请求实体类")
    class AdminTransferReqDto {
        @NotNull(message = "uid不能为空")
        @ApiModelProperty(name = "uid", value = "uid", example = "12")
        private Integer uid;
        @NotNull(message = "操作类型不能为空")
        @ApiModelProperty(name = "operatorType", value = "操作类型；1:活动类型，2：调账类型", example = "1")
        private Integer operatorType;
        @ApiModelProperty(name = "promotionsId", value = "活动ID", example = "1")
        private Integer promotionsId;
        @NotNull(message = "调整金额不能为空")
        @ApiModelProperty(name = "coin", value = "调整金额", example = "100.00")
        private BigDecimal coin;
        @ApiModelProperty(name = "category", value = "调整项目；调账原因:0-其他 1-误存调账 2-活动调账", example = "1")
        private Integer category;
        @ApiModelProperty(name = "coinOperatorType", value = "金额操作类型；1：增加金额，2：减少金额", example = "1")
        private Integer coinOperatorType;
        @ApiModelProperty(name = "mark", value = "备注", example = "转账异常")
        private String mark;
        @ApiModelProperty(name = "bankId", value = "银行ID", example = "1")
        private Integer bankId;
    }

    @Data
    @ApiModel(value = "UserCoinReqDto", description = "用户余额请求实体类")
    class UserCoinReqDto {
        @ApiModelProperty(name = "uid", value = "uid", example = "12")
        private Integer uid;
        @ApiModelProperty(name = "username", value = "username", example = "12")
        private String username;
    }

    @Data
    @ApiModel(value = "UserCoinResDto", description = "用户余额请求实体类")
    class UserCoinResDto {
        @ApiModelProperty(name = "avatar", value = "头像", example = "100.00")
        private String avatar;
        @ApiModelProperty(name = "uid", value = "用户uid", example = "100.00")
        private Integer uid;
        @ApiModelProperty(name = "username", value = "用户名", example = "100.00")
        private String username;
        @ApiModelProperty(name = "level", value = "会员等级", example = "100.00")
        private String level;
        @ApiModelProperty(name = "coin", value = "用户余额", example = "100.00")
        private BigDecimal coin;
    }

    @Data
    @ApiModel(value = "PromotionsListReqDto", description = "活动列表请求实体类")
    class PromotionsListReqDto {
        @ApiModelProperty(name = "id", value = "活动ID", example = "1")
        private Integer id;
        @ApiModelProperty(name = "codeZh", value = "活动名称", example = "一倍流水首存")
        private String codeZh;
    }
}
