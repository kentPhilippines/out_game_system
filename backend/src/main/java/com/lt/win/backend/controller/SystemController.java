package com.lt.win.backend.controller;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.lt.win.backend.aop.annotation.UnCheckToken;
import com.lt.win.backend.io.dto.System;
import com.lt.win.backend.io.dto.System.*;
import com.lt.win.backend.service.ISystemService;
import com.lt.win.utils.components.pagination.ReqPage;
import com.lt.win.utils.components.pagination.ResPage;
import com.lt.win.utils.components.response.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * <p>
 * 系统配置
 * </p>
 *
 * @author andy
 * @since 2020/10/5
 */
@Slf4j
@RestController
@Api(tags = "系统配置")
@ApiSupport(order = 19, author = "Andy")
@RequestMapping("/v1/system")
public class SystemController {
    @Resource
    private ISystemService systemServiceImpl;


    @ApiOperationSupport(author = "wells", order = 1)
    @PostMapping(value = "/bannerList")
    @ApiOperation(value = "Banner图 -> 列表查询", notes = "Banner图 -> 列表查询")
    public Result<ResPage<BannerListResBody>> bannerList(@RequestBody ReqPage<BannerListReqBody> reqBody) {
        return Result.ok(systemServiceImpl.bannerList(reqBody));
    }

    @ApiOperationSupport(author = "wells", order = 2)
    @PostMapping(value = "/addBanner")
    @ApiOperation(value = "Banner图 -> 新增", notes = "Banner图 -> 新增")
    public Result<Boolean> addBanner(@Valid @RequestBody AddBannerReqBody reqBody) {
        return Result.ok(systemServiceImpl.addBanner(reqBody));
    }

    @ApiOperationSupport(author = "wells", order = 3)
    @PostMapping(value = "/updateBanner")
    @ApiOperation(value = "Banner图 -> 修改", notes = "Banner图 -> 修改")
    public Result<Boolean> updateBanner(@Valid @RequestBody UpdateBannerReqBody reqBody) {
        return Result.ok(systemServiceImpl.updateBanner(reqBody));
    }

    @ApiOperationSupport(author = "wells", order = 4)
    @PostMapping(value = "/delBanner")
    @ApiOperation(value = "Banner图 -> 删除", notes = "Banner图 -> 删除")
    public Result<Boolean> delBanner(@Valid @RequestBody DelBannerReqBody reqBody) {
        return Result.ok(systemServiceImpl.delBanner(reqBody));
    }

    @UnCheckToken
    @PostMapping(value = "/init")
    @ApiOperation(value = "初始化接口", notes = "初始化")
    @ApiOperationSupport(author = "David", order = 5)
    public Result<List<InitResDto>> init() {
        return Result.ok(systemServiceImpl.init());
    }


    @PostMapping(value = "/getCommissionRule")
    @ApiOperation(value = "查询代理配置", notes = "查询代理配置")
    @ApiOperationSupport(order = 7)
    public Result<CommissionRuleDto> getCommissionRule() {
        return Result.ok(systemServiceImpl.getCommissionRule());
    }

    @PostMapping(value = "/updateCommissionRule")
    @ApiOperation(value = "修改代理配置", notes = "修改代理配置")
    @ApiOperationSupport(order = 8)
    public Result<Boolean> updateCommissionRule(@Valid @RequestBody CommissionRuleDto reqDto) {
        return Result.ok(systemServiceImpl.updateCommissionRule(reqDto));
    }

    @PostMapping(value = "/getPlatConfig")
    @ApiOperation(value = "查询平台配置", notes = "查询平台配置")
    @ApiOperationSupport(order = 9)
    public Result<PlatConfigDto> getPlatConfig() {
        return Result.ok(systemServiceImpl.getPlatConfig());
    }

    @PostMapping(value = "/updatePlatConfig")
    @ApiOperation(value = "修改平台配置", notes = "修改平台配置")
    @ApiOperationSupport(order = 10)
    public Result<Boolean> updatePlatConfig(@Valid @RequestBody PlatConfigDto reqDto) {
        return Result.ok(systemServiceImpl.updatePlatConfig(reqDto));
    }

    @UnCheckToken
    @GetMapping(value = "/resetCommission")
    @ApiOperation(value = "删除历史佣金记录-重新统计今日佣金记录", notes = "删除历史佣金记录-重新统计今日佣金记录")
    @ApiOperationSupport(author = "David", order = 99)
    public Result<String> resetCommission() {
        systemServiceImpl.resetCommission();
        return Result.ok("成功");
    }
}
