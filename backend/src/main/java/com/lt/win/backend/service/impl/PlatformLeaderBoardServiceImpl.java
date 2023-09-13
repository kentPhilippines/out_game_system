package com.lt.win.backend.service.impl;

import com.lt.win.backend.base.ReportCommon;
import com.lt.win.backend.base.ReportServiceBase;
import com.lt.win.backend.io.bo.ComprehensiveChart.ListChart;
import com.lt.win.backend.io.bo.ComprehensiveChart.ListCoinChart;
import com.lt.win.backend.io.bo.ComprehensiveChart.PlatProfitAndBetCountChartReqBody;
import com.lt.win.backend.io.bo.ComprehensiveChart.PlatProfitAndBetCountChartResBody;
import com.lt.win.backend.io.bo.PlatProfitAndBetCountBo;
import com.lt.win.backend.io.bo.ReportCenter;
import com.lt.win.backend.io.bo.StartEndTime;
import com.lt.win.backend.io.dto.Platform;
import com.lt.win.backend.io.po.PlatProfitAndBetCountChartPo;
import com.lt.win.backend.io.po.ReportStatisticsPo;
import com.lt.win.backend.mapper.ReportStatisticsMapper;
import com.lt.win.backend.redis.GameCache;
import com.lt.win.backend.service.IPlatformLeaderBoardService;
import com.lt.win.service.base.BetStatisticBase;
import com.lt.win.service.base.CoinLogStatisticBase;
import com.lt.win.service.base.ESSqlSearchBase;
import com.lt.win.service.base.EsTableNameBase;
import com.lt.win.service.cache.redis.ConfigCache;
import com.lt.win.service.cache.redis.UserCache;
import com.lt.win.service.exception.BusinessException;
import com.lt.win.service.io.dto.Betslips;
import com.lt.win.service.io.dto.CoinLog;
import com.lt.win.utils.BeanConvertUtils;
import com.lt.win.utils.components.response.CodeInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * <p>
 * 报表中心-各平台盈利排行版
 * </p>
 *
 * @author andy
 * @since 2020/6/16
 */
@Slf4j
@Service
public class PlatformLeaderBoardServiceImpl implements IPlatformLeaderBoardService {
    private static final AtomicInteger REPORT_THREAD_POOL_ID = new AtomicInteger();
    @Resource
    private ReportStatisticsMapper reportStatisticsMapper;
    @Resource
    private ReportServiceBase reportServiceBase;
    @Resource
    private ReportCommon reportCommon;
    @Resource
    private ConfigCache configCache;
    @Resource
    private GameCache gameCache;
    @Resource
    private UserCache userCache;
    @Resource
    private ESSqlSearchBase esSqlSearchBase;
    @Resource
    private BetStatisticBase betStatisticBase;

    private ThreadPoolExecutor getReportThreadPool() {
        return new ThreadPoolExecutor(
                32,
                48,
                2L,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(),
                x -> new Thread(x, "报表中心->统计_THREAD_POOL_" + REPORT_THREAD_POOL_ID.getAndIncrement()));

    }

    @Override
    public List<ReportCenter.PlatformLeaderBoardResBody> platformLeaderBoard(ReportCenter.PlatformLeaderBoardReqBody reqBody) {
        List<ReportCenter.PlatformLeaderBoardResBody> list = null;
        ReportStatisticsPo reqBo = BeanConvertUtils.beanCopy(reqBody, ReportStatisticsPo::new);

        /** 排除测试账号 **/
        List<Integer> testUidList = userCache.getTestUidList();
        reqBo.setTestUidList(testUidList);
        List<ReportStatisticsPo> reportStatisticsPos = reportStatisticsMapper.platLeaderBoardProfitStatisticsList(reqBo);
        if (Optional.ofNullable(reportStatisticsPos).isPresent()) {
            list = BeanConvertUtils.copyListProperties(reportStatisticsPos, ReportCenter.PlatformLeaderBoardResBody::new);
        }
        return list;
    }

