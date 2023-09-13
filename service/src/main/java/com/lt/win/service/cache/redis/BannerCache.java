package com.lt.win.service.cache.redis;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSON;
import com.lt.win.dao.generator.po.Banner;
import com.lt.win.dao.generator.po.Notice;
import com.lt.win.dao.generator.service.BannerService;
import com.lt.win.service.cache.KeyConstant;
import com.lt.win.utils.JedisUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * Banner图缓存
 * </p>
 *
 * @author andy
 * @since 2020/10/6
 */
@Component
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class BannerCache {
    private final BannerService bannerServiceImpl;
    private final JedisUtil jedisUtil;


    public List<Banner> getBannerList() {
        String data = jedisUtil.get(KeyConstant.BANNER_LIST);
        if (StringUtils.isNotBlank(data)) {
            return JSON.parseArray(data, Banner.class);
        }
        var bannerList = bannerServiceImpl.lambdaQuery()
                .eq(Banner::getStatus, 1).list();
        if (CollectionUtil.isNotEmpty(bannerList)) {
            jedisUtil.set(KeyConstant.BANNER_LIST, JSON.toJSONString(bannerList));
        }
        return bannerList;
    }

    public List<Banner> getBannerList(String device, String language) {
        return getBannerList().stream()
                .filter(banner -> banner.getDevice().equals(device) && banner.getLanguage().equals(language))
                .collect(Collectors.toList());
    }

    /**
     * 删除Banner图缓存
     */
    public void delBanner() {
        jedisUtil.del(KeyConstant.BANNER_LIST);
    }
}
