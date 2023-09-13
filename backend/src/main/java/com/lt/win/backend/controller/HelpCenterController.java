package com.lt.win.backend.controller;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.lt.win.backend.io.bo.help.HelpInfoRespBody;
import com.lt.win.backend.io.dto.HelpInfoParams;
import com.lt.win.backend.io.dto.HelpTypeParams;
import com.lt.win.backend.service.impl.IHelpInfoServiceImpl;
import com.lt.win.backend.service.impl.IHelpTypeServiceImpl;
import com.lt.win.dao.generator.po.HelpType;
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
import java.util.List;

/**
 * <p>
 * 帮助中心
 * </p>
 *
 * @author piking
 * @since 2022/8/1
 */
@Slf4j
@RestController
@RequestMapping("/v1/help")
@Api(tags = "帮助中心")
public class HelpCenterController {
    @Resource
    private IHelpInfoServiceImpl iHelpInfoServiceImpl;
    @Resource
    private IHelpTypeServiceImpl iHelpTypeServiceImpl;


    @ApiOperationSupport(author = "piking", order = 1)
    @ApiOperation(value = "帮助中心-帮助类型分页", notes = "帮助类型分页")
    @PostMapping("/pageHelpType")
    public Result<List<HelpType>> pageHelpType(@RequestBody HelpTypeParams.HelpTypeSelectReqDto reqBody) {
        return Result.ok(iHelpTypeServiceImpl.page(reqBody));
    }

    @ApiOperationSupport(author = "piking", order = 2)
    @ApiOperation(value = "帮助中心-帮助类型添加", notes = "帮助类型添加")
    @PostMapping("/addHelpType")
    public Result<Boolean> addHelpType(@Valid @RequestBody HelpTypeParams.HelpTypeAddReqDto reqBody) {
        return Result.ok(iHelpTypeServiceImpl.insert(reqBody));
    }

    @ApiOperationSupport(author = "piking", order = 3)
    @ApiOperation(value = "帮助中心-帮助类型修改", notes = "帮助类型修改")
    @PostMapping("/editHelpType")
    public Result<Boolean> editHelpType(@Valid @RequestBody HelpTypeParams.HelpTypeEditReqDto reqBody) {
        return Result.ok(iHelpTypeServiceImpl.update(reqBody));
    }

    @ApiOperationSupport(author = "piking", order = 4)
    @ApiOperation(value = "帮助中心-帮助类型删除", notes = "帮助类型删除")
    @PostMapping("/romoveHelpType")
    public Result<Boolean> romoveHelpType(@Valid @RequestBody HelpTypeParams.HelpTypeDeleteReqDto reqBody) {
        return Result.ok(iHelpTypeServiceImpl.deleteById(reqBody.getListId()));
    }


    @ApiOperationSupport(author = "piking", order = 5)
    @ApiOperation(value = "帮助中心-帮助详情分页", notes = "帮助详情分页")
    @PostMapping("/pageHelpInfo")
    public Result<ResPage<HelpInfoRespBody>> pageHelpInfo(@RequestBody ReqPage<HelpInfoParams.HelpInfoSelectReqDto> reqBody) {
        return Result.ok(iHelpInfoServiceImpl.page(reqBody));
    }


    @ApiOperationSupport(author = "piking", order = 6)
    @ApiOperation(value = "帮助中心-帮助详情添加", notes = "帮助详情添加")
    @PostMapping("/addHelpInfo")
    public Result<Boolean> addHelpInfo(@Valid @RequestBody HelpInfoParams.HelpInfoAddReqDto reqBody) {
        return Result.ok(iHelpInfoServiceImpl.insert(reqBody));
    }

    @ApiOperationSupport(author = "piking", order = 7)
    @ApiOperation(value = "帮助中心-帮助详情修改", notes = "帮助详情修改")
    @PostMapping("/editHelpInfo")
    public Result<Boolean> editHelpInfo(@Valid @RequestBody HelpInfoParams.HelpInfoEditReqDto reqBody) {
        return Result.ok(iHelpInfoServiceImpl.update(reqBody));
    }

    @ApiOperationSupport(author = "piking", order = 8)
    @ApiOperation(value = "帮助中心-帮助详情删除", notes = "帮助详情删除")
    @PostMapping("/romoveHelpInfo")
    public Result<Boolean> romoveHelpInfo(@Valid @RequestBody HelpInfoParams.HelpInfoDeleteReqDto reqBody) {
        return Result.ok(iHelpInfoServiceImpl.deleteById(reqBody.getListId()));
    }



}
