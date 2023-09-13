package com.lt.win.apiend.controller;


import cn.hutool.core.util.EnumUtil;
import com.alibaba.fastjson.JSONObject;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSort;
import com.lt.win.apiend.aop.annotation.LogAnnotation;
import com.lt.win.apiend.aop.annotation.UnCheckToken;
import com.lt.win.apiend.io.bo.PlatformParams;
import com.lt.win.apiend.io.dto.platform.GameListInfo;
import com.lt.win.apiend.io.dto.platform.GameStartBo;
import com.lt.win.apiend.io.dto.platform.SlotGameFavoriteReqDto;
import com.lt.win.apiend.io.dto.platform.SlotGameReqDto;
import com.lt.win.apiend.service.IGameService;
import com.lt.win.apiend.service.IUserInfoService;
import com.lt.win.service.base.UserCoinBase;
import com.lt.win.service.cache.redis.ConfigCache;
import com.lt.win.service.io.dto.BaseParams;
import com.lt.win.service.io.enums.CurrencyEm;
import com.lt.win.service.io.qo.GameTypeQo;
import com.lt.win.service.thread.ThreadHeaderLocalData;
import com.lt.win.utils.components.pagination.ReqPage;
import com.lt.win.utils.components.pagination.ResPage;
import com.lt.win.utils.components.response.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * @author Davids
 */
@Slf4j
//@RestController
@Api(tags = "首页 - 平台游戏")
@ApiSort(3)
@RestController
@RequestMapping("/v1/platform")
//@Deprecated
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PlatformController {
    private final IGameService gameServiceImpl;

   // private final GameDriveManager gameDriveManager;

    private final IUserInfoService userInfoServiceImpl;

    private final UserCoinBase userCoinBase;

    private final ConfigCache configCache;


    @ApiOperationSupport(author = "dianfang", order = 1)
    @ApiOperation(value = "平台列表", notes = "平台列表")
    @PostMapping("/platList")
    public Result<List<PlatformParams.PlatListResInfo>> platList() {
        return Result.ok(gameServiceImpl.platList());
    }

    @UnCheckToken
    @ApiOperationSupport(author = "dianfang", order = 2)
    @ApiOperation(value = "游戏类型列表", notes = "游戏类型列表")
    @PostMapping("/gameList")
    public Result<List<GameListInfo>> gameList(@RequestBody GameTypeQo qo) {
        return Result.ok(gameServiceImpl.gameList(qo.getGroups()));
    }

//
//    @UnCheckToken
//    @PostMapping(value = "/casinoList")
//    @ApiOperationSupport(author = "dianfang", order = 3)
//    @ApiOperation(value = "casino列表", notes = "casino列表")
//    public Result<Map<Integer, List<PlatFactoryParams.PlatSlotGameResDto>>> casinoList(@Valid @RequestBody GameTypeQo qo) {
//        return Result.ok(gameServiceImpl.casinoList(qo.getGroups(), qo.getSize(), qo.getName()));
//    }

//
//    @UnCheckToken
//    @PostMapping(value = "/slotGameList")
//    @ApiOperationSupport(author = "dianfang", order = 4)
//    @ApiOperation(value = "游戏列表", notes = "游戏列表")
//    public Result<ResPage<PlatFactoryParams.PlatSlotGameResDto>> slotGameList(@Valid @RequestBody ReqPage<SlotGameReqDto> dto) {
//        return Result.ok(gameServiceImpl.slotGameList(dto));
//    }
//
//    @UnCheckToken
//    @ApiOperationSupport(author = "dianfang", order = 5)
//    @PostMapping(value = "/details")
//    @ApiOperation(value = "游戏详情", notes = "游戏详情")
//    @ApiResponses({
//            @ApiResponse(code = 0, message = "data: startGameUrl 游戏启动地址", response = String.class)
//    })
//    public Result<PlatFactoryParams.PlatSlotGameResDto> details(@Valid @RequestBody GameStartBo bo) {
//        return Result.ok(gameServiceImpl.gameDetails(bo.getId(), bo.getPlatId()));
//    }


    @ApiOperationSupport(author = "dianfang", order = 6)
    @PostMapping(value = "/gameFavorite")
    @ApiOperation(value = "收藏/取消 游戏", notes = "游戏收藏")
    public Result<Boolean> slotGameFavorite(@Valid @RequestBody SlotGameFavoriteReqDto dto) {
        BaseParams.HeaderInfo user = ThreadHeaderLocalData.HEADER_INFO_THREAD_LOCAL.get();
        return Result.ok(gameServiceImpl.slotGameFavorite(dto, user));
    }


    @ApiOperationSupport(author = "David", order = 7)
    @PostMapping(value = "/startGame")
    @ApiOperation(value = "获取游戏启动url", notes = "获取游戏启动url")
    @ApiResponses({
            @ApiResponse(code = 0, message = "data: startGameUrl 游戏启动地址", response = String.class)
    })
    @LogAnnotation(value = "StartGame", description = "StartGame")
    public Result<String> startGame(@Valid @RequestBody GameStartBo bo) {
        BaseParams.HeaderInfo user = ThreadHeaderLocalData.HEADER_INFO_THREAD_LOCAL.get();
        String currencyType = configCache.getCurrency();
        Map<String, CurrencyEm> map = EnumUtil.getEnumMap(CurrencyEm.class);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("currency", map.get(currencyType).getValue());
        jsonObject.put("country", map.get(currencyType).getCountry());

      //  return Result.ok(gameDriveManager.getStartGameUrl(bo.getId(), bo.getPlatId(), String.valueOf(bo.getPlayMode()), user, jsonObject));
   return null;
    }
}
