package com.lt.win.backend.service.impl;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lt.win.backend.io.bo.ReportCenter;
import com.lt.win.backend.io.bo.StartEndTime;
import com.lt.win.dao.generator.po.CoinDepositRecord;
import com.lt.win.dao.generator.po.CoinWithdrawalRecord;
import com.lt.win.dao.generator.po.User;
import com.lt.win.dao.generator.po.UserLoginLog;
import com.lt.win.dao.generator.service.CoinDepositRecordService;
import com.lt.win.dao.generator.service.CoinWithdrawalRecordService;
import com.lt.win.dao.generator.service.UserLoginLogService;
import com.lt.win.dao.generator.service.UserService;
import com.lt.win.service.cache.redis.UserCache;
import com.lt.win.service.common.Constant;
import com.lt.win.service.exception.BusinessException;
import com.lt.win.service.io.enums.CurrencyEm;
import com.lt.win.utils.DateNewUtils;
import com.lt.win.utils.components.pagination.ReqPage;
import com.lt.win.utils.components.pagination.ResPage;
import com.lt.win.utils.components.response.CodeInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * <p>
 * 报表中心-每日报表-统计接口实现类
 * </p>
 *
 * @author andy
 * @since 2020/6/5
 */
@Service
@Slf4j(topic = "报表中心-每日转化报表")
public class DailyReportRealTimeStatisticsServiceImpl {
    private static final String COIN = "realAmount";
    private static final String COUNT = "count";
    private static final String SELECT_COUNT = "count(id) as count";

    @Resource
    private UserService userServiceImpl;

    @Resource
    private CoinDepositRecordService coinDepositRecordServiceImpl;
    @Resource
    private CoinWithdrawalRecordService coinWithdrawalRecordServiceImpl;
    @Resource
    private UserLoginLogService userLoginLogServiceImpl;
    @Resource
    private UserCache userCache;

    private static final AtomicInteger THREAD_ID = new AtomicInteger();

    public ExecutorService getPool() {
        return Executors.newCachedThreadPool(r -> new Thread(r, "每日转化报表_THREAD_" + THREAD_ID.incrementAndGet()));
    }

    /**
     * 每日转化报表-列表 实时查询
     *
     * @param reqBody reqBody
     * @return 每日转化报表-列表
     */
    public ReportCenter.DailyConversionReportStatisticsResBody dailyReportRealTimeStatistics(StartEndTime reqBody) {
        if (null == reqBody) {
            throw new BusinessException(CodeInfo.PARAMETERS_INVALID);
        }
        var startTime = reqBody.getStartTime();
        var endTime = reqBody.getEndTime();

        int registeredNum = 0;
        BigDecimal depositCoin = null;
        BigDecimal withdrawalCoin = null;
        var pool = getPool();
        try {
            /** 排除测试账号 **/
            List<Integer> testUidList = userCache.getTestUidList();

            var userUidListFuture = pool.submit(() -> getUserStatisticsUIdList(startTime, endTime));
            var depositCoinFuture = pool.submit(() -> getDepositStatisticsCoin(startTime, endTime, null, testUidList));
            var withdrawalCoinFuture = pool.submit(() -> getWithdrawalStatisticsCoin(startTime, endTime, testUidList));

            if (Optional.ofNullable(depositCoinFuture.get()).isPresent()) {
                depositCoin = (BigDecimal) depositCoinFuture.get().get(COIN);
            }
            if (Optional.ofNullable(withdrawalCoinFuture.get()).isPresent()) {
                withdrawalCoin = (BigDecimal) withdrawalCoinFuture.get().get(COIN);
            }
            if (Optional.ofNullable(userUidListFuture.get()).isPresent()) {
                registeredNum = userUidListFuture.get().size();
            }
        } catch (Exception e) {
            log.error("统计异常:{}", e.getMessage(), e);
        } finally {
            pool.shutdown();
        }
        depositCoin = Optional.ofNullable(depositCoin).orElse(BigDecimal.ZERO);
        withdrawalCoin = Optional.ofNullable(withdrawalCoin).orElse(BigDecimal.ZERO);
        return ReportCenter.DailyConversionReportStatisticsResBody.builder()
                .registeredNum(registeredNum)
                .depositCoin(depositCoin)
                .withdrawalCoin(withdrawalCoin)
                // 平台盈亏=存款-提款
                .profit(depositCoin.subtract(withdrawalCoin))
                .build();
    }

