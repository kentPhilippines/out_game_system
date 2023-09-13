package com.lt.win.service.impl;

import cn.hutool.core.util.EnumUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.baomidou.lock.annotation.Lock4j;
import com.lt.win.dao.generator.po.GameSlot;
import com.lt.win.dao.generator.po.UserWallet;
import com.lt.win.dao.generator.service.GameListService;
import com.lt.win.dao.generator.service.GameSlotService;
import com.lt.win.service.base.UserCoinBase;
import com.lt.win.service.cache.redis.ConfigCache;
import com.lt.win.service.cache.redis.GameSlotCache;
import com.lt.win.service.common.DigiSportErrorEnum;
import com.lt.win.service.exception.BusinessException;
import com.lt.win.service.exception.DigiSportException;
import com.lt.win.service.io.bo.MemberBalanceBo;
import com.lt.win.service.io.dto.BaseParams;
import com.lt.win.service.io.dto.Betslips;
import com.lt.win.service.io.dto.UserCoinChangeParams;
import com.lt.win.service.io.enums.CurrencyEm;
import com.lt.win.service.io.enums.StatusEnum;
import com.lt.win.service.server.IGameCallbackService;
import com.lt.win.utils.DateNewUtils;
import com.lt.win.utils.DateUtils;
import com.lt.win.utils.components.response.CodeInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * TODO
 *
 * @author fangzs
 * @date 2022/8/11 21:53
 */
@Slf4j
@Service
public class GameCallbackServiceImpl implements IGameCallbackService {
    @Autowired
    UserCoinBase userCoinBase;
    @Autowired
    GameSlotService gameSlotServiceImpl;
    @Autowired
    GameListService gameListServiceImpl;
    @Autowired
    BetslipsServiceImpl betslipsServiceImpl;
    @Autowired
    private ConfigCache configCache;
    @Resource
    private GameSlotCache gameSlotCache;

    @Override
    public MemberBalanceBo getBalance(BaseParams.HeaderInfo user, String currencyType) {
        MemberBalanceBo bo = new MemberBalanceBo();

        bo.setBalance(userCoinBase.getUserCoin(user.getId()));
        Map<String, CurrencyEm> map = EnumUtil.getEnumMap(CurrencyEm.class);
        bo.setCountry(map.get(currencyType).getCountry());
        bo.setCurrency(map.get(currencyType).getValue());
        return bo;
    }

