package com.lt.win.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.lock.annotation.Lock4j;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.lt.win.service.base.DigitainErrorCode;
import com.lt.win.service.base.UserCoinBase;
import com.lt.win.service.cache.redis.ConfigCache;
import com.lt.win.service.common.DigiSportErrorEnum;
import com.lt.win.service.exception.BusinessException;
import com.lt.win.service.exception.DigiSportException;
import com.lt.win.service.exception.DigitainException;
import com.lt.win.service.io.dto.BaseParams;
import com.lt.win.service.io.dto.Betslips;
import com.lt.win.service.io.dto.CoinLog;
import com.lt.win.service.io.dto.UserCoinChangeParams;
import com.lt.win.service.io.dto.admin.BetStatisticsDto;
import com.lt.win.service.io.game.DigitainBase;
import com.lt.win.service.io.qo.AdminBetQo;
import com.lt.win.service.io.qo.BetQo;
import com.lt.win.service.mapper.BetslipsMapper;
import com.lt.win.utils.JedisUtil;
import com.lt.win.utils.components.pagination.ReqPage;
import com.lt.win.utils.components.pagination.ResPage;
import com.lt.win.utils.components.response.CodeInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.zxp.esclientrhl.repository.PageSortHighLight;
import org.zxp.esclientrhl.repository.Sort;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * <p>
 * 注单表 服务实现类
 * </p>
 *
 * @author David
 * @since 2022-08-11
 */
@Service
@Slf4j
public class BetslipsServiceImpl extends ServiceImpl<BetslipsMapper, Betslips> implements IService<Betslips> {

    /**
     * roundId or transactionId, platId, gameId, uid
     */
    public static final String BET_KEY = "Bet:%d:%s:%s:%d";

    public static final String BET_SPORT_KEY = "Bet:%d:%s:%d";

    private final String BETSLIPS_ES = "betslips";
    @Autowired
    UserCoinBase userCoinBase;
//    @Resource
//    ElasticsearchTemplate<Betslips, String> elasticsearchTemplate;
//
//    @Resource
//    ElasticsearchTemplate<CoinLog, String> coinLogElasticsearchTemplate;
    @Autowired
    JedisUtil jedisUtil;
//    @Resource
//    private RestHighLevelClient restHighLevelClient;
    @Resource
    private ConfigCache configCache;

    public boolean saveBetslipsByRoundId(Betslips betslips) throws Exception {
        return saveBetslips(betslips, 1);
    }

    public boolean saveBetslipsByTransactionId(Betslips betslips) throws Exception {
        return saveBetslips(betslips, 2);
    }

    public List<CoinLog> queryCoinLog(Integer platId, String txId) throws Exception {
//        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
//        queryBuilder.must(QueryBuilders.termQuery("externalTxId.keyword", platId + "-" + txId));
//
//        return coinLogElasticsearchTemplate.search(queryBuilder, CoinLog.class);
        return null;
    }

    /**
     * 投注
     *
     * @param betslips 投注信息
     * @param type     1:roundId  2:transactionId
     * @return 成功失败
     */
    private boolean saveBetslips(Betslips betslips, int type) throws Exception {
//        String searchId = 1 == type ? betslips.getRoundId() : betslips.getTransactionId();
//        if (Objects.nonNull(queryBet(searchId, betslips.getGamePlatId(), betslips.getGameTypeId(), betslips.getXbUid(), type))) {
//            return false;
//        }
//        if (this.baseMapper.insert(betslips) > 0) {
//            jedisUtil.setex(String.format(BET_KEY, betslips.getGamePlatId(), searchId, betslips.getGameTypeId(), betslips.getXbUid()), 30, JSON.toJSONString(betslips));
//            return elasticsearchTemplate.save(betslips);
//        }
        return false;
    }

    /**
     * 体育投注查询
     *
     * @param betslips 投注信息
     * @return 成功失败
     */
    public boolean saveBetslipsByParam(Betslips betslips) throws Exception {
//        Betslips bet = queryBetByOrParam(betslips);
//        if (Objects.nonNull(bet)) {
//            if (bet.getRoundId().equals(betslips.getRoundId())) {
//                throw new DigiSportException(DigiSportErrorEnum.NotAllowed);
//            }
//            if (bet.getTransactionId().equals(betslips.getTransactionId())) {
//                throw new DigiSportException(DigiSportErrorEnum.TransactionAlreadyExists);
//            }
//            return false;
//        }
//        if (this.baseMapper.insert(betslips) > 0) {
//            jedisUtil.setex(String.format(BET_SPORT_KEY, betslips.getGamePlatId(), betslips.getRoundId(), betslips.getXbUid()), 30, JSON.toJSONString(betslips));
//            return elasticsearchTemplate.save(betslips);
//        }
        return false;
    }

