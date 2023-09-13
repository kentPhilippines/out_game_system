package com.lt.win.backend.controller;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.lt.win.backend.io.bo.PlatManager;
import com.lt.win.backend.service.IPlatManagerService;
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
 * 游戏管理
 * </p>
 *
 * @author andy
 * @since 2020/6/8
 */
@Slf4j
@RestController
@RequestMapping("/v1/platManager")
@Api(tags = {"游戏管理"})
public class PlatManagerController {
    @Resource
    private IPlatManagerService platManagerServiceImpl;

    @ApiOperationSupport(author = "Andy", order = 1)
    @ApiOperation(value = "平台管理-列表", notes = "平台管理-列表")
    @PostMapping("/listPlat")
    public Result<ResPage<PlatManager.ListPlatResBody>> listPlat(@Valid @RequestBody ReqPage<PlatManager.ListPlatReqBody> reqBody) {
        return Result.ok(platManagerServiceImpl.listPlat(reqBody));
    }

    @ApiOperationSupport(author = "Andy", order = 2)
    @ApiOperation(value = "平台管理-编辑或启用平台", notes = "平台管理-编辑或启用平台")
    @PostMapping("/updatePlat")
    public Result<Boolean> updatePlat(@Valid @RequestBody PlatManager.UpdatePlatReqBody reqBody) {
        platManagerServiceImpl.updatePlat(reqBody);
        return Result.ok(true);
    }

    @ApiOperationSupport(author = "Andy", order = 3)
    @ApiOperation(value = "平台子游戏管理-列表", notes = "平台子游戏管理-列表")
    @PostMapping("/listSubGame")
    public Result<ResPage<PlatManager.ListSubGameResBody>> listSubGame(@Valid @RequestBody ReqPage<PlatManager.ListSubGameReqBody> dto) {
        return Result.ok(platManagerServiceImpl.listSubGame(dto));
    }

    @ApiOperationSupport(author = "Andy", order = 4)
    @ApiOperation(value = "平台子游戏管理-编辑或启用平台", notes = "平台子游戏管理-编辑或启用平台")
    @PostMapping("/updateSubGame")
    public Result<Boolean> updateSubGame(@Valid @RequestBody PlatManager.UpdateSubGameReqBody reqBody) {
        platManagerServiceImpl.updateSubGame(reqBody);
        return Result.ok(true);
    }

    @ApiOperationSupport(author = "Andy", order = 5)
    @ApiOperation(value = "游戏列表", notes = "游戏列表")
    @PostMapping("/gameList")
    public Result<List<PlatManager.GameListResBody>> gameList() {
        List<PlatManager.GameListResBody> list = platManagerServiceImpl.gameList();
        return Result.ok(list);
    }
}
