package com.lt.win.backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lt.win.backend.base.ReportCommon;
import com.lt.win.backend.base.ReportServiceBase;
import com.lt.win.backend.io.bo.ComprehensiveChart.*;
import com.lt.win.backend.io.bo.StartEndTime;
import com.lt.win.backend.io.bo.user.CapitalStatisticsListReqBody;
import com.lt.win.backend.io.bo.user.CapitalStatisticsListResBody;
import com.lt.win.backend.service.IComprehensiveChartService;
import com.lt.win.backend.service.IPlatformLeaderBoardService;
import com.lt.win.service.base.BetStatisticBase;
import com.lt.win.service.base.CoinLogStatisticBase;
import com.lt.win.service.io.dto.CoinLog;
import com.lt.win.dao.generator.po.User;
import com.lt.win.dao.generator.po.UserLoginLog;
import com.lt.win.dao.generator.service.UserLoginLogService;
import com.lt.win.dao.generator.service.UserService;
import com.lt.win.service.cache.redis.UserCache;
import com.lt.win.service.common.Constant;
import com.lt.win.service.exception.BusinessException;
import com.lt.win.service.server.CoinLogService;
import com.lt.win.utils.DateUtils;
import com.lt.win.utils.components.response.CodeInfo;
import lombok.extern.slf4j.Slf4j;
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

import static com.lt.win.service.common.Constant.STATUS;
import static com.lt.win.service.common.Constant.UPDATED_AT;

/**
 * <p>
 * 报表中心-综合走势图
 * </p>
 *
 * @author andy
 * @since 2020/6/16
 */
@Slf4j
@Service
public class ComprehensiveChartServiceImpl implements IComprehensiveChartService {
    @Resource
    private CoinLogService coinLogServiceImpl;
    @Resource
    private UserService userServiceImpl;
    @Resource
    private UserLoginLogService userLoginLogServiceImpl;
    @Resource
    private ReportServiceBase reportServiceBase;
    @Resource
    private IPlatformLeaderBoardService platformLeaderBoardServiceImpl;
    @Resource
    private ReportCommon reportCommon;
    @Resource
    private UserCache userCache;
    @Resource
    private BetStatisticBase betStatisticBase;

    private static final AtomicInteger REPORT_THREAD_POOL_ID = new AtomicInteger();

    private ThreadPoolExecutor getThreadPoolExecutor() {
        return new ThreadPoolExecutor(
                16,
                32,
                2L,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(),
                x -> new Thread(x, "报表中心->综合走势图_THREAD_POOL_" + REPORT_THREAD_POOL_ID.getAndIncrement()));
    }

    /**
     * 综合走势图->新增会员
     *
     * @return
     */
    @Override
    public TotalUserCountResBody totalRegisterUserCount() {
        return TotalUserCountResBody.builder().count(getUserCount(null, null).size()).build();
    }

    /**
     * 综合走势图->充值|提现|盈亏
     *
     * @return
     */
    @Override
    public TotalPlatProfitResBody totalPlatProfit() {
        BigDecimal coinDeposit = BigDecimal.ZERO;
        BigDecimal coinWithdrawal = BigDecimal.ZERO;
        var pool = getThreadPoolExecutor();
        try {
            Future<BigDecimal> coinDepositFuture = pool.submit(() -> reportServiceBase.depositSum());
            Future<BigDecimal> coinWithdrawalFuture = pool.submit(() -> reportServiceBase.withdrawalSum());
            coinDeposit = coinDepositFuture.get();
            coinWithdrawal = coinWithdrawalFuture.get();
        } catch (Exception e) {
            String error = "综合走势图->统计充值|提现|盈亏失败";
            log.error(error + ":" + e);
        } finally {
            pool.shutdown();
        }
        return TotalPlatProfitResBody.builder()
                .coinDeposit(coinDeposit)
                .coinWithdrawal(coinWithdrawal)
                .profit(coinDeposit.subtract(coinWithdrawal))
                .build();
    }

