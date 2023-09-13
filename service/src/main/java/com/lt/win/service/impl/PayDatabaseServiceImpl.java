package com.lt.win.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lt.win.dao.generator.po.*;
import com.lt.win.dao.generator.service.CoinDepositRecordService;
import com.lt.win.dao.generator.service.CoinWithdrawalRecordService;
import com.lt.win.dao.generator.service.UserWalletService;
import com.lt.win.service.base.ActivityServiceBase;
import com.lt.win.service.base.NoticeBase;
import com.lt.win.service.base.UserCoinBase;
import com.lt.win.service.cache.redis.PayConfigCache;
import com.lt.win.service.cache.redis.UserCache;
import com.lt.win.service.exception.BusinessException;
import com.lt.win.service.io.dto.DepositFlagAnaCount;
import com.lt.win.service.io.dto.UserCoinChangeParams;
import com.lt.win.service.io.enums.SystemMessageEnum;
import com.lt.win.utils.DateUtils;
import com.lt.win.utils.JedisUtil;
import com.lt.win.utils.components.response.CodeInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Objects;

import static com.lt.win.service.cache.KeyConstant.DEPOSIT_ORDER;
import static com.lt.win.service.common.Constant.CHANNEL_IN;
import static com.lt.win.service.common.Constant.CHANNEL_OUT;