    /**
     * 综合走势图-各平台投注总额
     *
     * @param reqBody
     * @return
     */
    @Override
    public List<ListCoinChart> platBetCoinChart(StartEndTime reqBody) {
        if (null == reqBody) {
            throw new BusinessException(CodeInfo.PARAMETERS_INVALID);
        }
        List<ListCoinChart> betCoinList = new ArrayList<>();
        var gameList = gameList();
        if (!gameList.isEmpty()) {
            for (var entity : gameList) {
                // 各平台投注额
                BigDecimal coin = betStatisticBase.getValidBetCoinByGameId(reqBody.getStartTime(), reqBody.getEndTime(), entity.getId());
                ListCoinChart coinEntity = ListCoinChart.builder()
                        .name(entity.getName())
                        .coin(coin)
                        .build();
                betCoinList.add(coinEntity);
            }
        }
        return betCoinList;
    }

    /**
     * 综合走势图-游戏盈亏与投注注数
     *
     * @param reqBody
     * @return
     */
    @Override
    public PlatProfitAndBetCountChartResBody platProfitAndBetCountChart(PlatProfitAndBetCountChartReqBody reqBody) {
        List<ListChart> betCountList = new ArrayList<>();
        List<ListChart> betUserCountList = new ArrayList<>();
        List<ListCoinChart> profitList = new ArrayList<>();
        StringBuilder stringBuilder = new StringBuilder();
        String betslipsIndex = EsTableNameBase.getTableName(Betslips.class);
        stringBuilder.append("select createTimeStr,count(id) as betCount,ifNull(sum(xbProfit),0) as profit,count(DISTINCT xbUid) as betUserCount from  ")
                .append(betslipsIndex);
        stringBuilder.append(" where createdAt >= ").append(reqBody.getStartTime())
                .append(" and createdAt <=").append(reqBody.getEndTime());
        /** 排除测试账号 **/
        var testUidList = userCache.getTestUidList();
        if (CollectionUtils.isNotEmpty(testUidList)) {
            stringBuilder.append(" and xbUid not in(").append(StringUtils.join(testUidList, ",")).append(")");
        }
        if (null != reqBody.getGameListId()) {
            stringBuilder.append(" gameId = ").append(reqBody.getGameListId());
        }
        stringBuilder.append(" group by createTimeStr order by createTimeStr asc");
        List<PlatProfitAndBetCountBo> platProfitAndBetCountBoList = esSqlSearchBase.search(stringBuilder.toString(), PlatProfitAndBetCountBo::new);
        for (PlatProfitAndBetCountBo platProfitAndBetCountBo : platProfitAndBetCountBoList) {
            ListChart betCountChart = new ListChart();
            ListChart betUserCountChart = new ListChart();
            ListCoinChart coinChart = new ListCoinChart();
            betCountChart.setName(platProfitAndBetCountBo.getCreateTimeStr());
            betCountChart.setCount(platProfitAndBetCountBo.getBetCount());
            betCountList.add(betCountChart);
            betUserCountChart.setName(platProfitAndBetCountBo.getCreateTimeStr());
            betUserCountChart.setCount(platProfitAndBetCountBo.getBetUserCount());
            betUserCountList.add(betUserCountChart);
            coinChart.setName(platProfitAndBetCountBo.getCreateTimeStr());
            coinChart.setCoin(platProfitAndBetCountBo.getProfit());
            profitList.add(coinChart);
        }
        return PlatProfitAndBetCountChartResBody.builder()
                .betCountList(betCountList)
                .profitList(profitList)
                .betUserCountList(betUserCountList)
                .build();
    }


    /**
     * 每日报表->按平台统计:投注总额|输赢总额
     *
     * @param startTime
     * @param endTime
     * @return
     */
    public List<ReportStatisticsPo> getDailyReportStatisticsPlatCoin(Integer startTime, Integer endTime, List<Integer> uidList, List<Integer> testUidList) {
        List<ReportStatisticsPo> list = new ArrayList<>();

        try {
            list = getDailyReportStatisticsPlatCoin(startTime, endTime, uidList, null, null, testUidList);
        } catch (Exception e) {
            String error = "每日报表->按平台统计:投注总额|输赢总额:";
            log.error(error, e);
        }
        return list;
    }

