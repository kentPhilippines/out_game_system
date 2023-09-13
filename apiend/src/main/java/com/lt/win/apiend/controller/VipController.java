package com.lt.win.apiend.controller;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSort;
import com.lt.win.apiend.aop.annotation.UnCheckToken;
import com.lt.win.apiend.service.IVipService;
import com.lt.win.service.io.bo.VipBo;
import com.lt.win.utils.components.response.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Vip等级
 *
 * @author david
 */
@Slf4j
@RestController
@RequestMapping("/v1/vip")
@Api(tags = "VIP会员之家")
@ApiSort(6)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class VipController {
    private final IVipService vipServiceImpl;

    @ApiOperationSupport(author = "David", order = 1)
    @ApiOperation(value = "代理中心-MemberShip", notes = "代理中心-MemberShip")
    @PostMapping("/memberShipDetails")
    public Result<VipBo.MemberShipResDto> memberShipDetails() {
        return Result.ok(vipServiceImpl.memberShipDetails());
    }


    @UnCheckToken
    @ApiOperationSupport(author = "David", order = 1)
    @ApiOperation(value = "代理中心-MemberShip Level", notes = "代理中心-MemberShip Level")
    @PostMapping("/memberShipLevelDetails")
    public Result<List<VipBo.MemberShipLevelResDto>> memberShipLevelDetails() {
        return Result.ok(vipServiceImpl.memberShipLevelDetails());
    }
}