    /**
     * 按指定时间范围生成报表数据
     *
     * @param reqBody 时间范围列表
     */
    public ResPage<ReportCenter.DailyConversionReportListResBody> dailyReportRealTimeList(ReqPage<StartEndTime> reqBody) {
        if (null == reqBody) {
            throw new BusinessException(CodeInfo.PARAMETERS_INVALID);
        }
        var startTime = reqBody.getData().getStartTime();
        var endTime = reqBody.getData().getEndTime();
        log.info("reqBody==》{}", JSONUtil.toJsonStr(reqBody));
        ZonedDateTime starZonedDateTime = DateNewUtils.utc8Zoned(startTime);
        log.info("starZonedDateTime==》{}", starZonedDateTime.toString());
        Long daysDiff = DateNewUtils.daysDiff(starZonedDateTime, DateNewUtils.utc8Zoned(endTime));
        log.info("几天==》{}", daysDiff);
        var dateList = getTimeList(daysDiff.intValue(), starZonedDateTime);
        log.info("时间格式==>{}", JSONUtil.toJsonStr(dateList));
        List<ReportCenter.DailyConversionReportListResBody> list = new ArrayList<>();

        /** 排除测试账号 **/
        List<Integer> testUidList = userCache.getTestUidList();
        for (StartEndTime o : dateList) {
            try {
                list.add(generateDailyReport(o.getStartTime(), o.getEndTime(), testUidList));
            } catch (Exception e) {
                log.error("{} -> {} 异常:{}", o.getStartTime(), o.getEndTime(), e.getMessage(), e);
            }
        }

        // 内存分页
        Page<ReportCenter.DailyConversionReportListResBody> page = new Page<>(reqBody.getCurrent(), reqBody.getSize(), list.size());
        List<ReportCenter.DailyConversionReportListResBody> collect = list.stream()
                .skip(page.getSize() * (page.getCurrent() - 1))
                .limit(page.getSize()).collect(Collectors.toList());

        // 更新历史转化率
        collect.stream().forEach(o -> {
            var firstDepositUidList = o.getFirstDepositUidList();
            if (Optional.ofNullable(firstDepositUidList).isPresent() && !firstDepositUidList.isEmpty()) {
                Map<Integer, Long> dateNameMap = getOpenedAccountNumMapByDateName(firstDepositUidList);
                if (null != dateNameMap) {
                    list.stream().forEach(x -> {
                        if (dateNameMap.containsKey(x.getId())) {
                            var openedAccountNum = dateNameMap.get(x.getId());
                            x.setOpenedAccountNum(openedAccountNum.intValue());
                            x.setConversionRates(getConversionRates(openedAccountNum, Long.valueOf(x.getRegisteredNum())));
                        }
                    });
                }
            }
        });

        page.setRecords(collect);
        return ResPage.get(page);
    }

