package com.lt.win.service.base;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.lt.win.service.cache.redis.UserCache;
import com.lt.win.service.exception.BusinessException;
import com.lt.win.service.io.bo.AgentReportBo;
import com.lt.win.service.io.dto.CoinLog;
import com.lt.win.service.io.dto.UserCoinChangeParams;
import com.lt.win.utils.components.response.CodeInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.util.Strings;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.zxp.esclientrhl.enums.AggsType;
//import org.zxp.esclientrhl.repository.ElasticsearchTemplate;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

import static com.lt.win.service.io.dto.UserCoinChangeParams.FlowCategoryTypeEnum.*;
import static java.lang.System.currentTimeMillis;

/**
 * @Auther: wells
 * @Date: 2022/10/28 17:46
 * @Description:
 */
@Slf4j
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CoinLogStatisticBase {
  //  private final ElasticsearchTemplate<CoinLog, String> esCoinLogTemplate;
    private final UserCache userCache;
    private final ESSqlSearchBase esSqlSearchBase;

    /**
     * @return java.math.BigDecimal
     * @Description 计算流水类型金额
     * @Param [startTime, endTime, gameId, uid, field]
     **/
    public BigDecimal getCoin(Integer startTime, Integer endTime, Integer gameId, List<Integer> uidList, Integer category) {
        return getCoin(startTime, endTime, gameId, uidList, category, null);
    }

    public BigDecimal getCoin(Integer startTime, Integer endTime, Integer category) {
        return getCoin(startTime, endTime, null, null, category, null);
    }

    /**
     * @return java.math.BigDecimal
     * @Description 调账金额
     * @Param [startTime, endTime, uidList]
     **/
    public BigDecimal getTransferCoin(Integer startTime, Integer endTime, List<Integer> uidList) {
        BigDecimal inCoin = getCoin(startTime, endTime, null, uidList, SYSTEM_RECONCILIATION.getCode(), 1);
        BigDecimal outCoin = getCoin(startTime, endTime, null, uidList, SYSTEM_RECONCILIATION.getCode(), 0);
        return inCoin.subtract(outCoin);
    }

    /**
     * @return java.math.BigDecimal
     * @Description 获取金额
     * @Param [startTime, endTime, gameId, uid, field]
     **/
    public BigDecimal getCoin(Integer startTime, Integer endTime, Integer gameId, List<Integer> uidList, Integer category, Integer outIn) {
        try {
            BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
            List<Integer> testUidList = userCache.getTestUidList();
            if (CollectionUtil.isNotEmpty(testUidList)) {
                BoolQueryBuilder queryBuilder2 = QueryBuilders.boolQuery();
                for (Integer uid : testUidList) {
                    queryBuilder2.should(QueryBuilders.termQuery("uid", uid));
                }
                queryBuilder.mustNot(queryBuilder2);
            }

            if (null != gameId) {
                queryBuilder.must(QueryBuilders.termQuery("gameId", gameId));
            }
            if (null != outIn) {
                queryBuilder.must(QueryBuilders.termQuery("outIn", outIn));
            }
            if (CollectionUtil.isNotEmpty(uidList)) {
                BoolQueryBuilder queryBuilder2 = QueryBuilders.boolQuery();
                for (Integer uid : uidList) {
                    queryBuilder2.should(QueryBuilders.termQuery("uid", uid));
                }
                queryBuilder.must(queryBuilder2);
            }
            queryBuilder.must(QueryBuilders.termQuery("category", category));
            if (null != startTime && startTime > 0 && null != endTime && endTime > 0 && endTime > startTime) {
                queryBuilder.must(QueryBuilders.rangeQuery("createdAt").from(startTime).to(endTime));
            }
            String field = "coin";
            if (category.equals(WITHDRAWAL.getCode())) {
                field = "realCoin";
            }
       //     double validStakeSum = esCoinLogTemplate.aggs(field, AggsType.sum, queryBuilder, CoinLog.class);
            return new BigDecimal(0).setScale(2, RoundingMode.DOWN);
        } catch (Exception e) {
            log.error("获取有效投注总额异常", e);
            throw new BusinessException(CodeInfo.ES_CURRENT_SUM_BET_ERROR);
        }
    }

    /**
     * @return java.util.Map<java.lang.Integer, java.math.BigDecimal>
     * @Description 获取用户集合的有效投注金额
     * @Param [startTime, endTime]
     **/
    public Map<Integer, BigDecimal> getValidBetCoinMap(Integer startTime, Integer endTime) {
        return getValidBetCoinMap(null, startTime, endTime);
    }

    /**
     * 获取用户集合的有效投注金额
     *
     * @param uidSet
     * @param startTime
     * @param endTime
     * @return
     */
    public Map<Integer, BigDecimal> getValidBetCoinMap(List<Integer> uidSet, Integer startTime, Integer endTime) {
        Map<Integer, BigDecimal> reMap = new HashMap<>();
        try {
            log.info(" 获取用户集合时间段打码量入参：{},{},{}", startTime, endTime, uidSet);
            long s = System.currentTimeMillis();
            BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
            buildQueryUid(uidSet, queryBuilder);
            if (null != startTime && startTime > 0 && null != endTime && endTime > 0 && endTime >= startTime) {
                queryBuilder.must(QueryBuilders.rangeQuery("createdAt").from(startTime).to(endTime));
            }
            queryBuilder.must(QueryBuilders.termQuery("category", BET.getCode()));
          //  Map<Object, Object> map = esCoinLogTemplate.aggs("coin", AggsType.sum, queryBuilder, CoinLog.class, "uid");

            long e = System.currentTimeMillis();
            log.info(" 获取用户集合时间段打码量入参：{},{},{},耗时：{}", startTime, endTime, uidSet, e - s);
         //   map.forEach((key, value) -> reMap.put(Integer.valueOf(key.toString()), new BigDecimal(value.toString()).setScale(2, RoundingMode.DOWN)));
        } catch (Exception e) {
            log.error("获取用户集合时间段打码量异常", e);
            throw new BusinessException(CodeInfo.ES_SYNCHRONOUS_ERROR);
        }
        return reMap;
    }

    /**
     * 获取操作的会员人数
     *
     * @param uidList   uid列表`
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @param type      流水类型
     * @return res
     */
    public Integer getUserCount(List<Integer> uidList, Integer startTime, Integer endTime, UserCoinChangeParams.FlowCategoryTypeEnum type) {
        StringBuilder stringBuilder = new StringBuilder();
        String coinLogIndex = EsTableNameBase.getTableName(CoinLog.class);
        stringBuilder.append("select count(distinct uid) as count from ").append(coinLogIndex);
        stringBuilder.append(" where category=").append(type.getCode());
        if (Optional.ofNullable(startTime).isPresent()) {
            stringBuilder.append(" and createdAt>=").append(startTime);
        }
        if (Optional.ofNullable(endTime).isPresent()) {
            stringBuilder.append(" and createdAt<=").append(endTime);
        }
        if (CollectionUtils.isNotEmpty(uidList)) {
            stringBuilder.append(" and uid in(").append(StringUtils.join(uidList, ",")).append(")");
        }
        var testUidList = userCache.getTestUidList();
        if (CollectionUtils.isNotEmpty(testUidList)) {
            stringBuilder.append(" and uid not in(").append(StringUtils.join(testUidList, ",")).append(")");
        }

        String countStr = esSqlSearchBase.searchValue(stringBuilder.toString());
        return Strings.isEmpty(countStr) ? 0 : Double.valueOf(countStr).intValue();
    }

    /**
     * 分类统计 - CoinLog原始数据
     *
     * @param uids      ids
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return 统计数据
     * @author: David
     */
    public AgentReportBo.CoinLogStatistics statistics(List<Integer> uids, Integer startTime, Integer endTime) {
        var stat = new AgentReportBo.CoinLogStatistics();
        try {
            BoolQueryBuilder queryBuilder = getQuery(uids, startTime, endTime);
//            var map = esCoinLogTemplate.aggs("coin", AggsType.sum, queryBuilder, CoinLog.class, "category");
//            if (Objects.nonNull(map)) {
//                map.forEach((key, value) -> {
//                    var category = ((Long) key).intValue();
//                    BigDecimal sumCoin = BigDecimal.valueOf((Double) value).setScale(2, RoundingMode.DOWN);
//
//                    switch (category) {
//                        case 1:
//                            stat.setCoinDeposit(sumCoin);
//                            break;
//                        case 2:
//                            stat.setCoinWithdrawal(sumCoin);
//                            break;
//                        case 3:
//                            stat.setCoinBet(sumCoin);
//                            break;
//                        case 4:
//                            stat.setCoinPayOut(sumCoin);
//                            break;
//                        case 5:
//                            stat.setCoinRebate(sumCoin);
//                            break;
//                        case 6:
//                            stat.setCoinCommission(sumCoin);
//                            break;
//                        case 7:
//                            stat.setCoinRewards(sumCoin);
//                            break;
//                        case 9:
//                            stat.setCoinRefund(sumCoin);
//                            break;
//                        case 12:
//                            stat.setCoinWithdrawalRefund(sumCoin);
//                            break;
//                        default:
//                            break;
//                    }
//                });
//            }
            // 调账金额 涉及到有正负数 单独处理
            stat.setCoinReconciliation(getTransferCoin(startTime, endTime, uids));

        } catch (Exception e) {
            e.printStackTrace();
            log.error("统计交易记录类型;{}", e.getMessage());
        }
        return stat;
    }

    /**
     * 分组查询
     *
     * @param userIds   ids
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return 查询条件
     * @author: David
     */
    private BoolQueryBuilder getQuery(List<Integer> userIds, Integer startTime, Integer endTime) {
        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
        buildQueryUid(userIds, queryBuilder);

        if (Objects.nonNull(startTime) && Objects.nonNull(endTime)) {
            queryBuilder.must(QueryBuilders.rangeQuery("createdAt").
                    from(startTime)
                    .to(endTime)
                    .includeLower(true)
                    .includeUpper(true)
            );
        } else if (Objects.nonNull(startTime)) {
            queryBuilder.must(QueryBuilders.rangeQuery("createdAt").from(startTime).includeLower(true));
        } else if (Objects.nonNull(endTime)) {
            queryBuilder.must(QueryBuilders.rangeQuery("createdAt").to(endTime).includeUpper(true));
        }
        return queryBuilder;
    }

    /**
     * 生成UID查询条件，排除测试UID
     *
     * @param userIds      用户ID
     * @param queryBuilder 测试账号ID
     */
    private void buildQueryUid(List<Integer> userIds, BoolQueryBuilder queryBuilder) {
        if (CollectionUtil.isNotEmpty(userIds)) {
            BoolQueryBuilder queryBuilder1 = QueryBuilders.boolQuery();
            for (Integer uid : userIds) {
                queryBuilder1.should(QueryBuilders.termQuery("uid", uid));
            }
            queryBuilder.must(queryBuilder1);
        }

        // 排除测试账号
        List<Integer> testUidList = userCache.getTestUidList();
        if (CollectionUtil.isNotEmpty(testUidList)) {
            BoolQueryBuilder queryBuilder2 = QueryBuilders.boolQuery();
            for (Integer uid : testUidList) {
                queryBuilder2.should(QueryBuilders.termQuery("uid", uid));
            }
            queryBuilder.mustNot(queryBuilder2);
        }
    }
}
