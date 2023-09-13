package com.lt.win.backend.io.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * <p>
 * 游戏管理
 * </p>
 *
 * @author andy
 * @since 2020/6/8
 */
public interface PayManager {
    /**
     * 线上平台配置-列表ReqBody
     */
    @Data
    class PayPlatListReqBody {
        @ApiModelProperty(name = "name", value = "名称(中文)", example = "微码支付")
        private String name;

        @ApiModelProperty(name = "businessCode", value = "商户号", example = "101550")
        private String businessCode;

        @ApiModelProperty(name = "status", value = "状态:0-停用 1-启用 2-删除")
        private Integer status;
    }

    /**
     * 线上平台配置-列表ResBody
     */
    @Data
    class PayPlatListResBody {
        @ApiModelProperty(name = "id", value = "ID", example = "1")
        private Integer id;

        @ApiModelProperty(name = "code", value = "编码", example = "mango_pay")
        private String code;

        @ApiModelProperty(name = "name", value = "名称(中文)", example = "微码支付")
        private String name;

        @ApiModelProperty(name = "businessCode", value = "商户号", example = "101550")
        private String businessCode;

        @ApiModelProperty(name = "status", value = "状态:0-停用 1-启用 2-删除")
        private Integer status;

        @ApiModelProperty(name = "createdAt", value = "创建时间")
        private Integer createdAt;

        @ApiModelProperty(name = "updatedAt", value = "更新时间")
        private Integer updatedAt;

    }

    /**
     * 线上平台配置-新增ReqBody
     */
    @Data
    class PayPlatAddReqBody {

        @NotBlank(message = "code不能为空")
        @ApiModelProperty(name = "code", value = "编码", example = "mango_pay")
        private String code;

        @NotBlank(message = "name不能为空")
        @ApiModelProperty(name = "name", value = "名称(中文)", example = "微码支付")
        private String name;

        @NotBlank(message = "businessCode不能为空")
        @ApiModelProperty(name = "businessCode", value = "商户号", example = "101550")
        private String businessCode;

        @NotBlank(message = "businessPwd不能为空")
        @ApiModelProperty(name = "businessPwd", value = "商户秘钥", example = "e8eee466368501d48af31d80d4c21477")
        private String businessPwd;

        @NotBlank(message = "publicKey不能为空")
        @ApiModelProperty(name = "publicKey", value = "公钥")
        private String publicKey;

        @NotBlank(message = "privateKey不能为空")
        @ApiModelProperty(name = "privateKey", value = "私钥")
        private String privateKey;

        @NotBlank(message = "notifyUrl不能为空")
        @ApiModelProperty(name = "url", value = "付款地址")
        private String url;

        @NotBlank(message = "notifyUrl不能为空")
        @ApiModelProperty(name = "notifyUrl", value = "异步通知回调")
        private String notifyUrl;

        @NotBlank(message = "returnUrl不能为空")
        @ApiModelProperty(name = "returnUrl", value = "同步通知回调")
        private String returnUrl;

        @NotBlank(message = "returnUrl不能为空")
        @ApiModelProperty(name = "payModel", value = "支付模型", example = "MangoPayUtil01")
        private String payModel;
    }

    /**
     * 线上平台配置-修改ReqBody
     */
    @Data
    class
    PayPlatUpdateReqBody {
        @NotNull(message = "id不能为空")
        @ApiModelProperty(name = "id", value = "ID")
        private Integer id;

        @NotNull(message = "id不能为空")
        @ApiModelProperty(name = "status", value = "状态:0-停用 1-启用 2-删除")
        private Integer status;

//        @ApiModelProperty(name = "code", value = "编码", example = "mango_pay")
//        private String code;
//
//        @ApiModelProperty(name = "name", value = "名称(中文)", example = "微码支付")
//        private String name;
//
//        @ApiModelProperty(name = "businessCode", value = "商户号", example = "101550")
//        private String businessCode;
//
//        @ApiModelProperty(name = "businessPwd", value = "商户秘钥", example = "e8eee466368501d48af31d80d4c21477")
//        private String businessPwd;
//
//        @ApiModelProperty(name = "publicKey", value = "公钥")
//        private String publicKey;
//
//        @ApiModelProperty(name = "privateKey", value = "私钥")
//        private String privateKey;
//
//        @ApiModelProperty(name = "url", value = "付款地址")
//        private String url;
//
//        @ApiModelProperty(name = "notifyUrl", value = "异步通知回调")
//        private String notifyUrl;
//
//        @ApiModelProperty(name = "returnUrl", value = "同步通知回调")
//        private String returnUrl;
//
//        @ApiModelProperty(name = "payModel", value = "支付模型", example = "MangoPayUtil01")
//        private String payModel;
    }

