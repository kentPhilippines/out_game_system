package com.lt.win.backend.base;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lt.win.backend.io.bo.ReportCenter;
import com.lt.win.backend.io.dto.FinanceManageParams;
import com.lt.win.backend.io.po.PlatProfitAndBetCountChartPo;
import com.lt.win.backend.io.po.ReportStatisticsPo;
import com.lt.win.dao.generator.po.*;
import com.lt.win.dao.generator.service.CoinAdminTransferService;
import com.lt.win.dao.generator.service.CoinCommissionService;
import com.lt.win.dao.generator.service.CoinDepositRecordService;
import com.lt.win.dao.generator.service.CoinWithdrawalRecordService;
import com.lt.win.service.base.CoinLogStatisticBase;
import com.lt.win.service.cache.redis.UserCache;
import com.lt.win.service.exception.BusinessException;
import com.lt.win.service.io.dto.Betslips;
import com.lt.win.service.io.dto.CoinLog;
import com.lt.win.utils.components.response.CodeInfo;
import io.jsonwebtoken.lang.Collections;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.stereotype.Component;
import org.zxp.esclientrhl.enums.AggsType;
import org.zxp.esclientrhl.repository.Down;
//import org.zxp.esclientrhl.repository.ElasticsearchTemplate;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.lt.win.service.io.dto.UserCoinChangeParams.FlowCategoryTypeEnum.*;

/**
 * 报表统计ES
 *
 * @Auther: nobody
 * @Date: 2022/10/13 23:29
 * @Description:
 */

@Component
@Slf4j
public class ReportServiceBase {

//    @Resource
//    ElasticsearchTemplate<CoinLog, String> esCoinLogTemplate;
//
//    @Resource
//    ElasticsearchTemplate<Betslips, String> esBetslipsTemplate;
    @Resource
    private CoinDepositRecordService coinDepositRecordServiceImpl;
    @Resource
    private CoinWithdrawalRecordService coinWithdrawalRecordServiceImpl;
    @Resource
    private UserCache userCache;
    @Resource
    private CoinCommissionService coinCommissionServiceImpl;
    @Resource
    private CoinAdminTransferService coinAdminTransferServiceImpl;
    @Resource
    private CoinLogStatisticBase coinLogStatisticBase;


    public BigDecimal validBetCoin(ReportStatisticsPo po) {
        BigDecimal betCoin = getPlatCoinStatistics(po, "1", "2");
        BigDecimal refusedCoin = getPlatCoinStatistics(po, "3");
        return betCoin.subtract(refusedCoin);
    }

    public BigDecimal getPlatCoinStatistics(ReportStatisticsPo po, String... xbStatus) {
        try {
            BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
            if (CollectionUtil.isNotEmpty(po.getUidList())) {
                BoolQueryBuilder queryBuilder1 = QueryBuilders.boolQuery();
                for (Integer uid : po.getUidList()) {
                    queryBuilder1.should(QueryBuilders.termQuery("xbUid", uid));
                }
                queryBuilder.must(queryBuilder1);
            }
            if (CollectionUtil.isNotEmpty(po.getTestUidList())) {
                BoolQueryBuilder queryBuilder2 = QueryBuilders.boolQuery();
                for (Integer uid : po.getTestUidList()) {
                    queryBuilder2.should(QueryBuilders.termQuery("xbUid", uid));
                }
                queryBuilder.mustNot(queryBuilder2);
            }

            if (null != po.getGameListId()) {
                queryBuilder.must(QueryBuilders.termQuery("gameTypeId", po.getGameListId()));
            }
            if (null != xbStatus && xbStatus.length > 0) {
                queryBuilder.must(QueryBuilders.termsQuery("xbStatus", xbStatus));
            }

            if (null != po.getStartTime() && po.getStartTime() > 0 && null != po.getEndTime() && po.getEndTime() > 0 && po.getEndTime() > po.getStartTime()) {
                queryBuilder.must(QueryBuilders.rangeQuery("createdAt").from(po.getStartTime()).to(po.getEndTime()));
            }
        //    double xbProfit = esBetslipsTemplate.aggs(po.getColumnName(), AggsType.sum, queryBuilder, Betslips.class);

            return new BigDecimal(0).setScale(2, RoundingMode.DOWN);
        } catch (Exception e) {
            log.error("获取盈亏报表异常", e);
            throw new BusinessException(CodeInfo.ES_SYNCHRONOUS_ERROR);
        }
    }


