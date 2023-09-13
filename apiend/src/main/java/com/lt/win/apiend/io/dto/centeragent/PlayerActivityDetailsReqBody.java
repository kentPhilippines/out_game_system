package com.lt.win.apiend.io.dto.centeragent;

import com.lt.win.apiend.io.dto.StartEndTime;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * <p>
 * 代理中心-我的报表-玩家活跃度详情ReqBody
 * </p>
 *
 * @author andy
 * @since 2020/7/24
 */
@Data
public class PlayerActivityDetailsReqBody extends StartEndTime {

    @ApiModelProperty(name = "username", value = "用户名称", example = "andy")
    private String username;

    @NotNull(message = "类型不能为空")
    @ApiModelProperty(name = "category", value = "类型:1-总注册人数 2-新增人数 3-首充金额 4-二充金额", example = "1")
    private Integer category;
}