    /**
     * 生成每日报表记录
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @throws ExecutionException
     * @throws InterruptedException
     */
    private ReportCenter.DailyConversionReportListResBody generateDailyReport(Integer startTime, Integer endTime, List<Integer> testUidList) throws ExecutionException, InterruptedException {
        ReportCenter.DailyConversionReportListResBody po = new ReportCenter.DailyConversionReportListResBody();
        var pool = getPool();
        try {
            int registeredNum = 0;
            BigDecimal firstDepositCoin = BigDecimal.ZERO;
            Long firstDepositCount = 0L;
            BigDecimal secondDepositCoin = BigDecimal.ZERO;
            Long secondDepositCount = 0L;
            BigDecimal thirdDepositCoin = BigDecimal.ZERO;
            Long thirdDepositCount = 0L;
            BigDecimal depositCoin = BigDecimal.ZERO;
            Long depositCount = 0L;
            int depositNum = 0;
            BigDecimal withdrawalCoin = BigDecimal.ZERO;
            Long withdrawalCount = 0L;
            int withdrawalNum = 0;
            BigDecimal conversionRates = BigDecimal.ZERO;
            Long openedAccountNum = 0L;

            // 注册人数
            var userUidListFuture = pool.submit(() -> getUserStatisticsUIdList(startTime, endTime));
            // 首充uidList
            var firstDepositUidSetFuture = pool.submit(() -> getDepositStatisticsUIdList(startTime, endTime, List.of(Constant.DEP_STATUS_FIRST), testUidList));
            // 充值记录:1-首充 2-二充 3-三充
            var firstDepositCoinFuture = pool.submit(() -> getDepositStatisticsCoin(startTime, endTime, List.of(Constant.DEP_STATUS_FIRST), testUidList));
            var firstDepositCountFuture = pool.submit(() -> getDepositStatisticsCount(startTime, endTime, List.of(Constant.DEP_STATUS_FIRST), testUidList));

            var secondDepositCoinFuture = pool.submit(() -> getDepositStatisticsCoin(startTime, endTime, List.of(Constant.DEP_STATUS_SECOND), testUidList));
            var secondDepositCountFuture = pool.submit(() -> getDepositStatisticsCount(startTime, endTime, List.of(Constant.DEP_STATUS_SECOND), testUidList));


            var depositCoinFuture = pool.submit(() -> getDepositStatisticsCoin(startTime, endTime, null, testUidList));
            var depositCountFuture = pool.submit(() -> getDepositStatisticsCount(startTime, endTime, null, testUidList));
            var depositNumFuture = pool.submit(() -> getDepositStatisticsUIdList(startTime, endTime, null, testUidList));
            var withdrawalCoinFuture = pool.submit(() -> getWithdrawalStatisticsCoin(startTime, endTime, testUidList));
            var withdrawalCountFuture = pool.submit(() -> getWithdrawalStatisticsCount(startTime, endTime, testUidList));
            var withdrawalNumFuture = pool.submit(() -> getWithdrawalStatisticsUIdList(startTime, endTime, testUidList));
            registeredNum = Optional.ofNullable(userUidListFuture.get()).map(List::size).orElse(0);
            // 充值总额
            depositCoin = getCoin(depositCoinFuture.get());
            depositCount = getCount(depositCountFuture.get());
            if (Optional.ofNullable(depositNumFuture.get()).isPresent()) {
                depositNum = depositNumFuture.get().size();
            }

            // 首存
            firstDepositCoin = getCoin(firstDepositCoinFuture.get());
            firstDepositCount = getCount(firstDepositCountFuture.get());

            // 二存
            secondDepositCoin = getCoin(secondDepositCoinFuture.get());
            secondDepositCount = getCount(secondDepositCountFuture.get());

            // 提款记录
            withdrawalCoin = getCoin(withdrawalCoinFuture.get());
            withdrawalCount = getCount(withdrawalCountFuture.get());
            if (Optional.ofNullable(withdrawalNumFuture.get()).isPresent()) {
                withdrawalNum = withdrawalNumFuture.get().size();
            }

            // 首充uidList
            List<Integer> firstDepositUidList = null;
            if (Optional.ofNullable(firstDepositUidSetFuture.get()).isPresent()) {
                firstDepositUidList = firstDepositUidSetFuture.get().stream().map(CoinDepositRecord::getUid).collect(Collectors.toList());
            }

            // 主键ID(格式yyyyMMdd)
            po.setId(Integer.parseInt(convertTimeToString(startTime)));
            // 注册人数
            po.setRegisteredNum(registeredNum);
            // 开户人数
            po.setOpenedAccountNum(openedAccountNum.intValue());
            // 转化率(开户人数/注册人数)*100
            po.setConversionRates(conversionRates);
            // 首存金额
            po.setFirstDepositCoin(firstDepositCoin);
            // 首存笔数
            po.setFirstDepositCount(firstDepositCount.intValue());
            // 二存金额
            po.setSecondDepositCoin(secondDepositCoin);
            // 二存笔数
            po.setSecondDepositCount(secondDepositCount.intValue());

            // 存款金额
            po.setDepositCoin(depositCoin);
            // 存款笔数
            po.setDepositCount(depositCount.intValue());
            // 存款人数
            po.setDepositNum(depositNum);
            // 提款金额
            po.setWithdrawalCoin(withdrawalCoin);
            // 提款笔数
            po.setWithdrawalCount(withdrawalCount.intValue());
            // 提款人数
            po.setWithdrawalNum(withdrawalNum);
            // 盈亏金额(存款-提款)
            po.setProfit(po.getDepositCoin().subtract(po.getWithdrawalCoin()));
            // 首次充值UID列表
            po.setFirstDepositUidList(firstDepositUidList);
        } catch (Exception e) {
            log.error("异常 ==> {}", e.getMessage(), e);
            throw e;
        } finally {
            pool.shutdown();
        }
        return po;
    }