    public BigDecimal sumCoinOfCoinLog(Integer startTime, Integer endTime, List<Integer> uidList, Integer category, List<Integer> testUidList) throws Exception {
        try {
            BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
            if (CollectionUtil.isNotEmpty(uidList)) {
                BoolQueryBuilder queryBuilder1 = QueryBuilders.boolQuery();
                for (Integer uid : uidList) {
                    queryBuilder1.should(QueryBuilders.termQuery("xbUid", uid));
                }
                queryBuilder.must(queryBuilder1);
            }
            if (CollectionUtil.isNotEmpty(testUidList)) {
                BoolQueryBuilder queryBuilder2 = QueryBuilders.boolQuery();
                for (Integer uid : testUidList) {
                    queryBuilder2.should(QueryBuilders.termQuery("xbUid", uid));
                }
                queryBuilder.mustNot(queryBuilder2);
            }
            if (null != category && category == 8) {
                queryBuilder.must(QueryBuilders.termQuery("category", category));
                queryBuilder.must(QueryBuilders.termQuery("outIn", 0));
            } else if (null != category) {
                queryBuilder.must(QueryBuilders.termQuery("category", category));
            }

            queryBuilder.must(QueryBuilders.termQuery("status", 1));
            if (null != startTime && startTime > 0 && null != endTime && endTime > 0 && endTime > startTime) {
                queryBuilder.must(QueryBuilders.rangeQuery("createdAt").from(startTime).to(endTime));
            }
         //   double coin = esCoinLogTemplate.aggs("coin", AggsType.sum, queryBuilder, CoinLog.class);

            if (null != category && category == 8) {
                queryBuilder.must(QueryBuilders.termQuery("outIn", 1));
             //   double coinSystem = esCoinLogTemplate.aggs("coin", AggsType.sum, queryBuilder, CoinLog.class);
             //  coin = (coin * -1) + coinSystem;
            }

            return new BigDecimal(0).setScale(2, RoundingMode.DOWN);
        } catch (Exception e) {
            log.error("获取盈亏报表异常", e);
            throw new BusinessException(CodeInfo.ES_SYNCHRONOUS_ERROR);
        }
    }


    public long getBetCount(ReportStatisticsPo po) {
        try {
            BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
            if (CollectionUtil.isNotEmpty(po.getUidList())) {
                BoolQueryBuilder queryBuilder1 = QueryBuilders.boolQuery();
                for (Integer uid : po.getUidList()) {
                    queryBuilder1.should(QueryBuilders.termQuery("xbUid", uid));
                }
                queryBuilder.must(queryBuilder1);
            }
            if (CollectionUtil.isNotEmpty(po.getTestUidList())) {
                BoolQueryBuilder queryBuilder2 = QueryBuilders.boolQuery();
                for (Integer uid : po.getTestUidList()) {
                    queryBuilder2.should(QueryBuilders.termQuery("xbUid", uid));
                }
                queryBuilder.mustNot(queryBuilder2);
            }
            if (null != po.getStartTime() && po.getStartTime() > 0 && null != po.getEndTime() && po.getEndTime() > 0 && po.getEndTime() > po.getStartTime()) {
                queryBuilder.must(QueryBuilders.rangeQuery("createdAt").from(po.getStartTime()).to(po.getEndTime()));
            }
         //   double count = esBetslipsTemplate.aggs("xbUid", AggsType.count, queryBuilder, Betslips.class);
            return new Double(0).longValue();
        } catch (Exception e) {
            log.error("获取盈亏报表异常", e);
            throw new BusinessException(CodeInfo.ES_SYNCHRONOUS_ERROR);
        }
    }


