package com.lt.win.service.io.constant;


/**
 * 统一常量类
 *
 * @author David
 * @since 2020-02-07
 */
public interface ConstData {
    String TOKEN_SPERATOR = "_";
    Long INVALID_LOGIN_MAX_TIMES = 10L;

    int SLOT_GAME_CATEGORY = 2;
    String TOKEN_START_WITH = "Bearer ";
    String JWT_EXP_KEY = "exp";
    int ZERO = 0;
    int ONE = 1;
    int TWO = 2;
    int THREE = 3;
    int NINE = 9;
    int MINUTE = 60;

    String LANG = "Accept-Language";
    String DEVICE = "Accept-Device";
    String TOKEN = "Authorization";
    String BALANCE = "balance";
    String TIMESTAMP = "timestamp";
    int AGENT_TOTAL_LEVEL = 6;
    String BET_PREFIX = "betslips";
    String DIC_PREFIX = "dic";

    String CLASS_PATH_PREFIX = "com.lt.win.plat.service.impl.";
}