    /**
     * 线上平台配置-详情ResBody
     */
    @Data
    class PayPlatDetailResBody {
        @ApiModelProperty(name = "id", value = "ID", example = "1")
        private Integer id;

        @ApiModelProperty(name = "code", value = "编码", example = "mango_pay")
        private String code;

        @ApiModelProperty(name = "name", value = "名称(中文)", example = "微码支付")
        private String name;

        @ApiModelProperty(name = "businessCode", value = "商户号", example = "101550")
        private String businessCode;

        @ApiModelProperty(name = "businessPwd", value = "商户秘钥", example = "e8eee466368501d48af31d80d4c21477")
        private String businessPwd;

        @ApiModelProperty(name = "publicKey", value = "公钥")
        private String publicKey;

        @ApiModelProperty(name = "privateKey", value = "私钥")
        private String privateKey;

        @ApiModelProperty(name = "url", value = "付款地址")
        private String url;

        @ApiModelProperty(name = "notifyUrl", value = "异步通知回调")
        private String notifyUrl;

        @ApiModelProperty(name = "returnUrl", value = "同步通知回调")
        private String returnUrl;

        @ApiModelProperty(name = "status", value = "状态:0-停用 1-启用 2-删除")
        private Integer status;

        @ApiModelProperty(name = "createdAt", value = "时间")
        private Integer createdAt;

        @ApiModelProperty(name = "payModel", value = "支付模型", example = "MangoPayUtil01")
        private String payModel;
    }

    /**
     * 线上渠道配置-列表
     */
    @Data
    class PayOnLineListReqBody {
        @ApiModelProperty(name = "payName", value = "支付名称")
        private String payName;
        @ApiModelProperty(name = "category", value = "类型:1-银联 2-微信 3-支付宝 4-QQ 5-QR扫码")
        private Integer category;
        @ApiModelProperty(name = "status", value = "状态:1-启用 0-停用 2-删除")
        private Integer status;
    }

    @Data
    class PayoutOnLineListReqBody {
        @ApiModelProperty(name = "payName", value = "支付名称")
        private String payName;
        @ApiModelProperty(name = "status", value = "状态:1-启用 0-停用 2-删除")
        private Integer status;
    }

    /**
     * 线上渠道配置-列表
     */
    @Data
    class PayOnLineListResBody {
        @ApiModelProperty(name = "id", value = "ID")
        private Integer id;

        @ApiModelProperty(name = "code", value = "三方Code")
        private String code;

        @ApiModelProperty(name = "payName", value = "支付名称")
        private String payName;

        @ApiModelProperty(name = "coinMin", value = "最低支付金额")
        private Integer coinMin;

        @ApiModelProperty(name = "coinMax", value = "最高支付金额")
        private Integer coinMax;

        @ApiModelProperty(name = "category", value = "类型:1-银联 2-微信 3-支付宝 4-QQ 5-QR扫码")
        private Integer category;

        @ApiModelProperty(name = "sort", value = "排序:从高到低")
        private Integer sort;

        @ApiModelProperty(name = "mark", value = "备注信息")
        private String mark;

        @ApiModelProperty(name = "status", value = "状态:1-启用 0-停用 2-删除")
        private Integer status;

        @ApiModelProperty(name = "createdAt", value = "时间")
        private Integer createdAt;

        @ApiModelProperty(name = "updatedAt", value = "更新时间")
        private Integer updatedAt;

        @ApiModelProperty(name = "levelBitAllList", value = "所有会员等级List")
        private List<LevelBitBo> levelBitAllList;

        @ApiModelProperty(name = "levelBitCurrentList", value = "当前记录支持的会员等级List")
        private List<LevelBitBo> levelBitCurrentList;
    }

    @Data
    class PayoutOnLineListResBody {
        @ApiModelProperty(name = "id", value = "ID")
        private Integer id;

        @ApiModelProperty(name = "payName", value = "支付名称")
        private String payName;

        @ApiModelProperty(name = "coinMin", value = "最低支付金额")
        private Integer coinMin;