    /**
     * 交易记录->全平台投注总额->统计:投注总额|输赢总额
     *
     * @param startTime
     * @param endTime
     * @return
     */
    public List<ReportStatisticsPo> getPlatBetTotalStatistics(Integer startTime, Integer endTime, String id, Integer gameListId, String username, List<Integer> testUidList) {
        List<ReportStatisticsPo> list = new ArrayList<>();
        var pool = getReportThreadPool();
        List<Future<ReportStatisticsPo>> futureList = new ArrayList<>();
        try {
            var gameList = gameList();
            if (Optional.ofNullable(gameList).isEmpty() || gameList.isEmpty()) {
                return list;
            }
            if (null != gameListId) {
                gameList = gameList.stream().filter(o -> o.getId().equals(gameListId)).collect(Collectors.toList());
            }

//            futureList.add(pool.submit(() -> getDailyReportStatisticsPlatCoin(startTime, endTime, null, id, username, testUidList)));


            for (Future<ReportStatisticsPo> future : futureList) {
                if (null != future.get()) {
                    list.add(future.get());
                }
            }
        } catch (
                Exception e) {
            String error = "交易记录->全平台投注总额->统计:投注总额|输赢总额";
            log.error(error + ":" + e);
        }
        return list;
    }

    /**
     * 每日报表->按平台统计:投注笔数
     *
     * @param startTime
     * @param endTime
     * @return
     */
    public long dailyReportStatisticsBetCount(Integer startTime, Integer endTime, List<Integer> uidList, List<Integer> testUidList) {
        long betCount = 0;
        List<Future<Long>> futureList = new ArrayList<>();
        var pool = getReportThreadPool();
        try {
            futureList.add(pool.submit(() -> dailyReportStatisticsBetCountSql(startTime, endTime, uidList, testUidList)));
        } catch (Exception e) {
            String error = "每日报表->按平台统计:投注笔数";
            log.error(error + ":" + e);
        }

        for (Future<Long> future : futureList) {
            try {
                betCount += future.get();
            } catch (Exception e) {
                String error = "每日报表->按平台统计:投注笔数";
                log.error(error + ":" + e);
            }
        }
        return betCount;
    }

    /**
     * 综合走势图-游戏盈亏与投注注数:投注人数
     *
     * @return 投注注数
     */
    public long dailyReportStatisticsBetUserCount(Integer startTime, Integer endTime, List<Integer> uidList, List<Integer> testUidList) {
        try {
            return dailyReportStatisticsBetUserCountSql(startTime, endTime, uidList, testUidList);
        } catch (Exception e) {
            String error = "每日报表->按平台统计:投注人数";
            log.error(error + ":" + e);
        }
        return 0;

    }



    private List<Platform.GameListInfo> gameList() {
        return gameCache.getGameListResCache();
    }


    private List<ReportStatisticsPo> getDailyReportStatisticsPlatCoin(Integer startTime, Integer endTime, List<Integer> uidList, String id, String username, List<Integer> testUidList) {
        ReportStatisticsPo reqPo = new ReportStatisticsPo();
        reqPo.setStartTime(startTime);
        reqPo.setEndTime(endTime);
        reqPo.setUidList(uidList);

        reqPo.setUsername(username);
        reqPo.setId(id);
        /** 排除测试账号 **/
        reqPo.setTestUidList(testUidList);
        var resPo = reportStatisticsMapper.getDailyReportStatisticsPlatCoin(reqPo);
        return resPo;
    }

    private long dailyReportStatisticsBetCountSql(Integer startTime, Integer endTime, List<Integer> uidList, List<Integer> testUidList) {
        ReportStatisticsPo reqPo = new ReportStatisticsPo();
        reqPo.setStartTime(startTime);
        reqPo.setEndTime(endTime);
        reqPo.setUidList(uidList);

        /** 排除测试账号 **/
        reqPo.setTestUidList(testUidList);
        return reportServiceBase.getBetCount(reqPo);
    }

    private Integer dailyReportStatisticsBetUserCountSql(Integer startTime, Integer endTime, List<Integer> uidList, List<Integer> testUidList) {
        ReportStatisticsPo reqPo = new ReportStatisticsPo();
        reqPo.setStartTime(startTime);
        reqPo.setEndTime(endTime);
        reqPo.setUidList(uidList);

        /** 排除测试账号 **/
        reqPo.setTestUidList(testUidList);
        return reportServiceBase.getBetUserCount(reqPo);
    }




    /**
     * 根据gameListId GET投注表名称
     *
     * @param gameListId gameList主键ID
     * @return 投注表名称
     */
    private String switchTableName(Integer gameListId) {
        return configCache.getBetSlipsTableNameByGameListId(gameListId);
    }
}