    /**
     * 综合走势图-用户综合
     *
     * @param reqBody
     * @return
     */
    @Override
    public CapitalStatisticsListResBody userZH(CapitalStatisticsListReqBody reqBody) {
        if (null == reqBody) {
            throw new BusinessException(CodeInfo.PARAMETERS_INVALID);
        }
        // 充值金额
        BigDecimal coinCZ = BigDecimal.ZERO;
        // 提现金额
        BigDecimal coinTX = BigDecimal.ZERO;
        // 游戏盈亏
        BigDecimal coinYXYK = BigDecimal.ZERO;
        // 奖励支出
        BigDecimal coinJLZC = BigDecimal.ZERO;
        // 佣金支出
        BigDecimal coinYJZC = BigDecimal.ZERO;
        // 系统调账
        BigDecimal coinXTTZ = BigDecimal.ZERO;
        // 平台盈亏
        BigDecimal coinPTYK = BigDecimal.ZERO;
        // 有效投注总额
        BigDecimal coinBet = BigDecimal.ZERO;
        var pool = getThreadPoolExecutor();
        try {
            var uidList = reqBody.getUidList();
            Integer startTime = reqBody.getStartTime();
            Integer endTime = reqBody.getEndTime();

            /** 排除测试账号 **/
            var testUidList = userCache.getTestUidList();
            // 游戏盈亏
            coinYXYK = betStatisticBase.getProfit(startTime, endTime);
            // 有效投注总额
            coinBet = betStatisticBase.getValidBetCoin(startTime, endTime);
            Future<BigDecimal> coinDepositFuture = pool.submit(() -> reportServiceBase.depositSum(startTime, endTime));
            Future<BigDecimal> coinWithdrawalFuture = pool.submit(() -> reportServiceBase.withdrawalSum(startTime, endTime));
            Future<BigDecimal> coinJLZCFuture = pool.submit(() -> reportServiceBase.sumCoinOfCoinLog(startTime, endTime, uidList, 7, testUidList));
            Future<BigDecimal> coinYJZCFuture = pool.submit(() -> reportServiceBase.commissionSum(startTime, endTime));
            Future<BigDecimal> coinXTTZFuture = pool.submit(() -> reportServiceBase.sysTransferSum(startTime, endTime));
            log.info("====>{}", coinDepositFuture.get());
            coinCZ = coinDepositFuture.get();
            coinTX = coinWithdrawalFuture.get();
            coinPTYK = coinCZ.subtract(coinTX);
            coinXTTZ = coinXTTZFuture.get();
            coinJLZC = coinJLZCFuture.get();
            coinYJZC = coinYJZCFuture.get();
        } catch (Exception e) {
            String error = "综合走势图->用户综合统计失败";
            log.error(error + ":" + e);
        } finally {
            pool.shutdown();
        }
        CapitalStatisticsListResBody capitalStatisticsListResBody = CapitalStatisticsListResBody.builder()
                .coinCZ(coinCZ)
                .coinTX(coinTX)
                .coinPTYK(coinPTYK)
                .coinYXYK(coinYXYK)
                .coinJLZC(coinJLZC)
                .coinYJZC(coinYJZC)
                .coinXTTZ(coinXTTZ)
                .coinBet(coinBet)
                .build();
        return capitalStatisticsListResBody;
    }

    /**
     * 综合走势图-用户注册(图)
     *
     * @param reqBody
     * @return
     */
    @Override
    public List<ListChart> registerUserCountChart(StartEndTime reqBody) {
        if (null == reqBody) {
            throw new BusinessException(CodeInfo.PARAMETERS_INVALID);
        }
        int startTime = reqBody.getStartTime();
        int endTime = reqBody.getEndTime();
        List<ListChart> rangeDayList = reportCommon.rangeDayListWithCount(startTime, endTime);
        List<User> list = getUserCount(startTime, endTime);
        if (Optional.ofNullable(list).isEmpty() || list.isEmpty()) {
            return rangeDayList;
        }
        Map<String, Integer> dataMap = new HashMap<>();
        for (User user : list) {
            getListChartCountMap(dataMap, user.getCreatedAt());
        }
        reportCommon.fullCountList(rangeDayList, dataMap);
        return rangeDayList;
    }

