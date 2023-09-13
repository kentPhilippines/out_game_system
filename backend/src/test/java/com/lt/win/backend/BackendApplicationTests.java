package com.lt.win.backend;

import cn.hutool.json.JSONUtil;
import com.lt.win.backend.base.ReportServiceBase;
import com.lt.win.backend.io.bo.ComprehensiveChart;
import com.lt.win.backend.io.bo.ReportCenter;
import com.lt.win.backend.io.bo.StartEndTime;
import com.lt.win.backend.io.bo.user.CapitalStatisticsListReqBody;
import com.lt.win.backend.io.po.ReportStatisticsPo;
import com.lt.win.backend.redis.GameCache;
import com.lt.win.backend.service.IComprehensiveChartService;
import com.lt.win.backend.service.impl.DailyReportExStatisticsServiceImpl;
import com.lt.win.backend.service.impl.DailyReportRealTimeStatisticsServiceImpl;
import com.lt.win.backend.service.impl.PlatformLeaderBoardServiceImpl;
import com.lt.win.dao.generator.po.User;
import com.lt.win.dao.generator.service.UserService;
import com.lt.win.dao.generator.service.UserWalletService;
import com.lt.win.service.cache.KeyConstant;
import com.lt.win.service.cache.redis.UserCache;
import com.lt.win.utils.DateUtils;
import com.lt.win.utils.JedisUtil;
import com.lt.win.utils.components.pagination.ReqPage;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

//@SpringBootTest
@Slf4j
@SpringBootTest
class BackendApplicationTests {
    @Resource
    UserService userServiceImpl;
    @Resource
    UserWalletService userWalletServiceImpl;
    @Resource
    ReportServiceBase reportServiceBase;

    @Resource
    private UserCache userCache;

    @Resource
    DailyReportRealTimeStatisticsServiceImpl dailyReportRealTimeStatisticsServiceImpl;

    @Resource
    private IComprehensiveChartService comprehensiveChartServiceImpl;

    @Resource
    PlatformLeaderBoardServiceImpl platformLeaderBoardServiceImpl;
    @Resource
    private DailyReportExStatisticsServiceImpl dailyReportExStatisticsServiceImpl;

    @Resource
    GameCache GameCache;
    @Resource
    private JedisUtil jedisUtil;

    //    @Test
    void contextLoads() {
    }


    @Test
    void testConstructor() {
        var now = DateUtils.getCurrentTime();

        var use = userServiceImpl.getById(619);

        // 二级
        userServiceImpl.lambdaUpdate()
                .set(User::getSupUid1, use.getId())
                .set(User::getSupUsername1, use.getUsername())
                .set(User::getSupUid2, use.getSupUid1())
                .set(User::getRole, 1)
                .ge(User::getId, 620)
                .le(User::getId, 628)
                .update();
        // 三级
        var agent = userServiceImpl.getById(620);
        userServiceImpl.lambdaUpdate()
                .set(User::getSupUid1, agent.getId())
                .set(User::getSupUsername1, agent.getUsername())
                .set(User::getSupUid2, agent.getSupUid1())
                .set(User::getSupUid3, agent.getSupUid2())
                .set(User::getRole, 1)
                .ge(User::getId, 630)
                .le(User::getId, 637)
                .update();

        // 四级
        agent = userServiceImpl.getById(630);
        userServiceImpl.lambdaUpdate()
                .set(User::getSupUid1, agent.getId())
                .set(User::getSupUsername1, agent.getUsername())
                .set(User::getSupUid2, agent.getSupUid1())
                .set(User::getSupUid3, agent.getSupUid2())
                .set(User::getSupUid4, agent.getSupUid3())
                .set(User::getRole, 1)
                .ge(User::getId, 640)
                .le(User::getId, 646)
                .update();
    }


    @Test
    public void getGameList() {
//        jedisUtil.del(KeyConstant.PLATFORM_HASH);
//        log.info("游戏列表===》{}", GameCache.getGameListResCache());

//        StartEndTime reqBody = new StartEndTime();
//        reqBody.setStartTime(1664035200);
//        reqBody.setEndTime(1664553599);
//        platformLeaderBoardServiceImpl.platBetCoinChart(reqBody);
        ReportCenter.DailyReportStatisticsReqBody reqBody = new ReportCenter.DailyReportStatisticsReqBody();
        reqBody.setStartTime(1665417600);
        reqBody.setEndTime(1665503999);
        dailyReportExStatisticsServiceImpl.dailyReportStatisticsPlatCoin(reqBody);
    }

    @Test
    public void getDateTime() {
        ReqPage<StartEndTime> reqBody = new ReqPage<>();
        StartEndTime time = new StartEndTime();
        time.setStartTime(1664035200);
        time.setEndTime(1664207999);
        reqBody.setData(time);
        reqBody.setSize(10);
        reqBody.setCurrent(1);
        log.info("查询结果==》{}", dailyReportRealTimeStatisticsServiceImpl.dailyReportRealTimeList(reqBody));
    }

    /**
     * 投注分析
     */
    @Test
    public void platProfitAndBetCountChart() {

        ComprehensiveChart.PlatProfitAndBetCountChartReqBody reqBody = new ComprehensiveChart.PlatProfitAndBetCountChartReqBody();
        reqBody.setEndTime(1664207999);
        reqBody.setStartTime(1664035200);
        reqBody.setGameListId(292);
        log.info("查询结果==》{}", platformLeaderBoardServiceImpl.platProfitAndBetCountChart(reqBody));

        log.info("时间==》{}", System.currentTimeMillis());
    }

    /**
     * 投注分析
     */
    @Test
    public void getPlatCoinStatistics() throws Exception{

        ReportStatisticsPo reqBody = new ReportStatisticsPo();
        reqBody.setStartTime(1665763200);
        reqBody.setEndTime(1665849599);
//        reqBody.setGameListId(292);
//        reqBody.setColumnName("stake");
        reqBody.setColumnName("xbProfit");
//        List<Integer> userList = new ArrayList<>();
//        userList.add(9413);
//        List<Integer> userTestList = new ArrayList<>();
//        userTestList.add(9412);
//        reqBody.setUidList(userTestList);
//        log.info("查询结果==》{}", reportServiceBase.getPlatCoinStatistics(reqBody));

        CapitalStatisticsListReqBody req = new CapitalStatisticsListReqBody();
        req.setStartTime(1665763200);
        req.setEndTime(1665849599);
        comprehensiveChartServiceImpl.userZH(req);

        log.info("时间==》{}", System.currentTimeMillis());
    }

    @Test
    public void sumCoinOfCoinLog()throws Exception{
//        BigDecimal sum =   platformLeaderBoardServiceImpl.platTotalProfit(1665763200, 1665763200, null,null);
//        BigDecimal sum = reportServiceBase.sumCoinOfCoinLog(1665763200, 1665849599, null, 1, null);
//       BigDecimal sum = reportServiceBase.sumCoinOfCoinLog(null,null,null ,8,null );  1040110.76
        List<Integer> testUidList = userCache.getTestUidList();

        log.info("===testUidList====>{}",JSONUtil.toJsonStr(testUidList));
        var groupIdMap = reportServiceBase.getDailyReportStatisticsPlatCoin(1665936000, 1666022399, null, testUidList);

       log.info("=======>{}",groupIdMap);
    }


    @Test
    public void getBetCount()throws Exception{
        ReportStatisticsPo po = new ReportStatisticsPo();
        po.setStartTime(1665763200);
        po.setEndTime(1665849599);
        Long sum = reportServiceBase.getBetCount(po);
        log.info("=======>{}",sum);
    }



}
