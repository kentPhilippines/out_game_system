package com.lt.win.service.cache;

/**
 * @author admin
 */
public final class KeyConstant {
    /**
     * HASH 通用KEY
     */
    public static final String COMMON_TOTAL_HASH = "TOTAL";
    /**
     * 用户ID、Channel映射表
     */
    public static final String REL_UID_CHANNEL = "REL_UID_CHANNEL";

    /**
     * COMMON 系统配置相关
     */
    public static final String CONFIG_HASH = "CONFIG_HASH";
    public static final String CONFIG_HASH_API = "CONFIG_HASH_API";
    public static final String CONFIG_HASH_BACKEND = "CONFIG_HASH_BACKEND";
    public static final String CONFIG_HASH_JWT = "JWT";
    public static final String CONFIG_HASH_RSA = "RSA";
    public static final String GAME_LIST_ID_2_BET_SLIPS_MAPPER = "GAME_LIST_ID_2_BET_SLIPS_MAPPER";

    /**
     * 用户相关
     */
    public static final String USER_TOKEN_HASH = "USER_TOKEN_HASH";
    public static final String USER_LOGIN_INVALID_TIMES = "USER_LOGIN_INVALID_TIMES";
    public static final String SMS_CODE_HASH = "SMS_CODE_HASH";
    /**
     * 会员代理线缓存
     */
    public static final String USER_SUBORDINATE_HASH = "USER_SUBORDINATE_HASH";
    /**
     * 超级代理线缓存
     */
    public static final String USER_SUBORDINATE_SUPER_HASH = "USER_SUBORDINATE_SUPER_HASH";
    public static final String USER_INFO_HASH = "USER_INFO_HASH";
    public static final String USER_LEVEL_ID_LIST_HASH = "USER_LEVEL_ID_LIST_HASH";

    public static final String UID_IN_LEVEL_ID_HASH = "USER_HASH";
    public static final String TEST_UID_LIST = "TEST_UID_LIST";

    public static final String USER_DIGI_TOKEN = "USER_DIGI_TOKEN";
    public static final String AGENT_COMMISSION_RATE = "AGENT_COMMISSION_RATE";


    /**
     * Admin相关
     */
    public static final String ADMIN_TOKEN_HASH = "ADMIN_TOKEN_HASH";
    public static final String ADMIN_LOGIN_INVALID_TIMES = "ADMIN_LOGIN_INVALID_TIMES";
    public static final String ADMIN_INFO_ID_HASH = "ADMIN_INFO_ID_HASH";
    public static final String ADMIN_GROUP_ID_HASH = "ADMIN_GROUP_ID_HASH";

    /**
     * 首页平台游戏相关
     */
    public static final String PLATFORM_HASH = "PLATFORM_HASH";
    public static final String PLATFORM_HASH_PLAT_LIST = "PLAT_LIST";
    public static final String PLATFORM_HASH_GAME_LIST = "GAME_LIST";
    public static final String PLATFORM_HASH_GAME_GROUP_LIST = "GAME_GROUP";
    public static final String PLATFORM_HASH_GROUP_GAME_LIST = "GROUP_GAME_LIST";

    /**
     * 游戏相关
     */
    public static final String GAME_PROP_HASH = "GAME_PROP_HASH";
    public static final String GAME_LIST_HASH = "GAME_LIST_HASH";
    public static final String PLAT_LIST_HASH = "PLAT_LIST_HASH";
    public static final String GAME_SLOT_HASH = "GAME_SLOT_HASH";
    public static final String PLAT_CONFIG_HASH = "PLAT_CONFIG_HASH";

    public static final String BANNER_LIST = "BANNER_LIST";
    /**
     * 活动相关
     */
    public static final String PROMOTIONS_HASH = "PROMOTIONS_HASH";
    public static final String PROMOTIONS_HASH_CONFIG = "PROMOTIONS_HASH_CONFIG";

