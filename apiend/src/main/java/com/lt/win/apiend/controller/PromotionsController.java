package com.lt.win.apiend.controller;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.lt.win.apiend.aop.annotation.UnCheckToken;
import com.lt.win.apiend.io.dto.promotions.PromotionsApplyReqDto;
import com.lt.win.apiend.io.dto.promotions.PromotionsGroupResDto;
import com.lt.win.apiend.service.IPromotionsService;
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
 * Author: David
 * Date: 2020/4/7
 * description: 活动相关
 *
 * @author david
 */
@Slf4j
//@RestController
@ApiSupport(author = "David")
@Api(tags = "优惠活动")
@RequestMapping("/v1/promotions")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PromotionsController {
    private final IPromotionsService iPromotionsServiceImpl;


    @UnCheckToken
    @ApiOperationSupport(order = 1)
    @PostMapping(value = "/promotionsList")
    @ApiOperation(value = "优惠活动->列表", notes = "优惠活动->列表")
    public Result<List<PromotionsGroupResDto>> promotionsList() {
        return Result.ok(iPromotionsServiceImpl.promotionsList());
    }

    @ApiOperationSupport(order = 2)
    @PostMapping(value = "/promotionsInfo")
    @ApiOperation(value = "优惠活动->申请", notes = "优惠活动->申请")
    public Result<String> promotionsApply(@Valid @RequestBody PromotionsApplyReqDto reqDto) {
        iPromotionsServiceImpl.promotionsApply(reqDto);
        return Result.ok();
    }
}
