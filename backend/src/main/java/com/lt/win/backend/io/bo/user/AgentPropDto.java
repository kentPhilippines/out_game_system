package com.lt.win.backend.io.bo.user;

import com.lt.win.service.io.bo.UserCacheBo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * 代理返回实体
 * </p>
 *
 * @author David
 * @since 2022/8/26
 */
@Data
public class AgentPropDto {
    @ApiModelProperty(name = "id", value = "ID")
    private Integer id;
    @ApiModelProperty(name = "username", value = "用户名")
    private String username;
    @ApiModelProperty(name = "supUsername1", value = "上级代理")
    private String supUsername1;
    @ApiModelProperty(name = "status", value = "状态:10-正常 9-冻结")
    private Integer status;
    @ApiModelProperty(name = "createdAt", value = "注册时间")
    private Integer createdAt;
}