    public Integer getBetUserCount(ReportStatisticsPo po) {
        try {
            BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
            if (CollectionUtil.isNotEmpty(po.getUidList())) {
                BoolQueryBuilder queryBuilder1 = QueryBuilders.boolQuery();
                for (Integer uid : po.getUidList()) {
                    queryBuilder1.should(QueryBuilders.termQuery("xbUid", uid));
                }
                queryBuilder.must(queryBuilder1);
            }
            if (CollectionUtil.isNotEmpty(po.getTestUidList())) {
                BoolQueryBuilder queryBuilder2 = QueryBuilders.boolQuery();
                for (Integer uid : po.getTestUidList()) {
                    queryBuilder2.should(QueryBuilders.termQuery("xbUid", uid));
                }
                queryBuilder.mustNot(queryBuilder2);
            }
            if (null != po.getStartTime() && po.getStartTime() > 0 && null != po.getEndTime() && po.getEndTime() > 0 && po.getEndTime() > po.getStartTime()) {
                queryBuilder.must(QueryBuilders.rangeQuery("createdAt").from(po.getStartTime()).to(po.getEndTime()));
            }
       //     Map userSum = esBetslipsTemplate.aggs("xbUid", AggsType.count, queryBuilder, Betslips.class, "xbUid");
            return 0;
        } catch (Exception e) {
            log.error("获取盈亏报表异常", e);
            throw new BusinessException(CodeInfo.ES_SYNCHRONOUS_ERROR);
        }
    }


    public Map<Integer, ReportCenter.DailyReportStatisticsPlatCoinResBody> getDailyReportStatisticsPlatCoin(Integer startTime, Integer endTime, List<Integer> uidList, List<Integer> testUidList) {
        try {
            Map<Integer, ReportCenter.DailyReportStatisticsPlatCoinResBody> reMap = new HashMap<>();
            Map<Integer, BigDecimal> stakeMap = new HashMap<>();
            Map<Integer, BigDecimal> xbProfitMap = new HashMap<>();
            BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
            if (CollectionUtil.isNotEmpty(uidList)) {
                BoolQueryBuilder queryBuilder1 = QueryBuilders.boolQuery();
                for (Integer uid : uidList) {
                    queryBuilder1.should(QueryBuilders.termQuery("xbUid", uid));
                }
                queryBuilder.must(queryBuilder1);
            }
            if (CollectionUtil.isNotEmpty(testUidList)) {
                BoolQueryBuilder queryBuilder2 = QueryBuilders.boolQuery();
                for (Integer uid : testUidList) {
                    queryBuilder2.should(QueryBuilders.termQuery("xbUid", uid));
                }
                queryBuilder.mustNot(queryBuilder2);
            }
            if (null != startTime && startTime > 0 && null != endTime && endTime > 0 && endTime > startTime) {
                queryBuilder.must(QueryBuilders.rangeQuery("createdAt").from(startTime).to(endTime));
            }
         //   Map stakeSum = esBetslipsTemplate.aggs("validStake", AggsType.sum, queryBuilder, Betslips.class, "gameGroupId");
         //   stakeSum.forEach((key, value) -> stakeMap.put(Integer.valueOf(key.toString()), new BigDecimal(value.toString()).setScale(2, RoundingMode.DOWN)));


        //    Map xbProfitSum = esBetslipsTemplate.aggs("xbProfit", AggsType.sum, queryBuilder, Betslips.class, "gameGroupId");
     //       xbProfitSum.forEach((key, value) -> xbProfitMap.put(Integer.valueOf(key.toString()), new BigDecimal(value.toString()).setScale(2, RoundingMode.DOWN)));
            for (Integer key : stakeMap.keySet()) {
                reMap.put(key, ReportCenter.DailyReportStatisticsPlatCoinResBody.builder().betCoin(stakeMap.get(key)).build());
            }
            for (Integer key : xbProfitMap.keySet()) {
                if (reMap.containsKey(key)) {
                    ReportCenter.DailyReportStatisticsPlatCoinResBody oj = reMap.get(key);
                    oj.setProfitCoin(xbProfitMap.get(key));
                    reMap.put(key, oj);
                } else {
                    reMap.put(key, ReportCenter.DailyReportStatisticsPlatCoinResBody.builder().betCoin(BigDecimal.ZERO).profitCoin(xbProfitMap.get(key)).build());
                }
            }
            return reMap;
        } catch (Exception e) {
            log.error("获取盈亏报表异常", e);
            throw new BusinessException(CodeInfo.ES_SYNCHRONOUS_ERROR);
        }
    }