    /**
     * 综合走势图-登录用户数(图)
     *
     * @param reqBody
     * @return
     */
    @Override
    public List<ListChart> loginUserCountChart(StartEndTime reqBody) {
        if (null == reqBody) {
            throw new BusinessException(CodeInfo.PARAMETERS_INVALID);
        }
        int startTime = reqBody.getStartTime();
        int endTime = reqBody.getEndTime();
        List<ListChart> rangeDayList = reportCommon.rangeDayListWithCount(startTime, endTime);

        var wrapper = whereLoginList(startTime, endTime);
        /** 排除测试账号 **/
        var testUidList = userCache.getTestUidList();
        wrapper.notIn(Optional.ofNullable(testUidList).isPresent() && !testUidList.isEmpty(), UserLoginLog::getUid, testUidList);
        List<UserLoginLog> list = userLoginLogServiceImpl.list(wrapper);
        if (Optional.ofNullable(list).isEmpty() || list.isEmpty()) {
            return rangeDayList;
        }
        Map<String, List<UserLoginLog>> mapList = new HashMap<>();
        for (UserLoginLog s : list) {
            String name = DateUtils.yyyyMMdd(s.getCreatedAt());
            if (mapList.containsKey(name)) {
                List<UserLoginLog> userLoginLogList = mapList.get(name);
                userLoginLogList.add(s);
            } else {
                List<UserLoginLog> userLoginLogList = new ArrayList<>();
                userLoginLogList.add(s);
                mapList.put(name, userLoginLogList);
            }
        }
        Map<String, Integer> map = new HashMap<>();
        mapList.entrySet().stream().forEach(x -> map.put(x.getKey(), (int) x.getValue().stream().map(UserLoginLog::getUid).distinct().count()));
        reportCommon.fullCountList(rangeDayList, map);
        return rangeDayList;
    }

    /**
     * 综合走势图-充值与提现(图)
     *
     * @param reqBody
     * @return
     */
    @Override
    public DepositAndWithdrawalChart depositAndWithdrawalChart(StartEndTime reqBody) {
        if (null == reqBody) {
            throw new BusinessException(CodeInfo.PARAMETERS_INVALID);
        }
        DepositAndWithdrawalChart result = new DepositAndWithdrawalChart();
        var pool = getThreadPoolExecutor();
        try {
            Integer startTime = reqBody.getStartTime();
            Integer endTime = reqBody.getEndTime();
            List<ListCoinChart> depositList = reportCommon.daysRangeListWithCoin(startTime, endTime);
            List<ListCoinChart> withdrawalList = reportCommon.daysRangeListWithCoin(startTime, endTime);

            /** 排除测试账号 **/
            var testUidList = userCache.getTestUidList();
            Future<List<ListCoinChart>> coinDepositFuture = pool.submit(() -> listCoinDeposit(startTime, endTime, null, testUidList));
            Future<List<ListCoinChart>> coinWithdrawalFuture = pool.submit(() -> listCoinWithdrawal(startTime, endTime, null, testUidList));
            processCoinDeposit(depositList, coinDepositFuture.get());
            processCoinWithdrawal(withdrawalList, coinWithdrawalFuture.get());
            

            result.setDeposit(depositList);
            result.setWithdrawal(withdrawalList);
        } catch (Exception e) {
            String error = "综合走势图->用户综合统计失败";
            log.error(error + ":" + e);
        } finally {
            pool.shutdown();
        }
        return result;
    }

    private void processCoinDeposit(List<ListCoinChart> depositList, List<ListCoinChart> tmpList) {
        if (Optional.ofNullable(tmpList).isEmpty() || tmpList.isEmpty()) {
            return;
        }
        Map<String, BigDecimal> map = tmpList.stream().collect(Collectors.toMap(ListCoinChart::getName, ListCoinChart::getCoin));
        reportCommon.fullCoinList(depositList, map);
    }

    private void processCoinWithdrawal(List<ListCoinChart> withdrawalList, List<ListCoinChart> tmpList) {
        if (Optional.ofNullable(tmpList).isEmpty() || tmpList.isEmpty()) {
            return;
        }
        Map<String, BigDecimal> map = tmpList.stream().collect(Collectors.toMap(ListCoinChart::getName, ListCoinChart::getCoin));
        reportCommon.fullCoinList(withdrawalList, map);
    }

    private void getListChartCountMap(Map<String, Integer> dataMap, Integer createdAt) {
        if (null == createdAt || 0 == createdAt) {
            return;
        }
        String name = DateUtils.yyyyMMdd(createdAt);
        if (dataMap.containsKey(name)) {
            int count = dataMap.get(name);
            ++count;
            dataMap.put(name, count);
        } else {
            dataMap.put(name, 1);
        }
    }

