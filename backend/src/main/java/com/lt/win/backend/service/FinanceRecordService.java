package com.lt.win.backend.service;

import com.lt.win.backend.io.dto.FinanceRecordsParams.*;
import com.lt.win.utils.components.pagination.ReqPage;
import com.lt.win.utils.components.pagination.ResPage;

/**
 * @Auther: wells
 * @Date: 2022/8/29 00:06
 * @Description:
 */
public interface FinanceRecordService {

    /**
     * 出款记录-列表
     *
     * @param reqBody reqBody
     * @return 列表
     */
    ResPage<WithdrawalListResBody> withdrawalList(ReqPage<WithdrawalListReqBody> reqBody);

    /**
     * 出款记录-统计
     *
     * @param reqBody reqBody
     * @return 统计金额
     */
    CommonCoinResBody withdrawalStatistics(WithdrawalListReqBody reqBody);

    /**
     * 入款记录-列表
     *
     * @param reqBody reqBody
     * @return 列表
     */
    ResPage<DepositListResBody> depositList(ReqPage<DepositListReqBody> reqBody);

    /**
     * 入款记录-统计
     *
     * @param reqBody reqBody
     * @return 统计金额
     */
    CommonCoinResBody depositStatistics(DepositListReqBody reqBody);

    /**
     * 人工调账-列表
     *
     * @param reqBody reqBody
     * @return 列表
     */
    ResPage<AdminTransferListResBody> adminTransferList(ReqPage<AdminTransferListReqBody> reqBody);

    /**
     * 人工调账-统计
     *
     * @param reqBody reqBody
     * @return 统计金额
     */
    CommonCoinResBody adminTransferStatistics(AdminTransferListReqBody reqBody);




}