        @ApiModelProperty(name = "coinMax", value = "最高支付金额")
        private Integer coinMax;

        @ApiModelProperty(name = "levelBitAllList", value = "所有会员等级List")
        private List<LevelBitBo> levelBitAllList;

        @ApiModelProperty(name = "levelBitCurrentList", value = "当前记录支持的会员等级List")
        private List<LevelBitBo> levelBitCurrentList;

        @ApiModelProperty(name = "createdAt", value = "时间")
        private Integer createdAt;

        @ApiModelProperty(name = "status", value = "状态:1-启用 0-停用 2-删除")
        private Integer status;
    }

    /**
     * 线上渠道配置-修改ReqBody
     */
    @Data
    class PayOnLineUpdateReqBody {
        @NotNull(message = "id不能为空")
        @ApiModelProperty(name = "id", value = "ID")
        private Integer id;

        @ApiModelProperty(name = "coinMin", value = "最低支付金额")
        private Integer coinMin;

        @ApiModelProperty(name = "coinMax", value = "最高支付金额")
        private Integer coinMax;

        @ApiModelProperty(name = "levelBit", value = "支付分层(user_level位运算和)")
        private Integer levelBit;

        @ApiModelProperty(name = "status", value = "状态:0-停用 1-启用 2-删除")
        private Integer status;
    }

    /**
     * 线下渠道配置-列表
     */
    @Data
    class PayOffLineListReqBody {
        @ApiModelProperty(name = "userName", value = "持卡人")
        private String userName;
        @ApiModelProperty(name = "category", value = "类型:1-银联 2-微信 3-支付宝 4-QQ 5-QR扫码")
        private Integer category;
        @ApiModelProperty(name = "status", value = "状态:1-启用 0-停用 2-删除")
        private Integer status;
    }

    /**
     * 线下渠道配置-列表
     */
    @Data
    class PayOffLineListResBody {
        @ApiModelProperty(name = "id", value = "ID")
        private Integer id;

        @ApiModelProperty(name = "userName", value = "持卡人")
        private String userName;

        @ApiModelProperty(name = "bankName", value = "收款银行")
        private String bankName;

        @ApiModelProperty(name = "bankAccount", value = "收款卡号")
        private String bankAccount;

        @ApiModelProperty(name = "bankAddress", value = "收款开户行地址")
        private String bankAddress;

        @ApiModelProperty(name = "qrCode", value = "收款二维码")
        private String qrCode;

        @ApiModelProperty(name = "coinMin", value = "最低支付")
        private Integer coinMin;

        @ApiModelProperty(name = "coinMax", value = "最高支付")
        private Integer coinMax;

        @ApiModelProperty(name = "category", value = "类型:1-银联 2-微信 3-支付宝 4-QQ 5-QR扫码")
        private Integer category;

        @ApiModelProperty(name = "sort", value = "排序:从高到低")
        private Integer sort;

        @ApiModelProperty(name = "status", value = "状态:1-启用 0-停用 2-删除")
        private Integer status;

        @ApiModelProperty(name = "createdAt", value = "时间")
        private Integer createdAt;

        @ApiModelProperty(name = "updatedAt", value = "更新时间")
        private Integer updatedAt;

        @ApiModelProperty(name = "bankId", value = "银行ID")
        private Integer bankId;

        @ApiModelProperty(name = "levelBitAllList", value = "所有会员等级List")
        private List<LevelBitBo> levelBitAllList;

        @ApiModelProperty(name = "levelBitCurrentList", value = "当前记录支持的会员等级List")
        private List<LevelBitBo> levelBitCurrentList;
    }

    /**
     * 线下渠道配置-新增ReqBody
     */
    @Data
    class PayOffLineAddReqBody {
        @ApiModelProperty(name = "userName", value = "持卡人")
        private String userName;

        @ApiModelProperty(name = "bankAccount", value = "收款卡号")
        private String bankAccount;

        @ApiModelProperty(name = "bankAddress", value = "收款开户行地址")
        private String bankAddress;

        @ApiModelProperty(name = "qrCode", value = "收款二维码")
        private String qrCode;

        @NotNull(message = "coinMin不能为空")
        @ApiModelProperty(name = "coinMin", value = "最低支付")
        private Integer coinMin;

        @NotNull(message = "coinMax不能为空")
        @ApiModelProperty(name = "coinMax", value = "最高支付")
        private Integer coinMax;

