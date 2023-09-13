package com.lt.win.service.cache.redis;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSON;
import com.lt.win.dao.generator.po.Notice;
import com.lt.win.dao.generator.po.NoticeStatus;
import com.lt.win.dao.generator.po.PayPlatConfig;
import com.lt.win.dao.generator.service.NoticeService;
import com.lt.win.dao.generator.service.NoticeStatusService;
import com.lt.win.service.cache.KeyConstant;
import com.lt.win.utils.JedisUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.set.CompositeSet;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.lt.win.service.common.Constant.PLAT_CONFIG_ENABLE;

/**
 * @Auther: wells
 * @Date: 2022/8/13 17:01
 * @Description:
 */
@Component
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class NoticeCache {
    private final JedisUtil jedisUtil;
    private final NoticeService noticeServiceImpl;
    private final NoticeStatusService noticeStatusServiceImpl;

    public List<Notice> getNoticeList() {
        String data = jedisUtil.get(KeyConstant.NOTICE_LIST);
        if (StringUtils.isNotBlank(data)) {
            return JSON.parseArray(data, Notice.class);
        }
        List<Notice> noticeList = noticeServiceImpl.lambdaQuery()
                .eq(Notice::getStatus, 1).list();
        if (CollectionUtil.isNotEmpty(noticeList)) {
            jedisUtil.set(KeyConstant.NOTICE_LIST, JSON.toJSONString(noticeList));
        }
        return noticeList;
    }

    /**
     * @return java.util.List<com.lt.win.dao.generator.po.Notice>
     * @Description 获取消息列表
     * @Param []
     **/
    public List<Notice> getNoticeList(Integer uid) {
        List<Notice> noticeList = getNoticeList();
        Set<String> deleteSet = getDeleteSet(uid);
        noticeList = noticeList.stream()
                .filter(notice -> {
                    String uids = notice.getUids();
                    boolean flag1 = !deleteSet.contains(notice.getId().toString());
                    if (notice.getCategory() == 2) {
                        return flag1 && Strings.isNotEmpty(uids) && notice.getUids().contains(uid.toString());
                    }
                    if (notice.getCategory() == 3) {
                        return flag1 && Strings.isNotEmpty(uids) && notice.getUids().equals(uid.toString());
                    }
                    return flag1;
                })
                .collect(Collectors.toList());
        return noticeList;
    }

    /**
     * @return java.util.Set<java.lang.String>
     * @Description 获取用户已读消息列表
     * @Param [uid]
     **/
    public Set<String> getReadSet(Integer uid) {
        String data = jedisUtil.hget(KeyConstant.NOTICE_READ_HASH, uid.toString());
        if (Strings.isNotEmpty(data)) {
            return Arrays.stream(data.split(";")).collect(Collectors.toSet());
        }
        List<NoticeStatus> list = noticeStatusServiceImpl.lambdaQuery()
                .eq(NoticeStatus::getIsDelete, 0)
                .eq(NoticeStatus::getUid, uid)
                .eq(NoticeStatus::getIsRead, 1)
                .list();
        Set<String> noticeIdSet = new HashSet<>();
        if (CollectionUtil.isNotEmpty(list)) {
            noticeIdSet = list.stream()
                    .map(noticeStatus -> noticeStatus.getNoticeId().toString())
                    .collect(Collectors.toSet());
            String noticeIds = StringUtils.join(noticeIdSet, ";");
            jedisUtil.hset(KeyConstant.NOTICE_READ_HASH, uid.toString(), noticeIds);
        }
        return noticeIdSet;
    }


    /**
     * @return java.util.Set<java.lang.String>
     * @Description 获取用户的已删除列表
     * @Param [uid]
     **/
    public Set<String> getDeleteSet(Integer uid) {
        String data = jedisUtil.hget(KeyConstant.NOTICE_DELETE_HASH, uid.toString());
        if (Strings.isNotEmpty(data)) {
            return Arrays.stream(data.split(";")).collect(Collectors.toSet());
        }
        List<NoticeStatus> list = noticeStatusServiceImpl.lambdaQuery()
                .eq(NoticeStatus::getIsDelete, 1)
                .eq(NoticeStatus::getUid, uid)
                .list();
        Set<String> noticeIdSet = new HashSet<>();
        if (CollectionUtil.isNotEmpty(list)) {
            noticeIdSet = list.stream()
                    .map(noticeStatus -> noticeStatus.getNoticeId().toString())
                    .collect(Collectors.toSet());
            String noticeIds = StringUtils.join(noticeIdSet, ";");
            jedisUtil.hset(KeyConstant.NOTICE_DELETE_HASH, uid.toString(), noticeIds);
        }
        return noticeIdSet;
    }

}
