package com.lt.win.backend.controller;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSort;
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
    public Result<ResPage<LotteryTypePageRes>> lotteryTypePage(@RequestBody ReqPage<LotteryTypePageRes> reqBody) {
        return Result.ok(lotteryManagerServiceImpl.lotteryTypePage(reqBody));
    }



}
