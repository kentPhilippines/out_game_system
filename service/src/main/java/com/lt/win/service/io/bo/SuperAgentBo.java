package com.lt.win.service.io.bo;

import com.lt.win.service.io.constant.ConstData;
import com.lt.win.service.io.dto.BaseParams;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;

/**
 * 顶级代理Bo
 *
 * @author david
 * @since 2022/11/10
 */
public interface SuperAgentBo {
    /**
     * 登录入参
     */
    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    class ResetPasswordReqDto {
        @NotNull(message = "Can't Empty")
        @ApiModelProperty(name = "username", value = "用户名", example = "A2001")
        private String username;
        @NotNull(message = "密码不能为空")
        @Pattern(regexp = "^[A-Za-z\\d~'`!@#￥$%^&*()-+_=:|]{4,16}$", message = "4到16位英文、字母、特殊符号组成")
        @ApiModelProperty(name = "oldPassword", value = "密码(旧)", example = "******")
        private String oldPassword;
        @NotNull(message = "密码不能为空")
        @Pattern(regexp = "^[A-Za-z\\d~'`!@#￥$%^&*()-+_=:|]{4,16}$", message = "4到16位英文、字母、特殊符号组成")
        @ApiModelProperty(name = "password", value = "密码(新)", example = "******")
        private String password;
    }

    /**
     * 顶级代理列表
     */
    @EqualsAndHashCode(callSuper = true)
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @SuperBuilder
    class ListReqDto extends BaseParams.TimeParams {
        @ApiModelProperty(name = "username", value = "用户名")
        private String username;
        @ApiModelProperty(name = "mobile", value = "手机号码")
        private String mobile;
        @ApiModelProperty(name = "email", value = "邮箱")
        private String email;
        @ApiModelProperty(name = "status", value = "状态:10-正常 9-冻结 8-删除")
        private Integer status;
    }

    /**
     * 顶级代理列表
     */
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    class ListResDto {
        @ApiModelProperty(name = "id", value = "ID")
        private Integer id;
        @ApiModelProperty(name = "username", value = "用户名")
        private String username;
        @ApiModelProperty(name = "email", value = "邮箱")
        private String email;
        @ApiModelProperty(name = "mobile", value = "手机号码")
        private String mobile;
        @ApiModelProperty(name = "status", value = "状态")
        private Integer status;
        @ApiModelProperty(name = "promoCode", value = "推广码")
        private String promoCode;
        @ApiModelProperty(name = "createdAt", value = "创建时间")
        private Integer createdAt;
        @ApiModelProperty(name = "updatedAt", value = "更新时间")
        private Integer updatedAt;
    }

    /**
     * 顶级代理列表
     */
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @SuperBuilder
    class SaveOrUpdateReqDto {
        @ApiModelProperty(name = "id", value = "Id(新增不传,修改传)")
        private Integer id;
        @ApiModelProperty(name = "username", value = "用户名")
        private String username;
        @ApiModelProperty(name = "email", value = "邮箱")
        private String email;
        @ApiModelProperty(name = "areaCode", value = "区号")
        private String areaCode;
        @ApiModelProperty(name = "mobile", value = "手机号码")
        private String mobile;
        @ApiModelProperty(name = "passwordHash", value = "登陆密码")
        private String passwordHash;
        @ApiModelProperty(name = "status", value = "状态:10-正常 9-冻结 8-删除", example = "10")
        private Integer status;
    }

    /**
     * 顶级代理列表
     */
    @EqualsAndHashCode(callSuper = true)
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @SuperBuilder
    class AgentReportReqDto extends BaseParams.TimeParams {
        @ApiModelProperty(name = "id", value = "代理ID(代理报表带进来)")
        private Integer id;
        @ApiModelProperty(name = "username", value = "用户名")
        private String username;
        @ApiModelProperty(name = "level", value = "代理层级:不传-全部 1-直属 2-二级 3-三级 9-其他(字典: dic_agent_level_web)")
        private Integer level;
        @ApiModelProperty(name = "supUsername1", value = "上级代理")
        private String supUsername1;
        @ApiModelProperty(name = "email", value = "邮箱")
        private String email;
        @ApiModelProperty(name = "mobile", value = "手机号码")
        private String mobile;
        @ApiModelProperty(name = "status", value = "状态:10-正常 9-冻结 8-删除")
        private Integer status;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @SuperBuilder
    class AgentReportResDto {
        // 团队报表数据
        @ApiModelProperty(name = "coinDeposit", value = "团队总充值")
        private BigDecimal coinDeposit = BigDecimal.ZERO;
        @ApiModelProperty(name = "coinWithdrawal", value = "团队总提现")
        private BigDecimal coinWithdrawal = BigDecimal.ZERO;
        @ApiModelProperty(name = "coinWithdrawalReally", value = "团队实际到账")
        private BigDecimal coinWithdrawalReally = BigDecimal.ZERO;
        @ApiModelProperty(name = "coinAdjust", value = "团队总调账")
        private BigDecimal coinAdjust = BigDecimal.ZERO;
        @ApiModelProperty(name = "coinBet", value = "团队总投注")
        private BigDecimal coinBet = BigDecimal.ZERO;
        @ApiModelProperty(name = "coinBetBonus", value = "团队总派彩")
        private BigDecimal coinBetBonus = BigDecimal.ZERO;
        @ApiModelProperty(name = "coinProfit", value = "总负盈利")
        private BigDecimal coinProfit = BigDecimal.ZERO;
    }

