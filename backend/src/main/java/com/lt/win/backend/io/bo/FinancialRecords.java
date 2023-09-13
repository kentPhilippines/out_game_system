package com.lt.win.backend.io.bo;

import com.lt.win.service.io.bo.UserCacheBo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

/**
 * @description:财务管理-财务记录
 * @author: andy
 * @date: 2020/8/14
 */
public interface FinancialRecords {

    /**
     * 出款记录-列表ReqBody
     */
    @Data
    class WithdrawalListReqBody extends StartEndTime {
        @ApiModelProperty(name = "id", value = "ID")
        private Long id;

        @ApiModelProperty(name = "uid", value = "UID")
        private Integer uid;

        @ApiModelProperty(name = "username", value = "用户名")
        private String username;

        @ApiModelProperty(name = "status", value = "状态：0-申请中 1-提款成功 2-提款失败 3-稽核成功 4-稽核失败 9-系统出款")
        private Integer status;

        @ApiModelProperty(name = "adminUid", value = "操作人ID")
        private Integer adminUid;

    }

    /**
     * 代付记录-列表ReqBody
     */
    @Data
    class OnlineWithdrawalListReqBody extends StartEndTime {
        @ApiModelProperty(name = "orderId", value = "代付订单号")
        private String orderId;
        @ApiModelProperty(name = "username", value = "用户名")
        private String username;
        @ApiModelProperty(name = "withdrawalOrderId", value = "提款订单号")
        private String withdrawalOrderId;
        @ApiModelProperty(name = "category", value = "通道类型")
        private String category;
        @ApiModelProperty(name = "status", value = "状态：0-申请中 1-成功 2-失败")
        private Integer status;

    }

    /**
     * 出款记录-列表ResBody
     */
    @Data
    class WithdrawalListResBody {
        @ApiModelProperty(name = "id", value = "ID")
        private Long id;

        @ApiModelProperty(name = "uid", value = "UID")
        private Integer uid;

        @ApiModelProperty(name = "coin", value = "提现金额")
        private BigDecimal coin;

        @ApiModelProperty(name = "coinBefore", value = "提现前资金")
        private BigDecimal coinBefore;

        @ApiModelProperty(name = "adminUid", value = "操作人ID")
        private Integer adminUid;

        @ApiModelProperty(name = "mark", value = "备注")
        private String mark;

        @ApiModelProperty(name = "status", value = "状态：0-申请中 1-提款成功 2-提款失败 3-稽核成功 4-稽核失败 9-系统出款")
        private Integer status;

        @ApiModelProperty(name = "createdAt", value = "时间")
        private Integer createdAt;

        @ApiModelProperty(name = "accountName", value = "开户姓名")
        private String accountName;

        @ApiModelProperty(name = "bankAccount", value = "银行卡号")
        private String bankAccount;

        @ApiModelProperty(name = "bankName", value = "银行名称")
        private String bankName;

        @ApiModelProperty(name = "userFlagList", value = "会员旗列表")
        List<UserCacheBo.UserFlagInfo> userFlagList;
        @ApiModelProperty(name = "username", value = "会员名")
        private String username;

        @ApiModelProperty(name = "bankInfo", value = "银行信息", hidden = true)
        private String bankInfo;

        @ApiModelProperty(name = "accountAddress", value = "开户行地址")
        private String accountAddress;
        @ApiModelProperty(name = "levelText", value = "会员等级:vip1-乒乓球达人", example = "vip1-乒乓球达人")
        private String levelText;
        @ApiModelProperty(name = "category", value = "提现类型:0-其他 1-首次提款", example = "1")
        private Integer category;
    }

    /**
     * 代付记录-列表ResBody
     */
    @Data
    class OnlineWithdrawalListResBody {
        @ApiModelProperty(name = "orderId", value = "ID")
        private String id;

        @ApiModelProperty(name = "username", value = "用户名")
        private String username;

        @ApiModelProperty(name = "withdrawalOrderId", value = "提现订单号")
        private String withdrawalOrderId;

        @ApiModelProperty(name = "payoutCode", value = "代付平台")
        private String payoutCode;

        @ApiModelProperty(name = "coin", value = "代付金额")
        private BigDecimal coin;

        @ApiModelProperty(name = "bankInfo", value = "卡号信息")
        private String bankInfo;

        @ApiModelProperty(name = "category", value = "通道")
        private Integer category;

        @ApiModelProperty(name = "createdAt", value = "代付时间")
        private Integer createdAt;
        @ApiModelProperty(name = "status", value = "状态:0-提交申请 1-成功 2-失败")
        private Integer status;

    }

    /**
     * 平台转账-列表 ReqBody
     */
    @Data
    class PlatTransferListReqBody extends StartEndTime {
        @ApiModelProperty(name = "uid", value = "UID")
        private Integer uid;

        @ApiModelProperty(name = "username", value = "用户名")
        private String username;

        @ApiModelProperty(name = "id", value = "ID或订单号")
        private String id;


        @ApiModelProperty(name = "category", value = "类型:0-上分 1-下分")
        private Integer category;

        @ApiModelProperty(name = "status", value = "状态:0-提交申请 1-成功 2-失败")
        private Integer status;


    }

    /**
     * 平台转账->状态更新:成功或失败 ReqBody
     */
    @Data
    class UpdatePlatTransferStatusByIdReqBody {
        @NotEmpty(message = "id不能为空")
        @ApiModelProperty(name = "id", value = "ID")
        private String id;

