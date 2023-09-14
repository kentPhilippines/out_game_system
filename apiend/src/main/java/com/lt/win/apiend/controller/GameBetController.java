package com.lt.win.apiend.controller;

import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSONObject;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSort;
import com.github.yitter.idgen.YitIdHelper;
import com.lt.win.apiend.aop.annotation.UnCheckToken;
import com.lt.win.service.base.PlatCodeEnum;
import com.lt.win.service.cache.redis.ConfigCache;
import com.lt.win.service.cache.redis.DigiTokenCache;
import com.lt.win.service.cache.redis.PlatListCache;
import com.lt.win.service.cache.redis.UserCache;
import com.lt.win.service.impl.BetslipsServiceImpl;
import com.lt.win.service.io.dto.BaseParams;
import com.lt.win.service.io.dto.Betslips;
import com.lt.win.service.io.qo.BetQo;
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
//
//    @ApiOperationSupport(author = "nobody", order = 1)
//    @ApiOperation(value = "我的体育注单", notes = "我的体育注单")
//    @PostMapping("/queryMySportBets")
//    public Result<ResPage<SportUserDto>> queryMySportBets(@RequestBody @Valid ReqPage<BetQo> reqPage) {
//        try {
//            BaseParams.HeaderInfo userInfo = ThreadHeaderLocalData.HEADER_INFO_THREAD_LOCAL.get();
//            log.info("我的体育注单查询用户{}，入参{}", userInfo.getUsername(), JSONUtil.toJsonStr(reqPage));
//            ResPage<Betslips> pageResult = betslipsService.queryMySportBets(reqPage, userInfo);
//
//            List<Betslips> list = pageResult.getList();
//            SportUserDto sport;
//            CreditBetBo betBo;
//            DebitRequestItem debitBo = null;
//            List<SportUserDto> listSport = new ArrayList<>();
//            if (null != list && list.size() > 0) {
//                for (Betslips bs : list) {
//                    log.info("结果bs.getBetJson(){}", bs.getBetJson());
//                    JSONObject bet = JSONObject.parseObject(bs.getBetJson());
//                    betBo = JSONObject.toJavaObject(bet, CreditBetBo.class);
//
//                    sport = new SportUserDto();
//                    sport.setXbStatus(bs.getXbStatus());
//                    sport.setDtCompleted(bs.getDtCompleted());
//                    sport.setDtStarted(bs.getDtStarted());
//                    if (bs.getXbStatus() == 2) {
//                        JSONObject reward = JSONObject.parseObject(bs.getRewardJson());
//                        debitBo = JSONObject.toJavaObject(reward, DebitRequestItem.class);
//                    }
//                    listSport.add(sportUserDto(sport, betBo, debitBo));
//                }
//            }
//            ResPage<SportUserDto> result = new ResPage<>();
//            result.setCurrent(pageResult.getCurrent());
//            result.setSize(pageResult.getSize());
//            result.setTotal(pageResult.getTotal());
//            result.setList(listSport);
//            result.setPages(pageResult.getPages());
//            log.info("我的体育注单查询用户{}，结果{}", userInfo.getUsername(), JSONUtil.toJsonStr(result));
//            return Result.ok(result);
//        } catch (Exception e) {
//            log.error("我的体育注单查询异常：", e);
//        }
//        return null;
//    }
//
//    private SportUserDto sportUserDto(SportUserDto sportUserDto, CreditBetBo bo, DebitRequestItem debitBo) {
//        sportUserDto.setFactor(BigDecimal.valueOf(bo.getOrder().getBets().get(0).getFactor()).setScale(2, RoundingMode.DOWN).doubleValue());
//        sportUserDto.setMaxWinAmount(bo.getOrder().getMaxWinAmount().setScale(2, RoundingMode.DOWN));
//        sportUserDto.setOrderAmount(bo.getOrder().getOrderAmount());
//        sportUserDto.setTypeId(bo.getTypeId());
//        sportUserDto.setTransactionId(bo.getTransactionId());
//
//        ViewOrderBetDto betDto;
//        List<ViewOrderBetDto> betDtos = new ArrayList<>();
//        //单投
//        if (1 == bo.getTypeId()) {
//            ViewOrderBetStake stake = bo.getOrder().getBets().get(0).getBetStakes().get(0);
//            betDto = new ViewOrderBetDto();
//            betDto.setSportID(stake.getSportID());
//            betDto.setSportName(stake.getSportName());
//            betDto.setTournamentName(stake.getTournamentName());
//            betDto.setPeriodName(stake.getPeriodName());
//            betDto.setTeams(stake.getTeams());
//            betDto.setEventDateStr(stake.getEventDateStr());
//            betDto.setStakeId(stake.getStakeId());
//            if (null != debitBo && null != debitBo.getData() && null != debitBo.getData().getStakeStatuses() && debitBo.getData().getStakeStatuses().size() > 0) {
//                betDto.setScore(debitBo.getData().getStakeStatuses().get(0).getScore());
//            }
//            betDtos.add(betDto);
//            sportUserDto.setBets(betDtos);
//            return sportUserDto;
//        }
//        //多投
//        if (3 == bo.getTypeId() || 2 == bo.getTypeId()) {
//            List<ViewOrderBet> bets = bo.getOrder().getBets();
//            List<ViewOrderBetStake> betStakes;
//            List<StakeStatusesRequestItem> stakeStatuses;
//            for (ViewOrderBet bet : bets) {
//                betStakes = bet.getBetStakes();
//                for (ViewOrderBetStake betStake : betStakes) {
//                    betDto = new ViewOrderBetDto();
//                    betDto.setSportID(betStake.getSportID());
//                    betDto.setSportName(betStake.getSportName());
//                    betDto.setTournamentName(betStake.getTournamentName());
//                    betDto.setPeriodName(betStake.getPeriodName());
//                    betDto.setTeams(betStake.getTeams());
//                    betDto.setEventDateStr(betStake.getEventDateStr());
//                    betDto.setStakeId(betStake.getStakeId());
//                    betDto.setFactor(BigDecimal.valueOf(betStake.getFactor()).setScale(2, RoundingMode.DOWN).doubleValue());
//                    betDto.setScore(betStake.getScore());
//                    if (null != debitBo && null != debitBo.getData() && null != debitBo.getData().getStakeStatuses() && debitBo.getData().getStakeStatuses().size() > 0) {
//                        Integer stakeId = betStake.getStakeId();
//                        stakeStatuses = debitBo.getData().getStakeStatuses().stream().filter(item -> stakeId == item.getStakeID().intValue()).collect(Collectors.toList());
//                        if (stakeStatuses.size() > 0) {
//                            betDto.setScore(stakeStatuses.get(0).getScore());
//                        }
//                    }
//                    betDtos.add(betDto);
//                }
//            }
//        }
//        sportUserDto.setBets(betDtos);
//        return sportUserDto;
//    }


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