    /**
     * 总后台-代理线-代理列表出参
     */
    @EqualsAndHashCode(callSuper = true)
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @SuperBuilder
    class AgentListResDto extends AgentReportResDto {
        // 基本账户信息
        @ApiModelProperty(name = "id", value = "ID")
        private Integer id;
        @ApiModelProperty(name = "username", value = "用户名")
        private String username;
        @ApiModelProperty(name = "mobile", value = "手机号码")
        private String mobile;
        @ApiModelProperty(name = "email", value = "邮箱")
        private String email;
        @ApiModelProperty(name = "supUsername1", value = "上级代理")
        private String supUsername1;
        @ApiModelProperty(name = "status", value = "状态:10-正常 9-冻结")
        private Integer status;
        @ApiModelProperty(name = "createdAt", value = "注册时间")
        private Integer createdAt;
        @ApiModelProperty(name = "supLevelTop", value = "顶级代理层级")
        private Integer supLevelTop;
        // 明细数据-调用方设值
        @ApiModelProperty(name = "coin", value = "账户余额")
        private BigDecimal coin;
        @ApiModelProperty(name = "coinCommission", value = "佣金金额")
        private BigDecimal coinCommission;
        @ApiModelProperty(name = "teamNums", value = "团队人数")
        private Integer teamNums;
        @ApiModelProperty(name = "directNums", value = "直属人数")
        private Integer directNums;
        @ApiModelProperty(name = "coinBalance", value = "团队总余额")
        private BigDecimal coinBalance = BigDecimal.ZERO;
    }

    /**
     * 总后台-代理线-代理转移入参
     */
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @SuperBuilder
    class AgentTransferReqDto {
        // 团队报表数据
        @ApiModelProperty(name = "from", value = "被转移代理")
        @NotNull(message = "Can't Empty")
        private String from;
        @ApiModelProperty(name = "to", value = "转移至代理")
        @NotNull(message = "Can't Empty")
        private String to;
    }

    /**
     * 代理后台-代理中心-入参
     */
    @EqualsAndHashCode(callSuper = true)
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @SuperBuilder
    class WebAgentReportReqDto extends BaseParams.TimeParams {
        @ApiModelProperty(name = "level", value = "代理层级:不传-全部 1-直属 2-二级 3-三级 9-其他(字典: dic_agent_level_web)")
        private Integer level;
    }

    /**
     * 代理后台-代理中心-入参
     */
    @EqualsAndHashCode(callSuper = true)
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @SuperBuilder
    class WebAgentReportResDto extends AgentReportResDto {
        // 人数统计
        @ApiModelProperty(name = "teamNums", value = "团队人数")
        private Integer teamNums = ConstData.ZERO;
        @ApiModelProperty(name = "teamNewNums", value = "新增人数")
        private Integer teamNewNums = ConstData.ZERO;
        @ApiModelProperty(name = "depositFirstNums", value = "首充人数")
        private Integer depositFirstNums = ConstData.ZERO;
        @ApiModelProperty(name = "depositNums", value = "充值人数")
        private Integer depositNums = ConstData.ZERO;
        @ApiModelProperty(name = "withdrawalNums", value = "提款人数")
        private Integer withdrawalNums = ConstData.ZERO;
        @ApiModelProperty(name = "betNums", value = "投注人数")
        private Integer betNums = ConstData.ZERO;
        // 金额统计
        @ApiModelProperty(name = "coinDepositFirst", value = "首充金额")
        private BigDecimal coinDepositFirst = BigDecimal.ZERO;
        // agentLink
        @ApiModelProperty(name = "inviteCode", value = "邀请码")
        private String inviteCode;
        @ApiModelProperty(name = "inviteLink", value = "邀请链接")
        private String inviteLink;
    }

