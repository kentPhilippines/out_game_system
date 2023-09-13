package com.lt.win.apiend.cache.redis;

import com.lt.win.service.cache.redis.ConfigCache;
import com.lt.win.utils.BeanConvertUtils;
import com.lt.win.utils.JedisUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;

import static com.lt.win.apiend.io.bo.HomeParams.InitResDto;


/**
 * <p>
 * redis缓存类:首页信息
 * </p>
 *
 * @author David
 * @since 2020/6/23
 */
@Component
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class HomeCache {
    private final JedisUtil jedisUtil;
    private final ConfigCache configCache;

    /**
     * Init 初始化信息缓存
     *
     * @return 信息列表
     */
    public List<InitResDto> getInitResCache() {
        // 类型: 1-前端 2-后台
        return configCache.getConfigCacheByShowApp(1)
                .stream()
                .map(o -> BeanConvertUtils.beanCopy(o, InitResDto::new))
                .collect(Collectors.toList());
    }

    /**
     * 项目重启清空在线人数的缓存
     */
    @PostConstruct
    public void deleteRelUidChannel() {
        jedisUtil.del("REL_UID_CHANNEL");
    }
}