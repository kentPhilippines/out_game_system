package com.lt.win.backend.service.impl;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lt.win.backend.base.ReportServiceBase;
import com.lt.win.backend.io.bo.ReportCenter;
import com.lt.win.backend.io.bo.user.CapitalStatisticsListReqBody;
import com.lt.win.backend.io.bo.user.CapitalStatisticsListResBody;
import com.lt.win.backend.redis.GameCache;
import com.lt.win.backend.service.IComprehensiveChartService;
import com.lt.win.dao.generator.po.CoinDepositRecord;
import com.lt.win.dao.generator.po.CoinWithdrawalRecord;
import com.lt.win.dao.generator.service.CoinDepositRecordService;
import com.lt.win.dao.generator.service.CoinWithdrawalRecordService;
import com.lt.win.service.cache.redis.AdminCache;
import com.lt.win.service.cache.redis.UserCache;
import com.lt.win.service.common.Constant;
import com.lt.win.service.exception.BusinessException;
import com.lt.win.service.thread.ThreadHeaderLocalData;
import com.lt.win.utils.components.response.CodeInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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
@Slf4j(topic = "报表中心-每日报表")
public class DailyReportExStatisticsServiceImpl {
    private static final String COUNT = "count";
    private static final String SELECT_COUNT = "count(id) as count";
    @Resource
    private CoinDepositRecordService coinDepositRecordServiceImpl;
    @Resource
    private CoinWithdrawalRecordService coinWithdrawalRecordServiceImpl;
    @Resource
    private IComprehensiveChartService comprehensiveChartServiceImpl;
    @Resource
    private PlatformLeaderBoardServiceImpl platformLeaderBoardServiceImpl;

    @Resource
    ReportServiceBase reportServiceBase;
    @Resource
    private UserCache userCache;
    @Resource
    private AdminCache adminCache;
    @Resource
    private GameCache gameCache;


    private static final AtomicInteger THREAD_ID = new AtomicInteger();

    public ExecutorService getPool() {
        return Executors.newCachedThreadPool(r -> new Thread(r, "每日报表_THREAD_" + THREAD_ID.incrementAndGet()));
    }

    /**
     * 每日报表->投注/充值/提现(笔数/人数)统计
     *
     * @param reqBody reqBody
     * @return DailyReportStatisticsCountResBody
     */
    public ReportCenter.DailyReportStatisticsCountResBody dailyReportStatisticsCount(ReportCenter.DailyReportStatisticsReqBody reqBody) {
        if (null == reqBody) {
            throw new BusinessException(CodeInfo.PARAMETERS_INVALID);
        }
        var startTime = reqBody.getStartTime();
        var endTime = reqBody.getEndTime();

        List<Integer> searchUidList = null;
        // 按username查询
        var username = reqBody.getUsername();
        if (StringUtils.isNotBlank(username)) {
            // 类型:1-代理 2-会员
            if (null == reqBody.getType() || !List.of(1, 2).contains(reqBody.getType())) {
                throw new BusinessException(CodeInfo.PARAMETERS_INVALID);
            }
            var user = userCache.getUserInfo(username);
            if (null != user && Constant.USER_ROLE_CS == user.getRole()) {
                /** 测试账号不能查询报表数据 **/
                throw new BusinessException(CodeInfo.REPORT_TEST_USER_NOT_SEARCH_DATA);
            }

            searchUidList = searchUserName2Uid(username, reqBody.getType());
            if (null == searchUidList || searchUidList.isEmpty()) {
                return setDailyReportStatisticsCountResBodyDefaultValue();
            }
        }

        long depositCount = 0;
        long depositUserCount = 0;
        long withdrawalCount = 0;
        long withdrawalUserCount = 0;
        long betCount = 0;
        long betUserCount = 0;
        try {
            List<Integer> uidList = searchUidList;

            /** 排除测试账号 **/
            List<Integer> testUidList = userCache.getTestUidList();

            var depositCountFuture = getPool().submit(() -> getDepositStatisticsCount(startTime, endTime, null, uidList, testUidList));
            var depositUserCountFuture = getPool().submit(() -> getDepositStatisticsUIdCount(startTime, endTime, null, uidList, testUidList));
            var withdrawalCountFuture = getPool().submit(() -> getWithdrawalStatisticsCount(startTime, endTime, uidList, testUidList));
            var withdrawalUserCountFuture = getPool().submit(() -> getWithdrawalStatisticsUIdCount(startTime, endTime, uidList, testUidList));

            var betCountFuture = getPool().submit(() -> platformLeaderBoardServiceImpl.dailyReportStatisticsBetCount(startTime, endTime, uidList, testUidList));
            var betUserCountFuture = getPool().submit(() -> platformLeaderBoardServiceImpl.dailyReportStatisticsBetUserCount(startTime, endTime, uidList, testUidList));
            // 充值
            depositCount = getCount(depositCountFuture.get());
            if (Optional.ofNullable(depositUserCountFuture.get()).isPresent()) {
                depositUserCount = depositUserCountFuture.get().size();
            }
            // 提款
            withdrawalCount = getCount(withdrawalCountFuture.get());
            if (Optional.ofNullable(withdrawalUserCountFuture.get()).isPresent()) {
                withdrawalUserCount = withdrawalUserCountFuture.get().size();
            }

            // 投注
            betCount = betCountFuture.get();
            betUserCount = betUserCountFuture.get();


        } catch (Exception e) {
            log.error("异常 ==> {}", e.getMessage(), e);
        }

        return ReportCenter.DailyReportStatisticsCountResBody.builder()
                .betCount(betCount)
                .betUserCount(betUserCount)
                .depositCount(depositCount)
                .depositUserCount(depositUserCount)
                .withdrawalCount(withdrawalCount)
                .withdrawalUserCount(withdrawalUserCount)
                .build();
    }

