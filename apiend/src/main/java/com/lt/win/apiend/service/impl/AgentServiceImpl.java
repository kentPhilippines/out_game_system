package com.lt.win.apiend.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lt.win.apiend.io.dto.StartEndTime;
import com.lt.win.apiend.service.IAgentService;
import com.lt.win.apiend.service.IUserInfoService;
import com.lt.win.dao.generator.po.AgentCommissionRate;
import com.lt.win.dao.generator.po.CoinCommission;
import com.lt.win.dao.generator.po.CoinDepositRecord;
import com.lt.win.dao.generator.po.User;
import com.lt.win.dao.generator.service.AgentCommissionRateService;
import com.lt.win.dao.generator.service.CoinCommissionService;
import com.lt.win.dao.generator.service.CoinDepositRecordService;
import com.lt.win.dao.generator.service.UserService;
import com.lt.win.service.base.CoinLogStatisticBase;
import com.lt.win.service.base.UserCoinBase;
import com.lt.win.service.cache.redis.UserCache;
import com.lt.win.service.exception.BusinessException;
import com.lt.win.service.io.bo.AgentReportBo;
import com.lt.win.service.io.bo.UserBo;
import com.lt.win.service.io.dto.BaseParams;
import com.lt.win.service.io.dto.UserCoinChangeParams;
import com.lt.win.service.io.enums.CurrencyEm;
import com.lt.win.utils.BeanConvertUtils;
import com.lt.win.utils.DateUtils;
import com.lt.win.utils.TextUtils;
import com.lt.win.utils.components.pagination.ReqPage;
import com.lt.win.utils.components.pagination.ResPage;
import com.lt.win.utils.components.response.CodeInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import static com.lt.win.service.common.Constant.COMM_TRANSFER;


