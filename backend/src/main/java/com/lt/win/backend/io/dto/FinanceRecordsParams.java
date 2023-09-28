package com.lt.win.backend.io.dto;

import com.lt.win.backend.io.bo.StartEndTime;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * @description:财务管理-财务记录
 * @author: wells
 * @date: 2022/8/14
 */
public interface FinanceRecordsParams {

    /**
     * 出款记录-列表ReqBody
     */
    @Data
    @ApiModel(value = "WithdrawalListReq", description = "出款记录请求实体")
    @EqualsAndHashCode(callSuper = false)
    class WithdrawalListReqBody extends StartEndTime {
        @ApiModelProperty(name = "id", value = "ID")
        private Long id;

        @ApiModelProperty(name = "uid", value = "UID")
        private Integer uid;

        @ApiModelProperty(name = "code", value = "支付通道code" , example = "TRC-20")
        private String code;

        @ApiModelProperty(name = "username", value = "用户名")
        private String username;

        @ApiModelProperty(name = "status", value = "状态: 0-申请中，1-成功，2-失败;字典:dic_withdrawal_record_status")
        private Integer status;

        @ApiModelProperty(name = "adminUid", value = "操作人ID")
        private Integer adminUid;

    }


    /**
     * 出款记录-列表ResBody
     */
    @Data
    @ApiModel(value = "WithdrawalListRes", description = "出款记录响应实体")
    class WithdrawalListResBody {
        @ApiModelProperty(name = "id", value = "id", example = "71286223372685312")
        private Long id;
        @ApiModelProperty(name = "uid", value = "UID")
        private Integer uid;
        @ApiModelProperty(name = "username", value = "用户名")
        private String username;
        @ApiModelProperty(name = "userLevel", value = "会员等级", example = "VIP1")
        private String userLevel;
        @ApiModelProperty(name = "code", value = "支付通道code" , example = "TRC-20")
        private String code;
        @ApiModelProperty(name = "platName", value = "支付通道名称", example = "TRC-20")
        private String platName;
        @ApiModelProperty(name = "withdrawalAddress", value = "提款地址", example = "TEV6MRrbcQAGwzkb6pqMoHHb9b22xXb3zf")
        private String withdrawalAddress;
        @ApiModelProperty(name = "withdrawalAmount", value = "提款金额", example = "99.99")
        private String withdrawalAmount;
        @ApiModelProperty(name = "exchangeRate", value = "汇率", example = "0.9")
        private BigDecimal exchangeRate;
        @ApiModelProperty(name = "mainNetFees", value = "主网费", example = "5.00")
        private BigDecimal mainNetFees;
        @ApiModelProperty(name = "realAmount", value = "到账金额", example = "99.99")
        private String realAmount;
        @ApiModelProperty(name = "coinBefore", value = "当前余额", example = "99.99")
        private BigDecimal coinBefore;
        @ApiModelProperty(name = "status", value = "状态: 0-申请中，1-成功，2-失败;字典:dic_withdrawal_record_status", example = "1")
        private Integer status;
        @ApiModelProperty(name = "adminUid", value = "审核人ID", example = "1")
        private Integer adminUid;
        @ApiModelProperty(name = "mark", value = "备注", example = "提款100")
        private String mark;
        @ApiModelProperty(name = "createdAt", value = "创建时间", example = "1600448057")
        private Integer createdAt;
        @ApiModelProperty(name = "updatedAt", value = "修改时间", example = "1600448057")
        private Integer updatedAt;
    }
    /**
     * 公用总金额:ResBody
     */
    @Data
    @Builder
    class CommonCoinResBody {
        @ApiModelProperty(name = "coin", value = "总金额")
        private BigDecimal coin;
    }
    /**
     * 调账记录-列表ReqBody
     */
    @Data
    @ApiModel(value = "AdminTransferListReq", description = "调账记录请求实体")
    @EqualsAndHashCode(callSuper = false)
    class AdminTransferListReqBody extends StartEndTime {
        @ApiModelProperty(name = "uid", value = "UID")
        private Integer uid;

        @ApiModelProperty(name = "username", value = "用户名")
        private String username;

