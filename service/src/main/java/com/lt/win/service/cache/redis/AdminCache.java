package com.lt.win.service.cache.redis;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lt.win.dao.generator.po.Admin;
import com.lt.win.dao.generator.po.AuthAdminGroup;
import com.lt.win.dao.generator.service.AdminService;
import com.lt.win.dao.generator.service.AuthAdminGroupService;
import com.lt.win.service.exception.BusinessException;
import com.lt.win.service.io.dto.BaseParams;
import com.lt.win.service.cache.KeyConstant;
import com.lt.win.service.io.constant.ConstData;
import com.lt.win.service.io.enums.ConfigBo;
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


/**
 * <p>
 * redis缓存类:缓存Admin
 * </p>
 *
 * @author David
 * @since 2020/6/23
 */
@Component
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AdminCache {
    private final JedisUtil jedisUtil;
    private final JwtUtils jwtUtils;
    private final ConfigCache configCache;
    private final AdminService adminServiceImpl;
    private final AuthAdminGroupService authAdminGroupServiceImpl;

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

        String hget = jedisUtil.hget(KeyConstant.ADMIN_TOKEN_HASH, id.toString());
        if (StringUtils.isBlank(hget) || !hget.equals(token)) {
            throw new BusinessException(CodeInfo.STATUS_CODE_401);
        }

        return BaseParams.HeaderInfo.builder()
                .id(id)
                .username(jsonObject.getString("username"))
                .build();
    }

    /**
     * 设置用户Token
     */
    public void setUserToken(@NotNull Integer uid, String token) {
        jedisUtil.hset(KeyConstant.ADMIN_TOKEN_HASH, uid.toString(), token);
    }

    /**
     * 删除用户Token
     */
    public void delUserToken(@NotNull Integer uid) {
        jedisUtil.hdel(KeyConstant.ADMIN_TOKEN_HASH, uid.toString());
    }

    /**
     * 无效登录次数加上指定增量值
     */
    public Long modifyInvalidLoginTimes(String username, Long nums) {
        return jedisUtil.hincrBy(KeyConstant.ADMIN_LOGIN_INVALID_TIMES, username, nums);
    }

    /**
     * 删除用户错误登录次数
     */
    public void delInvalidLoginTimes(String username) {
        jedisUtil.hdel(KeyConstant.ADMIN_LOGIN_INVALID_TIMES, username);
    }

    /**
     * 根据adminID获取admin信息
     *
     * @param adminId adminId
     * @return admin信息
     */
    public Admin getAdminInfoById(Integer adminId) {
        String key = KeyConstant.ADMIN_INFO_ID_HASH;
        String value = jedisUtil.hget(key, adminId.toString());
        if (StringUtils.isNotBlank(value)) {
            return JSON.parseObject(value).toJavaObject(Admin.class);
        }
        // 状态:10-正常 9-冻结 8-删除
        Admin admin = adminServiceImpl.lambdaQuery().eq(Admin::getId, adminId).eq(Admin::getStatus, 10).one();
        if (null != admin) {
            jedisUtil.hset(key, adminId.toString(), JSON.toJSONString(admin));
        }
        return admin;
    }

    /**
     * 获取用户组的信息
     *
     * @param adminGroupId 用户组ID
     * @return admin信息
     */
    public AuthAdminGroup getAuthAdminGroupById(Integer adminGroupId) {
        String key = KeyConstant.ADMIN_GROUP_ID_HASH;
        String value = jedisUtil.hget(key, adminGroupId.toString());
        if (StringUtils.isNotBlank(value)) {
            return JSON.parseObject(value).toJavaObject(AuthAdminGroup.class);
        }
        //状态: 1-启用 0-冻结
        AuthAdminGroup adminGroup = authAdminGroupServiceImpl.lambdaQuery()
                .eq(AuthAdminGroup::getId, adminGroupId)
                .eq(AuthAdminGroup::getStatus, 1)
                .one();
        if (null != adminGroup) {
            jedisUtil.hset(key, adminGroupId.toString(), JSON.toJSONString(adminGroup));
        }
        return adminGroup;
    }


    public void updateAdminById(Integer adminId) {
        String key = KeyConstant.ADMIN_INFO_ID_HASH;
        jedisUtil.hdel(key, adminId.toString());
        getAdminInfoById(adminId);
    }
}
