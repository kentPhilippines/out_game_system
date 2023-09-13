package com.lt.win.service.base;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lt.win.dao.generator.po.CoinDepositRecord;
import com.lt.win.dao.generator.po.CoinWithdrawalRecord;
import com.lt.win.dao.generator.po.UserWallet;
import com.lt.win.dao.generator.service.CoinDepositRecordService;
import com.lt.win.dao.generator.service.CoinWithdrawalRecordService;
import com.lt.win.dao.generator.service.UserWalletService;
import com.lt.win.service.io.bo.QueryBo;
import com.lt.win.service.io.bo.SuperAgentBo;
import com.lt.win.service.io.constant.ConstData;
import com.lt.win.service.io.constant.ConstSql;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 代理相关公用方法
 *
 * @author david
 * @since 2022/11/10 00:09
 */
@Slf4j
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AgentBase {
    private final UserWalletService userWalletServiceImpl;
    private final CoinLogStatisticBase coinLogStatisticBase;
    private final CoinDepositRecordService coinDepositRecordServiceImpl;
    private final CoinWithdrawalRecordService coinWithdrawalRecordServiceImpl;

    /**
     * 计算团队账户总余额
     *
     * @param uid uid集合
     * @return 余额
     */
    public BigDecimal calcTeamBalance(@NotNull List<Integer> uid) {
        return uid.isEmpty() ? BigDecimal.ZERO : (BigDecimal) userWalletServiceImpl.getMap(
                new QueryWrapper<UserWallet>().select("ifNull(SUM(coin), 0) as coin")
                        .in("id", uid)
        ).getOrDefault("coin", BigDecimal.ZERO);
    }

    /**
     * 计算总提现金额
     *
     * @param uid uid集合
     * @return 余额
     */
    public BigDecimal calcWithdrawalCoin(@NotNull List<Integer> uid, Integer startTime, Integer endTime) {
        return uid.isEmpty() ? BigDecimal.ZERO : (BigDecimal) coinWithdrawalRecordServiceImpl.getMap(
                new QueryWrapper<CoinWithdrawalRecord>()
                        .select("ifNull(SUM(withdrawal_amount), 0) as coin")
                        .ge(Optional.ofNullable(startTime).isPresent(), ConstSql.CREATED_AT, startTime)
                        .le(Optional.ofNullable(endTime).isPresent(), ConstSql.CREATED_AT, endTime)
                        .in(ConstSql.UID, uid)
                        .eq(ConstSql.STATUS, 1)
        ).getOrDefault(ConstSql.COIN, BigDecimal.ZERO);
    }

    /**
     * 计算团队首充人数、首充金额
     *
     * @param uid uid集合
     * @return 首充数据
     */
    public QueryBo.QueryDto calcTeamFirstDeposit(@NotNull List<Integer> uid, Integer startTime, Integer endTime) {
        if (uid.isEmpty()) {
            return new QueryBo.QueryDto();
        }

        // 代理首页会员数据统计
        Map<String, Object> summary = coinDepositRecordServiceImpl.getMap(
                new QueryWrapper<CoinDepositRecord>()
                        .select(ConstSql.DEPOSIT_COIN_AND_COUNT)
                        .in(ConstSql.UID, uid)
                        .ge(Optional.ofNullable(startTime).isPresent(), ConstSql.CREATED_AT, startTime)
                        .le(Optional.ofNullable(endTime).isPresent(), ConstSql.CREATED_AT, endTime)
                        .eq(ConstSql.DEP_STATUS, ConstData.ONE)
                        .eq(ConstSql.STATUS, ConstData.ONE)
        );
        return JSON.parseObject(JSON.toJSONString(summary)).toJavaObject(QueryBo.QueryDto.class);
    }

    /**
     * 分类统计 - 获取指定代理下的团队详情
     *
     * @param response  返回数据
     * @param uIds      user下的团队Uid列表
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @author: David
     */
    public void statistics(SuperAgentBo.AgentReportResDto response, List<Integer> uIds, Integer startTime, Integer endTime) {
        var stat = coinLogStatisticBase.statistics(uIds, startTime, endTime);
        // 代理线报表数据
        response.setCoinDeposit(stat.getCoinDeposit());
        response.setCoinWithdrawal(stat.getCoinWithdrawal());
        response.setCoinBet(stat.getCoinBet().subtract(stat.getCoinRefund()));
        response.setCoinBetBonus(stat.getCoinPayOut());
        response.setCoinProfit(stat.getCoinPayOut().subtract(stat.getCoinBet()).add(stat.getCoinRefund()));
        response.setCoinAdjust(stat.getCoinReconciliation());
        // 实际到账金额(提款成功金额)
        response.setCoinWithdrawalReally(calcWithdrawalCoin(uIds, startTime, endTime));
    }
}
