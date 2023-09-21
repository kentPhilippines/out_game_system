package com.lt.win.apiend.task;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lt.win.apiend.io.dto.lottery.LotteryParams;
import com.lt.win.dao.generator.po.LotteryBetslips;
import com.lt.win.dao.generator.po.LotteryType;
import com.lt.win.dao.generator.po.UserWallet;
import com.lt.win.dao.generator.service.LotteryBetslipsService;
import com.lt.win.dao.generator.service.UserWalletService;
import com.lt.win.service.base.UserCoinBase;
import com.lt.win.service.cache.redis.LotteryCache;
import com.lt.win.service.exception.BusinessException;
import com.lt.win.utils.DateNewUtils;
import com.lt.win.utils.components.response.CodeInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @Auther: Jess
 * @Date: 2023/9/20 15:38
 * @Description:
 */
@Slf4j
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class LotteryBetTask {
    private final UserWalletService userWalletServiceImpl;
    private final LotteryBetslipsService lotteryBetslipsServiceImpl;
    private final UserCoinBase userCoinBase;
    private final LotteryCache lotteryCache;
    /**
     * 每期间隔时间(秒)
     **/
    private static final int INTERVAL = 300;
    Random random = new Random();

   // @Scheduled(cron = "20 */5 * * * ?")
    public void bet() {
        int toDaySecond = DateNewUtils.now() - DateNewUtils.todayStart();
        int restTime = INTERVAL - (toDaySecond % INTERVAL);
        if (restTime <= 10) {
            throw new BusinessException(CodeInfo.BET_TIME_EXCEPTION);
        }
        int startUid = 73542;
        for (int i = 1; i <= 30; i++) {
            int uid = startUid + i;
            String username = "bet" + String.format("%02d", i);
            int mainCode = random.nextInt(3) + 1;
            UserWallet userWallet = userWalletServiceImpl.getOne(new LambdaQueryWrapper<UserWallet>().eq(UserWallet::getId, uid));
            List<LotteryParams.BetDto> betList = new ArrayList<>();
            LotteryParams.BetDto betDto = new LotteryParams.BetDto();
            betDto.setPlateCode(random.nextInt(10) + 1);
            betDto.setCoin(BigDecimal.valueOf(10L).multiply(BigDecimal.valueOf((random.nextInt(10) + 1))));
            betList.add(betDto);
            BigDecimal coinSum = betList.stream().map(LotteryParams.BetDto::getCoin).reduce(BigDecimal::add)
                    .orElse(BigDecimal.ZERO);
            if (userWallet.getCoin().compareTo(coinSum) < 0) {
                throw new BusinessException(CodeInfo.BET_COIN_EXCEPTION);
            }
            String periodsNo = lotteryCache.getCurrPeriodsNo();
            LotteryType lotteryType = lotteryCache.getLotteryType();
            Integer now = DateNewUtils.now();
            List<LotteryBetslips> lotteryBetslipsList = new ArrayList<>();
            betList.forEach(bet -> {
                LotteryBetslips lotteryBetslips = new LotteryBetslips();
                lotteryBetslips.setPeriodsNo(periodsNo);
                lotteryBetslips.setUid(uid);
                lotteryBetslips.setUsername(username);
                lotteryBetslips.setCode(lotteryType.getCode());
                lotteryBetslips.setName(lotteryType.getName());
                lotteryBetslips.setOdds(lotteryType.getOdds());
                lotteryBetslips.setCoinBet(bet.coin);
                lotteryBetslips.setBetCode(bet.getPlateCode());
                lotteryBetslips.setMainCode(mainCode);
                lotteryBetslips.setCreatedAt(now);
                lotteryBetslips.setUpdatedAt(now);
                lotteryBetslipsList.add(lotteryBetslips);
            });
            lotteryBetslipsServiceImpl.saveBatch(lotteryBetslipsList);
            userCoinBase.updateConcurrentUserCoin(coinSum.negate(), uid);
            LotteryParams.BetRep res = new LotteryParams.BetRep();
            res.setStatus(1);
        }
    }

}