        @ApiModelProperty(name = "category", value = "类型:1-银联 2-微信 3-支付宝 4-QQ 5-QR扫码 6-upi pay")
        private Integer category;

        @ApiModelProperty(name = "sort", value = "排序:从高到低")
        private Integer sort;

        @ApiModelProperty(name = "bankId", value = "银行ID")
        private Integer bankId;

        @NotNull(message = "levelBit不能为空")
        @ApiModelProperty(name = "levelBit", value = "支付分层(user_level位运算和)")
        private Integer levelBit;

        @NotNull(message = "status不能为空")
        @ApiModelProperty(name = "status", value = "状态:1-启用 0-停用 2-删除")
        private Integer status;

        @ApiModelProperty(name = "contactType", value = "联系方式:1-在线客服 2-QQ 3-微信 4-WhatsApp")
        private Integer contactType;

        @ApiModelProperty(name = "contactNubmber", value = "联系号码")
        private String contactNubmber;
    }

    /**
     * 线下渠道配置-修改ReqBody
     */
    @Data
    class PayOffLineUpdateReqBody {
        @NotNull(message = "id不能为空")
        @ApiModelProperty(name = "id", value = "ID")
        private Integer id;

        @ApiModelProperty(name = "userName", value = "持卡人")
        private String userName;

        @ApiModelProperty(name = "bankAccount", value = "收款卡号")
        private String bankAccount;

        @ApiModelProperty(name = "bankAddress", value = "收款开户行地址")
        private String bankAddress;

        @ApiModelProperty(name = "qrCode", value = "收款二维码")
        private String qrCode;

        @ApiModelProperty(name = "coinMin", value = "最低支付")
        private Integer coinMin;

        @ApiModelProperty(name = "coinMax", value = "最高支付")
        private Integer coinMax;

        @ApiModelProperty(name = "category", value = "类型:1-银联 2-微信 3-支付宝 4-QQ 5-QR扫码")
        private Integer category;

        @ApiModelProperty(name = "sort", value = "排序:从高到低")
        private Integer sort;

        @ApiModelProperty(name = "bankId", value = "银行ID")
        private Integer bankId;

        @ApiModelProperty(name = "levelBit", value = "支付分层(user_level位运算和)")
        private Integer levelBit;

        @ApiModelProperty(name = "status", value = "状态:1-启用 0-停用 2-删除")
        private Integer status;

        @ApiModelProperty(name = "contactType", value = "联系方式:1-在线客服 2-QQ 3-微信 4-WhatsApp")
        private Integer contactType;

        @ApiModelProperty(name = "contactNubmber", value = "联系号码")
        private String contactNubmber;
    }

    @Data
    class PayoutOnLineUpdateReqBody {
        @NotNull(message = "id不能为空")
        @ApiModelProperty(name = "id", value = "ID")
        private Integer id;

        @ApiModelProperty(name = "coinMin", value = "最低支付")
        private Integer coinMin;

        @ApiModelProperty(name = "coinMax", value = "最高支付")
        private Integer coinMax;

        @ApiModelProperty(name = "levelBit", value = "支付分层(user_level位运算和)")
        private Integer levelBit;

        @ApiModelProperty(name = "status", value = "状态:1-启用 0-停用 2-删除")
        private Integer status;
    }

    /**
     * 线下渠道配置-详情ResBody
     */
    @Data
    class PayOffLineDetailResBody {

        @ApiModelProperty(name = "id", value = "ID")
        private Integer id;

        @ApiModelProperty(name = "userName", value = "持卡人")
        private String userName;

        @ApiModelProperty(name = "bankName", value = "收款银行")
        private String bankName;

        @ApiModelProperty(name = "bankAccount", value = "收款卡号")
        private String bankAccount;

        @ApiModelProperty(name = "bankAddress", value = "收款开户行地址")
        private String bankAddress;

        @ApiModelProperty(name = "qrCode", value = "收款二维码")
        private String qrCode;

        @ApiModelProperty(name = "coinMin", value = "最低支付")
        private Integer coinMin;

        @ApiModelProperty(name = "coinMax", value = "最高支付")
        private Integer coinMax;

        @ApiModelProperty(name = "category", value = "类型:1-银联 2-微信 3-支付宝 4-QQ 5-QR扫码")
        private Integer category;

        @ApiModelProperty(name = "sort", value = "排序:从高到低")
        private Integer sort;