    @Override
    public MemberBalanceBo getBalance(Integer userId, String currencyType) {
        MemberBalanceBo bo = new MemberBalanceBo();
        UserWallet userWallet = userCoinBase.getUserWallet(userId);
        bo.setBalance(userWallet.getCoin());
        bo.setVersion(userWallet.getVersion());
        Map<String, CurrencyEm> map = EnumUtil.getEnumMap(CurrencyEm.class);
        bo.setCountry(map.get(currencyType).getCountry());
        bo.setCurrency(map.get(currencyType).getValue());
        return bo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @Lock4j(keys = {"#betslips.xbUid", "#betslips.roundId", "#betslips.gameId"}, expire = 60000, acquireTimeout = 1000)
    public MemberBalanceBo bet(Betslips betslips) throws Exception {
        if (!betslips.isBonus()) {
            boolean flag = userCoinBase.userCoinChange(
                    new UserCoinChangeParams.UserCoinChangeReq()
                            .setUid(betslips.getXbUid())
                            .setOutIn(0)
                            .setCoin(betslips.getStake())
                            .setPlatId(betslips.getGamePlatId())
                            .setGameId(betslips.getGameId())
                            .setReferId(betslips.getId())
                            .setCategory(UserCoinChangeParams.FlowCategoryTypeEnum.BET.getCode())
            );
            if (!flag) {
                throw new BusinessException(CodeInfo.ACTIVE_BET_COIN_NO_ENOUGH);
            }
        }
        UserWallet userWallet = userCoinBase.getUserWallet(betslips.getXbUid());
        var gameSlot = gameSlotCache.one(betslips.getGameTypeId(), betslips.getGamePlatId());
        Assert.notNull(gameSlot, "游戏id错误");
        betslips.setGameId(gameSlot.getGameId());
        betslips.setCoinBefore(userCoinBase.getUserCoin(betslips.getXbUid()));
        betslips.setGameGroupId(gameListServiceImpl.queryGameListById(gameSlot.getGameId()).getGroupId());
        betslips.setGameName(gameSlot.getName());
        betslips.setCreateTimeStr(DateUtils.getYyyyMMddStr());
        betslips.setCoinBefore(betslips.isBonus() ? userWallet.getCoin() : userWallet.getCoin().add(betslips.getStake()));
        if (!betslipsServiceImpl.saveBetslipsByRoundId(betslips)) {
            throw new BusinessException(CodeInfo.BETSLIPS_REPETITION_ERROR);
        }

        MemberBalanceBo bo = new MemberBalanceBo();
        Map<String, CurrencyEm> map = EnumUtil.getEnumMap(CurrencyEm.class);
        bo.setCountry(map.get(configCache.getCurrency()).getCountry());
        bo.setCurrency(map.get(configCache.getCurrency()).getValue());
        bo.setBalance(userWallet.getCoin());
        bo.setVersion(userWallet.getVersion());
        return bo;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    @Lock4j(keys = {"#betslips.xbUid", "#betslips.roundId", "#betslips.gameId"}, expire = 60000, acquireTimeout = 1000)
    public MemberBalanceBo betByDiGi(Betslips betslips) throws Exception {
        MemberBalanceBo bo = new MemberBalanceBo();
        Map<String, CurrencyEm> map = EnumUtil.getEnumMap(CurrencyEm.class);
        bo.setCountry(map.get(configCache.getCurrency()).getCountry());
        bo.setCurrency(map.get(configCache.getCurrency()).getValue());
        if (!betslipsServiceImpl.saveBetslipsByParam(betslips)) {
            throw new DigiSportException(DigiSportErrorEnum.InternalServerError);
        }
        try {
            log.info("注单保存信息入参：{},保存成功。", betslips.getId());
            boolean flag = userCoinBase.userCoinChange(
                    new UserCoinChangeParams.UserCoinChangeReq()
                            .setUid(betslips.getXbUid())
                            .setOutIn(0)
                            .setCoin(betslips.getStake())
                            .setReferId(betslips.getId())
                            .setPlatId(betslips.getGamePlatId())
                            .setGameId(betslips.getGameId())
                            .setCategory(UserCoinChangeParams.FlowCategoryTypeEnum.BET.getCode())
            );
            if (!flag) {
                throw new DigiSportException(DigiSportErrorEnum.LowBalance);
            }
        } catch (BusinessException b) {
            throw new DigiSportException(DigiSportErrorEnum.LowBalance);
        }
        bo.setBalance(userCoinBase.getUserCoin(betslips.getXbUid()));
        return bo;
    }

    @Override
    public MemberBalanceBo betWin(Betslips betslips) throws Exception {
        MemberBalanceBo bo = new MemberBalanceBo();
        Map<String, CurrencyEm> map = EnumUtil.getEnumMap(CurrencyEm.class);
        bo.setCountry(map.get(configCache.getConfigByTitle("mainCurrency")).getCountry());
        bo.setCurrency(map.get(configCache.getConfigByTitle("mainCurrency")).getValue());
        GameSlot gameSlot = gameSlotCache.one(betslips.getGameTypeId(), betslips.getGamePlatId());
        betslips.setGameGroupId(gameListServiceImpl.queryGameListById(gameSlot.getGameId()).getGroupId());
        betslips.setCoinBefore(userCoinBase.getUserCoin(betslips.getXbUid()));
        betslips.setGameId(gameSlot.getGameId());
        betslips.setCreateTimeStr(DateUtils.getYyyyMMddStr());
        betslips.setGameName(gameSlot.getName());
        if (betslips.getTransactionId().equals(betslips.getRoundId())) {
            if (!betslipsServiceImpl.saveBetslipsByRoundId(betslips)) {
                throw new BusinessException(CodeInfo.ACTIVE_BET_COIN_NO_ENOUGH);
            }
        } else {
            if (!betslipsServiceImpl.saveBetslipsByTransactionId(betslips)) {
                throw new BusinessException(CodeInfo.ACTIVE_BET_COIN_NO_ENOUGH);
            }
        }
        boolean flag = true;
        if (!betslips.isBonus()) {
            flag = userCoinBase.userCoinChange(
                    new UserCoinChangeParams.UserCoinChangeReq()
                            .setUid(betslips.getXbUid())
                            .setOutIn(0)
                            .setCoin(betslips.getStake())
                            .setReferId(betslips.getId())
                            .setPlatId(betslips.getGamePlatId())
                            .setGameId(betslips.getGameId())
                            .setCategory(UserCoinChangeParams.FlowCategoryTypeEnum.BET.getCode())
            );
        }
        if (!flag) {
            throw new BusinessException(CodeInfo.ACTIVE_BET_COIN_NO_ENOUGH);
        }
        if (NumberUtil.isGreater(betslips.getPayout(), BigDecimal.ZERO)) {
            flag = userCoinBase.userCoinChange(
                    new UserCoinChangeParams.UserCoinChangeReq()
                            .setUid(betslips.getXbUid())
                            .setOutIn(1)
                            .setCoin(betslips.getPayout())
                            .setReferId(betslips.getId())
                            .setPlatId(betslips.getGamePlatId())
                            .setGameId(betslips.getGameId())
                            .setCategory(UserCoinChangeParams.FlowCategoryTypeEnum.DRAW.getCode())
            );
            if (!flag) {
                throw new BusinessException(CodeInfo.ACTIVE_BET_COIN_NO_ENOUGH);
            }
        }
        UserWallet userWallet = userCoinBase.getUserWallet(betslips.getXbUid());
        bo.setBalance(userWallet.getCoin());
        bo.setVersion(userWallet.getVersion());
        return bo;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    @Lock4j(keys = {"#betslips.xbUid", "#betslips.transactionId", "#betslips.gameId"}, expire = 60000, acquireTimeout = 1000)
    public UserWallet betByTransactionId(Betslips betslips) throws Exception {
        boolean flag = userCoinBase.userCoinChange(
                new UserCoinChangeParams.UserCoinChangeReq()
                        .setUid(betslips.getXbUid())
                        .setOutIn(0)
                        .setCoin(betslips.getStake())
                        .setReferId(betslips.getId())
                        .setPlatId(betslips.getGamePlatId())
                        .setGameId(betslips.getGameId())
                        .setCategory(UserCoinChangeParams.FlowCategoryTypeEnum.BET.getCode())
        );
        if (!flag) {
            throw new BusinessException(CodeInfo.ACTIVE_BET_COIN_NO_ENOUGH);
        }
        var bo = userCoinBase.getUserWallet(betslips.getXbUid());

        var now = DateNewUtils.now();
        var slot = gameSlotCache.one(betslips.getGameTypeId(), betslips.getGamePlatId());
        betslips.setGameTypeId(slot.getId());
        betslips.setGameName(slot.getName());
        betslips.setGameId(slot.getGameId());
        betslips.setGameGroupId(slot.getGameGroupId());
        betslips.setCreateTimeStr(DateUtils.getYyyyMMddStr());
        betslips.setCoinBefore(bo.getCoin().add(betslips.getStake()));
        betslips.setDtStarted(now);
        betslips.setCreatedAt(now);
        betslips.setUpdatedAt(now);
        if (!betslipsServiceImpl.saveBetslipsByTransactionId(betslips)) {
            throw new BusinessException(CodeInfo.ACTIVE_BET_COIN_NO_ENOUGH);
        }
        return bo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<Betslips> betBatchMysql(List<Betslips> list) {
        //不走es，不走流水
        for (Betslips betslips : list) {
            betslips.setResult(betslipsServiceImpl.saveBetslipsMysql(betslips));
        }
        return list;
    }


    /**
     * 批量注单
     * 快速游戏使用
     *
     * @param list 注单参数
     * @throws Exception 异常
     */
    @Override
    public void betBatchEs(List<Betslips> list) throws Exception {
        betslipsServiceImpl.saveBetslipsEs(list);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<Betslips> updateBatchByBetIdForMysql(List<Betslips> list) {
        for (Betslips betslips : list) {
            betslips.setResult(betslipsServiceImpl.updateByBetIdForMysql(betslips));
        }
        return list;
    }

    @Override
    public boolean updateBatchByBetIdForEs(List<Betslips> list) throws Exception {
        for (Betslips betslips : list) {
            boolean flag = userCoinBase.userCoinChange(
                    new UserCoinChangeParams.UserCoinChangeReq()
                            .setUid(betslips.getXbUid())
                            .setOutIn(1)
                            .setExternalTxId(betslips.getGamePlatId() + "-" + betslips.getWinTransactionId())
                            .setCoin(betslips.getPayout())
                            .setReferId(betslips.getId())
                            .setPlatId(betslips.getGamePlatId())
                            .setGameId(betslips.getGameId())
                            .setCategory(UserCoinChangeParams.FlowCategoryTypeEnum.DRAW.getCode())
            );
            if (!flag) {
                throw new BusinessException(CodeInfo.ACTIVE_BET_COIN_NO_ENOUGH);
            }
        }
        return betslipsServiceImpl.updateByBetIdForEs(list);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @Lock4j(keys = {"#betslips.xbUid", "#betslips.roundId", "#betslips.gameId"}, expire = 60000, acquireTimeout = 1000)
    public MemberBalanceBo creditByRoundId(Betslips betslips, BigDecimal amount) throws Exception {
        return credit(betslips, amount, 1);
    }

    /**
     * 开彩方法
     *
     * @param betslips 投注
     * @param type     1: 基于roundId, 2:基于transactionId
     * @return 会员信息
     */
    private MemberBalanceBo credit(Betslips betslips, BigDecimal amount, int type) throws Exception {
        JSONArray jsonArray = Optional.ofNullable(JSON.parseArray(betslips.getRewardJson())).orElse(new JSONArray());
        int creditNum = jsonArray.size();
        boolean flag = userCoinBase.userCoinChange(
                new UserCoinChangeParams.UserCoinChangeReq()
                        .setUid(betslips.getXbUid())
                        .setOutIn(1)
                        .setCoin(amount)
                        .setReferId(creditNum < 2 ? betslips.getId() : Long.valueOf(betslips.getId() + String.valueOf(creditNum)))
                        .setPlatId(betslips.getGamePlatId())
                        .setGameId(betslips.getGameId())
                        .setCategory(UserCoinChangeParams.FlowCategoryTypeEnum.DRAW.getCode())
        );
        if (!flag) {
            throw new BusinessException(CodeInfo.ACTIVE_BET_COIN_NO_ENOUGH);
        }

        betslips.setUpdatedAt(DateNewUtils.now());
        if (!betslipsServiceImpl.updateByBetId(betslips, type)) {
            throw new BusinessException(CodeInfo.ACTIVE_BET_COIN_NO_ENOUGH);
        }
        UserWallet userWallet = userCoinBase.getUserWallet(betslips.getXbUid());

        MemberBalanceBo bo = new MemberBalanceBo();
        Map<String, CurrencyEm> map = EnumUtil.getEnumMap(CurrencyEm.class);
        var currency = configCache.getCurrency();
        bo.setCountry(map.get(currency).getCountry());
        bo.setCurrency(map.get(currency).getValue());
        bo.setBalance(userWallet.getCoin());
        bo.setVersion(userWallet.getVersion());
        return bo;
    }


    /**
     * @param betslips 体育开奖
     * @param type     1: 基于roundId  2: 基于transactionId
     * @return 成功失败
     * @throws Exception ex
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean debitByBatch(Betslips betslips, Integer outIn, int type, BigDecimal amount) throws Exception {
        JSONArray jsonArray = Optional.ofNullable(JSON.parseArray(betslips.getRewardJson())).orElse(new JSONArray());
        int creditNum = jsonArray.size();
        if (!userCoinBase.userCoinChange(
                new UserCoinChangeParams.UserCoinChangeReq()
                        .setUid(betslips.getXbUid())
                        .setOutIn(outIn)
                        .setCoin(amount)
                        .setReferId(creditNum < 2 ? betslips.getId() : Long.valueOf(betslips.getId() + String.valueOf(creditNum)))
                        .setPlatId(betslips.getGamePlatId())
                        .setGameId(betslips.getGameId())
                        .setCategory(UserCoinChangeParams.FlowCategoryTypeEnum.DRAW.getCode())
        )) {
            throw new BusinessException(CodeInfo.ACTIVE_BET_COIN_NO_ENOUGH);
        }
        log.info("用户流水更新完毕==>{}", creditNum);
        return betslipsServiceImpl.updateSportById(betslips);
    }


    /**
     * 开彩方法
     *
     * @param betslips 投注
     * @param amount   投注金额
     * @return 会员信息
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public MemberBalanceBo bet(Betslips betslips, BigDecimal amount) throws Exception {
        MemberBalanceBo bo = new MemberBalanceBo();
        Map<String, CurrencyEm> map = EnumUtil.getEnumMap(CurrencyEm.class);
        String currency = configCache.getConfigByTitle("mainCurrency");
        bo.setCountry(map.get(currency).getCountry());
        bo.setCurrency(currency);
        betslips.setCoinBefore(userCoinBase.getUserCoin(betslips.getXbUid()));
        JSONArray jsonArray = Optional.ofNullable(JSON.parseArray(betslips.getBetJson())).orElse(new JSONArray());
        int creditNum = jsonArray.size();
        if (NumberUtil.isGreater(amount, BigDecimal.ZERO)) {
            boolean flag = userCoinBase.userCoinChange(
                    new UserCoinChangeParams.UserCoinChangeReq()
                            .setUid(betslips.getXbUid())
                            .setOutIn(0)
                            .setCoin(amount)
                            .setReferId(creditNum < 2 ? betslips.getId() : Long.valueOf(betslips.getId() + String.valueOf(creditNum)))
                            .setPlatId(betslips.getGamePlatId())
                            .setGameId(betslips.getGameId())
                            .setCategory(UserCoinChangeParams.FlowCategoryTypeEnum.BET.getCode())
            );
            if (!flag) {
                throw new BusinessException(CodeInfo.ACTIVE_BET_COIN_NO_ENOUGH);
            }
        }
        if (!betslipsServiceImpl.updateByBetId(betslips, 1)) {
            throw new BusinessException(CodeInfo.ACTIVE_BET_COIN_NO_ENOUGH);
        }
        UserWallet userWallet = userCoinBase.getUserWallet(betslips.getXbUid());
        bo.setBalance(userWallet.getCoin());
        bo.setVersion(userWallet.getVersion());
        return bo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @Lock4j(keys = {"#betslips.xbUid", "#betslips.transactionId", "#betslips.gameId"}, expire = 60000, acquireTimeout = 1000)
    public MemberBalanceBo creditByTransactionId(Betslips betslips, BigDecimal amount) throws Exception {
        return credit(betslips, amount, 2);
    }

    /**
     * 处理体育退款
     *
     * @return res
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    @Lock4j(keys = {"#betslips.xbUid", "#betslips.roundId", "#betslips.gameId"}, expire = 60000, acquireTimeout = 1000)
    public boolean refund(Betslips betslips) throws Exception {
        //退款
        betslips.setXbStatus(3);
        betslips.setValidStake(BigDecimal.ZERO);
        betslipsServiceImpl.updateByBetId(betslips, 1);
        boolean flag = userCoinBase.userCoinChange(
                new UserCoinChangeParams.UserCoinChangeReq()
                        .setUid(betslips.getXbUid())
                        .setOutIn(1)
                        .setCoin(betslips.getStake())
                        .setReferId(betslips.getId())
                        .setPlatId(betslips.getGamePlatId())
                        .setGameId(betslips.getGameId())
                        .setCategory(UserCoinChangeParams.FlowCategoryTypeEnum.REFUND.getCode())
        );
        log.info("退款订单入参{}，退款状态{} ", JSONUtil.toJsonStr(betslips), flag);
        if (!flag) {
            throw new DigiSportException(DigiSportErrorEnum.InternalServerError);
        }
        return true;
    }

    /**
     * 处理三方退款
     *
     * @param betslips 对象
     * @return res
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    @Lock4j(keys = {"#betslips.xbUid", "#betslips.roundId", "#betslips.gameId"}, expire = 60000, acquireTimeout = 1000)
    public UserWallet refundByRoundId(Betslips betslips, BigDecimal coin) {
        boolean flag = userCoinBase.userCoinChange(
                new UserCoinChangeParams.UserCoinChangeReq()
                        .setUid(betslips.getXbUid())
                        .setOutIn(1)
                        .setCoin(coin)
                        .setReferId(betslips.getId())
                        .setPlatId(betslips.getGamePlatId())
                        .setGameId(betslips.getGameId())
                        .setCategory(UserCoinChangeParams.FlowCategoryTypeEnum.REFUND.getCode())
        );
        if (!flag) {
            throw new BusinessException(CodeInfo.UPDATE_EXCEPTION);
        }

        betslips.setValidStake(BigDecimal.ZERO);
        betslips.setXbStatus(StatusEnum.BET_STATUS.REFUND.getCode());
        betslips.setUpdatedAt(DateNewUtils.now());
        try {
            if (!betslipsServiceImpl.updateByBetId(betslips, 1)) {
                throw new BusinessException(CodeInfo.UPDATE_EXCEPTION);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return userCoinBase.getUserWallet(betslips.getXbUid());
    }

    /**
     * 处理三方退款
     *
     * @param betslips 对象
     * @return res
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    @Lock4j(keys = {"#betslips.xbUid", "#betslips.transactionId", "#betslips.gameId"}, expire = 60000, acquireTimeout = 1000)
    public UserWallet refundByTransactionId(Betslips betslips, BigDecimal coin) {
        String last = "";
        try {
            Object object = JSON.parse(betslips.getRefundJson());
            if (object instanceof JSONArray) {
                JSONArray jsonArray = (JSONArray) object;
                last = String.valueOf(jsonArray.size());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        boolean flag = userCoinBase.userCoinChange(
                new UserCoinChangeParams.UserCoinChangeReq()
                        .setUid(betslips.getXbUid())
                        .setOutIn(1)
                        .setCoin(coin)
                        .setReferId(Long.valueOf(betslips.getId() + last))
                        .setPlatId(betslips.getGamePlatId())
                        .setGameId(betslips.getGameId())
                        .setCategory(UserCoinChangeParams.FlowCategoryTypeEnum.REFUND.getCode())
        );
        if (!flag) {
            throw new BusinessException(CodeInfo.UPDATE_EXCEPTION);
        }

        betslips.setUpdatedAt(DateNewUtils.now());
        try {
            if (!betslipsServiceImpl.updateByBetId(betslips, 2)) {
                throw new BusinessException(CodeInfo.UPDATE_EXCEPTION);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return userCoinBase.getUserWallet(betslips.getXbUid());
    }

    /**
     * 直接派发免费奖金(老虎机场景) - 没有投注对应的订单号
     *
     * @param betslips 对象
     * @param amount   开彩金额
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    @Lock4j(keys = {"#betslips.transactionId", "#betslips.gameId", "#betslips.xbUid"}, expire = 60000, acquireTimeout = 1000)
    public UserWallet bonus(Betslips betslips, BigDecimal amount) {
        if (betslips.getPayout().compareTo(BigDecimal.ZERO) > 0) {
            boolean flag = userCoinBase.userCoinChange(
                    new UserCoinChangeParams.UserCoinChangeReq()
                            .setUid(betslips.getXbUid())
                            .setOutIn(1)
                            .setCoin(amount)
                            .setReferId(betslips.getId())
                            .setPlatId(betslips.getGamePlatId())
                            .setGameId(betslips.getGameId())
                            .setCategory(UserCoinChangeParams.FlowCategoryTypeEnum.DRAW.getCode())
            );
            if (!flag) {
                throw new BusinessException(CodeInfo.ACTIVE_BET_COIN_NO_ENOUGH);
            }
        }

        // 获取三方游戏信息
        GameSlot gameSlot = gameSlotCache.one(betslips.getGameTypeId(), betslips.getGamePlatId());
        betslips.setGameGroupId(gameSlot.getGameGroupId());
        betslips.setGameId(gameSlot.getGameId());
        betslips.setGameName(gameSlot.getName());
        betslips.setCreateTimeStr(DateUtils.getYyyyMMddStr());
        try {
            if (!betslipsServiceImpl.saveBetslipsByTransactionId(betslips)) {
                throw new BusinessException(CodeInfo.UPDATE_EXCEPTION);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return userCoinBase.getUserWallet(betslips.getXbUid());
    }
}
