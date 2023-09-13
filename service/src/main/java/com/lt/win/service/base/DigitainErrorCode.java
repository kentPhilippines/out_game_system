package com.lt.win.service.base;

/**
 * Digitain错误码
 *
 * @author fangzs
 * @date 2022/8/31 18:32
 */
public interface DigitainErrorCode {
    /**
     * 成功
     */
    int SUCCESS = 1;
    /**
     * 没有回话
     */
    int NOT_TOKEN = 2;
    /**
     * 令牌过期
     */
    int OVERDUE_TOKEN = 3;
    /**
     * 找不到玩家
     */
    int NOT_USER = 4;

    /**
     * 禁用改用户
     */
    int DISABLE_USER = 5;

    /**
     * 余额不足
     */
    int LESS_AMOUNT = 6;
    /**
     * 找不到交易
     */
    int TRANSACTION_NOT_FOUND = 7;
    /**
     * 交易已经存在
     */
    int TRANSACTION_ALREADY_EXISTS = 8;
    /**
     * 游戏不存在
     */
    int GAME_NOT_FOUND = 11;
    /**
     * 签名错误
     */
    int WRONG_API_CREDENTIALS = 12;


    /**
     *
     */
    int TRANSACTION_ALREADY_ROLLED_BACK = 14;

    int WRONG_OPERATOR_ID = 15;
    int WRONG_CURRENCY_ID = 16;
    /**
     * 请求参数丢失
     */
    int REQUEST_PARAMETER_MISSING = 17;
    /**
     * 数据无效
     */
    int INVALID_DATA = 18;
    int INCORRECT_OPERATION_TYPE = 19;
    int TRANSACTION_ALREADY_WON = 20;
}