    /**
     * 顶级代理列表
     */
    @EqualsAndHashCode(callSuper = true)
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @SuperBuilder
    class AgentTeamListReqDto extends BaseParams.TimeParams {
        @ApiModelProperty(name = "username", value = "用户名")
        private String username;
        @ApiModelProperty(name = "level", value = "代理层级:不传-全部 1-直属 2-二级 3-三级 9-其他(字典: dic_agent_level_web)")
        private Integer level;
        @ApiModelProperty(name = "supUsername1", value = "上级代理")
        private String supUsername1;
        @ApiModelProperty(name = "status", value = "状态:10-正常 9-冻结 8-删除")
        private Integer status;
    }

    /**
     * 总后台-代理线-代理列表出参
     */
    @EqualsAndHashCode(callSuper = true)
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @SuperBuilder
    class AgentTeamListResDto extends AgentReportResDto {
        // 基本账户信息
        @ApiModelProperty(name = "id", value = "ID")
        private Integer id;
        @ApiModelProperty(name = "username", value = "用户名")
        private String username;
        @ApiModelProperty(name = "supUsername1", value = "上级代理")
        private String supUsername1;
        @ApiModelProperty(name = "mobile", value = "手机号")
        private String mobile;
        @ApiModelProperty(name = "status", value = "状态:10-正常 9-冻结")
        private Integer status;
        @ApiModelProperty(name = "createdAt", value = "注册时间")
        private Integer createdAt;
        @ApiModelProperty(name = "supLevelTop", value = "顶级代理层级")
        private Integer supLevelTop;
        @ApiModelProperty(name = "coin", value = "账户余额")
        private BigDecimal coin;
    }

    @EqualsAndHashCode(callSuper = true)
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @SuperBuilder
    class BetListReqDto extends BaseParams.TimeParams {
        @ApiModelProperty(name = "level", value = "代理层级:不传-全部 1-直属 2-二级 3-三级 9-其他(字典: dic_agent_level_web)")
        private Integer level;
        @ApiModelProperty(name = "username", value = "用户名")
        private String username;
        @ApiModelProperty(name = "supUsername1", value = "直属上级")
        private String supUsername1;
        @ApiModelProperty(name = "gameGroupId", value = "游戏类型:dic_game_group")
        private Integer gameGroupId;
        @ApiModelProperty(name = "status", value = "状态:dic_bet_status")
        private Integer status;
    }

    /**
     * 代理线-财务管理-投注记录
     */
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    class BetListResDto {
        @ApiModelProperty(name = "id", value = "订单号")
        private Long id;
        @ApiModelProperty(name = "uid", value = "UID")
        private Integer uid;
        @ApiModelProperty(name = "mobile", value = "手机号")
        private String mobile;
        @ApiModelProperty(name = "username", value = "用户名")
        private String username;
        @ApiModelProperty(name = "levelId", value = "会员等级")
        private Integer levelId;
        @ApiModelProperty(name = "supUsername1", value = "上级代理")
        private String supUsername1;
        @ApiModelProperty(name = "status", value = "状态:dic_bet_status")
        private Integer status;
        @ApiModelProperty(name = "supLevelTop", value = "顶级代理层级")
        private Integer supLevelTop;
        @ApiModelProperty(name = "createdAt", value = "投注时间")
        private Integer createdAt;
        @ApiModelProperty(name = "registerAt", value = "注册时间")
        private Integer registerAt;
        @ApiModelProperty(name = "coin", value = "投注金额")
        private BigDecimal coin;
        @ApiModelProperty(name = "coinBefore", value = "前金额")
        private BigDecimal coinBefore;
        @ApiModelProperty(name = "coinPayOut", value = "派彩金额")
        private BigDecimal coinPayOut;
        @ApiModelProperty(name = "coinProfit", value = "盈亏金额")
        private BigDecimal coinProfit;
        @ApiModelProperty(name = "gameName", value = "游戏名称")
        private String gameName;
    }

    @EqualsAndHashCode(callSuper = true)
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @SuperBuilder
    class DepositListReqDto extends BaseParams.TimeParams {
        @NotNull(message = "Can't Empty")
        @Min(0)
        @Max(1)
        @ApiModelProperty(name = "categoryCurrency", value = "类型:0-数字货币 1-法币")
        private Integer categoryCurrency;
        @ApiModelProperty(name = "username", value = "用户名")
        private String username;
        @ApiModelProperty(name = "supUsername1", value = "上级代理")
        private String supUsername1;
        @ApiModelProperty(name = "categoryTransfer", value = "交易类型:1-使用字典dic_category_currency_virtual 0-使用dic_category_currency_legal")
        private String categoryTransfer;
        @ApiModelProperty(name = "status", value = "字典:dic_deposit_record_status")
        private Integer status;
    }