    private ReportCenter.DailyReportStatisticsCountResBody setDailyReportStatisticsCountResBodyDefaultValue() {
        return ReportCenter.DailyReportStatisticsCountResBody.builder()
                .betCount(0)
                .betUserCount(0)
                .depositCount(0)
                .depositUserCount(0)
                .withdrawalCount(0)
                .withdrawalUserCount(0)
                .build();
    }

    /**
     * 每日报表->按平台统计:投注总额|输赢总额
     *
     * @param reqBody reqBody
     * @return DailyReportStatisticsPlatCoinResBody
     */
    public List<ReportCenter.DailyReportStatisticsPlatCoinResBody>  dailyReportStatisticsPlatCoin(ReportCenter.DailyReportStatisticsReqBody reqBody) {
        if (null == reqBody) {
            throw new BusinessException(CodeInfo.PARAMETERS_INVALID);
        }
        var startTime = reqBody.getStartTime();
        var endTime = reqBody.getEndTime();

        var buildList = gameCache.getGameGroupCache().stream().map(o -> ReportCenter.DailyReportStatisticsPlatCoinResBody.builder()
                .groupId(o.getId())
                .groupName(o.getNameAbbr())
                .profitCoin(BigDecimal.ZERO)
                .betCoin(BigDecimal.ZERO)
                .build()).collect(Collectors.toList());

        List<Integer> searchUidList = null;
        // 按username查询
        var username = reqBody.getUsername();
        if (StringUtils.isNotBlank(username)) {
            // 类型:1-代理 2-会员
            if (null == reqBody.getType() || !List.of(1, 2).contains(reqBody.getType())) {
                throw new BusinessException(CodeInfo.PARAMETERS_INVALID);
            }

            var user = userCache.getUserInfo(username);
            if (null != user && Constant.USER_ROLE_CS == user.getRole()) {
                /** 测试账号不能查询报表数据 **/
                throw new BusinessException(CodeInfo.REPORT_TEST_USER_NOT_SEARCH_DATA);
            }

            searchUidList = searchUserName2Uid(username, reqBody.getType());
            if (null == searchUidList || searchUidList.isEmpty()) {
                return buildList;
            }
        }
        /** 排除测试账号 **/
        List<Integer> testUidList = userCache.getTestUidList();
        var groupIdMap = reportServiceBase.getDailyReportStatisticsPlatCoin(startTime, endTime, searchUidList, testUidList);
        if (Optional.ofNullable(groupIdMap).isPresent() && groupIdMap.size() > 0 ) {

            log.info("查询groupIdMap===>{}", JSONUtil.toJsonStr(groupIdMap));
            buildList.forEach(o -> {
                var data = groupIdMap.get(o.getGroupId());
                if (Optional.ofNullable(data).isPresent()) {
                    o.setBetCoin(data.getBetCoin());
                    o.setProfitCoin(data.getProfitCoin());
                }
            });
        }
        log.info("查询buildList===>{}", JSONUtil.toJsonStr(buildList));

        return buildList;
    }


