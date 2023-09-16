package com.lt.win.backend.io.dto;

import com.lt.win.backend.io.bo.StartEndTime;
import com.lt.win.utils.components.pagination.ResPage;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

/**
 * @Auther: wells
 * @Date: 2022/8/24 01:45
 * @Description:
 */
public interface FinanceManageParams {

    @Data
    @ApiModel(value = "DepositRecordReq", description = "充值记录请求实体")
    class DepositRecordReq {
        @ApiModelProperty(name = "id", value = "id", example = "71286223372685312")
        private Long id;
        @ApiModelProperty(name = "uid", value = "用户Id", example = "1")
        private Integer uid;
        @ApiModelProperty(name = "code", value = "支付通道code", example = "TRC-20")
        private String code;
        @ApiModelProperty(name = "status", value = "状态: 0-申请中 1-成功;字典:dic_deposit_record_status", example = "1")
        private Integer status;
        @ApiModelProperty(name = "category", value = "类型:0-钱包充值 1-佣金钱包转账充值;字典:dic_deposit_record_category", example = "1")
        private Integer category;
        @ApiModelProperty(name = "username", value = "用户名", example = "1")
        private String username;
        @ApiModelProperty(name = "orderId", value = "订单号(三方平台用)", example = "I2020071517060230457")
        private String orderId;
        @ApiModelProperty(name = "startTime", value = "开始时间", example = "1600448057")
        private Integer startTime;
        @ApiModelProperty(name = "endTime", value = "结束时间", example = "1600559067")
        private Integer endTime;
    }

    @Data
    @ApiModel(value = "DepositRecordRes", description = "充值记录响应实体")
    class DepositRecordRes {
        @ApiModelProperty(name = "id", value = "id", example = "71286223372685312")
        private Long id;
        @ApiModelProperty(name = "username", value = "用户名", example = "1")
        private String username;
        @ApiModelProperty(name = "code", value = "支付通道code", example = "TRC-20")
        private String code;
        @ApiModelProperty(name = "platName", value = "支付平台名称", example = "TRC-20")
        private String platName;
        @ApiModelProperty(name = "orderId", value = "订单号", example = "O2022081102422478706")
        private String orderId;
        @ApiModelProperty(name = "platOrderId", value = "三方支付订单号", example = "OP20221001021")
        private String platOrderId;
        @ApiModelProperty(name = "payAmount", value = "充值金额", example = "99.99")
        private String payAmount;
        @ApiModelProperty(name = "payAddress", value = "加密地址", example = "TEV6MRrbcQAGwzkb6pqMoHHb9b22xXb3zf")
        private String payAddress;
        @ApiModelProperty(name = "realAmount", value = "到账金额", example = "99.99")
        private String realAmount;
        @ApiModelProperty(name = "exchangeRate", value = "汇率", example = "0.9")
        private BigDecimal exchangeRate;
        @ApiModelProperty(name = "depStatus", value = "充值标识:1-首充 2-二充 9-其他；字典:dic_coin_deposit_dep_status", example = "1")
        private Integer depStatus;
        @ApiModelProperty(name = "category", value = "类型:0-钱包充值 1-佣金钱包转账充值;字典:dic_deposit_record_category", example = "1")
        private Integer category;
        @ApiModelProperty(name = "status", value = "状态: 0-申请中 1-成功;字典:dic_deposit_record_status", example = "1")
        private Integer status;
        @ApiModelProperty(name = "createdAt", value = "创建时间", example = "1600448057")
        private Integer createdAt;
        @ApiModelProperty(name = "updatedAt", value = "到账时间；状态不成功则不展示", example = "1600448057")
        private Integer updatedAt;
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
    @Builder
    @ApiModel(value = "DepositSumResDto", description = "充值汇总响应实体类")
    class DepositSumResDto {
        @ApiModelProperty(name = "count", value = "充值笔数", example = "10")
        private Integer count = 0;
        @ApiModelProperty(name = "totalCoin", value = "充值总金额", example = "99.99")
        private BigDecimal totalCoin = BigDecimal.ZERO;
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
    class DepositDetailResDto extends DepositRecordRes {
        @ApiModelProperty(name = "uid", value = "用户Id", example = "1")
        private Integer uid;
        @ApiModelProperty(name = "coinBefore", value = "当时余额", example = "100.12")
        private BigDecimal coinBefore;
        @ApiModelProperty(name = "updatedAt", value = "到账时间", example = "1600448057")
        private Integer updatedAt;
    }

    @Data
    @ApiModel(value = "WithdrawalRecordReq", description = "提款记录请求实体")
    class WithdrawalRecordReq extends StartEndTime {
        @ApiModelProperty(name = "id", value = "id", example = "71286223372685312")
        private Long id;
        @ApiModelProperty(name = "code", value = "支付通道code", example = "TRC-20")
        private String code;
        @ApiModelProperty(name = "username", value = "用户名", example = "1")
        private String username;
        @ApiModelProperty(name = "uid", value = "用户Id", example = "1")
        private Integer uid;
        @ApiModelProperty(name = "adminUid", value = "审核用户Id", example = "1")
        private Integer adminUid;
        @ApiModelProperty(name = "platName", value = "支付通道名称", example = "TRC-20")
        private String platName;
        @ApiModelProperty(name = "status", value = "状态: 0-申请中，1-成功，2-失败;字典:dic_withdrawal_record_status", example = "1")
        private Integer status;
    }

