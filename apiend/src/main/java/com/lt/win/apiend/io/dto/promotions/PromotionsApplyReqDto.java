package com.lt.win.apiend.io.dto.promotions;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author: David
 * @date: 2020/4/16
 * @description:
 */
@Data
@ApiModel(value = "PromotionsApplyReqDto", description = "优惠活动详情申请请求实体")
public class PromotionsApplyReqDto {
    @NotNull(message = "不能为空")
    @ApiModelProperty(name = "id", value = "活动ID", example = "1")
    private Integer id;
}
