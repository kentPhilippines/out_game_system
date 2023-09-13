package com.lt.win.backend.io.bo.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author : Wells
 * @Date : 2020-12-09 8:01 下午
 * @Description : 清除用户token与手机验证次数请求参数
 */
@Data
public class ClearTokenCodeReqBody {
    @ApiModelProperty(name = "uid", value = "用户名", example = "1")
    private Integer uid;
    @ApiModelProperty(name = "code", value = "号码", example = "9612671617")
    private String code;

}