    @Data
    @ApiModel(value = "CoinWithdrawalRecordRes", description = "提款记录响应实体")
    class WithdrawalRecordRes {
        @ApiModelProperty(name = "id", value = "id", example = "71286223372685312")
        private Long id;
        @ApiModelProperty(name = "orderId", value = "订单号", example = "1")
        private String orderId;
        @ApiModelProperty(name = "platOrderId", value = "三方支付订单号", example = "G20221001021")
        private String platOrderId;
        @ApiModelProperty(name = "uid", value = "用户ID", example = "1")
        private Integer uid;
        @ApiModelProperty(name = "username", value = "用户名", example = "1")
        private String username;
        @ApiModelProperty(name = "platName", value = "平台名称", example = "pay_cloud")
        private String platName;
        @ApiModelProperty(name = "code", value = "支付通道", example = "TRC-20")
        private String code;
        @ApiModelProperty(name = "withdrawalAddress", value = "提款地址", example = "TEV6MRrbcQAGwzkb6pqMoHHb9b22xXb3zf")
        private String withdrawalAddress;
        @ApiModelProperty(name = "withdrawalAmount", value = "提款金额", example = "99.99")
        private String withdrawalAmount;
        @ApiModelProperty(name = "coinBefore", value = "当前余额", example = "99.99")
        private BigDecimal coinBefore;
        @ApiModelProperty(name = "exchangeRate", value = "汇率", example = "0.9")
        private String exchangeRate;
        @ApiModelProperty(name = "mainNetFees", value = "主网费", example = "5.00")
        private String mainNetFees;
        @ApiModelProperty(name = "realAmount", value = "到账金额", example = "99.99")
        private String realAmount;
        @ApiModelProperty(name = "status", value = "状态: 0-申请中，1-成功，2-失败;字典:dic_withdrawal_record_status", example = "1")
        private Integer status;
        @ApiModelProperty(name = "adminUid", value = "审核人ID", example = "1")
        private Integer adminUid;
        @ApiModelProperty(name = "mark", value = "备注", example = "提款100")
        private String mark;
        @ApiModelProperty(name = "createdAt", value = "创建时间", example = "1600448057")
        private Integer createdAt;
        @ApiModelProperty(name = "updatedAt", value = "审核时间", example = "1600448057")
        private Integer updatedAt;
        @ApiModelProperty(name = "withdrawalChannelList", value = "通道列表", example = "")
        private List<WithdrawalChannelList> withdrawalChannelList;
    }

    @Data
    class WithdrawalChannelList {
        @ApiModelProperty(name = "code", value = "通道编号", example = "TRC-20")
        private String code;
        @ApiModelProperty(name = "name", value = "通道名称", example = "TRC-20_USDT")
        private String name;
    }

    @Data
    @Builder
    @ApiModel(value = "WithdrawalSumResDto", description = "提款汇总响应实体类")
    class WithdrawalSumResDto {
        @ApiModelProperty(name = "count", value = "提款笔数", example = "10")
        private Integer count = 0;
        @ApiModelProperty(name = "totalCoin", value = "提款总金额", example = "99.99")
        private BigDecimal totalCoin = BigDecimal.ZERO;
    }


    @Data
    @Builder
    @ApiModel(value = "UpdateWithdrawalStatusRes", description = "修改提款状态响应实体类")
    class UpdateWithdrawalStatusResDto {
        @ApiModelProperty(name = "auditMsg", value = "审核时弹框信息", example = "充值成功!")
        private String auditMsg;
        @ApiModelProperty(name = "auditFlag", value = "是否稽核成功", example = "false!")
        private boolean auditFlag;
    }

    @Data
    @ApiModel(value = "UpdateWithdrawalStatusReq", description = "修改提款状态请求实体类")
    class UpdateWithdrawalStatusReqDto {
        @NotNull(message = "id不能为空")
        @ApiModelProperty(name = "id", value = "id", example = "79681096069025792")
        private Long id;
        @ApiModelProperty(name = "status", value = "状态：0-申请中 1-提款成功 2-提款失败 3-稽核成功 4-稽核失败 9-系统出款", example = "0")
        private Integer status;
        @ApiModelProperty(name = "mark", value = "备注", example = "通过")
        private String mark;
    }

    @Data
    @ApiModel(value = "WithdrawalDetailReqDto", description = "提款记录详情请求实体")
    class WithdrawalDetailReqDto {
        @ApiModelProperty(name = "id", value = "id", example = "79681096069025792")
        private Long id;
    }

