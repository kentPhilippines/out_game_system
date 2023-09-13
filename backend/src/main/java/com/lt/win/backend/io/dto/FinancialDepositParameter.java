package com.lt.win.backend.io.dto;

import com.lt.win.backend.io.bo.StartEndTime;
import com.lt.win.service.io.bo.UserCacheBo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author: wells
 * @date: 2020/8/12
 * @description: 财务管理参数接口
 */

public interface FinancialDepositParameter {


    @Data
    @EqualsAndHashCode(callSuper = false)
    @ApiModel(value = "DepositListReqDto", description = "充值列表请求实体类")
    class DepositListReqDto extends StartEndTime {
        @ApiModelProperty(name = "uid", value = "用户Id", example = "1")
        private Integer uid;
        @ApiModelProperty(name = "username", value = "用户名", example = "1")
        private String username;
        @ApiModelProperty(name = "orderId", value = "订单号(三方平台用)", example = "I2020071517060230457")
        private String orderId;
        @ApiModelProperty(name = "payType", value = "类型支付类型:0-离线 1-在线", example = "0")
        private Integer payType;
        @ApiModelProperty(name = "category", value = "类型类型:1-银联 2-微信 3-支付宝 4-QQ 5-QR扫码", example = "1")
        private Integer category;
        @ApiModelProperty(name = "status", value = "上分状态:0-申请中 1-手动到账 2-自动到账 3-充值失败 8-充值锁定 9-管理员充值;dic_coin_deposit_status", example = "1")
        private Integer status;
        @ApiModelProperty(name = "auditInfo", value = "审核信息（审核id或者打款人姓名）", example = "1/老王")
        private String auditInfo;
    }

    @Data
    @ApiModel(value = "DepositListResDto", description = "充值列表响应实体类")
    class DepositListResDto {
        @ApiModelProperty(name = "id", value = "id", example = "1")
        private Long id;
        @ApiModelProperty(name = "uid", value = "用户Id", example = "1")
        private Integer uid;
        @ApiModelProperty(name = "username", value = "用户名称", example = "隔壁老王")
        private String username;
        @ApiModelProperty(name = "userFlagList", value = "会员旗", example = "隔壁老王")
        private List<UserCacheBo.UserFlagInfo> userFlagList;
        @ApiModelProperty(name = "coin", value = "充值金额", example = "100")
        private BigDecimal coin;
        @ApiModelProperty(name = "payCoin", value = "到账金额", example = "100")
        private BigDecimal payCoin;
        @ApiModelProperty(name = "payType", value = "类型支付类型:0-离线 1-在线；dic_coin_deposit_pay_type", example = "0")
        private Integer payType;
        @ApiModelProperty(name = "category", value = "类型类型:1-银联 2-微信 3-支付宝 4-QQ 5-QR扫码", example = "1")
        private Integer category;
        @ApiModelProperty(name = "orderId", value = "订单号(三方平台用)", example = "00001")
        private String orderId;
        @ApiModelProperty(name = "createdAt", value = "创建时间", example = "1595949274")
        private Integer createdAt;
        @ApiModelProperty(name = "depositedAt", value = "上分时间或到账时间", example = "1595949274")
        private Integer depositedAt;
        @ApiModelProperty(name = "status", value = "上分状态:0-申请中 1-手动到账 2-自动到账 3-充值失败 8-充值锁定 9-管理员充值;dic_coin_deposit_status", example = "0")
        private Integer status;
        @ApiModelProperty(name = "auditStatus", value = "审核状态:0-未审核 1-审核中 2-审核失败 3-审核通过;dic_coin_deposit_audit_status", example = "0")
        private Integer auditStatus;
        @ApiModelProperty(name = "auditMark", value = "审核备注", example = "通过")
        private String auditMark;
        @ApiModelProperty(name = "auditUid", value = "审核人Id", example = "老王")
        private Integer auditUid;
        @ApiModelProperty(name = "depMark", value = "备注", example = "上分成功")
        private String depMark;
        @ApiModelProperty(name = "isSelf", value = "是否本人;0:否，1：是", example = "0")
        private Integer isSelf;
        @ApiModelProperty(name = "depStatus", value = "充值标识:1-首充 2-二充 3-三充 9-其他;字典：dic_coin_deposit_dep_status", example = "1")
        private Integer depStatus;
    }


    @Data
    @Builder
    @ApiModel(value = "DepositSumResDto", description = "充值汇总响应实体类")
    class DepositSumResDto {
        @ApiModelProperty(name = "count", value = "充值笔数", example = "10")
        private Integer count = 0;
        @ApiModelProperty(name = "totalCoin", value = "充值总金额", example = "99.99")
        private BigDecimal totalCoin = BigDecimal.ZERO;
    }

    @Data
    @ApiModel(value = "UpdateDepositStatusReqDto", description = "修改充值状态请求实体类")
    class UpdateDepositStatusReqDto {
        @ApiModelProperty(name = "id", value = "id", example = "71286223372685312")
        private Long id;
        @ApiModelProperty(name = "payCoin", value = "到账金额", example = "100")
        private BigDecimal payCoin;
        @ApiModelProperty(name = "status", value = "上分状态:0-申请中 1-手动到账 2-自动到账 3-充值失败 8-充值锁定 9-管理员充值", example = "0")
        private Integer status;
        @ApiModelProperty(name = "auditStatus", value = "审核状态:0-未审核 1-审核中 2-审核失败 3-审核通过", example = "0")
        private Integer auditStatus;
        @ApiModelProperty(name = "auditMark", value = "审核备注", example = "通过")
        private String auditMark;
        @ApiModelProperty(name = "depMark", value = "打款人备注", example = "上分成功")
        private String depMark;
    }

    @Data
    @Builder
    @ApiModel(value = "UpdateDepositStatusResDto", description = "修改充值状态响应实体类")
    class UpdateDepositStatusResDto {
        @ApiModelProperty(name = "auditMsg", value = "审核时弹框信息", example = "充值成功!")
        private String auditMsg;
    }

    @Data
    @ApiModel(value = "DepositDetailReqDto", description = "充值记录详情请求实体类")
    class DepositDetailReqDto {
        @ApiModelProperty(name = "id", value = "id", example = "71286223372685312")
        private Long id;
    }

    @Data
    @EqualsAndHashCode(callSuper = false)
    @ApiModel(value = "DepositDetailResDto", description = "充值记录详情响应实体类")
    class DepositDetailResDto extends DepositListResDto {
        @ApiModelProperty(name = "username", value = "用户名", example = "test0001")
        private String username;
        @ApiModelProperty(name = "depRealname", value = "打卡人姓名", example = "言福才")
        private String depRealname;
        @ApiModelProperty(name = "bankAccount", value = "收款卡号；线上:---,线下：卡号", example = "6230200731549932")
        private String bankAccount;
        @ApiModelProperty(name = "coinBefore", value = "当时余额", example = "100.12")
        private BigDecimal coinBefore;
        @ApiModelProperty(name = "auditedAt", value = "审核时间", example = "1594873715")
        private Integer auditedAt;
        @ApiModelProperty(name = "auditedUid", value = "审核人id", example = "1")
        private Integer auditedUid;
    }


}