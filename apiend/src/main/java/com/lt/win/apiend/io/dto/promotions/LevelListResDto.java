package com.lt.win.apiend.io.dto.promotions;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author: wells
 * @date: 2020/4/7
 * @description:
 */
@Data
@ApiModel(value = "LevelListResDto", description = "优惠活动vip会员成长响应实体")
public class LevelListResDto {
    @ApiModelProperty(name = "id", value = "ID", example = "1")
    private Integer id;
    @ApiModelProperty(name = "code", value = "等级名称", example = "vip0")
    private String code;
    @ApiModelProperty(name = "name", value = "等级名称", example = "暂无奖牌")
    private String name;
    @ApiModelProperty(name = "scoreUpgrade", value = "升级积分", example = "100")
    private BigDecimal scoreUpgrade;
    @ApiModelProperty(name = "currentFlow", value = "当前流水", example = "100")
    private BigDecimal currentFlow;
    @ApiModelProperty(name = "scoreRelegation", value = "保级积分", example = "99")
    private BigDecimal scoreRelegation;
    @ApiModelProperty(name = "rewardsUpgrade", value = "升级奖励", example = "99")
    private BigDecimal rewardsUpgrade;
    @ApiModelProperty(name = "rewardsMonthly", value = "每月红包", example = "88")
    private BigDecimal rewardsMonthly;
    @ApiModelProperty(name = "rewardsBirthday", value = "生日奖励", example = "77")
    private BigDecimal rewardsBirthday;
    @ApiModelProperty(name = "withdrawalNums", value = "提款次数(每日)", example = "5")
    private Integer withdrawalNums;
    @ApiModelProperty(name = "withdrawalTotalCoin", value = "提款限额(万/日)", example = "10000")
    private Integer withdrawalTotalCoin;
}