        @ApiModelProperty(name = "status", value = "状态:1-启用 0-停用 2-删除")
        private Integer status;

        @ApiModelProperty(name = "createdAt", value = "时间")
        private Integer createdAt;

        @ApiModelProperty(name = "updatedAt", value = "更新时间")
        private Integer updatedAt;

        @ApiModelProperty(name = "levelBitCurrentList", value = "当前记录支持的会员等级List")
        private List<LevelBitBo> levelBitCurrentList;

        @ApiModelProperty(name = "contactType", value = "联系方式:1-在线客服 2-QQ 3-微信 4-WhatsApp")
        private Integer contactType;

        @ApiModelProperty(name = "contactNubmber", value = "联系号码")
        private String contactNubmber;
    }

    /**
     * 公用ID请求类
     */
    @Data
    class CommonIdReq {
        @NotNull(message = "id不能为空")
        @ApiModelProperty(name = "id", value = "ID")
        private Integer id;
    }

    /**
     * 会员等级
     */
    @Data
    class LevelBitBo {
        @ApiModelProperty(name = "id", value = "ID")
        private Integer id;
        @ApiModelProperty(name = "code", value = "会员等级")
        private String code;
    }

    @Data
    class PayoutOnLineAddReqBody {
        @NotNull(message = "code不能为空")
        @ApiModelProperty(name = "code", value = "支付代码")
        private String code;
        @NotNull(message = "payName不能为空")
        @ApiModelProperty(name = "payName", value = "支付名称")
        private String payName;
        @NotNull(message = "payModel不能为空")
        @ApiModelProperty(name = "payModel", value = "支付模型")
        private String payModel;
        @NotNull(message = "coinMin不能为空")
        @ApiModelProperty(name = "coinMin", value = "最低支付")
        private Integer coinMin;
        @NotNull(message = "coinMax不能为空")
        @ApiModelProperty(name = "coinMax", value = "最高支付")
        private Integer coinMax;
        @NotNull(message = "category不能为空")
        @ApiModelProperty(name = "category", value = "类型:1-银联 2-微信 3-支付宝 4-QQ 5-QR扫码 6-upi")
        private Integer category;
        @ApiModelProperty(name = "sort", value = "排序:从高到低")
        private Integer sort;
        @ApiModelProperty(name = "mark", value = "备注")
        private String mark;
        @NotNull(message = "levelBit不能为空")
        @ApiModelProperty(name = "levelBit", value = "支付分层(user_level位运算和)")
        private Integer levelBit;
        @NotNull(message = "status不能为空")
        @ApiModelProperty(name = "status", value = "状态:1-启用 0-停用 2-删除")
        private Integer status;

    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    class ListBankReqDto {
        @ApiModelProperty(name = "bankId", value = "银行代码", example = "1")
        public Integer bankId;
        @NotNull(message = "uid不能为空")
        @ApiModelProperty(name = "uid", value = "用户id", example = "1")
        public Integer uid;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    class PayoutBankList {
        @ApiModelProperty(name = "payoutCode", value = "代付厂商")
        private String payoutCode;
        @ApiModelProperty(name = "payoutList", value = "代付厂商")
        private List<PayoutList> payoutList;


    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    class PayoutList {
        @ApiModelProperty(name = "id", value = "代付id")
        private Integer id;
        @ApiModelProperty(name = "payoutChannel", value = "代付通道")
        private String payoutChannel;
        @ApiModelProperty(name = "bankLists", value = "代付银行列表")
        private List<BankList> bankLists;
        @ApiModelProperty(name = "coinMin", value = "最低支付")
        private Integer coinMin;
        @ApiModelProperty(name = "coinMax", value = "最高支付")
        private Integer coinMax;
        @ApiModelProperty(name = "levelBitAllList", value = "所有会员等级List")
        private List<LevelBitBo> levelBitAllList;
        @ApiModelProperty(name = "levelBitCurrentList", value = "当前记录支持的会员等级List")
        private List<LevelBitBo> levelBitCurrentList;

    }

    @Data
    class PayoutPayNameListResBody {

        @ApiModelProperty(name = "payName", value = "支付名称")
        private String payName;

    }

    @Data
    class BankList {
        @ApiModelProperty(name = "code", value = "代付代码")
        private String code;
        @ApiModelProperty(name = "name", value = "代付银行")
        private String name;
    }
}
