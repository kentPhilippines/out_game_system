package com.lt.win.backend.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lt.win.backend.io.bo.user.AgentCenterParameter;
import com.lt.win.backend.service.IAgentCenterService;
import com.lt.win.dao.generator.po.User;
import com.lt.win.dao.generator.service.CoinCommissionService;
import com.lt.win.dao.generator.service.UserService;
import com.lt.win.service.base.UserCoinBase;
import com.lt.win.service.cache.redis.UserCache;
import com.lt.win.service.common.Constant;
import com.lt.win.utils.BeanConvertUtils;
import com.lt.win.utils.components.pagination.ReqPage;
import com.lt.win.utils.components.pagination.ResPage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.IntFunction;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;


/**
 * @author: wells
 * @date: 2020/8/27
 * @description:
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AgentCenterServiceImpl implements IAgentCenterService {
    private static final String SUP_UID = "getSupUid";
    private final UserService userServiceImpl;
    private final CoinCommissionService coinCommissionServiceImpl;
    private final UserCache userCache;
    private final UserCoinBase userCoinBase;

    /**
     * 代理中心-会员列表
     *
     * @param reqBody 请求参数石实例类
     * @return 分页响应实体类
     */
    @Override
    public ResPage<AgentCenterParameter.AgentUserListResBody> agentUserList(ReqPage<AgentCenterParameter.AgentUserListReqBody> reqBody) {
        //角色过滤
        var roleList = List.of(Constant.USER_ROLE_HY, Constant.USER_ROLE_DL);
        var reqDto = reqBody.getData();
        var userCacheInfo = userCache.getUserInfo(Optional.ofNullable(reqDto.getAgentUsername()).orElse(""));
        var agentId = Objects.nonNull(userCacheInfo) ? userCacheInfo.getId() : 0;
        var userList = userServiceImpl.lambdaQuery().in(User::getRole, roleList).list();
        if (CollectionUtils.isEmpty(userList)) {
            return new ResPage<>();
        }
        //会员Id对应的用户信息
        var userMap = userList.stream().collect(Collectors.toMap(User::getId, v -> v));
        Integer usernameToUid = null;
        if (nonNull(reqDto.getUsername())) {
            var userIdInfo = userCache.getUserInfo(Optional.ofNullable(reqDto.getUsername()).orElse(""));
            usernameToUid = Objects.nonNull(userIdInfo) ? userIdInfo.getId() : 0;
        }
        var ids = userList.stream().filter(user -> reqDto.getLevelId() != null && user.getLevelId().equals(reqDto.getLevelId())).map(User::getId).collect(Collectors.toList());
        var queryWrapper = new QueryWrapper<User>().lambda()
                //UID
                .eq(nonNull(reqDto.getUid()), User::getId, reqDto.getUid())
                //用户名
                .eq(nonNull(reqDto.getUsername()) && nonNull(usernameToUid), User::getId, usernameToUid)
                //会员状态
                .eq(nonNull(reqDto.getStatus()), User::getStatus, reqDto.getStatus())
                //开始时间
                .ge(nonNull(reqDto.getStartTime()), User::getCreatedAt, reqDto.getStartTime())
                //结束时间
                .le(nonNull(reqDto.getEndTime()), User::getCreatedAt, reqDto.getEndTime());
        //初始化的用户
        queryWrapper.in(reqDto.getLevelId() == null, User::getId, userMap.keySet());
        //会员等级
        queryWrapper.in(reqDto.getLevelId() != null && !CollectionUtils.isEmpty(ids), User::getId, ids);
        queryWrapper.eq(reqDto.getLevelId() != null && CollectionUtils.isEmpty(ids), User::getId, null);
        getQueryWrapper(reqDto, agentId, queryWrapper);
        //不分页查询用户信息统计团对人数
        var userProfileList = userServiceImpl.lambdaQuery().in(User::getId, userMap.keySet()).list();
        var page = userServiceImpl.page(reqBody.getPage(), queryWrapper);
        //获取代理名字
        IntFunction<String> nameFunction = id -> {
            var user = userMap.get(id);
            if (isNull(user)) {
                return "";
            }
            return user.getUsername();
        };
        var returnPage = BeanConvertUtils.copyPageProperties(page, AgentCenterParameter.AgentUserListResBody::new, (userProfile, agentUserList) -> {
            var user = userMap.get(userProfile.getId());
            BigDecimal userCoin = userCoinBase.getUserCoin(user.getId());
            agentUserList.setSupUid1Name(nameFunction.apply(userProfile.getSupUid1()));
            agentUserList.setSupUid2Name(nameFunction.apply(userProfile.getSupUid2()));
            agentUserList.setSupUid3Name(nameFunction.apply(userProfile.getSupUid3()));
            agentUserList.setSupUid4Name(nameFunction.apply(userProfile.getSupUid4()));
            agentUserList.setSupUid5Name(nameFunction.apply(userProfile.getSupUid5()));
            agentUserList.setSupUid6Name(nameFunction.apply(userProfile.getSupUid6()));
            agentUserList.setCoin(userCoin);
            agentUserList.setUserName(user.getUsername());
            agentUserList.setLevelId(user.getLevelId());
            agentUserList.setUserName(user.getUsername());
            agentUserList.setTeamCount(getTeam(userProfile.getId(), userProfileList));
            agentUserList.setTeamAmount(getTeamAmount(userProfile.getId(), userProfileList, userMap));
        });
        return ResPage.get(returnPage);
    }

    /**
     * 代理id与代理层级
     * 1.代理id与代理层级都有值，则正常查询
     * 2.代理id为空，代理层级不为空，则查询当前层级符合代理id的记录（当前级的id不为0）
     * 3.代理id不为空，代理层级为空，则查询每个层级的id任意一个符合代理id的记录
     *
     * @param reqDto       请求实例
     * @param agentId      代理ID
     * @param queryWrapper 构造条件参数
     */
    private void getQueryWrapper(AgentCenterParameter.AgentUserListReqBody reqDto, Integer agentId, LambdaQueryWrapper<User> queryWrapper) {
        if (reqDto.getAgentLayer() != null && agentId != 0) {
            queryWrapper.eq(reqDto.getAgentLayer() == 1, User::getSupUid1, agentId);
            queryWrapper.eq(reqDto.getAgentLayer() == 2, User::getSupUid2, agentId);
            queryWrapper.eq(reqDto.getAgentLayer() == 3, User::getSupUid3, agentId);
            queryWrapper.eq(reqDto.getAgentLayer() == 4, User::getSupUid4, agentId);
            queryWrapper.eq(reqDto.getAgentLayer() == 5, User::getSupUid5, agentId);
            queryWrapper.eq(reqDto.getAgentLayer() == 6, User::getSupUid6, agentId);
        } else if (reqDto.getAgentLayer() != null) {
            queryWrapper.ne(reqDto.getAgentLayer() == 1, User::getSupUid1, 0);
            queryWrapper.ne(reqDto.getAgentLayer() == 2, User::getSupUid2, 0);
            queryWrapper.ne(reqDto.getAgentLayer() == 3, User::getSupUid3, 0);
            queryWrapper.ne(reqDto.getAgentLayer() == 4, User::getSupUid4, 0);
            queryWrapper.ne(reqDto.getAgentLayer() == 5, User::getSupUid5, 0);
            queryWrapper.ne(reqDto.getAgentLayer() == 6, User::getSupUid6, 0);
        } else if (reqDto.getAgentLayer() == null && agentId != 0) {
            queryWrapper.and(wrapper -> wrapper.eq(User::getSupUid1, agentId).or().eq(User::getSupUid2, agentId).or().eq(User::getSupUid3, agentId).or().eq(User::getSupUid4, agentId).or().eq(User::getSupUid5, agentId).or().eq(User::getSupUid6, agentId));
        }
    }

    /**
     * 获取团队人数信息
     *
     * @param uid             用户ID
     * @param userProfileList 用户实例集合
     * @return 团队集合
     */
    private List<AgentCenterParameter.Team> getTeam(Integer uid, List<User> userProfileList) {
        var teamList = new ArrayList<AgentCenterParameter.Team>();
        IntFunction<Long> function = layer -> userProfileList.stream().filter(x -> getSupUid(x, layer).equals(uid)).count();
        for (int i = 1; i < 7; i++) {
            teamList.add(AgentCenterParameter.Team.builder().agentLayer(i).count(function.apply(i).intValue()).build());
        }
        return teamList;
    }

    /**
     * 计算团队金额
     *
     * @param uid             用户id
     * @param userProfileList 用户上下关系集合
     * @param userMap         用户余额集合
     * @return 团队金额集合
     */
    private List<AgentCenterParameter.TeamAmount> getTeamAmount(Integer uid, List<User> userProfileList, Map<Integer, User> userMap) {
        var teamList = new ArrayList<AgentCenterParameter.TeamAmount>();
        IntFunction<BigDecimal> function = layer -> userProfileList.stream().filter(x -> getSupUid(x, layer).equals(uid)).map(x -> {
            var user = userMap.get(x.getId());
            var coin = userCoinBase.getUserCoin(user.getId());
            return Objects.nonNull(user) ? coin : BigDecimal.ZERO;
        }).reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
        var total = BigDecimal.ZERO;
        for (int i = 1; i < 7; i++) {
            var amount = function.apply(i);
            teamList.add(AgentCenterParameter.TeamAmount.builder().agentLayer(i).amount(amount).build());
            total = total.add(amount);
        }
        teamList.add(AgentCenterParameter.TeamAmount.builder().agentLayer(7).amount(total).build());
        return teamList;
    }

    /**
     * 反射根据字段名获取值
     *
     * @param object 对象
     * @param layer  等级
     * @return 字段值
     */
    public Integer getSupUid(Object object, Integer layer) {
        try {
            return (Integer) object.getClass().getMethod(SUP_UID + layer).invoke(object);
        } catch (Exception e) {
            return -1;
        }
    }
}

