package com.lt.win.backend.service.impl;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lt.win.backend.io.bo.BetPlatformProfitBo;
import com.lt.win.backend.io.bo.ReportCenter.PlatformProfitListReqBody;
import com.lt.win.backend.io.bo.ReportCenter.PlatformProfitListResBody;
import com.lt.win.backend.io.bo.ReportCenter.PlatformProfitResBody;
import com.lt.win.backend.redis.GameCache;
import com.lt.win.dao.generator.po.GameSlot;
import com.lt.win.dao.generator.po.UserLevel;
import com.lt.win.service.base.ESSqlSearchBase;
import com.lt.win.service.base.EsTableNameBase;
import com.lt.win.service.cache.redis.PlatListCache;
import com.lt.win.service.cache.redis.UserCache;
import com.lt.win.service.exception.BusinessException;
import com.lt.win.service.io.dto.Betslips;
import com.lt.win.service.io.enums.LangEnum;
import com.lt.win.utils.BeanConvertUtils;
import com.lt.win.utils.components.pagination.BasePage;
import com.lt.win.utils.components.pagination.ReqPage;
import com.lt.win.utils.components.pagination.ResPage;
import com.lt.win.utils.components.response.CodeInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * <p>
 * 各平台盈亏报表Service
 * </p>
 *
 * @author andy
 * @since 2020/12/10
 */
@Service
@Slf4j
public class PlatformProfitReportServiceImpl {
    private static final AtomicInteger REPORT_THREAD_POOL_ID = new AtomicInteger();
    private static final String ALL_TEXT = "--";
    private static final String ROLE_TEXT_0 = "会员";
    private static final String ROLE_TEXT_1 = "代理";
    private static final String PROFIT = "profit";
    @Resource
    private GameCache gameCache;
    @Resource
    private PlatListCache platListCache;
    @Resource
    private UserCache userCache;
    @Resource
    private DailyReportExStatisticsServiceImpl dailyReportExStatisticsServiceImpl;
    @Resource
    private ESSqlSearchBase esSqlSearchBase;

