package com.lt.win.service.io.dto;

import com.lt.win.dao.generator.po.User;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * @author: David
 * @date: 13/06/2020
 */
public interface BaseParams {
    /**
     * LOG 日志统一格式相关
     */
    String LOG_FAIL_START = "\n===================\t[ {}  失败  ]\t=========================================================";
    String LOG_FAIL_END = "\n===================\t[ {}  失败  ]\t=========================================================\n";
    String LOG_ORDER_ID = "\nOrder ID-----------\t{}";

    /**
     * 统一上游回调日志
     *
     * @return 正常格式
     */
    @NotNull
    @Contract(pure = true)
    static String formatUpAndDownStreamRequest() {
        return "\n===================\t[ {} START ]\t============================================================="
                + "\nRequest------------\t{}"
                + "\nIpAddress----------\t{}"
                + "\nResponse-----------\t{}"
                + "\n===================\t[ {}  END  ]\t=============================================================\n";
    }

    /**
     * 统一单行日志打印格式
     *
     * @return 正常格式
     */
    @NotNull
    @Contract(pure = true)
    static String formatLineLog() {
        return "\n===================\t{}";
    }

    /**
     * Header 信息
     */
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    class HeaderInfo {
        @ApiModelProperty(name = "id", value = "会员ID", example = "1")
        public Integer id;
        @ApiModelProperty(name = "username", value = "会员姓名")
        public String username;
        @ApiModelProperty(name = "lang", value = "语言:en-英文, zh-cn-简体, zh-tw-繁体")
        public String lang;
        @ApiModelProperty(name = "device", value = "设备:m-H5, d-PC, android-安卓, ios-苹果")
        public String device;
        @ApiModelProperty(name = "token", value = "token信息")
        public String token;
        @ApiModelProperty(name = "currency", value = "currency信息")
        public String currency;

    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @SuperBuilder
    class TimeParams {
        @ApiModelProperty(name = "startTime", value = "开始时间", example = "1595692801")
        private Integer startTime;
        @ApiModelProperty(name = "endTime", value = "结束时间", example = "1595692801")
        private Integer endTime;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    class IdParams {
        @ApiModelProperty(name = "id", value = "id", example = "1595692801")
        private Integer id;
    }
}