    /**
     * 按注册时间计算->yyyyMMdd 计算开户人数
     *
     * @param firstDepositUidList 首充UID列表
     * @return Map<Integer, Long> key->格式化日期 value->开户人数
     */
    private Map<Integer, Long> getOpenedAccountNumMapByDateName(List<Integer> firstDepositUidList) {
        Map<Integer, Long> map = null;
        List<User> userList = getUserListByFirstDepositUidList(firstDepositUidList);
        if (Optional.ofNullable(userList).isPresent() && !userList.isEmpty()) {
            map = userList.stream().map(User::getCreatedAt).map(o -> Integer.parseInt(convertTimeToString(o)))
                    .collect(Collectors.groupingBy(o -> o, Collectors.counting()));
        }
        return map;
    }

    /**
     * 获取首次充值UID列表
     *
     * @param firstDepositUidList 首次充值UID列表
     * @return 首次充值UID列表
     */
    private List<User> getUserListByFirstDepositUidList(List<Integer> firstDepositUidList) {
        List<User> userList = null;
        if (Optional.ofNullable(firstDepositUidList).isPresent() && !firstDepositUidList.isEmpty()) {
            userList = userServiceImpl.lambdaQuery().in(User::getId, firstDepositUidList).list();
        }
        return userList;
    }

    /**
     * 获取转化率:转化率=开户人数/注册人数*100
     *
     * @param openedAccountNum 开户人数
     * @param registeredNum    注册人数
     * @return 转化率
     */
    private BigDecimal getConversionRates(Long openedAccountNum, Long registeredNum) {
        BigDecimal result = BigDecimal.ZERO;
        if (registeredNum > 0) {
            BigDecimal divide = new BigDecimal(openedAccountNum).divide(new BigDecimal(registeredNum), 4, RoundingMode.DOWN);
            result = divide.multiply(new BigDecimal(100));
        }
        return result;
    }

    /**
     * 充值人数=>SQL查询条件一
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return LambdaQueryWrapper
     */
    private QueryWrapper buildDepositWrapper(Integer startTime, Integer endTime, List<Integer> depStatusList, List<Integer> testUidList) {
        var query = Wrappers.query();
        query.in(null != depStatusList && !depStatusList.isEmpty(), Constant.DEP_STATUS, depStatusList)
                .in(Constant.STATUS, 1)
                .ge(null != startTime, Constant.UPDATED_AT, startTime)
                .le(null != endTime, Constant.UPDATED_AT, endTime);

        /** 排除测试账号 **/
        query.notIn(Optional.ofNullable(testUidList).isPresent() && !testUidList.isEmpty(), Constant.UID, testUidList);
        return query;
    }

