package com.lt.win.apiend.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lt.win.apiend.io.bo.UserParams;
import com.lt.win.apiend.io.dto.mapper.UserInfoResDto;
import com.lt.win.apiend.service.IAgentSuperService;
import com.lt.win.apiend.service.IUserInfoService;
import com.lt.win.dao.generator.po.CoinDepositRecord;
import com.lt.win.dao.generator.po.CoinWithdrawalRecord;
import com.lt.win.dao.generator.po.User;
import com.lt.win.dao.generator.service.CoinDepositRecordService;
import com.lt.win.dao.generator.service.CoinWithdrawalRecordService;
import com.lt.win.dao.generator.service.UserService;
import com.lt.win.service.base.AgentBase;
import com.lt.win.service.base.CoinLogStatisticBase;
import com.lt.win.service.base.GoogleAuthBase;
import com.lt.win.service.base.UserCoinBase;
import com.lt.win.service.cache.redis.ConfigCache;
import com.lt.win.service.cache.redis.UserCache;
import com.lt.win.service.exception.BusinessException;
import com.lt.win.service.impl.BetslipsServiceImpl;
import com.lt.win.service.io.bo.SuperAgentBo;
import com.lt.win.service.io.constant.ConstData;
import com.lt.win.service.io.dto.UserCoinChangeParams;
import com.lt.win.service.io.enums.StatusEnum;
import com.lt.win.utils.BeanConvertUtils;
import com.lt.win.utils.DateNewUtils;
import com.lt.win.utils.PasswordUtils;
import com.lt.win.utils.components.pagination.ReqPage;
import com.lt.win.utils.components.pagination.ResPage;
import com.lt.win.utils.components.response.CodeInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import static com.lt.win.service.io.constant.ConstData.INVALID_LOGIN_MAX_TIMES;


