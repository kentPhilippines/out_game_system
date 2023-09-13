package com.lt.win.backend.service;

import com.lt.win.backend.io.bo.ReportCenter;
import com.lt.win.utils.components.pagination.ReqPage;
import com.lt.win.utils.components.pagination.ResPage;

/**
 * <p>
 * 主账户综合报表-统计接口
 * </p>
 *
 * @author andy
 * @since 2020/6/5
 */
public interface IMasterAccountStatisticsService {
    /**
     * 报表中心-主账户综合报表-列表
     *
     * @param reqBody reqBody
     * @return 分页列表
     */
    ResPage<ReportCenter.MasterAccountList> masterAccountList(ReqPage<ReportCenter.ListMasterAccountReqBody> reqBody);

    /**
     * 报表中心-主账户综合报表-统计
     *
     * @param reqBody reqBody
     * @return 统计结果
     */
    ReportCenter.MasterAccountStatistics masterAccountStatistics(ReportCenter.ListMasterAccountReqBody reqBody);

    /**
     * 报表中心-主账户综合报表-下级-列表
     *
     * @param reqBody reqBody
     * @return 分页列表
     */
    ResPage<ReportCenter.MasterAccountList> masterAccountListOfSubordinate(ReportCenter.MasterAccountListOfSubordinateReqBody reqBody);

    /**
     * 报表中心-主账户综合报表-下级-统计
     *
     * @param reqBody reqBody
     * @return 统计结果
     */
    ReportCenter.MasterAccountStatistics masterAccountStatisticsOfSubordinate(ReportCenter.MasterAccountListOfSubordinateReqBody reqBody);
}
