package com.lt.win.backend.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.google.common.base.CaseFormat;
import com.lt.win.backend.base.DictionaryBase;
import com.lt.win.backend.io.bo.GameRecord.GameDetailsReqBody;
import com.lt.win.backend.io.bo.GameRecord.GameListReqBody;
import com.lt.win.backend.io.dto.RecordParams;
import com.lt.win.backend.io.dto.RecordParams.*;
import com.lt.win.backend.io.dto.record.*;
import com.lt.win.backend.service.IGameRecordService;
import com.lt.win.dao.generator.po.GameList;
import com.lt.win.dao.generator.po.GameSlot;
import com.lt.win.dao.generator.service.GameListService;
import com.lt.win.dao.generator.service.GameSlotService;
import com.lt.win.service.common.Constant;
import com.lt.win.service.exception.BusinessException;
import com.lt.win.service.io.dto.BaseParams;
import com.lt.win.service.io.enums.LangEnum;
import com.lt.win.service.thread.ThreadHeaderLocalData;
import com.lt.win.utils.BeanConvertUtils;
import com.lt.win.utils.SpringUtils;
import com.lt.win.utils.components.pagination.ReqPage;
import com.lt.win.utils.components.pagination.ResPage;
import com.lt.win.utils.components.response.CodeInfo;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.expression.DateTimeLiteralExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static com.alibaba.fastjson.JSON.*;
import static java.util.Objects.nonNull;

/**
 * <p>
 * 游戏管理->平台管理
 * </p>
 *
 * @author andy
 * @since 2020/6/8
 */
@Slf4j
@Service
public class GameRecordServiceImpl implements IGameRecordService {
    /*注单表service实例表前缀*/
    public static final String BET_PREFIX = "betslips";
    /*注单表service实例表后缀*/
    public static final String BET_SUFFIX = "ServiceImpl";
    /*游戏Id的映射字段*/
    public static final String GAME_ID_FIELD = "gameIdField";
    /*游戏type的映射字段*/
    public static final String GAME_TYPE_FIELD = "gameTypeField";
    /*投注号码的映射字段*/
    public static final String BETContent = "betContent";
    public static final String ACTIONNO = "actionNo";
    /*玩家名称*/
    public static final String PLATYER_ID_FIELD = "playerIdField";
    /*字典表前缀*/
    public static final String DIC_PREFIX = "dic";
    /*字典表前缀*/
    public static final String CLASS_PATH = "com.lt.win.backend.io.dto.";
    public static final String CREATED_AT = "created_at";
    @Autowired
    private DictionaryBase dictionaryBase;
    @Autowired
    private GameSlotService gameSlotServiceImpl;
    @Resource
    private GameListService gameListServiceImpl;

    /**
     * 获取游戏列表
     *
     * @param dto
     * @return
     */
    @Override
    public List<GameListResBody> getGameListByGroupId(GameListReqBody dto) {
        BaseParams.HeaderInfo headerInfo = ThreadHeaderLocalData.HEADER_INFO_THREAD_LOCAL.get();//:en-英文, zh-cn-简体, zh-tw-繁体
        LambdaQueryWrapper<GameList> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(GameList::getGroupId, dto.getGroupId());
        wrapper.eq(GameList::getStatus, 1);
        wrapper.orderByAsc(GameList::getId);

        List<SubcatalogGameList> collect = null;
        Map<String, String> gameNameMap;
        List<GameList> gameList = gameListServiceImpl.list(wrapper);
        HashMap<Object, Object> objectObjectHashMap = new HashMap<>();
        for (var x : gameList) {
            var configJsonObject = parseObject("");
            var gameIdField = configJsonObject.getString(GAME_ID_FIELD);
            //电子游戏查询电子游戏列表数据
            if (x.getGroupId() == 2) {
                List<GameSlot> gameSlotList = gameSlotServiceImpl.lambdaQuery().eq(GameSlot::getGameId, x.getId()).list();
                gameNameMap = gameSlotList.stream().collect(Collectors.toMap(GameSlot::getId, (LangEnum.ZH.getValue().equals(headerInfo.getLang()) ? GameSlot::getNameZh : GameSlot::getName)));
                collect = gameNameMap.entrySet().stream().map(m -> SubcatalogGameList.builder().id(m.getKey()).name(m.getValue()).build()).collect(Collectors.toList());
                objectObjectHashMap.put(x.getId(), collect);
            } else {
                //拼接字典表的key
                var businessName = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, configJsonObject.getString(BET_PREFIX));
                var dicKey = DIC_PREFIX + "_" + BET_PREFIX + "_" + businessName + "_" + gameIdField;
                gameNameMap = dictionaryBase.getCategoryMap(dicKey);
                collect = gameNameMap.entrySet().stream().map(m -> SubcatalogGameList.builder().id(m.getKey()).name(m.getValue()).build()).collect(Collectors.toList());
                objectObjectHashMap.put(x.getId(), collect);
            }
        }

