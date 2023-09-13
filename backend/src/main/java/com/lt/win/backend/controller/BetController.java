package com.lt.win.backend.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.lt.win.backend.io.dto.Platform;
import com.lt.win.backend.service.impl.PlatManagerServiceImpl;
import com.lt.win.service.impl.BetslipsServiceImpl;
import com.lt.win.service.io.dto.Betslips;
import com.lt.win.service.io.dto.admin.BetStatisticsDto;
import com.lt.win.service.io.qo.AdminBetQo;
import com.lt.win.service.io.qo.GameTypeQo;
import com.lt.win.utils.components.pagination.ReqPage;
import com.lt.win.utils.components.pagination.ResPage;
import com.lt.win.utils.components.response.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

/**
 * 投注记录 投注
 *
 * @author fangzs
 * @date 2022/8/25 02:27
 */
@Slf4j
@RestController
@RequestMapping("/v1/bet")
@Api(tags = "投注记录")
public class BetController {
    @Autowired
    private BetslipsServiceImpl betslipsServiceImpl;
    @Autowired
    private PlatManagerServiceImpl platManagerServiceImpl;

    @ApiOperationSupport(author = "dianfang", order = 1)
    @ApiOperation(value = "投注分页", notes = "投注分页")
    @PostMapping("/page")
    public Result<ResPage<Betslips>> page(@RequestBody ReqPage<AdminBetQo> reqPage) throws Exception {
        return Result.ok(betslipsServiceImpl.queryBets(reqPage));
    }

    @ApiOperationSupport(author = "dianfang", order = 2)
    @ApiOperation(value = "投注统计", notes = "投注统计")
    @PostMapping("/betStatistics")
    public Result<List<BetStatisticsDto>> betStatistics(@RequestBody AdminBetQo qo) throws Exception {
        return Result.ok(betslipsServiceImpl.betStatistics(qo));
    }


    @ApiOperation(value = "游戏类型列表", notes = "游戏类型列表")
    @ApiOperationSupport(author = "dianfnag", order = 3)
    @PostMapping("/gameList")
    public Result<List<Platform.GameListInfo>> gameList(@RequestBody GameTypeQo qo) {
        return Result.ok(platManagerServiceImpl.gameList(qo.getGroups()));
    }


   /* @ApiOperation(value = "体育注单详情", notes = "体育注单详情")
    @ApiOperationSupport(author = "piking", order = 3)
    @PostMapping("/sportDetails")
    public Result<SportUserDto> sportDetails(@RequestBody AdminBetQo qo) throws Exception {
        Betslips bs = betslipsServiceImpl.getBetslipsById(qo.getId());
        if (null != bs) {
            SportUserDto sport = new SportUserDto();
            JSONObject jj = JSONObject.parseObject(bs.getBetJson());
            CreditBetBo betBo = JSONObject.toJavaObject(jj, CreditBetBo.class);

            sport.setXbStatus(bs.getXbStatus());
            sport.setDtCompleted(bs.getDtCompleted());
            sport.setDtStarted(bs.getDtStarted());
            return Result.ok(sportUserDto(sport, betBo));
        }

        return null;
    }
    private SportUserDto sportUserDto(SportUserDto sportUserDto, CreditBetBo bo) {
        sportUserDto.setFactor(BigDecimal.valueOf(bo.getOrder().getBets().get(0).getFactor()).setScale(2, RoundingMode.DOWN).doubleValue());
        sportUserDto.setMaxWinAmount(bo.getOrder().getMaxWinAmount().setScale(2,RoundingMode.DOWN));
        sportUserDto.setOrderAmount(bo.getOrder().getOrderAmount());
        sportUserDto.setTypeId(bo.getTypeId());
        sportUserDto.setTransactionId(bo.getTransactionId());
        ViewOrderBetDto betDto;
        List<ViewOrderBetDto> betDtos = new ArrayList<>();
        //单投汇率
        if (1 == bo.getTypeId()) {
            ViewOrderBetStake stake = bo.getOrder().getBets().get(0).getBetStakes().get(0);
            betDto = new ViewOrderBetDto();
            betDto.setSportID(stake.getSportID());
            betDto.setSportName(stake.getSportName());
            betDto.setTournamentName(stake.getTournamentName());
            betDto.setPeriodName(stake.getPeriodName());
            betDto.setTeams(stake.getTeams());
            betDto.setEventDateStr(stake.getEventDateStr());
            betDto.setStakeId(stake.getStakeId());
            betDtos.add(betDto);
            sportUserDto.setBets(betDtos);
            sportUserDto.setSportTypeName(stake.getSportName());
            return sportUserDto;
        }
        //多投汇率
        if (3 == bo.getTypeId() || 2 == bo.getTypeId()) {
            List<ViewOrderBet> Bets = bo.getOrder().getBets();
            List<ViewOrderBetStake> BetStakes;
            for (ViewOrderBet bet : Bets) {
                BetStakes = bet.getBetStakes();
                StringBuilder typeName = new StringBuilder();
                for (int i = 0; i < BetStakes.size(); i++) {
                    betDto = new ViewOrderBetDto();
                    betDto.setSportID(BetStakes.get(i).getSportID());
                    betDto.setSportName(BetStakes.get(i).getSportName());
                    betDto.setTournamentName(BetStakes.get(i).getTournamentName());
                    betDto.setPeriodName(BetStakes.get(i).getPeriodName());
                    betDto.setTeams(BetStakes.get(i).getTeams());
                    betDto.setEventDateStr(BetStakes.get(i).getEventDateStr());
                    betDto.setStakeId(BetStakes.get(i).getStakeId());
                    betDto.setFactor(BigDecimal.valueOf(BetStakes.get(i).getFactor()).setScale(2, RoundingMode.DOWN).doubleValue());
                    betDtos.add(betDto);
                    typeName.append(BetStakes.get(i).getSportName()).append(",");
                }
                sportUserDto.setSportTypeName(typeName.substring(0,typeName.length()-1));
                sportUserDto.setBets(betDtos);
            }
        }
        return sportUserDto;
    }*/

}
