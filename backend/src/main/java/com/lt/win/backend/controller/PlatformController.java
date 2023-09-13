package com.lt.win.backend.controller;


import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.lt.win.backend.aop.annotation.UnCheckLog;
import com.lt.win.backend.io.dto.Platform;
import com.lt.win.backend.service.IPlatformService;
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
 * @author Davids
 */
@Slf4j
@Api(tags = "第三方平台游戏")
@RequestMapping("/v1/platform")
@RestController
public class PlatformController {
    @Resource
    private IPlatformService platformServiceImpl;

    @ApiOperationSupport(author = "David", order = 1)
    @ApiOperation(value = "平台列表", notes = "平台列表")
    @PostMapping("/platList")
    public Result<List<Platform.PlatListResInfo>> platList() {
        return Result.ok(platformServiceImpl.platList());
    }

    @ApiOperationSupport(author = "David", order = 2)
    @ApiOperation(value = "游戏列表", notes = "游戏列表")
    @PostMapping("/gameList")
    public Result<List<Platform.GameListInfo>> gameList(@Valid @RequestBody Platform.GameListReqDto dto) {
        return Result.ok(platformServiceImpl.gameList(dto));
    }

    @UnCheckLog
    @ApiOperationSupport(author = "Andy", order = 99)
    @ApiOperation(value = "游戏类型->平台名称->游戏名称->三级联动", notes = "游戏类型->平台名称->游戏名称->三级联动")
    @PostMapping("/getCascadeByGameGroupList")
    public Result<List<Platform.GameGroupDict>> getCascadeByGameGroupList() {
        return Result.ok(platformServiceImpl.getCascadeByGameGroupList());
    }
}
