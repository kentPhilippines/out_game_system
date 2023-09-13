package com.lt.win.backend.controller;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.lt.win.backend.io.bo.ComprehensiveChart;
import com.lt.win.backend.io.bo.ReportCenter.*;
import com.lt.win.backend.io.bo.StartEndTime;
import com.lt.win.backend.io.bo.user.CapitalStatisticsListReqBody;
import com.lt.win.backend.io.bo.user.CapitalStatisticsListResBody;
import com.lt.win.backend.service.IComprehensiveChartService;
import com.lt.win.backend.service.IDailyReportStatisticsService;
import com.lt.win.backend.service.IMasterAccountStatisticsService;
import com.lt.win.backend.service.IPlatformLeaderBoardService;
import com.lt.win.backend.service.impl.DailyReportExStatisticsServiceImpl;
import com.lt.win.backend.service.impl.PlatformProfitReportServiceImpl;
import com.lt.win.utils.components.pagination.ReqPage;
import com.lt.win.utils.components.pagination.ResPage;
import com.lt.win.utils.components.response.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * <p>
 * 报表中心
 * </p>
 *
 * @author andy
 * @since 2020/6/15
 */
@Slf4j
@RestController
@RequestMapping("/v1/report")
@Api(tags = "报表中心")
public class ReportCenterController {
    @Resource
    private IMasterAccountStatisticsService masterAccountStatisticsServiceImpl;
    @Resource
    private IPlatformLeaderBoardService platformLeaderBoardServiceImpl;
    @Resource
    private IComprehensiveChartService comprehensiveChartServiceImpl;
    @Resource
    private IDailyReportStatisticsService dailyReportStatisticsServiceImpl;
    @Resource
    private DailyReportExStatisticsServiceImpl dailyReportExStatisticsServiceImpl;
    @Resource
    private PlatformProfitReportServiceImpl platformProfitReportServiceImpl;


    @ApiOperationSupport(author = "Andy", order = 1)
    @ApiOperation(value = "主账户综合报表-列表", notes = "主账户综合报表-列表")
    @PostMapping("/masterAccountList")
    public Result<ResPage<MasterAccountList>> masterAccountList(@Valid @RequestBody ReqPage<ListMasterAccountReqBody> reqBody) {
        return Result.ok(masterAccountStatisticsServiceImpl.masterAccountList(reqBody));
    }

    @ApiOperationSupport(author = "Andy", order = 2)
    @ApiOperation(value = "主账户综合报表-统计", notes = "主账户综合报表-统计")
    @PostMapping("/masterAccountStatistics")
    public Result<MasterAccountStatistics> masterAccountStatistics(@Valid @RequestBody ListMasterAccountReqBody reqBody) {
        return Result.ok(masterAccountStatisticsServiceImpl.masterAccountStatistics(reqBody));
    }

    @ApiOperationSupport(author = "Andy", order = 3)
    @ApiOperation(value = "主账户综合报表-列表-下级", notes = "主账户综合报表-列表-下级")
    @PostMapping("/masterAccountListOfSubordinate")
    public Result<ResPage<MasterAccountList>> masterAccountListOfSubordinate(@Valid @RequestBody MasterAccountListOfSubordinateReqBody reqBody) {
        return Result.ok(masterAccountStatisticsServiceImpl.masterAccountListOfSubordinate(reqBody));
    }

    @ApiOperationSupport(author = "Andy", order = 4, ignoreParameters = {"current", "size"})
    @ApiOperation(value = "主账户综合报表-统计-下级", notes = "主账户综合报表-统计-下级")
    @PostMapping("/masterAccountStatisticsOfSubordinate")
    public Result<MasterAccountStatistics> masterAccountStatisticsOfSubordinate(@Valid @RequestBody MasterAccountListOfSubordinateReqBody reqBody) {
        return Result.ok(masterAccountStatisticsServiceImpl.masterAccountStatisticsOfSubordinate(reqBody));
    }

    @ApiOperationSupport(author = "Andy", order = 5)
    @ApiOperation(value = "综合走势图-新增会员数", notes = "综合走势图-新增会员:查询会员和代理人数")
    @PostMapping("/comprehensiveChart/totalRegisterUserCount")
    public Result<ComprehensiveChart.TotalUserCountResBody> totalRegisterUserCount() {
        return Result.ok(comprehensiveChartServiceImpl.totalRegisterUserCount());
    }

