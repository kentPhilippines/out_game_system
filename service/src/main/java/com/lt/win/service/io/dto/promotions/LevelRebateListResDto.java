package com.lt.win.service.io.dto.promotions;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author: wells
 * @date: 2020/4/7
 * @description:
 */
@Data
@ApiModel(value = "LevelRebateListResDto", description = "优惠活动全场返水响应实体")
public class LevelRebateListResDto {
    @ApiModelProperty(name = "levelId", value = "会员等级ID", example = "1")
    private Integer levelId;
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

}
