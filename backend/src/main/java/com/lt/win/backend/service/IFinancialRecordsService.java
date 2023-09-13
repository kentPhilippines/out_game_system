package com.lt.win.backend.service;

import com.lt.win.backend.io.bo.FinancialRecords;
import com.lt.win.utils.components.pagination.ReqPage;
import com.lt.win.utils.components.pagination.ResPage;

/**
 * @description:财务记录
 * @author: andy
 * @date: 2020/8/14
 */
public interface IFinancialRecordsService {

    /**
     * 出款记录-列表
     *
     * @param reqBody reqBody
     * @return 列表
     */
    ResPage<FinancialRecords.WithdrawalListResBody> withdrawalList(ReqPage<FinancialRecords.WithdrawalListReqBody> reqBody);

    /**
     * 出款记录-统计
     *
     * @param reqBody reqBody
     * @return 统计金额
     */
    FinancialRecords.CommonCoinResBody withdrawalStatistics(FinancialRecords.WithdrawalListReqBody reqBody);

    /**
     * 入款记录-列表
     *
     * @param reqBody reqBody
     * @return 列表
     */
    ResPage<FinancialRecords.DepositListResBody> depositList(ReqPage<FinancialRecords.DepositListReqBody> reqBody);

    /**
     * 入款记录-统计
     *
     * @param reqBody reqBody
     * @return 统计金额
     */
    FinancialRecords.CommonCoinResBody depositStatistics(FinancialRecords.DepositListReqBody reqBody);


    /**
     * 平台转账-列表
     *
     * @param reqBody reqBody
     * @return 列表
     */
    ResPage<FinancialRecords.PlatTransferListResBody> platTransferList(ReqPage<FinancialRecords.PlatTransferListReqBody> reqBody);

    /**
     * 平台转账-统计
     *
     * @param reqBody reqBody
     * @return 统计金额
     */
    FinancialRecords.PlatTransferStatisticsResBody platTransferStatistics(FinancialRecords.PlatTransferListReqBody reqBody);

    /**
     * 平台转账-列表
     *
     * @param reqBody reqBody
     * @return 列表
     */
    ResPage<FinancialRecords.AdminTransferListResBody> adminTransferList(ReqPage<FinancialRecords.AdminTransferListReqBody> reqBody);

    /**
     * 平台转账-统计
     *
     * @param reqBody reqBody
     * @return 统计金额
     */
    FinancialRecords.CommonCoinResBody adminTransferStatistics(FinancialRecords.AdminTransferListReqBody reqBody);

    /**
     * 平台转账->状态更新:成功或失败
     *
     * @param reqBody
     * @return
     */
    boolean updatePlatTransferStatusById(FinancialRecords.UpdatePlatTransferStatusByIdReqBody reqBody);

    ResPage<FinancialRecords.OnlineWithdrawalListResBody> onlineWithdrawalList(ReqPage<FinancialRecords.OnlineWithdrawalListReqBody> reqBody);

    FinancialRecords.CommonCoinResBody onlineWithdrawalStatistics(FinancialRecords.OnlineWithdrawalListReqBody reqBody);

    Boolean updateOnlineWithdrawalStatus(FinancialRecords.UpdateOnlineWithdrawalReqBody reqBody);
}
