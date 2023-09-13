package com.lt.win;

import com.alibaba.fastjson.JSONObject;
import com.lt.win.apiend.ApiendApplication;
import com.lt.win.service.base.*;
import com.lt.win.service.impl.BetslipsServiceImpl;
import com.lt.win.service.io.dto.Betslips;
import com.lt.win.service.io.dto.CoinLog;
import com.lt.win.service.io.dto.UserCoinChangeParams;
import com.lt.win.utils.components.pagination.ReqPage;
import com.lt.win.utils.components.pagination.ResPage;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.client.Request;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestHighLevelClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
//import org.zxp.esclientrhl.repository.ElasticsearchTemplate;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Auther: wells
 * @Date: 2022/8/23 00:50
 * @Description:
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApiendApplication.class)
@Slf4j
public class CodeTest {
    @Autowired
    private ActivityServiceBase activityServiceBase;
//    @Resource
//    ElasticsearchTemplate<CoinLog, String> elasticsearchTemplate;
//    @Resource
//    private RestHighLevelClient restHighLevelClient;
    @Resource
    private UserCoinBase userCoinBase;
//    @Resource
//    ElasticsearchTemplate<Betslips, String> betslipsElasticsearchTemplate;
    @Resource
    private BetslipsServiceImpl betslipsServiceImpl;
    @Resource
    private ESSqlSearchBase esSqlSearchBase;
    @Resource
    private CoinLogStatisticBase coinLogStatisticBase;
    @Resource
    private EsTableNameBase esTableNameBase;
    @Resource
    private BetStatisticBase betStatisticBase;


    @Test
    public void test() {
        List<Integer> idList = Stream.of(610,
                613).collect(Collectors.toList());
        //  Map<Integer, BigDecimal> map = activityServiceBase.getActivityBetSum(idList, "USD", null, null);
        // log.info("map=" + map);
        //   BigDecimal betSum = activityServiceBase.getBetSum(610, 3, 1661013235, 1661013999);
        //   Map<Integer, BigDecimal> betSum1 = activityServiceBase.getBetSum(idList, 3);
        //  Map<Integer, Map<String, BigDecimal>> betSum2 = activityServiceBase.getBetSum(idList, null, null);
        //  log.info("betSum=" + betSum);
        //   log.info("betSum1=" + betSum1);
        //  log.info("betSum2=" + betSum2);
    }

//    @Test
//    public void aggregation(){
//        TermsAggregationBuilder jobInfoTerm = AggregationBuilders.terms("job_info").field("job");
//        StatsAggregationBuilder subTerm = AggregationBuilders.stats("sal_info").field("sal");
//        jobInfoTerm.subAggregation(subTerm);
//
//        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
//                .withIndices("employee").withTypes("_doc")
//                .addAggregation(jobInfoTerm)
//                .build();
//
//        Map<Object, Object> resultMap = elasticsearchTemplate.query(searchQuery, searchResponse -> {
//            Map<Object, Object> objectObjectMap = Maps.newHashMap();
//
//            Terms terms = searchResponse.getAggregations().get("job_info");
//            for (Terms.Bucket bt : terms.getBuckets()) {
//                Object key = bt.getKey();
//                long docCount = bt.getDocCount();
//                objectObjectMap.put(key, docCount);
//                // 获取子集
//                InternalStats aggregations = bt.getAggregations().get("sal_info");
//                // 根据需求自行取值
//                long count = aggregations.getCount();
//            }
//            return objectObjectMap;
//        });
//
//        System.out.println(resultMap);
//
//    }

    @SneakyThrows
    @Test
    public void esQueryBySql() {
        Request request = new Request("POST", "/_sql");
        String betslipsIndex = EsTableNameBase.getTableName(Betslips.class);
        request.setJsonEntity("{\"query\":\"select xbUid,gamePlatId,sum(xbProfit) from "+betslipsIndex+"group by xbUid ,gamePlatId ORDER BY sum(xbProfit) desc  \"}");
//   //     Response response = restHighLevelClient.getLowLevelClient().performRequest(request);
//        String responseBody = EntityUtils.toString(response.getEntity(), "utf-8");
//        System.out.println(responseBody);
//        System.out.println(JSONObject.parse(responseBody));

    }

