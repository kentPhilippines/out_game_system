package com.lt.win.service.io.qo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 后台注单查询
 *
 * @author fangzs
 * @date 2022/8/25 02:32
 */
@ApiModel("注单查询")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminBetQo implements Serializable {

    private static final long serialVersionUID = 4090604507255941741L;
    @ApiModelProperty(value = "注单状态 1:待开彩  2:完成  3: 退款  0查询所有")
    private Integer status;

    @ApiModelProperty(value = "游戏类型id")
    private List<Integer> gameTypeIds;

    @ApiModelProperty(value = "子游戏id")
    private String gameId;

    @ApiModelProperty("用户id")
    private Integer uid;

    @ApiModelProperty("用户昵称")
    private String username;

    @ApiModelProperty(name = "startTime", value = "开始时间")
    private Integer startTime;

    @ApiModelProperty(name = "endTime", value = "结束时间")
    private Integer endTime;

    /**
     * 游戏大类类型:1-体育 2-电子 3-真人 4-捕鱼 5-棋牌 6-电竞 7-彩票 8-动物 9-快速 ',
     */
    @ApiModelProperty("游戏大类类型id")
    private Integer gameGroupId;

    @ApiModelProperty("游戏平台id")
    private Integer gamePlatId;


    @ApiModelProperty("主键")
    private Long id;

}