    @Data
    @EqualsAndHashCode(callSuper = false)
    @ApiModel(value = "CoinWithdrawalDetailResDto", description = "提款记录详情响应实体类")
    class WithdrawalDetailResDto extends WithdrawalRecordRes {
        @ApiModelProperty(name = "admin_uid", value = "审核uid", example = "1")
        private Integer adminUid;
        @ApiModelProperty(name = "mark", value = "备注", example = "提款成功")
        private String mark;
    }


    @Data
    @ApiModel(value = "AdminTransferReqDto", description = "人工调整请求实体类")
    class AdminTransferReqDto {
        @NotNull(message = "uid不能为空")
        @ApiModelProperty(name = "uid", value = "uid", example = "12")
        private Integer uid;
        @NotNull(message = "调整金额不能为空")
        @ApiModelProperty(name = "coin", value = "调整金额", example = "100.00")
        private BigDecimal coin;
        @ApiModelProperty(name = "category", value = "调整项目；调账原因:0-其他 1-误存调账 2-活动调账", example = "1")
        private Integer category;

        @ApiModelProperty(name = "operatorType", value = "金额操作类型；1：系统调账，2：活动派彩，3：后台充值", example = "1")
        private Integer operatorType;
        @ApiModelProperty(name = "mark", value = "备注", example = "转账异常")
        private String mark;
        @NotNull(message = "type 不能为空")
        @ApiModelProperty(name = "type", value = "加款或者减款 add:加款 pop:减款", example = "类型")
        private String type;
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
    @ApiModel(value = "AuditRes", description = "稽核请求实体类")
    class AuditReqDto {
        @ApiModelProperty(name = "id", value = "提款订单号", example = "79681096069025792")
        private Long id;
    }

    @Data
    @ApiModel(value = "UserCoinReq", description = "用户余额请求实体类")
    class UserCoinReq {
        @ApiModelProperty(name = "username", value = "用户名", example = "test01")
        private String username;
    }

    @Data
    @ApiModel(value = "UserCoinRes", description = "用户余额响应实体类")
    class UserCoinRes {
        @ApiModelProperty(name = "uid", value = "用户uid", example = "100.00")
        private Integer uid;
        @ApiModelProperty(name = "coin", value = "用户余额", example = "100.00")
        private BigDecimal coin;
    }


    @Builder
    @Data
    @ApiModel(value = "AuditDetailRes", description = "稽核详情请求实体类")
    class AuditDetailResDto {
        @ApiModelProperty(name = "id", value = "提款订单号", example = "79681096069025792")
        private Long id;
        @ApiModelProperty(name = "codeRequire", value = "所需打码量", example = "100.0")
        private BigDecimal codeRequire;
        @ApiModelProperty(name = "codeReal", value = "真实打码量", example = "100.0")
        private BigDecimal codeReal;
        @ApiModelProperty(name = "auditStatus", value = "状态：1：稽核成功，2：稽核失败", example = "1")
        private Integer auditStatus;
        @ApiModelProperty(name = "CodeRecordsResDto", value = "打码量列表", example = "")
        private ResPage<CodeRecordsResDto> codeRecordsResDtoList;
    }

    @Data
    @ApiModel(value = "CodeRecordsRes", description = "打码量列表请求实体类")
    class CodeRecordsResDto {
        @ApiModelProperty(name = "uid", value = "uid", example = "1")
        private Integer uid;
        @ApiModelProperty(name = "username", value = "用户名", example = "隔壁老王")
        private String username;
        @ApiModelProperty(name = "referId", value = "关联id", example = "69743416925360128")
        private Long referId;
        @ApiModelProperty(name = "category", value = "类型", example = "1")
        private Integer category;
        @ApiModelProperty(name = "coin", value = "金额", example = "102.00")
        private BigDecimal coin;
        @ApiModelProperty(name = "codeRequire", value = "所需打码量", example = "102.00")
        private BigDecimal codeRequire;
        @ApiModelProperty(name = "status", value = "状态:0-未结算 1-结算", example = "1")
        private Integer status;
        @ApiModelProperty(name = "createdAt", value = "创建时间", example = "1594436126")
        private Integer createdAt;
    }

    @Data
    @ApiModel(value = "PlatConfigRes", description = "平台配置响应实体")
    class PlatConfigRes {
        @ApiModelProperty(name = "code", value = "支付平台编码", example = "TRC-20")
        private String code;
        @ApiModelProperty(name = "name", value = "支付通道名称", example = "TRC-20")
        private String name;
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
    @ApiModel(value = "ReplenishmentOrderReq", description = "补单请求参数")
    class ReplenishmentOrderReq {
        @ApiModelProperty(name = "id", value = "记录ID", example = "79681096069025792")
        private Long id;
        @ApiModelProperty(name = "status", value = "状态:1-成功 2-失败", example = "1")
        private Integer status;
        @ApiModelProperty(name = "mark", value = "备注", example = "补单100")
        private String mark;

    }
}
