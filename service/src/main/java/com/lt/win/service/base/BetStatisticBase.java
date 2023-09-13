package com.lt.win.service.base;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.lt.win.service.cache.redis.UserCache;
import com.lt.win.service.exception.BusinessException;
import com.lt.win.service.io.dto.Betslips;
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

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.System.currentTimeMillis;

/**
 * @Auther: wells
 * @Date: 2022/10/28 17:46
 * @Description:
 */
@Slf4j
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class BetStatisticBase {
//    private final ElasticsearchTemplate<Betslips, String> esBetslipsTemplate;
    private final UserCache userCache;
    private final ESSqlSearchBase esSqlSearchBase;

    /**
     * @return java.math.BigDecimal
     * @Description 获取有效投注总额
     * @Param [startTime, endTime]
     **/
    public BigDecimal getValidBetCoin(Integer startTime, Integer endTime) {
        return getValidBetCoin(startTime, endTime, null, null);
    }

    /**
     * @return java.math.BigDecimal
     * @Description 获取某用户有效投注总额
     * @Param [startTime, endTime]
     **/
    public BigDecimal getValidBetCoinByUid(Integer startTime, Integer endTime, Integer uid) {
        return getValidBetCoin(startTime, endTime, null, uid);
    }

    public BigDecimal getValidBetCoinByUidList(Integer startTime, Integer endTime, List<Integer> uidList) {
        return getCoin(startTime, endTime, null, uidList, "validStake");
    }

    /**
     * @return java.math.BigDecimal
     * @Description 获取某游戏有效投注总额
     * @Param [startTime, endTime]
     **/
    public BigDecimal getValidBetCoinByGameId(Integer startTime, Integer endTime, Integer gameId) {
        return getValidBetCoin(startTime, endTime, gameId, null);
    }

    /**
     * @return java.math.BigDecimal
     * @Description 获取某用户某游戏有效投注总额
     * @Param [startTime, endTime]
     **/
    public BigDecimal getValidBetCoin(Integer startTime, Integer endTime, Integer gameId, Integer uid) {
        List<Integer> uidList = null;
        if (null != uid) {
            uidList = List.of(uid);
        }
        return getCoin(startTime, endTime, gameId, uidList, "validStake");
    }

    /**
     * @return java.math.BigDecimal
     * @Description 获取游戏盈亏总额
     * @Param [startTime, endTime]
     **/
    public BigDecimal getProfit(Integer startTime, Integer endTime) {
        return getProfit(startTime, endTime, null, null);
    }

    /**
     * @return java.math.BigDecimal
     * @Description 获取某用户游戏盈亏总额
     * @Param [startTime, endTime, uid]
     **/
    public BigDecimal getProfitByUid(Integer startTime, Integer endTime, Integer uid) {
        return getProfit(startTime, endTime, null, uid);
    }

    public BigDecimal getProfitByUidList(Integer startTime, Integer endTime, List<Integer> uidList) {
        return getCoin(startTime, endTime, null, uidList, "xbProfit");
    }

    /**
     * @return java.math.BigDecimal
     * @Description 获取某游戏盈亏总额
     * @Param [startTime, endTime, gameId]
     **/
    public BigDecimal getProfitByGameId(Integer startTime, Integer endTime, Integer gameId) {
        return getProfit(startTime, endTime, gameId, null);
    }

    /**
     * @return java.math.BigDecimal
     * @Description 获取某用户某游戏盈亏总额
     * @Param [startTime, endTime, gameId, uid]
     **/
    public BigDecimal getProfit(Integer startTime, Integer endTime, Integer gameId, Integer uid) {
        List<Integer> uidList = null;
        if (null != uid) {
            uidList = List.of(uid);
        }
        return getCoin(startTime, endTime, gameId, uidList, "xbProfit");
    }


    /**
     * @return java.math.BigDecimal
     * @Description 获取有效投注或盈亏金额
     * @Param [startTime, endTime, gameId, uid, field]
     **/
    public BigDecimal getCoin(Integer startTime, Integer endTime, Integer gameId, List<Integer> uidList, String field) {
        try {
            BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
            List<Integer> testUidList = userCache.getTestUidList();
            if (CollectionUtil.isNotEmpty(testUidList)) {
                BoolQueryBuilder queryBuilder2 = QueryBuilders.boolQuery();
                for (Integer xbUid : testUidList) {
                    queryBuilder2.should(QueryBuilders.termQuery("xbUid", xbUid));
                }
                queryBuilder.mustNot(queryBuilder2);
            }

            if (null != gameId) {
                queryBuilder.must(QueryBuilders.termQuery("gameId", gameId));
            }
            if (CollectionUtil.isNotEmpty(uidList)) {
                BoolQueryBuilder queryBuilder2 = QueryBuilders.boolQuery();
                for (Integer xbUid : uidList) {
                    queryBuilder2.should(QueryBuilders.termQuery("xbUid", xbUid));
                }
                queryBuilder.must(queryBuilder2);
            }

            if (null != startTime && startTime > 0 && null != endTime && endTime > 0 && endTime > startTime) {
                queryBuilder.must(QueryBuilders.rangeQuery("createdAt").from(startTime).to(endTime));
            }
      //      double validStakeSum = esBetslipsTemplate.aggs(field, AggsType.sum, queryBuilder, Betslips.class);
         //   return new BigDecimal(validStakeSum).setScale(2, RoundingMode.DOWN);
      return null;
        } catch (Exception e) {
            log.error("获取有效投注总额异常", e);
            throw new BusinessException(CodeInfo.ES_SYNCHRONOUS_ERROR);
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
            if (CollectionUtil.isNotEmpty(uidSet)) {
                BoolQueryBuilder queryBuilder1 = QueryBuilders.boolQuery();
                for (Integer uid : uidSet) {
                    queryBuilder1.should(QueryBuilders.termQuery("xbUid", uid));
                }
                queryBuilder.must(queryBuilder1);
            }
            List<Integer> testUidList = userCache.getTestUidList();
            if (CollectionUtil.isNotEmpty(testUidList)) {
                BoolQueryBuilder queryBuilder2 = QueryBuilders.boolQuery();
                for (Integer uid : testUidList) {
                    queryBuilder2.should(QueryBuilders.termQuery("xbUid", uid));
                }
                queryBuilder.mustNot(queryBuilder2);
            }
            if (null != startTime && startTime > 0 && null != endTime && endTime > 0 && endTime >= startTime) {
                queryBuilder.must(QueryBuilders.rangeQuery("createdAt").from(startTime).to(endTime));
            }

         //   Map<Object, Object> map = esBetslipsTemplate.aggs("validStake", AggsType.sum, queryBuilder, Betslips.class, "xbUid");

            long e = System.currentTimeMillis();
            log.info(" 获取用户集合时间段打码量入参：{},{},{},耗时：{}", startTime, endTime, uidSet, e - s);
        //    map.forEach((key, value) -> reMap.put(Integer.valueOf(key.toString()), new BigDecimal(value.toString()).setScale(2, RoundingMode.DOWN)));
        } catch (Exception e) {
            log.error("获取用户集合时间段打码量异常", e);
            throw new BusinessException(CodeInfo.ES_SYNCHRONOUS_ERROR);
        }
        return reMap;
    }

    /**
     * @return java.lang.Integer
     * @Description 查询投注数
     * @Param [startTime, endTime]
     **/
    public Integer getBetCount(Integer startTime, Integer endTime, List<Integer> uidList) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("select count(id) as count from betslips ");
        stringBuilder.append(" where createdAt>=").append(startTime).append(" and createdAt<=").append(endTime);
        var testUidList = userCache.getTestUidList();
        if (CollectionUtils.isNotEmpty(testUidList)) {
            stringBuilder.append(" and xbUid not in(").append(StringUtils.join(testUidList, ",")).append(")");
        }
        if (CollectionUtils.isNotEmpty(uidList)) {
            stringBuilder.append(" and xbUid  in(").append(StringUtils.join(uidList, ",")).append(")");
        }
        String countStr = esSqlSearchBase.searchValue(stringBuilder.toString());
        return Strings.isEmpty(countStr) ? 0 : Integer.parseInt(countStr);
    }

    /**
     * @return java.lang.Integer
     * @Description 查询投注用户数
     * @Param [startTime, endTime]
     **/
    public Integer getBetUserCount(Integer startTime, Integer endTime, List<Integer> uidList) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("select count(distinct xbUid) as count from betslips ");
        stringBuilder.append(" where createdAt>=").append(startTime).append(" and createdAt<=").append(endTime);
        var testUidList = userCache.getTestUidList();
        if (CollectionUtils.isNotEmpty(testUidList)) {
            stringBuilder.append(" and xbUid not in(").append(StringUtils.join(testUidList, ",")).append(")");
        }
        if (CollectionUtils.isNotEmpty(uidList)) {
            stringBuilder.append(" and xbUid  in(").append(StringUtils.join(uidList, ",")).append(")");
        }
        String countStr = esSqlSearchBase.searchValue(stringBuilder.toString());
        return Strings.isEmpty(countStr) ? 0 : Double.valueOf(countStr).intValue();
    }
}