/**
 * 超级代理-后台直接添加
 *
 * @author David
 * @since 2022/11/11
 */
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AgentSuperServiceImpl implements IAgentSuperService {
    private final UserCache userCache;
    private final GoogleAuthBase googleAuthBase;
    private final UserService userServiceImpl;
    private final IUserInfoService userInfoServiceImpl;
    private final ConfigCache configCache;
    private final AgentBase agentBase;
    private final CoinLogStatisticBase coinLogStatisticBase;
    private final UserCoinBase userCoinBase;
    private final BetslipsServiceImpl betslipsServiceImpl;
    private final CoinDepositRecordService coinDepositRecordServiceImpl;
    private final CoinWithdrawalRecordService coinWithdrawalRecordServiceImpl;

    /**
     * 代理线-登陆
     *
     * @param dto dto
     * @return res
     */
    @Override
    public UserInfoResDto login(UserParams.LoginReqDto dto) {
        Long times = userCache.modifyInvalidLoginTimes(dto.getUsername(), 0L);
        if (times >= INVALID_LOGIN_MAX_TIMES) {
            throw new BusinessException(CodeInfo.LOGIN_INVALID_OVER_LIMIT);
        }

        var user = userServiceImpl.lambdaQuery()
                .eq(User::getUsername, dto.getUsername())
                .eq(User::getSupLevelTop, ConstData.ZERO)
                .one();
        if (Optional.ofNullable(user).isEmpty()) {
            throw new BusinessException(CodeInfo.ACCOUNT_NOT_EXISTS);
        } else if (user.getSupLevelTop() != ConstData.ZERO) {
            throw new BusinessException(CodeInfo.ACCOUNT_NOT_EXISTS);
        } else if (user.getStatus() != StatusEnum.USER_STATUS.NORMAL.getCode()) {
            throw new BusinessException(CodeInfo.ACCOUNT_STATUS_INVALID);
        }

        if (Boolean.FALSE.equals(PasswordUtils.validatePasswordHash(dto.getPassword(), user.getPasswordHash()))) {
            userCache.modifyInvalidLoginTimes(dto.getUsername(), 1L);
            throw new BusinessException(CodeInfo.PASSWORD_INVALID);
        }

        // 验证Google Auth Code
        googleAuthBase.checkGoogleCode(user.getId(), dto.getGoogleCode() + "");

        var response = BeanConvertUtils.beanCopy(user, UserInfoResDto::new);

        String jwtToken = userInfoServiceImpl.genJwtToken(user.getId(), user.getUsername());
        response.setApiToken(jwtToken);
        response.setGoogleCodeStatus(googleAuthBase.getGoogleAuthStatus(user.getId()));

        // 更新Redis缓存
        userCache.setUserToken(user.getId(), jwtToken);
        userCache.delInvalidLoginTimes(user.getId());
        userServiceImpl.lambdaUpdate().eq(User::getId, user.getId()).set(User::getUpdatedAt, DateNewUtils.now()).update();

        return response;
    }

    /**
     * 代理线-重置密码
     *
     * @param dto dto
     * @return res
     */
    @Override
    public UserInfoResDto resetPassword(SuperAgentBo.ResetPasswordReqDto dto) {
        var user = userServiceImpl.lambdaQuery()
                .eq(User::getUsername, dto.getUsername())
                .eq(User::getSupLevelTop, ConstData.ZERO)
                .one();
        if (Optional.ofNullable(user).isEmpty()) {
            throw new BusinessException(CodeInfo.ACCOUNT_NOT_EXISTS);
        } else if (user.getSupLevelTop() != ConstData.ZERO) {
            throw new BusinessException(CodeInfo.ACCOUNT_NOT_EXISTS);
        } else if (user.getStatus() != StatusEnum.USER_STATUS.NORMAL.getCode()) {
            throw new BusinessException(CodeInfo.ACCOUNT_STATUS_INVALID);
        }

        if (Boolean.FALSE.equals(PasswordUtils.validatePasswordHash(dto.getOldPassword(), user.getPasswordHash()))) {
            throw new BusinessException(CodeInfo.PASSWORD_INVALID);
        }

        user.setPasswordHash(PasswordUtils.generatePasswordHash(dto.getPassword()));
        userServiceImpl.updateById(user);
        var response = BeanConvertUtils.beanCopy(user, UserInfoResDto::new);
        String jwtToken = userInfoServiceImpl.genJwtToken(user.getId(), user.getUsername());
        response.setApiToken(jwtToken);
        response.setGoogleCodeStatus(googleAuthBase.getGoogleAuthStatus(user.getId()));

        // 更新Redis缓存
        userCache.setUserToken(user.getId(), jwtToken);
        userCache.delInvalidLoginTimes(user.getId());
        return response;
    }

    /**
     * 代理线-代理中心-数据展示
     *
     * @param dto dto
     * @return res
     */
    @Override
    public SuperAgentBo.WebAgentReportResDto report(SuperAgentBo.WebAgentReportReqDto dto) {
        var header = userInfoServiceImpl.getHeadLocalData();
        var user = userCache.getUserInfo(header.getId());
        var response = new SuperAgentBo.WebAgentReportResDto();
        response.setInviteCode(user.getPromoCode());
        response.setInviteLink(String.format(configCache.getInviteLink(), user.getPromoCode()));
        // 获取指定层级的ID列表
        var ids = userCache.subordinateSuper(user.getId(), dto.getLevel());
        response.setTeamNums(ids.size());
        if (ids.isEmpty()) {
            return response;
        }

        agentBase.statistics(response, ids, dto.getStartTime(), dto.getEndTime());
        // 新增会员
        var teamNewNums = userServiceImpl.lambdaQuery()
                .in(User::getId, ids)
                .ge(Optional.ofNullable(dto.getStartTime()).isPresent(), User::getCreatedAt, dto.getStartTime())
                .le(Optional.ofNullable(dto.getEndTime()).isPresent(), User::getCreatedAt, dto.getEndTime())
                .count();
        response.setTeamNewNums(teamNewNums);
        // 首充数据
        var firstDeposit = agentBase.calcTeamFirstDeposit(ids, dto.getStartTime(), dto.getEndTime());
        response.setDepositFirstNums(firstDeposit.getCount());
        response.setCoinDepositFirst(firstDeposit.getCoin());
        // 获取充值、提现、投注会员
        response.setDepositNums(coinLogStatisticBase.getUserCount(ids, dto.getStartTime(), dto.getEndTime(), UserCoinChangeParams.FlowCategoryTypeEnum.BANK_SAVINGS));
        response.setWithdrawalNums(coinLogStatisticBase.getUserCount(ids, dto.getStartTime(), dto.getEndTime(), UserCoinChangeParams.FlowCategoryTypeEnum.WITHDRAWAL));
        response.setBetNums(coinLogStatisticBase.getUserCount(ids, dto.getStartTime(), dto.getEndTime(), UserCoinChangeParams.FlowCategoryTypeEnum.BET));
        return response;
    }

    /**
     * 代理线-团队详情-列表
     *
     * @param dto dto
     * @return res
     */
    @Override
    public ResPage<SuperAgentBo.AgentTeamListResDto> teamList(ReqPage<SuperAgentBo.AgentTeamListReqDto> dto) {
        var data = dto.getData();
        var header = userInfoServiceImpl.getHeadLocalData();
        var team = userCache.subordinateSuper(header.getId(), data.getLevel());
        if (team.isEmpty()) {
            return new ResPage<>();
        }

        // 代理数据分页
        var result = BeanConvertUtils.copyPageProperties(
                userServiceImpl.page(
                        dto.getPage(),
                        new LambdaQueryWrapper<User>()
                                .in(User::getId, team)
                                .eq(Optional.ofNullable(data.getStatus()).isPresent(), User::getStatus, data.getStatus())
                                .like(Optional.ofNullable(data.getUsername()).isPresent(), User::getUsername, data.getUsername())
                                .like(Optional.ofNullable(data.getSupUsername1()).isPresent(), User::getSupUsername1, data.getSupUsername1())
                ), SuperAgentBo.AgentTeamListResDto::new);
        CompletableFuture.allOf(result.getRecords().stream().map(o -> CompletableFuture.supplyAsync(() -> {
            o.setCoin(userCoinBase.getUserCoin(o.getId()));
            if (o.getSupLevelTop() > ConstData.THREE) {
                o.setSupLevelTop(ConstData.NINE);
            }
            agentBase.statistics(o, List.of(o.getId()), data.getStartTime(), data.getEndTime());
            return null;
        })).toArray(CompletableFuture[]::new)).join();

        return ResPage.get(result);
    }

    /**
     * 代理线-团队详情-汇总
     *
     * @param dto dto
     * @return res
     */
    @Override
    public SuperAgentBo.AgentReportResDto teamSummary(SuperAgentBo.AgentReportReqDto dto) {
        var header = userInfoServiceImpl.getHeadLocalData();
        var team = userCache.subordinateSuper(header.getId(), dto.getLevel());
        var response = new SuperAgentBo.AgentReportResDto();
        if (team.isEmpty()) {
            return response;
        }

        agentBase.statistics(response, team, dto.getStartTime(), dto.getEndTime());
        return response;
    }

    /**
     * 代理线-财务管理-投注记录
     *
     * @param dto dto
     * @return res
     */
    @Override
    public ResPage<SuperAgentBo.BetListResDto> betList(ReqPage<SuperAgentBo.BetListReqDto> dto) {
        var data = dto.getData();
        var ids = filter(data.getUsername(), data.getSupUsername1(), data.getLevel());
        if (ids.isEmpty()) {
            return new ResPage<>();
        }

        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
        BoolQueryBuilder queryBuilder1 = QueryBuilders.boolQuery();
        for (Integer uid : ids) {
            queryBuilder1.should(QueryBuilders.termQuery("xbUid", uid));
        }
        queryBuilder.must(queryBuilder1);
        // 排除测试账号
        List<Integer> testUidList = userCache.getTestUidList();
        if (CollectionUtil.isNotEmpty(testUidList)) {
            BoolQueryBuilder queryBuilder2 = QueryBuilders.boolQuery();
            for (Integer uid : testUidList) {
                queryBuilder2.should(QueryBuilders.termQuery("xbUid", uid));
            }
            queryBuilder.mustNot(queryBuilder2);
        }

        if (Optional.ofNullable(data.getGameGroupId()).isPresent()) {
            queryBuilder.must(QueryBuilders.termQuery("gameGroupId", data.getGameGroupId()));
        }
        if (Optional.ofNullable(data.getStatus()).isPresent()) {
            queryBuilder.must(QueryBuilders.termQuery("xbStatus", data.getStatus()));
        }
        if (Objects.nonNull(data.getStartTime()) && Objects.nonNull(data.getEndTime())) {
            queryBuilder.must(QueryBuilders.rangeQuery("createdAt").from(data.getStartTime()).to(data.getEndTime()).includeLower(true).includeUpper(true));
        } else if (Objects.nonNull(data.getStartTime())) {
            queryBuilder.must(QueryBuilders.rangeQuery("createdAt").from(data.getStartTime()).includeLower(true));
        } else if (Objects.nonNull(data.getEndTime())) {
            queryBuilder.must(QueryBuilders.rangeQuery("createdAt").to(data.getEndTime()).includeUpper(true));
        }

        var page = betslipsServiceImpl.queryBets(dto, queryBuilder);
        return ResPage.get(
                BeanConvertUtils.copyPageProperties(
                        page,
                        SuperAgentBo.BetListResDto::new,
                        (ori, dest) -> {
                            var user = userCache.getUserInfo(ori.getXbUid());
                            dest.setUid(ori.getXbUid());
                            dest.setMobile(user.getMobile());
                            dest.setUsername(user.getUsername());
                            dest.setLevelId(user.getLevelId());
                            dest.setSupUsername1(user.getSupUsername1());
                            dest.setStatus(ori.getXbStatus());
                            dest.setSupLevelTop(user.getSupLevelTop());
                            dest.setRegisterAt(user.getCreatedAt());
                            dest.setCoin(ori.getStake());
                            dest.setCoinPayOut(ori.getPayout());
                            dest.setCoinProfit(ori.getXbProfit());
                        }));
    }

    /**
     * 代理线-财务管理-充值记录
     *
     * @param dto dto
     * @return res
     */
    @Override
    public ResPage<SuperAgentBo.DepositListResDto> depositList(ReqPage<SuperAgentBo.DepositListReqDto> dto) {
        var data = dto.getData();
        var ids = filter(data.getUsername(), data.getSupUsername1());
        if (ids.isEmpty()) {
            return new ResPage<>();
        }

        var page = coinDepositRecordServiceImpl.lambdaQuery()
                .eq(CoinDepositRecord::getCategoryCurrency, data.getCategoryCurrency())
                .in(CoinDepositRecord::getUid, ids)
                .eq(Optional.ofNullable(data.getCategoryTransfer()).isPresent(), CoinDepositRecord::getCategoryTransfer, data.getCategoryTransfer())
                .eq(Optional.ofNullable(data.getStatus()).isPresent(), CoinDepositRecord::getStatus, data.getStatus())
                .ge(Optional.ofNullable(data.getStartTime()).isPresent(), CoinDepositRecord::getCreatedAt, data.getStartTime())
                .le(Optional.ofNullable(data.getEndTime()).isPresent(), CoinDepositRecord::getCreatedAt, data.getEndTime())
                .orderByDesc(CoinDepositRecord::getCreatedAt)
                .page(dto.getPage());

        return ResPage.get(
                BeanConvertUtils.copyPageProperties(
                        page,
                        SuperAgentBo.DepositListResDto::new,
                        (ori, dest) -> {
                            var user = userCache.getUserInfo(ori.getUid());
                            dest.setId(ori.getOrderId());
                            dest.setMobile(user.getMobile());
                            dest.setLevelId(user.getLevelId());
                            dest.setSupUsername1(user.getSupUsername1());
                            dest.setSupLevelTop(user.getSupLevelTop());
                            dest.setRegisterAt(user.getCreatedAt());
                            dest.setCoinApply(ori.getPayAmount());
                            dest.setCoinReally(ori.getRealAmount());
                        })
        );
    }

    /**
     * 代理线-财务管理-提现记录
     *
     * @param dto dto
     * @return res
     */
    @Override
    public ResPage<SuperAgentBo.WithdrawalListResDto> withdrawalList(ReqPage<SuperAgentBo.WithdrawalListReqDto> dto) {
        var data = dto.getData();
        var ids = filter(data.getUsername(), data.getSupUsername1());
        if (ids.isEmpty()) {
            return new ResPage<>();
        }

        var page = coinWithdrawalRecordServiceImpl.lambdaQuery()
                .eq(CoinWithdrawalRecord::getCategoryCurrency, data.getCategoryCurrency())
                .in(CoinWithdrawalRecord::getUid, ids)
                .eq(Optional.ofNullable(data.getCategoryTransfer()).isPresent(), CoinWithdrawalRecord::getCategoryTransfer, data.getCategoryTransfer())
                .eq(Optional.ofNullable(data.getStatus()).isPresent(), CoinWithdrawalRecord::getStatus, data.getStatus())
                .ge(Optional.ofNullable(data.getStartTime()).isPresent(), CoinWithdrawalRecord::getCreatedAt, data.getStartTime())
                .le(Optional.ofNullable(data.getEndTime()).isPresent(), CoinWithdrawalRecord::getCreatedAt, data.getEndTime())
                .orderByDesc(CoinWithdrawalRecord::getCreatedAt)
                .page(dto.getPage());

        return ResPage.get(
                BeanConvertUtils.copyPageProperties(
                        page,
                        SuperAgentBo.WithdrawalListResDto::new,
                        (ori, dest) -> {
                            var user = userCache.getUserInfo(ori.getUid());
                            dest.setId(ori.getOrderId());
                            dest.setMobile(user.getMobile());
                            dest.setLevelId(user.getLevelId());
                            dest.setSupUsername1(user.getSupUsername1());
                            dest.setSupLevelTop(user.getSupLevelTop());
                            dest.setRegisterAt(user.getCreatedAt());
                            dest.setCoinApply(ori.getWithdrawalAmount());
                            dest.setCoinReally(ori.getRealAmount());
                        })
        );
    }

    /**
     * 根据条件过滤代理下级会员信息
     *
     * @param username     username
     * @param supUsername1 supUsername1
     * @return res
     */
    private List<Integer> filter(String username, String supUsername1) {
        return filter(username, supUsername1, null);
    }

    /**
     * 根据条件过滤代理下级会员信息
     *
     * @param username     username
     * @param supUsername1 supUsername1
     * @param levelId      levelId
     * @return res
     */
    private List<Integer> filter(String username, String supUsername1, Integer levelId) {
        var header = userInfoServiceImpl.getHeadLocalData();
        List<Integer> team;
        if (Optional.ofNullable(levelId).isPresent()) {
            team = userCache.subordinateSuper(header.getId(), levelId);
        } else {
            team = userCache.subordinateSuper(header.getId()).getTeam();
        }
        if (team.isEmpty()) {
            return team;
        }

        return userServiceImpl.lambdaQuery()
                .in(User::getId, team)
                .like(Optional.ofNullable(username).isPresent(), User::getUsername, username)
                .like(Optional.ofNullable(supUsername1).isPresent(), User::getSupUsername1, supUsername1)
                .list()
                .stream()
                .map(User::getId)
                .collect(Collectors.toList());
    }
}
