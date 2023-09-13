package com.lt.win.apiend.controller;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSort;
import com.lt.win.apiend.aop.annotation.LogAnnotation;
import com.lt.win.apiend.aop.annotation.UnCheckToken;
import com.lt.win.apiend.io.bo.HomeParams;
import com.lt.win.apiend.io.dto.mapper.BannerReqDto;
import com.lt.win.apiend.io.dto.mapper.BannerResDto;
import com.lt.win.apiend.service.impl.HomeServiceImpl;
import com.lt.win.utils.components.pagination.ReqPage;
import com.lt.win.utils.components.pagination.ResPage;
import com.lt.win.utils.components.response.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;



/**
 * @author: David
 * @date: 19/04/2020
 * @description:
 */
@Slf4j
@RestController
@Api(tags = "首页")
@ApiSort(1)
@RequestMapping("/v1/home")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class HomeController {
    @Autowired
    private  HomeServiceImpl homeServiceImpl;

    @UnCheckToken
    @PostMapping(value = "/init")
    @ApiOperation(value = "初始化接口", notes = "初始化")
    @ApiOperationSupport(author = "David", order = 1)
    public Result<List<HomeParams.InitResDto>> init(HttpServletRequest request) {
        return Result.ok(homeServiceImpl.init(request));
    }

    @UnCheckToken
    @PostMapping(value = "/getBannerList")
    @ApiOperation(value = "获取轮播图列表", notes = "获取轮播图列表")
    @ApiOperationSupport(author = "David", order = 2)
    public Result<List<HomeParams.BannerListRes>> getBannerList(HttpServletRequest request) {
        return Result.ok(homeServiceImpl.getBannerList(request));
    }
}