    private static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Map<Object, Boolean> seen = new ConcurrentHashMap<>();
        return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }

    private ThreadPoolExecutor getPool() {
        return new ThreadPoolExecutor(
                32,
                48,
                2L,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(),
                x -> new Thread(x, "各平台盈亏报表_POOL_" + REPORT_THREAD_POOL_ID.getAndIncrement()));
    }

    /**
     * 各平台盈亏报表->统计
     *
     * @param reqBody
     * @return
     */
    public PlatformProfitResBody getPlatformProfit(PlatformProfitListReqBody reqBody) {
        if (null == reqBody) {
            throw new BusinessException(CodeInfo.PARAMETERS_INVALID);
        }
        StringBuilder stringBuilder = new StringBuilder();
        String betslipsIndex = EsTableNameBase.getTableName(Betslips.class);
        stringBuilder.append("select sum(xbProfit) as totalProfit from  ")
                .append(betslipsIndex);
        //构建查询条件
        stringBuilder = platFromSearchCondition(stringBuilder, reqBody);
        List<PlatformProfitResBody> list = esSqlSearchBase.search(stringBuilder.toString(), PlatformProfitResBody::new);
        BigDecimal totalProfit = BigDecimal.ZERO;
        if (CollectionUtils.isNotEmpty(list)) {
            totalProfit = list.get(0).getTotalProfit();
        }
        PlatformProfitResBody platformProfitResBody = new PlatformProfitResBody();
        platformProfitResBody.setTotalProfit(totalProfit);
        return platformProfitResBody;
    }

    /**
     * 各平台盈亏报表->导出列表
     *
     * @param req
     * @return
     */
    public List<PlatformProfitListResBody> exportPlatformProfitList(PlatformProfitListReqBody req) {
        if (null == req) {
            throw new BusinessException(CodeInfo.PARAMETERS_INVALID);
        }
        StringBuilder stringBuilder = new StringBuilder();
        String betslipsIndex = EsTableNameBase.getTableName(Betslips.class);
        stringBuilder.append("select xbUid as uid, gameGroupId,gamePlatId,gameId, sum(xbProfit) as profit from  ")
                .append(betslipsIndex);
        //构建查询条件
        stringBuilder = platFromSearchCondition(stringBuilder, req);
        stringBuilder.append(" group by xbUid,gameGroupId,gamePlatId,gameId ");

        List<BetPlatformProfitBo> platformProfitBoList = esSqlSearchBase.search(stringBuilder.toString(), BetPlatformProfitBo::new);
        return BeanConvertUtils.copyListProperties(platformProfitBoList, PlatformProfitListResBody::new,
                (source, target) -> {
                    var userInfo = userCache.getUserInfo(source.getUid());
                    target.setUsername(userInfo.getUsername());
                    target.setLevelText(getLevelTextByLevelId(userInfo.getLevelId()));
                    target.setCreatedAt(userInfo.getCreatedAt());
                    target.setGroupName(getGroupName(source.getGameGroupId()));
                    target.setGameListName(getGameListName(source.getGameId()));
                    if (null != userInfo.getRole()) {
                        // 会员类型:0-会员 1-代理  4-测试 '
                        target.setTypeName(1 == userInfo.getRole() ? ROLE_TEXT_1 : ROLE_TEXT_0);
                    }
                });
    }

    /**
     * 各平台盈亏报表->列表
     *
     * @param reqBody
     * @return
     */
    public ResPage<PlatformProfitListResBody> getPlatformProfitList(ReqPage<PlatformProfitListReqBody> reqBody) {
        if (null == reqBody) {
            throw new BusinessException(CodeInfo.PARAMETERS_INVALID);
        }
        PlatformProfitListReqBody req = reqBody.getData();
        StringBuilder stringBuilder = new StringBuilder();
        String betslipsIndex = EsTableNameBase.getTableName(Betslips.class);
        stringBuilder.append("select xbUid as uid, gameGroupId,gamePlatId,gameId, sum(xbProfit) as profit from ")
                .append(betslipsIndex);
        //构建查询条件
        stringBuilder = platFromSearchCondition(stringBuilder, req);
        stringBuilder.append(" group by xbUid,gameGroupId,gamePlatId,gameId ");
        String[] sortField = reqBody.getSortField();
        if (sortField.length > 0 && sortField[0].equals("profit")) {
            reqBody.setSortField(new String[]{" sum(xbProfit) "});
        }
        ResPage<BetPlatformProfitBo> page = esSqlSearchBase.searchPage(stringBuilder.toString(), BetPlatformProfitBo::new, reqBody);
        ResPage<PlatformProfitListResBody> resPage = BeanConvertUtils.copyProperties(page, ResPage::new);
        List<PlatformProfitListResBody> platformProfitListResBodyList = BeanConvertUtils.copyListProperties(page.getList(), PlatformProfitListResBody::new,
                (source, target) -> {
                    var userInfo = userCache.getUserInfo(source.getUid());
                    target.setUsername(userInfo.getUsername());
                    target.setLevelText(getLevelTextByLevelId(userInfo.getLevelId()));
                    target.setCreatedAt(userInfo.getCreatedAt());
                    target.setGroupName(getGroupName(source.getGameGroupId()));
                    target.setGameListName(getGameListName(source.getGameId()));
                    if (null != userInfo.getRole()) {
                        // 会员类型:0-会员 1-代理  4-测试 '
                        target.setTypeName(1 == userInfo.getRole() ? ROLE_TEXT_1 : ROLE_TEXT_0);
                    }
                });
        resPage.setList(platformProfitListResBodyList);
        return resPage;
    }

    public StringBuilder platFromSearchCondition(StringBuilder stringBuilder, PlatformProfitListReqBody req) {
        stringBuilder.append(" where createdAt>=").append(req.getStartTime()).append(" and createdAt<=").append(req.getEndTime());
        if (null != req.getGroupId()) {
            stringBuilder.append(" and gameGroupId=").append(req.getGroupId());
        }
        if (null != req.getGameListId()) {
            stringBuilder.append(" and gameId=").append(req.getGameListId());
        }
        if (Strings.isNotEmpty(req.getGameId())) {
            stringBuilder.append(" and gameTypeId=").append(req.getGameId());
        }
        var testUidList = userCache.getTestUidList();
        if (CollectionUtils.isNotEmpty(testUidList)) {
            stringBuilder.append(" and xbUid not in(").append(StringUtils.join(testUidList, ",")).append(")");
        }
        if (null != req.getLevelId()) {
            List<Integer> levelUidList = levelUidList = userCache.getUidListByLevelId(req.getLevelId());
            stringBuilder.append(" and xbUid in (").append(StringUtils.join(levelUidList, ",")).append(")");
        }

        String username = req.getUsername();
        if (StringUtils.isNotBlank(username)) {
            Integer type = req.getType();
            // 类型:1-代理 2-会员
            if (type == 1) {
                var user = userCache.getUserInfo(username);
                if (null != user) {
                    stringBuilder.append(" and xbUid =").append(user.getId());
                }
            } else if (type == 2) {
                List<Integer> searchUidList = dailyReportExStatisticsServiceImpl.searchUserName2Uid(username, type);
                if (CollectionUtils.isNotEmpty(searchUidList)) {
                    stringBuilder.append(" and xbUid in (").append(StringUtils.join(searchUidList, ",")).append(")");
                }
            }
        }
        return stringBuilder;
    }

    private void setOrders(String[] sortField, Page page) {
        if (Optional.ofNullable(sortField).isEmpty() || sortField.length == 0 || !sortField[0].equals(PROFIT)) {
            page.setOrders(OrderItem.descs(PROFIT));
        }
    }

    private List<PlatformProfitListResBody> processDataList(List<PlatformProfitListResBody> tmpDataList, String groupName, String gameListName, String gameName, String levelText) {
        for (var entity : tmpDataList) {
            Integer uid = entity.getUid();
            // 会员等级 -> vip1-乒乓球达人
            var userInfo = userCache.getUserInfo(uid);
            if (null != userInfo) {
                entity.setLevelText(StringUtils.isNotBlank(levelText) ? levelText : getLevelTextByLevelId(userInfo.getLevelId()));
                entity.setCreatedAt(userInfo.getCreatedAt());
                if (null != userInfo.getRole()) {
                    // 会员类型:0-会员 1-代理 2-总代理 3-股东 4-测试 10-系统账号'
                    entity.setTypeName(1 == userInfo.getRole() ? ROLE_TEXT_1 : ROLE_TEXT_0);
                }
            }
            // 设置游戏类型名称(游戏组名称)
            entity.setGroupName(groupName);
            entity.setGameListName(gameListName);
        }
        return tmpDataList;
    }

    /**
     * 各平台盈亏报表->列表:排序
     *
     * @param sortKey   sortKey
     * @param sortField sortField
     * @param list      list
     */
    private void getPlatBetTotalListOrderByField(String sortKey, String[]
            sortField, List<PlatformProfitListResBody> list) {
        if (StringUtils.isNotBlank(sortKey) && Optional.ofNullable(sortField).isPresent() && sortField.length > 0) {
            if ("createdAt".equals(sortField[0]) && BasePage.DESC_SORT.equalsIgnoreCase(sortKey)) {
                list.sort((x, y) -> y.getCreatedAt() - x.getCreatedAt());
            } else if ("createdAt".equals(sortField[0]) && BasePage.ASC_SORT.equalsIgnoreCase(sortKey)) {
                list.sort(Comparator.comparingInt(PlatformProfitListResBody::getCreatedAt));
            } else if (PROFIT.equals(sortField[0]) && BasePage.DESC_SORT.equalsIgnoreCase(sortKey)) {
                list.sort((x, y) -> y.getProfit().compareTo(x.getProfit()));
            } else if (PROFIT.equals(sortField[0]) && BasePage.ASC_SORT.equalsIgnoreCase(sortKey)) {
                list.sort(Comparator.comparing(PlatformProfitListResBody::getProfit));
            } else if ("typeName".equals(sortField[0]) && BasePage.DESC_SORT.equalsIgnoreCase(sortKey)) {
                list.sort((x, y) -> y.getTypeName().compareTo(x.getTypeName()));
            } else if ("typeName".equals(sortField[0]) && BasePage.ASC_SORT.equalsIgnoreCase(sortKey)) {
                list.sort(Comparator.comparing(PlatformProfitListResBody::getTypeName));
            } else if ("levelText".equals(sortField[0]) && BasePage.DESC_SORT.equalsIgnoreCase(sortKey)) {
                list.sort((x, y) -> y.getLevelText().compareTo(x.getLevelText()));
            } else if ("levelText".equals(sortField[0]) && BasePage.ASC_SORT.equalsIgnoreCase(sortKey)) {
                list.sort(Comparator.comparing(PlatformProfitListResBody::getLevelText));
            }
        }
    }

    /**
     * 获取游戏名称-老虎机
     *
     * @param gameSlotList 老虎机列表
     * @param lang         语言
     * @param gameId       gameId
     * @return GameName
     */
    private String getGameId2GameNameByGroupId(List<GameSlot> gameSlotList, String lang, String gameId) {
        var gameNameMap = lang.equals(LangEnum.ZH.getValue()) ? gameSlotList.stream().filter(Objects::nonNull).collect(Collectors.toMap(GameSlot::getId, GameSlot::getNameZh)) : gameSlotList.stream().filter(Objects::nonNull).collect(Collectors.toMap(GameSlot::getId, GameSlot::getName));
        return null != gameNameMap ? gameNameMap.get(gameId) : null;
    }

    /**
     * 获取会员等级by等级ID
     *
     * @param levelId 等级ID
     * @return 会员等级:vip1-乒乓球达人
     */
    private String getLevelTextByLevelId(Integer levelId) {
        String levelText = null;
        UserLevel userLevel = userCache.getUserLevelById(levelId);
        if (null != userLevel) {
            levelText = userLevel.getCode() + " - " + userLevel.getName();
        }
        return levelText;
    }

    private String getGroupName(Integer groupId) {
        return gameCache.getGameGroupCache().stream().filter(o -> o.getId().equals(groupId)).findAny().get().getNameAbbr();
    }

    private String getGameListName(Integer gameListId) {
        return platListCache.gameList(gameListId).getName();
    }

    private String getGameName(Integer groupId, Integer gameListId, String gameId, String lang) {
        String gameName = "--";
        // 游戏列表(老虎机)
        if (2 == groupId) {
            var gameSlotListCache = gameCache.getGameSlotListCache();
            if (Optional.ofNullable(gameSlotListCache).isPresent() && !gameSlotListCache.isEmpty()) {
                gameName = getGameId2GameNameByGroupId(gameSlotListCache, lang, gameId);
            }
        }
        return gameName;
    }
}
