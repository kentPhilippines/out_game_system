package com.lt.win.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lt.win.backend.io.po.PlatProfitAndBetCountChartPo;
import com.lt.win.backend.io.po.ReportStatisticsPo;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * 报表统计mapper
 * </p>
 *
 * @author andy
 * @since 2020/3/12
 */
public interface ReportStatisticsMapper extends BaseMapper<ReportStatisticsPo> {

    /**
     * 报表中心-各平台盈利排行版
     *
     * @param po PO
     * @return List<ReportStatisticsPo>
     */
    List<ReportStatisticsPo> platLeaderBoardProfitStatisticsList(ReportStatisticsPo po);

    /**
     * 报表中心-各平台盈亏报表->列表
     *
     * @param po PO
     * @return List<ReportStatisticsPo>
     */
    List<ReportStatisticsPo> getPlatformProfitList(ReportStatisticsPo po);

    /**
     * 交易记录-投注总额-SQL分页
     *
     * @param page
     * @param po
     * @return Page<ReportStatisticsPo>
     */
    Page<ReportStatisticsPo> getPlatformProfitPage(Page page, ReportStatisticsPo po);

    /**
     * 综合走势图-各平台投注总额
     *
     * @param po PO
     * @return 各平台投注总额
     */
    BigDecimal getPlatCoinStatistics(ReportStatisticsPo po);

    /**
     * 综合走势图-游戏盈亏与投注注数:游戏盈亏
     *
     * @param po PO
     * @return 游戏盈亏
     */
    List<PlatProfitAndBetCountChartPo> getPlatProfitStatistics(ReportStatisticsPo po);

    /**
     * 综合走势图-游戏盈亏与投注注数:投注注数
     *
     * @param po PO
     * @return 投注注数
     */
    List<PlatProfitAndBetCountChartPo> getPlatBetCountStatistics(ReportStatisticsPo po);

    /**
     * 综合走势图-游戏盈亏与投注注数:投注人数
     *
     * @param po PO
     * @return 投注人数
     */
    List<ReportStatisticsPo> getPlatBetUserCountStatistics(ReportStatisticsPo po);

    /**
     * 每日报表new->按平台统计:投注总额|输赢总额
     *
     * @param po PO
     * @return 投注人数
     */
    List<ReportStatisticsPo> getDailyReportStatisticsPlatCoin(ReportStatisticsPo po);

    /**
     * 每日报表new->按平台统计:投注人数
     *
     * @param po PO
     * @return 投注人数
     */
    List<ReportStatisticsPo> getBetUserCount(ReportStatisticsPo po);

    /**
     * 每日报表new->按平台统计:投注笔数
     *
     * @param po PO
     * @return 投注笔数
     */
    long getBetCount(ReportStatisticsPo po);


    /**
     * 交易记录-投注总额-列表
     *
     * @param po PO
     * @return List<ReportStatisticsPo>
     */
    List<ReportStatisticsPo> getPlatBetTotalList(ReportStatisticsPo po);

    /**
     * 交易记录-投注总额-分页
     *
     * @param page
     * @param po
     * @return Page<ReportStatisticsPo>
     */
    Page<ReportStatisticsPo> getPlatBetTotalListPage(Page page, ReportStatisticsPo po);

}
