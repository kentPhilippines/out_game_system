package com.lt.win.service.base;

import lombok.Getter;

/**
 * 游戏平台Code枚举
 *
 * @author fangzs
 */
@Getter
public enum PlatCodeEnum {
    /**
     * PlatList Code值
     */
    DIGITAIN("Digitain"),
    PLAYSON("BngSlot"),
    GMT_SLOT("GmtSlot"),

    ONE_GAME("OneGame"),
    PG_SOFT("PgSoft"),
    DRAGON("Dragon"),
    EZUGI("Ezugi"),
    HABA("HABA"),
    JDB("JDB"),
    YGG("YGG"),
    DIGISPORT("DigiSport");
    String code;

    PlatCodeEnum(String code) {
        this.code = code;
    }
}