    @Test
    public void esSqlTest() {
        String betslipsIndex = EsTableNameBase.getTableName(Betslips.class);
        String sql = "select xbUid as uid, gameGroupId,gamePlatId,gameTypeId, sum(xbProfit) as profit from "+betslipsIndex+" group by xbUid,gameGroupId,gamePlatId,gameTypeId";
        // List<BetDto> betDtos = esSqlSearchBase.search(sql,BetDto::new);
        ReqPage rePage = new ReqPage();
        rePage.setPage(1, 10);
        rePage.setSortKey("DESC");
        rePage.setSortField(new String[]{"sum(xbProfit)"});
        ResPage<BetDto> resPage = esSqlSearchBase.searchPage(sql, BetDto::new, rePage);
        System.out.println("resPage=" + resPage);
    }

    @Test
    public void esSqlValueTest() {
//        String sql = "select sum(xbProfit) from betslips where createdAt>=1666454400 and createdAt<=1666886399 and xbUid not in(9428)";
//        // String sql = "select xbUid as uid, gameGroupId,gamePlatId,gameTypeId, sum(xbProfit) as profit from betslips group by xbUid,gameGroupId,gamePlatId,gameTypeId";
//        //  BetDto betDto = esSqlSearchBase.searchObject(sql, BetDto::new);
//        BigDecimal value = esSqlSearchBase.searchValue(sql, BigDecimal.class);
//        log.info("value=" + value);
    }

    @Test
    public void coinChange() {
        userCoinBase.updateConcurrentUserCoin(new BigDecimal(-1000), 611);
    }

    @SneakyThrows
    @Test
    public void activityBetSum() {
//        Betslips betslips = betslipsServiceImpl.getById(344172705587270L);
//        betslipsElasticsearchTemplate.save(betslips);
        CoinLog coinLog = new CoinLog();
        coinLog.setCoin(new BigDecimal(100));
        coinLog.setUid(9492);
        coinLog.setCategory(UserCoinChangeParams.FlowCategoryTypeEnum.BET.getCode());
        coinLog.setReferId(344172705587270L);
        // activityServiceBase.addActivityBetSum(coinLog);
        // activityServiceBase.subActivityBetSum(610,new BigDecimal(100));
        activityServiceBase.lastAddActivityCoin(coinLog);
    }

    @Test
    public void diffTest() {
//        var list = new I18nUtils().resolveCode(new Locale("es", "es"));
//        var codeArr = CodeInfo.values();
//        // for (CodeInfo codeInfo : codeArr) {
//        for (String str : list) {
//            String msg = str;
//            System.out.println(msg);
//            // set.add(codeInfo.getMsg());
////            String msg = codeInfo.getMsg().replace(" ", "");
////            String i18Msg = list.stream().filter(s -> s.contains(msg)).findFirst().orElse("");
////            if (Strings.isEmpty(i18Msg)) {
////                System.out.println();
////            } else {
////                System.out.println(i18Msg.split("=")[1]);
////            }
////            JSONObject.parseObject("" , Feature.OrderedField);
//            System.out.println(msg.split("=")[0].replace(" ", "") + "=" + msg.split("=")[1]);
//
//        }
//
//        //  list.forEach(out::println);
    }


    @Test
    @SneakyThrows
    public void syncBet() {
        List<Betslips> betslipsList = betslipsServiceImpl.list();
        DeleteIndexRequest deleteIndexRequest = new DeleteIndexRequest("betslips");
        //AcknowledgedResponse acknowledgedResponse = restHighLevelClient.indices().delete(deleteIndexRequest, RequestOptions.DEFAULT);
        //  boolean acknowledged = acknowledgedResponse.isAcknowledged();
        // log.info("acknowledged=" + acknowledged);
        //betslipsElasticsearchTemplate.saveBatch(betslipsList);

    }

    @Test
    public void validStakeOrProfit() {
        Integer startTime = 1666454400;
        Integer endTime = 1676886399;
        BigDecimal validStake = betStatisticBase.getValidBetCoinByUid(startTime, endTime, 610);
        log.info("validStake=" + validStake);
        BigDecimal profit = betStatisticBase.getProfitByUid(startTime, endTime, 610);
        log.info("profit=" + profit);
    }

    @Test
    public void testEsTableName() {
        EsTableNameBase.getTableName(CoinLog.class);
        EsTableNameBase.getTableName(Betslips.class);
    }

}

@Data
@NoArgsConstructor
class BetDto {
    private Integer uid;

    private String userName;

    private Integer gameGroupId;

    private Integer gamePlatId;

    private Integer gameTypeId;

    private BigDecimal Profit;
}