    /**
     * 存款笔数
     *
     * @param startTime
     * @param endTime
     * @return
     */
    private Map<String, Object> getDepositStatisticsCount(Integer startTime, Integer endTime, List<Integer> depStatusList, List<Integer> uidList, List<Integer> testUidList) {
        var sql = buildDepositWrapper(startTime, endTime, depStatusList, uidList, testUidList);
        sql.select(SELECT_COUNT);
        return coinDepositRecordServiceImpl.getMap(sql);
    }

    /**
     * 存款人数
     *
     * @param startTime
     * @param endTime
     * @return
     */
    private List<CoinDepositRecord> getDepositStatisticsUIdCount(Integer startTime, Integer endTime, List<Integer> depStatusList, List<Integer> uidList, List<Integer> testUidList) {
        var sql = buildDepositWrapper(startTime, endTime, depStatusList, uidList, testUidList);
        sql.select(Constant.ID);
        sql.groupBy(Constant.UID);
        return coinDepositRecordServiceImpl.list(sql);
    }

    /**
     * 充值人数=>SQL查询条件一
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return LambdaQueryWrapper
     */
    private QueryWrapper buildDepositWrapper(Integer startTime, Integer endTime, List<Integer> depStatusList, List<Integer> uidList, List<Integer> testUidList) {
        var query = Wrappers.query();
        query.in(null != depStatusList && !depStatusList.isEmpty(), Constant.DEP_STATUS, depStatusList)
                .in(null != uidList && !uidList.isEmpty(), Constant.UID, uidList)
                .in(Constant.STATUS, 1, 2, 9)
                .ge(null != startTime, Constant.UPDATED_AT, startTime)
                .le(null != endTime, Constant.UPDATED_AT, endTime);

        /** 排除测试账号 **/
        query.notIn(Optional.ofNullable(testUidList).isPresent() && !testUidList.isEmpty(), Constant.UID, testUidList);
        return query;
    }

    /**
     * 提款笔数
     *
     * @param startTime
     * @param endTime
     * @param uidList
     * @return
     */
    private Map<String, Object> getWithdrawalStatisticsCount(Integer startTime, Integer endTime, List<Integer> uidList, List<Integer> testUidList) {
        var sql = buildWithdrawalWrapper(startTime, endTime, uidList, testUidList);
        sql.select(SELECT_COUNT);
        return coinWithdrawalRecordServiceImpl.getMap(sql);
    }

    /**
     * 提款人数
     *
     * @param startTime
     * @param endTime
     * @param uidList
     * @return
     */
    private List<CoinWithdrawalRecord> getWithdrawalStatisticsUIdCount(Integer startTime, Integer endTime, List<Integer> uidList, List<Integer> testUidList) {
        var sql = buildWithdrawalWrapper(startTime, endTime, uidList, testUidList);
        sql.select(Constant.ID);
        sql.groupBy(Constant.UID);
        return coinWithdrawalRecordServiceImpl.list(sql);
    }

    /**
     * 提款人数=>SQL查询条件
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return LambdaQueryWrapper
     */
    private QueryWrapper buildWithdrawalWrapper(Integer startTime, Integer endTime, List<Integer> uidList, List<Integer> testUidList) {
        var query = Wrappers.query();
        query.in(Constant.STATUS, 1, 9)
                .in(null != uidList && !uidList.isEmpty(), Constant.UID, uidList)
                .ge(null != startTime, Constant.UPDATED_AT, startTime)
                .le(null != endTime, Constant.UPDATED_AT, endTime);

        /** 排除测试账号 **/
        query.notIn(Optional.ofNullable(testUidList).isPresent() && !testUidList.isEmpty(), Constant.UID, testUidList);
        return query;
    }

