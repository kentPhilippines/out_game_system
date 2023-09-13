package com.lt.win.service.base;

import com.alibaba.fastjson.JSONObject;
import com.lt.win.dao.generator.po.CoinDepositRecord;
import com.lt.win.dao.generator.po.CoinWithdrawalRecord;
import com.lt.win.dao.generator.po.Notice;
import com.lt.win.dao.generator.service.CoinDepositRecordService;
import com.lt.win.dao.generator.service.CoinWithdrawalRecordService;
import com.lt.win.dao.generator.service.GameListService;
import com.lt.win.dao.generator.service.NoticeService;
import com.lt.win.dao.generator.service.impl.CoinWithdrawalRecordServiceImpl;
import com.lt.win.service.cache.KeyConstant;
import com.lt.win.service.cache.redis.ConfigCache;
import com.lt.win.service.common.Constant;
import com.lt.win.service.io.enums.MessageDeviceEnum;
import com.lt.win.service.io.enums.NoticeEnum;
import com.lt.win.service.io.enums.SystemMessageEnum;
import com.lt.win.utils.DateUtils;
import com.lt.win.utils.JedisUtil;
import lombok.RequiredArgsConstructor;
import org.apache.groovy.util.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import static com.alibaba.fastjson.JSON.parseObject;

/**
 * @Author : Wells
 * @Date : 2020-12-12 1:53 下午
 * @Description :  组装消息内容
 */
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class NoticeBase {
    private static final Map<Integer, String> NOTIFICATION_MAP = Maps.of(
            1, NoticeEnum.SYSTEM_NOTIFICATION.getCode(),
            2, NoticeEnum.STATION_LETTER.getCode()
    );
    private final JedisUtil jedisUtil;
    private final CoinDepositRecordService coinDepositRecordServiceImpl;
    private final CoinWithdrawalRecordService coinWithdrawalRecordServiceImpl;
    private final NoticeService noticeServiceImpl;
    private final ConfigCache configCache;

    /**
     * 存款与提款条数
     *
     * @param action 类型
     */
    @Async
    public void writeDepositAndWithdrawalCount(String action) {
        var count = 0;
        //存款
        if (Constant.PUSH_DN.equals(action)) {
            count = coinDepositRecordServiceImpl.lambdaQuery()
                    .eq(CoinDepositRecord::getStatus, 0)
                    // 支付类型:0-离线 1-在线
                    .count();
            //提款
        } else if (Constant.PUSH_WN.equals(action)) {
            count = coinWithdrawalRecordServiceImpl.lambdaQuery()
                    .eq(CoinWithdrawalRecord::getStatus, 0)
                    .count();
        }
        var reJson = new JSONObject();
        reJson.put("action", action);
        reJson.put("device", MessageDeviceEnum.B.getCode());
        var msgJson = new JSONObject();
        msgJson.put("count", count);
        reJson.put("message", msgJson);
        jedisUtil.leftPush(KeyConstant.WEB_SOCKET_QUEUE, reJson.toJSONString());
    }

    /**
     * 存款与提款信息
     *
     * @param noticeEnum  类型
     * @param orderId     订单ID
     * @param uid         uid
     * @param coin        金额
     * @param accountDate 到账时间
     */
    @Async
    public void writeDepositAndWithdrawal(NoticeEnum noticeEnum, Integer uid, Long orderId, BigDecimal coin, Integer accountDate) {
        var msgJson = new JSONObject();
        msgJson.put("uid", String.valueOf(uid));
        msgJson.put("orderId", orderId);
        msgJson.put("coin", coin);
        msgJson.put("accountDate", accountDate);
        cacheMessage(noticeEnum.getCode(), msgJson);
    }

    /**
     * 维护信息
     * 游戏的维护信息数据格式{"start":1607695200,"end":1608134399,"info":""}
     *
     * @param gameId 游戏ID
     * @return String
     */
    @Async
    public void writeMaintainInfo(Integer gameId) {
//        var game = gameListServiceImpl.getById(gameId);
//        var maintenanceJson = parseObject(game.getMaintenance());
//        var startTime = Optional.ofNullable(maintenanceJson.getInteger("start")).orElse(0);
//        var endTime = Optional.ofNullable(maintenanceJson.getInteger("end")).orElse(0);
//        var msgJson = new JSONObject();
//        msgJson.put("gameId", gameId);
//        msgJson.put("status", game.getStatus());
//        msgJson.put("gameName", game.getName());
//        msgJson.put("startTime", startTime);
//        msgJson.put("endTime", endTime);
//        cacheMessage(NoticeEnum.MAINTAIN_INFO.getCode(), msgJson);
    }

    /**
     * 公告消息
     *
     * @param noticeId 消息ID
     */
    //  @Async
    public void writeNotification(Integer noticeId) {
        var notice = noticeServiceImpl.getById(noticeId);
        if (notice.getCategory() == 2 || notice.getCategory() == 1) {
            var msgJson = new JSONObject();
            msgJson.put("title", notice.getTitle());
            msgJson.put("content", notice.getContent());
            if (notice.getCategory() == 2) {
                msgJson.put("uid", notice.getUids());
            }
            cacheMessage(NOTIFICATION_MAP.get(notice.getCategory()), msgJson);
        }
    }

    /**
     * 消息格式
     *
     * @param action  消息头
     * @param mesJson 消息内容
     * @return JSONObject
     */
    private void cacheMessage(String action, JSONObject mesJson) {
        var reJson = new JSONObject();
        reJson.put("action", action);
        reJson.put("device", MessageDeviceEnum.D.getCode());
        reJson.put("message", mesJson);
        jedisUtil.leftPush(KeyConstant.WEB_SOCKET_QUEUE, reJson.toJSONString());
    }

    /**
     * @return void
     * @Description 金额变化消息
     * @Param [uid, Coin]
     **/
    @Async
    public void coinChange(Integer uid, BigDecimal coin) {
        var reJson = new JSONObject();
        reJson.put("action", Constant.COIN_CHANGE);
        reJson.put("device", MessageDeviceEnum.D.getCode());
        JSONObject mesJson = new JSONObject();
        mesJson.put("uid", uid);
        mesJson.put("coin", coin);
        reJson.put("message", mesJson);
        jedisUtil.leftPush(KeyConstant.WEB_SOCKET_QUEUE, reJson.toJSONString());
    }

    /**
     * @return void
     * @Description 流水变化
     * @Param [uid, coin]
     **/
    public void depositCoinChange(Integer uid, BigDecimal coin) {
        var reJson = new JSONObject();
        reJson.put("action", Constant.DEPOSIT_COIN_CHANGE);
        reJson.put("device", MessageDeviceEnum.D.getCode());
        JSONObject mesJson = new JSONObject();
        mesJson.put("uid", uid);
        mesJson.put("coin", coin);
        reJson.put("message", mesJson);
        jedisUtil.leftPush(KeyConstant.WEB_SOCKET_QUEUE, reJson.toJSONString());
    }

    /**
     * @return void
     * @Description 消息变化
     * @Param [uid, coin]
     **/
    public void noticeChange() {
        var reJson = new JSONObject();
        reJson.put("action", Constant.NOTICE_CHANGE);
        reJson.put("device", MessageDeviceEnum.D.getCode());
        reJson.put("message", new JSONObject());
        jedisUtil.leftPush(KeyConstant.WEB_SOCKET_QUEUE, reJson.toJSONString());
    }

    /**
     * @return void
     * @Description 保存系统消息
     * @Param [systemMessageEnum]
     **/
    public void saveSystemMessage(SystemMessageEnum systemMessageEnum, String coin, BigDecimal userCoin, Integer uid) {
        Notice notice = new Notice();
        Integer now = DateUtils.getCurrentTime();
        notice.setTitle(systemMessageEnum.getTitle());
        StringBuilder stringBuilder = new StringBuilder(systemMessageEnum.getContent());
        if (Objects.nonNull(coin)) {
            stringBuilder.append("#").append(coin);
        }
        if (Objects.nonNull(userCoin)) {
            String currency = configCache.getCurrency();
            stringBuilder.append("#").append(userCoin.setScale(2, RoundingMode.DOWN))
                    .append(currency);
        }
        notice.setContent(stringBuilder.toString());
        notice.setUids(uid.toString());
        notice.setCategory(3);
        notice.setStatus(1);
        notice.setCreatedAt(now);
        notice.setUpdatedAt(now);
        noticeServiceImpl.save(notice);
        jedisUtil.del(KeyConstant.NOTICE_LIST);
    }

}
