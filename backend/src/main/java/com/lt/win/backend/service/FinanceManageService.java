package com.lt.win.backend.service;

import com.lt.win.backend.io.dto.FinanceManageParams.*;
import com.lt.win.utils.components.pagination.ReqPage;
import com.lt.win.utils.components.pagination.ResPage;
import com.lt.win.utils.components.response.CodeInfo;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.List;

/**
 * @Auther: wells
 * @Date: 2022/8/24 01:47
 * @Description:
 */
public interface FinanceManageService {

    ResPage<DepositRecordRes> depositRecordList(ReqPage<DepositRecordReq> req);

    DepositSumResDto depositSum(DepositRecordReq reqDto);

    UpdateDepositStatusResDto updateDepositStatus(UpdateDepositStatusReqDto reqDto);

    DepositDetailResDto depositDetail(DepositDetailReqDto reqDto);

    ResPage<WithdrawalRecordRes> withdrawalRecordList(ReqPage<WithdrawalRecordReq> req);

    WithdrawalSumResDto withdrawalSum(WithdrawalRecordReq reqDto);

    /**
     * 修改提款记录
     *
     * @param reqDto
     * @return
     */
    UpdateWithdrawalStatusResDto updateWithdrawalStatus(UpdateWithdrawalStatusReqDto reqDto);

    /**
     * 提款记录详情
     *
     * @param reqDto
     * @return
     */
    WithdrawalDetailResDto withdrawalDetail(WithdrawalDetailReqDto reqDto);

    /**
     * 稽核
     *
     * @param reqDto
     * @return
     */
    UpdateWithdrawalStatusResDto isAudit(AuditReqDto reqDto);

    /**
     * @return AuditDetailResDto
     * @Description 稽核详情
     * @Param [reqDto]
     **/
    AuditDetailResDto auditDetail(ReqPage<AuditReqDto> reqDto);

    /**
     * 人工调;
     *
     * @param reqDto
     * @return
     */
    boolean adminTransfer(AdminTransferReqDto reqDto);

    /**
     * @return java.util.List<com.lt.win.backend.io.dto.FinanceManageParams.UserCoinRes>
     * @Description 获取用户余额
     * @Param [reqDto]
     **/
    UserCoinRes getUserCoin(UserCoinReq reqDto);


    List<PayChannelRes> getChannelList(PayChannelReq req);

    Boolean replenishmentOrder(@Valid @RequestBody ReplenishmentOrderReq req);
}
