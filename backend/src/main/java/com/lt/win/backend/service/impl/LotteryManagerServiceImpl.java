package com.lt.win.backend.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lt.win.backend.io.dto.LotteryManagerParams.*;
import com.lt.win.backend.service.LotteryManagerService;
import com.lt.win.dao.generator.po.*;
import com.lt.win.dao.generator.service.*;
import com.lt.win.service.base.LotteryCommBase;
import com.lt.win.service.base.UserCoinBase;
import com.lt.win.service.cache.KeyConstant;
import com.lt.win.service.cache.redis.LotteryCache;
import com.lt.win.service.exception.BusinessException;
import com.lt.win.service.io.dto.BaseParams;
import com.lt.win.service.thread.ThreadHeaderLocalData;
import com.lt.win.utils.BeanConvertUtils;
import com.lt.win.utils.DateNewUtils;
import com.lt.win.utils.JedisUtil;
import com.lt.win.utils.components.pagination.ReqPage;
import com.lt.win.utils.components.pagination.ResPage;
import com.lt.win.utils.components.response.CodeInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

/**
 * @Auther: Jess
 * @Date: 2023/9/19 11:00
 * @Description:
 */
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class LotteryManagerServiceImpl implements LotteryManagerService {
    private final LotteryTypeService lotteryTypeServiceImpl;
    private final LotteryMainPlateService lotteryMainPlateServiceImpl;
    private final LotteryPlateService lotteryPlateServiceImpl;
    private final LotteryOpenService lotteryOpenServiceImpl;
    private final LotteryBetslipsService lotteryBetslipsServiceImpl;
    private final LotteryCache lotteryCache;
    private final JedisUtil jedisUtil;
    private final LotteryCommBase lotteryCommBase;
    private final UserService userServiceImpl;
    private final UserWalletService userWalletServiceImpl;
    private final UserCoinBase userCoinBase;
    /**
     * 每期间隔时间(秒)
     **/
    private static final int INTERVAL = 300;

    /**
     * @return com.lt.win.utils.components.pagination.ResPage<com.lt.win.backend.io.dto.LotteryManagerParams.LotteryTypePageRes>
     * @Description 彩种列表
     * @Param [reqBody]
     */
    @Override
    public ResPage<LotteryTypePageRes> lotteryTypePage(ReqPage<LotteryTypePageReq> reqBody) {
        Page<LotteryType> page = lotteryTypeServiceImpl.page(reqBody.getPage(), new LambdaQueryWrapper<LotteryType>()
                .eq(nonNull(reqBody.getData().getName()), LotteryType::getName, reqBody.getData().getName())
                .orderByAsc(LotteryType::getCode));
        Page<LotteryTypePageRes> lotteryTypePageResPage = BeanConvertUtils.copyPageProperties(page, LotteryTypePageRes::new);
        return ResPage.get(lotteryTypePageResPage);
    }

    /**
     * @return java.lang.Boolean
     * @Description 新增彩种
     * @Param [reqBody]
     */
    @Override
    public Boolean addLotteryType(AddLotteryTypeReq reqBody) {
        LotteryType lotteryType = BeanConvertUtils.copyProperties(reqBody, LotteryType::new);
        Integer now = DateNewUtils.now();
        lotteryType.setCreatedAt(now);
        lotteryType.setUpdatedAt(now);
        jedisUtil.del(KeyConstant.LOTTERY_TYPE_LIST);
        return lotteryTypeServiceImpl.save(lotteryType);
    }

    /**
     * @return java.lang.Boolean
     * @Description 修改彩种
     * @Param [reqBody]
     */
    @Override
    public Boolean updateLotteryType(UpdateLotteryTypeReq reqBody) {
        LotteryType lotteryType = BeanConvertUtils.copyProperties(reqBody, LotteryType::new);
        Integer now = DateNewUtils.now();
        lotteryType.setUpdatedAt(now);
        jedisUtil.del(KeyConstant.LOTTERY_TYPE_LIST);
        return lotteryTypeServiceImpl.updateById(lotteryType);
    }

    /**
     * @return java.lang.Boolean
     * @Description 删除彩种
     * @Param [reqBody]
     */
    @Override
    public Boolean deleteLotteryType(DeleteLotteryTypeReq reqBody) {
        jedisUtil.del(KeyConstant.LOTTERY_TYPE_LIST);
        return lotteryTypeServiceImpl.removeById(reqBody.getId());
    }

    /**
     * @return com.lt.win.utils.components.pagination.ResPage<com.lt.win.backend.io.dto.LotteryManagerParams.LotteryTypePageRes>
     * @Description 查询主板列表
     * @Param [reqBody]
     */
    @Override
    public ResPage<MainPlatePageRes> mainPlatePage(ReqPage<MainPlatePageReq> reqBody) {
        Page<LotteryMainPlate> page = lotteryMainPlateServiceImpl.page(reqBody.getPage(), new LambdaQueryWrapper<LotteryMainPlate>()
                .eq(nonNull(reqBody.getData().getName()), LotteryMainPlate::getName, reqBody.getData().getName())
                .orderByAsc(LotteryMainPlate::getCode));
        Page<MainPlatePageRes> mainPlatePageResPage = BeanConvertUtils.copyPageProperties(page, MainPlatePageRes::new);
        return ResPage.get(mainPlatePageResPage);

    }

    /***
     * @Description
     * @Param [reqBody]新增主板列表
     * @return java.lang.Boolean
     *  */
    @Override
    public Boolean addMainPlate(AddMainPlateReq reqBody) {
        LotteryMainPlate lotteryMainPlate = BeanConvertUtils.copyProperties(reqBody, LotteryMainPlate::new);
        Integer now = DateNewUtils.now();
        lotteryMainPlate.setCreatedAt(now);
        lotteryMainPlate.setUpdatedAt(now);
        jedisUtil.del(KeyConstant.LOTTERY_MAIN_PLATE_LIST);
        return lotteryMainPlateServiceImpl.save(lotteryMainPlate);
    }

    /***
     * @Description
     * @Param [reqBody]修改主板列表
     * @return java.lang.Boolean
     *  */
    @Override
    public Boolean updateMainPlate(UpdateMainPlateReq reqBody) {
        LotteryMainPlate lotteryMainPlate = BeanConvertUtils.copyProperties(reqBody, LotteryMainPlate::new);
        Integer now = DateNewUtils.now();
        lotteryMainPlate.setUpdatedAt(now);
        jedisUtil.del(KeyConstant.LOTTERY_MAIN_PLATE_LIST);
        return lotteryMainPlateServiceImpl.updateById(lotteryMainPlate);
    }

    /***
     * @Description
     * @Param [reqBody]删除主板列表
     * @return java.lang.Boolean
     *  */
    @Override
    public Boolean deleteMainPlate(DeleteMainPlateReq reqBody) {
        jedisUtil.del(KeyConstant.LOTTERY_MAIN_PLATE_LIST);
        return lotteryMainPlateServiceImpl.removeById(reqBody.getId());
    }

    /**
     * @return com.lt.win.utils.components.pagination.ResPage<com.lt.win.backend.io.dto.LotteryManagerParams.PlatePageRes>
     * @Description
     * @Param [reqBody]
     */
    @Override
    public ResPage<PlatePageRes> platePage(ReqPage<PlatePageReq> reqBody) {
        PlatePageReq req = reqBody.getData();
        Page<LotteryPlate> page = lotteryPlateServiceImpl.page(
                reqBody.getPage(), new LambdaQueryWrapper<LotteryPlate>()
                        .eq(nonNull(req.getName()), LotteryPlate::getName, req.getName())
                        .eq(nonNull(req.getMainCode()), LotteryPlate::getMainCode, req.getMainCode())
                        .orderByAsc(LotteryPlate::getMainCode, LotteryPlate::getCode));
        Map<Integer, String> lotteryMainPlateMap = lotteryCache.getLotteryMainPlateMap();
        Page<PlatePageRes> platePageResPage = BeanConvertUtils.copyPageProperties(page, PlatePageRes::new,
                (s, t) ->
                        t.setMainName(lotteryMainPlateMap.get(s.getMainCode()))
        );
        return ResPage.get(platePageResPage);
    }

    /**
     * @return java.lang.Boolean
     * @Description
     * @Param [reqBody]
     */
    @Override
    public Boolean addPlate(AddPlateReq reqBody) {
        LotteryPlate lotteryPlate = BeanConvertUtils.copyProperties(reqBody, LotteryPlate::new);
        Integer now = DateNewUtils.now();
        lotteryPlate.setCreatedAt(now);
        lotteryPlate.setUpdatedAt(now);
        jedisUtil.del(KeyConstant.LOTTERY_PLATE_LIST);
        return lotteryPlateServiceImpl.save(lotteryPlate);
    }

    /**
     * @return java.lang.Boolean
     * @Description
     * @Param [reqBody]
     */
    @Override
    public Boolean updatePlate(UpdatePlateReq reqBody) {
        LotteryPlate lotteryPlate = BeanConvertUtils.copyProperties(reqBody, LotteryPlate::new);
        Integer now = DateNewUtils.now();
        lotteryPlate.setUpdatedAt(now);
        jedisUtil.del(KeyConstant.LOTTERY_PLATE_LIST);
        return lotteryPlateServiceImpl.updateById(lotteryPlate);
    }

    /***
     * @Description
     * @Param [reqBody]
     * @return java.lang.Boolean
     *  */
    @Override
    public Boolean deletePlate(DeletePlateReq reqBody) {
        jedisUtil.del(KeyConstant.LOTTERY_PLATE_LIST);
        return lotteryPlateServiceImpl.removeById(reqBody.getId());
    }

    /**
     * @return com.lt.win.utils.components.pagination.ResPage<com.lt.win.backend.io.dto.LotteryManagerParams.OpenPageRes>
     * @Description 开奖结果列表
     * @Param [reqBody]
     */
    @Override
    public ResPage<OpenPageRes> openPage(ReqPage<OpenPageReq> reqBody) {
        OpenPageReq req = reqBody.getData();
        Page<LotteryOpen> page = lotteryOpenServiceImpl.page(
                reqBody.getPage(), new LambdaQueryWrapper<LotteryOpen>()
                        .eq(nonNull(req.getOpenCode()), LotteryOpen::getOpenCode, req.getOpenCode())
                        .eq(nonNull(req.getMainCode()), LotteryOpen::getMainCode, req.getMainCode())
                        .eq(nonNull(req.getPeriodsNo()), LotteryOpen::getPeriodsNo, req.getPeriodsNo())
                        .orderByDesc(LotteryOpen::getCreatedAt, LotteryOpen::getMainCode));
        Map<Integer, String> lotteryMainPlateMap = lotteryCache.getLotteryMainPlateMap();

        Page<OpenPageRes> openPageResPage = BeanConvertUtils.copyPageProperties(page, OpenPageRes::new,
                (s, t) -> {
                    t.setMainName(lotteryMainPlateMap.get(s.getMainCode()));
                    Map<Integer, String> plateMap = lotteryCache.getLotteryPlateMap(s.getMainCode());

                    List<Integer> openCodeList = Stream.of(s.getOpenCode().split(","))
                            .map(Integer::parseInt)
                            .collect(Collectors.toList());
                    List<String> payoutNameList = openCodeList.stream().map(plateMap::get).collect(Collectors.toList());
                    t.setOpenName(StringUtils.join(payoutNameList, ","));
                    List<Integer> codeList = Stream.of(s.getOpenAllCode().split(","))
                            .map(Integer::parseInt)
                            .collect(Collectors.toList());

                    List<String> nameList = codeList.stream().map(plateMap::get).collect(Collectors.toList());
                    String plateName = StringUtils.join(nameList, ",");
                    t.setOpenAllName(plateName);
                }
        );
        return ResPage.get(openPageResPage);
    }

    /**
     * @return java.lang.Boolean
     * @Description 新增开奖结果
     * @Param [reqBody]
     */
    @Override
    public Boolean addOpen(AddOpenReq reqBody) {
        BaseParams.HeaderInfo currentLoginUser = ThreadHeaderLocalData.HEADER_INFO_THREAD_LOCAL.get();
        LotteryOpen lotteryOpen = BeanConvertUtils.copyProperties(reqBody, LotteryOpen::new);
        List<Integer> openCodeList = Stream.of(lotteryOpen.getOpenCode().split(","))
                .map(Integer::parseInt)
                .collect(Collectors.toList());
        List<Integer> list = IntStream.range(1, 11)
                .boxed()
                .filter(e -> !openCodeList.contains(e))
                .collect(Collectors.toList());
        openCodeList.addAll(list);
        Integer now = DateNewUtils.now();
        LotteryType lotteryType = lotteryCache.getLotteryType();
        lotteryOpen.setLotteryCode(lotteryType.getCode());
        lotteryOpen.setLotteryName(lotteryType.getName());
        lotteryOpen.setOpenAllCode(StringUtils.join(openCodeList, ","));
        lotteryOpen.setUpdateUser(currentLoginUser.getUsername());
        lotteryOpen.setCreatedAt(now);
        lotteryOpen.setUpdatedAt(now);
        return lotteryOpenServiceImpl.save(lotteryOpen);
    }

    /**
     * @return java.lang.Boolean
     * @Description 修改开奖结果
     * @Param [reqBody]
     */
    @Override
    public Boolean updateOpen(UpdateOpenReq reqBody) {
        LotteryOpen open = lotteryOpenServiceImpl.getById(reqBody.getId());
        //开奖状态不能改修改
        if (nonNull(open) && open.getStatus() != 0) {
            throw new BusinessException(CodeInfo.LOTTERY_OPEN_STATUS_EXCEPTION);
        }
        BaseParams.HeaderInfo currentLoginUser = ThreadHeaderLocalData.HEADER_INFO_THREAD_LOCAL.get();
        LotteryOpen lotteryOpen = BeanConvertUtils.copyProperties(reqBody, LotteryOpen::new);
        List<Integer> openCodeList = Stream.of(lotteryOpen.getOpenCode().split(","))
                .map(Integer::parseInt)
                .collect(Collectors.toList());
        List<Integer> list = IntStream.range(1, 11)
                .boxed()
                .filter(e -> !openCodeList.contains(e))
                .collect(Collectors.toList());
        openCodeList.addAll(list);
        Integer now = DateNewUtils.now();
        lotteryOpen.setOpenAllCode(StringUtils.join(openCodeList, ","));
        lotteryOpen.setUpdateUser(currentLoginUser.getUsername());
        lotteryOpen.setUpdatedAt(now);
        return lotteryOpenServiceImpl.updateById(lotteryOpen);
    }

    /***
     * @Description 删除开奖结果
     * @Param [reqBody]
     * @return java.lang.Boolean
     *  */
    @Override
    public Boolean deleteOpen(DeleteOpenReq reqBody) {
        LotteryOpen open = lotteryOpenServiceImpl.getById(reqBody.getId());
        //开奖状态不能改修改
        if (nonNull(open) && open.getStatus() != 0) {
            throw new BusinessException(CodeInfo.LOTTERY_OPEN_STATUS_EXCEPTION);
        }
        return lotteryOpenServiceImpl.removeById(reqBody.getId());
    }

    /**
     * @return com.lt.win.utils.components.pagination.ResPage<com.lt.win.backend.io.dto.LotteryManagerParams.BetRecordRes>
     * @Description 注单列表
     * @Param [reqPage]
     */
    @Override
    public ResPage<BetRecordRes> betPage(ReqPage<BetRecordReq> reqPage) {
        BetRecordReq data = reqPage.getData();
        String upUsername = data.getUpUsername();
        List<Integer> uidList = new ArrayList<>();
        if (Objects.nonNull(upUsername)) {
            List<User> userList = userServiceImpl.lambdaQuery().eq(User::getSupUsername1, upUsername).list();
            if (CollUtil.isNotEmpty(userList)) {
                uidList = userList.stream().map(User::getId).collect(Collectors.toList());
            } else {
                uidList.add(-1);
            }
        }
        Page<LotteryBetslips> page = lotteryBetslipsServiceImpl.page(
                reqPage.getPage(), new LambdaQueryWrapper<LotteryBetslips>()
                        .eq(Objects.nonNull(data.getPeriodsNo()), LotteryBetslips::getPeriodsNo, data.getPeriodsNo())
                        .ge(Objects.nonNull(data.getStartTime()), LotteryBetslips::getCreatedAt, data.getStartTime())
                        .le(Objects.nonNull(data.getEndTime()), LotteryBetslips::getCreatedAt, data.getEndTime())
                        .eq(Objects.nonNull(data.getBetCode()), LotteryBetslips::getBetCode, data.getBetCode())
                        .like(Objects.nonNull(data.getPayoutCode()), LotteryBetslips::getPayoutCode, data.getPayoutCode())
                        .eq(Objects.nonNull(data.getMainCode()), LotteryBetslips::getMainCode, data.getMainCode())
                        .eq(Objects.nonNull(data.getUsername()), LotteryBetslips::getUsername, data.getUsername())
                        .eq(Objects.nonNull(data.getStatus()), LotteryBetslips::getStatus, data.getStatus())
                        .in(CollUtil.isNotEmpty(uidList), LotteryBetslips::getUid, uidList)
                        .orderByDesc(LotteryBetslips::getCreatedAt));
        Map<String, String> lotteryPlateMap = lotteryCache.getLotteryPlateMap();
        Map<Integer, String> lotteryMainPlateMap = lotteryCache.getLotteryMainPlateMap();
        Page<BetRecordRes> betRecordResPage = BeanConvertUtils.copyPageProperties(page, BetRecordRes::new,
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
     * @return java.util.List<com.lt.win.backend.io.dto.LotteryManagerParams.MainPlateRes>
     * @Description 查询主板记录
     * @Param []
     **/
    @Override
    public List<MainPlateRes> queryMainPlateList() {
        List<LotteryMainPlate> mainPlateList = lotteryCache.getLotteryMainPlateList();
        return BeanConvertUtils.copyListProperties(mainPlateList, MainPlateRes::new,
                (source, target) -> {
                    target.setMainCode(source.getCode());
                    target.setMainName(source.getName());
                });
    }

    /**
     * @return java.util.List<com.lt.win.backend.io.dto.LotteryManagerParams.PlateRes>
     * @Description &#x67E5;&#x8BE2;&#x677F;&#x5757;&#x8BB0;&#x5F55;
     * @Param [req]
     */
    @Override
    public List<PlateRes> queryPlateList(PlateReq req) {
        List<LotteryPlate> lotteryPlateList = lotteryCache.getLotteryPlateList(req.getMainCode());
        return BeanConvertUtils.copyListProperties(lotteryPlateList, PlateRes::new, (source, target) -> {
            target.setPlateCode(source.getCode());
            target.setPlateName(source.getName());
        });
    }

    /**
     * @return com.lt.win.backend.io.dto.LotteryManagerParams.PlateBetStatisticsRes
     * @Description 每期板块投注统计
     * @Param [req]
     */
    @Override
    public PlateBetStatisticsRes plateBetStatistics(PlateBetStatisticsReq req) {
        String periodsNo = lotteryCache.getCurrPeriodsNo();
        int restTime = 0;
        if (req.getPeriodsNo().equals(periodsNo)) {
            int toDaySecond = DateNewUtils.now() - DateNewUtils.todayStart();
            restTime = INTERVAL - (toDaySecond % INTERVAL);
        }
        Map<Integer, String> lotteryPlateMap = lotteryCache.getLotteryPlateMap(req.getMainCode());
        List<LotteryBetslips> lotteryBetslips = lotteryBetslipsServiceImpl.lambdaQuery()
                .eq(LotteryBetslips::getPeriodsNo, req.getPeriodsNo())
                .eq(LotteryBetslips::getMainCode, req.getMainCode())
                .list();
        Map<Integer, List<LotteryBetslips>> betMap = lotteryBetslips.stream().collect(Collectors.groupingBy(LotteryBetslips::getBetCode));
        LotteryType lotteryType = lotteryCache.getLotteryType();
        List<PlateBetStatisticsDto> betStatisticsDtoList = new ArrayList<>();
        BigDecimal betTotal = lotteryBetslips.stream().map(LotteryBetslips::getCoinBet).reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
        lotteryPlateMap.forEach((betCode, plateName) -> {
            PlateBetStatisticsDto p = new PlateBetStatisticsDto();
            p.setPlateName(plateName);
            List<LotteryBetslips> lotteryBetslipsList = betMap.get(betCode);
            BigDecimal betPlateTotal = BigDecimal.ZERO;
            if (CollUtil.isNotEmpty(lotteryBetslipsList)) {
                betPlateTotal = lotteryBetslipsList.stream().map(LotteryBetslips::getCoinBet).reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
            }
            BigDecimal payoutCoin = betPlateTotal.multiply(lotteryType.getOdds()).setScale(2, RoundingMode.DOWN);
            BigDecimal winPlateTotal = betTotal.subtract(payoutCoin);
            p.setBetTotal(betPlateTotal);
            p.setWinTotal(winPlateTotal);
            betStatisticsDtoList.add(p);
        });
        betStatisticsDtoList.sort(Comparator.comparing(PlateBetStatisticsDto::getWinTotal).reversed());
        PlateBetStatisticsRes plateBetStatisticsRes = new PlateBetStatisticsRes();
        plateBetStatisticsRes.setRestTime(restTime);
        plateBetStatisticsRes.setList(betStatisticsDtoList);
        return plateBetStatisticsRes;
    }

    /**
     * @return java.lang.Boolean
     * @Description 开奖结果-结算
     * @Param [req]
     */
    @Override
    public Boolean openSettle(OpenSettleReq req) {
        return lotteryCommBase.settle(req.periodsNo, req.mainCode);
    }

    /**
     * @return java.lang.Boolean
     * @Description 注单记录-结算
     * @Param [req]
     */
    @Override
    public Boolean betSettle(BetSettleReq req) {
        return lotteryCommBase.settle(req.getId());
    }

    /**
     * @return java.util.List<com.lt.win.backend.io.dto.LotteryManagerParams.BetRecordRes>
     * @Description 注单导出
     * @Param [req]
     */
    @Override
    public List<BetRecordRes> betExport(BetRecordReq req) {
        String upUsername = req.getUpUsername();
        List<Integer> uidList = new ArrayList<>();
        if (Objects.nonNull(upUsername)) {
            List<User> userList = userServiceImpl.lambdaQuery().eq(User::getSupUsername1, upUsername).list();
            if (CollUtil.isNotEmpty(userList)) {
                uidList = userList.stream().map(User::getId).collect(Collectors.toList());
            } else {
                uidList.add(-1);
            }
        }
        List<LotteryBetslips> list = lotteryBetslipsServiceImpl.lambdaQuery()
                .eq(Objects.nonNull(req.getPeriodsNo()), LotteryBetslips::getPeriodsNo, req.getPeriodsNo())
                .ge(Objects.nonNull(req.getStartTime()), LotteryBetslips::getCreatedAt, req.getStartTime())
                .le(Objects.nonNull(req.getEndTime()), LotteryBetslips::getCreatedAt, req.getEndTime())
                .eq(Objects.nonNull(req.getBetCode()), LotteryBetslips::getBetCode, req.getBetCode())
                .like(Objects.nonNull(req.getPayoutCode()), LotteryBetslips::getPayoutCode, req.getPayoutCode())
                .eq(Objects.nonNull(req.getMainCode()), LotteryBetslips::getMainCode, req.getMainCode())
                .eq(Objects.nonNull(req.getUsername()), LotteryBetslips::getUsername, req.getUsername())
                .eq(Objects.nonNull(req.getStatus()), LotteryBetslips::getStatus, req.getStatus())
                .in(CollUtil.isNotEmpty(uidList), LotteryBetslips::getUid, uidList)
                .orderByDesc(LotteryBetslips::getCreatedAt).list();
        Map<String, String> lotteryPlateMap = lotteryCache.getLotteryPlateMap();
        Map<Integer, String> lotteryMainPlateMap = lotteryCache.getLotteryMainPlateMap();
        return BeanConvertUtils.copyListProperties(list, BetRecordRes::new,
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
    }

    /**
     * @param req
     * @return java.lang.Boolean
     * @Description 修改注单信息
     * @Param [req]
     */
    @Override
    public Boolean updateBetRecord(UpdateBetRecordReq req) {
        LotteryBetslips betslips = lotteryBetslipsServiceImpl.getById(req.getId());
        if(isNull(betslips)){
            return false;
        }
        //开奖状态不能改修改
        if (betslips.getStatus() != 0) {
            throw new BusinessException(CodeInfo.LOTTERY_BET_SETTLE_EXCEPTION);
        }
        BigDecimal subValue =req.getCoinBet().subtract(betslips.getCoinBet());
        UserWallet userWallet = userWalletServiceImpl.getOne(new LambdaQueryWrapper<UserWallet>().eq(UserWallet::getId, betslips.getUid()));
        if (userWallet.getCoin().compareTo(subValue) < 0) {
            throw new BusinessException(CodeInfo.BET_COIN_EXCEPTION);
        }
        userCoinBase.updateConcurrentUserCoin(subValue.negate(), betslips.getUid());
        BaseParams.HeaderInfo currentLoginUser = ThreadHeaderLocalData.HEADER_INFO_THREAD_LOCAL.get();
        Integer now = DateNewUtils.now();
        betslips.setCoinBet(req.getCoinBet());
        betslips.setUpdateUser(currentLoginUser.getUsername());
        betslips.setUpdatedAt(now);
        return lotteryBetslipsServiceImpl.updateById(betslips);
    }
}
