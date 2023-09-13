package com.lt.win.backend.service.impl;

import com.lt.win.backend.service.DailyReportService;
import com.lt.win.dao.generator.po.CoinCommission;
import com.lt.win.dao.generator.po.User;
import com.lt.win.dao.generator.service.UserService;
import com.lt.win.service.base.CoinLogStatisticBase;
import com.lt.win.service.base.CommissionBase;
import com.lt.win.service.base.UserCoinBase;
import com.lt.win.service.cache.redis.AgentCache;
import com.lt.win.service.io.constant.ConstData;
import com.lt.win.utils.DateNewUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;


/**
 * 每日定时任务执行计划
 *
 * @author david
 */
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class DailyReportServiceImpl implements DailyReportService {
    private final CommissionBase commissionBase;
    private final UserService userServiceImpl;
    private final CoinLogStatisticBase coinLogStatisticBase;
    private final UserCoinBase userCoinBase;
    private final AgentCache agentCache;


    /**
     * 代理佣金(定时发放)
     */
    @Override
    public void agentCommission() {
        var startTime = DateNewUtils.yesterdayStart();
        var endTime = DateNewUtils.yesterdayEnd();
        var betRecords = coinLogStatisticBase.getValidBetCoinMap(startTime, endTime);
        betRecords.forEach((key, value) -> setCommissionCoin(key, value, 0));
    }

    /**
     * 代理佣金发放
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     */
    @Override
    public void agentCommission(Integer startTime, Integer endTime) {
        var betRecords = coinLogStatisticBase.getValidBetCoinMap(startTime, endTime);
        betRecords.forEach((key, value) -> setCommissionCoin(key, value, 0));
    }

    /**
     * 递归设置代理佣金分润
     *
     * @param subUid       下级ID
     * @param coin         投注金额
     * @param recursionNum 递归次数(默认递归六次)
     */
    private void setCommissionCoin(Integer subUid, @NotNull BigDecimal coin, Integer recursionNum) {
        var user = userServiceImpl.getById(subUid);
        if (recursionNum >= ConstData.AGENT_TOTAL_LEVEL) {
            return;
        } else {
            recursionNum += 1;
        }

        // 获取佣金费率配置
        var rate = agentCache.commission(recursionNum);
        if (rate.compareTo(BigDecimal.ZERO) <= 0) {
            return;
        }

        var agent = userServiceImpl.lambdaQuery()
                .eq(recursionNum == 1, User::getId, user.getSupUid1())
                .eq(recursionNum == 2, User::getId, user.getSupUid2())
                .eq(recursionNum == 3, User::getId, user.getSupUid3())
                .eq(recursionNum == 4, User::getId, user.getSupUid4())
                .eq(recursionNum == 5, User::getId, user.getSupUid5())
                .eq(recursionNum == 6, User::getId, user.getSupUid6())
                .one();
        if (Optional.ofNullable(agent).isEmpty() || agent.getSupLevelTop() == ConstData.ZERO) {
            return;
        }

        var date = DateNewUtils.yesterday();
        var now = DateNewUtils.now();
        var commission = new CoinCommission();
        commission.setUid(agent.getId());
        commission.setUsername(agent.getUsername());
        commission.setAgentLevel(recursionNum);
        commission.setRiqi(Integer.parseInt(date));
        commission.setCoin(coin.multiply(rate));
        commission.setRate(rate);
        commission.setSubUid(subUid);
        commission.setSubUsername(user.getUsername());
        commission.setSubBetTrunover(coin);
        commission.setCoinBefore(userCoinBase.getUserCoin(agent.getId()));
        commission.setCreatedAt(now);
        commission.setUpdatedAt(now);

        commissionBase.setCommission(commission);

        // 递归设置代理佣金
        setCommissionCoin(subUid, coin, recursionNum);
    }
}
