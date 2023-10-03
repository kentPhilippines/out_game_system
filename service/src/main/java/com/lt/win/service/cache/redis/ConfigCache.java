package com.lt.win.service.cache.redis;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.lt.win.dao.generator.po.Config;
import com.lt.win.dao.generator.po.PlatList;
import com.lt.win.dao.generator.service.ConfigService;
import com.lt.win.dao.generator.service.PlatListService;
import com.lt.win.service.cache.KeyConstant;
import com.lt.win.service.common.Constant;
import com.lt.win.service.exception.BusinessException;
import com.lt.win.service.io.enums.ConfigBo;
import com.lt.win.utils.JedisUtil;
import com.lt.win.utils.components.response.CodeInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.alibaba.fastjson.JSON.parseObject;
import static com.lt.win.service.io.enums.ConfigBo.Jwt;
import static com.lt.win.service.io.enums.ConfigBo.Rsa;


/**
 * <p>
 * 系统相关信息缓存
 * </p>
 *
 * @author David
 * @since 2020/6/23
 */
@Component
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ConfigCache {
    private final JedisUtil jedisUtil;
    private final ConfigService configServiceImpl;
    private final PlatListService platListServiceImpl;

    /**
     * 从缓存中获取JWT 属性
     *
     * @return key|expiredTime
     */
    public Jwt getJwtProp() {
        String data = jedisUtil.hget(KeyConstant.CONFIG_HASH, KeyConstant.CONFIG_HASH_JWT);
        if (StringUtils.isNotBlank(data)) {
            return JSON.parseObject(data).toJavaObject(Jwt.class);
        }

        Config config = configServiceImpl.lambdaQuery().eq(Config::getTitle, "jwt").one();
        if (Objects.nonNull(config)) {
            String secret = config.getValue();
            JSONObject jsonObject = JSON.parseObject(secret);
            Jwt build = Jwt.builder()
                    .secret(jsonObject.getString("secret"))
                    .expired(jsonObject.getLong("expired"))
                    .build();

            jedisUtil.hset(KeyConstant.CONFIG_HASH, KeyConstant.CONFIG_HASH_JWT, JSON.toJSONString(build));
            return build;
        }
        return Jwt.builder().build();
    }

    /**
     * 获取RSA密钥信息
     *
     * @return publicKey|privateKey
     */
    public Rsa getRsaProps() {
        String data = jedisUtil.hget(KeyConstant.CONFIG_HASH, KeyConstant.CONFIG_HASH_RSA);
        if (StringUtils.isNotBlank(data)) {
            return JSON.parseObject(data).toJavaObject(Rsa.class);
        }

        Config config = configServiceImpl.lambdaQuery().eq(Config::getTitle, "rsa").one();
        if (Objects.nonNull(config)) {
            String secret = config.getValue();
            JSONObject jsonObject = JSON.parseObject(secret);
            Rsa build = Rsa.builder()
                    .privateKey(jsonObject.getString("private_key"))
                    .publicKey(jsonObject.getString("public_key"))
                    .build();

            jedisUtil.hset(KeyConstant.CONFIG_HASH, KeyConstant.CONFIG_HASH_RSA, JSON.toJSONString(build));
            return build;
        }
        return Rsa.builder().build();
    }

    /**
     * 获取国家简称
     *
     * @return 英文简称
     */
    public String getCountry() {
        return getConfigByTitle("country");
    }

    /**
     * 获取货币
     *
     * @return 货币
     */
    public String getCurrency() {
        return getConfigByTitle("mainCurrency");
    }

    public String getAutoBet() {
        return getConfigByTitle("autoBet");
    }

    /**
     * 注册是否强制邀请码
     *
     * @return 货币
     */
    public Integer registerInviteCode() {
        return Integer.parseInt(getConfigByTitle(Constant.REGISTER_INVITE_CODE));
    }

    /**
     * 获取推广链接j
     *
     * @return 货币
     */
    public String getInviteLink() {
        return getConfigByTitle("inviteLink");
    }

    /**
     * 获取webSocket地址
     *
     * @return webSocket地址
     */
    public String getWsServer() {
        return getConfigByTitle("ws_server");
    }

    public BigDecimal getDepositMultiple() {
        return new BigDecimal(getConfigByTitle("deposit_multiple"));
    }

    public BigDecimal getTransferMultiple() {
        return new BigDecimal(getConfigByTitle("deposit_multiple"));
    }

    public BigDecimal getFees() {
        return new BigDecimal(getConfigByTitle("fees"));
    }

    /**
     * 获取游戏列表ID与注单表对应的表明
     *
     * @param gameListId gameListId
     * @return 表名
     */
    public String getBetSlipsTableNameByGameListId(Integer gameListId) {
        String data = getConfigByTitle(KeyConstant.GAME_LIST_ID_2_BET_SLIPS_MAPPER);
        JSONObject jsonObject = parseObject(data);
        Map<Integer, String> map = jsonObject.toJavaObject(new TypeReference<Map<Integer, String>>() {
        });
        return map.get(gameListId);
    }

    /**
     * 根据标题获取配置的Value值
     *
     * @param title 标题
     * @return 值
     */
    public String getConfigByTitle(String title) {
        var data = jedisUtil.hget(KeyConstant.CONFIG_HASH, title);
        if (StringUtils.isNotBlank(data)) {
            return data;
        }

        var one = configServiceImpl.lambdaQuery().eq(Config::getTitle, title).one();
        if (Objects.nonNull(one)) {
            jedisUtil.hset(KeyConstant.CONFIG_HASH, title, one.getValue());
            return one.getValue();
        }
        throw BusinessException.buildException(CodeInfo.CONFIG_INVALID, title);
    }

    /**
     * 获取平台config字段对应的信息
     *
     * @param model       平台名称
     * @param toJavaClass 类型
     * @param <T>         返回类型
     * @return 配置信息
     */
    public <T> T platConfigByModelName(String model, Class<T> toJavaClass) {
        String config = jedisUtil.hget(KeyConstant.PLAT_CONFIG_HASH, model);
        if (StringUtils.isNotBlank(config)) {
            return JSON.parseObject(config).toJavaObject(toJavaClass);
        }
        PlatList one = platListServiceImpl.lambdaQuery().eq(PlatList::getName, model).one();
        if (one != null && StringUtils.isNotEmpty(one.getConfig())) {
            jedisUtil.hset(KeyConstant.PLAT_CONFIG_HASH, model, one.getConfig());
            return JSON.parseObject(one.getConfig()).toJavaObject(toJavaClass);
        }

        log.error("=====" + model + ": 平台配置异常");
        throw BusinessException.buildException(CodeInfo.CONFIG_INVALID, model);
    }

    /**
     * @Author Wells
     * @Description 初始化平台参数
     * @Date 2020/9/16 4:01 下午
     * @param1
     * @Return void
     **/
    @PostConstruct
    private void initConfig() {
        var platList = platListServiceImpl.lambdaQuery().eq(PlatList::getStatus, 1).list();
        if (!CollectionUtils.isEmpty(platList)) {
            platList.forEach(plat ->
                    jedisUtil.hset(KeyConstant.PLAT_CONFIG_HASH, plat.getName(), plat.getConfig())
            );
        }
    }

    /**
     * 获取RedisKey失效时间
     *
     * @return RedisKey失效时间秒
     */
    public String getRedisExpireTime() {
        String time = "10";
        try {
            time = getConfigByTitle("redis_expire_time");
            if (StringUtils.isBlank(time)) {
                time = "10";
            }
        } catch (Exception e) {
            log.error("redis_expire_time ===> {}", e.getMessage(), e);
        }
        return time;
    }

    /**
     * 获取前端或backend配置列表
     *
     * @param showApp 1-前端 2-后台
     * @return 前端或backend配置列表
     */
    public List<Config> getConfigCacheByShowApp(Integer showApp) {
        List<Integer> showAppList;
        String key = KeyConstant.CONFIG_HASH;
        String subKey;
        // 类型: 0-全部 1-前端 2-后台
        if (1 == showApp) {
            subKey = KeyConstant.CONFIG_HASH_API;
            showAppList = List.of(0, 1);
        } else {
            subKey = KeyConstant.CONFIG_HASH_BACKEND;
            showAppList = List.of(0, 2);
        }
        String value = jedisUtil.hget(key, subKey);
        if (StringUtils.isNotBlank(value)) {
            return JSON.parseArray(value, Config.class);
        }
        var collect = configServiceImpl.lambdaQuery()
                .eq(Config::getStatus, 1)
                .in(Config::getShowApp, showAppList)
                .list();
        if (!collect.isEmpty()) {
            jedisUtil.hset(key, subKey, JSON.toJSONString(collect));
        }

        return collect;
    }

    /**
     * 获取邮箱发送配置信息
     *
     * @return 邮箱配置信息
     */
    public ConfigBo.SesConfig getSesConfig() {
        return JSON.parseObject(getConfigByTitle("sesConfig")).toJavaObject(ConfigBo.SesConfig.class);
    }
}
