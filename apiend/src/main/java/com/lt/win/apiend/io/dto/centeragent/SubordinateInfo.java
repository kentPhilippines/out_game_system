package com.lt.win.apiend.io.dto.centeragent;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 代理中心-我的报表-充值与提现详情-用户详情-下级成员信息
 * </p>
 *
 * @author andy
 * @since 2020/7/24
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SubordinateInfo {
    @ApiModelProperty(name = "username", value = "下级成员", example = "andy1")
    private String username;

    @ApiModelProperty(name = "createdAt", value = "注册时间", example = "1587226981")
    private Integer createdAt;

    @ApiModelProperty(name = "levelId", value = "会员等级ID")
    private Integer levelId;

    @ApiModelProperty(name = "avatar", value = "头像")
    private String avatar;

    @ApiModelProperty(name = "count", value = "人数")
    private Integer count;

    @ApiModelProperty(name = "uid", value = "UID")
    private Integer uid;
}