    /**
     * 根据RoundId查询注单信息
     *
     * @param roundId    roundId
     * @param gamePlatId gamePlatId
     * @return res
     */
    public Betslips queryBetByRoundId(String roundId, Integer gamePlatId) {
        try {
            return this.queryBet(roundId, gamePlatId, null, null, 1);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 根据RoundId查询注单信息
     *
     * @param roundId    roundId
     * @param gamePlatId gamePlatId
     * @param gameTypeId gameTypeId
     * @return res
     */
    public Betslips queryBetByRoundId(String roundId, Integer gamePlatId, String gameTypeId) {
        try {
            return this.queryBet(roundId, gamePlatId, gameTypeId, null, 1);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 查询注单
     *
     * @param searchId   查询id
     * @param gamePlatId 平台id
     * @param gameTypeId 游戏类型ID
     * @param userId     用户id
     * @param type       1：基于roundId, 2:基于transactionId
     * @return res
     */
    private Betslips queryBet(String searchId, Integer gamePlatId, String gameTypeId, Integer userId, Integer type) throws Exception {
//        Betslips betslips = jedisUtil.get(String.format(BET_KEY, gamePlatId, searchId, gameTypeId, userId), Betslips.class);
//        if (Objects.nonNull(betslips)) {
//            return betslips;
//        }
//        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
//        if (type == 2) {
//            queryBuilder.must(QueryBuilders.termQuery("transactionId.keyword", searchId));
//        } else if (type == 1) {
//            queryBuilder.must(QueryBuilders.termQuery("roundId.keyword", searchId));
//        }
//        if (Optional.ofNullable(gameTypeId).isPresent()) {
//            queryBuilder.must(QueryBuilders.termQuery("gameTypeId.keyword", gameTypeId));
//        }
//        if (Optional.ofNullable(userId).isPresent()) {
//            queryBuilder.must(QueryBuilders.termQuery("xbUid", userId));
//        }
//        queryBuilder.must(QueryBuilders.termQuery("gamePlatId", gamePlatId));
//        List<Betslips> list = elasticsearchTemplate.search(queryBuilder, Betslips.class);
//        if (CollectionUtil.isNotEmpty(list)) {
//            return list.get(0);
//        }
        return null;
    }


    /**
     * 查询注单
     *
     * @param roundId    查询id
     * @param gamePlatId 平台id
     * @param userId     用户id
     * @return res
     */
    public Betslips queryBetByRoundId(String roundId, Integer gamePlatId, Integer userId) throws Exception {
//        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
//        queryBuilder.must(QueryBuilders.termQuery("roundId.keyword", roundId));
//        queryBuilder.must(QueryBuilders.termQuery("gamePlatId", gamePlatId));
//        queryBuilder.must(QueryBuilders.termQuery("xbUid", userId));
//        List<Betslips> list = elasticsearchTemplate.search(queryBuilder, Betslips.class);
//        if (CollectionUtil.isNotEmpty(list)) {
//            return list.get(0);
//        }
        return null;
    }

    /**
     * 查询注单
     *
     * @param txId       查询id
     * @param gamePlatId 平台id
     * @return res
     */
    public Betslips queryBetTxId(String txId, Integer gamePlatId) throws Exception {
//        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
//        queryBuilder.must(QueryBuilders.termQuery("transactionId.keyword", txId));
//        queryBuilder.must(QueryBuilders.termQuery("gamePlatId", gamePlatId));
//        List<Betslips> list = elasticsearchTemplate.search(queryBuilder, Betslips.class);
//        if (CollectionUtil.isNotEmpty(list)) {
//            return list.get(0);
//        }
        return null;
    }

    /**
     * 体育条件 或（or） 查询注单
     *
     * @return
     */
    private Betslips queryBetByOrParam(Betslips bet) throws Exception {
//        Betslips betslips = jedisUtil.get(String.format(BET_SPORT_KEY, bet.getGamePlatId(), bet.getRoundId(), bet.getXbUid()), Betslips.class);
//        if (Objects.nonNull(betslips)) {
//            return betslips;
//        }
//        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
//        BoolQueryBuilder queryBuilder1 = QueryBuilders.boolQuery();
//        queryBuilder1.must(QueryBuilders.termQuery("transactionId.keyword", bet.getTransactionId()));
//        queryBuilder1.must(QueryBuilders.termQuery("gamePlatId", bet.getGamePlatId()));
//        BoolQueryBuilder queryBuilder2 = QueryBuilders.boolQuery();
//        queryBuilder2.must(QueryBuilders.termQuery("roundId.keyword", bet.getRoundId()));
//        queryBuilder2.must(QueryBuilders.termQuery("gamePlatId", bet.getGamePlatId()));
//
//        queryBuilder.should(queryBuilder1);
//        queryBuilder.should(queryBuilder2);
//
//        List<Betslips> list = elasticsearchTemplate.search(queryBuilder, Betslips.class);
//        if (CollectionUtil.isNotEmpty(list)) {
//            return list.get(0);
//        }
        return null;
    }

    public Betslips queryBetByTransactionId(String transactionId, Integer gamePlatId, Integer userId) {
        try {
            return this.queryBet(transactionId, gamePlatId, null, userId, 2);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Betslips queryBetByTransactionId(String transactionId, Integer gamePlatId, String gameTypeId, Integer userId) {
        try {
            return this.queryBet(transactionId, gamePlatId, gameTypeId, userId, 2);
        } catch (Exception e) {
            return null;
        }
    }

    public Betslips queryBetByRoundId(String roundId, Integer gamePlatId, String gameTypeId, Integer userId) {
        try {
            return this.queryBet(roundId, gamePlatId, gameTypeId, userId, 1);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public Betslips queryBetByParam(Integer gamePlatId, String roundId, Long userId) throws Exception {
//        Betslips betslips = jedisUtil.get(String.format(BET_SPORT_KEY, gamePlatId, roundId, userId), Betslips.class);
//        if (Objects.nonNull(betslips)) {
//            return betslips;
//        }
//        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
//        queryBuilder.must(QueryBuilders.termQuery("roundId.keyword", roundId));
//        queryBuilder.must(QueryBuilders.termQuery("gamePlatId", gamePlatId));
//        queryBuilder.must(QueryBuilders.termQuery("xbUid", userId));
//
//        List<Betslips> list = elasticsearchTemplate.search(queryBuilder, Betslips.class);
//        if (CollectionUtil.isNotEmpty(list)) {
//            return list.get(0);
//        }
        return null;
    }

    public Betslips queryBetByParam(Integer gamePlatId, String roundId) throws Exception {
//        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
//        queryBuilder.must(QueryBuilders.termQuery("gamePlatId", gamePlatId));
//        queryBuilder.must(QueryBuilders.termQuery("roundId.keyword", roundId));
//
//        List<Betslips> list = elasticsearchTemplate.search(queryBuilder, Betslips.class);
//        if (CollectionUtil.isNotEmpty(list)) {
//            return list.get(0);
//        }
        return null;
    }

    /**
     * @param betslips 投注信息
     * @param :        基于roundId  2: 基于transactionId
     * @return 成功失败
     * @throws Exception
     */
    public boolean updateSportById(Betslips betslips) throws Exception {
        if (this.baseMapper.updateById(betslips) > 0) {
            jedisUtil.setex(String.format(BET_SPORT_KEY, betslips.getGamePlatId(), betslips.getRoundId(), betslips.getXbUid()), 30, JSON.toJSONString(betslips));

            BulkRequest request = new BulkRequest();
            request.add(new IndexRequest(BETSLIPS_ES).id(betslips.getId().toString()).source(JSONObject.toJSONString(betslips), XContentType.JSON));
            // 写入后强制刷新，重要，影响性能。因为es是1秒刷新一次
            request.setRefreshPolicy("true");
            //同步执行
        //    BulkResponse bulkResponse = restHighLevelClient.bulk(request, RequestOptions.DEFAULT);
        //    return bulkResponse.status().equals(RestStatus.OK);
        }
        return false;
    }


    /**
     * @param betslips 投注信息
     * @param type     1: 基于roundId  2: 基于transactionId
     * @return 成功失败
     * @throws Exception
     */
    public boolean updateByBetId(Betslips betslips, int type) throws Exception {
//        log.info("更新用户注单1==>{}", JSONUtil.toJsonStr(betslips));
//        if (this.baseMapper.updateById(betslips) > 0) {
//            log.info("更新用户注单==>{}", betslips.getId());
//            jedisUtil.setex(String.format(BET_KEY, betslips.getGamePlatId(), 1 == type ? betslips.getRoundId() : betslips.getTransactionId(), betslips.getGameTypeId(), betslips.getXbUid()), 30, JSON.toJSONString(betslips));
//            return elasticsearchTemplate.update(betslips);
//        }
        return false;
    }

    /**
     * 查询我的体育注单
     *
     * @param reqPage 查询参数
     * @return 结果
     */
    public ResPage<Betslips> queryMySportBets(ReqPage<BetQo> reqPage, BaseParams.HeaderInfo userInfo) throws Exception {
        // 获取Header信息 Token、lang、device
        PageSortHighLight psh = new PageSortHighLight((int) reqPage.getCurrent(), (int) reqPage.getSize());
        psh.setSort(new Sort(new Sort.Order(SortOrder.DESC, "dtStarted")));
        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
        queryBuilder.must(QueryBuilders.termQuery("xbUid", userInfo.getId()));
        queryBuilder.must(QueryBuilders.termQuery("gameGroupId", 1));
        if (null != reqPage.getData().getStatus() && reqPage.getData().getStatus().intValue() != 0) {
            queryBuilder.must(QueryBuilders.termQuery("xbStatus", reqPage.getData().getStatus()));
        }
        if (null != reqPage.getData().getTypeId()) {
            queryBuilder.must(QueryBuilders.termQuery("amountType", reqPage.getData().getTypeId())); // 单注，多注
        }
        if (null != reqPage.getData().getGameTypeId()) {
            queryBuilder.must(QueryBuilders.wildcardQuery("sportType", ("*" + reqPage.getData().getGameTypeId() + "*").toLowerCase())); // 体育比赛类型：足球，棒球
        }

        if (1 == reqPage.getData().getStatus().intValue()) {
            if (Objects.nonNull(reqPage.getData().getStartTime()) && Objects.nonNull(reqPage.getData().getEndTime())) {
                queryBuilder.must(QueryBuilders.rangeQuery("dtStarted").from(reqPage.getData().getStartTime()).to(reqPage.getData().getEndTime()).includeLower(true).includeUpper(true));
            } else if (Objects.nonNull(reqPage.getData().getStartTime())) {
                queryBuilder.must(QueryBuilders.rangeQuery("dtStarted").from(reqPage.getData().getStartTime()).includeLower(true));
            } else if (Objects.nonNull(reqPage.getData().getEndTime())) {
                queryBuilder.must(QueryBuilders.rangeQuery("dtStarted").to(reqPage.getData().getEndTime()).includeUpper(true));
            }
        } else {
            if (Objects.nonNull(reqPage.getData().getStartTime()) && Objects.nonNull(reqPage.getData().getEndTime())) {
                queryBuilder.must(QueryBuilders.rangeQuery("dtCompleted").from(reqPage.getData().getStartTime()).to(reqPage.getData().getEndTime()).includeLower(true).includeUpper(true));
            } else if (Objects.nonNull(reqPage.getData().getStartTime())) {
                queryBuilder.must(QueryBuilders.rangeQuery("dtCompleted").from(reqPage.getData().getStartTime()).includeLower(true));
            } else if (Objects.nonNull(reqPage.getData().getEndTime())) {
                queryBuilder.must(QueryBuilders.rangeQuery("dtCompleted").to(reqPage.getData().getEndTime()).includeUpper(true));
            }
        }
//
//        log.info("queryBuilder==>{}", JSONUtil.toJsonStr(queryBuilder));
//        PageList<Betslips> pageList = elasticsearchTemplate.search(queryBuilder, psh, Betslips.class);
//        ResPage<Betslips> result = new ResPage<>();
//        result.setCurrent(pageList.getCurrentPage());
//        result.setSize(pageList.getPageSize());
//        result.setTotal(pageList.getTotalElements());
//        result.setList(pageList.getList());
//        result.setPages(pageList.getTotalPages());
        return null;
    }

    /**
     * 查询我的注单
     *
     * @param reqPage 查询参数
     * @return 结果
     */
    public ResPage<Betslips> queryMyBets(ReqPage<BetQo> reqPage, BaseParams.HeaderInfo userInfo) throws Exception {
        // 获取Header信息 Token、lang、device
        PageSortHighLight psh = new PageSortHighLight((int) reqPage.getCurrent(), (int) reqPage.getSize());
        psh.setSort(new Sort(new Sort.Order(SortOrder.DESC, "dtStarted")));
        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
        queryBuilder.must(QueryBuilders.termQuery("xbUid", userInfo.getId()));
        if (Objects.nonNull(reqPage.getData().getStatus()) && reqPage.getData().getStatus() != 0) {
            queryBuilder.must(QueryBuilders.termQuery("xbStatus", reqPage.getData().getStatus()));
        }
        if (Objects.nonNull(reqPage.getData().getStartTime()) && Objects.nonNull(reqPage.getData().getEndTime())) {
            queryBuilder.must(QueryBuilders.rangeQuery("dtStarted").from(reqPage.getData().getStartTime()).to(reqPage.getData().getEndTime()).includeLower(true).includeUpper(true));
        } else if (Objects.nonNull(reqPage.getData().getStartTime())) {
            queryBuilder.must(QueryBuilders.rangeQuery("dtStarted").from(reqPage.getData().getStartTime()).includeLower(true));
        } else if (Objects.nonNull(reqPage.getData().getEndTime())) {
            queryBuilder.must(QueryBuilders.rangeQuery("dtStarted").to(reqPage.getData().getEndTime()).includeUpper(true));
        }
        if (Objects.nonNull(reqPage.getData().getGameTypeId()) && reqPage.getData().getGameTypeId() != 0) {
            queryBuilder.must(QueryBuilders.termQuery("gameTypeId.keyword", reqPage.getData().getGameTypeId()));
        }
        if (Objects.nonNull(reqPage.getData().getPlatId()) && reqPage.getData().getPlatId() != 0) {
            queryBuilder.must(QueryBuilders.termQuery("gamePlatId", reqPage.getData().getPlatId()));
        }
//        PageList<Betslips> pageList = elasticsearchTemplate.search(queryBuilder, psh, Betslips.class);
//        ResPage<Betslips> result = new ResPage<>();
//        result.setCurrent(pageList.getCurrentPage());
//        result.setSize(pageList.getPageSize());
//        result.setTotal(pageList.getTotalElements());
//        result.setList(pageList.getList());
//        result.setPages(pageList.getTotalPages());
        return null;
    }


    public ResPage<Betslips> queryBets(ReqPage<AdminBetQo> reqPage) {
        return ResPage.get(queryBets(reqPage, assembleParameter(reqPage.getData())));
    }

    public List<BetStatisticsDto> betStatistics(AdminBetQo reqPage) throws Exception {

        BoolQueryBuilder queryBuilder = assembleParameter(reqPage);
        List<BetStatisticsDto> list = new ArrayList<>();
//        double stakeStatsSum = elasticsearchTemplate.aggs("validStake", AggsType.sum, queryBuilder, Betslips.class);
//        double profitStatsSum = elasticsearchTemplate.aggs("xbProfit", AggsType.sum, queryBuilder, Betslips.class);
//
//        BetStatisticsDto dto = new BetStatisticsDto();
//        dto.setCurrency(configCache.getCurrency());
//        dto.setTotalStake(BigDecimal.valueOf(stakeStatsSum));
//        dto.setTotalProfit(BigDecimal.valueOf(profitStatsSum));
//        list.add(dto);
        return list;
    }

    public Betslips getBetslipsById(Long id) throws Exception {
      //  return elasticsearchTemplate.getById(id.toString(), Betslips.class);
        return null;
    }

    private BoolQueryBuilder assembleParameter(AdminBetQo qo) {
        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
        if (Objects.nonNull(qo.getId()) && qo.getId() != 0) {
            queryBuilder.must(QueryBuilders.termQuery("id", qo.getId()));
        }
        if (Objects.nonNull(qo.getUid()) && qo.getUid() != 0) {
            queryBuilder.must(QueryBuilders.termQuery("xbUid", qo.getUid()));
        }
        if (null != qo.getGameGroupId()) {
            queryBuilder.must(QueryBuilders.termQuery("gameGroupId", qo.getGameGroupId()));
        }

        if (null != qo.getGamePlatId()) {
            queryBuilder.must(QueryBuilders.termQuery("gamePlatId", qo.getGamePlatId()));
        }

        if (StringUtils.isNotBlank(qo.getUsername())) {
            queryBuilder.must(QueryBuilders.matchQuery("xbUsername", qo.getUsername()));
        }
        if (Objects.nonNull(qo.getStatus()) && qo.getStatus() != 0) {
            queryBuilder.must(QueryBuilders.termQuery("xbStatus", qo.getStatus()));
        }
        if (StringUtils.isNotBlank(qo.getGameId())) {
            queryBuilder.must(QueryBuilders.termQuery("gameTypeId.keyword", qo.getGameId()));
        }
        if (Objects.nonNull(qo.getStartTime()) && Objects.nonNull(qo.getEndTime())) {
            queryBuilder.must(QueryBuilders.rangeQuery("dtStarted").from(qo.getStartTime()).to(qo.getEndTime()).includeLower(true).includeUpper(true));
        } else if (Objects.nonNull(qo.getStartTime())) {
            queryBuilder.must(QueryBuilders.rangeQuery("dtStarted").from(qo.getStartTime()).includeLower(true));
        } else if (Objects.nonNull(qo.getEndTime())) {
            queryBuilder.must(QueryBuilders.rangeQuery("dtStarted").to(qo.getEndTime()).includeUpper(true));
        }
        if (CollectionUtil.isNotEmpty(qo.getGameTypeIds())) {
            BoolQueryBuilder queryBuilder1 = QueryBuilders.boolQuery();
            for (Integer gameTypeId : qo.getGameTypeIds()) {
                queryBuilder1.should(QueryBuilders.termQuery("gameId", gameTypeId));
            }
            queryBuilder.must(queryBuilder1);
        }
        return queryBuilder;
    }

    /**
     * fastgame使用
     *
     * @param betslips
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Lock4j(keys = {"#betslips.xbUid", "#betslips.transactionId", "#betslips.gameId"}, expire = 60000, acquireTimeout = 1000)
    public boolean saveBetslipsMysql(Betslips betslips) {
        Betslips oldBetslips = queryBetByTransactionId(betslips.getTransactionId(), betslips.getGamePlatId(), null, betslips.getXbUid());
        if (Objects.nonNull(oldBetslips)) {
            List<DigitainBase.BetItem> betItemList = Optional.ofNullable(JSON.parseArray(oldBetslips.getBetJson(), DigitainBase.BetItem.class)).orElse(new ArrayList<>());
            if (betItemList.stream().noneMatch(m -> m.getTxId().equals(betslips.getTransactionId()))) {
                List<DigitainBase.BetItem> itemList = Optional.ofNullable(JSON.parseArray(betslips.getBetJson(), DigitainBase.BetItem.class)).orElse(new ArrayList<>());
                betItemList.addAll(itemList);
                oldBetslips.setBetJson(JSONObject.toJSONString(betItemList));
                oldBetslips.setStake(NumberUtil.add(oldBetslips.getStake(), betslips.getStake()));
                boolean flag = true;
                if (NumberUtil.isGreater(betslips.getStake(), BigDecimal.ZERO) && betslips.getChangeBalance()) {
                    flag = userCoinBase.userCoinChange(
                            new UserCoinChangeParams.UserCoinChangeReq()
                                    .setUid(betslips.getXbUid())
                                    .setOutIn(0)
                                    .setExternalTxId(betslips.getGamePlatId() + "-" + itemList.get(0).getTxId())
                                    .setCoin(betslips.getStake())
                                    .setReferId(betslips.getId())
                                    .setCategory(UserCoinChangeParams.FlowCategoryTypeEnum.BET.getCode())
                    );
                }
                if (!flag) {
                    throw new BusinessException(CodeInfo.ACTIVE_BET_COIN_NO_ENOUGH);
                }
                jedisUtil.setex(String.format(BET_KEY, betslips.getGamePlatId(), betslips.getTransactionId(), null, betslips.getXbUid()), 30, JSON.toJSONString(betslips));
                return this.updateByBetIdForMysql(oldBetslips);
            }
            throw new DigitainException(DigitainErrorCode.TRANSACTION_ALREADY_EXISTS, "TRANSACTION_ALREADY_EXISTS", null);

        }
        if (this.baseMapper.insert(betslips) > 0) {
            if (NumberUtil.isGreater(betslips.getStake(), BigDecimal.ZERO) && betslips.getChangeBalance()) {
                boolean flag = userCoinBase.userCoinChange(
                        new UserCoinChangeParams.UserCoinChangeReq()
                                .setUid(betslips.getXbUid())
                                .setOutIn(0)
                                .setExternalTxId(betslips.getGamePlatId() + "-" + betslips.getTransactionId())
                                .setCoin(betslips.getStake())
                                .setReferId(betslips.getId())
                                .setCategory(UserCoinChangeParams.FlowCategoryTypeEnum.BET.getCode())
                );
                if (!flag) {
                    throw new BusinessException(CodeInfo.ACTIVE_BET_COIN_NO_ENOUGH);
                }
            }
            jedisUtil.setex(String.format(BET_KEY, betslips.getGamePlatId(), betslips.getTransactionId(), null, betslips.getXbUid()), 30, JSON.toJSONString(betslips));
            return true;
        }
        return false;
    }

    /**
     * fastgame使用
     *
     * @param list
     * @return
     * @throws Exception
     */
    public void saveBetslipsEs(List<Betslips> list) throws Exception {
      //  elasticsearchTemplate.saveBatch(list);
    }

    /**
     * fastgame使用
     *
     * @param betslips
     * @return
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean updateByBetIdForMysql(Betslips betslips) {
        if (SqlHelper.retBool(this.baseMapper.updateById(betslips))) {
            jedisUtil.setex(String.format(BET_KEY, betslips.getGamePlatId(), betslips.getTransactionId(), null, betslips.getXbUid()), 30, JSON.toJSONString(betslips));
            return true;
        }
        return false;
    }

    /**
     * fastgame使用
     *
     * @param list
     * @return
     * @throws Exception
     */
    public boolean updateByBetIdForEs(List<Betslips> list) throws Exception {
//        for (Betslips betslips : list) {
//            elasticsearchTemplate.update(betslips);
//        }
        return true;
    }

    /**
     * fastGame 游戏修改投注查询
     *
     * @param uid           用户id
     * @param gameId        游戏id
     * @param platId        平台id
     * @param txId          交易变化
     * @param operationType 37 开彩  38投注
     * @return
     */
    public Betslips queryByTxId(Integer uid, String gameId, Integer platId, String txId, Integer operationType) throws Exception {
        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
        queryBuilder.must(QueryBuilders.termQuery("xbUid", uid));
        queryBuilder.must(QueryBuilders.termQuery("gameId", gameId));
        queryBuilder.must(QueryBuilders.termQuery("gamePlatId", platId));
        if (operationType == 37) {
            queryBuilder.must(QueryBuilders.termQuery("winTransactionId", txId));
        } else if (operationType == 38) {
            queryBuilder.must(QueryBuilders.termQuery("transactionId", txId));
        } else {
            queryBuilder.must(QueryBuilders.termQuery("winTransactionId", txId));
        }
//        List<Betslips> list = elasticsearchTemplate.search(queryBuilder, Betslips.class);
//        if (CollectionUtil.isNotEmpty(list)) {
//            return list.get(0);
//        }
        return null;
    }

    /**
     * 分页查询ES注单表记录
     *
     * @param reqPage      分页信息
     * @param queryBuilder 查询条件
     * @return res
     */
    public Page<Betslips> queryBets(ReqPage<?> reqPage, BoolQueryBuilder queryBuilder) {
        try {
            PageSortHighLight psh = new PageSortHighLight((int) reqPage.getCurrent(), (int) reqPage.getSize());
            if (ArrayUtils.isNotEmpty(reqPage.getSortField())) {
                Sort.Order[] sorts = new Sort.Order[reqPage.getSortField().length];
                for (int i = 0; i < reqPage.getSortField().length; i++) {
                    sorts[i] = new Sort.Order(SortOrder.DESC.name().equals(reqPage.getSortKey()) ? SortOrder.DESC : SortOrder.ASC, reqPage.getSortField()[i]);
                }
                psh.setSort(new Sort(sorts));
            } else {
                psh.setSort(new Sort(new Sort.Order(SortOrder.DESC, "createdAt")));
            }
//
//            PageList<Betslips> pageList = elasticsearchTemplate.search(queryBuilder, psh, Betslips.class);
//            Page<Betslips> result = new Page<>();
//            result.setCurrent(pageList.getCurrentPage());
//            result.setSize(pageList.getPageSize());
//            result.setTotal(pageList.getTotalElements());
//            result.setRecords(pageList.getList());
//            result.setPages(pageList.getTotalPages());
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
