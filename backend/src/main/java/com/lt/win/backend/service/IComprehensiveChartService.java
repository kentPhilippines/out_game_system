package com.lt.win.backend.service;

import com.lt.win.backend.io.bo.ComprehensiveChart;
import com.lt.win.backend.io.bo.StartEndTime;
import com.lt.win.backend.io.bo.user.CapitalStatisticsListReqBody;
import com.lt.win.backend.io.bo.user.CapitalStatisticsListResBody;

import java.util.List;

/**
 * <p>
 * 报表中心-综合走势图
 * </p>
 *
 * @author andy
 * @since 2020/6/16
 */
public interface IComprehensiveChartService {
    /**
     * 综合走势图-新增会员
     *
     * @return
     */
    ComprehensiveChart.TotalUserCountResBody totalRegisterUserCount();

    /**
     * 综合走势图-充值|提现|盈亏
     *
     * @return
     */
    ComprehensiveChart.TotalPlatProfitResBody totalPlatProfit();

    /**
     * 综合走势图-用户综合
     *
     * @param reqBody
     * @return
     */
    CapitalStatisticsListResBody userZH(CapitalStatisticsListReqBody reqBody);

    /**
     * 综合走势图-用户注册数(图)
     *
     * @param reqBody
     * @return
     */
    List<ComprehensiveChart.ListChart> registerUserCountChart(StartEndTime reqBody);

    /**
     * 综合走势图-登录用户(图)
     *
     * @param reqBody
     * @return
     */
    List<ComprehensiveChart.ListChart> loginUserCountChart(StartEndTime reqBody);

    /**
     * 综合走势图-充值与提现(图)
     *
     * @param reqBody
     * @return
     */
    ComprehensiveChart.DepositAndWithdrawalChart depositAndWithdrawalChart(StartEndTime reqBody);
}
