package com.lt.win.apiend.service;

import com.lt.win.apiend.io.dto.wallet.*;
import com.lt.win.service.exception.BusinessException;
import com.lt.win.utils.components.pagination.ReqPage;
import com.lt.win.utils.components.pagination.ResPage;
import com.lt.win.utils.components.response.CodeInfo;
import com.lt.win.apiend.io.dto.wallet.*;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.Optional;

/**
 * <p>
 * 交易记录业务处理接口
 * </p>
 *
 * @author andy
 * @since 2020/4/21
 */
public interface ITransactionService {
    /**
     * 钱包-交易记录-查询交易记录
     *
     * @param reqBody
     * @return
     */
    ResPage<TransactionListResBody> transactionList(ReqPage<TransactionListReqBody> reqBody);

    /**
     * 钱包-交易记录-交易详情
     *
     * @param reqBody
     * @return
     */
    Object transactionDetail(@Valid @RequestBody TransactionDetailReqBody reqBody);

    /**
     * 钱包-交易记录-邀请记录
     *
     * @param reqBody
     * @return
     */
    TransactionInvite.ResBody transactionInvite(ReqPage<TransactionInvite.ReqBody> reqBody);

    /**
     * 提款提示信息及金额
     */
    WithdrawalHint.HintResBody withdrawalHint();

    /**
     * 请求参数检查
     *
     * @param e   请求参数
     * @param <E> 任何类型
     */
    default <E> void checkParams(E e) {
        if (Optional.ofNullable(e).isEmpty()) {
            throw new BusinessException((CodeInfo.PARAMETERS_INVALID));
        }
    }
}
