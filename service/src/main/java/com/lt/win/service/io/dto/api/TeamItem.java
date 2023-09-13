package com.lt.win.service.io.dto.api;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Auther: nobody
 * @Date: 2022/8/28 15:52
 * @Description: 团队项⽬
 */

@Data
public class TeamItem implements Serializable {
    private static final long serialVersionUID = -18931141837589583L;


    //队伍的安置（例如：皇家⻢德⾥ ‒ 巴塞罗那 ‒ 皇家⻢德⾥ = 1 和巴塞 罗那 = 2）
    @ApiModelProperty(name = "Side", value = "队伍的安置")
    private Integer Side;

    @ApiModelProperty(name = "Side", value = "团队标识符")
    private Long Id;

    @ApiModelProperty(name = "Side", value = "团队名称")
    private String Name;


}