    @ApiOperationSupport(author = "Andy", order = 6)
    @ApiOperation(value = "综合走势图-充值|提现|盈亏", notes = "综合走势图-充值|提现|盈亏:盈亏=存-提")
    @PostMapping("/comprehensiveChart/totalPlatProfit")
    public Result<ComprehensiveChart.TotalPlatProfitResBody> totalPlatProfit() {
        return Result.ok(comprehensiveChartServiceImpl.totalPlatProfit());
    }

    @ApiOperationSupport(author = "Andy", order = 7, ignoreParameters = {"reqBody.uid"})
    @ApiOperation(value = "综合走势图-用户综合", notes = "综合走势图-用户综合")
    @PostMapping("/comprehensiveChart/userZH")
    public Result<CapitalStatisticsListResBody> userZH(@Valid @RequestBody CapitalStatisticsListReqBody reqBody) {
        CapitalStatisticsListResBody resBody = comprehensiveChartServiceImpl.userZH(reqBody);
        return Result.ok(resBody);
    }

    @ApiOperationSupport(author = "Andy", order = 8)
    @ApiOperation(value = "综合走势图-用户注册(图)", notes = "综合走势图-用户注册(图)")
    @PostMapping("/comprehensiveChart/registerUserCountChart")
    public Result<List<ComprehensiveChart.ListChart>> registerUserCountChart(@Valid @RequestBody StartEndTime reqBody) {
        return Result.ok(comprehensiveChartServiceImpl.registerUserCountChart(reqBody));
    }

    @ApiOperationSupport(author = "Andy", order = 9)
    @ApiOperation(value = "综合走势图-登录用户(图)", notes = "综合走势图-登录用户(图)")
    @PostMapping("/comprehensiveChart/loginUserCountChart")
    public Result<List<ComprehensiveChart.ListChart>> loginUserCountChart(@Valid @RequestBody StartEndTime reqBody) {
        return Result.ok(comprehensiveChartServiceImpl.loginUserCountChart(reqBody));
    }

    @ApiOperationSupport(author = "Andy", order = 10)
    @ApiOperation(value = "综合走势图-游戏盈亏与投注注数", notes = "综合走势图-游戏盈亏与投注注数:gameList")
    @PostMapping("/comprehensiveChart/platProfitAndBetCountChart")
    public Result<ComprehensiveChart.PlatProfitAndBetCountChartResBody> platProfitAndBetCountChart(@Valid @RequestBody ComprehensiveChart.PlatProfitAndBetCountChartReqBody reqBody) {
        return Result.ok(platformLeaderBoardServiceImpl.platProfitAndBetCountChart(reqBody));
    }

    @ApiOperationSupport(author = "Andy", order = 11)
    @ApiOperation(value = "综合走势图-各平台投注总额", notes = "综合走势图-各平台投注总额:gameList")
    @PostMapping("/comprehensiveChart/platBetCoinChart")
    public Result<List<ComprehensiveChart.ListCoinChart>> platBetCoinChart(@Valid @RequestBody StartEndTime reqBody) {
        return Result.ok(platformLeaderBoardServiceImpl.platBetCoinChart(reqBody));
    }

    @ApiOperationSupport(author = "Andy", order = 12)
    @ApiOperation(value = "综合走势图-充值与提现(图)", notes = "综合走势图-充值与提现(图)")
    @PostMapping("/comprehensiveChart/depositAndWithdrawalChart")
    public Result<ComprehensiveChart.DepositAndWithdrawalChart> depositAndWithdrawalChart(@Valid @RequestBody StartEndTime reqBody) {
        return Result.ok(comprehensiveChartServiceImpl.depositAndWithdrawalChart(reqBody));
    }

    @ApiOperationSupport(author = "Andy", order = 15)
    @ApiOperation(value = "各平台盈利排行版", notes = "各平台盈利排行版")
    @PostMapping("/platformLeaderBoard")
    public Result<List<PlatformLeaderBoardResBody>> platformLeaderBoard(@Valid @RequestBody PlatformLeaderBoardReqBody reqBody) {
        return Result.ok(platformLeaderBoardServiceImpl.platformLeaderBoard(reqBody));
    }

