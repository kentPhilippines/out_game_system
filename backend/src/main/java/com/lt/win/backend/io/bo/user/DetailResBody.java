package com.lt.win.backend.io.bo.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 会员详情-响应
 * </p>
 *
 * @author andy
 * @since 2020/3/13
 */
@Data
public class DetailResBody extends ListResBody {
    @ApiModelProperty(name = "mobile", value = "手机号", example = "19912345678")
    private String mobile;
    @ApiModelProperty(name = "email", value = "邮箱", example = "admin@gmail.com")
    private String email;
    @ApiModelProperty(name = "childCount", value = "下级会员", example = "99")
    private Integer childCount;
    @ApiModelProperty(name = "proxyList", value = "上级代理")
    private List<SupProxyListReqBody> proxyList = new ArrayList<>();
    @ApiModelProperty(name = "createdAt", value = "注册时间", example = "1583907337")
    private Integer createdAt;
    @ApiModelProperty(name = "sex", value = "性别:1-男 0-女 2-未知", example = "1")
    private Integer sex;
    @ApiModelProperty(name = "bindBankCount", value = "绑定银行卡", example = "1")
    private Integer bindBankCount;
    @ApiModelProperty(name = "category", value = "类型dic_user_login_log_category", example = "1")
    private Integer category;
    @ApiModelProperty(name = "device", value = "设备dic_user_login_log_device", example = "1")
    private String device;
    @ApiModelProperty(name = "ip", value = "ip", example = "192.168.1.11")
    private String ip;
    @ApiModelProperty(name = "areaCode", value = "区号")
    private String areaCode;
}
