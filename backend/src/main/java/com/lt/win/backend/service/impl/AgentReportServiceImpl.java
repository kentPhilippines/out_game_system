package com.lt.win.backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lt.win.backend.io.bo.user.AgentCenterParameter;
import com.lt.win.backend.service.IAgentReportService;
import com.lt.win.dao.generator.po.CoinCommission;
import com.lt.win.dao.generator.po.User;
import com.lt.win.dao.generator.service.CoinCommissionService;
import com.lt.win.dao.generator.service.UserService;
import com.lt.win.service.base.AgentBase;
import com.lt.win.service.base.CoinLogStatisticBase;
import com.lt.win.service.base.UserCoinBase;
import com.lt.win.service.cache.redis.UserCache;
import com.lt.win.service.exception.BusinessException;
import com.lt.win.service.io.bo.AgentReportBo;
import com.lt.win.service.io.bo.SuperAgentBo;
import com.lt.win.service.io.constant.ConstData;
import com.lt.win.utils.BeanConvertUtils;
import com.lt.win.utils.DateUtils;
import com.lt.win.utils.components.pagination.ReqPage;
import com.lt.win.utils.components.pagination.ResPage;
import com.lt.win.utils.components.response.CodeInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import static java.util.Objects.nonNull;


/**
 * 代理模块 接口实现
 *
 * @author David
 * @since 2020/3/1
 */
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AgentReportServiceImpl implements IAgentReportService {
    private final UserCache userCache;
    private final UserCoinBase userCoinBase;
    private final CoinCommissionService coinCommissionServiceImpl;
    private final AgentBase agentBase;
    private final UserService userServiceImpl;
    private final CoinLogStatisticBase coinLogStatisticBase;


    /**
     * 报表管理 -> 代理报表 -> 列表
     *
     * @param dto dto
     * @return bo
     */
    @Override
    public ResPage<SuperAgentBo.AgentListResDto> agent(ReqPage<AgentReportBo.AgentReqDto> dto) {
        var data = dto.getData();

        // 代理数据分页
        var result = BeanConvertUtils.copyPageProperties(
                userServiceImpl.page(
                        dto.getPage(),
                        new LambdaQueryWrapper<User>()
                                .eq(User::getIsPromoter, 1)
                                .eq(Optional.ofNullable(data.getStatus()).isPresent(), User::getStatus, data.getStatus())
                                .like(Optional.ofNullable(data.getUsername()).isPresent(), User::getUsername, data.getUsername())
                                .like(Optional.ofNullable(data.getSupUsername1()).isPresent(), User::getSupUsername1, data.getSupUsername1())
                ),
                SuperAgentBo.AgentListResDto::new
        );

        CompletableFuture.allOf(result.getRecords().stream().map(o -> CompletableFuture.supplyAsync(() -> {
            // 余额统计
            o.setCoin(userCoinBase.getUserCoin(o.getId()));
            // 团队人数
            var subordinate = userCache.subordinate(o.getId());
            o.setTeamNums(subordinate.getTeam().size());
            o.setDirectNums(subordinate.getLevel1().size());
            if (subordinate.getTeam().size() == 0) {
                return null;
            }

            // 团队总余额
            var balance = agentBase.calcTeamBalance(subordinate.getTeam());
            o.setCoinBalance(balance);
            // 佣金
            var commission = (BigDecimal) coinCommissionServiceImpl.getMap(
                            new QueryWrapper<CoinCommission>()
                                    .select("ifNull(SUM(coin), 0) as coin")
                                    .eq("uid", o.getId()))
                    .getOrDefault("coin", BigDecimal.ZERO);
            o.setCoinCommission(commission);

            agentBase.statistics(o, subordinate.getTeam(), data.getStartTime(), data.getEndTime());
            return null;
        })).toArray(CompletableFuture[]::new)).join();

        return ResPage.get(result);
    }

    /**
     * 报表管理 -> 代理报表 ->  转移
     *
     * @param dto dto
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void agentTransfer(AgentReportBo.AgentTransferReqDto dto) {
        var from = userServiceImpl.lambdaQuery().eq(User::getUsername, dto.getFrom()).one();
        if (Optional.ofNullable(from).isEmpty()) {
            throw BusinessException.buildException(CodeInfo.ACCOUNT_NOT_EXISTS_USER_NAME, dto.getFrom());
        }

        var to = userServiceImpl.lambdaQuery().eq(User::getUsername, dto.getTo()).one();
        if (Optional.ofNullable(to).isEmpty()) {
            throw BusinessException.buildException(CodeInfo.ACCOUNT_NOT_EXISTS, dto.getTo());
        } else if (to.getSupLevelTop() == ConstData.ZERO) {
            throw BusinessException.buildException(CodeInfo.AGENT_TRANSFER_MEMBER_TO_SUPER_AGENT_INVALID, dto.getTo());
        }
        var now = DateUtils.getCurrentTime();

        // 被转移代理降级为会员, 转移至用户升级为代理
        from.setIsPromoter(0);
        userServiceImpl.updateById(from);
        if (to.getIsPromoter() != 1) {
            to.setIsPromoter(1);
            userServiceImpl.updateById(to);
        }
        // level1
        userServiceImpl.lambdaUpdate()
                .set(User::getUpdatedAt, now)
                .set(User::getSupUid1, to.getId())
                .set(User::getSupUsername1, to.getUsername())
                .set(User::getSupUid2, to.getSupUid1())
                .set(User::getSupUid3, to.getSupUid2())
                .set(User::getSupUid4, to.getSupUid3())
                .set(User::getSupUid5, to.getSupUid4())
                .set(User::getSupUid6, to.getSupUid5())
                .eq(User::getSupUid1, from.getId())
                .update();
        userServiceImpl.lambdaUpdate()
                .set(User::getUpdatedAt, now)
                .set(User::getSupUid2, to.getId())
                .set(User::getSupUid3, to.getSupUid1())
                .set(User::getSupUid4, to.getSupUid2())
                .set(User::getSupUid5, to.getSupUid3())
                .set(User::getSupUid6, to.getSupUid5())
                .eq(User::getSupUid2, from.getId())
                .update();
        userServiceImpl.lambdaUpdate()
                .set(User::getUpdatedAt, now)
                .set(User::getSupUid3, to.getId())
                .set(User::getSupUid4, to.getSupUid1())
                .set(User::getSupUid5, to.getSupUid2())
                .set(User::getSupUid6, to.getSupUid3())
                .eq(User::getSupUid3, from.getId())
                .update();
        userServiceImpl.lambdaUpdate()
                .set(User::getUpdatedAt, now)
                .set(User::getSupUid4, to.getId())
                .set(User::getSupUid5, to.getSupUid1())
                .set(User::getSupUid6, to.getSupUid2())
                .eq(User::getSupUid4, from.getId())
                .update();
        userServiceImpl.lambdaUpdate()
                .set(User::getUpdatedAt, now)
                .set(User::getSupUid5, to.getId())
                .set(User::getSupUid6, to.getSupUid1())
                .eq(User::getSupUid5, from.getId())
                .update();
        userServiceImpl.lambdaUpdate()
                .set(User::getUpdatedAt, now)
                .set(User::getSupUid6, to.getId())
                .eq(User::getSupUid6, from.getId())
                .update();
        // 删除用户缓存
        userCache.delUserCache(to.getId());
        userCache.delUserCache(from.getId());
    }


    /**
     * 报表管理 -> 代理报表 -> 团队详情 -> 列表
     *
     * @param dto dto
     * @return bo
     */
    @Override
    public ResPage<AgentReportBo.AgentTeamResDto> agentTeam(ReqPage<AgentReportBo.AgentTeamReqDto> dto) {
        var data = dto.getData();
        // 获取代理信息
        var agent = userServiceImpl.getById(data.getAgentId());
        if (Optional.ofNullable(agent).isEmpty()) {
            throw BusinessException.buildException(CodeInfo.ACCOUNT_NOT_EXISTS);
        }
        // 获取所有下级成员列表
        var sub = userCache.subordinate(agent.getId(), data.getAgentLevel());
        if (sub.isEmpty()) {
            return new ResPage<>();
        }

        // 获取分页数据
        var result = BeanConvertUtils.copyPageProperties(
                userServiceImpl.page(
                        dto.getPage(),
                        new LambdaQueryWrapper<User>()
                                .in(User::getId, sub)
                                .like(Optional.ofNullable(data.getUsername()).isPresent(), User::getUsername, data.getUsername())
                                .eq(Optional.ofNullable(data.getStatus()).isPresent(), User::getStatus, data.getStatus())
                ),
                AgentReportBo.AgentTeamResDto::new
        );

        var subordinate = userCache.subordinate(data.getAgentId());
        CompletableFuture.allOf(result.getRecords().stream().map(o -> CompletableFuture.supplyAsync(() -> {
            if (Optional.ofNullable(data.getAgentLevel()).isPresent()) {
                o.setAgentLevel(data.getAgentLevel());
            } else if (subordinate.getLevel1().contains(o.getId())) {
                o.setAgentLevel(1);
            } else if (subordinate.getLevel2().contains(o.getId())) {
                o.setAgentLevel(2);
            } else if (subordinate.getLevel3().contains(o.getId())) {
                o.setAgentLevel(3);
            } else if (subordinate.getLevel4().contains(o.getId())) {
                o.setAgentLevel(4);
            } else if (subordinate.getLevel5().contains(o.getId())) {
                o.setAgentLevel(5);
            } else if (subordinate.getLevel6().contains(o.getId())) {
                o.setAgentLevel(6);
            }
            // 余额统计
            var coin = userCoinBase.getUserCoin(o.getId());
            o.setCoin(coin);

            var statistics = coinLogStatisticBase.statistics(List.of(o.getId()), data.getStartTime(), data.getEndTime());
            o.setCoinDeposit(statistics.getCoinDeposit());
            o.setCoinWithdrawal(statistics.getCoinWithdrawal());
            o.setCoinWithdrawalReally(statistics.getCoinWithdrawal().subtract(statistics.getCoinWithdrawalRefund()));
            o.setCoinAdjust(statistics.getCoinReconciliation());
            o.setCoinBet(statistics.getCoinBet().subtract(statistics.getCoinRefund()));
            o.setCoinBetBonus(statistics.getCoinPayOut());
            o.setCoinProfit(statistics.getCoinBet().subtract(statistics.getCoinRefund()).subtract(statistics.getCoinPayOut()));
            return null;
        })).toArray(CompletableFuture[]::new)).join();


        return ResPage.get(result);
    }

    /**
     * 报表管理 -> 代理报表 -> 团队详情 -> 汇总
     *
     * @param dto dto
     * @return bo
     */
    @Override
    public AgentReportBo.MemberOrAgentTeamSummaryResDto agentTeamSummary(AgentReportBo.AgentTeamReqDto dto) {
        List<Integer> sub;
        if (Optional.ofNullable(dto.getIds()).isEmpty()) {
            // 获取下级会员UID列表
            var agent = userServiceImpl.getById(dto.getAgentId());
            if (Optional.ofNullable(agent).isEmpty()) {
                throw BusinessException.buildException(CodeInfo.ACCOUNT_NOT_EXISTS);
            }
            sub = userCache.subordinate(agent.getId(), dto.getAgentLevel());
        } else {
            sub = dto.getIds();
        }

        return memberSummery(AgentReportBo.AgentReqDto.builder().ids(sub).build());
    }

    /**
     * 代理报表 -> 佣金收益
     *
     * @param dto dto
     * @return res
     */
    @Override
    public AgentCenterParameter.AgentCommissionResBody agentCommissionProfit(ReqPage<AgentCenterParameter.AgentCommissionReqBody> dto) {
        var reqDto = dto.getData();
        var queryWrapper = new LambdaQueryWrapper<CoinCommission>()
                //UID
                .eq(nonNull(reqDto.getAgentId()), CoinCommission::getUid, reqDto.getAgentId())
                //用户名
                .eq(nonNull(reqDto.getUsername()), CoinCommission::getUsername, reqDto.getUsername())
                //佣金状态
                .eq(nonNull(reqDto.getStatus()), CoinCommission::getStatus, reqDto.getStatus())
                //开始时间
                .ge(nonNull(reqDto.getStartTime()), CoinCommission::getCreatedAt, reqDto.getStartTime())
                //结束时间
                .le(nonNull(reqDto.getEndTime()), CoinCommission::getCreatedAt, reqDto.getEndTime());
        var page = coinCommissionServiceImpl.page(dto.getPage(), queryWrapper);
        var returnPage = BeanConvertUtils.copyPageProperties(page, AgentCenterParameter.AgentCommissionList::new, (commission, agentCommission) -> agentCommission.setSubBetTrunover(String.valueOf(commission.getSubBetTrunover().setScale(2, RoundingMode.DOWN))));
        //累计金额
        var totalCoin = BigDecimal.ZERO;
        //总投注额
        var totalBetCoin = BigDecimal.ZERO;
        var commissionList = coinCommissionServiceImpl.list(queryWrapper);
        if (!CollectionUtils.isEmpty(commissionList)) {
            totalCoin = commissionList.stream().map(CoinCommission::getCoin).reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
            totalBetCoin = commissionList.stream().map(CoinCommission::getSubBetTrunover).reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
        }
        return AgentCenterParameter.AgentCommissionResBody.builder()
                .agentCommissionResPage(ResPage.get(returnPage))
                .totalCoin(totalCoin)
                .totalBetCoin(totalBetCoin)
                .build();
    }

    /**
     * 报表管理 -> 团队报表 -> 表头
     *
     * @param dto dto
     * @return bo
     */
    private AgentReportBo.MemberOrAgentTeamSummaryResDto memberSummery(AgentReportBo.AgentReqDto dto) {
        var ids = dto.getIds();

        var statistics = coinLogStatisticBase.statistics(ids, dto.getStartTime(), dto.getEndTime());
        return AgentReportBo.MemberOrAgentTeamSummaryResDto.builder()
                .coinDeposit(statistics.getCoinDeposit())
                .coinWithdrawal(statistics.getCoinWithdrawal())
                .coinWithdrawalReally(statistics.getCoinWithdrawal().subtract(statistics.getCoinWithdrawalRefund()))
                .coinAdjust(statistics.getCoinReconciliation())
                .coinBet(statistics.getCoinBet().subtract(statistics.getCoinRefund()))
                .coinBetBonus(statistics.getCoinPayOut())
                .coinProfit(statistics.getCoinBet().subtract(statistics.getCoinRefund()).subtract(statistics.getCoinPayOut()))
                .build();
    }
}
