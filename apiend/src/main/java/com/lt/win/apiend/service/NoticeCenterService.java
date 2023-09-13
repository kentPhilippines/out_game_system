package com.lt.win.apiend.service;

import com.lt.win.apiend.io.dto.notice.NoticeParams;
import com.lt.win.utils.components.pagination.ReqPage;
import com.lt.win.utils.components.pagination.ResPage;
import com.lt.win.utils.components.response.Result;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.List;

/**
 * @Auther: wells
 * @Date: 2022/8/13 16:46
 * @Description:
 */
public interface NoticeCenterService {

    ResPage<NoticeParams.MessageListRes> getNoticeList(@Valid @RequestBody ReqPage<NoticeParams.MessageListReq> req);

    List<NoticeParams.MessageCountRes> getNoticeCount();

    List<NoticeParams.MessageCountRes> updateMessageStatus(@Valid @RequestBody NoticeParams.UpdateMessageReq req);

    List<NoticeParams.MessageCountRes> deleteMessageStatus(@Valid @RequestBody NoticeParams.DeleteMessageStatusReq req );

    List<NoticeParams.LampListRes> getLampList();
}
