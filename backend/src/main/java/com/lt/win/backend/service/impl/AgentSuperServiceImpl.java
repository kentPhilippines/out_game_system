package com.lt.win.backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lt.win.backend.service.IAgentSuperService;
import com.lt.win.dao.generator.po.User;
import com.lt.win.dao.generator.service.UserService;
import com.lt.win.service.base.AgentBase;
import com.lt.win.service.base.UserCoinBase;
import com.lt.win.service.cache.redis.UserCache;
import com.lt.win.service.exception.BusinessException;
import com.lt.win.service.io.bo.AgentReportBo;
import com.lt.win.service.io.bo.SuperAgentBo;
import com.lt.win.service.io.constant.ConstData;
import com.lt.win.utils.BeanConvertUtils;
import com.lt.win.utils.PasswordUtils;
import com.lt.win.utils.TextUtils;
import com.lt.win.utils.components.pagination.ReqPage;
import com.lt.win.utils.components.pagination.ResPage;
import com.lt.win.utils.components.response.CodeInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;


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
    private final AgentBase agentBase;
    private final UserService userServiceImpl;
    private final UserCoinBase userCoinBase;

    /**
     * 总后台-会员管理-代理列表
     *
     * @param dto dto
     * @return bo
     */
    @Override
    public ResPage<SuperAgentBo.ListResDto> list(ReqPage<SuperAgentBo.ListReqDto> dto) {
        var data = dto.getData();
        var page = userServiceImpl.page(
                dto.getPage(),
                new LambdaQueryWrapper<User>()
                        .like(Optional.ofNullable(data.getUsername()).isPresent(), User::getUsername, data.getUsername())
                        .like(Optional.ofNullable(data.getEmail()).isPresent(), User::getEmail, data.getEmail())
                        .like(Optional.ofNullable(data.getMobile()).isPresent(), User::getMobile, data.getMobile())
                        .eq(Optional.ofNullable(data.getStatus()).isPresent(), User::getStatus, data.getStatus())
                        .ge(Optional.ofNullable(data.getStartTime()).isPresent(), User::getCreatedAt, data.getStartTime())
                        .le(Optional.ofNullable(data.getEndTime()).isPresent(), User::getCreatedAt, data.getEndTime())
                        .eq(User::getSupLevelTop, 0));
        var result = BeanConvertUtils.copyPageProperties(page, SuperAgentBo.ListResDto::new);
        return ResPage.get(result);
    }

    /**
     * 总后台-会员管理-代理列表-新增或修改
     *
     * @param dto dto
     */
    @Override
    public void saveOrUpdate(SuperAgentBo.SaveOrUpdateReqDto dto) {
        var user = BeanConvertUtils.beanCopy(dto, User::new);
        if (Optional.ofNullable(dto.getId()).isEmpty()) {
            // 判断会员账号
            var count = userServiceImpl.lambdaQuery().eq(User::getUsername, dto.getUsername()).count();
            if (count > 0) {
                throw new BusinessException(CodeInfo.ACCOUNT_EXISTS);
            }
            // 判断邮箱号码
            count = userServiceImpl.lambdaQuery().eq(Optional.ofNullable(dto.getEmail()).isPresent(), User::getEmail, dto.getEmail()).count();
            if (count > 0) {
                throw new BusinessException(CodeInfo.EMAIL_EXISTS);
            }

            // 邀请码生成
            var promoCode = TextUtils.generateRandomString(6).toUpperCase();
            while (userServiceImpl.lambdaQuery().eq(User::getPromoCode, promoCode).count() != ConstData.ZERO) {
                promoCode = TextUtils.generateRandomString(6).toUpperCase();
            }
            user.setPromoCode(promoCode);
            user.setSupLevelTop(ConstData.ZERO);
        } else {
            // 不可以修改用户名
            user.setUsername(null);
            userCache.delUserCache(user.getId(), true);
        }

        if (Optional.ofNullable(dto.getPasswordHash()).isPresent()) {
            user.setPasswordHash(PasswordUtils.generatePasswordHash(dto.getPasswordHash()));
        }

        userServiceImpl.saveOrUpdate(user);
    }

    /**
     * 总后台-报表中心-代理报表-列表
     *
     * @param dto dto
     * @return res
     */
    @Override
    public ResPage<SuperAgentBo.AgentListResDto> agentList(ReqPage<SuperAgentBo.AgentReportReqDto> dto) {
        var data = dto.getData();

        // 设置用户列表
        List<Integer> team = List.of();
        if (Optional.ofNullable(data.getId()).isPresent()) {
            team = userCache.subordinateSuper(data.getId(), data.getLevel());
            if (team.isEmpty()) {
                return new ResPage<>();
            }
        }

        // 代理数据分页
        var result = BeanConvertUtils.copyPageProperties(
                userServiceImpl.page(
                        dto.getPage(),
                        new LambdaQueryWrapper<User>()
                                .eq(Optional.ofNullable(data.getId()).isEmpty(), User::getSupLevelTop, ConstData.ZERO)
                                .in(Optional.ofNullable(data.getId()).isPresent(), User::getId, team)
                                .eq(Optional.ofNullable(data.getStatus()).isPresent(), User::getStatus, data.getStatus())
                                .like(Optional.ofNullable(data.getUsername()).isPresent(), User::getUsername, data.getUsername())
                                .like(Optional.ofNullable(data.getSupUsername1()).isPresent(), User::getSupUsername1, data.getSupUsername1())
                                .like(Optional.ofNullable(data.getEmail()).isPresent(), User::getEmail, data.getEmail())
                                .like(Optional.ofNullable(data.getMobile()).isPresent(), User::getMobile, data.getMobile())
                                .ge(Optional.ofNullable(data.getStartTime()).isPresent(), User::getCreatedAt, data.getStartTime())
                                .le(Optional.ofNullable(data.getEndTime()).isPresent(), User::getCreatedAt, data.getEndTime())
                ), SuperAgentBo.AgentListResDto::new);
        CompletableFuture.allOf(result.getRecords().stream().map(o -> CompletableFuture.supplyAsync(() -> {
            AgentReportBo.SubordinateList subordinate = new AgentReportBo.SubordinateList();
            if (o.getSupLevelTop() == ConstData.ZERO) {
                subordinate = userCache.subordinateSuper(o.getId());
                // 团队人数
                o.setDirectNums(subordinate.getLevel1().size());
                o.setTeamNums(subordinate.getTeam().size());
                // 团队总余额
                var balance = agentBase.calcTeamBalance(subordinate.getTeam());
                o.setCoinBalance(balance);
                if (subordinate.getTeam().size() == ConstData.ZERO) {
                    return null;
                }
            } else {
                o.setCoin(userCoinBase.getUserCoin(o.getId()));
                subordinate.setTeam(List.of(o.getId()));
            }

            agentBase.statistics(o, subordinate.getTeam(), data.getStartTime(), data.getEndTime());
            return null;
        })).toArray(CompletableFuture[]::new)).join();

        return ResPage.get(result);
    }

    /**
     * 总后台-报表中心-代理报表-汇总
     *
     * @param dto dto
     * @return res
     */
    @Override
    public SuperAgentBo.AgentReportResDto agentReport(SuperAgentBo.AgentReportReqDto dto) {
        // 设置用户列表
        List<Integer> team = new ArrayList<>();
        if (Optional.ofNullable(dto.getId()).isPresent()) {
            team = userCache.subordinateSuper(dto.getId(), dto.getLevel());
            if (team.isEmpty()) {
                return new SuperAgentBo.AgentReportResDto();
            }
        }

        var filterIds = userServiceImpl.lambdaQuery()
                .eq(Optional.ofNullable(dto.getId()).isEmpty(), User::getSupLevelTop, ConstData.ZERO)
                .in(Optional.ofNullable(dto.getId()).isPresent(), User::getId, team)
                .eq(Optional.ofNullable(dto.getStatus()).isPresent(), User::getStatus, dto.getStatus())
                .like(Optional.ofNullable(dto.getUsername()).isPresent(), User::getUsername, dto.getUsername())
                .like(Optional.ofNullable(dto.getEmail()).isPresent(), User::getEmail, dto.getEmail())
                .like(Optional.ofNullable(dto.getMobile()).isPresent(), User::getMobile, dto.getMobile())
                .like(Optional.ofNullable(dto.getSupUsername1()).isPresent(), User::getSupUsername1, dto.getSupUsername1())
                .ge(Optional.ofNullable(dto.getStartTime()).isPresent(), User::getCreatedAt, dto.getStartTime())
                .le(Optional.ofNullable(dto.getEndTime()).isPresent(), User::getCreatedAt, dto.getEndTime())
                .list()
                .stream()
                .map(User::getId)
                .collect(Collectors.toList());
        if (filterIds.isEmpty()) {
            return new SuperAgentBo.AgentReportResDto();
        }
        // 合并所有代理线的会员列表
        if (Optional.ofNullable(dto.getId()).isEmpty()) {
            for (int i = 0; i < filterIds.size(); i++) {
                team.addAll(userCache.subordinateSuper(i).getTeam());
            }
        }

        var response = new SuperAgentBo.AgentListResDto();
        agentBase.statistics(response, filterIds, dto.getStartTime(), dto.getEndTime());
        return BeanConvertUtils.beanCopy(response, SuperAgentBo.AgentReportResDto::new);
    }

    /**
     * 总后台-报表中心-代理报表-代理转移
     *
     * @param dto dto
     */
    @Override
    public void agentTransfer(SuperAgentBo.AgentTransferReqDto dto) {
        var from = userServiceImpl.lambdaQuery()
                .eq(User::getUsername, dto.getFrom())
                .one();
        if (Optional.ofNullable(from).isEmpty()) {
            throw BusinessException.buildException(CodeInfo.AGENT_ACCOUNT_NOT_EXISTS, dto.getFrom());
        } else if (from.getSupLevelTop() != ConstData.ZERO) {
            throw BusinessException.buildException(CodeInfo.AGENT_ACCOUNT_INVALID, dto.getFrom());
        }

        var to = userServiceImpl.lambdaQuery()
                .eq(User::getUsername, dto.getTo())
                .one();
        if (Optional.ofNullable(to).isEmpty()) {
            throw BusinessException.buildException(CodeInfo.AGENT_ACCOUNT_NOT_EXISTS, dto.getTo());
        } else if (from.getSupLevelTop() != ConstData.ZERO) {
            throw BusinessException.buildException(CodeInfo.AGENT_ACCOUNT_INVALID, dto.getTo());
        }

        userServiceImpl.lambdaUpdate()
                .set(User::getSupUidTop, to.getId())
                .set(User::getSupUsernameTop, to.getUsername())
                .eq(User::getSupUidTop, from.getId())
                .update();
        userServiceImpl.lambdaUpdate()
                .set(User::getSupUid1, to.getId())
                .set(User::getSupUsername1, to.getUsername())
                .eq(User::getSupUid1, from.getId())
                .update();
        userServiceImpl.lambdaUpdate().set(User::getSupUid2, to.getId()).eq(User::getSupUid2, from.getId()).update();
        userServiceImpl.lambdaUpdate().set(User::getSupUid3, to.getId()).eq(User::getSupUid3, from.getId()).update();
        userServiceImpl.lambdaUpdate().set(User::getSupUid4, to.getId()).eq(User::getSupUid4, from.getId()).update();
        userServiceImpl.lambdaUpdate().set(User::getSupUid5, to.getId()).eq(User::getSupUid5, from.getId()).update();
        userServiceImpl.lambdaUpdate().set(User::getSupUid6, to.getId()).eq(User::getSupUid6, from.getId()).update();

        userCache.delUserCache(from.getId());
        userCache.delUserCache(to.getId());
    }
}
