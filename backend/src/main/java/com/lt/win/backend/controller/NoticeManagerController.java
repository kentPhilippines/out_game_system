package com.lt.win.backend.controller;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.lt.win.backend.io.bo.notice.*;
import com.lt.win.backend.service.INoticeManagerService;
import com.lt.win.utils.components.pagination.ReqPage;
import com.lt.win.utils.components.pagination.ResPage;
import com.lt.win.utils.components.response.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * <p>
 * 公告管理
 * </p>
 *
 * @author andy
 * @since 2020/3/12
 */
@Slf4j
@RestController
@RequestMapping("/v1/notice")
@Api(tags = "公告管理")
public class NoticeManagerController {
    @Resource
    private INoticeManagerService noticeManagerServiceImpl;

    @ApiOperationSupport(author = "Andy", order = 1)
    @ApiOperation(value = "公告管理-列表", notes = "公告管理-列表")
    @PostMapping("/list")
    public Result<ResPage<ListNoticeResBody>> list(@Valid @RequestBody ReqPage<ListNoticeReqBody> reqBody) {
        return Result.ok(noticeManagerServiceImpl.list(reqBody));
    }

    @ApiOperationSupport(author = "Andy", order = 2)
    @ApiOperation(value = "公告管理-添加", notes = "公告管理-添加")
    @PostMapping("/add")
    public Result<?> add(@Valid @RequestBody AddNoticeReqBody reqBody) {
        noticeManagerServiceImpl.add(reqBody);
        return Result.ok();
    }

    @ApiOperationSupport(author = "Andy", order = 3)
    @ApiOperation(value = "公告管理-编辑", notes = "公告管理-编辑")
    @PostMapping("/update")
    public Result<?> update(@Valid @RequestBody UpdateNoticeReqBody reqBody) {
        noticeManagerServiceImpl.update(reqBody);
        return Result.ok();
    }

    @ApiOperationSupport(author = "Andy", order = 4)
    @ApiOperation(value = "公告管理-删除", notes = "公告管理-删除")
    @PostMapping("/delete")
    public Result<?> delete(@Valid @RequestBody DeleteNoticeReqBody reqBody) {
        noticeManagerServiceImpl.delete(reqBody);
        return Result.ok();
    }

    @ApiOperationSupport(author = "Andy", order = 5)
    @ApiOperation(value = "公告管理-查询UID(站内信)", notes = "公告管理-查询UID(站内信)")
    @PostMapping("/uidList")
    public Result<ResPage<UidListResBody>> uidList(@Valid @RequestBody ReqPage<UidListReqBody> reqBody) {
        return Result.ok(noticeManagerServiceImpl.uidList(reqBody));
    }
}
