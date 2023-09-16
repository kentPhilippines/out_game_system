package com.lt.win.apiend.controller;

import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSONObject;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSort;
import com.github.yitter.idgen.YitIdHelper;
import com.lt.win.apiend.aop.annotation.UnCheckToken;
import com.lt.win.dao.generator.po.LotteryBetslips;
import com.lt.win.service.base.PlatCodeEnum;
import com.lt.win.service.cache.redis.ConfigCache;
import com.lt.win.service.cache.redis.DigiTokenCache;
import com.lt.win.service.cache.redis.PlatListCache;
import com.lt.win.service.cache.redis.UserCache;
import com.lt.win.service.impl.BetslipsServiceImpl;
import com.lt.win.service.impl.LBServiceImpl;
import com.lt.win.service.io.dto.BaseParams;
import com.lt.win.service.io.dto.Betslips;
import com.lt.win.service.io.qo.BetQo;
import com.lt.win.service.io.qo.Betslip;
import com.lt.win.service.thread.ThreadHeaderLocalData;
import com.lt.win.utils.IdUtils;
import com.lt.win.utils.components.pagination.ReqPage;
import com.lt.win.utils.components.pagination.ResPage;
import com.lt.win.utils.components.response.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 注单管理
 *
 * @author fangzs
 * @date 2022/8/12 20:45
 */
@Slf4j
@Api(tags = "注单接口")
@ApiSort(4)
//@RestController
@RequestMapping("/v1/bet")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class GameBetController {

    @Autowired
    BetslipsServiceImpl betslipsService;

    @Autowired
    UserCache userCache;

    @Autowired
    ConfigCache configCache;

    @Resource
    private DigiTokenCache digiTokenCache;

    @Resource
    private PlatListCache platListCache;

    @ApiOperationSupport(author = "David", order = 1)
    @ApiOperation(value = "我的注单", notes = "我的注单")
    @PostMapping("/queryMyBets")
    public Result<ResPage<Betslips>> queryMyBets(@RequestBody @Valid ReqPage<BetQo> reqPage) throws Exception {
        BaseParams.HeaderInfo userInfo = ThreadHeaderLocalData.HEADER_INFO_THREAD_LOCAL.get();
        return Result.ok(betslipsService.queryMyBets(reqPage, userInfo));
    }

    @ApiOperationSupport(author = "nobody", order = 1)
    @ApiOperation(value = "体育登录token", notes = "体育登录token")
    @PostMapping("/getSportToken")
    public Result<String> getSportToken() {
        BaseParams.HeaderInfo userInfo = ThreadHeaderLocalData.HEADER_INFO_THREAD_LOCAL.get();
        if (null == userInfo) {
            return null;
        }
        String token = userInfo.getToken();
        StringBuilder sb = new StringBuilder();
        sb.append(token.substring(token.length() - 20));
        sb.append(IdUtils.nextId());
        sb.append("-");
        sb.append(userInfo.getId());
        digiTokenCache.addDigiUserInfoToken(userInfo.id.toString(), sb.toString());
        return Result.ok(sb.toString());
    }
//    @UnCheckToken
//    @ApiOperationSupport(author = "David", order = 4)
//    @ApiOperation(value = "获取体育前端路由", notes = "获取体育前端路由")
//    @GetMapping("/getSportFrontUrl")
//    public Result<String> getSportFrontUrl(){
//        DigiSportConfigBo configBo = platListCache.platListConfig(PlatCodeEnum.DIGISPORT.getCode(), DigiSportConfigBo.class);
//        return Result.ok(configBo.getSportFrontUrl());
//    }
}
