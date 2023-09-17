package com.lt.win.utils.components.response;

import lombok.Getter;
import org.jetbrains.annotations.NotNull;

/**
 * @author: David
 * @date: 06/04/2020
 * @description: 总异常状态码
 */
@Getter
public enum CodeInfo {
    // 默认状态码
    STATUS_CODE_ERROR(-1, "system error"),
    STATUS_CODE_SUCCESS(0, "success"),
    STATUS_CODE_400(400, "bad request"),
    STATUS_CODE_401(401, "Invalid Token"),
    STATUS_CODE_401_2(401, "TOKEN has expired"),
    STATUS_CODE_403(403, "forbidden"),
    STATUS_CODE_403_6(403, "IP address rejected"),
    STATUS_CODE_404(404, "Not Found"),
    STATUS_CODE_405(405, "Resources are forbidden"),
    STATUS_CODE_408(408, "Request timed out"),
    STATUS_CODE_500(500, "Internal server error, please contact the administrator"),

    // 请求统一异常
    PARAMETERS_INVALID(10000, "Parameters Invalid"),
    HEADER_LANG_ERROR(10002, "Invalid language"),
    HEADER_DEVICE_ERROR(10003, "Invalid device source"),
    CONFIG_INVALID(10007, "Configuration error[%s]"),

    /***************** 用户模块 20000 - 29999 ******************/
    // [账户 20100 - 20199]
    ACCOUNT_EXISTS(20100, "Username already exists"),
    ACCOUNT_NOT_EXISTS(20102, "Username no exists"),
    AGENT_ACCOUNT_NOT_EXISTS(20102, "Agent account no exists[%s]"),
    AGENT_ACCOUNT_INVALID(20102, "Agent account invalid[%s]"),
    AGENT_LOGIN_URL_INVALID(20102, "Please login the agent url"),
    AGENT_TRANSFER_MEMBER_TO_SUPER_AGENT_INVALID(20102, "Can't transfer member agent to super agent"),
    ACCOUNT_STATUS_INVALID(20104, "Account status is abnormal, please concat the online customer"),
    VERIFICATION_CODE_INVALID(20105, "Verification Code no exists"),


    // [密码 20400 - 20499]
    PASSWORD_INVALID(20401, "Invalid password. Please try again!"),
    GOOGLE_CODE_INVALID(20403, "INVALID GOOGLE CODE"),
    // [手机 20500 - 20599]
    MOBILE_EXISTS(20502, "Phone number already exists"),
    EMAIL_EXISTS(20503, "email already exists"),
    // [推广码 20600 - 20699]
    PROMO_CODE_INVALID(20601, "Share Code Invalid"),
    // [金额 20700 - 20799]
    COIN_TRANSFER_INVALID(20701, "Transaction Error"),
    COIN_NOT_ENOUGH(20706, "Insufficient balance"),
    COIN_TRANSFER_OVER_LIMIT(20707, "At least 1%s"),
    // [登录/登出 20800 - 20899]
    LOGIN_INVALID_OVER_LIMIT(20802, "Too many login errors, please contact the administrator or retrieve your password"),
    // [注册 20900 - 20999]
    REGISTER_INVALID(20902, "Registration error"),
    UPDATE_ERROR(21203, "modification failed"),
    EMAIL_INVALID(21204, "Email address invalid"),

    /***************** SMS 30000 - 30099 ******************/
    SES_CODE_INVALID(30003, "Ses Code Invalid"),
    SES_CODE_EXPIRED(30004, "Ses Code expired"),
    SES_CODE_ALREADY_USED(30005, "Ses Code already used"),

    /***************** 优惠活动 30100 - 30199 ******************/
    ACTIVE_BET_COIN_NO_ENOUGH(30119, "Insufficient valid bet amount!"),
    ACTIVE_CODE_REPEAT(30126, "Active code can not be repeated!！"),
    ACTIVE_APPLY_ID_INVALID(30131, "Active apply id invalid"),
    ACTIVE_FIRST_DEPOSIT_ALREADY_DEPOSIT(30131, "Already deposit can't join this active"),
    ACTIVE_REPEAT_APPLY(30131, "Active repeat apply"),
    ACTIVE_DEPOSIT_NUM_OUT_OF_LIMIT(30131, "Active deposit num out of limit"),
    ACTIVE_ONE_TIME_ONLY_APPLY_1(30131, "Only can join 1 activity at a time"),
    ACTIVE_FIRST_ORDER_EXCEPTION(30132, "The first order has been completed"),


    /***************** 游戏平台相关 39000 - 39999 ******************/
    // 本平台异常
    GAME_NOT_EXISTS(39001, "Game no exist"),
    GAME_NOT_OPEN(39002, "The game is close for maintenance, please wait patiently"),
    GAME_UNDER_MAINTENANCE(39003, "The game is under maintenance"),
    GAME_SLOT_FAVORITE_ALREADY(39004, "Already in favorite list"),
    GAME_SLOT_FAVORITE_NOT_YET(39005, "Please collect to the favorite list first"),
    GAME_RECORDS_EMPTY(39006, "No such game record!"),
    PLAT_FACTORY_NOT_EXISTS(39007, "Platform factory does not exist"),
    // 三方平台映射异常
    PLAT_INVALID_PARAM(39901, "Invalid Parameter "),

