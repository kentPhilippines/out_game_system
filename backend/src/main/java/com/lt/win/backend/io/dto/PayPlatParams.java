package com.lt.win.backend.io.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @Auther: wells
 * @Date: 2022/8/29 02:30
 * @Description:
 */
public interface PayPlatParams {

    @Data
    @ApiModel(value = "PayPlatListReq", description = "支付平台列表请求实体")
    class PayPlatListReq {
        /**
         * 编码
         */
        @ApiModelProperty(name = "platName", value = "编码", example = "pay_cloud")
        private String platName;
        /**
         * 商户号
         */
        @ApiModelProperty(name = "merchantId", value = "商户号", example = "58")
        private String merchantId;
        /**
         * 状态:0-停用 1-启用 2-删除
         */
        @ApiModelProperty(name = "status", value = "状态:0-停用 1-启用 字典：dic_on_off_status", example = "1")
        private Integer status;
    }

    @Data
    @ApiModel(value = "PayPlatListRes", description = "支付平台列表响应实体")
    class PayPlatListRes extends PayPlat {
        @ApiModelProperty(name = "id", value = "id", example = "1")
        private Integer id;
        /**
         * 创建时间
         */
        @ApiModelProperty(name = "createdAt", value = "创建时间", example = "1594829331")
        private Integer createdAt;

    }

    @Data
    @ApiModel(value = "AddPayPlatReq", description = "支付平台新增响应实体")
    class AddPayPlatReq extends PayPlat {
        /**
         * 编码
         */
        @ApiModelProperty(name = "code", value = "编码", example = "ERC-20")
        private String code;
    }

    @Data
    @ApiModel(value = "UpdatePayPlatReq", description = "支付平台修改响应实体")
    class UpdatePayPlatReq extends PayPlat {
    }


    @Data
    class PayPlat {
        @ApiModelProperty(name = "id", value = "id", example = "1")
        private Integer id;
        /**
         * 平台名称
         */
        @ApiModelProperty(name = "platName", value = "平台名称", example = "TRC-20")
        private String platName;

        /**
         * 商户号
         */
        @ApiModelProperty(name = "merchantId", value = "商户号", example = "58")
        private String merchantId;
        /**
         * 商户秘钥
         */
        @ApiModelProperty(name = "apiKey", value = "商户秘钥", example = "e1337b9941ffdb6f6724a391d23464dc")
        private String apiKey;

        /**
         * 状态:0-停用 1-启用 2-删除
         */
        @ApiModelProperty(name = "status", value = "状态:0-停用 1-启用 字典：dic_on_off_status", example = "1")
        private Integer status;

        /**
         * 平台特殊配置
         */
        @ApiModelProperty(name = "platConfig", value = "平台特殊配置", example = "")
        private String platConfig;

        /**
         * 操作时间
         */
        @ApiModelProperty(name = "updatedAt", value = "操作时间", example = "1662047665")
        private Integer updatedAt;
    }

    @Data
    @ApiModel(value = "CoinRateListReq", description = "汇率列表请求实体")
    class CoinRateListReq {
        @ApiModelProperty(name = "originalCurrency", value = "原始币种", example = "BRL")
        private String originalCurrency;
        @ApiModelProperty(name = "transferCurrency", value = "转换币种", example = "USD")
        private String transferCurrency;
    }

    @Data
    @ApiModel(value = "CoinRateListRes", description = "汇率列表响应实体")
    class CoinRateListRes {
        @ApiModelProperty(name = "id", value = "id", example = "1")
        private Integer id;
        @ApiModelProperty(name = "originalCurrency", value = "原始币种；字典：dic_original_currency", example = "BRL")
        private String originalCurrency;
        @ApiModelProperty(name = "transferCurrency", value = "转换币种；字典：dic_transfer_currency", example = "USD")
        private String transferCurrency;
        @ApiModelProperty(name = "rate", value = "汇率", example = "0.9")
        private BigDecimal rate;
        @ApiModelProperty(name = "status", value = "状态：0-关闭；1-开启 字典：dic_on_off_status", example = "0")
        private Integer status;
    }

