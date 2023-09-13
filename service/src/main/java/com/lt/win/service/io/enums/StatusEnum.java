package com.lt.win.service.io.enums;

import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author: David
 * @date: 15/06/2020
 */
public interface StatusEnum {
    @Getter
    @NoArgsConstructor
    enum STATUS {
        /**
         * 启用状态(所有表通用): 1-启用 0-不启用
         */
        OFF(0, "未开启"),
        ON(1, "开启"),
        MAINTAIN(2, "维护");
        int code;
        String desc;

        STATUS(Integer code, String desc) {
            this.code = code;
            this.desc = desc;
        }
    }

    @Getter
    @NoArgsConstructor
    enum USER_STATUS {
        /**
         * 启用状态(所有表通用): 1-启用 0-不启用
         */
        NORMAL(10, "正常"),
        FROZEN(9, "冻结"),
        DELETE(8, "删除");
        int code;
        String desc;

        USER_STATUS(int code, String desc) {
            this.code = code;
            this.desc = desc;
        }
    }

    @Getter
    @NoArgsConstructor
    enum MEMBER_AGENT_LEVEL {
        /**
         * Member 代理级别
         */
        LEVEL_1(1, "一级代理"),
        LEVEL_2(2, "二级代理"),
        LEVEL_3(3, "三级代理"),
        LEVEL_4(4, "四级代理"),
        LEVEL_5(5, "五级代理"),
        LEVEL_6(6, "六级代理"),
        OTHER(9, "其他层级(1~3以外)");
        Integer code;
        String desc;

        MEMBER_AGENT_LEVEL(Integer code, String desc) {
            this.code = code;
            this.desc = desc;
        }
    }

    @Getter
    @NoArgsConstructor
    enum BET_STATUS {
        /**
         * 投注表 Xb_STATUS状态
         */
        WAITING(1, "待开奖"),
        FINISH(2, "已开奖"),
        REFUND(3, "退款");
        int code;
        String desc;

        BET_STATUS(int code, String desc) {
            this.code = code;
            this.desc = desc;
        }
    }

    /**
     * 货币类型
     */
    @Getter
    @NoArgsConstructor
    enum CATEGORY_CURRENCY {
        /**
         * VIRTUAL-数字货币
         * LEGAL-法定货币
         */
        VIRTUAL(0, "虚拟货币"),
        LEGAL(1, "法币");

        int code;
        String desc;

        CATEGORY_CURRENCY(int code, String desc) {
            this.code = code;
            this.desc = desc;
        }
    }

    /**
     * 货币类型
     */
    @Getter
    @NoArgsConstructor
    enum CATEGORY_TRANSFER {
        /**
         * 虚拟币类型
         */
        TRC(1, "TRC-20"),
        ETC(2, "ERC-20"),
        BANK(3, "银行卡"),
        PIX(4, "PIX"),
        GCASH(5, "GCASH");
        int code;
        String desc;

        CATEGORY_TRANSFER(int code, String desc) {
            this.code = code;
            this.desc = desc;
        }
    }
}
