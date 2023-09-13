package com.lt.win.service.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * 体育错误枚举
 *
 * @author fangzs
 * @date 2022/11/11 23:57
 */
@Getter
@AllArgsConstructor
public enum DigiSportErrorEnum {
    /**
     * 正常
     */
    Success(0,""),
    /**
     * 网络错误
     */
    NetworkError(1,"NetworkError"),
    /**
     * 客⼾端被阻⽌
     */
    ClientBlocked(13,"ClientBlocked"),
    /**
     * 如果在 Token 和 ClientId 中有⼀些错误
     */
    AccountNotFound(19,"ClientBlocked"),
    /**
     * 货币不存在
     */
    CurrencyNotExists(20,"CurrencyNotExists"),
    /**
     * 一般异常
     */
    GeneralException(21, "GeneralException"),
    /**
     * ClientId 不存在
     */
    ClientNotFound(22, "ClientNotFound"),
    /**
     * 如果系统中没有相同订单号的 CreditBet
     */
    DocumentNotFound(28, "DocumentNotFound"),
    /**
     * 错误令牌
     */
    WrongToken(37, "WrongToken"),
    ClientMaxLimitExceeded(45, "ClientMaxLimitExceeded"),
    /**
     * 交易已经存在
     */
    TransactionAlreadyExists(46, "TransactionAlreadyExists"),
    /**
     * 不能删除回滚⽂档
     */
    CanNotDeleteRollbackDocument(56, "CanNotDeleteRollbackDocument"),
    DocumentAlreadyRollbacked(58, "DocumentAlreadyRollbacked"),
    /**
     * CreditBet 相同的 OrderNumber
     */
    NotAllowed(68, "NotAllowed"),
    /**
     * 未找到合作伙伴
     */
    PartnerNotFound(70, "PartnerNotFound"),
    /**
     * 低余额
     */
    LowBalance(71, "LowBalance"),
    /**
     * 错误的请求
     */
    Bad_Request(400, "Bad Request"),
    /**
     * 禁⽌的
     */
    Forbidden(403, "Forbidden"),
    /**
     * 内部服务器错误
     */
    InternalServerError(500, "InternalServerError"),
    /**
     * ⽆效的 BetState、WinType
     */
    InvalidInputParameters(1013, "InvalidInputParameters"),
    InvalidSignature(1016, "InvalidSignature"),
    ;
    private final int code;
    private final String message;
    @Getter
    private static final Map<Integer,String> ERROR_MAP = new HashMap<>();

    static {
        for (DigiSportErrorEnum digiSportErrorEnum: DigiSportErrorEnum.values()){
            ERROR_MAP.put(digiSportErrorEnum.getCode(), digiSportErrorEnum.getMessage());
        }
    }


}
