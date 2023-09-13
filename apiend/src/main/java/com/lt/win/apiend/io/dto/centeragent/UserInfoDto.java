package com.lt.win.apiend.io.dto.centeragent;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 代理中心-我的下级-列表-直推人数列表-用户信息
 * </p>
 *
 * @author andy
 * @since 2020/5/4
 */
@Data
public class UserInfoDto {
    @ApiModelProperty(name = "id", value = "UID", example = "1")
    private Integer id;

    @ApiModelProperty(name = "username", value = "用户名")
    private String username;

    @ApiModelProperty(name = "avatar", value = "头像")
    private String avatar;
}
