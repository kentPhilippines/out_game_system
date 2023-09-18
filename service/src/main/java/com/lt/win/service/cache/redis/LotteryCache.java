package com.lt.win.service.cache.redis;

import cn.hutool.core.collection.CollUtil;
import com.alibaba.fastjson.JSON;
import com.lt.win.dao.generator.po.LotteryMainPlate;
import com.lt.win.dao.generator.po.LotteryPlate;
import com.lt.win.dao.generator.po.LotteryType;
import com.lt.win.dao.generator.service.LotteryMainPlateService;
import com.lt.win.dao.generator.service.LotteryPlateService;
import com.lt.win.dao.generator.service.LotteryTypeService;
import com.lt.win.service.cache.KeyConstant;
import com.lt.win.utils.DateNewUtils;
import com.lt.win.utils.DateUtils;
import com.lt.win.utils.JedisUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Auther: Jess
 * @Date: 2023/9/18 14:05
 * @Description:
 */
@Component
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class LotteryCache {
    private final JedisUtil jedisUtil;
    private final LotteryTypeService lotteryTypeServiceImpl;
    private final LotteryMainPlateService lotteryMainPlateServiceImpl;
    private final LotteryPlateService lotteryPlateServiceImpl;


    private static final String PK10 = "pk10";
    /**
     * 每期间隔时间(秒)
     **/
    private static final int INTERVAL_SECOND = 300;

    /**
     * @return java.util.List<com.lt.win.dao.generator.po.LotteryType>
     * @Description 获取彩中列表
     * @Param []
     **/
    public List<LotteryType> getLotteryTypeList() {
        String data = jedisUtil.get(KeyConstant.LOTTERY_TYPE_LIST);
        if (StringUtils.isNotBlank(data)) {
            return JSON.parseArray(data, LotteryType.class);
        }
        List<LotteryType> lotteryTypeList = lotteryTypeServiceImpl.list();
        if (CollUtil.isNotEmpty(lotteryTypeList)) {
            jedisUtil.set(KeyConstant.LOTTERY_TYPE_LIST, JSON.toJSONString(lotteryTypeList));
        }
        return lotteryTypeList;
    }

    /***
     * @Description 获取彩种信息
     * @Param []
     * @return com.lt.win.dao.generator.po.LotteryType
     **/
    public LotteryType getLotteryType() {
        return getLotteryTypeList().stream().filter(s -> s.getCode().equals(PK10)).findFirst().orElse(null);
    }

    /**
     * @return java.util.List<com.lt.win.dao.generator.po.LotteryMainPlate>
     * @Description 获取主板列表
     * @Param []
     **/
    public List<LotteryMainPlate> getLotteryMainPlateList() {
        String data = jedisUtil.get(KeyConstant.LOTTERY_MAIN_PLATE_LIST);
        if (StringUtils.isNotBlank(data)) {
            return JSON.parseArray(data, LotteryMainPlate.class);
        }
        List<LotteryMainPlate> lotteryMainPlateList = lotteryMainPlateServiceImpl.list();
        if (CollUtil.isNotEmpty(lotteryMainPlateList)) {
            jedisUtil.set(KeyConstant.LOTTERY_MAIN_PLATE_LIST, JSON.toJSONString(lotteryMainPlateList));
        }
        return lotteryMainPlateList;
    }

    /**
     * @return com.lt.win.dao.generator.po.LotteryMainPlate
     * @Description 获取主板信息
     * @Param [mainCode]
     **/
    public LotteryMainPlate getLotteryMainPlate(Integer mainCode) {
        return getLotteryMainPlateList().stream().filter(mainPlate -> mainPlate.getCode().equals(mainCode))
                .findFirst().orElse(null);
    }

    /**
     * @return com.lt.win.dao.generator.po.LotteryMainPlate
     * @Description 获取主板MAP
     * @Param [mainCode]
     **/
    public Map<Integer, String> getLotteryMainPlateMap() {
        return getLotteryMainPlateList().stream().collect(Collectors.toMap(LotteryMainPlate::getCode, LotteryMainPlate::getName));
    }

    /**
     * @return java.util.List<com.lt.win.dao.generator.po.LotteryPlate>
     * @Description 获取板块列表
     * @Param []
     **/
    public List<LotteryPlate> getLotteryPlateList() {
        String data = jedisUtil.get(KeyConstant.LOTTERY_PLATE_LIST);
        if (StringUtils.isNotBlank(data)) {
            return JSON.parseArray(data, LotteryPlate.class);
        }
        List<LotteryPlate> lotteryPlateList = lotteryPlateServiceImpl.list();
        if (CollUtil.isNotEmpty(lotteryPlateList)) {
            jedisUtil.set(KeyConstant.LOTTERY_PLATE_LIST, JSON.toJSONString(lotteryPlateList));
        }
        return lotteryPlateList;
    }

    /**
     * @return java.util.List<com.lt.win.dao.generator.po.LotteryPlate>
     * @Description 获取板块列表通过主板
     * @Param []
     **/
    public List<LotteryPlate> getLotteryPlateList(Integer mainCode) {
        return getLotteryPlateList().stream().filter(lotteryPlate -> lotteryPlate.getMainCode().equals(mainCode))
                .collect(Collectors.toList());
    }

    /**
     * @return java.util.Map<java.lang.Integer, java.lang.String>
     * @Description 获取板块Map
     * @Param [mainCode]
     **/
    public Map<Integer, String> getLotteryPlateMap(Integer mainCode) {
        return getLotteryPlateList().stream().filter(lotteryPlate -> lotteryPlate.getMainCode().equals(mainCode))
                .collect(Collectors.toMap(LotteryPlate::getCode, LotteryPlate::getName));
    }

    public Map<String, String> getLotteryPlateMap() {
        return getLotteryPlateList().stream()
                .collect(Collectors.toMap(k -> k.getMainCode() + "_" + k.getCode(), LotteryPlate::getName));
    }

    /**
     * @return com.lt.win.dao.generator.po.LotteryPlate
     * @Description 获取板块信息
     * @Param [mainCode, code]
     **/
    public LotteryPlate getLotteryPlate(Integer mainCode, Integer code) {
        return getLotteryPlateList().stream().filter(lotteryPlate -> lotteryPlate.getMainCode().equals(mainCode) && lotteryPlate.getCode().equals(code))
                .findFirst().orElse(null);

    }

    /**
     * @return java.lang.String
     * @Description 获取当前期号
     * @Param []
     **/
    public String getCurrPeriodsNo() {
        int toDaySecond = DateNewUtils.now() - DateNewUtils.todayStart();
        int num = toDaySecond / INTERVAL_SECOND;
        num = toDaySecond % INTERVAL_SECOND == 0 ? num : num + 1;
        return DateUtils.yyyyMMdd2(DateUtils.getCurrentTime()) + String.format("%03d", num);
    }

    /**
     * @return java.lang.String
     * @Description 获取当前期号
     * @Param []
     **/
    public String getUpPeriodsNo() {
        int upTime = DateNewUtils.now() - INTERVAL_SECOND;
        int toDaySecond = DateNewUtils.now() - INTERVAL_SECOND - DateNewUtils.todayStart();
        toDaySecond = toDaySecond < 0 ? toDaySecond + 24 * 3600 : toDaySecond;
        int num = toDaySecond / INTERVAL_SECOND;
        num = toDaySecond % INTERVAL_SECOND == 0 ? num : num + 1;
        return DateUtils.yyyyMMdd2(upTime) + String.format("%03d", num);
    }

}