    /**
     * @return java.math.BigDecimal
     * @Description 查询充值总金额
     * @Param []
     **/
    public BigDecimal depositSum() {
        return depositSum(null, null);
    }

    /**
     * @return java.math.BigDecimal
     * @Description 某个时间段的充值总金额
     * @Param [startTime, endTime]
     **/
    public BigDecimal depositSum(Integer startTime, Integer endTime) {
        /*  排除测试账号 **/
        var testUidList = userCache.getTestUidList();
        QueryWrapper<CoinDepositRecord> queryWrapper = new QueryWrapper<CoinDepositRecord>()
                .select("ifNull(sum(real_amount), 0) as coin")
                .eq("status", 1)
                .notIn("uid", testUidList)
                .ge(null != startTime, "created_at", startTime)
                .le(null != endTime, "created_at", endTime);
        Map<String, Object> map = coinDepositRecordServiceImpl.getMap(queryWrapper);
        BigDecimal coin = BigDecimal.ZERO;
        if (!Collections.isEmpty(map)) {
            coin = (BigDecimal) map.getOrDefault("coin", BigDecimal.ZERO);
        }
        return coin;
    }

    /**
     * @return java.math.BigDecimal
     * @Description 提款总金额
     * @Param []
     **/
    public BigDecimal withdrawalSum() {
        return withdrawalSum(null, null);
    }

    /**
     * @return java.math.BigDecimal
     * @Description 某段时间的提款总金额
     * @Param [startTime, endTime]
     **/
    public BigDecimal withdrawalSum(Integer startTime, Integer endTime) {
        /*  排除测试账号 **/
        var testUidList = userCache.getTestUidList();
        QueryWrapper<CoinWithdrawalRecord> queryWrapper = new QueryWrapper<CoinWithdrawalRecord>()
                .select("ifNull(sum(withdrawal_amount), 0) as coin")
                .eq("status", 1)
                .notIn("uid", testUidList)
                .ge(null != startTime, "created_at", startTime)
                .le(null != endTime, "created_at", endTime);
        Map<String, Object> map = coinWithdrawalRecordServiceImpl.getMap(queryWrapper);
        BigDecimal coin = BigDecimal.ZERO;
        if (!Collections.isEmpty(map)) {
            coin = (BigDecimal) map.getOrDefault("coin", BigDecimal.ZERO);
        }
        return coin;
    }

    /**
     * @return java.math.BigDecimal
     * @Description 佣金总额
     * @Param [startTime, endTime]
     **/
    public BigDecimal commissionSum(Integer startTime, Integer endTime) {
        return coinLogStatisticBase.getCoin(startTime, endTime, BROKERAGE.getCode());
    }

    /**
     * @return java.math.BigDecimal
     * @Description 佣金总额
     * @Param [startTime, endTime]
     **/
    public BigDecimal sysTransferSum(Integer startTime, Integer endTime) {
        return coinLogStatisticBase.getCoin(startTime, endTime, SYSTEM_RECONCILIATION.getCode());
    }

}
