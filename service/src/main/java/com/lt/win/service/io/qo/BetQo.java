package com.lt.win.service.io.qo;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 *
 * @author fangzs
 * @date 2022/8/12 21:21
 */
@ApiModel("注单查询")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BetQo implements Serializable {


    @ApiModelProperty(value = "注单状态 1:待开彩  2:完成  3: 退款 ", required = true)
    @NotNull(message = "status not null")
    private Integer status;

    @ApiModelProperty(value = "游戏类型id")
    private Integer gameTypeId;

    @ApiModelProperty("游戏平台id")
    private Integer platId;

    @ApiModelProperty(name = "startTime", value = "开始时间")
    private Integer startTime;

    @ApiModelProperty(name = "endTime", value = "结束时间")
    private Integer endTime;

    @ApiModelProperty(name = "TypeId", value = "投注类型的标识符 单 = 1 系统 = 2 快速 = 3")
    private Integer typeId;



}
