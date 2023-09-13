package com.lt.win.backend.io.bo.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * <p>
 * 会员管理-列表查询:用户信息实体
 * </p>
 *
 * @author andy
 * @since 2020/3/12
 */
@Data
public class UserInfo {
    @ApiModelProperty(name = "id", value = "UID", example = "1")
    private Integer id;
    @ApiModelProperty(name = "username", value = "用户名", example = "test999")
    private String username;
    @ApiModelProperty(name = "levelId", value = "用户等级:1-暂无奖牌 2-乒乓球达人 3-羽毛球达人 4-网球达人 5-斯洛克达人 6-高尔夫达人 7-棒球达人 8-排球达人 9-橄榄球达人 10-篮球达人 11-足球达人", example = "1")
    private Integer levelId;
    @ApiModelProperty(name = "role", value = "用户类型:0-会员 1-代理 2-总代理 3-股东 4-水军 5-测试 10-系统账号", example = "0")
    private List<Integer> role;
    @ApiModelProperty(name = "status", value = "账号状态:10-正常 9-冻结 8-永久冻结", example = "10")
    private Integer status;
}