    /**
     * 用户综合:充值金额|提现金额|返水支出|佣金支出|奖励支出|系统调账
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @param uidList   UID
     * @param category  类型:1-存款 2-提款 3-上分 4-下分 5-返水 6-佣金 7-活动(奖励) 8-系统调账
     * @return 金额
     */
    private BigDecimal sumCoinOfCoinLog(Integer startTime, Integer endTime, List<Integer> uidList, Integer category, List<Integer> testUidList) {
        BigDecimal coin = BigDecimal.ZERO;
        QueryWrapper wrapper = getCoinLogQueryWrapper(startTime, endTime, uidList, category);
        if (Optional.ofNullable(category).isPresent() && category == 8) {
            wrapper.select("SUM(CASE WHEN out_in=0 THEN -1 * coin  ELSE coin END) AS coin");
        } else {
            wrapper.select("IFNULL(SUM(coin),0) AS coin");
        }
        /** 排除测试账号 **/
        wrapper.notIn(Optional.ofNullable(testUidList).isPresent() && !testUidList.isEmpty(), Constant.UID, testUidList);
        CoinLog one = coinLogServiceImpl.getOne(wrapper);
        if (null != one) {
            coin = one.getCoin();
        }
        return coin;
    }

    private List<ListCoinChart> listCoinDeposit(Integer startTime, Integer endTime, Integer uid, List<Integer> testUidList) {
        return sumCoinChartOfCoinLog(getQueryWrapperChart(startTime, endTime, uid, 1, testUidList));
    }

    private List<ListCoinChart> listCoinWithdrawal(Integer startTime, Integer endTime, Integer uid, List<Integer> testUidList) {
        return sumCoinChartOfCoinLog(getQueryWrapperChart(startTime, endTime, uid, 2, testUidList));
    }

    private List<ListCoinChart> sumCoinChartOfCoinLog(QueryWrapper queryWrapper) {
        List<Map<String, Object>> mapList = coinLogServiceImpl.listMaps(queryWrapper);
        List<ListCoinChart> list = new ArrayList<>();
        mapList.stream().forEach(map -> list.add(ListCoinChart.builder().coin((BigDecimal) map.get("coin")).name((String) map.get("name")).build()));
        return list;
    }

    private List<User> getUserCount(Integer startTime, Integer endTime) {
        return userServiceImpl.list(getUserCountQueryWrapper(startTime, endTime));
    }

    private QueryWrapper getQueryWrapperChart(Integer startTime, Integer endTime, Integer uid, Integer category, List<Integer> testUidList) {
        QueryWrapper wrapper = Wrappers.query();
        wrapper.eq(STATUS, 1);
        wrapper.select("ifnull(sum(coin),0) as coin", "FROM_UNIXTIME(updated_at,'%Y-%m-%d') as name");
        wrapper.ge(null != startTime, UPDATED_AT, startTime);
        wrapper.le(null != endTime, UPDATED_AT, endTime);
        wrapper.ge(null != uid, Constant.UID, uid);
        wrapper.eq(null != category, Constant.CATEGORY, category);

        /** 排除测试账号 **/
        wrapper.notIn(Optional.ofNullable(testUidList).isPresent() && !testUidList.isEmpty(), Constant.UID, testUidList);
        wrapper.groupBy("FROM_UNIXTIME(updated_at,'%Y-%m-%d')");
        return wrapper;
    }

    private LambdaQueryWrapper<User> getUserCountQueryWrapper(Integer startTime, Integer endTime) {
        LambdaQueryWrapper<User> wrapper = Wrappers.lambdaQuery();
        // 角色:0-会员 1-代理 2-总代理 3-股东 4-测试 10-系统账号
        wrapper.in(User::getRole, 0, 1);
        wrapper.ge(null != startTime, User::getCreatedAt, startTime);
        wrapper.le(null != endTime, User::getCreatedAt, endTime);
        return wrapper;
    }

    private LambdaQueryWrapper<UserLoginLog> whereLoginList(Integer startTime, Integer endTime) {
        LambdaQueryWrapper<UserLoginLog> where = new LambdaQueryWrapper();
        where.ge(null != startTime, UserLoginLog::getCreatedAt, startTime);
        where.le(null != endTime, UserLoginLog::getCreatedAt, endTime);
        return where;
    }

    private QueryWrapper getCoinLogQueryWrapper(Integer startTime, Integer endTime, List<Integer> uidList, Integer category) {
        QueryWrapper wrapper = Wrappers.query();
        wrapper.eq(STATUS, 1);
        wrapper.eq(null != category, Constant.CATEGORY, category);
        wrapper.ge(null != startTime, UPDATED_AT, startTime);
        wrapper.le(null != endTime, UPDATED_AT, endTime);
        wrapper.in(null != uidList && !uidList.isEmpty(), Constant.UID, uidList);
        return wrapper;
    }

}
