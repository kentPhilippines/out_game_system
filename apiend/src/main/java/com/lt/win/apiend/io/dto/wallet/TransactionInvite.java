package com.lt.win.apiend.io.dto.wallet;

import com.lt.win.utils.components.pagination.ResPage;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author: wells
 * @date: 2020/6/15
 * @description:
 */

public interface TransactionInvite {
    @Data
    @Builder
    @ApiModel(value = "ReqBody", description = "邀请记录请求实体")
    class ReqBody {
        @ApiModelProperty(name = "startTime", value = "开始时间", example = "1587224407")
        private Integer startTime;
        @ApiModelProperty(name = "endTime", value = "结束时间", example = "1587224507")
        private Integer endTime;
    }

    @Data
    @Builder
    @ApiModel(value = "ResBody", description = "邀请记录响应实体")
    class ResBody {
        @ApiModelProperty(name = "totalInviteCoin", value = "总邀请金额", example = "10.1")
        private BigDecimal totalInviteCoin;
        @ApiModelProperty(name = "totalRebateCoin", value = "总返利金额", example = "10.2")
        private BigDecimal totalRewardCoin;
        @ApiModelProperty(name = "list", value = "邀请记录")
        private ResPage<ResListBody> resPage;
    }

    @Data
    @ApiModel(value = "ResListBody", description = "邀请列表响应实体")
    class ResListBody {
        @ApiModelProperty(name = "userName", value = "邀请好友", example = "test01")
        private String userName;
        @ApiModelProperty(name = "registerAt", value = "注册时间", example = "1587224507")
        private Integer registerAt;
        @ApiModelProperty(name = "category", value = "奖金类型", example = "1")
        private Integer category;
        @ApiModelProperty(name = "rewardCoin", value = "奖金金额", example = "66")
        private BigDecimal rewardCoin;
        @ApiModelProperty(name = "status", value = "状态:1-正常 0-撤销", example = "1")
        private Integer status;
    }
}
