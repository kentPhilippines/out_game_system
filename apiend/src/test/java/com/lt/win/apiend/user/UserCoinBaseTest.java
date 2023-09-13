package com.lt.win.apiend.user;

import com.lt.win.dao.generator.po.User;
import com.lt.win.dao.generator.po.UserWallet;
import com.lt.win.dao.generator.service.UserService;
import com.lt.win.dao.generator.service.UserWalletService;
import com.lt.win.service.base.ActivityServiceBase;
//import com.lt.win.service.base.EsSaveBase;
import com.lt.win.service.base.EsTableNameBase;
import com.lt.win.service.base.UserCoinBase;
import com.lt.win.service.cache.redis.CoinRewardCache;
import com.lt.win.service.cache.redis.UserCache;
import com.lt.win.service.impl.CoinLogServiceImpl;
import com.lt.win.service.io.dto.CoinLog;
import com.lt.win.utils.DateNewUtils;
import com.lt.win.utils.DateUtils;
import com.lt.win.utils.PasswordUtils;
import com.lt.win.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
//import org.zxp.esclientrhl.repository.ElasticsearchTemplate;

import javax.annotation.Resource;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * @Auther: nobody
 * @Date: 2022/8/16 17:41
 * @Description:
 */

@Slf4j
@SpringBootTest
public class UserCoinBaseTest {

    @Autowired
    RedisUtils redisUtils;
    @Resource
    private UserCoinBase userCoinBase;
    @Resource
    private CoinLogServiceImpl coinLogServiceImpl;
    @Resource
    private ActivityServiceBase activityServiceBase;
    @Resource
    private RestHighLevelClient restHighLevelClient;
    @Resource
    private UserService userServiceImpl;
    @Resource
    private CoinRewardCache coinRewardCache;
    @Resource
    private UserWalletService userWalletServiceImpl;
    @Resource
    private UserCache userCache;
//    @Resource
//    private EsSaveBase esSaveBase;
//    @Resource
//    private ElasticsearchTemplate elasticsearchTemplate;


    //CoinLog{id=347896130908065792, uid=610, username=wells002, category=1, subCategory=0, referId=347896100075737088, coin=94.99, currency=USD, coinBefore=199.9900, coinAfter=294.9800, outIn=1, status=1, createdAt=1660752900, updatedAt=1660752900}
    @Test
    public void coinBetTest() {
        //日志入库
        //  for(int i=0;i<30;i++) {
        Boolean flag = false;
        CoinLog coinLog = new CoinLog();
        coinLog.setId(345445758567L);
        coinLog.setUid(110);
        coinLog.setUsername("wells002");
        coinLog.setReferId(336545545482L);
        //日志类型
        coinLog.setCategory(3);
        coinLog.setCoin(new BigDecimal("10"));
        coinLog.setCoinBefore(new BigDecimal("199.0000"));
        coinLog.setCoinAfter(new BigDecimal("200"));
        coinLog.setStatus(1);
        coinLog.setCreatedAt(DateUtils.getCurrentTime());
        coinLog.setUpdatedAt(DateUtils.getCurrentTime());
        flag = coinLogServiceImpl.save(coinLog);
        if (flag) {
            //esSaveBase.saveAndES(coinLog, BigDecimal.ONE);
            // }
            log.info("=====>{}", flag);
        }
    }

    @Test
    public void deleteIndexRequest() throws IOException {
        DeleteIndexRequest request = new DeleteIndexRequest("betslips");
        AcknowledgedResponse deleteIndexResponse = restHighLevelClient.indices().delete(request, RequestOptions.DEFAULT);
        boolean acknowledged = deleteIndexResponse.isAcknowledged();
        log.info("删除索引{}，状态{}", "betslips", acknowledged);

        GetIndexRequest getIndexRequest = new GetIndexRequest("betslips");
        //是返回本地信息还是从主节点检索状态
        getIndexRequest.local(false);
        //以适合人类的格式返回结果
        getIndexRequest.humanReadable(true);
        //是否为每个索引返回所有默认设置
        getIndexRequest.includeDefaults(false);
        boolean exists = restHighLevelClient.indices().exists(getIndexRequest, RequestOptions.DEFAULT);
        log.info("删除索引状态{}", exists);
    }

    @Test
    public void deleteIndex() throws Exception {
        String coinLogIndex = EsTableNameBase.getTableName(CoinLog.class);
        DeleteIndexRequest request = new DeleteIndexRequest(coinLogIndex);
        AcknowledgedResponse deleteIndexResponse = restHighLevelClient.indices().delete(request, RequestOptions.DEFAULT);
        boolean acknowledged = deleteIndexResponse.isAcknowledged();
        log.info("删除索引{}，状态{}", "coin_log_es", acknowledged);

        GetIndexRequest getIndexRequest = new GetIndexRequest(coinLogIndex);
        //是返回本地信息还是从主节点检索状态
        getIndexRequest.local(false);
        //以适合人类的格式返回结果
        getIndexRequest.humanReadable(true);
        //是否为每个索引返回所有默认设置
        getIndexRequest.includeDefaults(false);
        boolean exists = restHighLevelClient.indices().exists(getIndexRequest, RequestOptions.DEFAULT);
        log.info("删除索引状态{}", exists);
    }


    @Test
    public void initUser() {
        int now = DateNewUtils.now();
        var list = new ArrayList<User>();
        var listWallet = new ArrayList<UserWallet>();
        for (int i = 80; i <= 99; i++) {
            User user = new User();
            user.setId(i);
            user.setUsername("test0" + i);
            user.setEmail(user.getUsername() + "@gmail.com");
            user.setPromoCode("PROM" + i);
            user.setCreatedAt(now);
            user.setUpdatedAt(now);
            user.setPasswordHash(PasswordUtils.generatePasswordHash("1234qwer"));
            userServiceImpl.save(user);

            UserWallet userWallet = new UserWallet();
            userWallet.setId(i);
            userWallet.setCoin(new BigDecimal(10000));
            userWallet.setUsername(user.getUsername());
            userWallet.setUpdatedAt(now);
            userWallet.setCreatedAt(now);
            userWalletServiceImpl.save(userWallet);

        }

    }
}
