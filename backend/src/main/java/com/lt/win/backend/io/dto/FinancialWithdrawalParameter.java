package com.lt.win.backend.io.dto;

import com.lt.win.backend.io.bo.StartEndTime;
import com.lt.win.service.io.bo.UserCacheBo;
import com.lt.win.utils.components.pagination.ResPage;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author: wells
 * @date: 2020/8/13
 * @description:
 */

public interface FinancialWithdrawalParameter {
    @Data
    @EqualsAndHashCode(callSuper = false)
    @ApiModel(value = "WithdrawalListReqDto", description = "提款列表请求实体类")
    class WithdrawalListReqDto extends StartEndTime {
        @ApiModelProperty(name = "uid", value = "用户Id", example = "1")
        private Integer uid;
        @ApiModelProperty(name = "username", value = "用户名", example = "1")
        private String username;
        @ApiModelProperty(name = "id", value = "订单号", example = "69784519280037888")
        private Long id;
        @ApiModelProperty(name = "bankInfo", value = "银行信息；姓名或卡号", example = "老王/69784519280037888")
        private String bankInfo;
        @ApiModelProperty(name = "adminUid", value = "操作UID", example = "1")
        private Integer adminUid;
        @ApiModelProperty(name = "status", value = "状态：0-申请中 1-提款成功 2-提款失败 3-稽核成功 4-稽核失败 9-系统出款", example = "0")
        private Integer status;
    }


    @Data
    @ApiModel(value = "WithdrawalListResDto", description = "提款列表响应实体类")
    class WithdrawalListResDto {
        @ApiModelProperty(name = "id", value = "订单号", example = "1")
        private Long id;
        @ApiModelProperty(name = "uid", value = "用户Id", example = "1")
        private Integer uid;
        @ApiModelProperty(name = "username", value = "用户名称", example = "老王")
        private String username;
        @ApiModelProperty(name = "userFlagList", value = "会员旗", example = "")
        private List<UserCacheBo.UserFlagInfo> userFlagList;
        @ApiModelProperty(name = "coin", value = "提款前金额", example = "100")
        private BigDecimal coin;
        @ApiModelProperty(name = "coinBefore", value = "当前余额", example = "100")
        private BigDecimal coinBefore;
        @ApiModelProperty(name = "accountName", value = "持卡人姓名", example = "王老五")
        private String accountName;
        @ApiModelProperty(name = "bankAccount", value = "银行卡号", example = "622513568889088900")
        private String bankAccount;
        @ApiModelProperty(name = "createdAt", value = "创建时间", example = "1595949274")
        private Integer createdAt;
        @ApiModelProperty(name = "status", value = "状态：0-申请中 1-提款成功 2-提款失败 3-稽核成功 4-稽核失败 9-系统出款", example = "0")
        private Integer status;
        @ApiModelProperty(name = "admin_uid", value = "审核uid", example = "1")
        private Integer adminUid;
        @ApiModelProperty(name = "updated_at", value = "审核时间", example = "1595949274")
        private Integer updatedAt;
        @ApiModelProperty(name = "category", value = "提现类型:0-其他 1-首次提款", example = "0")
        private Integer category;
        @ApiModelProperty(name = "accountAddress", value = "开户行地址", example = "0")
        private String accountAddress;
        @ApiModelProperty(name = "bankId", value = "银行代码", example = "1")
        public Integer bankId;
        @ApiModelProperty(name = "mark", value = "备注", example = "稽核失败，打码量不足！")
        public String mark;
        @ApiModelProperty(name = "payoutInfo", value = "代付信息", example = "代付厂商|代付金额|代付状态 {\"\":\"\"}")
        public String payoutInfo;
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
    @ApiModel(value = "UpdateWithdrawalStatusReqDto", description = "修改提款状态请求实体类")
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
    @Builder
    @ApiModel(value = "UpdateWithdrawalStatusResDto", description = "修改提款状态响应实体类")
    class UpdateWithdrawalStatusResDto {
        @ApiModelProperty(name = "auditMsg", value = "审核时弹框信息", example = "充值成功!")
        private String auditMsg;
    }

    @Data
    @ApiModel(value = "WithdrawalDetailReqDto", description = "提款记录详情请求实体")
    class WithdrawalDetailReqDto {
        @ApiModelProperty(name = "id", value = "id", example = "79681096069025792")
        private Long id;
    }

    @Data
    @EqualsAndHashCode(callSuper = false)
    @ApiModel(value = "WithdrawalDetailResDto", description = "提款记录详情响应实体类")
    class WithdrawalDetailResDto extends WithdrawalListResDto {
        @ApiModelProperty(name = "bankName", value = "开户行", example = "花旗银行")
        private String bankName;
        @ApiModelProperty(name = "bankBranchName", value = "支行", example = "香港九龙支行")
        private String bankBranchName;
        @ApiModelProperty(name = "mark", value = "备注", example = "提款成功")
        private String mark;
    }



    @Builder
    @Data
    @ApiModel(value = "AuditDetailResDto", description = "稽核详情请求实体类")
    class AuditDetailResDto {
        @ApiModelProperty(name = "id", value = "提款订单号", example = "79681096069025792")
        private Long id;
        @ApiModelProperty(name = "codeRequire", value = "所需打码量", example = "100.0")
        private BigDecimal codeRequire;
        @ApiModelProperty(name = "codeReal", value = "真实打码量", example = "100.0")
        private BigDecimal codeReal;
        @ApiModelProperty(name = "lastCodeReal", value = "最后一笔打码量", example = "50.0")
        private BigDecimal lastCodeReal;
        @ApiModelProperty(name = "extraCode", value = "额外打码量", example = "50.0")
        private BigDecimal extraCode;
        @ApiModelProperty(name = "auditStatus", value = "状态：1：稽核成功，2：稽核失败", example = "1")
        private Integer auditStatus;
        @ApiModelProperty(name = "realStatus", value = "状态：1：稽核成功，2：稽核失败", example = "1")
        private Integer realStatus;

        @ApiModelProperty(name = "CodeRecordsResDto", value = "打码量列表", example = "")
        private ResPage<CodeRecordsResDto> codeRecordsResDtoList;
    }

    @Data
    @ApiModel(value = "CodeRecordsResDto", description = "打码量列表请求实体类")
    class CodeRecordsResDto {
        @ApiModelProperty(name = "uid", value = "uid", example = "1")
        private Integer uid;
        @ApiModelProperty(name = "username", value = "用户名", example = "隔壁老王")
        private String username;
        @ApiModelProperty(name = "userFlagList", value = "会员旗", example = "隔壁老王")
        private List<UserCacheBo.UserFlagInfo> userFlagList;
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

    //状态：0-申请中 1-提款成功 2-提款失败 3-稽核成功 4-稽核失败 9-系统出款
    @Getter
    @AllArgsConstructor
    enum WithdrawalStatus {
        APPLY(0, "申请中"),
        WITHDRAWAL_SUCCESS(1, "提款成功"),
        WITHDRAWAL_FAIL(2, "提款失败"),
        AUDIT_SUCCESS(3, "稽核成功"),
        AUDIT_FAIL(4, "稽核失败"),
        SYSTEM_WITHDRAWAL(9, "系统出款");
        Integer code;
        String description;
    }
}
