package com.lt.win.service.io.qo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 查询游戏类型对象
 *
 * @author fangzs
 * @date 2022/8/6 11:05
 */
@Data
public class GameTypeQo implements Serializable {
    private static final long serialVersionUID = 5962863334763867855L;

    @ApiModelProperty(name = "groups",value = "1-体育 2-电子 3-真人 4-捕鱼 5-棋牌 6-电竞 7-彩票 8-动物 9-快速游戏 10-技能",example = "[2]")
    private List<Integer> groups;
    @ApiModelProperty(name = "size",value = "页容量",example = "1")
    private Integer size;
    @ApiModelProperty(name = "name", value = "游戏名称(搜索用)", example = "经典扑克100手")
    private String name;
}
