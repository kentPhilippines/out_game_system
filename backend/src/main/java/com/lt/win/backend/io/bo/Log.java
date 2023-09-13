package com.lt.win.backend.io.bo;

import com.alibaba.fastjson.JSON;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * <p>
 * 日志管理
 * </p>
 *
 * @author andy
 * @since 2020/6/5
 */
public interface Log {
    /**
     * 会员管理-登录日志 -ResponseBody
     */
    @Data
    class LogInfo {
        @ApiModelProperty(name = "uid", value = "UID", example = "1000201")
        private Integer uid;
        @ApiModelProperty(name = "username", value = "用户名", example = "ww")
        private String username;
        @ApiModelProperty(name = "ip", value = "IP", example = "192.168.1.24")
        private String ip;
        @ApiModelProperty(name = "userAgent", value = "登录信息")
        private String userAgent;
        @ApiModelProperty(name = "createdAt", value = "时间", example = "1591253974")
        private Integer createdAt;
        @ApiModelProperty(name = "status", value = "在线状态:0-在线 1-离线", example = "1")
        private Integer status;
        @ApiModelProperty(name = "category", value = "在线状态:0-在线 1-离线", example = "1")
        private Integer category;
    }

    /**
     * 会员管理-会员登录日志 -ResponseBody
     */
    @Data
    class UserLoginLogInfoResBody {
        @ApiModelProperty(name = "id", value = "ID", example = "1")
        private Long id;

        @ApiModelProperty(name = "uid", value = "UID", example = "1000201")
        private Integer uid;

        @ApiModelProperty(name = "username", value = "用户名", example = "andy001")
        private String username;
        @ApiModelProperty(name = "ip", value = "IP地址")
        private String ip;

        @ApiModelProperty(name = "gameName", value = "游戏名称")
        private String gameName;
        @ApiModelProperty(name = "device", value = "设备:H5-手机 PC-电脑 ANDROID-安卓 IOS-苹果")
        private String device;

        @ApiModelProperty(name = "category", value = "类型:0-登出 1-登录 2-进入游戏")
        private Integer category;

        @ApiModelProperty(name = "remark", value = "备注")
        private String remark;

        @ApiModelProperty(name = "createdAt", value = "登录日期")
        private Integer createdAt;
    }

    /**
     * 会员管理-会员登录日志 -RequestBody
     */
    @Data
    @Builder
    class UserLoginLogInfoReqBody extends StartEndTime {
        @ApiModelProperty(name = "uid", value = "UID", example = "1000201")
        private Integer uid;
        @ApiModelProperty(name = "username", value = "用户名", example = "andy001")
        private String username;
        @ApiModelProperty(name = "ip", value = "IP地址")
        private String ip;
        @ApiModelProperty(name = "category", value = "类型:0-登出 1-登录 2-进入游戏")
        private Integer category;
        @ApiModelProperty(name = "onLineNumFlag", value = "是否按在线人数查询:Y-需要 N-不需要", example = "N")
        private String onLineNumFlag;
    }

    /**
     * 会员管理-会员登录日志 -RequestBody
     */
    @Data
    @Builder
    class UpdateUserLoginLogReqBody {

        @NotNull(message = "id不能为空")
        @ApiModelProperty(name = "id", value = "ID", example = "1")
        private Long id;

        @NotEmpty(message = "remark不能为空")
        @ApiModelProperty(name = "remark", value = "备注")
        private String remark;
    }

    /**
     * 会员管理-admin登录日志 -RequestBody
     */
    @Data
    @Builder
    class LogInfoReqBody extends StartEndTime {

        @ApiModelProperty(name = "uid", value = "UID", example = "1000201")
        private Integer uid;

        @ApiModelProperty(name = "username", value = "用户名", example = "ww")
        private String username;

        @ApiModelProperty(name = "ip", value = "IP", example = "192.168.1.24")
        private String ip;

        @ApiModelProperty(name = "status", value = "在线状态:0-在线 1-离线", example = "1")
        private Integer status;
        @ApiModelProperty(name = "category", value = "状态:0-登出 1-登陆", example = "1")
        private Integer category;
    }

    /**
     * 用户中心-admin登录日志 -RequestBody
     */
    @Data
    class AdminOperateLogReqBody extends StartEndTime {
        @ApiModelProperty(name = "uid", value = "操作UID")
        private Integer uid;
        @ApiModelProperty(name = "username", value = "用户名")
        private String username;
    }

    /**
     * 后台管理-操作日志 -ResponseBody
     */
    @Data
    class AdminOperateLog {
        @ApiModelProperty(name = "id", value = "ID")
        private Integer id;

        @ApiModelProperty(name = "uid", value = "操作UID", example = "1000201")
        private Integer uid;

        @ApiModelProperty(name = "username", value = "用户名")
        private String username;

        @ApiModelProperty(name = "url", value = "请求地址")
        private String url;

        @ApiModelProperty(name = "reqParams", value = "请求参数")
        private JSON reqParams;

        @ApiModelProperty(name = "resParams", value = "响应参数")
        private JSON resParams;

        @ApiModelProperty(name = "ip", value = "IP", example = "192.168.1.24")
        private String ip;

        @ApiModelProperty(name = "createdAt", value = "操作时间", example = "1591253974")
        private Integer createdAt;
    }

    @Data
    @Builder
    class OnLineNum {
        @ApiModelProperty(name = "number", value = "在线人数")
        private Integer number;
    }

}
