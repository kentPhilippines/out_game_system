package com.lt.win.service.cache.redis;

import com.alibaba.fastjson.JSONObject;
import com.lt.win.service.cache.KeyConstant;
import com.lt.win.service.common.DigiSportErrorEnum;
import com.lt.win.service.exception.DigiSportException;
import com.lt.win.service.io.constant.ConstData;
import com.lt.win.service.io.dto.BaseParams;
import com.lt.win.service.io.enums.ConfigBo;
import com.lt.win.utils.JedisUtil;
import com.lt.win.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Instant;


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
public final class DigiTokenCache {
    private final JedisUtil jedisUtil;
    private final JwtUtils jwtUtils;
    private final ConfigCache configCache;

    /**
     * 登录diti 体育所需要的token
     *
     * @param
     */
    public String getDigiUserInfoToken(String uid) {
        return jedisUtil.hget(KeyConstant.USER_DIGI_TOKEN, uid);
    }

    /**
     * 登录diti 体育所需要的token
     *
     * @param token
     */
    public void addDigiUserInfoToken(@NotNull String uid, String token) {
        jedisUtil.hset(KeyConstant.USER_DIGI_TOKEN, uid, token);
    }


    /**
     * 体育token 验证
     *
     * @param token
     * @return
     */
    public BaseParams.HeaderInfo digiTokenVerif(String token, String uid) {
        String tokenUid;
        String apiToken = null;
        if (StringUtils.isNotBlank(token)) {
            if (token.length() < 33) {
                throw new DigiSportException(DigiSportErrorEnum.WrongToken);
            }

            tokenUid = token.substring(token.lastIndexOf("-") + 1);
            if (!token.equals(getDigiUserInfoToken(tokenUid))) {
                throw new DigiSportException(DigiSportErrorEnum.WrongToken);
            }
            if (!token.equals(jedisUtil.hget(KeyConstant.USER_DIGI_TOKEN, tokenUid))){
                throw new DigiSportException(DigiSportErrorEnum.WrongToken);
            }
            apiToken = jedisUtil.hget(KeyConstant.USER_TOKEN_HASH, tokenUid);
            if (StringUtils.isBlank(apiToken)) {
                throw new DigiSportException(DigiSportErrorEnum.WrongToken);
            }

            if (!apiToken.substring(apiToken.length() - 20).equals(token.substring(0, 20))) {
                throw new DigiSportException(DigiSportErrorEnum.WrongToken);
            }
        }

        // 获取JWT密钥、有效时间
        ConfigBo.Jwt jwtProp = configCache.getJwtProp();
        jwtUtils.init(jwtProp.getSecret(), jwtProp.getExpired());
        // JWT 解析 验证不通过则删除Token
        JSONObject jsonObject = jwtUtils.parseToken(apiToken);
        if (jsonObject == null) {
            throw new DigiSportException(DigiSportErrorEnum.WrongToken);
        }
        // 判断Token 是否过期
        if (jsonObject.getLong(ConstData.JWT_EXP_KEY) < Instant.now().getEpochSecond()) {
            throw new DigiSportException(DigiSportErrorEnum.WrongToken);
        }
        Integer id = jsonObject.getInteger("id");
        if (StringUtils.isNotBlank(uid)) {
            String hget = jedisUtil.hget(KeyConstant.USER_TOKEN_HASH, uid);
            if (StringUtils.isBlank(hget)) {
                throw new DigiSportException(DigiSportErrorEnum.ClientNotFound);
            } else if (!uid.equals(id.toString())) {
                throw new DigiSportException(DigiSportErrorEnum.WrongToken);
            }
        }
        return BaseParams.HeaderInfo.builder().id(id).username(jsonObject.getString("username")).build();

    }
}
