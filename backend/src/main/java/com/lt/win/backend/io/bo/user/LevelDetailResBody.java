package com.lt.win.backend.io.bo.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * <p>
 * 会员等级管理-详情
 * </p>
 *
 * @author andy
 * @since 2020/6/5
 */
@Data
public class LevelDetailResBody {

    @ApiModelProperty(name = "id", value = "等级Id", example = "1")
    private Integer id;

    @ApiModelProperty(name = "scoreUpgrade", value = "升级积分", example = "4000.00")
    private BigDecimal scoreUpgrade;

    @ApiModelProperty(name = "scoreRelegation", value = "保级积分", example = "0.00")
    private BigDecimal scoreRelegation;

    @ApiModelProperty(name = "rewardsUpgrade", value = "升级奖励", example = "4000.00")
    private BigDecimal rewardsUpgrade;

    @ApiModelProperty(name = "rewardsBirthday", value = "生日奖励", example = "4000.00")
    private BigDecimal rewardsBirthday;

    @ApiModelProperty(name = "withdrawalNums", value = "提款次数(每日)", example = "5")
    private Integer withdrawalNums;

    @ApiModelProperty(name = "withdrawalTotalCoin", value = "提款限额(万/日)", example = "20")
    private Integer withdrawalTotalCoin;

    @ApiModelProperty(name = "sports", value = "体育返水", example = "0.0038")
    private BigDecimal sports;

    @ApiModelProperty(name = "eGames", value = "电子返水", example = "0.0038")
    private BigDecimal eGames;

    @ApiModelProperty(name = "livesGame", value = "真人返水", example = "0.0038")
    private BigDecimal livesGame;

    @ApiModelProperty(name = "finishGame", value = "捕鱼返水", example = "0.0038")
    private BigDecimal finishGame;

    @ApiModelProperty(name = "chess", value = "棋牌返水", example = "0.0038")
    private BigDecimal chess;
}
