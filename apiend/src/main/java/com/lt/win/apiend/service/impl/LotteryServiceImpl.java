package com.lt.win.apiend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lt.win.apiend.io.dto.lottery.LotteryParams;
import com.lt.win.apiend.service.LotteryService;
import com.lt.win.dao.generator.po.*;
import com.lt.win.dao.generator.service.*;
import com.lt.win.service.base.UserCoinBase;
import com.lt.win.service.exception.BusinessException;
import com.lt.win.service.io.dto.BaseParams;
import com.lt.win.service.thread.ThreadHeaderLocalData;
import com.lt.win.utils.BeanConvertUtils;
import com.lt.win.utils.DateNewUtils;
import com.lt.win.utils.DateUtils;
import com.lt.win.utils.components.pagination.ReqPage;
import com.lt.win.utils.components.pagination.ResPage;
import com.lt.win.utils.components.response.CodeInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Auther: Jess
 * @Date: 2023/9/14 20:42
 * @Description:
 */
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class LotteryServiceImpl implements LotteryService {
    private final LotteryPlateService lotteryPlateServiceImpl;
    private final LotteryTypeService lotteryTypeServiceImpl;
    private final LotteryOpenService lotteryOpenServiceImpl;
    private final UserWalletService userWalletServiceImpl;
    private final LotteryBetslipsService lotteryBetslipsServiceImpl;
    private final UserCoinBase userCoinBase;

    private static final String PK10 = "pk10";
    /**
     * 每期间隔时间(秒)
     **/
    private static final int INTERVAL = 300;

    /**
     * @return com.lt.win.apiend.io.dto.lottery.LotteryParams.LotteryInfoRep
     * @Description 查询期数信息
     * @Param []
     **/
    @Override
    public LotteryParams.LotteryInfoRep queryLotteryInfo() {
        LotteryParams.LotteryInfoRep req = new LotteryParams.LotteryInfoRep();
        int toDaySecond = DateNewUtils.now() - DateNewUtils.todayStart();
        int restTime = INTERVAL - (toDaySecond % INTERVAL);
        String periodsNo = getCurrPeriodsNo();
        LotteryType lotteryType = lotteryTypeServiceImpl.getOne(new LambdaQueryWrapper<LotteryType>()
                .eq(LotteryType::getCode, PK10));
        BigDecimal odds = lotteryType.getOdds();
        List<LotteryPlate> lotteryPlateList = lotteryPlateServiceImpl.list();
        lotteryPlateList.sort(Comparator.comparing(LotteryPlate::getPayoutCount).reversed());
        List<LotteryParams.PlateDto> plateDtoList = new ArrayList<>();
        lotteryPlateList.forEach(lotteryPlate -> {
            LotteryParams.PlateDto plateDto = new LotteryParams.PlateDto();
            plateDto.setPlateCode(lotteryPlate.getCode());
            plateDto.setPlateName(lotteryPlate.getName());
            plateDto.setPayoutCount(lotteryPlate.getPayoutCount());
            plateDtoList.add(plateDto);
        });
        req.setPeriodsNo(periodsNo);
        req.setRestTime(restTime);
        req.setOdds(odds);
        req.setPlateDtoList(plateDtoList);
        return req;

    }

    /**
     * @return com.lt.win.apiend.io.dto.lottery.LotteryParams.LotteryResultRep
     * @Description 查询开奖结果
     * @Param []
     **/
    @Override
    public ResPage<LotteryParams.LotteryResultRep> queryLotteryResult(ReqPage<Object> reqPage) {
        Page<LotteryOpen> page = lotteryOpenServiceImpl.page(
                reqPage.getPage(),
                new LambdaQueryWrapper<LotteryOpen>().eq(LotteryOpen::getStatus, 1)
                        .orderByDesc(LotteryOpen::getPeriodsNo));
        Map<Integer, String> plateMap = lotteryPlateServiceImpl.list().stream()
                .collect(Collectors.toMap(LotteryPlate::getCode, LotteryPlate::getName));
        Page<LotteryParams.LotteryResultRep> lotteryResultRepPage = BeanConvertUtils.copyPageProperties(page, LotteryParams.LotteryResultRep::new, (source, target) -> {
            String periodsNo = source.getPeriodsNo();
            Integer num = Integer.parseInt(periodsNo.substring(periodsNo.length() - 3));
            String openAllCode = source.getOpenAllCode();
            List<Integer> codeList = Stream.of(openAllCode.split(","))
                    .map(Integer::parseInt)
                    .collect(Collectors.toList());
            List<String> nameList = codeList.stream().map(plateMap::get).collect(Collectors.toList());
            String plateName = StringUtils.join(nameList, ",");
            target.setNum(num);
            target.setPlateName(plateName);
        });
        return ResPage.get(lotteryResultRepPage);
    }

    /**
     * @return com.lt.win.apiend.io.dto.lottery.LotteryParams.BetRep
     * @Description 投注
     * @Param [res]
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public LotteryParams.BetRep bet(LotteryParams.BetRes req) {
        BaseParams.HeaderInfo headerInfo = ThreadHeaderLocalData.HEADER_INFO_THREAD_LOCAL.get();
        Integer uid = headerInfo.id;
        UserWallet userWallet = userWalletServiceImpl.getOne(new LambdaQueryWrapper<UserWallet>().eq(UserWallet::getId, uid));
        List<LotteryParams.BetDto> betList = req.getBetList();
        BigDecimal coinSum = betList.stream().map(LotteryParams.BetDto::getCoin).reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);
        if (userWallet.getCoin().compareTo(coinSum) < 0) {
            throw new BusinessException(CodeInfo.BET_COIN_EXCEPTION);
        }
        String periodsNo = getCurrPeriodsNo();
        Map<Integer, String> plateMap = lotteryPlateServiceImpl.list().stream()
                .collect(Collectors.toMap(LotteryPlate::getCode, LotteryPlate::getName));
        LotteryType lotteryType = lotteryTypeServiceImpl.getOne(new LambdaQueryWrapper<LotteryType>()
                .eq(LotteryType::getCode, PK10));
        Integer now = DateNewUtils.now();
        List<LotteryBetslips> lotteryBetslipsList = new ArrayList<>();
        betList.forEach(bet -> {
            LotteryBetslips lotteryBetslips = new LotteryBetslips();
            lotteryBetslips.setPeriodsNo(periodsNo);
            lotteryBetslips.setUid(uid);
            lotteryBetslips.setUsername(headerInfo.getUsername());
            lotteryBetslips.setCode(lotteryType.getCode());
            lotteryBetslips.setName(lotteryType.getName());
            lotteryBetslips.setOdds(lotteryType.getOdds());
            lotteryBetslips.setCoinBet(bet.coin);
            lotteryBetslips.setBetCode(bet.getPlateCode());
            lotteryBetslips.setBetName(plateMap.get(bet.plateCode));
            lotteryBetslips.setCreatedAt(now);
            lotteryBetslips.setUpdatedAt(now);
            lotteryBetslipsList.add(lotteryBetslips);
        });
        lotteryBetslipsServiceImpl.saveBatch(lotteryBetslipsList);
        userCoinBase.updateConcurrentUserCoin(coinSum.negate(), uid);
        LotteryParams.BetRep res = new LotteryParams.BetRep();
        res.setStatus(1);
        return res;
    }

    /**
     * @return com.lt.win.utils.components.pagination.ResPage<com.lt.win.apiend.io.dto.lottery.LotteryParams.BetRecordRes>
     * @Description 查询注单记录
     * @Param [reqPage]
     */
    @Override
    public ResPage<LotteryParams.BetRecordRes> queryBetRecord(ReqPage<LotteryParams.BetRecordReq> reqPage) {
        LotteryParams.BetRecordReq data = reqPage.getData();
        BaseParams.HeaderInfo headerInfo = ThreadHeaderLocalData.HEADER_INFO_THREAD_LOCAL.get();
        Integer uid = headerInfo.id;
        Page<LotteryBetslips> page = lotteryBetslipsServiceImpl.page(
                reqPage.getPage(), new LambdaQueryWrapper<LotteryBetslips>()
                        .eq(LotteryBetslips::getUid, uid)
                        .eq(Objects.nonNull(data.getPeriodsNo()), LotteryBetslips::getPeriodsNo, data.getPeriodsNo())
                        .ge(Objects.nonNull(data.getStartTime()), LotteryBetslips::getCreatedAt, data.getStartTime())
                        .le(Objects.nonNull(data.getEndTime()), LotteryBetslips::getCreatedAt, data.getEndTime())
                        .eq(Objects.nonNull(data.getBetCode()), LotteryBetslips::getBetCode, data.getBetCode())
                        .eq(Objects.nonNull(data.getPayoutCode()), LotteryBetslips::getPayoutCode, data.getPayoutCode())
                        .orderByDesc(LotteryBetslips::getCreatedAt));
        Page<LotteryParams.BetRecordRes> betRecordResPage = BeanConvertUtils.copyPageProperties(page, LotteryParams.BetRecordRes::new);
        return ResPage.get(betRecordResPage);
    }

    /**
     * @return java.lang.String
     * @Description 获取当前期号
     * @Param []
     **/
    private String getCurrPeriodsNo() {
        int toDaySecond = DateNewUtils.now() - DateNewUtils.todayStart();
        int num = toDaySecond / INTERVAL;
        num = toDaySecond % INTERVAL == 0 ? num : num + 1;
        return DateUtils.yyyyMMdd2(DateUtils.getCurrentTime()) + String.format("%03d", num);
    }
}