        @ApiModelProperty(name = "id", value = "ID")
        private Long id;

        @ApiModelProperty(name = "adminId", value = "操作人ID")
        private Integer adminId;

        @ApiModelProperty(name = "category", value = "调账原因:0-其他 1-误存调账 2-活动调账")
        private Integer category;
    }

    /**
     * 调账记录-列表ResBody
     */
    @Data
    @ApiModel(value = "AdminTransferListRes", description = "调账记录响应实体")
    class AdminTransferListResBody {
        @ApiModelProperty(name = "uid", value = "UID")
        private Integer uid;

        @ApiModelProperty(name = "id", value = "ID")
        private Long id;

        @ApiModelProperty(name = "adminId", value = "操作人ID")
        private Integer adminId;

        @ApiModelProperty(name = "adminName", value = "操作人姓名")
        private String adminName;

        @ApiModelProperty(name = "coin", value = "调账金额")
        private BigDecimal coin;

        @ApiModelProperty(name = "coinBefore", value = "调账前金额")
        private BigDecimal coinBefore;

        @ApiModelProperty(name = "category", value = "调账原因:0-其他 1-误存调账 2-活动调账")
        private Integer category;

        @ApiModelProperty(name = "mark", value = "备注(调账原因)")
        private String mark;

        @ApiModelProperty(name = "createdAt", value = "时间")
        private Integer createdAt;

        @ApiModelProperty(name = "username", value = "会员名")
        private String username;
    }

    /**
     * 入款记录-列表ReqBody
     */
    @Data
    @ApiModel(value = "DepositListReq", description = "入款记录请求实体")
    @EqualsAndHashCode(callSuper = false)
    class DepositListReqBody extends StartEndTime {
        @ApiModelProperty(name = "id", value = "ID或订单号")
        private String id;

        @ApiModelProperty(name = "uid", value = "UID")
        private Integer uid;

        @ApiModelProperty(name = "username", value = "用户名")
        private String username;

        @ApiModelProperty(name = "code", value = "支付通道code" , example = "TRC-20")
        private String code;

        @ApiModelProperty(name = "status", value = "状态: 0-申请中 1-成功;字典:dic_deposit_record_status", example = "1")
        private Integer status;

        @ApiModelProperty(name = "category", value = "类型:0-钱包充值 1-佣金钱包转账充值", example = "1")
        private Integer category;

    }

    /**
     * 入款记录-列表ResBody
     */
    @Data
    @ApiModel(value = "DepositListRes", description = "入款记录响应实体")
    class DepositListResBody {
        @ApiModelProperty(name = "id", value = "id", example = "71286223372685312")
        private Long id;
        @ApiModelProperty(name = "username", value = "用户名", example = "1")
        private String username;
        @ApiModelProperty(name = "code", value = "支付通道code" , example = "TRC-20")
        private String code;
        @ApiModelProperty(name = "platName", value = "支付平台名称", example = "TRC-20")
        private String platName;
        @ApiModelProperty(name = "orderId", value = "订单号", example = "O2022081102422478706")
        private String orderId;
        @ApiModelProperty(name = "payAmount", value = "充值金额", example = "99.99")
        private String payAmount;
        @ApiModelProperty(name = "payAddress", value = "加密地址", example = "TEV6MRrbcQAGwzkb6pqMoHHb9b22xXb3zf")
        private String payAddress;
        @ApiModelProperty(name = "category", value = "类型:0-钱包充值 1-佣金钱包转账充值;字典:dic_deposit_record_category", example = "1")
        private Integer category;
        @ApiModelProperty(name = "realAmount", value = "到账金额", example = "99.99")
        private String realAmount;
        @ApiModelProperty(name = "exchangeRate", value = "汇率", example = "0.9")
        private BigDecimal exchangeRate;
        @ApiModelProperty(name = "status", value = "状态: 0-申请中 1-成功;字典:dic_deposit_record_status", example = "1")
        private Integer status;
        @ApiModelProperty(name = "createdAt", value = "创建时间", example = "1600448057")
        private Integer createdAt;
    }

}