    /**
     * 活动触发中缓存
     */
    public static final String COIN_REWARD_HASH = "COIN_REWARD_HASH";


    /**
     * /**
     * 字典相关:api字典
     */
    public static final String DICTIONARY_API_HASH = "DICTIONARY_API_HASH";
    /**
     * 字典相关:backend字典
     */
    public static final String DICTIONARY_BACKEND_HASH = "DICTIONARY_BACKEND_HASH";
    /**
     * Netty推送缓存KEY
     */
    public static final String NETTY_PUSH_DN = "NETTY_PUSH_DN";
    public static final String NETTY_PUSH_WN = "NETTY_PUSH_WN";
    /**
     * 订阅充值信息
     */
    public static final String SUBSCRIBE_DN = "DN";
    /**
     * 支付平台配置key
     */
    public static final String PAY_PLAT_CONFIG_LIST = "PAY_PLAT_CONFIG_LIST";
    /**
     * 支付平台通道配置key
     */
    public static final String PAY_CHANNEL_LIST_ = "PAY_CHANNEL_HASH";
    /**
     * 用户提款地址key
     */
    public static final String WITHDRAWAL_ADDRESS_HASH = "WITHDRAWAL_ADDRESS_HASH";
    /**
     * 消息配置key
     */
    public static final String NOTICE_LIST = "NOTICE_LIST";
    /**
     * 消息已读配置key
     */
    public static final String NOTICE_READ_HASH = "NOTICE_READ_HASH";
    /**
     * 消息已删配置key
     */
    public static final String NOTICE_DELETE_HASH = "NOTICE_DELETE_HASH";
    /**
     * 币种价格key
     */
    public static final String CURRENCY_PRICE = "CURRENCY_PRICE";
    /**
     * 汇率HASH
     */
    public static final String COIN_RATE_HASH = "COIN_RATE_HASH";

    /**
     * 三方游戏 Rollback 回滚ID缓存
     */
    public static final String PLAT_ROLLBACK_ID_HASH = "PLAT_ROLLBACK_ID_HASH[%s]";


    /**
     * websSocket uid在线缓存
     */
    public static final String UID_ONLINE_HASH = "UID_ONLINE_HASH";

    /**
     * 充值订单失效
     */
    public static final String DEPOSIT_ORDER = "DEPOSIT_ORDER";

    /**
     * websSocket uid在线缓存
     */
    public static final String WITHDRAWAL_HASH = "WITHDRAWAL_HASH";
    /**
     * websSocket 消息队列
     */
    public static final String WEB_SOCKET_QUEUE = "WEB_SOCKET__QUEUE";
    /**
     * 活动期间总流水HASH
     */
    public static final String ACTIVITY_BET_SUM_HASH = "ACTIVITY_BET_SUM_HASH";
    /**
     * 快速游戏退款回合单号
     */
    public static final String DIGITAIN_REFUND_ROUNDID_KEY = "Digitain:Refund:RoundId:%s";
    /**
     * 三方游戏TOKEN_KEY
     */
    public static final String GAME_HABA_TOKEN = "GAME_HABA_TOKEN";
    public static final String GAME_PG_SOFT_TOKEN = "GAME_PG_SOFT_TOKEN";
    public static final String GAME_YGG_SLOT_TOKEN = "GAME_YGG_SLOT_TOKEN";
    public static final String GAME_ONE_GAME_SITE_SESSION_TOKEN = "GAME_ONE_GAME_SITE_SESSION_TOKEN";
    public static final String GAME_ONE_GAME_GAME_SESSION_TOKEN = "GAME_ONE_GAME_GAME_SESSION_TOKEN";
    public static final String GAME_BTI_SPORTS_TOKEN = "GAME_BTI_SPORTS_TOKEN";

    /**
     * 用户退出缓存
     **/
    public static final String USER_LOGOUT_HASH = "USER_LOGOUT_HASH";
}