/**
 * 代理中心业务处理接口
 *
 * @author David
 * @since 2022/8/22
 */
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AgentServiceImpl implements IAgentService {
    private final IUserInfoService userInfoServiceImpl;
    private final UserCache userCache;
    private final CoinCommissionService coinCommissionServiceImpl;
    private final UserCoinBase userCoinBase;
    private final CoinDepositRecordService coinDepositRecordServiceImpl;
    private final UserService userServiceImpl;
    private final AgentCommissionRateService agentCommissionRateServiceImpl;
    private final CoinLogStatisticBase coinLogStatisticBase;


    /**
     * 代理中心-团队盈亏报表
     *
     * @return dto
     */
    @Override
    public AgentReportBo.TeamBenefitsResDto teamBenefits() {
        var user = userInfoServiceImpl.findIdentityByApiToken();

        var subordinate = userCache.subordinate(user.getId());
        // 计算今日新增人数
        var todayIncrease = userServiceImpl.lambdaQuery()
                .gt(User::getCreatedAt, DateUtils.todayZeroTime())
                .and(
                        i -> i.eq(User::getSupUid1, user.getId())
                                .or()
                                .eq(User::getSupUid2, user.getId())
                                .or()
                                .eq(User::getSupUid3, user.getId())
                                .or()
                                .eq(User::getSupUid4, user.getId())
                                .or()
                                .eq(User::getSupUid5, user.getId())
                                .or()
                                .eq(User::getSupUid6, user.getId())
                )
                .count();
        var ids = List.of(user.getId());
        return AgentReportBo.TeamBenefitsResDto.builder()
                // 今日新增代理
                .todayIncreaseAgentNum(todayIncrease)
                // 总下线人数
                .totalAgentNum(subordinate.getTeam().size())
                // 本人累计佣金
                .totalCommission(calcCommissionUsd(ids))
                // 本人佣金可提现金额
                .allowWithdrawal(user.getCoinCommission())
                .build();
    }

    /**
     * 根据用户集计算用佣金金额
     *
     * @param uids uids
     * @return 佣金金额
     */
    private BigDecimal calcCommissionUsd(List<Integer> uids) {
        // 历史佣金金额
        return uids.isEmpty() ? BigDecimal.ZERO : (BigDecimal) coinCommissionServiceImpl.getMap(
                new QueryWrapper<CoinCommission>().select("ifNull(SUM(coin), 0) as coin")
                        .in("uid", uids)
        ).getOrDefault("coin", BigDecimal.ZERO);
    }

    /**
     * 根据用户集计算投注流水金额
     *
     * @param uids uids
     * @return 流水金额
     */
    private @NotNull BigDecimal calcBettingTurnOver(List<Integer> uids) {
        if (uids.isEmpty()) {
            return BigDecimal.ZERO;
        }
        // 历史投注流水

        var activityBetSum = coinLogStatisticBase.getValidBetCoinMap(uids, null, null);
        if (Optional.ofNullable(activityBetSum).isEmpty()) {
            return BigDecimal.ZERO;
        }
        return activityBetSum.values().stream().reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    /**
     * 代理中心-下六级代理明细详情
     *
     * @return res
     */
    @Override
    public AgentReportBo.SubordinateDetailsResDto subordinateDetails() {
        BaseParams.HeaderInfo userInfo = userInfoServiceImpl.getHeadLocalData();
        var sub = userCache.subordinateArray(userInfo.getId());
        var resData = new AgentReportBo.SubordinateDetailsResDto();
        // 线程聚合等待(并发请求)
        CompletableFuture.allOf(
                sub.stream().map(
                        productLine -> CompletableFuture.supplyAsync(() -> {
                            var temp = (JSONObject) productLine;
                            List<Integer> uids = JSONObject.parseArray(temp.getJSONArray("uids").toString(), Integer.class);

                            var subDto = new AgentReportBo.SubordinateDetailsResSubDto();
                            var commission = calcCommissionUsd(uids);
                            var bettingTurnOver = calcBettingTurnOver(uids);
                            subDto.setCommission(commission);
                            subDto.setBettingTurnOver(bettingTurnOver);
                            subDto.setNums(uids.size());

                            switch (temp.getString("level")) {
                                case "level1":
                                    resData.setLevel1(subDto);
                                    break;
                                case "level2":
                                    resData.setLevel2(subDto);
                                    break;
                                case "level3":
                                    resData.setLevel3(subDto);
                                    break;
                                case "level4":
                                    resData.setLevel4(subDto);
                                    break;
                                case "level5":
                                    resData.setLevel5(subDto);
                                    break;
                                case "level6":
                                    resData.setLevel6(subDto);
                                    break;
                                case "team":
                                    resData.setCommission(commission);
                                    resData.setBettingTurnOver(bettingTurnOver);
                                    break;
                                default:
                                    break;
                            }
                            return null;
                        })
                ).toArray(CompletableFuture[]::new)
        ).join();

        return resData;
    }

    /**
     * 代理中心-CommissionRecords
     *
     * @return 佣金流水记录
     */
    @Override
    public ResPage<AgentReportBo.CommissionRecordsResDto> commissionRecords(ReqPage<StartEndTime> reqPage) {
        var user = userInfoServiceImpl.getHeadLocalData();
        var data = reqPage.getData();

        Page<CoinCommission> page = coinCommissionServiceImpl.page(
                reqPage.getPage(),
                new LambdaQueryWrapper<CoinCommission>()
                        .eq(CoinCommission::getUid, user.getId())
                        .gt(Optional.ofNullable(data.getStartTime()).isPresent(), CoinCommission::getCreatedAt, data.getStartTime())
                        .lt(Optional.ofNullable(data.getEndTime()).isPresent(), CoinCommission::getCreatedAt, data.getEndTime())
                        .orderByDesc(CoinCommission::getCreatedAt)
        );
        var commissionRecordsResDtoPage = BeanConvertUtils.copyPageProperties(
                page,
                AgentReportBo.CommissionRecordsResDto::new,
                (ori, dest) -> {
                    dest.setBettingTurnOver(ori.getSubBetTrunover());
                    dest.setCommissionUsd(ori.getCoin());
                    dest.setUsername(ori.getSubUsername());
                }
        );
        return ResPage.get(commissionRecordsResDtoPage);
    }

    /**
     * 佣金可提现金额转账
     *
     * @param dto dto
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void coinTransfer(AgentReportBo.CoinTransfer dto) {
        var header = userInfoServiceImpl.getHeadLocalData();
        var user = userServiceImpl.getById(header.getId());
        if (dto.getCoin().compareTo(user.getCoinCommission()) > 0) {
            throw new BusinessException(CodeInfo.API_COIN_COMMISSION_INSUFFICIENT);
        }

        User userInfo = userCache.getUserInfo(user.getId());
        // 修改佣金钱包金额
        boolean update = userServiceImpl.lambdaUpdate().setSql("coin_commission = coin_commission + " + dto.getCoin().negate())
                .set(User::getUpdatedAt, DateUtils.getCurrentTime())
                .eq(User::getUpdatedAt, user.getUpdatedAt())
                .eq(User::getId, user.getId())
                .update();
        if (!update) {
            throw new BusinessException(CodeInfo.UPDATE_ERROR);
        }

        var coin = userCoinBase.getUserCoin(user.getId());
        Integer now = DateUtils.getCurrentTime();
        CoinDepositRecord coinDepositRecord = new CoinDepositRecord();
        coinDepositRecord.setOrderId("T" + TextUtils.generateTransferOrderId());
        coinDepositRecord.setUid(user.getId());
        coinDepositRecord.setUsername(userInfo.getUsername());
        coinDepositRecord.setCode(COMM_TRANSFER);
        coinDepositRecord.setPlatName(COMM_TRANSFER);
        coinDepositRecord.setCoinBefore(coin);
        coinDepositRecord.setPayAmount(dto.getCoin());
        coinDepositRecord.setExchangeRate(BigDecimal.ONE);
        coinDepositRecord.setRealAmount(dto.getCoin());
        coinDepositRecord.setCurrency(CurrencyEm.USD.getValue());
        coinDepositRecord.setCategory(1);
        coinDepositRecord.setStatus(1);
        coinDepositRecord.setCreatedAt(now);
        coinDepositRecord.setUpdatedAt(now);
        coinDepositRecordServiceImpl.save(coinDepositRecord);
        //充值用户加金额
        UserCoinChangeParams.UserCoinChangeReq userCoinChangeReq = new UserCoinChangeParams.UserCoinChangeReq();
        userCoinChangeReq.setUid(user.getId());
        userCoinChangeReq.setCoin(dto.getCoin());
        userCoinChangeReq.setCategory(UserCoinChangeParams.FlowCategoryTypeEnum.COMM_COIN_TRANSFER.getCode());
        userCoinChangeReq.setOutIn(1);
        userCoinChangeReq.setReferId(coinDepositRecord.getId());
        userCoinBase.userCoinChange(userCoinChangeReq);

    }

    /**
     * 佣金层级列表
     *
     * @return res
     */
    @Override
    public List<UserBo.AgentCommissionRateDto> commissionRateList() {
        return BeanConvertUtils.copyListProperties(
                agentCommissionRateServiceImpl.lambdaQuery().orderByAsc(AgentCommissionRate::getAgentLevel).list(),
                UserBo.AgentCommissionRateDto::new,
                (ori, dest) -> dest.setAgentLevelRate(ori.getAgentLevelRate().toString())
        );
    }
}
