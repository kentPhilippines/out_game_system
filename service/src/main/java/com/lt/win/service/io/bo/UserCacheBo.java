package com.lt.win.service.io.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Map;

/**
 * <p>
 * 用户缓存实体
 * </p>
 *
 * @author andy
 * @since 2020/7/24
 */
public interface UserCacheBo {
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    class UserCacheInfo {
        /**
         * id
         */
        private Integer id;

        /**
         * 用户名
         */
        private String username;

        /**
         * 头像
         */
        private String avatar;

        /**
         * 会员等级
         */
        private Integer levelId;

        /**
         * 角色:0-会员 1-代理 2-总代理 3-股东 4-测试 10-系统账号
         */
        private Integer role;

        /**
         * 推广码
         */
        private String promoCode;

        /**
         * 会员旗
         */
        private Integer flag;

        /**
         * 注册时间
         */
        private Integer createdAt;

        /**
         * 额外打码量
         */
        private BigDecimal extraCode;

    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    class MemberBasicInfo {
        private Integer id;
        private String username;
        private Integer levelId;
        private Integer status;
        private Integer supUid1;
        private String supUsername1;
        private Integer supUid2;
        private Integer supUid3;
        private Integer supUid4;
        private Integer supUid5;
        private Integer supUid6;
        private Integer role;
        private Integer createdAt;
    }

    /**
     * 用户会员旗
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    class UserFlagInfo {
        @ApiModelProperty(name = "icon", value = "图标", example = "icon-ordinary_vip-5")
        private String icon;
        @ApiModelProperty(name = "iconColor", value = "图标颜色", example = "#A2BF8B")
        private String iconColor;
        @ApiModelProperty(name = "name", value = "会员旗名称", example = "VIP会员")
        private String name;
        @ApiModelProperty(name = "bitCode", value = "会员旗", example = "512")
        private Integer bitCode;
    }


    /**
     * 用户打码量
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    class UserBetSum {
        private Map<Long, Integer> betSum;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @ApiModel(value = "WithdrawalAddressReqDto", description = "提款地址列表请求实体")
    class WithdrawalAddressReqDto {
        @ApiModelProperty(name = "uid", value = "用户ID", example = "1000", required = true)
        private Integer uid;
        @ApiModelProperty(name = "category", value = "1-USDT 2-PIX", example = "1000", required = true)
        private Integer category;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @ApiModel(value = "WithdrawalAddressResDto", description = "提款地址列表响应实体")
    class WithdrawalAddressResDto {
        @ApiModelProperty(name = "id", value = "提款地址ID", example = "1")
        private Integer id;
        @ApiModelProperty(name = "uid", value = "会员ID", example = "1")
        private Integer uid;
        @ApiModelProperty(name = "username", value = "会员名称", example = "test9999")
        private String username;
        @ApiModelProperty(name = "code", value = "主网名称", example = "TRC-20")
        private String code;
        @ApiModelProperty(name = "address", value = "提款地址", example = "TEV6MRrbcQAGwzkb6pqMoHHb9b22xXb3zf")
        private String address;
        @ApiModelProperty(name = "accountType", value = "账户类型-字典dic_withdrawal_address_account_type", example = "TEV6MRrbcQAGwzkb6pqMoHHb9b22xXb3zf")
        private String accountType;
        @ApiModelProperty(name = "address", value = "账户地址", example = "TEV6MRrbcQAGwzkb6pqMoHHb9b22xXb3zf")
        private String accountNo;
        @ApiModelProperty(name = "status", value = "状态:1-默认地址(启用) 2-正常启用 3-删除", example = "2")
        private Integer status;
        @ApiModelProperty(name = "updatedAt", value = "操作时间", example = "2")
        private Integer updatedAt;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @ApiModel(value = "AddWithdrawalAddressReqDto", description = "新增提款地址请求实体")
    class AddWithdrawalAddressReqDto {
        @ApiModelProperty(name = "uid", value = "会员ID", example = "1", required = true)
        private Integer uid;
        @ApiModelProperty(name = "code", value = "主网名称:USDT-TRC20/ERC20 银行卡-PIX", example = "TRC-20", required = true)
        private String code;
        @ApiModelProperty(name = "address", value = "提款地址", example = "TEV6MRrbcQAGwzkb6pqMoHHb9b22xXb3zf", required = true)
        private String address;
        @ApiModelProperty(name = "accountType", value = "账户类型-字典dic_withdrawal_address_account_type", example = "TEV6MRrbcQAGwzkb6pqMoHHb9b22xXb3zf")
        private String accountType;
        @ApiModelProperty(name = "accountNo", value = "账户卡号", example = "TEV6MRrbcQAGwzkb6pqMoHHb9b22xXb3zf")
        private String accountNo;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @ApiModel(value = "UpdateWithdrawalAddressReq", description = "修改提款地址请求实体")
    class UpdateWithdrawalAddressReq {
        @ApiModelProperty(name = "id", value = "提款地址ID", example = "1", required = true)
        private Integer id;
        @ApiModelProperty(name = "code", value = "主网名称:USDT-TRC20/ERC20 银行卡-PIX", example = "TRC-20")
        private String code;
        @ApiModelProperty(name = "address", value = "提款地址", example = "TEV6MRrbcQAGwzkb6pqMoHHb9b22xXb3zf")
        private String address;
        @ApiModelProperty(name = "accountType", value = "账户类型-字典dic_withdrawal_address_account_type", example = "TEV6MRrbcQAGwzkb6pqMoHHb9b22xXb3zf")
        private String accountType;
        @ApiModelProperty(name = "accountNo", value = "账户卡号", example = "TEV6MRrbcQAGwzkb6pqMoHHb9b22xXb3zf")
        private String accountNo;
        @ApiModelProperty(name = "status", value = "状态:1-默认地址(启用) 2-正常启用 3-删除", example = "2")
        private Integer status;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @ApiModel(value = "DeleteWithdrawalAddressReqDto", description = "删除提款地址请求实体")
    class DeleteWithdrawalAddressReqDto {
        @ApiModelProperty(name = "id", value = "提款地址ID", example = "1", required = true)
        private Integer id;
    }
}
