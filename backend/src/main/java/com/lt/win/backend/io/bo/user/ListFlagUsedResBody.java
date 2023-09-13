package com.lt.win.backend.io.bo.user;

import com.lt.win.service.io.bo.UserCacheBo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * <p>
 * 会员旗管理-已被使用的会员旗列表-响应body
 * </p>
 *
 * @author andy
 * @since 2020/3/25
 */
@Data
public class ListFlagUsedResBody {

    @ApiModelProperty(name = "uid", value = "UID")
    private Integer uid;
    @ApiModelProperty(name = "username", value = "用户名")
    private String username;
    @ApiModelProperty(name = "userFlagList", value = "会员旗列表")
    private List<UserCacheBo.UserFlagInfo> userFlagList;


}
