package com.lt.win.apiend.controller;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSort;
import com.lt.win.apiend.aop.annotation.UnCheckToken;
import com.lt.win.apiend.io.bo.HelpParams;
import com.lt.win.apiend.service.impl.IHelpCenterServiceImpl;
import com.lt.win.utils.components.pagination.ReqPage;
import com.lt.win.utils.components.pagination.ResPage;
import com.lt.win.utils.components.response.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


/**
 * @author: piking
 * @date: 08/01/2022
 * @description:
 */
@Slf4j
//@RestController
@Api(tags = "首页 - 帮助中心")
@ApiSort(2)
@RequestMapping("/v1/help")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class HelpCenterController {

    private final IHelpCenterServiceImpl iHelpCenterServiceImpl;

    @UnCheckToken
    @PostMapping(value = "/pageHelpType")
    @ApiOperationSupport(author = "piking", order = 1)
    @ApiOperation(value = "帮助类型分页", notes = "帮助中心")
    public Result<ResPage<HelpParams.HelpTypeRespBody>> pageHelpType(@RequestBody ReqPage reqBody) {
        return Result.ok(iHelpCenterServiceImpl.pageHelpType(reqBody));
    }

    @UnCheckToken
    @PostMapping(value = "/pageHelpInfo")
    @ApiOperationSupport(author = "piking", order = 2)
    @ApiOperation(value = "根据帮助类型查询内容分页", notes = "帮助中心")
    public Result<ResPage<HelpParams.HelpInfoRespBody>> pageHelpInfo(@Valid @RequestBody ReqPage<HelpParams.HelpInfoPageReqDto> reqBody) {
        return Result.ok(iHelpCenterServiceImpl.pageHelpInfo(reqBody));
    }

    @UnCheckToken
    @PostMapping(value = "/helpInfoById")
    @ApiOperationSupport(author = "piking", order = 2)
    @ApiOperation(value = "帮助详情内容", notes = "帮助中心")
    public Result<HelpParams.HelpInfoRespBody> helpInfoById(@Valid @RequestBody HelpParams.HelpInfoReqDto reqBody) {
        return Result.ok(iHelpCenterServiceImpl.helpInfoById(reqBody));
    }






}
