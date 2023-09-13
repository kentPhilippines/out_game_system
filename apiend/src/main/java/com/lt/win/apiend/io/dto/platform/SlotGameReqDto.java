package com.lt.win.apiend.io.dto.platform;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author David
 * @date 14/05/2020
 * @description
 */
@Data
public class SlotGameReqDto {
    @ApiModelProperty(name = "id", value = "游戏类型ID", example = "[106]")
    private List<Integer> ids;

    @NotNull
    @ApiModelProperty(name = "category", value = "种类:0-全部游戏 1-热门游戏 2-最新游戏 3-我的收藏 4-isCasino")
    private Integer category;

    @ApiModelProperty(name = "name", value = "游戏名称(搜索用)", example = "经典扑克100手")
    private String name;
    /**
     * 是否推荐主页 0否 1是
     */
    @ApiModelProperty(name = "isCasino", value = "是否推荐主页 0否 1是", example = "1")
    private Integer isCasino;
}
