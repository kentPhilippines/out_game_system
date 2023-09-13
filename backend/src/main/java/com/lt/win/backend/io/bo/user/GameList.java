package com.lt.win.backend.io.bo.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 会员管理-用户游戏余额查询
 * </p>
 *
 * @author andy
 * @since 2020/6/5
 */
@Data
public class GameList {
    @ApiModelProperty(name = "id", value = "ID", example = "101")
    private Integer id;

    @ApiModelProperty(name = "name", value = "名称", example = "利记体育")
    private String name;

    @ApiModelProperty(name = "model", value = "编码", example = "SBOSports")
    private String model;
}