    PLAT_REQUEST_FREQUENT(39908, "Request too frequent"),
    PLAT_INVALID_CURRENCY(39909, "Invalid currency type"),
    PLAT_INVALID_LANGUAGE(39910, "Invalid language type"),

    /***************** APIEND 50000 - 59999 ******************/
    API_COIN_COMMISSION_INSUFFICIENT(5006, "Commission coin Insufficient"),

    /***************** BACKEND 60000 - 69999 ******************/
    USER_SUP_ACCOUNT_NOT_EXISTS(60002, "The superior agent does not exist"),
    USER_TRANSACTION_RECORD_LIST_ERROR(60005, "Transaction record query fail"),
    USER_NOT_EXISTS(60016, "Member no exist"),
    ADMIN_GROUP_EXIST(60018, "admin group exist"),
    ROLE_DELETE_VERIFICATION(60019, "This role has been associated with users, if you want to delete, please remove all users corresponding to the role!"),
    ADMIN_GROUP_DELETE_VERIFICATION(60020, "This group has been associated with users. If you need to delete it, please remove all users corresponding to the roles in the group!"),
    NO_AUTH_TO_GET_RULES(60021, "no auth to get rules"),
    AGENT_GROUP_NO_UPDATE(60022, "agent group don't update"),
    ROLE_ALREADY_EXIST(60018, "role already exist!"),
    REPORT_TEST_USER_NOT_SEARCH_DATA(60019, "Test account cannot inquiry report data"),
    ACCOUNT_NOT_EXISTS_USER_NAME(60019, "Account not exists user name[%s]"),

    /***************** 图片上传 70000 - 70100 ******************/
    UPLOAD_IMAGE_FAIL(70003, "Failed to upload file"),

    /*****************注单异常  70100 - 70200 ******************/
    ES_SYNCHRONOUS_ERROR(70100, "ES user coinLog synchronization exception"),
    ES_CURRENT_SUM_BET_ERROR(70101, "ES Statistics The current bet flow is abnormal"),


    WITHDRAWAL_ORDER_ERROR(70105, "withdrawal order  error"),
    BETSLIPS_REFUND_ERROR(70106, "betslips refund error"),

    WITHDRAWAL_AMOUNT_ERROR(70107, "withdrawal amount  less than the minimum withdrawal amount"),
    WITHDRAWAL_ORDERS_NO_PROCESS(70108, "withdrawal orders no process"),
    COIN_RATE_EXCEPTION(70109, "coin rate does not exist"),
    WITHDRAWAL_ADDRESS_COUNT_EXCEPTION(70110, "withdrawal address article 3 the most "),
    BETSLIPS_REPETITION_ERROR(70111, "betslips save repetition error"),

    BETSLIPS_REFUND_ORDER_NOTHINGNESS_ERROR(70112, "betslips refund order nothingness error"),

    NO_BETTING_WAS_RECORDED(70115, "No betting was recorded"),

    REPEATED_BETS(70116, "Repeated bets"),

    NOTICE_UID_EXCEPTION(70117, "the uid of the in-station message cannot be empty "),
    /*****************Digi 体育异常  70300 - 70400 ******************/

    SIGNATURE_VERIFICATION_ERROR(70300, "sports signature verification error"),

    PARTNERID_ERROR(70301, "sports PartnerId error"),
    CURRENCYID_ERROR(70302, "sports currencyid error"),

    USER_NOT_EXIST(70304, "sports user not exist"),

    USER_BLACK(70314, "sports user black"),

    BET_TRANSACTION_ID_EXIST(70305, "sports bet transaction id exist"),
    BET_ORDER_NUMBER_EXIST(70306, "sports bet order number exist"),
    BET_AMOUNT_MAX(70307, "sports bet amount max"),
    RECORD_DOES_NOT_EXIST(70308, "The record does not exist"),
    WITHDRAWAL_AMOUNT_ABNORMAL(70308, "The withdrawal amount is abnormal"),
    PAY_CHANNEL_IS_CLOSE(70309, "pay channel is close!"),
    PAY_PLAT_IS_CLOSE(70310, "pay plat is close!"),
    WITHDRAWAL_HAS_BEEN_INITIATED(70311, "withdrawal order payment has been initiated"),

    WITHDRAWAL_ADDRESS_REPEAT(70313, "withdrawal address repeat"),
    COIN_INVALID(70314, "amount is invalid"),

    BET_COIN_EXCEPTION(80001, "钱包余额不足!"),
    BET_TIME_EXCEPTION(80002, "当前期投资结束,请下期继续!"),

    // 不要直接抛出异常、仅作用于中转
    DUPLICATE_KEY_ERROR(10004, "Not Allow Duplicate data"),
    DEFINED_EXCEPTION(99000, "自定义异常"),
    UPDATE_EXCEPTION(99001, "数据库保存异常"),
    UPDATE_COIN_LOG_EXCEPTION(99002, "帐变异常");


    private String msg;
    private int code;

    CodeInfo(int code, String msg) {
        this.msg = msg;
        this.code = code;
    }

    /**
     * 自定义异常枚举
     *
     * @param code 需要设置的Code
     * @param msg  需要设置的异常信息
     * @return 组装后的枚举
     */
    @NotNull
    public static CodeInfo setMsg(int code, String msg) {
        CodeInfo info = CodeInfo.DEFINED_EXCEPTION;
        info.setCode(code);
        info.setMsg(msg);
        return info;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    void setCode(int code) {
        this.code = code;
    }
}
