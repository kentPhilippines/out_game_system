package com.lt.win.backend.io.bo.user;

import com.lt.win.backend.io.bo.StartEndTime;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * <p>
 * 会员管理-在线人数->列表查询
 * </p>
 *
 * @author andy
 * @since 2020/3/12
 */
@Data
public class OnlineUserCountListReqBody extends StartEndTime {
    @ApiModelProperty(name = "username", value = "用户名", example = "test999")
    private String username;
    @ApiModelProperty(name = "supUsername", value = "代理账号", example = "andy66")
    private String supUsername;
    @ApiModelProperty(name = "ip", value = "IP地址")
    private String ip;
    @ApiModelProperty(name = "device", value = "设备字典:dic_user_login_log_device")
    private String device;
    @ApiModelProperty(name = "supUid1", value = "上级代理UID", hidden = true)
    private Integer supUid1;
    @ApiModelProperty(name = "uidList", value = "在线UID列表", hidden = true)
    private List<Integer> uidList;
}