    private long getCount(Map<String, Object> map) {
        return null == map ? 0 : (long) map.get(COUNT);
    }

    /**
     * 每日报表->综合统计
     *
     * @param reqBody reqBody
     * @return reqBody
     */
    public CapitalStatisticsListResBody dailyReportStatisticsSynthesis(ReportCenter.DailyReportStatisticsReqBody reqBody) {
        if (null == reqBody) {
            throw new BusinessException(CodeInfo.PARAMETERS_INVALID);
        }
        var req = new CapitalStatisticsListReqBody();
        List<Integer> searchUidList = null;
        // 按username查询
        var username = reqBody.getUsername();
        if (StringUtils.isNotBlank(username)) {
            // 类型:1-代理 2-会员
            if (null == reqBody.getType() || !List.of(1, 2).contains(reqBody.getType())) {
                throw new BusinessException(CodeInfo.PARAMETERS_INVALID);
            }
            var user = userCache.getUserInfo(username);
            if (null != user && Constant.USER_ROLE_CS == user.getRole()) {
                /** 测试账号不能查询报表数据 **/
                throw new BusinessException(CodeInfo.REPORT_TEST_USER_NOT_SEARCH_DATA);
            }

            searchUidList = searchUserName2Uid(username, reqBody.getType());
            if (null == searchUidList || searchUidList.isEmpty()) {
                return returnDefaultValue();
            }
            req.setUidList(searchUidList);
        }


        req.setStartTime(reqBody.getStartTime());
        req.setEndTime(reqBody.getEndTime());
        var po = comprehensiveChartServiceImpl.userZH(req);
        return po;
    }

    /**
     * 每日报表->综合统计
     * 91
     *
     * @return 默认值
     */
    private CapitalStatisticsListResBody returnDefaultValue() {
        return CapitalStatisticsListResBody.builder()
                .build();
    }

    /**
     * 根据搜索类型2UID
     *
     * @param searchUserName 搜索username
     * @param type           类型:1-代理 2-非代理
     * @return UID
     */
    public List<Integer> searchUserName2Uid(String searchUserName, Integer type) {
        log.info("========> type={},username={}", type, searchUserName);
        var searchUid = getCacheUidByUserName(searchUserName);
        if (null == searchUid) {
            return new ArrayList<>();
        }
        var currentLoginAdmin = ThreadHeaderLocalData.HEADER_INFO_THREAD_LOCAL.get();
        var agentId = adminCache.getAdminInfoById(currentLoginAdmin.id).getAgentId();
        // 代理账号登录:查询代理下六级的某一个UID
        if (0 != agentId) {
            var uid = getOneOfSupUid6ListByAgentId(agentId, searchUid);
            return null != uid ? List.of(uid) : null;
        }

        // 非代理账号登录
        // 查询代理下六级所有UID
        if (1 == type) {
            return userCache.subordinate(searchUid).getTeam();
        }
        // 查询全局的某一个UID
        return List.of(searchUid);
    }

    /**
     * 查询代理下六级的某一个UID
     *
     * @param agentId   代理ID
     * @param searchUid 代理下的某一个UID
     * @return 代理下的某一个UID
     */
    public Integer getOneOfSupUid6ListByAgentId(Integer agentId, Integer searchUid) {
        var uidList = userCache.subordinate(agentId).getTeam();
        if (Optional.ofNullable(uidList).isEmpty() || uidList.isEmpty()) {
            return null;
        }
        return uidList.stream().filter(uid -> uid.equals(searchUid)).findFirst().orElse(null);
    }

    /**
     * 根据userName获取UID
     *
     * @param userName userName
     * @return UID
     */
    public Integer getCacheUidByUserName(String userName) {
        Integer uid = null;
        if (StringUtils.isNotBlank(userName)) {
            var searchUserInfo = userCache.getUserInfo(userName);
            if (null != searchUserInfo) {
                uid = searchUserInfo.getId();
            }
        }
        return uid;
    }
}
