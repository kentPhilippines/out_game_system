package com.lt.win.service.io.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @Author : Wells
 * @Date : 2020-12-08 8:24 下午
 * @Description : 修改用户金额
 */
public interface UpdateUserCoinParams {


    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    class UpdateUserCoinSaveLogDto {
        @ApiModelProperty(name = "uid", value = "会员ID", example = "1")
        public Integer uid;
        @ApiModelProperty(name = "outIn", value = "收支类型:0-支出 1-收入")
        public Integer outIn = 0;
        @ApiModelProperty(name = "coin", value = "金额")
        public BigDecimal coin;
        @ApiModelProperty(name = "fcoin", value = "冻结金额")
        public BigDecimal fcoin;
        @ApiModelProperty(name = "referId", value = " 对应的业务记录ID")
        public Long referId;
        @ApiModelProperty(name = "category", value = "日志类型")
        public Integer category;
        @ApiModelProperty(name = "subCategory", value = "category为活动时，子类型有值，否则取默认值0")
        public Integer subCategory = 0;
        @ApiModelProperty(name = "status", value = "状态")
        public Integer status;
        @ApiModelProperty(name = "now", value = "当前时间")
        public Integer now;
    }

    @Data
    @Builder
    class UpdateUserCoinUpdateLogByReferId {
        @ApiModelProperty(name = "uid", value = "会员ID", example = "1")
        public Integer uid;
        @ApiModelProperty(name = "coin", value = "金额")
        public BigDecimal coin;
        @ApiModelProperty(name = "fcoin", value = "冻结金额")
        public BigDecimal fcoin;
        @ApiModelProperty(name = "referId", value = " 对应的业务记录ID")
        public Long referId;
        @ApiModelProperty(name = "category", value = "日志类型")
        public Integer category;
        @ApiModelProperty(name = "status", value = "状态")
        public Integer status;
        @ApiModelProperty(name = "now", value = "当前时间")
        public Integer now;
    }

    @Data
    @Builder
    class UpdateUserCoinUpdateLogById {
        @ApiModelProperty(name = "uid", value = "会员ID", example = "1")
        public Integer uid;
        @ApiModelProperty(name = "coin", value = "金额")
        public BigDecimal coin;
        @ApiModelProperty(name = "referId", value = " 对应的业务记录ID")
        public Long logId;
        @ApiModelProperty(name = "status", value = "状态")
        public Integer status;
        @ApiModelProperty(name = "now", value = "当前时间")
        public Integer now;
    }


}
