package com.lt.win.backend.service;

import com.lt.win.backend.io.bo.ReportCenter;
import com.lt.win.backend.io.bo.StartEndTime;
import com.lt.win.utils.components.pagination.ReqPage;
import com.lt.win.utils.components.pagination.ResPage;

/**
 * <p>
 * 报表中心-每日报表-统计接口
 * </p>
 *
 * @author andy
 * @since 2020/6/5
 */
public interface IDailyReportStatisticsService {
    /**
     * 报表中心-每日报表-列表
     *
     * @param reqBody reqBody
     * @return 分页列表
     */
    ResPage<ReportCenter.DailyConversionReportListResBody> dailyReportList(ReqPage<StartEndTime> reqBody);

    /**
     * 报表中心-每日报表-统计
     *
     * @param reqBody reqBody
     * @return 统计结果
     */
    ReportCenter.DailyConversionReportStatisticsResBody dailyReportStatistics(StartEndTime reqBody);



    /**
     * @Author Wells
     * @Date 2020/9/19 5:07 下午
     * @Description 税收报表
     * @param1 reqBody
     * @Return com.lt.win.utils.components.pagination.ResPage<com.lt.win.backend.io.bo.ReportCenter.RevenueResBody>
     **/
    ResPage<ReportCenter.RevenueResBody> revenueStatistic(ReqPage<ReportCenter.RevenueReqBody> reqBody);
}