    @Data
    @ApiModel(value = "AddCoinRateReq", description = "新增汇率请求实体")
    class AddCoinRateReq {
        @ApiModelProperty(name = "originalCurrency", value = "原始币种", example = "BRL")
        private String originalCurrency;
        @ApiModelProperty(name = "transferCurrency", value = "转换币种", example = "USD")
        private String transferCurrency;
        @ApiModelProperty(name = "rate", value = "汇率", example = "0.9")
        private BigDecimal rate;
        @ApiModelProperty(name = "status", value = "状态：0-关闭；1-开启 字典：dic_on_off_status", example = "0")
        private Integer status;
    }

    @Data
    @ApiModel(value = "UpdateCoinRateReq", description = "修改汇率请求实体")
    class UpdateCoinRateReq {
        @ApiModelProperty(name = "id", value = "id", example = "1")
        private Integer id;
        @ApiModelProperty(name = "originalCurrency", value = "原始币种", example = "BRL")
        private String originalCurrency;
        @ApiModelProperty(name = "transferCurrency", value = "转换币种", example = "USD")
        private String transferCurrency;
        @ApiModelProperty(name = "rate", value = "汇率", example = "0.9")
        private BigDecimal rate;
        @ApiModelProperty(name = "status", value = "状态：0-关闭；1-开启 字典：dic_on_off_status", example = "0")
        private Integer status;
    }

    @Data
    @ApiModel(value = "DeleteCoinRateReq", description = "删除汇率请求实体")
    class DeleteCoinRateReq {
        @ApiModelProperty(name = "id", value = "id", example = "1")
        private Integer id;
    }

    @Data
    @ApiModel(value = "PayChannelListRes", description = "支付通道列表响应实体")
    class PayChannelListRes {
        @ApiModelProperty(name = "id", value = "id", example = "1")
        private Integer id;
        @ApiModelProperty(name = "code", value = "通道编码", example = "TRC-20")
        private String code;
        @ApiModelProperty(name = "name", value = "通道名称", example = "TRC-20")
        private String name;
        @ApiModelProperty(name = "platName", value = "平台名称", example = "pay_cloud")
        private String platName;
        @ApiModelProperty(name = "category", value = "通道类型；1:代收 2:代付；字典：dic_pay_channel_category", example = "1")
        private Integer category;
        @ApiModelProperty(name = "subCategory", value = "子类型：1:虚拟币；2：PIX", example = "1")
        private Integer subCategory;
        @ApiModelProperty(name = "requestUrl", value = "请求地址", example = "https://")
        private String requestUrl;
        @ApiModelProperty(name = "notifyUrl", value = "回调地址", example = "https://")
        private String notifyUrl;
        @ApiModelProperty(name = "currency", value = " 币种", example = "USDT")
        private String currency;
        @ApiModelProperty(name = "status", value = "状态：0-关闭；1-开启 字典：dic_on_off_status", example = "0")
        private Integer status;


    }

    @Data
    @ApiModel(value = "PayChannelListReq", description = "支付通道列表请求实体")
    class PayChannelListReq {
        /**
         * 平台名称
         */
        @ApiModelProperty(name = "platName", value = "平台名称", example = "pay_cloud")
        private String platName;
        /**
         * 平台名称
         */
        @ApiModelProperty(name = "name", value = "通道名称", example = "TRC-20")
        private String name;
        @ApiModelProperty(name = "status", value = "状态：0-关闭；1-开启 字典：dic_on_off_status", example = "0")
        private Integer status;
        @ApiModelProperty(name = "category", value = "通道类型；1:代收 2:代付 字典：dic_pay_channel_category", example = "0")
        private Integer category;
    }

    @Data
    @ApiModel(value = "UpdateChannelReq", description = "支付通道修改响应实体")
    class UpdateChannelReq{
        @ApiModelProperty(name = "id", value = "id", example = "1")
        private Integer id;
        @ApiModelProperty(name = "status", value = "状态：0-关闭；1-开启 字典：dic_on_off_status", example = "0")
        private Integer status;
    }
}
