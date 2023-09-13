package com.lt.win.service.io.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.math.BigDecimal;

/**
 * <p>
 * 用户缓存实体
 * </p>
 *
 * @author andy
 * @since 2020/7/24
 */
public interface PromotionsBo {
    @Getter
    @NoArgsConstructor
    enum WeekDay {
        /**
         * 语言名称
         */
        EN_1("Monday"),
        EN_2("Tuesday"),
        EN_3("Wednesday"),
        EN_4("Thursday"),
        EN_5("Friday"),
        EN_6("Saturday"),
        EN_7("Sunday"),
        ZH_1("星期一"),
        ZH_2("星期二"),
        ZH_3("星期三"),
        ZH_4("星期四"),
        ZH_5("星期五"),
        ZH_6("星期六"),
        ZH_7("星期日"),
        RU_1("Monday"),
        RU_2("Tuesday"),
        RU_3("Wednesday"),
        RU_4("Thursday"),
        RU_5("Friday"),
        RU_6("Saturday"),
        RU_7("Sunday"),
        KO_1("Monday"),
        KO_2("Tuesday"),
        KO_3("Wednesday"),
        KO_4("Thursday"),
        KO_5("Friday"),
        KO_6("Saturday"),
        KO_7("Sunday"),
        PT_1("Monday"),
        PT_2("Tuesday"),
        PT_3("Wednesday"),
        PT_4("Thursday"),
        PT_5("Friday"),
        PT_6("Saturday"),
        PT_7("Sunday"),
        ES_1("Monday"),
        ES_2("Tuesday"),
        ES_3("Wednesday"),
        ES_4("Thursday"),
        ES_5("Friday"),
        ES_6("Saturday"),
        ES_7("Sunday");
        String value;

        WeekDay(String value) {
            this.value = value;
        }
    }

    /**
     * 活动配置信息
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    class Config {
        @ApiModelProperty(name = "flowClaim", value = "流水倍数", example = "100")
        private Integer flowClaim;
        @ApiModelProperty(name = "minCoin", value = "最低充值金额", example = "100")
        private BigDecimal minCoin;
        @ApiModelProperty(name = "maxCoin", value = "最高充值金额", example = "100")
        private BigDecimal maxCoin;
        @ApiModelProperty(name = "rate", value = "赠送比例", example = "100")
        private BigDecimal rate;
        @ApiModelProperty(name = "depositNums", value = "存款次数-续存优惠使用", example = "100")
        private Integer depositNums;
        @ApiModelProperty(name = "category", value = "首单包赔-1-限制申请前投注 0-不限制", example = "100")
        private Integer category;
        @ApiModelProperty(name = "weekdays", value = "每周几(快乐周五)使用", example = "100")
        private Integer weekdays;
    }

    /**
     * 活动配置信息
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    class Range {
        @ApiModelProperty(name = "start", value = "开始时间", example = "100")
        private Integer start;
        @ApiModelProperty(name = "end", value = "结束时间", example = "100")
        private Integer end;
    }
}