    /**
     * 代理线-财务管理-充值记录
     */
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    class DepositListResDto {
        @ApiModelProperty(name = "id", value = "订单号")
        private String id;
        @ApiModelProperty(name = "uid", value = "UID")
        private Integer uid;
        @ApiModelProperty(name = "mobile", value = "手机号")
        private String mobile;
        @ApiModelProperty(name = "username", value = "用户名")
        private String username;
        @ApiModelProperty(name = "levelId", value = "会员等级")
        private Integer levelId;
        @ApiModelProperty(name = "supUsername1", value = "上级代理")
        private String supUsername1;
        @ApiModelProperty(name = "status", value = "状态:10-正常 9-冻结")
        private Integer status;
        @ApiModelProperty(name = "supLevelTop", value = "顶级代理层级")
        private Integer supLevelTop;
        @ApiModelProperty(name = "registerAt", value = "注册时间")
        private Integer registerAt;
        @ApiModelProperty(name = "orderId", value = "订单号")
        private String orderId;
        @ApiModelProperty(name = "coinBefore", value = "前金额")
        private BigDecimal coinBefore;
        @ApiModelProperty(name = "coinApply", value = "申请金额")
        private BigDecimal coinApply;
        @ApiModelProperty(name = "coinReally", value = "到账金额")
        private BigDecimal coinReally;
        @ApiModelProperty(name = "exchangeRate", value = "汇率", example = "0.9")
        private BigDecimal exchangeRate;
        @ApiModelProperty(name = "categoryCurrency", value = "货币类型")
        private Integer categoryCurrency;
        @ApiModelProperty(name = "categoryTransfer", value = "转账类型")
        private Integer categoryTransfer;
        @ApiModelProperty(name = "createdAt", value = "充值时间")
        private Integer createdAt;
        @ApiModelProperty(name = "currency", value = "货币")
        private String currency;
    }

    @EqualsAndHashCode(callSuper = true)
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @SuperBuilder
    class WithdrawalListReqDto extends BaseParams.TimeParams {
        @NotNull(message = "Can't Empty")
        @Min(0)
        @Max(1)
        @ApiModelProperty(name = "categoryCurrency", value = "类型:0-数字货币 1-法币")
        private Integer categoryCurrency;
        @ApiModelProperty(name = "categoryTransfer", value = "交易类型:1-使用字典dic_category_currency_virtual 0-使用dic_category_currency_legal")
        private String categoryTransfer;
        @ApiModelProperty(name = "username", value = "用户名")
        private String username;
        @ApiModelProperty(name = "supUsername1", value = "上级代理")
        private String supUsername1;
        @ApiModelProperty(name = "status", value = "字典:dic_withdrawal_record_status")
        private Integer status;
    }

    /**
     * 代理线-财务管理-提现记录
     */
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    class WithdrawalListResDto {
        @ApiModelProperty(name = "id", value = "订单号")
        private String id;
        @ApiModelProperty(name = "uid", value = "UID")
        private Integer uid;
        @ApiModelProperty(name = "mobile", value = "手机号")
        private String mobile;
        @ApiModelProperty(name = "username", value = "用户名")
        private String username;
        @ApiModelProperty(name = "levelId", value = "会员等级")
        private Integer levelId;
        @ApiModelProperty(name = "supUsername1", value = "上级代理")
        private String supUsername1;
        @ApiModelProperty(name = "status", value = "状态:10-正常 9-冻结")
        private Integer status;
        @ApiModelProperty(name = "supLevelTop", value = "顶级代理层级")
        private Integer supLevelTop;
        @ApiModelProperty(name = "registerAt", value = "注册时间")
        private Integer registerAt;
        @ApiModelProperty(name = "orderId", value = "订单号")
        private String orderId;
        @ApiModelProperty(name = "coinBefore", value = "前金额")
        private BigDecimal coinBefore;
        @ApiModelProperty(name = "coinApply", value = "申请金额")
        private BigDecimal coinApply;
        @ApiModelProperty(name = "coinReally", value = "到账金额")
        private BigDecimal coinReally;
        @ApiModelProperty(name = "exchangeRate", value = "汇率", example = "0.9")
        private BigDecimal exchangeRate;
        @ApiModelProperty(name = "categoryCurrency", value = "货币类型")
        private Integer categoryCurrency;
        @ApiModelProperty(name = "categoryTransfer", value = "转账类型")
        private Integer categoryTransfer;
        @ApiModelProperty(name = "createdAt", value = "提现时间")
        private Integer createdAt;
        @ApiModelProperty(name = "currency", value = "货币")
        private String currency;
    }
}
