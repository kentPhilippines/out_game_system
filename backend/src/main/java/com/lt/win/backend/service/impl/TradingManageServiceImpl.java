package com.lt.win.backend.service.impl;

import com.lt.win.backend.io.dto.record.TradingManageParams.*;
import com.lt.win.backend.service.TradingManageService;
import com.lt.win.service.io.dto.CoinLog;
import com.lt.win.utils.BeanConvertUtils;
import com.lt.win.utils.components.pagination.ReqPage;
import com.lt.win.utils.components.pagination.ResPage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zxp.esclientrhl.enums.AggsType;
//import org.zxp.esclientrhl.repository.ElasticsearchTemplate;
import org.zxp.esclientrhl.repository.PageList;
import org.zxp.esclientrhl.repository.PageSortHighLight;
import org.zxp.esclientrhl.repository.Sort;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @Auther: wells
 * @Date: 2022/8/31 00:40
 * @Description:
 */
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TradingManageServiceImpl implements TradingManageService {
//    private final ElasticsearchTemplate<CoinLog, String> elasticsearchTemplate;

    /**
     * @param req
     * @return com.lt.win.utils.components.pagination.ResPage<com.lt.win.backend.io.dto.record.TradingManageParams.CoinLogListReq>
     * @Description 查询交易记录
     * @Param [reqBody]
     */
    @Override
    public ResPage<CoinLogListRes> tradingManageList(ReqPage<CoinLogListReq> req) {
        ResPage<CoinLogListRes> result = new ResPage<>();
        try {
            CoinLogListReq data = req.getData();
            PageSortHighLight psh = new PageSortHighLight((int) req.getCurrent(), (int) req.getSize());
            String orderFiled = req.getSortField().length == 0 ? "createdAt" : req.getSortField()[0];
            SortOrder sortOrder = "ASC".equals(req.getSortKey()) ? SortOrder.ASC : SortOrder.DESC;
            psh.setSort(new Sort(new Sort.Order(sortOrder, orderFiled)));
            BoolQueryBuilder queryBuilder = getQuery(data);
         //   PageList<CoinLog> pageList = elasticsearchTemplate.search(queryBuilder, psh, CoinLog.class);
//            result.setCurrent(pageList.getCurrentPage());
//            result.setSize(pageList.getPageSize());
//            result.setTotal(pageList.getTotalElements());
//            List<CoinLogListRes> coinLogListRes = BeanConvertUtils.copyListProperties(pageList.getList(), CoinLogListRes::new);
//            result.setList(coinLogListRes);
//            result.setPages(pageList.getTotalPages());
        } catch (Exception e) {
            e.printStackTrace();
            log.error("查询交易记录接口异常;{}", e.getMessage());
        }
        return result;
    }

    /**
     * @param reqBody
     * @return StatisticsTrading
     * @Description 统计交易记录类型
     * @Param
     */
    @Override
    public StatisticsTrading statisticsTrading(CoinLogListReq reqBody) {
        StatisticsTrading statisticsTrading = new StatisticsTrading();
        try {
            BoolQueryBuilder queryBuilder = getQuery(reqBody);
        //    Map map = elasticsearchTemplate.aggs("coin", AggsType.sum, queryBuilder, CoinLog.class, "category");
//            if (Objects.nonNull(map)) {
//                map.forEach((key, value) -> {
//                    Integer category = ((Long) key).intValue();
//                    BigDecimal sumCoin = BigDecimal.valueOf((Double) value).setScale(2, RoundingMode.DOWN);
//
//                    switch (category) {
//                        case 1:
//                            statisticsTrading.setCoinDeposit(sumCoin);
//                            break;
//                        case 2:
//                            statisticsTrading.setCoinWithdrawal(sumCoin);
//                            break;
//                        case 3:
//                            statisticsTrading.setCoinBet(sumCoin);
//                            break;
//                        case 4:
//                            statisticsTrading.setCoinPayOut(sumCoin);
//                            break;
//                        case 5:
//                            statisticsTrading.setCoinRebate(sumCoin);
//                            break;
//                        case 6:
//                            statisticsTrading.setCoinCommission(sumCoin);
//                            break;
//                        case 7:
//                            statisticsTrading.setCoinRewards(sumCoin);
//                            break;
//                        case 8:
//                            statisticsTrading.setCoinReconciliation(sumCoin);
//                            break;
//                        default:
//                            break;
//                    }
//                });
 //           }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("统计交易记录类型;{}", e.getMessage());
        }
        return statisticsTrading;
    }

    /**
     * @return org.elasticsearch.index.query.BoolQueryBuilder
     * @Description 构建查询条件
     * @Param [req]
     **/
    public BoolQueryBuilder getQuery(CoinLogListReq req) {
        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
        if (Objects.nonNull(req.getCategory())) {
            queryBuilder.must(QueryBuilders.termQuery("category", req.getCategory()));
        }
        if (Objects.nonNull(req.getStatus())) {
            queryBuilder.must(QueryBuilders.termQuery("status", req.getStatus()));
        }
        if (Objects.nonNull(req.getUid())) {
            queryBuilder.must(QueryBuilders.termQuery("uid", req.getUid()));
        }
        if (Objects.nonNull(req.getId())) {
            BoolQueryBuilder idQuery = QueryBuilders.boolQuery();
            idQuery.should(QueryBuilders.termQuery("id", req.getId()));
            idQuery.should(QueryBuilders.termQuery("referId", req.getId()));
            queryBuilder.must(idQuery);
        }
        if (Objects.nonNull(req.getUsername())) {
            queryBuilder.must(QueryBuilders.termQuery("username.keyword", req.getUsername()));
        }
        if (Objects.nonNull(req.getStartTime()) && Objects.nonNull(req.getEndTime())) {
            queryBuilder.must(QueryBuilders.rangeQuery("createdAt").from(req.getStartTime()).to(req.getEndTime()).includeLower(true).includeUpper(true));
        } else if (Objects.nonNull(req.getStartTime())) {
            queryBuilder.must(QueryBuilders.rangeQuery("createdAt").from(req.getStartTime()).includeLower(true));
        } else if (Objects.nonNull(req.getEndTime())) {
            queryBuilder.must(QueryBuilders.rangeQuery("createdAt").to(req.getEndTime()).includeUpper(true));
        }
        return queryBuilder;
    }
}
