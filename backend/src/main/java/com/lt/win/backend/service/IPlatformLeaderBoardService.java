package com.lt.win.backend.service;

import com.lt.win.backend.io.bo.ComprehensiveChart;
import com.lt.win.backend.io.bo.ReportCenter;
import com.lt.win.backend.io.bo.StartEndTime;

import java.util.List;

/**
 * <p>
 * 报表中心-各平台盈利排行版
 * </p>
 *
 * @author andy
 * @since 2020/6/16
 */
public interface IPlatformLeaderBoardService {
    /**
     * 报表中心-各平台盈利排行版
     *
     * @param reqBody
     * @return
     */
    List<ReportCenter.PlatformLeaderBoardResBody> platformLeaderBoard(ReportCenter.PlatformLeaderBoardReqBody reqBody);

    /**
     * 综合走势图-各平台投注总额
     *
     * @param reqBody
     * @return
     */
    List<ComprehensiveChart.ListCoinChart> platBetCoinChart(StartEndTime reqBody);

    /**
     * 综合走势图-游戏盈亏与投注注数
     *
     * @param reqBody
     * @return
     */
    ComprehensiveChart.PlatProfitAndBetCountChartResBody platProfitAndBetCountChart(ComprehensiveChart.PlatProfitAndBetCountChartReqBody reqBody);
}
