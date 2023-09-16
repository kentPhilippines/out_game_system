package com.lt.win.service.io.qo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 *
 * @author fangzs
 * @date 2022/8/12 21:21
 */

public interface Betslip {




    @ApiModel("注单查询")
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    class  BetslipsDto implements Serializable {
        @ApiModelProperty(name = "periodsNo",value = "注单号")
        private String periodsNo;
        @ApiModelProperty(name = "uid",value = "用户id" )
        private String uid;
        @ApiModelProperty(name = "username",value = "用户名" )
        private String username;
        @ApiModelProperty(name = "status",value = "注单状态 0:待开彩  1派彩成功  2: 退款" )
        private Integer status;
        @ApiModelProperty(name = "payoutCode",value = "开奖板块编号" )
        private Integer payoutCode;
        @ApiModelProperty(name = "payoutName",value = "开奖板块名称" )
        private String payoutName;
        @ApiModelProperty(name = "betCode",value = "投注板块编号" )
        private Integer betCode;
        @ApiModelProperty(name = "betName",value = "投注板块名称" )
        private String betName;
        @ApiModelProperty(name = "startTime", value = "开始时间", example = "1583932627")
        private Integer startTime;
        @ApiModelProperty(name = "endTime", value = "结束时间", example = "1584725218")
        private Integer endTime;
    }
    @ApiModel("注单汇总查询")
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    class  BetslipsDtoSum implements Serializable {
        @ApiModelProperty(name = "uid",value = "用户id" )
        private String uid;
        @ApiModelProperty(name = "username",value = "用户名" )
        private String username;
        @ApiModelProperty(name = "startTime", value = "开始时间", example = "1583932627")
        private Integer startTime;
        @ApiModelProperty(name = "endTime", value = "结束时间", example = "1584725218")
        private Integer endTime;
    }





}