        @NotNull(message = "status不能为空")
        @ApiModelProperty(name = "status", value = "状态:1-成功 2-失败")
        private Integer status;


    }

    /**
     * 平台转账-列表 ResBody
     */
    @Data
    class PlatTransferListResBody {
        @ApiModelProperty(name = "id", value = "ID")
        private Long id;

        @ApiModelProperty(name = "orderPlat", value = "第三方平台订单号")
        private String orderPlat;

        @ApiModelProperty(name = "uid", value = "UID")
        private Integer uid;

        @ApiModelProperty(name = "coinBefore", value = "操作前金额")
        private BigDecimal coinBefore;

        @ApiModelProperty(name = "coin", value = "操作金额")
        private BigDecimal coin;

        @ApiModelProperty(name = "category", value = "类型:0-上分 1-下分")
        private Integer category;

        @ApiModelProperty(name = "mark", value = "备注")
        private String mark;

        @ApiModelProperty(name = "status", value = "状态:0-提交申请 1-成功 2-失败")
        private Integer status;

        @ApiModelProperty(name = "createdAt", value = "时间")
        private Integer createdAt;

        @ApiModelProperty(name = "userFlagList", value = "会员旗列表")
        List<UserCacheBo.UserFlagInfo> userFlagList;
        @ApiModelProperty(name = "username", value = "会员名")
        private String username;
    }

    /**
     * 平台转账-统计 ResBody
     */
    @Data
    @Builder
    class PlatTransferStatisticsResBody {
        @ApiModelProperty(name = "coinUp", value = "总转出金额")
        private BigDecimal coinUp;

        @ApiModelProperty(name = "coinDown", value = "总转入金额")
        private BigDecimal coinDown;
    }

    /**
     * 调账记录-列表ReqBody
     */
    @Data
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

        @ApiModelProperty(name = "userFlagList", value = "会员旗列表")
        List<UserCacheBo.UserFlagInfo> userFlagList;
        @ApiModelProperty(name = "username", value = "会员名")
        private String username;
    }

    /**
     * 入款记录-列表ReqBody
     */
    @Data
    class DepositListReqBody extends StartEndTime {
        @ApiModelProperty(name = "id", value = "ID或订单号")
        private String id;

        @ApiModelProperty(name = "uid", value = "UID")
        private Integer uid;

        @ApiModelProperty(name = "username", value = "用户名")
        private String username;

        @ApiModelProperty(name = "payType", value = "充值类型:0-离线 1-在线")
        private Integer payType;

        @ApiModelProperty(name = "category", value = "充值方式:1-银联 2-微信 3-支付宝 4-QQ 5-QR扫码")
        private Integer category;

        @ApiModelProperty(name = "adminId", value = "操作人ID")
        private Integer adminId;

        @ApiModelProperty(name = "status", value = "状态:1-手动到账 2-自动到账 3-充值失败 9-管理员充值")
        private Integer status;

        @ApiModelProperty(name = "depRealname", value = "打款人姓名")
        private String depRealname;
    }

    /**
     * 入款记录-列表ResBody
     */
    @Data
    class DepositListResBody {
        @ApiModelProperty(name = "id", value = "ID")
        private Long id;

        @ApiModelProperty(name = "orderId", value = "订单号")
        private String orderId;

        @ApiModelProperty(name = "uid", value = "UID")
        private Integer uid;

        @ApiModelProperty(name = "payType", value = "充值类型:0-离线 1-在线")
        private Integer payType;

        @ApiModelProperty(name = "category", value = "充值方式:1-银联 2-微信 3-支付宝 4-QQ 5-QR扫码")
        private Integer category;

        @ApiModelProperty(name = "coin", value = "提交金额")
        private BigDecimal coin;

        @ApiModelProperty(name = "payCoin", value = "到账金额")
        private BigDecimal payCoin;

        @ApiModelProperty(name = "depRealname", value = "打款人姓名")
        private String depRealname;

        @ApiModelProperty(name = "status", value = "状态:1-手动到账 2-自动到账 3-充值失败 9-管理员充值,对应字典dic_coin_deposit_status_finally")
        private Integer status;

        @ApiModelProperty(name = "depMark", value = "打款人备注")
        private String depMark;

        @ApiModelProperty(name = "adminId", value = "操作人ID")
        private Integer adminId;

        @ApiModelProperty(name = "createdAt", value = "时间")
        private Integer createdAt;

        @ApiModelProperty(name = "userFlagList", value = "会员旗列表")
        List<UserCacheBo.UserFlagInfo> userFlagList;

        @ApiModelProperty(name = "username", value = "会员名")
        private String username;

        @ApiModelProperty(name = "depStatus", value = "充值标识:1-首充 2-二充 3-三充 9-其他;字典：dic_coin_deposit_dep_status", example = "1")
        private Integer depStatus;
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
     *
     */
    @Data
    class UpdateOnlineWithdrawalReqBody {
        @NotNull(message = "OrderId不能为空")
        @ApiModelProperty(name = "OrderId", value = "代付订单号")
        private String orderId;
        @NotNull(message = "withdrawalOrderId不能为空")
        @ApiModelProperty(name = "withdrawalOrderId", value = "提款订单号")
        private String withdrawalOrderId;
        @Max(value = 2, message = "1-成功 2-失败 ")
        @Min(value = 1, message = "1-成功 2-失败")
        @NotNull(message = "status不能为空")
        @ApiModelProperty(name = "status", value = "状态： 1-成功 2-失败")
        private Integer status;

    }
}
