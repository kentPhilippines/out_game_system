package com.lt.win.backend.io.bo.user;

import com.lt.win.service.io.bo.UserCacheBo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
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
public class OnlineUserCountListResBody {

    @ApiModelProperty(name = "username", value = "用户名", example = "test999")
    private String username;
    @ApiModelProperty(name = "coin", value = "账户余额", example = "99999.00")
    private BigDecimal coin;
    @ApiModelProperty(name = "levelText", value = "会员等级:vip1-乒乓球达人", example = "vip1-乒乓球达人")
    private String levelText;
    @ApiModelProperty(name = "supUsername", value = "代理账号", example = "andy66")
    private String supUsername;
    @ApiModelProperty(name = "ip", value = "IP地址")
    private String ip;
    @ApiModelProperty(name = "device", value = "设备:H5-手机 PC-电脑 ANDROID-安卓 IOS-苹果")
    private String device;
    @ApiModelProperty(name = "loginTime", value = "最近登录时间，不能排序", example = "1583907337")
    private Integer loginTime;
    @ApiModelProperty(name = "uid", value = "UID", hidden = true)
    private Integer uid;
    @ApiModelProperty(name = "supUid1", value = "上级代理UID", hidden = true)
    private Integer supUid1;
    @ApiModelProperty(name = "levelId", hidden = true)
    private Integer levelId;
}