        return BeanConvertUtils.copyListProperties(gameList, GameListResBody::new, (bo, sb) ->
                sb.setSubcatalogGameList((List<SubcatalogGameList>) objectObjectHashMap.get(bo.getId()))
        );
    }

    /**
     * 获取总计
     *
     * @param dto
     * @return
     */
    @Override
    public GrossResBody getGross(QueryGameRecordDto dto) {
        GrossResBody grossResBody = new GrossResBody();
        var stringBuilder = new StringBuilder();
        //获取游戏的基本配置信息
        var gameLists = getConfig(dto);
        var configJsonObject =new JSONObject();
        //获取gameId的映射字段
        var gameIdField = configJsonObject.getString(GAME_ID_FIELD);
        var betslips = BET_PREFIX + configJsonObject.getString(BET_PREFIX) + BET_SUFFIX;
        var iService = (IService) SpringUtils.getBean(betslips);
        //有效投注额字段
        stringBuilder.append("ifNull(sum(xb_valid_coin),0 )as xb_valid_coin");
        //盈亏字段
        stringBuilder.append(",ifNull(sum(xb_profit),0)as xb_profit");
        //投注金额总额
        stringBuilder.append(",ifNull(sum(xb_coin),0)as xb_coin");
        var queryWrapper = new QueryWrapper<>()
                .eq(nonNull(dto.getUid()), "xb_uid", dto.getUid())
                .eq(nonNull(dto.getUsername()), "xb_username", dto.getUsername())
                .eq(nonNull(dto.getStatus()), "xb_status", dto.getStatus())
                .eq(nonNull(dto.getGameName()), gameIdField, dto.getGameName())
                .eq(nonNull(dto.getOrderId()), "id", dto.getOrderId())
                .ge(nonNull(dto.getStartTime()), Constant.UPDATED_AT, dto.getStartTime())
                .le(nonNull(dto.getEndTime()), Constant.UPDATED_AT, dto.getEndTime());
        grossResBody.setOrderNum(iService.count(queryWrapper));

        var object = iService.getMap(queryWrapper.select(stringBuilder.toString()));
        grossResBody.setValidAmount(new BigDecimal(String.valueOf(object.get("xb_valid_coin"))));
        grossResBody.setProfitAmount(new BigDecimal(String.valueOf(object.get("xb_profit"))));
        grossResBody.setBetAmount(new BigDecimal(String.valueOf(object.get("xb_coin"))));
        return grossResBody;
    }

    /**
     * 电竞列表
     *
     * @param dto
     * @return
     */
    @Override
    public ResPage<ESportsBetslips> getESportGameRecord(ReqPage<QueryGameRecordDto> dto) {
        try {
            ResPage<? extends ESportsBetslips> betsRecords = (ResPage<? extends ESportsBetslips>) getBetsRecords(dto);
            return (ResPage<ESportsBetslips>) betsRecords;
        } catch (Exception e) {
            log.info(e.toString());
            throw e;
        }
    }

    /**
     * 电游列表
     *
     * @param dto
     * @return
     */
    @Override
    public ResPage<EGamesBetslips> getEGamesRecord(ReqPage<QueryGameRecordDto> dto) {
        try {
            ResPage<? extends EGamesBetslips> betsRecords = (ResPage<? extends EGamesBetslips>) getBetsRecords(dto);
            return (ResPage<EGamesBetslips>) betsRecords;
        } catch (Exception e) {
            log.info(e.toString());
            throw e;
        }
    }

    /**
     * 棋牌类别
     *
     * @param dto
     * @return
     */
    @Override
    public ResPage<ChessBetslips> getChessGameRecord(ReqPage<QueryGameRecordDto> dto) {
        try {
            ResPage<? extends ChessBetslips> betsRecords = (ResPage<? extends ChessBetslips>) getBetsRecords(dto);
            return (ResPage<ChessBetslips>) betsRecords;
        } catch (Exception e) {
            log.info(e.toString());
            throw e;
        }
    }

    /**
     * 捕鱼类别
     *
     * @param dto
     * @return
     */
    @Override
    public ResPage<FishingBetslips> getFishingGameRecord(ReqPage<QueryGameRecordDto> dto) {
        try {
            ResPage<? extends FishingBetslips> betsRecords = (ResPage<? extends FishingBetslips>) getBetsRecords(dto);
            return (ResPage<FishingBetslips>) betsRecords;
        } catch (Exception e) {
            log.info(e.toString());
            throw e;
        }
    }

    /**
     * 真人列表
     *
     * @param dto
     * @return
     */
    @Override
    public ResPage<LiveBetslips> getLiveGameRecord(ReqPage<QueryGameRecordDto> dto) {
        try {
            ResPage<? extends LiveBetslips> betsRecords = (ResPage<? extends LiveBetslips>) getBetsRecords(dto);
            return (ResPage<LiveBetslips>) betsRecords;
        } catch (Exception e) {
            log.info(e.toString());
            throw e;
        }
    }

    /**
     * 动物竞技类别
     *
     * @param dto
     * @return
     */
    @Override
    public ResPage<AnimalFightBetslips> getAnimalFightGameRecord(ReqPage<QueryGameRecordDto> dto) {
        try {
            ResPage<? extends AnimalFightBetslips> betsRecords = (ResPage<? extends AnimalFightBetslips>) getBetsRecords(dto);
            return (ResPage<AnimalFightBetslips>) betsRecords;
        } catch (Exception e) {
            log.info(e.toString());
            throw e;
        }
    }

    /**
     * 彩票类别
     *
     * @param dto
     * @return
     */
    @Override
    public ResPage<LotteryBetslips> getLotteryGameRecord(ReqPage<QueryGameRecordDto> dto) {
        try {
            ResPage<? extends RecordParams.LotteryBetslips> betsRecords = (ResPage<? extends RecordParams.LotteryBetslips>) getBetsRecords(dto);
            return (ResPage<RecordParams.LotteryBetslips>) betsRecords;
        } catch (Exception e) {
            log.info(e.toString());
            throw e;
        }
    }

    /**
     * 体育类别
     *
     * @param dto
     * @return
     */
    @Override
    public ResPage<RecordParams.SportBetslips> getSportGameRecord(ReqPage<RecordParams.QueryGameRecordDto> dto) {
        try {
            ResPage<? extends RecordParams.SportBetslips> betsRecords = (ResPage<? extends RecordParams.SportBetslips>) getBetsRecords(dto);
            return (ResPage<RecordParams.SportBetslips>) betsRecords;
        } catch (Exception e) {
            log.info(e.toString());
            throw e;
        }
    }

    @Override
    public BetslipsMgDto getMgGameDetails(GameDetailsReqBody dto) {
        BetslipsMgDto betslipsMgDto = BeanConvertUtils.copyProperties(getGameDetails(dto, 203), BetslipsMgDto::new);
        Map map = parseObject(toJSONString(betslipsMgDto, SerializerFeature.WRITE_MAP_NULL_FEATURES), Map.class);
        return parseObject(toJSONString(toFormat(map)), BetslipsMgDto.class);
    }

    @Override
    public BetslipsUpgDto getUpgGameDetails(GameDetailsReqBody dto) {
        BetslipsUpgDto betslipsUpgDto = BeanConvertUtils.copyProperties(getGameDetails(dto, 204), BetslipsUpgDto::new);
        Map map = parseObject(toJSONString(betslipsUpgDto, SerializerFeature.WRITE_MAP_NULL_FEATURES), Map.class);
        return parseObject(toJSONString(toFormat(map)), BetslipsUpgDto.class);
    }

    @Override
    public BetslipsHbDto getHbGameDetails(GameDetailsReqBody dto) {
        BetslipsHbDto betslipsHbDto = BeanConvertUtils.copyProperties(getGameDetails(dto, 202), BetslipsHbDto::new);
        Map map = parseObject(toJSONString(betslipsHbDto, SerializerFeature.WRITE_MAP_NULL_FEATURES), Map.class);
        return parseObject(toJSONString(toFormat(map)), BetslipsHbDto.class);
    }

    @Override
    public BetslipsBbinGameDto getBbinGameDetails(GameDetailsReqBody dto) {
        BetslipsBbinGameDto betslipsBbinGameDto = BeanConvertUtils.copyProperties(getGameDetails(dto, 201), BetslipsBbinGameDto::new);
        Map map = parseObject(toJSONString(betslipsBbinGameDto, SerializerFeature.WRITE_MAP_NULL_FEATURES), Map.class);
        return parseObject(toJSONString(toFormat(map)), BetslipsBbinGameDto.class);
    }

    @Override
    public BetslipsBbinFishingExpertDto getBbinFishingExpertDetails(GameDetailsReqBody dto) {
        BetslipsBbinFishingExpertDto betslipsBbinFishingExpertDto = BeanConvertUtils.copyProperties(getGameDetails(dto, 402), BetslipsBbinFishingExpertDto::new);
        Map map = parseObject(toJSONString(betslipsBbinFishingExpertDto, SerializerFeature.WRITE_MAP_NULL_FEATURES), Map.class);
        return parseObject(toJSONString(toFormat(map)), BetslipsBbinFishingExpertDto.class);
    }

    @Override
    public BetslipsTcgDto getTcgLotteryDetails(GameDetailsReqBody dto) {
        BetslipsTcgDto betslipsTcgDto = BeanConvertUtils.copyProperties(getGameDetails(dto, 701), BetslipsTcgDto::new);
        Map map = parseObject(toJSONString(betslipsTcgDto, SerializerFeature.WRITE_MAP_NULL_FEATURES), Map.class);
        return parseObject(toJSONString(toFormat(map)), BetslipsTcgDto.class);
    }

    @Override
    public BetslipsSlottoDto getSlottoDetails(GameDetailsReqBody dto) {
        BetslipsSlottoDto betslipsSlottoDto = BeanConvertUtils.copyProperties(getGameDetails(dto, 702), BetslipsSlottoDto::new);
        Map map = parseObject(toJSONString(betslipsSlottoDto, SerializerFeature.WRITE_MAP_NULL_FEATURES), Map.class);
        return parseObject(toJSONString(toFormat(map)), BetslipsSlottoDto.class);
    }

    @Override
    public BetslipsAgDto getAgLiveDetails(GameDetailsReqBody dto) {
        BetslipsAgDto betslipsAgDto = BeanConvertUtils.copyProperties(getGameDetails(dto, 304), BetslipsAgDto::new);
        Map map = parseObject(toJSONString(betslipsAgDto, SerializerFeature.WRITE_MAP_NULL_FEATURES), Map.class);
        return parseObject(toJSONString(toFormat(map)), BetslipsAgDto.class);
    }

    @Override
    public BetslipsBbinLiveDto getBbinLiveGameDetails(GameDetailsReqBody dto) {
        BetslipsBbinLiveDto betslipsBbinLiveDto = BeanConvertUtils.copyProperties(getGameDetails(dto, 302), BetslipsBbinLiveDto::new);
        Map map = parseObject(toJSONString(betslipsBbinLiveDto, SerializerFeature.WRITE_MAP_NULL_FEATURES), Map.class);
        return parseObject(toJSONString(toFormat(map)), BetslipsBbinLiveDto.class);
    }

    @Override
    public BetslipsWmDto getVmLiveDetails(GameDetailsReqBody dto) {
        BetslipsWmDto betslipsWmDto = BeanConvertUtils.copyProperties(getGameDetails(dto, 301), BetslipsWmDto::new);
        Map map = parseObject(toJSONString(betslipsWmDto, SerializerFeature.WRITE_MAP_NULL_FEATURES), Map.class);
        return parseObject(toJSONString(toFormat(map)), BetslipsWmDto.class);
    }

    @Override
    public BetslipsDgDto getDgLiveDetails(GameDetailsReqBody dto) {
        BetslipsDgDto betslipsDgDto = BeanConvertUtils.copyProperties(getGameDetails(dto, 303), BetslipsDgDto::new);
        Map map = parseObject(toJSONString(betslipsDgDto, SerializerFeature.WRITE_MAP_NULL_FEATURES), Map.class);
        return parseObject(toJSONString(toFormat(map)), BetslipsDgDto.class);
    }

    @Override
    public BetslipsGgDto getGgLiveDetails(GameDetailsReqBody dto) {
        BetslipsGgDto betslipsGgDto = BeanConvertUtils.copyProperties(getGameDetails(dto, 401), BetslipsGgDto::new);
        Map map = parseObject(toJSONString(betslipsGgDto, SerializerFeature.WRITE_MAP_NULL_FEATURES), Map.class);
        return parseObject(toJSONString(toFormat(map)), BetslipsGgDto.class);
    }

    @Override
    public BetslipsDsDto getDsChessDetails(GameDetailsReqBody dto) {
        BetslipsDsDto betslipsDsDto = BeanConvertUtils.copyProperties(getGameDetails(dto, 501), BetslipsDsDto::new);
        Map map = parseObject(toJSONString(betslipsDsDto, SerializerFeature.WRITE_MAP_NULL_FEATURES), Map.class);
        return parseObject(toJSONString(toFormat(map)), BetslipsDsDto.class);

    }

    @Override
    public BetslipsShabaSportsDto getIbcSportDetails(GameDetailsReqBody dto) {
        BetslipsShabaSportsDto betslipsShabaSportsDto = BeanConvertUtils.copyProperties(getGameDetails(dto, 105), BetslipsShabaSportsDto::new);
        Map map = parseObject(toJSONString(betslipsShabaSportsDto, SerializerFeature.WRITE_MAP_NULL_FEATURES), Map.class);
        return parseObject(toJSONString(toFormat(map)), BetslipsShabaSportsDto.class);

    }


    @Override
    public BetslipsSboSportsDto getSboSportDetails(GameDetailsReqBody dto) {
        BetslipsSboSportsDto betslipsSboSportsDto = BeanConvertUtils.copyProperties(getGameDetails(dto, 101), BetslipsSboSportsDto::new);
        Map map = parseObject(toJSONString(betslipsSboSportsDto, SerializerFeature.WRITE_MAP_NULL_FEATURES), Map.class);
        return parseObject(toJSONString(toFormat(map)), BetslipsSboSportsDto.class);
    }

    @Override
    public BetslipsS128Dto getS128AnimalFightDetails(GameDetailsReqBody dto) {
        BetslipsS128Dto betslipsS128Dto = BeanConvertUtils.copyProperties(getGameDetails(dto, 801), BetslipsS128Dto::new);
        Map map = parseObject(toJSONString(betslipsS128Dto, SerializerFeature.WRITE_MAP_NULL_FEATURES), Map.class);
        return parseObject(toJSONString(toFormat(map)), BetslipsS128Dto.class);
    }

    @Override
    public BetslipsBbinFishingMasterDto getBbinFishingMasterDetails(GameDetailsReqBody dto) {
        BetslipsBbinFishingMasterDto betslipsBbinFishingMasterDto = BeanConvertUtils.copyProperties(getGameDetails(dto, 403), BetslipsBbinFishingMasterDto::new);

        Map map = parseObject(toJSONString(betslipsBbinFishingMasterDto, SerializerFeature.WRITE_MAP_NULL_FEATURES), Map.class);
        return parseObject(toJSONString(toFormat(map)), BetslipsBbinFishingMasterDto.class);
    }

    @Override
    public BetslipsAeSexyDto getAESexyLiveDetails(GameDetailsReqBody dto) {
        BetslipsAeSexyDto betslipsAeSexyDto = BeanConvertUtils.copyProperties(getGameDetails(dto, 305), BetslipsAeSexyDto::new);
        Map map = parseObject(toJSONString(betslipsAeSexyDto, SerializerFeature.WRITE_MAP_NULL_FEATURES), Map.class);
        return parseObject(toJSONString(toFormat(map)), BetslipsAeSexyDto.class);
    }

    @Override
    public BetslipsAeKingMakerDto getAEKingMakerChessDetails(GameDetailsReqBody dto) {
        BetslipsAeKingMakerDto betslipsAeKingMakerDto = BeanConvertUtils.copyProperties(getGameDetails(dto, 502), BetslipsAeKingMakerDto::new);
        Map map = parseObject(toJSONString(betslipsAeKingMakerDto, SerializerFeature.WRITE_MAP_NULL_FEATURES), Map.class);
        return parseObject(toJSONString(toFormat(map)), BetslipsAeKingMakerDto.class);
    }

    @Override
    public ResPage<SportBetslips> getSportRecord(ReqPage<QueryGameRecordDto> dto) {
        try {
            ResPage<? extends SportBetslips> betsRecords = (ResPage<? extends SportBetslips>) getBetsRecords(dto);
            return (ResPage<SportBetslips>) betsRecords;
        } catch (Exception e) {
            log.info(e.toString());
            throw e;
        }
    }

    @Override
    public BetslipsFuturesLotteryDto getFuturesDetails(GameDetailsReqBody dto) {
        BetslipsFuturesLotteryDto betslipsFuturesLotteryDto = BeanConvertUtils.copyProperties(getGameDetails(dto, 703), BetslipsFuturesLotteryDto::new);
        Map map = parseObject(toJSONString(betslipsFuturesLotteryDto, SerializerFeature.WRITE_MAP_NULL_FEATURES), Map.class);
        return parseObject(toJSONString(toFormat(map)), BetslipsFuturesLotteryDto.class);
    }

    @Override
    public BetslipsCq9GameDto getCQ9GameDetails(GameDetailsReqBody dto) {
        BetslipsCq9GameDto betslipsCq9GameDto = BeanConvertUtils.copyProperties(getGameDetails(dto, 205), BetslipsCq9GameDto::new);
        Map map = parseObject(toJSONString(betslipsCq9GameDto, SerializerFeature.WRITE_MAP_NULL_FEATURES), Map.class);
        return parseObject(toJSONString(toFormat(map)), BetslipsCq9GameDto.class);
    }

    @Override
    public BetslipsCq9ThunderFighterDto getCQ9ThunderFighterDetails(GameDetailsReqBody dto) {
        BetslipsCq9ThunderFighterDto betslipsCq9ThunderFighterDto = BeanConvertUtils.copyProperties(getGameDetails(dto, 405), BetslipsCq9ThunderFighterDto::new);
        Map map = parseObject(toJSONString(betslipsCq9ThunderFighterDto, SerializerFeature.WRITE_MAP_NULL_FEATURES), Map.class);
        return parseObject(toJSONString(toFormat(map)), BetslipsCq9ThunderFighterDto.class);
    }

    @Override
    public BetslipsCq9ParadiseDto getCQ9ParadiseDetails(GameDetailsReqBody dto) {
        BetslipsCq9ParadiseDto betslipsCq9ParadiseDto = BeanConvertUtils.copyProperties(getGameDetails(dto, 404), BetslipsCq9ParadiseDto::new);
        Map map = parseObject(toJSONString(betslipsCq9ParadiseDto, SerializerFeature.WRITE_MAP_NULL_FEATURES), Map.class);
        return parseObject(toJSONString(toFormat(map)), BetslipsCq9ParadiseDto.class);
    }



    @Override
    public BetslipsJokerHaibaDto getJokerHaibaDetail(GameDetailsReqBody dto) {
        BetslipsJokerHaibaDto betslipsJokerHaibaDto = BeanConvertUtils.copyProperties(getGameDetails(dto, 407), BetslipsJokerHaibaDto::new);
        Map map = parseObject(toJSONString(betslipsJokerHaibaDto, SerializerFeature.WRITE_MAP_NULL_FEATURES), Map.class);
        return parseObject(toJSONString(toFormat(map)), BetslipsJokerHaibaDto.class);
    }

    @Override
    public BetslipsSv388Dto getSV388CockFightDetail(GameDetailsReqBody dto) {
        BetslipsSv388Dto betslipsSv388Dto = BeanConvertUtils.copyProperties(getGameDetails(dto, 802), BetslipsSv388Dto::new);
        Map map = parseObject(toJSONString(betslipsSv388Dto, SerializerFeature.WRITE_MAP_NULL_FEATURES), Map.class);
        return parseObject(toJSONString(toFormat(map)), BetslipsSv388Dto.class);
    }

    @Override
    public BetslipsSaDto getSALiveDetails(GameDetailsReqBody dto) {
        BetslipsSaDto betslipsSaDto = BeanConvertUtils.copyProperties(getGameDetails(dto, 306), BetslipsSaDto::new);
        Map map = parseObject(toJSONString(betslipsSaDto, SerializerFeature.WRITE_MAP_NULL_FEATURES), Map.class);
        return parseObject(toJSONString(toFormat(map)), BetslipsSaDto.class);
    }

    @Override
    public BetslipsBtiDto getBtiSportDetails(GameDetailsReqBody dto) {
        BetslipsBtiDto betslipsBtiDto = BeanConvertUtils.copyProperties(getGameDetails(dto, 102), BetslipsBtiDto::new);
        Map map = parseObject(toJSONString(betslipsBtiDto, SerializerFeature.WRITE_MAP_NULL_FEATURES), Map.class);
        return parseObject(toJSONString(toFormat(map)), BetslipsBtiDto.class);
    }

    @Override
    public BetslipsPgChessDto getPGChessDetails(GameDetailsReqBody dto) {
        BetslipsPgChessDto betslipsPgChessDto = BeanConvertUtils.copyProperties(getGameDetails(dto, 503), BetslipsPgChessDto::new);
        Map map = parseObject(toJSONString(betslipsPgChessDto, SerializerFeature.WRITE_MAP_NULL_FEATURES), Map.class);
        return parseObject(toJSONString(toFormat(map)), BetslipsPgChessDto.class);
    }

    @Override
    public BetslipsPgGameDto getPGGameDetails(GameDetailsReqBody dto) {
        BetslipsPgGameDto betslipsPgGameDto = BeanConvertUtils.copyProperties(getGameDetails(dto, 207), BetslipsPgGameDto::new);
        Map map = parseObject(toJSONString(betslipsPgGameDto, SerializerFeature.WRITE_MAP_NULL_FEATURES), Map.class);
        return parseObject(toJSONString(toFormat(map)), BetslipsPgGameDto.class);
    }

    @Override
    public BetslipsEbetDto getEBetDetails(GameDetailsReqBody dto) {
        BetslipsEbetDto betslipsEbetDto = BeanConvertUtils.copyProperties(getGameDetails(dto, 307), BetslipsEbetDto::new);
        Map map = parseObject(toJSONString(betslipsEbetDto, SerializerFeature.WRITE_MAP_NULL_FEATURES), Map.class);
        return parseObject(toJSONString(toFormat(map)), BetslipsEbetDto.class);
    }



    /**
     * 注单详情获取
     *
     * @param dto
     * @return
     */
    public Object getGameDetails(GameDetailsReqBody dto, Integer gameId) {
        var gameLists = gameListServiceImpl.lambdaQuery()
                .eq(nonNull(gameId), GameList::getId, gameId)
                //启动状态
                .eq(GameList::getStatus, 1)
                .list();
        if (CollectionUtils.isEmpty(gameLists)) {
            throw new BusinessException(CodeInfo.GAME_RECORDS_EMPTY);
        }
        var configJsonObject =new JSONObject();
        var betslips = BET_PREFIX + configJsonObject.getString(BET_PREFIX) + BET_SUFFIX;
        var iService = (IService) SpringUtils.getBean(betslips);
        var receipt = iService.getOne(new QueryWrapper().eq(nonNull(dto.getOrderId()), "id", dto.getOrderId()));
        if (receipt != null) {
            return receipt;
        } else {
            throw new BusinessException(CodeInfo.USER_TRANSACTION_RECORD_LIST_ERROR);
        }

    }


    /**
     * 类别列表返回集
     *
     * @param platGameQueryDateDto
     * @return
     */
    @SneakyThrows
    public ResPage<?> getBetsRecords(@Valid ReqPage<RecordParams.QueryGameRecordDto> platGameQueryDateDto) {

        ReqPage<QueryGameRecordDto> m = new ReqPage<>();
        m.setPage(platGameQueryDateDto.getCurrent(), platGameQueryDateDto.getSize());
        m.setSortField(platGameQueryDateDto.getSortField());
        m.setSortKey(platGameQueryDateDto.getSortKey());
        m.setData(platGameQueryDateDto.getData());

        BaseParams.HeaderInfo headerInfo = ThreadHeaderLocalData.HEADER_INFO_THREAD_LOCAL.get();//:en-英文, zh-cn-简体, zh-tw-繁体
        int i = LangEnum.ZH.getValue().equals(headerInfo.getLang()) ? 0 : 1;
        var reqDto = m.getData();
        //获取游戏的基本配置信息
        var gameLists = getConfig(reqDto);
        var resPage = new ResPage<>();
        for (var game : gameLists) {
            var configJsonObject = new JSONObject();
            var betslips = BET_PREFIX + configJsonObject.getString(BET_PREFIX) + BET_SUFFIX;
            var iService = (IService) SpringUtils.getBean(betslips);
            var iGame = String.format("RecordParams$%sBetslips", switchDto(gameLists));
            String className2 = CLASS_PATH + iGame;
            Class<?> aClass2 = Class.forName(className2);

            Map<String, String> gameNameMap;
            //获取gameId的映射字段
            var gameIdField = configJsonObject.getString(GAME_ID_FIELD);
            var gameIdFormat = CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, gameIdField);
            //获取投注数字的映射字段
            var betContentField = null == configJsonObject.getString(BETContent) ? "--" : configJsonObject.getString(BETContent);
            var betContentFormat = CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, betContentField);
            //获取gameType的映射字段
            var gameTypeField = configJsonObject.getString(GAME_TYPE_FIELD) == null ? "--" : configJsonObject.getString(GAME_TYPE_FIELD);
            var gameTypeFormat = CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, gameTypeField);
            //获取期号的映射字段
            var actionNoField = null == configJsonObject.getString(ACTIONNO) ? "--" : configJsonObject.getString(ACTIONNO);
            var actionNoFormat = CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, actionNoField);
            //电子游戏查询电子游戏列表数据
            if (game.getGroupId() == 2) {
                List<GameSlot> gameSlotList = gameSlotServiceImpl.lambdaQuery().eq(GameSlot::getGameId, game.getId()).list();
                gameNameMap = gameSlotList.stream().collect(Collectors.toMap(GameSlot::getId, gameSlot -> gameSlot.getNameZh() + "_" + gameSlot.getName() + "_" + gameSlot.getGameTypeName()));
            } else {
                //拼接字典表的key
                var businessName = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, configJsonObject.getString(BET_PREFIX));
                var dicKey = DIC_PREFIX + "_" + BET_PREFIX + "_" + businessName + "_" + gameIdField;
                gameNameMap = dictionaryBase.getCategoryMap(dicKey);
            }

            var queryWrapper = new QueryWrapper<>()
                    .eq(nonNull(reqDto.getUid()), "xb_uid", reqDto.getUid())
                    .eq(nonNull(reqDto.getUsername()), "xb_username", reqDto.getUsername())
                    .eq(nonNull(reqDto.getStatus()), "xb_status", reqDto.getStatus())
                    .eq(nonNull(reqDto.getGameName()), gameIdField, reqDto.getGameName())
                    .eq(nonNull(reqDto.getOrderId()), "id", reqDto.getOrderId())
                    .ge(nonNull(reqDto.getStartTime()), Constant.UPDATED_AT, reqDto.getStartTime())
                    .le(nonNull(reqDto.getEndTime()), Constant.UPDATED_AT, reqDto.getEndTime());

            //转换排序字段
            var reqPage = platGameQueryDateDto.getPage();
            List<OrderItem> orders = reqPage.getOrders();
            if (!CollectionUtils.isEmpty(orders)) {
                orders.forEach(item -> {
                    if (item.getColumn().equals("createdAt")) {
                        item.setColumn(CREATED_AT);
                    }
                    if (item.getColumn().equals("name")) {
                        item.setColumn(gameIdField);
                    }
                });
            }
            var page = iService.page(reqPage, queryWrapper);
            //转换Page为ResPage
            var list = page.getRecords();

            if (!CollectionUtils.isEmpty(list)) {
                var jsonList = (List<?>) list.stream().map(x -> {
                    var json = parseObject(toJSONString(x));
                    var key = json.getString(gameIdFormat);
                    var gameTypekey = json.getString(gameTypeFormat) == null ? "--" : json.getString(gameTypeFormat);
                    var gameName = gameNameMap.getOrDefault(key, key);
                    gameName = gameName != null && gameName.contains("_") ? gameName.split("_")[i] : gameName;
                    var gameType = gameNameMap.getOrDefault(gameTypekey, gameTypekey);
                    gameType = gameType.contains("_") ? gameType.split("_")[2] : gameType;
                    json.put("gameName", gameName);
                    if (game.getGroupId() == 7) {
                        gameType = json.getString(betContentFormat);
                        var actionNo = json.getString(actionNoFormat);
                        json.put("actionNo", actionNo);
                    }
                    String regEx = "[`~!@#$%^&*()+=|{};\\\\[\\\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、,？'\"]";
                    json.put("gameType", Pattern.compile(regEx).matcher(gameType).replaceAll(" ").trim());
                    return parseObject(json.toJSONString(), aClass2);
                }).collect(Collectors.toList());
                resPage = ResPage.get((Page) page);
                resPage.setList((List<Object>) jsonList);
            }
        }
        return resPage;
    }

    /**
     * 获取类别
     *
     * @param gameLists
     * @return
     */
    private String switchDto(List<GameList> gameLists) {
        Integer groupId = gameLists.get(0).getGroupId();
        Map<Integer, String> playType = Map.of(
                1, "Sport", 2, "EGames",
                3, "Live", 4, "Fishing",
                5, "Chess", 6, "ESport",
                7, "Lottery", 8, "AnimalFight");
        return playType.get(groupId);

    }

    /**
     * 获取游戏配置
     *
     * @param reqDto
     * @return
     */
    public List<GameList> getConfig(@Valid RecordParams.QueryGameRecordDto reqDto) {
        var gameLists = gameListServiceImpl.lambdaQuery()
                .eq(nonNull(reqDto.getId()), GameList::getId, reqDto.getId())
                //启动状态
                .eq(GameList::getStatus, 1)
                .list();
        //判断游戏是否存在
        if (CollectionUtils.isEmpty(gameLists)) {
            throw new BusinessException(CodeInfo.GAME_RECORDS_EMPTY);
        }
        return gameLists;
    }

    /**
     * 出参格式化
     *
     * @param map
     * @return
     */
    private Map toFormat(Map map) {
        if (map != null) {
            map.forEach((k, v) -> {
                if (null == v || v.equals("")) {
                    if (v instanceof Integer) {
                        map.put(k, 0);
                    }
                    if (v instanceof BigDecimal) {
                        map.put(k, 0.00);
                    }
                    if (v instanceof DateTimeLiteralExpression.DateTime) {
                        map.put(k, "-");
                    }
                    if (v instanceof String) {
                        map.put(k, "_");
                    }
                }
            });
        }
        return map;
    }
}
