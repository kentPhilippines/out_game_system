package com.lt.win.backend.controller;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.lt.win.backend.io.dto.PromotionsParameter.*;
import com.lt.win.backend.service.IPromoService;
import com.lt.win.dao.generator.po.Blog;
import com.lt.win.service.io.dto.BaseParams;
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
 * @author: David
 * @date: 2022/8/31
 * @description: 活动配置
 */
@Slf4j
@RestController
@ApiSupport(author = "David")
@RequestMapping("/v1/promotion")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Api(tags = "活动配置")
public class PromotionsController {
    private final IPromoService promoServiceImpl;

    @PostMapping(value = "/promotionsList")
    @ApiOperation(value = "活动列表", notes = "活动列表")
    @ApiOperationSupport(order = 2)
    public Result<ResPage<ListResDto>> promotionsList(@Valid @RequestBody ReqPage<ListReqDto> reqDto) {
        return Result.ok(promoServiceImpl.promotionsList(reqDto));
    }

    @PostMapping(value = "/saveOrUpdatePromotions")
    @ApiOperation(value = "新增或修改活动", notes = "新增或修改活动")
    @ApiOperationSupport(order = 3)
    public Result<String> saveOrUpdatePromotions(@Valid @RequestBody SavaOrUpdateReqDto reqDto) {
        promoServiceImpl.saveOrUpdatePromotions(reqDto);
        return Result.ok();
    }

    @PostMapping(value = "/deletePromotions")
    @ApiOperation(value = "删除活动", notes = "删除活动")
    @ApiOperationSupport(order = 4)
    public Result<Boolean> deletePromotions(@Valid @RequestBody DeleteReqDto reqDto) {
        return Result.ok(promoServiceImpl.deletePromotions(reqDto));
    }

    @PostMapping(value = "/getPromotionsByLang")
    @ApiOperation(value = "根据语言获取活动信息", notes = "根据语言获取活动信息")
    @ApiOperationSupport(order = 18)
    public Result<PromotionsByLangResDto> getPromotionsByLang(@Valid @RequestBody PromotionsByLangReqDto reqDto) {
        return Result.ok(promoServiceImpl.getPromotionsByLang(reqDto));
    }

    @PostMapping(value = "/rewardsList")
    @ApiOperation(value = "活动彩金列表", notes = "活动彩金列表")
    @ApiOperationSupport(order = 19)
    public Result<ResPage<RewardsListResDto>> rewardsList(@Valid @RequestBody ReqPage<RewardsListReqDto> dto) {
        return Result.ok(promoServiceImpl.rewardsList(dto));
    }

    @PostMapping(value = "/blogList")
    @ApiOperation(value = "博客列表", notes = "博客列表")
    @ApiOperationSupport(order = 31)
    public Result<ResPage<Blog>> blogList(@Valid @RequestBody ReqPage<BlogListReqDto> dto) {
        return Result.ok(promoServiceImpl.blogList(dto));
    }

    @PostMapping(value = "/blogSaveOrUpdate")
    @ApiOperation(value = "博客列表 - 新增或修改", notes = "博客列表 - 新增或修改")
    @ApiOperationSupport(order = 32)
    public Result<String> blogSaveOrUpdate(@Valid @RequestBody BlogSaveOrUpdateReqDto dto) {
        promoServiceImpl.blogSaveOrUpdate(dto);
        return Result.ok();
    }

    @PostMapping(value = "/blogDelete")
    @ApiOperation(value = "博客列表 - 删除", notes = "博客列表 - 删除")
    @ApiOperationSupport(order = 33)
    public Result<String> blogDelete(@Valid @RequestBody BaseParams.IdParams dto) {
        promoServiceImpl.blogDelete(dto);
        return Result.ok();
    }
}
