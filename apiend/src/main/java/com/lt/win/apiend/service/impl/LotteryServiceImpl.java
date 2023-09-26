package com.lt.win.apiend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lt.win.apiend.io.dto.lottery.LotteryParams;
import com.lt.win.apiend.service.LotteryService;
import com.lt.win.dao.generator.po.*;
import com.lt.win.dao.generator.service.*;
import com.lt.win.service.base.UserCoinBase;
import com.lt.win.service.cache.redis.LotteryCache;
import com.lt.win.service.exception.BusinessException;
import com.lt.win.service.io.dto.BaseParams;
import com.lt.win.service.thread.ThreadHeaderLocalData;
import com.lt.win.utils.BeanConvertUtils;
import com.lt.win.utils.DateNewUtils;
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
    private final LotteryOpenService lotteryOpenServiceImpl;
    private final UserWalletService userWalletServiceImpl;
    private final LotteryBetslipsService lotteryBetslipsServiceImpl;
    private final UserCoinBase userCoinBase;
    private final LotteryCache lotteryCache;


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
    public LotteryParams.LotteryInfoRes queryLotteryInfo(LotteryParams.LotteryInfoReq req) {
        LotteryParams.LotteryInfoRes res = new LotteryParams.LotteryInfoRes();
        int toDaySecond = DateNewUtils.now() - DateNewUtils.todayStart();
        int restTime = INTERVAL - (toDaySecond % INTERVAL);
        String periodsNo = lotteryCache.getCurrPeriodsNo();
        LotteryType lotteryType = lotteryCache.getLotteryType();
        BigDecimal odds = lotteryType.getOdds();
        List<LotteryPlate> lotteryPlateList = lotteryCache.getLotteryPlateList(req.mainCode);
        Map<Integer, LotteryPlate> lotteryPlateMap = lotteryPlateList.stream().collect(Collectors.toMap(LotteryPlate::getCode, v -> v));
        LotteryOpen lotteryOpen = lotteryOpenServiceImpl.getOne(new LambdaQueryWrapper<LotteryOpen>()
                .eq(LotteryOpen::getMainCode,req.mainCode)
                .eq(LotteryOpen::getStatus, 1)
                .orderByDesc(LotteryOpen::getPeriodsNo).last("limit 1"));
        LotteryMainPlate mainPlate = lotteryCache.getLotteryMainPlate(req.mainCode);
        List<LotteryParams.PlateDto> plateDtoList = new ArrayList<>();
        Stream.of(lotteryOpen.getOpenAllCode().split(",")).forEach(
                code -> {
                    Integer plateCode = Integer.parseInt(code);
                    LotteryPlate lotteryPlate = lotteryPlateMap.get(plateCode);
                    LotteryParams.PlateDto plateDto = new LotteryParams.PlateDto();
                    plateDto.setPlateCode(plateCode);
                    plateDto.setPlateName(lotteryPlate.getName());
                    plateDto.setPayoutCount(lotteryPlate.getPayoutCount());
                    plateDtoList.add(plateDto);
                });
        res.setMainCode(req.mainCode);
        res.setMainName(mainPlate.getName());
        res.setPeriodsNo(periodsNo);
        res.setRestTime(restTime);
        res.setOdds(odds);
        res.setPlateDtoList(plateDtoList);
        return res;

    }

    /**
     * @return com.lt.win.apiend.io.dto.lottery.LotteryParams.LotteryResultRep
     * @Description 查询开奖结果
     * @Param []
     **/
    @Override
    public ResPage<LotteryParams.LotteryResultRes> queryLotteryResult(ReqPage<LotteryParams.LotteryResultReq> reqPage) {
        Integer mainCode = reqPage.getData().mainCode;
        Page<LotteryOpen> page = lotteryOpenServiceImpl.page(
                reqPage.getPage(),
                new LambdaQueryWrapper<LotteryOpen>()
                        .eq(LotteryOpen::getStatus, 1)
                        .eq(LotteryOpen::getMainCode, mainCode)
                        .orderByDesc(LotteryOpen::getPeriodsNo));
        Map<Integer, String> plateMap = lotteryCache.getLotteryPlateMap(mainCode);
        Page<LotteryParams.LotteryResultRes> lotteryResultRepPage = BeanConvertUtils.copyPageProperties(page, LotteryParams.LotteryResultRes::new, (source, target) -> {
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
        int toDaySecond = DateNewUtils.now() - DateNewUtils.todayStart();
        int restTime = INTERVAL - (toDaySecond % INTERVAL);
        if (restTime <= 10) {
            throw new BusinessException(CodeInfo.BET_TIME_EXCEPTION);
        }
        BaseParams.HeaderInfo headerInfo = ThreadHeaderLocalData.HEADER_INFO_THREAD_LOCAL.get();
        Integer uid = headerInfo.id;
        UserWallet userWallet = userWalletServiceImpl.getOne(new LambdaQueryWrapper<UserWallet>().eq(UserWallet::getId, uid));
        List<LotteryParams.BetDto> betList = req.getBetList();
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
            lotteryBetslips.setUsername(headerInfo.getUsername());
            lotteryBetslips.setCode(lotteryType.getCode());
            lotteryBetslips.setName(lotteryType.getName());
            lotteryBetslips.setOdds(lotteryType.getOdds());
            lotteryBetslips.setCoinBet(bet.coin);
            lotteryBetslips.setBetCode(bet.getPlateCode());
            lotteryBetslips.setMainCode(req.getMainCode());
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
                        .eq(Objects.nonNull(data.getMainCode()), LotteryBetslips::getMainCode, data.getMainCode())
                        .orderByDesc(LotteryBetslips::getCreatedAt));
        Map<String, String> lotteryPlateMap = lotteryCache.getLotteryPlateMap();
        Map<Integer, String> lotteryMainPlateMap = lotteryCache.getLotteryMainPlateMap();
        Page<LotteryParams.BetRecordRes> betRecordResPage = BeanConvertUtils.copyPageProperties(page, LotteryParams.BetRecordRes::new,
                (source, target) -> {
                    target.setMainName(lotteryMainPlateMap.get(source.getMainCode()));
                    target.setBetName(lotteryPlateMap.get(source.getMainCode() + "_" + source.getBetCode()));
                    if (Objects.nonNull(source.getPayoutCode())) {
                        List<Integer> openCodeList = Stream.of(source.getPayoutCode().split(","))
                                .map(Integer::parseInt)
                                .collect(Collectors.toList());
                        List<String> payoutNameList = openCodeList.stream().map(code -> lotteryPlateMap.get(source.getMainCode() + "_" + code)).collect(Collectors.toList());
                        target.setPayoutName(StringUtils.join(payoutNameList, ","));
                    }
                });
        return ResPage.get(betRecordResPage);
    }

    /**
     * @return java.util.List<com.lt.win.apiend.io.dto.lottery.LotteryParams.MainPlateRes>
     * @Description 查询主板列表
     * @Param []
     **/
    @Override
    public List<LotteryParams.MainPlateRes> queryMainPlateList() {
        List<LotteryMainPlate> mainPlateList = lotteryCache.getLotteryMainPlateList();
        return BeanConvertUtils.copyListProperties(mainPlateList, LotteryParams.MainPlateRes::new,
                (source, target) -> {
                    target.setMainCode(source.getCode());
                    target.setMainName(source.getName());
                });
    }

    /**
     * @return java.util.List<com.lt.win.apiend.io.dto.lottery.LotteryParams.PlateRes>
     * @Description 查询板块列表
     * @Param [req]
     */
    @Override
    public List<LotteryParams.PlateRes> queryPlateList(LotteryParams.PlateReq req) {
        List<LotteryPlate> lotteryPlateList = lotteryCache.getLotteryPlateList(req.getMainCode());
        return BeanConvertUtils.copyListProperties(lotteryPlateList, LotteryParams.PlateRes::new, (source, target) -> {
            target.setPlateCode(source.getCode());
            target.setPlateName(source.getName());
        });
    }


}