    @ApiOperationSupport(author = "Andy", order = 16)
    @ApiOperation(value = "每日转化报表-列表", notes = "每日转化报表-列表")
    @PostMapping("/dailyConversionReportList")
    public Result<ResPage<DailyConversionReportListResBody>> dailyReportList(@Valid @RequestBody ReqPage<StartEndTime> reqBody) {
        return Result.ok(dailyReportStatisticsServiceImpl.dailyReportList(reqBody));
    }

    @ApiOperationSupport(author = "Andy", order = 17)
    @ApiOperation(value = "每日转化报表-统计", notes = "每日转化报表-统计")
    @PostMapping("/dailyConversionReportStatistics")
    public Result<DailyConversionReportStatisticsResBody> dailyReportStatistics(@Valid @RequestBody StartEndTime reqBody) {
        return Result.ok(dailyReportStatisticsServiceImpl.dailyReportStatistics(reqBody));
    }

    @ApiOperationSupport(author = "wells", order = 18)
    @ApiOperation(value = "每月报表-税收统计", notes = "每月报表-税收统计")
    @PostMapping("/revenueStatistic")
    public Result<ResPage<RevenueResBody>> revenueStatistic(@Valid @RequestBody ReqPage<RevenueReqBody> reqBody) {
        return Result.ok(dailyReportStatisticsServiceImpl.revenueStatistic(reqBody));
    }

    @ApiOperationSupport(author = "Andy", order = 19)
    @ApiOperation(value = "每日报表new->综合统计", notes = "每日报表->综合统计")
    @PostMapping("/dailyReportStatisticsSynthesis")
    public Result<CapitalStatisticsListResBody> dailyReportStatisticsSynthesis(@Valid @RequestBody DailyReportStatisticsReqBody reqBody) {
        return Result.ok(dailyReportExStatisticsServiceImpl.dailyReportStatisticsSynthesis(reqBody));
    }

    @ApiOperationSupport(author = "Andy", order = 20)
    @ApiOperation(value = "每日报表new->投注/充值/提现(笔数/人数)统计", notes = "每日报表->投注/充值/提现(笔数/人数)统计")
    @PostMapping("/dailyReportStatisticsCount")
    public Result<DailyReportStatisticsCountResBody> dailyReportStatisticsCount(@Valid @RequestBody DailyReportStatisticsReqBody reqBody) {
        return Result.ok(dailyReportExStatisticsServiceImpl.dailyReportStatisticsCount(reqBody));
    }

    @ApiOperationSupport(author = "Andy", order = 21)
    @ApiOperation(value = "每日报表new->按平台统计:投注总额|输赢总额", notes = "每日报表-平台列表-统计:投注总额|输赢总额")
    @PostMapping("/dailyReportStatisticsPlatCoin")
    public Result<List<DailyReportStatisticsPlatCoinResBody>> dailyReportStatisticsPlatCoin(@Valid @RequestBody DailyReportStatisticsReqBody reqBody) {
        return Result.ok(dailyReportExStatisticsServiceImpl.dailyReportStatisticsPlatCoin(reqBody));
    }

    @ApiOperationSupport(author = "Andy", order = 22)
    @ApiOperation(value = "各平台盈亏报表->列表", notes = "各平台盈亏报表->列表")
    @PostMapping("/getPlatformProfitList")
    public Result<ResPage<PlatformProfitListResBody>> getPlatformProfitList(@Valid @RequestBody ReqPage<PlatformProfitListReqBody> reqBody) {
        return Result.ok(platformProfitReportServiceImpl.getPlatformProfitList(reqBody));
    }

    @ApiOperationSupport(author = "Andy", order = 23)
    @ApiOperation(value = "各平台盈亏报表->统计", notes = "各平台盈亏报表->统计")
    @PostMapping("/getPlatformProfit")
    public Result<PlatformProfitResBody> getPlatformProfit(@Valid @RequestBody PlatformProfitListReqBody reqBody) {
        return Result.ok(platformProfitReportServiceImpl.getPlatformProfit(reqBody));
    }

    @ApiOperationSupport(author = "Andy", order = 24)
    @ApiOperation(value = "各平台盈亏报表->导出列表", notes = "各平台盈亏报表->导出列表")
    @PostMapping("/exportPlatformProfitList")
    public Result<List<PlatformProfitListResBody>> exportPlatformProfitList(@Valid @RequestBody PlatformProfitListReqBody reqBody) {
        return Result.ok(platformProfitReportServiceImpl.exportPlatformProfitList(reqBody));
    }

}
