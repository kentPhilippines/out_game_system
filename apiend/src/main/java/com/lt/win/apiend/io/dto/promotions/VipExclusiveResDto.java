package com.lt.win.apiend.io.dto.promotions;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author: wells
 * @date: 2020/5/5
 * @description:
 */
@Data
@ApiModel(value = "VipExclusiveResDto", description = "优惠活动vip专属请求实体")
public class VipExclusiveResDto {
    @ApiModelProperty(name = "sports", value = "体育返水", example = "0.1")
    private String sports;
    @ApiModelProperty(name = "eGames", value = "电子返水", example = "0.1")
    private String eGames;
    @ApiModelProperty(name = "livesGame", value = "真人返水", example = "0.1")
    private String livesGame;
    @ApiModelProperty(name = "finishGame", value = "捕鱼返水", example = "0.1")
    private String finishGame;
    @ApiModelProperty(name = "chess", value = "棋牌返水", example = "0.1")
    private String chess;
    @ApiModelProperty(name = "eSports", value = "电竞返水", example = "0.1")
    private String eSports;
    @ApiModelProperty(name = "code", value = "等级名称", example = "vip0")
    private String code;
    @ApiModelProperty(name = "name", value = "等级名称", example = "暂无奖牌")
    private String name;
    @ApiModelProperty(name = "scoreUpgrade", value = "升级积分", example = "100")
    private BigDecimal scoreUpgrade;
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
    @ApiModelProperty(name = "completeWithdrawal", value = "已完成存款", example = "100")
    private BigDecimal completeWithdrawal = BigDecimal.ZERO;
    @ApiModelProperty(name = "completeFlow", value = "已完成流水", example = "200")
    private BigDecimal completeFlow = BigDecimal.ZERO;
    @ApiModelProperty(name = "isReceiveRewards", value = "领取生日奖励；1:是，0：否", example = "0")
    private Integer isReceiveRewards = 0;
}
