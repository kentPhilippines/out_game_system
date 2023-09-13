package com.lt.win.backend.io.bo.user;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * <p>
 * 会员管理-添加会员-响应body
 * </p>
 *
 * @author andy
 * @since 2020/3/12
 */
@Data
public class AddUserResBody {
    @ApiModelProperty(name = "id", value = "id")
    private Integer id;
    @ApiModelProperty(name = "username", value = "用户名")
    private String username;
    @ApiModelProperty(name = "email", value = "邮箱")
    private String email;
    @ApiModelProperty(name = "mainCurrency", value = "主货币")
    private String mainCurrency;
    @ApiModelProperty(name = "avatar", value = "头像")
    private String avatar;
    @ApiModelProperty(name = "areaCode", value = "区号")
    private String areaCode;
    @ApiModelProperty(name = "mobile", value = "手机号")
    private String mobile;
    @ApiModelProperty(name = "coin", value = "金额")
    private BigDecimal coin;
    @ApiModelProperty(name = "fcoin", value = "冻结资金")
    private BigDecimal fcoin;
    @ApiModelProperty(name = "levelId", value = "会员等级:1-暂无奖牌 2-乒乓球达人 3-羽毛球达人 4-网球达人 5-斯洛克达人 6-高尔夫达人 7-棒球达人 8排球达人 9-橄榄球达人 10-篮球达人 11-足球达人")
    private Integer levelId;
    @ApiModelProperty(name = "apiToken", value = "ApiToken")
    private String apiToken;
    @ApiModelProperty(name = "score", value = "积分")
    private Integer score;
    @ApiModelProperty(name = "promoCode", value = "推广码")
    private String promoCode;
}
