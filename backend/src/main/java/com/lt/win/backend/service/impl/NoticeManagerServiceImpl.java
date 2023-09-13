package com.lt.win.backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.mustachejava.Code;
import com.lt.win.backend.io.bo.notice.*;
import com.lt.win.backend.service.INoticeManagerService;
import com.lt.win.dao.generator.po.Notice;
import com.lt.win.dao.generator.po.User;
import com.lt.win.dao.generator.service.NoticeService;
import com.lt.win.dao.generator.service.UserService;
import com.lt.win.service.base.NoticeBase;
import com.lt.win.service.base.websocket.WebSocketClient;
import com.lt.win.service.cache.KeyConstant;
import com.lt.win.service.common.Constant;
import com.lt.win.service.exception.BusinessException;
import com.lt.win.utils.BeanConvertUtils;
import com.lt.win.utils.DateUtils;
import com.lt.win.utils.JedisUtil;
import com.lt.win.utils.components.pagination.ReqPage;
import com.lt.win.utils.components.pagination.ResPage;
import com.lt.win.utils.components.response.CodeInfo;
import com.lt.win.backend.io.bo.notice.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * <p>
 * 公告管理
 * </p>
 *
 * @author andy
 * @since 2020/6/8
 */
@Slf4j
@Service
public class NoticeManagerServiceImpl implements INoticeManagerService {
    @Resource
    private NoticeService noticeServiceImpl;
    @Resource
    private UserService userServiceImpl;
    @Resource
    private JedisUtil jedisUtil;


    @Resource
    private WebSocketClient webSocketClient;
    private static final AtomicInteger POOL_ID = new AtomicInteger();
    public static final ThreadPoolExecutor NETTY_THREAD_POOL = new ThreadPoolExecutor(
            8,
            32,
            1L,
            TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(),
            x -> new Thread(x, "公告信息推送_THREAD_" + POOL_ID.getAndIncrement()));


    @Override
    public ResPage<ListNoticeResBody> list(ReqPage<ListNoticeReqBody> reqBody) {
        if (null == reqBody) {
            throw new BusinessException(CodeInfo.PARAMETERS_INVALID);
        }
        LambdaQueryWrapper<Notice> wrapper = null;
        if (null != reqBody.getData()) {
            ListNoticeReqBody data = reqBody.getData();
            wrapper = Wrappers.lambdaQuery();
            if (null != data.getId()) {
                wrapper.eq(Notice::getId, data.getId());
            }
            if (null != data.getCategory()) {
                wrapper.eq(Notice::getCategory, data.getCategory());
            }
            if (null != data.getStatus()) {
                wrapper.eq(Notice::getStatus, data.getStatus());
            }
            if (StringUtils.isNotBlank(data.getTitle())) {
                wrapper.like(Notice::getTitle, data.getTitle());
            }
            wrapper.ne(Notice::getCategory, 3);
        }
        Page<Notice> page1 = noticeServiceImpl.page(reqBody.getPage(), wrapper);
        Page<ListNoticeResBody> page = BeanConvertUtils.copyPageProperties(page1, ListNoticeResBody::new);
        return ResPage.get(page);
    }

    @Autowired
    private NoticeBase noticeBase;

    @Override
    public void add(AddNoticeReqBody reqBody) {
        Integer currentTime = DateUtils.getCurrentTime();
        Notice notice = BeanConvertUtils.beanCopy(reqBody, Notice::new);
        notice.setCreatedAt(currentTime);
        notice.setUpdatedAt(currentTime);
        if (reqBody.getCategory() == 2) {
            if (StringUtils.isEmpty(reqBody.getUids())) {
                throw new BusinessException(CodeInfo.NOTICE_UID_EXCEPTION);
            }
            long count = userServiceImpl.lambdaQuery().in(User::getId, reqBody.getUids()).count();
            if (count == 0) {
                throw new BusinessException(CodeInfo.NOTICE_UID_EXCEPTION);
            }
        }
        boolean flag = noticeServiceImpl.save(notice);
        //推送公告消息
        if (flag) {
            jedisUtil.del(KeyConstant.NOTICE_LIST);
            noticeBase.writeNotification(notice.getId());
            noticeBase.noticeChange();
        }
    }

    @Override
    public void update(UpdateNoticeReqBody reqBody) {
        Integer currentTime = DateUtils.getCurrentTime();
        Notice notice = BeanConvertUtils.beanCopy(reqBody, Notice::new);
        notice.setUpdatedAt(currentTime);
        if (null != notice.getId()) {
            var flag = noticeServiceImpl.update(notice, new LambdaQueryWrapper<Notice>().eq(Notice::getId, notice.getId()));
            //推送公告消息
            if (flag) {
                jedisUtil.del(KeyConstant.NOTICE_LIST);
                noticeBase.writeNotification(reqBody.getId());
                noticeBase.noticeChange();
            }
        }
    }

    @Override
    public void delete(DeleteNoticeReqBody reqBody) {
        noticeServiceImpl.removeById(reqBody.getId());
        jedisUtil.del(KeyConstant.NOTICE_LIST);
        noticeBase.noticeChange();
    }

    @Override
    public ResPage<UidListResBody> uidList(ReqPage<UidListReqBody> reqBody) {
        UidListReqBody data = reqBody.getData();
        LambdaQueryWrapper<User> wrapper = Wrappers.lambdaQuery();
        wrapper.ne(User::getRole, Constant.USER_ROLE_CS);
        if (data != null && data.getUserMark() != null) {
            wrapper.eq(User::getId, data.getUserMark()).or().likeRight(User::getUsername, data.getUserMark());
        }
        Page<UidListResBody> page = BeanConvertUtils.copyPageProperties(userServiceImpl.page(reqBody.getPage(), wrapper), UidListResBody::new);
        return ResPage.get(page);
    }
}
