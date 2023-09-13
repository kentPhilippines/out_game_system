package com.lt.win.backend.io.bo.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author: wells
 * @date: 2020/8/27
 * @description:
 */
@Data
@ApiModel(value = "CodeUidReqBody", description = "打码量详情与稽核请求实体类")
public class CodeUidReqBody {
    @ApiModelProperty(name = "uid", value = "用户ID", example = "10")
    private Integer uid;
}
