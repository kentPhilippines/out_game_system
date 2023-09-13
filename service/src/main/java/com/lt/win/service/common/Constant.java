package com.lt.win.service.common;

/**
 * <p>
 * 统一常量类
 * </p>
 *
 * @author andy
 * @since 2020-02-07
 */
public interface Constant {
    String CREATED_AT = "created_at";
    String UPDATED_AT = "updated_at";
    String CATEGORY = "category";
    String STATUS = "status";
    String DEP_STATUS = "dep_status";
    String ORDER_ID = "order_id";
    String UID = "uid";
    String ID = "id";
    String USERNAME = "username";
    String USER_ROLE = "role";
    String ROOT = "root";

    String SUPER_ADMIN = "0";

    String TERMINAL_H5 = "H5";
    String TERMINAL_PC = "PC";
    String GAME_ID_FIELD = "gameIdField";
    String ACTION_NO = "actionNo";
    //是
    Integer SUCCESS = 1;
    //否
    Integer FAIL = 0;

    /**
     * 角色:0-会员 1-代理 2-总代理 3-股东 4-测试 10-系统账号'
     */
    int USER_ROLE_HY = 0;
    /**
     * 角色:0-会员 1-代理 2-总代理 3-股东 4-测试 10-系统账号'
     */
    int USER_ROLE_DL = 1;
    /**
     * 角色:0-会员 1-代理 2-总代理 3-股东 4-测试 10-系统账号'
     */
    int USER_ROLE_CS = 4;
    /**
     * 稽核成功
     */
    String AUDIT_SUCCESS = "1";
    /**
     * 稽核失败
     */
    String AUDIT_FAIL = "2";
    /**
     * 稽核失败
     */
    String WITHDRAWAL_FAIL = "3";

    /**
     * 权限列表
     */
    String PERMISSION_LIST = "PERMISSION_LIST";

    /**
     * 充值标识:1-首充 2-二充 3-三充 9-其他
     */
    int DEP_STATUS_FIRST = 1;
    /**
     * 充值标识:1-首充 2-二充 3-三充 9-其他
     */
    int DEP_STATUS_SECOND = 2;

    String SUPPLEMENT_MARK = "投注流水不足,还需金额";

    /**
     * 存款记录
     */
    String PUSH_DN = "PUSH_DN";
    /**
     * 提款记录
     */
    String PUSH_WN = "PUSH_WN";

    /**
     * 后台
     */
    String ADMIN = "admin";
    /**
     * 前端
     */
    String WEB = "web";
    /**
     * 用户退出
     */
    String USER_QUIT = "USER_QUIT";
    /**
     * 退出标识
     */
    String LOGOUT = "logout";
    /**
     * 手机号码
     */
    String MOBILE = "mobile";
    /**
     * 真实名称
     */
    String REAL_NAME = "realName";
    /**
     * 银行卡号
     */
    String BANK_ACCOUNT = "bankAccount";
    String COMMISSION_RULE = "commission_rule";
    String SETTLE_TYPE = "settleType";
    String EFFECT_TYPE = "effectType";
    String OLD_SETTLE_TYPE = "oldSettleType";
    String OLD_EFFECT_TYPE = "oldEffectType";
    String EFFECT_DATE = "effectDate";

    /**
     * 生效方式：0-次月生效，1-立即生效（次日）
     */
    Integer EFFECT_MONTH = 0;
    Integer EFFECT_DAY = 1;

    //手机号码：0-显示，1-不显示
    String REGISTER_MOBILE = "registerMobile";
    //邀请码：0-显示(选填)，1-显示(必填)，2-不显示
    String REGISTER_INVITE_CODE = "registerInviteCode";
    //登录方式：0-用户名登录，1-手机号登录
    String LOGIN_TYPE = "loginType";
    //谷歌验证配置
    String VERIFICATION_OF_GOOGLE = "verificationOfGoogle";
    //显示谷歌验证码
    String VERIFICATION_SHOW = "1";
    //平台名称
    String TITLE = "title";
    //平台logo
    String PLAT_LOGO = "platLogo";
    //语言
    String LANG = "lang";
    //Socket地址
    String WS_SERVER = "ws_server";
    //短息发送
    String SMS = "sms";
    //app首页下载地址
    String DOWNLOAD = "download";
    //app首页下载是否展示；0-不显示，1-显示
    String DOWNLOAD_SHOW = "downloadShow";
    //app首页下载Logo的URL
    String DOWNLOAD_LOGO = "downloadLogo";
    //提款配置
    String MIN_DRAW_COIN = "MinDrawCoin";
    //提款最低金额
    String MIN_COIN = "minCoin";
    String MAX_COIN = "maxCoin";
    String FEES = "fees";
    String PC = "pc";
    String H5 = "h5";

    //提款地址状态1-默认地址(启用) 2-正常启用 3-删除
    Integer WITHDRAWAL_ADDRESS_STATUS_DEFAULT = 1;
    Integer WITHDRAWAL_ADDRESS_STATUS_NORMAL = 2;
    Integer WITHDRAWAL_ADDRESS_STATUS_DELETE = 3;

    //平台启用
    Integer PLAT_CONFIG_ENABLE = 1;
    /**
     * 充值流水倍数
     **/
    String DEPOSIT_MULTIPLE = "deposit_multiple";
    /**
     * 系统调账流水倍数
     **/
    String TRANSFER_MULTIPLE = "transfer_multiple";
    /**
     * 抽佣标识
     **/
    Integer COMM = 0;
    String COMM_TRANSFER = "COMM_TRANSFER";
    /**
     * 金额变化消息标识
     */
    String COIN_CHANGE = "COIN_CHANGE";
    String DEPOSIT_COIN_CHANGE = "DEPOSIT_COIN_CHANGE";

    String NOTICE_CHANGE = "NOTICE_CHANGE";

    /**
     * 代收标识
     */
    Integer CHANNEL_IN = 1;
    /**
     * 代付标识
     */
    Integer CHANNEL_OUT = 2;

    /**
     * PIX通道
     */
    String PIX = "PIX";
    /**
     * PIX通道
     */
    String GCASH = "GCASH";

    String GCASH_ALFA = "GCash-Alfa";
    String GCASH_C = "GCash-C";
    /**
     * PIX通道
     */
    String TRC_20 = "TRC-20";
    /**
     * 支付失败返回值
     */
    String PAY_FAIL = "FAIL";
    /**
     * 支付成功返回值
     */
    String PAY_SUCCESS = "SUCCESS";

    String OK = "ok";

    /**
     * 活动已满足
     */
    Integer SATISFIED = 1;
    /**
     * 活动申请中
     */
    Integer APPLYING = 0;

    String DIGI_SPORT = "DIGISPORT";


}

