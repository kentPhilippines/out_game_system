//package com.lt.win.service.base;
//
//import com.alibaba.fastjson.JSONObject;
//import com.lt.win.service.exception.BusinessException;
//import com.lt.win.service.io.dto.Betslips;
//import com.lt.win.service.io.dto.CoinLog;
//import com.lt.win.service.io.dto.UserCoinChangeParams;
//import com.lt.win.utils.components.response.CodeInfo;
//import lombok.extern.slf4j.Slf4j;
//import org.elasticsearch.action.bulk.BulkRequest;
//import org.elasticsearch.action.bulk.BulkResponse;
//import org.elasticsearch.action.index.IndexRequest;
//import org.elasticsearch.client.RequestOptions;
//import org.elasticsearch.client.RestHighLevelClient;
//import org.elasticsearch.common.xcontent.XContentType;
//import org.elasticsearch.rest.RestStatus;
//import org.springframework.scheduling.annotation.Async;
//import org.springframework.stereotype.Component;
//import org.zxp.esclientrhl.repository.ElasticsearchTemplate;
//
//import javax.annotation.Resource;
//import java.math.BigDecimal;
//
//import static com.lt.win.service.io.dto.UserCoinChangeParams.FlowCategoryTypeEnum.*;
//
///**
// * @Auther: wells
// * @Date: 2022/10/5 19:43
// * @Description:
// */
//@Slf4j
//@Component
//public class EsSaveBase {
//    @Resource
//    private RestHighLevelClient restHighLevelClient;
//    @Resource
//    private ActivityServiceBase activityServiceBase;
//    @Resource
//    private ElasticsearchTemplate<Betslips, String> betslipsTemplate;
//
//    /**
//     * 用户流水存储同步ES
//     *
//     * @param coinLog
//     * @return
//     */
//    @Async
//    public void saveAndES(CoinLog coinLog, BigDecimal coinAfter) {
//        try {
//            log.info("用户流水同步ES入参：{}", JSONObject.toJSONString(coinLog));
//            long s = System.currentTimeMillis();
//            BulkRequest request = new BulkRequest();
//            String coinLogIndex = EsTableNameBase.getTableName(CoinLog.class);
//            request.add(new IndexRequest(coinLogIndex).id(coinLog.getId().toString()).source(JSONObject.toJSONString(coinLog), XContentType.JSON));
//            // 写入后强制刷新，重要，影响性能。因为es是1秒刷新一次
//            request.setRefreshPolicy("true");
//            //同步执行
//            BulkResponse bulkResponse = restHighLevelClient.bulk(request, RequestOptions.DEFAULT);
//            long e = System.currentTimeMillis();
//            log.info("用户流水同步ES入参ID：{},耗时 {}", coinLog.getId(), s - e);
//            if (!bulkResponse.status().equals(RestStatus.OK)) {
//
//                log.error("用户流水同步ES 失败：{}，ID：{} ", bulkResponse.status(), coinLog.getId());
//                throw new BusinessException(CodeInfo.ES_SYNCHRONOUS_ERROR);
//            }
//
//            // 根据用户开彩流水 统计打码量
//            if (DRAW.getCode() == coinLog.getCategory()) {
//                var referStr = coinLog.getReferId().toString();
//                var validStake = BigDecimal.ZERO;
//                //查询注单的游戏的有效投资金额
//                if (referStr.length() > 16) {
//                    referStr = referStr.substring(0, 16);
//                    var betslips = betslipsTemplate.getById(referStr, Betslips.class);
//                    validStake = betslips.getValidStake();
//                }
//                //用户余额少于1则不参与活动流程
//                if (coinAfter.compareTo(BigDecimal.ONE) > 0) {
//                    activityServiceBase.addActivityBetSum(coinLog, validStake);
//                    activityServiceBase.RiskFreeBet(coinLog);
//                } else {
//                    activityServiceBase.lastAddActivityCoin(coinLog, validStake);
//                }
//            } else if (UserCoinChangeParams.FlowCategoryTypeEnum.REFUND.getCode() == coinLog.getCategory()) {
//
//                activityServiceBase.subActivityBetSum(coinLog);
//
//            } else if (SYSTEM_RECONCILIATION.getCode() == coinLog.getCategory() && (coinAfter.compareTo(BigDecimal.ONE) < 0)) {
//                activityServiceBase.lastAddActivityCoin(coinLog);
//            }
//
//        } catch (
//                Exception e) {
//            log.error("用户流水同步ES异常", e);
//            throw new BusinessException(CodeInfo.ES_SYNCHRONOUS_ERROR);
//        }
//
//    }
//
//}
