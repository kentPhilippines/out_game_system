package com.lt.win.service.base;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.lt.win.dao.generator.po.*;
import com.lt.win.dao.generator.service.*;
import com.lt.win.service.cache.redis.ConfigCache;
import com.lt.win.service.cache.redis.UserCache;
import com.lt.win.service.exception.BusinessException;
import com.lt.win.service.io.dto.AuditReturnParams;
import com.lt.win.utils.DateUtils;
import com.lt.win.utils.components.response.CodeInfo;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @Auther: wells
 * @Date: 2022/8/29 18:09
 * @Description: 打码量稽核
 */
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AuditCoinBase {
    private final CoinWithdrawalRecordService coinWithdrawalRecordServiceImpl;
    private final CodeAuditService codeAuditServiceImpl;
    private final CoinDepositRecordService coinDepositRecordServiceImpl;
    private final CoinRewardsService coinRewardsServiceImpl;
    private final UserCache userCache;
    private final CodeRecordsService codeRecordsServiceImpl;
    private final CoinAdminTransferService coinAdminTransferServiceImpl;
    private final ConfigCache configCache;
    private final BetStatisticBase betStatisticBase;


    /**
     * @return boolean
     * @Description 根据提款记录ID稽核
     * @Param [orderId]
     **/
    public boolean audit(Long id, Integer auditId) {
        //获取提款信息
        CoinWithdrawalRecord record = coinWithdrawalRecordServiceImpl.lambdaQuery()
                .eq(CoinWithdrawalRecord::getStatus, 0)
                .eq(CoinWithdrawalRecord::getId, id)
                .one();
        if (Objects.isNull(record)) {
            throw new BusinessException(CodeInfo.WITHDRAWAL_ORDER_ERROR);
        }
        Integer uid = record.getUid();
        var userInfo = userCache.getUserInfo(uid);
        String username = userInfo.getUsername();
        var auditReturnParams = checkWithdrawal(uid);
        var reFlag = auditReturnParams.isReFlag();
        Integer now = DateUtils.getCurrentTime();
        var codeAudit = new CodeAudit();
        codeAudit.setUid(uid);
        codeAudit.setUsername(username);
        codeAudit.setCodeRequire(auditReturnParams.getCodeRequire());
        codeAudit.setCodeReal(auditReturnParams.getCodeReal());
        codeAudit.setReferId(record.getId());
        codeAudit.setAuditId(auditId);
        codeAudit.setCreatedAt(now);
        codeAudit.setUpdatedAt(now);
        int status = 0;
        int withdrawalStatus;
        //稽核信息入库
        if (auditReturnParams.isReFlag()) {
            //稽核成功入库
            codeAudit.setStatus(1);
            status = 1;
            //稽核成功
            withdrawalStatus = 3;
        } else {
            //稽核失败入库
            codeAudit.setStatus(2);
            //提款失败
            withdrawalStatus = 2;
        }

        List<CodeRecords> codeRecordsList = new ArrayList<>();
        var coinDepositRecordList = auditReturnParams.getCoinDepositRecordList();
        Integer finalStatus = status;
        BigDecimal depositMultiple = configCache.getDepositMultiple();
        BigDecimal transferMultiple = configCache.getTransferMultiple();
        if (CollectionUtils.isNotEmpty(coinDepositRecordList)) {
            coinDepositRecordList.forEach(coinDepositRecord -> {
                //打码量入库
                var codeRecords = new CodeRecords();
                codeRecords.setUid(uid);
                codeRecords.setUsername(username);
                codeRecords.setCoin(coinDepositRecord.getRealAmount());
                codeRecords.setCodeRequire(coinDepositRecord.getRealAmount().multiply(depositMultiple).setScale(2, RoundingMode.DOWN));
                //打码量类型1：充值
                codeRecords.setCategory(1);
                codeRecords.setReferId(coinDepositRecord.getId());
                codeRecords.setStatus(finalStatus);
                codeRecords.setReferWithdrawalId(id);
                codeRecords.setCreatedAt(now);
                codeRecords.setUpdatedAt(now);
                codeRecordsList.add(codeRecords);
            });
        }
        var coinRewardsList = auditReturnParams.getCoinRewardsList();
        if (CollectionUtils.isNotEmpty(coinRewardsList)) {
            coinRewardsList.forEach(coinRewards -> {
                var codeRecords = new CodeRecords();
                codeRecords.setUid(uid);
                codeRecords.setUsername(username);
                codeRecords.setCoin(coinRewards.getCoin());
                codeRecords.setCodeRequire(coinRewards.getCodes());
                //打码量类型2： 活动
                codeRecords.setCategory(2);
                codeRecords.setReferId(coinRewards.getId());
                codeRecords.setStatus(finalStatus);
                codeRecords.setReferWithdrawalId(id);
                codeRecords.setCreatedAt(now);
                codeRecords.setUpdatedAt(now);
                codeRecordsList.add(codeRecords);
            });
        }
        List<CoinAdminTransfer> coinAdminTransferList = auditReturnParams.getCoinAdminTransferList();
        if (CollectionUtils.isNotEmpty(coinAdminTransferList)) {
            coinAdminTransferList.forEach(coinAdminTransfer -> {
                var codeRecords = new CodeRecords();
                codeRecords.setUid(uid);
                codeRecords.setUsername(username);
                codeRecords.setCoin(coinAdminTransfer.getCoin());
                codeRecords.setCodeRequire(coinAdminTransfer.getCoin().multiply(transferMultiple).setScale(2, RoundingMode.DOWN));
                //打码量类型2： 活动
                codeRecords.setCategory(2);
                codeRecords.setReferId(coinAdminTransfer.getId());
                codeRecords.setStatus(finalStatus);
                codeRecords.setReferWithdrawalId(id);
                codeRecords.setCreatedAt(now);
                codeRecords.setUpdatedAt(now);
                codeRecordsList.add(codeRecords);
            });
        }

        if (CollectionUtils.isNotEmpty(codeRecordsList)) {
            codeRecordsServiceImpl.saveBatch(codeRecordsList);
        }
        codeAuditServiceImpl.save(codeAudit);
        //更新提款表状态
        coinWithdrawalRecordServiceImpl.update(
                new LambdaUpdateWrapper<CoinWithdrawalRecord>()
                        .set(CoinWithdrawalRecord::getStatus, withdrawalStatus)
                        .set(CoinWithdrawalRecord::getAdminUid, auditId)
                        .set(CoinWithdrawalRecord::getUpdatedAt, DateUtils.getCurrentTime())
                        .eq(CoinWithdrawalRecord::getId, id));
        return reFlag;
    }

    /**
     * @return boolean
     * @Description 根据用户和币种稽核
     * @Param [uid, currency]
     **/
    public boolean audit(Integer uid) {
        AuditReturnParams auditReturnParams = checkWithdrawal(uid);
        return auditReturnParams.isReFlag();
    }

    public BigDecimal getNeedCodeCoin(Integer uid) {
        AuditReturnParams auditReturnParams = checkWithdrawal(uid);
        BigDecimal needCodeCoin = auditReturnParams.getCodeRequire().subtract(auditReturnParams.getCodeReal());
        return needCodeCoin.compareTo(BigDecimal.ZERO) > 0 ? needCodeCoin : BigDecimal.ZERO;
    }

    /**
     * @return boolean
     * @Description 根据用户和币种稽核
     * @Param [uid, currency]
     **/
    private AuditReturnParams checkWithdrawal(Integer uid) {
        var startTime = getStartTime(uid);
        BigDecimal depositMultiple = configCache.getDepositMultiple();
        BigDecimal transferMultiple = configCache.getTransferMultiple();
        //计算所需打码量 = 充值+活动
        List<CoinDepositRecord> coinDepositRecordList = coinDepositRecordServiceImpl.lambdaQuery()
                .eq(CoinDepositRecord::getUid, uid)
                .eq(CoinDepositRecord::getStatus, 1)
                .ge(CoinDepositRecord::getCreatedAt, startTime)
                .list();
        var depositRealAmountSum = coinDepositRecordList.stream()
                .map(CoinDepositRecord::getRealAmount)
                .reduce(BigDecimal::add).orElse(BigDecimal.ZERO);

        List<CoinRewards> coinRewardsList = coinRewardsServiceImpl.lambdaQuery()
                .eq(CoinRewards::getUid, uid)
                .eq(CoinRewards::getStatus, 2)
                .ge(CoinRewards::getUpdatedAt, startTime)
                .list();
        var rewardCoinSum = coinRewardsList.stream()
                .map(CoinRewards::getCodes)
                .reduce(BigDecimal::add).orElse(BigDecimal.ZERO);

        var coinAdminTransferList = coinAdminTransferServiceImpl.lambdaQuery()
                .eq(CoinAdminTransfer::getUid, uid)
                .ge(CoinAdminTransfer::getCreatedAt, startTime)
                .list();

        var transferSum = coinAdminTransferList.stream()
                .map(CoinAdminTransfer::getCoin)
                .reduce(BigDecimal::add).orElse(BigDecimal.ZERO);

        var mustCode = depositRealAmountSum.multiply(depositMultiple).setScale(2, RoundingMode.DOWN)
                .add(rewardCoinSum)
                .add(transferSum.multiply(transferMultiple).setScale(2, RoundingMode.DOWN));
        //实际打码量=用户游戏流水
        var userBetCoinSum = betStatisticBase.getValidBetCoinByUid(startTime, DateUtils.getCurrentTime(),uid);
        var reFlag = userBetCoinSum.compareTo(mustCode) >= 0;
        AuditReturnParams auditReturnParams = new AuditReturnParams();
        if (reFlag) {
            auditReturnParams.setCoinDepositRecordList(coinDepositRecordList);
            auditReturnParams.setCoinRewardsList(coinRewardsList);
            auditReturnParams.setCoinAdminTransferList(coinAdminTransferList);
        }
        auditReturnParams.setCodeReal(userBetCoinSum);
        auditReturnParams.setCodeRequire(mustCode);
        auditReturnParams.setReFlag(reFlag);
        return auditReturnParams;
    }


    private Integer getStartTime(Integer uid) {
        //获取用户的计算打码量时
        CodeAudit codeAudit = codeAuditServiceImpl.getOne(new LambdaQueryWrapper<CodeAudit>()
                .eq(CodeAudit::getUid, uid)
                .eq(CodeAudit::getStatus, 1)
                .orderByDesc(CodeAudit::getCreatedAt).last("limit 1"));
        Integer startTime = 0;
        if (Objects.nonNull(codeAudit)) {
            startTime = codeAudit.getCreatedAt();
        }
        return startTime;

    }

}