    /**
     * 提款人数=>SQL查询条件
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return LambdaQueryWrapper
     */
    private QueryWrapper buildWithdrawalWrapper(Integer startTime, Integer endTime, List<Integer> testUidList) {
        var query = Wrappers.query();
        query.in(Constant.STATUS, 1, 9)
                .ge(null != startTime, Constant.UPDATED_AT, startTime)
                .le(null != endTime, Constant.UPDATED_AT, endTime);
        /** 排除测试账号 **/
        query.notIn(Optional.ofNullable(testUidList).isPresent() && !testUidList.isEmpty(), Constant.UID, testUidList);
        return query;
    }

    /**
     * 活跃人数=>SQL查询条件
     *
     * @param userUidList UIDList
     * @param startTime   开始时间
     * @param endTime     结束时间
     * @return LambdaQueryWrapper
     */
    private QueryWrapper buildUserLoginLogWrapper(Integer startTime, Integer endTime, List<Integer> userUidList) {
        var query = Wrappers.query();
        query.in(null != userUidList && !userUidList.isEmpty(), Constant.UID, userUidList)
                .ge(null != startTime, Constant.CREATED_AT, startTime)
                .le(null != endTime, Constant.CREATED_AT, endTime);

        return query;
    }

    /**
     * 活跃人数
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return 活跃人数列表
     */
    private List<UserLoginLog> getUserLoginLogStatisticsUIdCount(Integer startTime, Integer endTime, List<Integer> userUidList) {
        var sql = buildUserLoginLogWrapper(startTime, endTime, userUidList);
        sql.select(Constant.ID);
        sql.groupBy(Constant.UID);
        return userLoginLogServiceImpl.list(sql);
    }

    /**
     * 注册UID列表
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return UID列表
     */
    private List<User> getUserStatisticsUIdList(Integer startTime, Integer endTime) {
        var sql = buildUserWrapper(startTime, endTime);
        sql.select(Constant.ID);
        sql.groupBy(Constant.ID);
        return userServiceImpl.list(sql);
    }

    /**
     * 注册人数=>SQL查询条件
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return LambdaQueryWrapper
     */
    private QueryWrapper buildUserWrapper(Integer startTime, Integer endTime) {
        var query = Wrappers.query();
        query.ge(null != startTime, Constant.CREATED_AT, startTime)
                .le(null != endTime, Constant.CREATED_AT, endTime);
        // 角色:0-会员 1-代理 2-总代理 3-股东 4-测试 10-系统账号
        query.in(Constant.USER_ROLE, List.of(0, 1));
        return query;
    }

    /**
     * 存款金额
     *
     * @param startTime     开始时间
     * @param endTime       结束时间
     * @param depStatusList 充值标识:1-首充 2-二充 3-三充 9-其他
     * @return
     */
    private Map<String, Object> getDepositStatisticsCoin(Integer startTime, Integer endTime, List<Integer> depStatusList, List<Integer> testUidList) {
        var sql = buildDepositWrapper(startTime, endTime, depStatusList, testUidList);
        sql.select("sum(real_amount) as realAmount");
        return coinDepositRecordServiceImpl.getMap(sql);
    }

    /**
     * 存款笔数
     *
     * @param startTime     开始时间
     * @param endTime       结束时间
     * @param depStatusList 充值标识:1-首充 2-二充 3-三充 9-其他
     * @return
     */
    private Map<String, Object> getDepositStatisticsCount(Integer startTime, Integer endTime, List<Integer> depStatusList, List<Integer> tesUidList) {
        var sql = buildDepositWrapper(startTime, endTime, depStatusList, tesUidList);
        sql.select(SELECT_COUNT);
        return coinDepositRecordServiceImpl.getMap(sql);
    }

