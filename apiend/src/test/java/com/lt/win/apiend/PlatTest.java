package com.lt.win.apiend;

import cn.hutool.core.date.DateUtil;
import com.github.yitter.idgen.YitIdHelper;
import com.lt.win.service.base.PlatCodeEnum;


import com.lt.win.service.cache.redis.PlatListCache;
import com.lt.win.service.cache.redis.UserCache;
import com.lt.win.service.impl.BetslipsServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 游戏对接请求接口demo
 *
 * @author fangzs
 * @date 2022/7/11 15:39
 */
@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class PlatTest {

    @Autowired
    private BetslipsServiceImpl betslipsServiceImpl;
    @Autowired
    PlatListCache platListCache;
//    @Test
//    public void postGames() {
//        gameDriveManager.pullGames(11);
//    }

    @Test
    public void gameLaunch() {
//        LaunchBo launchBo = new LaunchBo();
//        launchBo.setApiKey(DragonGameConstant.API_KEY)
//                .setSessionId("jljlkjgsdgwsddsfsd")
//                .setProvider("dragongaming")
//                .setGameId("1142")
//                .setGameType("slots")
//                .setPlatform("mobile")
//                .setLanguage("en")
//                .setAmountType("fun");
////                .setAmountType("real");
//
//        PlayerBo context = new PlayerBo()
//                .setId("1111")
//                .setUsername("test_2")
//                .setCountry("USA")
//                .setCurrency("USD");
//        launchBo.setContext(context);
//        DragonGameUtils.gameLaunch(launchBo,DragonGameConstant.BASE_URL);
    }

    @Test
    public void queryGameHistory() {
//        DragonGameUtils.gameHistory(new GameHistoryQo()
//                .setApiKey(DragonGameConstant.API_KEY)
//                .setPlayerId("1111")
//                .setPageNum(1)
//                .setEndDate(DateUtil.parseDate("2022-08-01 00:00:00"))
//                .setStartDate(DateUtil.parseDate("2022-07-15 00:00:00"))
//                .setAmountType("real")
//                ,DragonGameConstant.BASE_URL
//        );
    }

//    @Test
//    public void testBet() throws Exception {
//        Betslips betslips = betslipsServiceImpl.queryBetByRoundId("0006-6986", platListCache.platList(GamePlatEnum.GMT_SLOT.getCode()).getId(), "yakthai", 914);
//        System.out.println(JSONObject.toJSON(betslips));
//    }
//
    @Test
    public void postPlaySonGames() {
        //gameDriveManager.pullGames(platListCache.platList(PlatCodeEnum.DIGITAIN.getCode()).getId());
    }

    @Test
    public void testId(){

        System.out.println(YitIdHelper.nextId());
    }
    @Autowired
    protected UserCache userCache;
    /**
     * 过期token生产
     */
    @Test
    public void jwtToken(){
        /**
         * ConfigCache  # getJwtProp()
         * 注释56 57行
         * secret = "{\"secret\":\"1e2f9120334f9449e3ed95c8f58f1817\",\"expired\":1}";
         */
     //   String jwtToken = userInfoServiceImpl.genJwtToken(9417, "dianfang08");
//        System.out.println(jwtToken);
//        userCache.setUserToken(9417, jwtToken);
    }

}