/**
 * @Auther: wells
 * @Date: 2022/10/2 18:04
 * @Description:
 */
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PayDatabaseServiceImpl {
    private final CoinDepositRecordService coinDepositRecordServiceImpl;
    private final CoinWithdrawalRecordService coinWithdrawalRecordServiceImpl;
    private final UserCache userCache;
    private final PayConfigCache payConfigCache;
    private final UserWalletService userWalletServiceImpl;
    private final UserCoinBase userCoinBase;
    private final DepositAfterServiceImpl depositAfterServiceImpl;
    private final JedisUtil jedisUtil;
    private final NoticeBase noticeBase;
    private final ActivityServiceBase activityServiceBase;

    @Transactional(rollbackFor = Exception.class)
    public void withdrawUpdate(String orderNo, String amount, String sysOrderNo) {
        CoinWithdrawalRecord record = coinWithdrawalRecordServiceImpl.getOne(new LambdaQueryWrapper<CoinWithdrawalRecord>()
                .eq(CoinWithdrawalRecord::getOrderId, orderNo));
        if (Objects.isNull(record)) {
            throw new BusinessException(CodeInfo.RECORD_DOES_NOT_EXIST);
        }
        BigDecimal realAmount = new BigDecimal(amount);
        if (realAmount.intValue() != record.getRealAmount().intValue()) {
            throw new BusinessException(CodeInfo.WITHDRAWAL_AMOUNT_ABNORMAL);
        }
        coinWithdrawalRecordServiceImpl.lambdaUpdate()
                .set(CoinWithdrawalRecord::getStatus, 1)
                .set(Strings.isNotEmpty(sysOrderNo), CoinWithdrawalRecord::getPlatOrderId, sysOrderNo)
                .set(CoinWithdrawalRecord::getUpdatedAt, DateUtils.getCurrentTime())
                .eq(CoinWithdrawalRecord::getOrderId, orderNo)
                .update();
        //判断活动是否取消
        var userCoin = userCoinBase.getUserCoin(record.getUid());
        activityServiceBase.lastAddActivityCoin(record.getUid(), UserCoinChangeParams.FlowCategoryTypeEnum.WITHDRAWAL.getCode(), userCoin);
        //保存系统消息
        String realAmountStr = realAmount + record.getCurrency();
        noticeBase.saveSystemMessage(SystemMessageEnum.WITHDRAWAL_SUCCESS, realAmountStr, record.getCoinBefore().subtract(realAmount), record.getUid());

    }

    /**
     * @Description PIX 充值记录入库
     * @Param [uid, amount, orderNo]
     **/
    public void depositSave(String code, Integer uid, String amount, String orderNo, String url, String platOrderId) {
        Integer now = DateUtils.getCurrentTime();
        User userInfo = userCache.getUserInfo(uid);
        BigDecimal payAmount = new BigDecimal(amount);
        PayChannel payChannel = payConfigCache.getPayChannel(code, CHANNEL_IN);
        UserWallet userWallet = userWalletServiceImpl.lambdaQuery()
                .eq(UserWallet::getId, uid)
                .one();
        CoinDepositRecord coinDepositRecord = new CoinDepositRecord();
        coinDepositRecord.setOrderId(orderNo);
        coinDepositRecord.setUid(uid);
        coinDepositRecord.setUsername(userInfo.getUsername());
        coinDepositRecord.setCode(code);
        coinDepositRecord.setCategoryCurrency(payChannel.getCategoryCurrency());
        coinDepositRecord.setCategoryTransfer(payChannel.getCategoryTransfer());
        coinDepositRecord.setPlatName(payChannel.getName());
        coinDepositRecord.setCoinBefore(userWallet.getCoin());
        coinDepositRecord.setPayAddress(url);
        coinDepositRecord.setPlatOrderId(platOrderId);
        coinDepositRecord.setPayAmount(payAmount);
        coinDepositRecord.setExchangeRate(BigDecimal.ONE);
        coinDepositRecord.setRealAmount(BigDecimal.ZERO);
        coinDepositRecord.setCurrency(payChannel.getCurrency());
        //充值标识
        coinDepositRecord.setDepStatus(9);
        coinDepositRecord.setStatus(0);
        coinDepositRecord.setCreatedAt(now);
        coinDepositRecord.setUpdatedAt(now);
        coinDepositRecordServiceImpl.saveOrUpdate(coinDepositRecord);
    }

    public void depositUpdate(String orderNo) {
        depositUpdate(orderNo, null, null);
    }

    @Transactional(rollbackFor = Exception.class)
    public void depositUpdate(String orderNo, BigDecimal amount, Integer adminUid) {
        CoinDepositRecord coinDepositRecord = coinDepositRecordServiceImpl.getOne(new LambdaQueryWrapper<CoinDepositRecord>()
                .eq(CoinDepositRecord::getOrderId, orderNo));
        if (Objects.isNull(coinDepositRecord)) {
            throw new BusinessException(CodeInfo.RECORD_DOES_NOT_EXIST);
        }
        Integer uid = coinDepositRecord.getUid();
        jedisUtil.del(DEPOSIT_ORDER + ":" + uid + ":" + coinDepositRecord.getCode());
        //更新充值记录
        PayChannel payChannel = payConfigCache.getPayChannel(coinDepositRecord.getCode(), CHANNEL_IN);
        BigDecimal relAmount = coinDepositRecord.getPayAmount();
        coinDepositRecord.setRealAmount(coinDepositRecord.getPayAmount());
        if (Objects.nonNull(amount)) {
            relAmount = amount;
            coinDepositRecord.setRealAmount(amount);
        }
        if (Objects.nonNull(adminUid)) {
            coinDepositRecord.setAdminUid(adminUid);
        }
        coinDepositRecord.setStatus(1);
        coinDepositRecord.setUpdatedAt(DateUtils.getCurrentTime());
        //充值标识
        DepositFlagAnaCount depositFlagAnaCount = depositAfterServiceImpl.getDepStatus(uid);
        coinDepositRecord.setDepStatus(depositFlagAnaCount.getDepositFlag());
        coinDepositRecordServiceImpl.updateById(coinDepositRecord);
        //充值用户加金额
        UserCoinChangeParams.UserCoinChangeReq userCoinChangeReq = new UserCoinChangeParams.UserCoinChangeReq();
        userCoinChangeReq.setUid(uid);
        userCoinChangeReq.setCoin(relAmount);
        userCoinChangeReq.setCategory(1);
        userCoinChangeReq.setOutIn(1);
        userCoinChangeReq.setReferId(coinDepositRecord.getId());
        userCoinBase.userCoinChange(userCoinChangeReq);
        //保存系统消息
        String relAmountStr = relAmount + payChannel.getCurrency();
        noticeBase.saveSystemMessage(SystemMessageEnum.DEPOSIT_SUCCESS, relAmountStr, coinDepositRecord.getCoinBefore().add(relAmount), uid);
        //活动首充与续充触发
        depositAfterServiceImpl.depositActivity(uid, relAmount, depositFlagAnaCount);
        //vip升级触发
        depositAfterServiceImpl.checkUserVip(uid, relAmount);
    }

    /**
     * @Description 更新提款表code
     * @Param [orderNo, code, platOrderId]
     **/
    @Transactional(rollbackFor = Exception.class)
    public void updateWithdrawalCode(String orderNo, String code, String platOrderId) {
        PayChannel payChannel = payConfigCache.getPayChannel(code, CHANNEL_OUT);
        PayPlatConfig payPlatConfig = payConfigCache.getPayPlatConfig(payChannel.getPlatId());
        coinWithdrawalRecordServiceImpl.lambdaUpdate()
                .set(Strings.isNotEmpty(platOrderId), CoinWithdrawalRecord::getPlatOrderId, platOrderId)
                .set(CoinWithdrawalRecord::getCode, code)
                .set(CoinWithdrawalRecord::getPlatName, payPlatConfig.getPlatName())
                .set(CoinWithdrawalRecord::getStatus, 4)
                .eq(CoinWithdrawalRecord::getOrderId, orderNo)
                .update();
    }

}
