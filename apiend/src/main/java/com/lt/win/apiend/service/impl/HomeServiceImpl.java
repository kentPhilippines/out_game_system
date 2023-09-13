package com.lt.win.apiend.service.impl;


import com.lt.win.apiend.base.DictionaryBase;
import com.lt.win.apiend.cache.redis.HomeCache;
import com.lt.win.apiend.io.bo.HomeParams;
import com.lt.win.apiend.io.bo.HomeParams.*;
import com.lt.win.apiend.service.IHomeService;
import com.lt.win.service.cache.redis.BannerCache;
import com.lt.win.utils.BeanConvertUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * @author: David
 * @date: 06/04/2020
 * @description:
 */
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class HomeServiceImpl implements IHomeService {
    private final HomeCache homeCache;
    private final DictionaryBase dictionaryBase;
    private final BannerCache bannerCache;

    /**
     * Init 获取系统配置相关信息
     *
     * @return 出参
     */
    @Override
    public List<HomeParams.InitResDto> init(HttpServletRequest request) {
        List<HomeParams.InitResDto> resDtoList = homeCache.getInitResCache();
        var dicMap = dictionaryBase.getCategoryMap("dic_lang");
        List<String> list = new ArrayList<>(dicMap.keySet());
        Collections.sort(list);
        HomeParams.InitResDto initResDto = HomeParams.InitResDto.builder()
                .id(0)
                .title("lang")
                .titleZh("语言")
                .value(StringUtils.join(list, ","))
                .build();
        resDtoList.add(initResDto);
        return resDtoList;
    }

    @Override
    public List<HomeParams.BannerListRes> getBannerList(HttpServletRequest request) {
        var device = request.getHeader("accept-device");
        var language = request.getHeader("accept-language");
        var bannerList = bannerCache.getBannerList(device, language);
        return BeanConvertUtils.copyListProperties(bannerList, BannerListRes::new);
    }

    /**
     * 截取原域名
     * 原http://pch5.sp.com或https://pch5.sp.com -> sp.com
     * 原http://www.pch5.sp.com或https://www.pch5.sp.com -> sp.com
     *
     * @param link 原域名
     * @return sp.com
     */
    private String subStringLink(String link) {
        log.info("======before link====>" + link);
        if (StringUtils.isNotBlank(link)) {
            String[] split = link.split("[.]");
            if (split.length >= 2) {
                link = split[split.length - 2] + "." + split[split.length - 1];
            }
        }
        log.info("======after link====>" + link);
        return link;
    }
}
