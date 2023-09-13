package com.lt.win.service.cache.redis;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lt.win.dao.generator.po.User;
import com.lt.win.dao.generator.po.UserLevel;
import com.lt.win.dao.generator.service.UserLevelService;
import com.lt.win.dao.generator.service.UserService;
import com.lt.win.service.cache.KeyConstant;
import com.lt.win.service.exception.BusinessException;
import com.lt.win.service.io.bo.AgentReportBo;
import com.lt.win.service.io.constant.ConstData;
import com.lt.win.service.io.dto.BaseParams;
import com.lt.win.service.io.enums.ConfigBo;
import com.lt.win.service.io.enums.StatusEnum;
import com.lt.win.utils.JedisUtil;
import com.lt.win.utils.JwtUtils;
import com.lt.win.utils.components.response.CodeInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
 * <p>
 * redis缓存类:缓存用户
 * </p>
 *
 * @author David
 * @since 2020/6/23
 */
@Component
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public final class UserCache {
    private final JedisUtil jedisUtil;
    private final JwtUtils jwtUtils;
    private final ConfigCache configCache;
    private final UserService userServiceImpl;
    private final UserLevelService userLevelServiceImpl;

    /**
     * 清空用户缓存
     * 1. 基本信息缓存
     * 2. 代理层级关系
     *
     * @param uid uid
     */
    public void delUserCache(Integer uid) {
        delUserCache(uid, false);
    }

    /**
     * 清空用户缓存
     * 1. 基本信息缓存
     * 2. 代理层级关系
     *
     * @param uid     uid
     * @param tickOut 删除Token:true-删除 false-不删除
     */
    public void delUserCache(Integer uid, boolean tickOut) {
        var user = userServiceImpl.getById(uid);
        // 用户基本信息
        jedisUtil.hdel(KeyConstant.USER_INFO_HASH, uid.toString());
        jedisUtil.hdel(KeyConstant.USER_INFO_HASH, user.getUsername());
        // 等级下UID集合缓存-包含测试号缓存
        jedisUtil.del(KeyConstant.UID_IN_LEVEL_ID_HASH);

        // 会员代理-本身和上6级代理缓存更新
        jedisUtil.hdel(KeyConstant.USER_SUBORDINATE_HASH, uid.toString());
        jedisUtil.hdel(KeyConstant.USER_SUBORDINATE_HASH, user.getSupUid1() + "");
        jedisUtil.hdel(KeyConstant.USER_SUBORDINATE_HASH, user.getSupUid2() + "");
        jedisUtil.hdel(KeyConstant.USER_SUBORDINATE_HASH, user.getSupUid3() + "");
        jedisUtil.hdel(KeyConstant.USER_SUBORDINATE_HASH, user.getSupUid4() + "");
        jedisUtil.hdel(KeyConstant.USER_SUBORDINATE_HASH, user.getSupUid5() + "");
        jedisUtil.hdel(KeyConstant.USER_SUBORDINATE_HASH, user.getSupUid6() + "");

        // 顶级代理-更新缓存
        if (user.getSupUidTop() > 0) {
            jedisUtil.hdel(KeyConstant.USER_SUBORDINATE_SUPER_HASH, user.getSupUidTop() + "");
        }
        jedisUtil.hdel(KeyConstant.USER_SUBORDINATE_SUPER_HASH, uid + "");

        // 需要删除用户Token
        if (tickOut) {
            delUserToken(uid);
        }
    }

    /**
     * 验证用户Token是否有效
     *
     * @return true-有效 false-无效
     */
    public BaseParams.HeaderInfo validUserToken(String token) {
        // 获取JWT密钥、有效时间
        ConfigBo.Jwt jwtProp = configCache.getJwtProp();
        jwtUtils.init(jwtProp.getSecret(), jwtProp.getExpired());

        // JWT 解析 验证不通过则删除Token
        JSONObject jsonObject = jwtUtils.parseToken(token);
        if (jsonObject == null) {
            throw new BusinessException(CodeInfo.STATUS_CODE_401);
        }
        // 判断Token 是否过期
        if (jsonObject.getLong(ConstData.JWT_EXP_KEY) < Instant.now().getEpochSecond()) {
            throw new BusinessException(CodeInfo.STATUS_CODE_401_2);
        }
        Integer id = jsonObject.getInteger("id");

        String hget = jedisUtil.hget(KeyConstant.USER_TOKEN_HASH, id.toString());
        if (StringUtils.isBlank(hget) || !hget.equals(token)) {
            throw new BusinessException(CodeInfo.STATUS_CODE_401);
        }

        return BaseParams.HeaderInfo.builder().id(id).username(jsonObject.getString("username")).build();
    }


    /**
     * 设置用户Token
     */
    public void setUserToken(@NotNull Integer uid, String token) {
        jedisUtil.hset(KeyConstant.USER_TOKEN_HASH, uid.toString(), token);
    }

    /**
     * 删除用户Token
     */
    public void delUserToken(@NotNull Integer uid) {
        jedisUtil.hdel(KeyConstant.USER_TOKEN_HASH, uid.toString());
    }

    /**
     * 无效登录次数加上指定增量值
     */
    public Long modifyInvalidLoginTimes(String username, Long nums) {
        return jedisUtil.hincrBy(KeyConstant.USER_LOGIN_INVALID_TIMES, username, nums);
    }

    /**
     * 删除用户错误登录次数
     */
    public void delInvalidLoginTimes(Integer id) {
        jedisUtil.hdel(KeyConstant.USER_LOGIN_INVALID_TIMES, id.toString());
    }

    /**
     * 根据UID获取用户信息
     *
     * @param uid UID
     * @return 用户信息
     */
    public User getUserInfo(Integer uid) {
        String key = KeyConstant.USER_INFO_HASH;
        String value = jedisUtil.hget(key, uid.toString());
        if (StringUtils.isNotBlank(value)) {
            return JSON.parseObject(value).toJavaObject(User.class);
        }
        var user = userServiceImpl.getById(uid);
        if (Optional.ofNullable(user).isPresent()) {
            jedisUtil.hset(key, uid.toString(), JSON.toJSONString(user));
        }

        return user;
    }

    /**
     * 根据userName获取用户信息
     *
     * @param userName 用户名
     * @return 用户信息
     */
    public User getUserInfo(String userName) {
        String key = KeyConstant.USER_INFO_HASH;
        String value = jedisUtil.hget(key, userName);
        if (StringUtils.isNotBlank(value)) {
            return JSON.parseObject(value).toJavaObject(User.class);
        }
        var user = userServiceImpl.lambdaQuery().eq(User::getUsername, userName).one();
        if (Optional.ofNullable(user).isPresent()) {
            jedisUtil.hset(key, userName, JSON.toJSONString(user));
        }

        return user;
    }

    /**
     * 缓存所有会员等级
     *
     * @return 所有会员等级
     */
    public List<UserLevel> getUserLevelList() {
        String key = KeyConstant.USER_LEVEL_ID_LIST_HASH;
        String subKey = KeyConstant.COMMON_TOTAL_HASH;
        String value = jedisUtil.hget(key, subKey);
        if (StringUtils.isNotBlank(value)) {
            return JSON.parseArray(value, UserLevel.class);
        }
        List<UserLevel> list = userLevelServiceImpl.list();
        if (!list.isEmpty()) {
            jedisUtil.hset(key, subKey, JSON.toJSONString(list));
        }
        return list;
    }

    /**
     * 根据id获取UserLevel
     *
     * @return UserLevel
     */
    public UserLevel getUserLevelById(Integer id) {
        String key = KeyConstant.USER_LEVEL_ID_LIST_HASH;
        String value = jedisUtil.hget(key, id.toString());
        if (StringUtils.isNotBlank(value)) {
            return JSON.parseObject(value).toJavaObject(UserLevel.class);
        }
        UserLevel userLevel = userLevelServiceImpl.lambdaQuery().eq(UserLevel::getId, id).one();
        if (null != userLevel) {
            jedisUtil.hset(key, id.toString(), JSON.toJSONString(userLevel));
        }
        return userLevel;
    }

    /**
     * 获取测试账号UID列表
     *
     * @return 测试账号UID列表
     */
    public List<Integer> getTestUidList() {
        String key = KeyConstant.UID_IN_LEVEL_ID_HASH;
        String subKey = KeyConstant.TEST_UID_LIST;
        String value = jedisUtil.hget(key, subKey);
        if (StringUtils.isNotBlank(value)) {
            return JSON.parseArray(value, Integer.class);
        }
        List<Integer> uidList = userServiceImpl.lambdaQuery()
                // 角色:0-会员 1-代理 2-总代理 3-股东 4-测试
                .eq(User::getRole, 4)
                .list()
                .stream()
                .map(User::getId)
                .collect(Collectors.toList());
        if (!uidList.isEmpty()) {
            jedisUtil.hset(key, subKey, JSON.toJSONString(uidList));
        }

        return uidList;
    }

    /**
     * 根据等级Id获取UID列表
     *
     * @return 等级UID列表
     */
    public List<Integer> getUidListByLevelId(Integer levelId) {
        String key = KeyConstant.UID_IN_LEVEL_ID_HASH;
        String subKey = levelId.toString();
        String value = jedisUtil.hget(key, subKey);
        if (StringUtils.isNotBlank(value)) {
            return JSON.parseArray(value, Integer.class);
        }
        List<Integer> uidList = userServiceImpl.lambdaQuery()
                .eq(User::getLevelId, levelId)
                .list()
                .stream()
                .map(User::getId)
                .collect(Collectors.toList());
        if (!uidList.isEmpty()) {
            jedisUtil.hset(key, subKey, JSON.toJSONString(uidList));
        }
        return uidList;
    }

    /**
     * 会员代理-缓存下六级Uid列表信息
     *
     * @param uid UID
     * @return 下级UID列表
     */
    public AgentReportBo.SubordinateList subordinate(Integer uid) {
        String key = KeyConstant.USER_SUBORDINATE_HASH;
        String value = jedisUtil.hget(key, uid.toString());
        if (Optional.ofNullable(value).isPresent()) {
            return JSON.parseObject(value).toJavaObject(AgentReportBo.SubordinateList.class);
        }

        List<Integer> levels = List.of(1, 2, 3, 4, 5, 6);

        var resData = new AgentReportBo.SubordinateList();
        // 线程聚合等待(并发请求)
        CompletableFuture.allOf(levels.stream().map(productLine -> CompletableFuture.supplyAsync(() -> {
            var query = userServiceImpl.lambdaQuery();
            switch (productLine) {
                case 1:
                    var uids = query.eq(User::getSupUid1, uid).list().stream().map(User::getId).collect(Collectors.toList());
                    resData.setLevel1(uids);
                    break;
                case 2:
                    uids = query.eq(User::getSupUid2, uid).list().stream().map(User::getId).collect(Collectors.toList());
                    resData.setLevel2(uids);
                    break;
                case 3:
                    uids = query.eq(User::getSupUid3, uid).list().stream().map(User::getId).collect(Collectors.toList());
                    resData.setLevel3(uids);
                    break;
                case 4:
                    uids = query.eq(User::getSupUid4, uid).list().stream().map(User::getId).collect(Collectors.toList());
                    resData.setLevel4(uids);
                    break;
                case 5:
                    uids = query.eq(User::getSupUid5, uid).list().stream().map(User::getId).collect(Collectors.toList());
                    resData.setLevel5(uids);
                    break;
                case 6:
                    uids = query.eq(User::getSupUid6, uid).list().stream().map(User::getId).collect(Collectors.toList());
                    resData.setLevel6(uids);
                    break;
                default:
                    break;
            }
            return null;
        })).toArray(CompletableFuture[]::new)).join();

        // 合并一~六级代理作为团队会员
        var team = Stream.of(resData.getLevel1(), resData.getLevel2(), resData.getLevel3(), resData.getLevel4(), resData.getLevel5(), resData.getLevel6()).flatMap(Collection::stream).collect(Collectors.toList());
        resData.setTeam(team);

        // 入库
        jedisUtil.hset(key, uid.toString(), JSON.toJSONString(resData));
        return resData;
    }

    /**
     * 获取账号的下级(根据层级获取, 不含测试号)
     *
     * @param uid        uid
     * @param agentLevel agentLevel
     * @return listIds
     */
    public List<Integer> subordinate(Integer uid, Integer agentLevel) {
        return subordinate(subordinate(uid), agentLevel);
    }

    /**
     * 获取账号的下级(根据层级获取, 不含测试号)
     *
     * @param subordinate subordinate
     * @param agentLevel  agentLevel
     * @return listIds
     */
    public List<Integer> subordinate(AgentReportBo.SubordinateList subordinate, Integer agentLevel) {
        if (Objects.equals(agentLevel, StatusEnum.MEMBER_AGENT_LEVEL.LEVEL_1.getCode())) {
            return subordinate.getLevel1();
        } else if (Objects.equals(agentLevel, StatusEnum.MEMBER_AGENT_LEVEL.LEVEL_2.getCode())) {
            return subordinate.getLevel2();
        } else if (Objects.equals(agentLevel, StatusEnum.MEMBER_AGENT_LEVEL.LEVEL_3.getCode())) {
            return subordinate.getLevel3();
        } else if (Objects.equals(agentLevel, StatusEnum.MEMBER_AGENT_LEVEL.LEVEL_4.getCode())) {
            return subordinate.getLevel4();
        } else if (Objects.equals(agentLevel, StatusEnum.MEMBER_AGENT_LEVEL.LEVEL_5.getCode())) {
            return subordinate.getLevel5();
        } else if (Objects.equals(agentLevel, StatusEnum.MEMBER_AGENT_LEVEL.LEVEL_6.getCode())) {
            return subordinate.getLevel6();
        } else if (Objects.equals(agentLevel, StatusEnum.MEMBER_AGENT_LEVEL.OTHER.getCode())) {
            return subordinate.getOther();
        } else {
            return subordinate.getTeam();
        }
    }

    /**
     * 缓存下六级Uid列表信息
     *
     * @param uid UID
     * @return 下级UID列表
     */
    public JSONArray subordinateArray(Integer uid) {
        var sub = JSON.parseObject(JSON.toJSONString(subordinate(uid)));
        JSONArray retArray = new JSONArray();
        for (Map.Entry<String, Object> entry : sub.entrySet()) {
            JSONObject tmpData = new JSONObject();
            tmpData.put("level", entry.getKey());
            tmpData.put("uids", entry.getValue());
            retArray.add(tmpData);
        }

        return retArray;
    }

    /**
     * 顶级代理-缓存下六级Uid列表信息
     *
     * @param uid UID
     * @return 下级UID列表
     */
    public AgentReportBo.SubordinateList subordinateSuper(Integer uid) {
        String key = KeyConstant.USER_SUBORDINATE_SUPER_HASH;
        String value = jedisUtil.hget(key, uid.toString());
        if (Optional.ofNullable(value).isPresent()) {
            return JSON.parseObject(value).toJavaObject(AgentReportBo.SubordinateList.class);
        }

        var res = subordinate(uid);
        // 顶级代理线
        var team = userServiceImpl.lambdaQuery()
                .eq(User::getSupUidTop, uid)
                .list()
                .stream()
                .map(User::getId)
                .collect(Collectors.toList());
        res.setTeam(team);
        // 其他层级(一～三级以外的会员)
        var other = userServiceImpl.lambdaQuery()
                .eq(User::getSupUidTop, uid)
                .gt(User::getSupLevelTop, ConstData.THREE)
                .list()
                .stream()
                .map(User::getId)
                .collect(Collectors.toList());
        res.setOther(other);

        // 入库
        jedisUtil.hset(key, uid.toString(), JSON.toJSONString(res));
        return res;
    }

    /**
     * 顶级代理-缓存下六级Uid列表信息
     *
     * @param uid UID
     * @return 下级UID列表
     */
    public List<Integer> subordinateSuper(Integer uid, Integer agentLevel) {
        return subordinate(subordinateSuper(uid), agentLevel);
    }
}