    /**
     * 存款UID列表
     *
     * @param startTime     开始时间
     * @param endTime       结束时间
     * @param depStatusList 充值标识:1-首充 2-二充 3-三充 9-其他
     * @return UID列表
     */
    private List<CoinDepositRecord> getDepositStatisticsUIdList(Integer startTime, Integer endTime, List<Integer> depStatusList, List<Integer> testUidList) {
        var sql = buildDepositWrapper(startTime, endTime, depStatusList, testUidList);
        sql.select(Constant.UID);
        sql.groupBy(Constant.UID);
        return coinDepositRecordServiceImpl.list(sql);
    }


    /**
     * 提款金额
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return 提款金额
     */
    private Map<String, Object> getWithdrawalStatisticsCoin(Integer startTime, Integer endTime, List<Integer> testUidList) {
        var sql = buildWithdrawalWrapper(startTime, endTime, testUidList);
        sql.select(" sum(real_amount) as realAmount");
        return coinWithdrawalRecordServiceImpl.getMap(sql);
    }

    /**
     * 提款笔数
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return 提款笔数
     */
    private Map<String, Object> getWithdrawalStatisticsCount(Integer startTime, Integer endTime, List<Integer> testUidList) {
        var sql = buildWithdrawalWrapper(startTime, endTime, testUidList);
        sql.select(SELECT_COUNT);
        return coinWithdrawalRecordServiceImpl.getMap(sql);
    }

    /**
     * 提款人数
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return 提款人数
     */
    private List<CoinWithdrawalRecord> getWithdrawalStatisticsUIdList(Integer startTime, Integer endTime, List<Integer> testUidList) {
        var sql = buildWithdrawalWrapper(startTime, endTime, testUidList);
        sql.select(Constant.UID);
        sql.groupBy(Constant.UID);
        return coinWithdrawalRecordServiceImpl.list(sql);
    }

    /**
     * 获取时间切片
     *
     * @param days 0-今天整天，1-昨天整天 2-前天整天 ...
     * @return 时间切片
     */
    List<StartEndTime> getTimeList(int days, ZonedDateTime zonedDateTime) {
        List<StartEndTime> list = new ArrayList<>();
        for (int i = 0; i <= days; i++) {
            ZonedDateTime tmp = zonedDateTime.plusDays(i);
            ZonedDateTime start = tmp.with(LocalTime.MIN);
            ZonedDateTime end = tmp.with(LocalTime.MAX);
            int startTime = (int) start.toEpochSecond();
            int endTime = (int) end.toEpochSecond();

            StartEndTime time = new StartEndTime();
            time.setStartTime(startTime);
            time.setEndTime(endTime);
            list.add(time);
        }
        Collections.reverse(list);
        return list;
    }

    /**
     * 将10位长度Integer类型的时间戳转换成String 类型的时间格式，时间格式为：yyyyMMdd
     */
    private static String convertTimeToString(@NotNull Integer time) {
        DateTimeFormatter ftf = DateTimeFormatter.ofPattern(DateNewUtils.Format.yyyyMMdd.getValue());
        return ftf.format(LocalDateTime.ofInstant(Instant.ofEpochSecond(time),  ZoneId.ofOffset("UTC", ZoneOffset.of("+8"))));
    }

    /**
     * getCoin
     *
     * @param map map
     * @return coin
     */
    private BigDecimal getCoin(Map<String, Object> map) {
        if (CollectionUtils.isEmpty(map)) {
            return BigDecimal.ZERO;
        }
        BigDecimal coin = (BigDecimal) map.get(COIN);
        return null == coin ? BigDecimal.ZERO : coin;
    }

    /**
     * getCount
     *
     * @param map map
     * @return count
     */
    private Long getCount(Map<String, Object> map) {
        if (CollectionUtils.isEmpty(map)) {
            return 0L;
        }
        Long count = (Long) map.get(COUNT);
        return null == count ? 0L : count;
    }

}
