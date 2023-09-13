package com.lt.win.apiend.io.dto.promotions;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Author: David
 * Date: 2020/4/15
 * Description:
 */
@Data
@ApiModel(value = "PromotionsResDto", description = "优惠活动详情响应实体")
public class PromotionsResDto {
    @ApiModelProperty(name = "id", value = "ID", example = "1")
    private Integer id;
    @ApiModelProperty(name = "category", value = "类型", example = "1")
    private String category;
    @ApiModelProperty(name = "codeZh", value = "活动标题", example = "first deposit")
    private String codeZh;
    @ApiModelProperty(name = "img", value = "图片", example = "1")
    private String img;
    @ApiModelProperty(name = "descript", value = "详情描述", example = "1")
    private String descript;
    @ApiModelProperty(name = "startedAt", value = "开始时间", example = "1585991266")
    private Integer startedAt;
    @ApiModelProperty(name = "endedAt", value = "结束时间", example = "1585991266")
    private Integer endedAt;
    @ApiModelProperty(name = "isActivated", value = "是否激活状态:1-激活 0-否(按钮置灰)", example = "1")
    private Integer isActivated;
}
