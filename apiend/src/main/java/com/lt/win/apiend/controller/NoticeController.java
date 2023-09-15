package com.lt.win.apiend.controller;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSort;
import com.lt.win.apiend.aop.annotation.UnCheckToken;
import com.lt.win.apiend.io.dto.notice.NoticeParams;
import com.lt.win.apiend.service.NoticeCenterService;
import com.lt.win.utils.components.pagination.ReqPage;
import com.lt.win.utils.components.pagination.ResPage;
import com.lt.win.utils.components.response.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * @Auther: wells
 * @Date: 2022/8/13 16:40
 * @Description:
 */
@Slf4j
//@RestController
@Api(tags = "消息")
@ApiSort(5)
@RequestMapping("/v1/notice")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class NoticeController {
    private final NoticeCenterService noticeCenterServiceImpl;

    @PostMapping(value = "/getMessageList")
    @ApiOperation(value = "消息列表", notes = "消息列表")
    @ApiOperationSupport(author = "wells", order = 1)
    public Result<ResPage<NoticeParams.MessageListRes>> getNoticeList(@Valid @RequestBody ReqPage<NoticeParams.MessageListReq> req) {
        return Result.ok(noticeCenterServiceImpl.getNoticeList(req));
    }

    @PostMapping(value = "/getNoticeCount")
    @ApiOperation(value = "每个类型消息数量", notes = "每个类型消息数量")
    @ApiOperationSupport(author = "wells", order = 2)
    public Result<List<NoticeParams.MessageCountRes>> getNoticeCount() {
        return Result.ok(noticeCenterServiceImpl.getNoticeCount());
    }

    @PostMapping(value = "/updateMessageStatus")
    @ApiOperation(value = "修改消息状态", notes = "修改消息状态")
    @ApiOperationSupport(author = "wells", order = 3)
    public Result<List<NoticeParams.MessageCountRes>> updateMessageStatus(@Valid @RequestBody NoticeParams.UpdateMessageReq req) {
        return Result.ok(noticeCenterServiceImpl.updateMessageStatus(req));
    }


    @PostMapping(value = "/deleteMessageStatus")
    @ApiOperation(value = "删除全部消息", notes = "删除全部消息")
    @ApiOperationSupport(author = "wells", order = 4)
    public Result<List<NoticeParams.MessageCountRes>> deleteMessageStatus(@Valid @RequestBody NoticeParams.DeleteMessageStatusReq req) {
        return Result.ok(noticeCenterServiceImpl.deleteMessageStatus(req));
    }

    @UnCheckToken
    @PostMapping(value = "/getLampList")
    @ApiOperation(value = "跑马灯信息", notes = "跑马灯信息")
    @ApiOperationSupport(author = "wells", order = 5)
    public Result<List<NoticeParams.LampListRes>> getLampList() {
        return Result.ok(noticeCenterServiceImpl.getLampList());
    }
}
