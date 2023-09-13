package com.lt.win.apiend.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lt.win.apiend.io.dto.notice.NoticeParams;
import com.lt.win.apiend.service.NoticeCenterService;
import com.lt.win.dao.generator.po.Notice;
import com.lt.win.dao.generator.po.NoticeStatus;
import com.lt.win.dao.generator.service.NoticeStatusService;
import com.lt.win.service.cache.KeyConstant;
import com.lt.win.service.cache.redis.NoticeCache;
import com.lt.win.service.io.dto.BaseParams;
import com.lt.win.service.thread.ThreadHeaderLocalData;
import com.lt.win.utils.BeanConvertUtils;
import com.lt.win.utils.DateUtils;
import com.lt.win.utils.I18nUtils;
import com.lt.win.utils.JedisUtil;
import com.lt.win.utils.components.pagination.ReqPage;
import com.lt.win.utils.components.pagination.ResPage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @Auther: wells
 * @Date: 2022/8/13 16:46
 * @Description:
 */
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class NoticeCenterServiceImpl implements NoticeCenterService {
    private final JedisUtil jedisUtil;
    private final NoticeCache noticeCache;
    private final NoticeStatusService noticeStatusServiceImpl;


    @Override
    public ResPage<NoticeParams.MessageListRes> getNoticeList(ReqPage<NoticeParams.MessageListReq> req) {
        List<Notice> notices;
        List<NoticeParams.MessageListRes> messageLis;
        BaseParams.HeaderInfo headerInfo = ThreadHeaderLocalData.HEADER_INFO_THREAD_LOCAL.get();
        Integer uid = headerInfo.id;
        List<Notice> noticeList = noticeCache.getNoticeList(uid);
        Set<String> deleteSet = noticeCache.getDeleteSet(uid);
        Set<String> readSet = noticeCache.getReadSet(uid);
        notices = noticeList.stream()
                .filter(notice -> !deleteSet.contains(notice.getId().toString())
                        && notice.getCategory().equals(req.getData().getCategory()))
                .sorted(Comparator.comparing(Notice::getCreatedAt).reversed())
                .collect(Collectors.toList());
        messageLis = BeanConvertUtils.copyListProperties(notices, NoticeParams.MessageListRes::new,
                (source, target) -> {
                    target.setRead(readSet.contains(source.getId().toString()));
                    if (source.getCategory() == 3) {
                        String[] arr = source.getContent().split("#");
                        String content = I18nUtils.getLocaleMessage(arr[0]);
                        if (arr.length == 2) {
                            content = String.format(content, arr[1]);
                        } else if (arr.length == 3) {
                            content = String.format(content, arr[1], arr[2]);
                        }
                        target.setContent(content);
                    }
                }
        );
        Page<NoticeParams.MessageListRes> page = new Page<>(req.getCurrent(), req.getSize(), messageLis.size());
        messageLis = messageLis.stream().
                skip(page.getSize() * (page.getCurrent() - 1)).
                limit(page.getSize()).
                collect(Collectors.toList());
        var resPage = ResPage.get(page);
        resPage.setList(messageLis);
        return resPage;
    }

    @Override
    public List<NoticeParams.MessageCountRes> getNoticeCount() {
        BaseParams.HeaderInfo headerInfo = ThreadHeaderLocalData.HEADER_INFO_THREAD_LOCAL.get();
        Integer uid = headerInfo.id;
        List<Notice> notices;
        List<Notice> noticeList = noticeCache.getNoticeList(uid);
        Set<String> readSet = noticeCache.getReadSet(uid);
        Set<String> deleteSet = noticeCache.getDeleteSet(uid);
        notices = noticeList.stream().filter(notice -> !deleteSet.contains(notice.getId().toString()) &&
                !readSet.contains(notice.getId().toString()))
                .collect(Collectors.toList());
        Map<Integer, Long> map = notices.stream().collect(Collectors.groupingBy(Notice::getCategory, Collectors.counting()));
        List<NoticeParams.MessageCountRes> list = new ArrayList<>();
        //类型:1-站内信 2-系统公告 3-系统消息
        for (int i = 1; i < 4; i++) {
            NoticeParams.MessageCountRes messageCountRes = new NoticeParams.MessageCountRes();
            messageCountRes.setCategory(i);
            messageCountRes.setCount(map.getOrDefault(i, 0L));
            list.add(messageCountRes);
        }
        return list;
    }

    @Override
    public List<NoticeParams.MessageCountRes> updateMessageStatus(NoticeParams.UpdateMessageReq req) {
        BaseParams.HeaderInfo headerInfo = ThreadHeaderLocalData.HEADER_INFO_THREAD_LOCAL.get();
        Integer id = req.getId();
        Integer now = DateUtils.getCurrentTime();
        if (Objects.isNull(id)) {
            //修改所有信息的已读状态
            List<Notice> noticeList = noticeCache.getNoticeList(headerInfo.id);
            Set<String> readSet = noticeCache.getReadSet(headerInfo.getId());
            Set<String> deleteSet = noticeCache.getDeleteSet(headerInfo.getId());
            List<Notice> notices = noticeList.stream()
                    .filter(notice -> !deleteSet.contains(notice.getId().toString()) &&
                            !readSet.contains(notice.getId().toString()) &&
                            notice.getCategory().equals(req.category))
                    .collect(Collectors.toList());
            if (CollectionUtil.isNotEmpty(notices)) {
                List<NoticeStatus> noticeStatusList = new ArrayList<>();
                notices.forEach(notice -> {
                    NoticeStatus noticeStatus = new NoticeStatus();
                    noticeStatus.setNoticeId(notice.getId());
                    noticeStatus.setUid(headerInfo.id);
                    noticeStatus.setIsRead(1);
                    noticeStatus.setCreateAt(now);
                    noticeStatus.setUpdateAt(now);
                    noticeStatusList.add(noticeStatus);
                });
                noticeStatusServiceImpl.saveBatch(noticeStatusList);
            }
        } else {
            NoticeStatus noticeStatusDb = noticeStatusServiceImpl.lambdaQuery()
                    .eq(NoticeStatus::getUid, headerInfo.getId())
                    .eq(NoticeStatus::getNoticeId, req.getId())
                    .one();
            if (Objects.isNull(noticeStatusDb)) {
                NoticeStatus noticeStatus = new NoticeStatus();
                noticeStatus.setNoticeId(req.getId());
                noticeStatus.setUid(headerInfo.id);
                noticeStatus.setIsRead(1);
                noticeStatus.setCreateAt(now);
                noticeStatus.setUpdateAt(now);
                noticeStatusServiceImpl.save(noticeStatus);
            } else {
                noticeStatusDb.setIsRead(1);
                noticeStatusDb.setUpdateAt(now);
                noticeStatusServiceImpl.saveOrUpdate(noticeStatusDb);
            }
        }
        jedisUtil.hdel(KeyConstant.NOTICE_READ_HASH, headerInfo.getId().toString());
        return getNoticeCount();
    }

    @Override
    public List<NoticeParams.MessageCountRes> deleteMessageStatus(NoticeParams.DeleteMessageStatusReq req) {
        BaseParams.HeaderInfo headerInfo = ThreadHeaderLocalData.HEADER_INFO_THREAD_LOCAL.get();
        List<Notice> noticeList = noticeCache.getNoticeList(headerInfo.id);
        noticeList = noticeList.stream()
                .filter(notice -> notice.getCategory().equals(req.category))
                .collect(Collectors.toList());
        Integer now = DateUtils.getCurrentTime();
        List<NoticeStatus> noticeStatuses = noticeStatusServiceImpl.lambdaQuery()
                .eq(NoticeStatus::getUid, headerInfo.getId())
                .list();
        Map<Integer, NoticeStatus> noticeStatusMap = noticeStatuses.stream()
                .collect(Collectors.toMap(NoticeStatus::getNoticeId, a -> a, (k1, k2) -> k1));
        if (CollectionUtil.isNotEmpty(noticeList)) {
            List<NoticeStatus> addNoticeStatusList = new ArrayList<>();
            noticeList.forEach(notice -> {
                NoticeStatus orgNoticeStatus = noticeStatusMap.get(notice.getId());
                if (Objects.isNull(orgNoticeStatus)) {
                    NoticeStatus noticeStatus = new NoticeStatus();
                    noticeStatus.setNoticeId(notice.getId());
                    noticeStatus.setUid(headerInfo.id);
                    noticeStatus.setIsDelete(1);
                    noticeStatus.setCreateAt(now);
                    noticeStatus.setUpdateAt(now);
                    addNoticeStatusList.add(noticeStatus);
                } else {
                    orgNoticeStatus.setIsDelete(1);
                    orgNoticeStatus.setUpdateAt(now);
                    addNoticeStatusList.add(orgNoticeStatus);
                }
            });
            noticeStatusServiceImpl.saveOrUpdateBatch(addNoticeStatusList);
        }
        jedisUtil.hdel(KeyConstant.NOTICE_DELETE_HASH, headerInfo.getId().toString());
        return getNoticeCount();
    }

    @Override
    public List<NoticeParams.LampListRes> getLampList() {
        List<Notice> noticeList = noticeCache.getNoticeList();
        noticeList = noticeList.stream().filter(notice -> notice.getCategory() == 1)
                .collect(Collectors.toList());
        return BeanConvertUtils.copyListProperties(noticeList, NoticeParams.LampListRes::new);
    }
}
