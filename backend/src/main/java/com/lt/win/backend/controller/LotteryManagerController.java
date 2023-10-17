package com.lt.win.backend.controller;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSort;
import com.lt.win.backend.aop.annotation.UnCheckToken;
import com.lt.win.backend.io.dto.LotteryManagerParams.*;
import com.lt.win.backend.service.LotteryManagerService;
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
 * @Auther: Jess
 * @Date: 2023/9/19 10:57
 * @Description:
 */
@Slf4j
@RestController
@Api(tags = " 彩票管理")
@ApiSort(2)
@RequestMapping("/v1/lotteryManager")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class LotteryManagerController {
    private final LotteryManagerService lotteryManagerServiceImpl;

    @ApiOperationSupport(author = "jess", order = 1)
    @ApiOperation(value = "彩种列表", notes = "彩种列表")
    @PostMapping("/lotteryTypePage")
    public Result<ResPage<LotteryTypePageRes>> lotteryTypePage(@RequestBody ReqPage<LotteryTypePageReq> reqBody) {
        return Result.ok(lotteryManagerServiceImpl.lotteryTypePage(reqBody));
    }

    @ApiOperationSupport(author = "jess", order = 2)
    @ApiOperation(value = "新增彩种", notes = "新增彩种")
    @PostMapping("/addLotteryType")
    public Result<Boolean> addLotteryType(@RequestBody AddLotteryTypeReq reqBody) {
        return Result.ok(lotteryManagerServiceImpl.addLotteryType(reqBody));
    }

    @ApiOperationSupport(author = "jess", order = 3)
    @ApiOperation(value = "修改彩种", notes = "修改彩种")
    @PostMapping("/updateLotteryType")
    public Result<Boolean> updateLotteryType(@RequestBody UpdateLotteryTypeReq reqBody) {
        return Result.ok(lotteryManagerServiceImpl.updateLotteryType(reqBody));
    }

    @ApiOperationSupport(author = "jess", order = 4)
    @ApiOperation(value = "删除彩种", notes = "删除彩种")
    @PostMapping("/deleteLotteryType")
    public Result<Boolean> deleteLotteryType(@RequestBody DeleteLotteryTypeReq reqBody) {
        return Result.ok(lotteryManagerServiceImpl.deleteLotteryType(reqBody));
    }


    @ApiOperationSupport(author = "jess", order = 5)
    @ApiOperation(value = "主板列表", notes = "主板列表")
    @PostMapping("/mainPlatePage")
    public Result<ResPage<MainPlatePageRes>> mainPlatePage(@RequestBody ReqPage<MainPlatePageReq> reqBody) {
        return Result.ok(lotteryManagerServiceImpl.mainPlatePage(reqBody));
    }

    @ApiOperationSupport(author = "jess", order = 6)
    @ApiOperation(value = "新增主板", notes = "新增主板")
    @PostMapping("/addMainPlate")
    public Result<Boolean> addMainPlate(@RequestBody AddMainPlateReq reqBody) {
        return Result.ok(lotteryManagerServiceImpl.addMainPlate(reqBody));
    }

    @ApiOperationSupport(author = "jess", order = 7)
    @ApiOperation(value = "修改主板", notes = "修改主板")
    @PostMapping("/updateMainPlate")
    public Result<Boolean> updateMainPlate(@RequestBody UpdateMainPlateReq reqBody) {
        return Result.ok(lotteryManagerServiceImpl.updateMainPlate(reqBody));
    }

    @ApiOperationSupport(author = "jess", order = 8)
    @ApiOperation(value = "删除主板", notes = "删除主板")
    @PostMapping("/deleteMainPlate")
    public Result<Boolean> deleteMainPlate(@RequestBody DeleteMainPlateReq reqBody) {
        return Result.ok(lotteryManagerServiceImpl.deleteMainPlate(reqBody));
    }

    @ApiOperationSupport(author = "jess", order = 9)
    @ApiOperation(value = "板块列表", notes = "板块列表")
    @PostMapping("/platePage")
    public Result<ResPage<PlatePageRes>> platePage(@RequestBody ReqPage<PlatePageReq> reqBody) {
        return Result.ok(lotteryManagerServiceImpl.platePage(reqBody));
    }

    @ApiOperationSupport(author = "jess", order = 10)
    @ApiOperation(value = "新增板块", notes = "新增板块")
    @PostMapping("/addPlate")
    public Result<Boolean> addPlate(@RequestBody AddPlateReq reqBody) {
        return Result.ok(lotteryManagerServiceImpl.addPlate(reqBody));
    }

    @ApiOperationSupport(author = "jess", order = 11)
    @ApiOperation(value = "修改板块", notes = "修改板块")
    @PostMapping("/updatePlate")
    public Result<Boolean> updatePlate(@RequestBody UpdatePlateReq reqBody) {
        return Result.ok(lotteryManagerServiceImpl.updatePlate(reqBody));
    }

    @ApiOperationSupport(author = "jess", order = 12)
    @ApiOperation(value = "删除板块", notes = "删除板块")
    @PostMapping("/deletePlate")
    public Result<Boolean> deletePlate(@RequestBody DeletePlateReq reqBody) {
        return Result.ok(lotteryManagerServiceImpl.deletePlate(reqBody));
    }

    @ApiOperationSupport(author = "jess", order = 13)
    @ApiOperation(value = "开奖结果列表", notes = "开奖结果列表")
    @PostMapping("/openPage")
    public Result<ResPage<OpenPageRes>> openPage(@RequestBody ReqPage<OpenPageReq> reqBody) {
        return Result.ok(lotteryManagerServiceImpl.openPage(reqBody));
    }

    @ApiOperationSupport(author = "jess", order = 14)
    @ApiOperation(value = "新增开奖结果", notes = "新增开奖结果")
    @PostMapping("/addOpen")
    public Result<Boolean> addPlate(@RequestBody AddOpenReq reqBody) {
        return Result.ok(lotteryManagerServiceImpl.addOpen(reqBody));
    }

    @ApiOperationSupport(author = "jess", order = 15)
    @ApiOperation(value = "修改开奖结果", notes = "修改开奖结果")
    @PostMapping("/updateOpen")
    public Result<Boolean> updateOpen(@RequestBody UpdateOpenReq reqBody) {
        return Result.ok(lotteryManagerServiceImpl.updateOpen(reqBody));
    }

    @ApiOperationSupport(author = "jess", order = 16)
    @ApiOperation(value = "删除开奖结果", notes = "删除开奖结果")
    @PostMapping("/deleteOpen")
    public Result<Boolean> deleteOpen(@RequestBody DeleteOpenReq reqBody) {
        return Result.ok(lotteryManagerServiceImpl.deleteOpen(reqBody));
    }

    @PostMapping(value = "/betPage")
    @ApiOperation(value = "注单列表", notes = "注单列表")
    @ApiOperationSupport(author = "jess", order = 17)
    public Result<ResPage<BetRecordRes>> betPage(@Valid @RequestBody ReqPage<BetRecordReq> reqPage ) {
        return Result.ok(lotteryManagerServiceImpl.betPage(reqPage));
    }

    @UnCheckToken
    @PostMapping(value = "/queryMainPlateList")
    @ApiOperation(value = "查询主板记录", notes = "查询主板记录")
    @ApiOperationSupport(author = "jess", order = 18)
    public Result<List<MainPlateRes>> queryMainPlateList() {
        return Result.ok(lotteryManagerServiceImpl.queryMainPlateList());
    }

    @UnCheckToken
    @PostMapping(value = "/queryPlateList")
    @ApiOperation(value = "查询板块记录", notes = "查询板块记录")
    @ApiOperationSupport(author = "jess", order = 19)
    public Result<List<PlateRes>> queryPlateList(@Valid @RequestBody PlateReq req) {
        return Result.ok(lotteryManagerServiceImpl.queryPlateList(req));
    }

    @PostMapping(value = "/plateBetStatistics")
    @ApiOperation(value = "每期板块投注统计", notes = "每期板块投注统计")
    @ApiOperationSupport(author = "jess", order = 20)
    public Result<PlateBetStatisticsRes> plateBetStatistics(@Valid @RequestBody PlateBetStatisticsReq req) {
        return Result.ok(lotteryManagerServiceImpl.plateBetStatistics(req));
    }

    @PostMapping(value = "/openSettle")
    @ApiOperation(value = "开奖结果-结算", notes = "开奖结果-结算")
    @ApiOperationSupport(author = "jess", order = 21)
    public Result<Boolean> openSettle(@Valid @RequestBody OpenSettleReq req) {
        return Result.ok(lotteryManagerServiceImpl.openSettle(req));
    }

    @PostMapping(value = "/betSettle")
    @ApiOperation(value = "注单记录-结算", notes = "注单记录-结算")
    @ApiOperationSupport(author = "jess", order = 21)
    public Result<Boolean> betSettle(@Valid @RequestBody BetSettleReq req) {
        return Result.ok(lotteryManagerServiceImpl.betSettle(req));
    }

    @PostMapping(value = "/betExport")
    @ApiOperation(value = "注单导出", notes = "注单导出")
    @ApiOperationSupport(author = "jess", order = 22)
    public Result<List<BetRecordRes>> betExport(@Valid @RequestBody BetRecordReq req ) {
        return Result.ok(lotteryManagerServiceImpl.betExport(req));
    }
    @PostMapping(value = "/updateBetRecord")
    @ApiOperation(value = "修改注单记录", notes = "修改注单记录")
    @ApiOperationSupport(author = "jess", order = 23)
    public Result<Boolean> updateBetRecord(@Valid @RequestBody UpdateBetRecordReq req ) {
        return Result.ok(lotteryManagerServiceImpl.updateBetRecord(req));
    }
}
