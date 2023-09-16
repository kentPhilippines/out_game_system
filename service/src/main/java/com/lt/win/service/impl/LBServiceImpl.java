package com.lt.win.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lt.win.dao.generator.po.CoinDepositRecord;
import com.lt.win.dao.generator.po.LotteryBetslips;
import com.lt.win.dao.generator.po.User;
import com.lt.win.dao.generator.service.GameSlotService;
import com.lt.win.dao.generator.service.LotteryBetslipsService;
import com.lt.win.service.exception.BusinessException;
import com.lt.win.service.io.dto.BaseParams;
import com.lt.win.service.io.dto.admin.BetStatisticsSumDto;
import com.lt.win.service.io.qo.Betslip;
import com.lt.win.service.server.LBService;
import com.lt.win.utils.BeanConvertUtils;
import com.lt.win.utils.components.pagination.ReqPage;
import com.lt.win.utils.components.pagination.ResPage;
import com.lt.win.utils.components.response.CodeInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
public class LBServiceImpl implements LBService {
    @Autowired
    LotteryBetslipsService lotteryBetslipsServiceImpl;


    @Override
    public ResPage<LotteryBetslips> queryBets(ReqPage<Betslip.BetslipsDto> reqPage, BaseParams.HeaderInfo userInfo) {
        if (null == reqPage) {
            throw new BusinessException(CodeInfo.PARAMETERS_INVALID);
        }
        Betslip.BetslipsDto data = reqPage.getData();
        var page =   lotteryBetslipsServiceImpl.page(reqPage.getPage(), new LambdaQueryWrapper<LotteryBetslips>()
                .eq(Optional.ofNullable(data.getPeriodsNo()).isPresent(), LotteryBetslips::getPeriodsNo, data.getPeriodsNo())
                .eq(Optional.ofNullable(data.getUid()).isPresent(), LotteryBetslips::getUid, data.getUid())
                .like(Optional.ofNullable(data.getUsername()).isPresent(), LotteryBetslips::getUsername, data.getUsername())
                .eq(Optional.ofNullable(data.getPayoutCode()).isPresent(), LotteryBetslips::getPayoutCode, data.getPayoutCode())
                .eq(Optional.ofNullable(data.getPayoutName()).isPresent(), LotteryBetslips::getPayoutName, data.getPayoutName())
                .eq(Optional.ofNullable(data.getBetCode()).isPresent(), LotteryBetslips::getBetCode, data.getBetCode())
                .eq(Optional.ofNullable(data.getBetName()).isPresent(), LotteryBetslips::getBetName, data.getBetName())
                .eq(Optional.ofNullable(data.getStatus()).isPresent(), LotteryBetslips::getStatus, data.getStatus())
                .gt(Optional.ofNullable(data.getStartTime()).isPresent(), LotteryBetslips::getCreatedAt, data.getStartTime())
                .lt(Optional.ofNullable(data.getEndTime()).isPresent(), LotteryBetslips::getCreatedAt, data.getEndTime()));
        var result = BeanConvertUtils.copyPageProperties(
                page,
                LotteryBetslips::new
        );
        return ResPage.get(result);
    }

    public  BetStatisticsSumDto queryBetsSum(ReqPage<Betslip.BetslipsDtoSum> reqPage, BaseParams.HeaderInfo userInfo) {
        if (null == reqPage) {
            throw new BusinessException(CodeInfo.PARAMETERS_INVALID);
        }
        Betslip.BetslipsDtoSum data = reqPage.getData();
        Map<String, Object> map = lotteryBetslipsServiceImpl.getMap(
                new QueryWrapper<LotteryBetslips>()
                        .select("ifNull(SUM(coinBet), 0) as coinBet , ifNull(SUM(coinPayout), 0) as coinPayout")
                        .eq(Optional.ofNullable(data.getUid()).isPresent(), "uid", data.getUid())
                        .like(Optional.ofNullable(data.getUsername()).isPresent(), "username", data.getUsername())
                        .gt(Optional.ofNullable(data.getStartTime()).isPresent(), "created_at", data.getStartTime())
                        .lt(Optional.ofNullable(data.getEndTime()).isPresent(), "created_at", data.getEndTime())
        );
        BetStatisticsSumDto betStatisticsSumDto = new BetStatisticsSumDto();
        betStatisticsSumDto.setTotalProfit((BigDecimal) map.getOrDefault("coinPayout", BigDecimal.ZERO));
        betStatisticsSumDto.setTotalStake((BigDecimal) map.getOrDefault("coinBet", BigDecimal.ZERO));
        return betStatisticsSumDto;
    }
}
