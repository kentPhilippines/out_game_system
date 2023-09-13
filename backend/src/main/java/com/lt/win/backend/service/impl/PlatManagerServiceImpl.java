package com.lt.win.backend.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lt.win.backend.io.bo.PlatManager;
import com.lt.win.backend.io.dto.Platform;
import com.lt.win.backend.mapper.PlatManagerMapper;
import com.lt.win.backend.redis.GameCache;
import com.lt.win.backend.service.IPlatManagerService;
import com.lt.win.dao.generator.po.GameList;
import com.lt.win.dao.generator.po.GameSlot;
import com.lt.win.dao.generator.po.PlatList;
import com.lt.win.dao.generator.service.GameListService;
import com.lt.win.dao.generator.service.GameSlotService;
import com.lt.win.dao.generator.service.PlatListService;
import com.lt.win.service.base.NoticeBase;
import com.lt.win.service.cache.redis.PlatListCache;
import com.lt.win.service.exception.BusinessException;
import com.lt.win.service.io.dto.BaseParams;
import com.lt.win.service.io.enums.LangEnum;
import com.lt.win.service.thread.ThreadHeaderLocalData;
import com.lt.win.utils.BeanConvertUtils;
import com.lt.win.utils.DateNewUtils;
import com.lt.win.utils.DateUtils;
import com.lt.win.utils.components.pagination.ReqPage;
import com.lt.win.utils.components.pagination.ResPage;
import com.lt.win.utils.components.response.CodeInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.alibaba.fastjson.JSON.parseObject;
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
public class PlatManagerServiceImpl extends ServiceImpl<PlatManagerMapper, PlatManager.ListPlatReqBody> implements IPlatManagerService {
    @Resource
    private PlatListService platListServiceImpl;
    @Resource
    private GameListService gameListServiceImpl;
    @Resource
    private GameSlotService gameSlotServiceImpl;
    @Resource
    private GameCache gameCache;
    @Resource
    private NoticeBase noticeBase;
    @Resource
    private PlatListCache platListCache;

    @Override
    public ResPage<PlatManager.ListPlatResBody> listPlat(ReqPage<PlatManager.ListPlatReqBody> reqBody) {
        ResPage<PlatManager.ListPlatResBody> resPage = ResPage.get(new Page<>());
        if (nonNull(reqBody)) {
            PlatManager.ListPlatReqBody data = reqBody.getData();
            var wrapper = new QueryWrapper<GameList>();
            if (data != null) {
                if (nonNull(data.getCode())) {
                    List<PlatList> list = platListServiceImpl.lambdaQuery().likeRight(PlatList::getName, data.getCode()).list();
                    if (CollectionUtil.isEmpty(list)) {
                        return resPage;
                    }
                    wrapper.in("plat_list_id", list.stream().map(PlatList::getId).collect(Collectors.toList()));
                }
                wrapper.eq(nonNull(data.getName()), "name", data.getName());
                wrapper.eq(nonNull(data.getStatus()), "status", data.getStatus());
            }
            var page = gameListServiceImpl.page(reqBody.getPage(), wrapper);
            DecimalFormat df = new DecimalFormat("0.00");
            var listPlatResBodyPage = BeanConvertUtils.copyPageProperties(page, PlatManager.ListPlatResBody::new, (sb, bo) -> {
                if (StringUtils.isNotBlank(sb.getMaintenance())) {
                    bo.setMaintenance(!parseObject(sb.getMaintenance()).isEmpty() ? parseObject(sb.getMaintenance()) : new JSONObject());
                }
                bo.setRevenueRate(df.format(sb.getRevenueRate().multiply(BigDecimal.valueOf(100))) + "%");
                bo.setPlatName(platListCache.platList(sb.getPlatListId()).getName());
            });
            resPage = ResPage.get(listPlatResBodyPage);
        }
        return resPage;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updatePlat(PlatManager.UpdatePlatReqBody reqBody) {
        if (nonNull(reqBody) && nonNull(reqBody.getId())) {
            var maintenance = nonNull(reqBody.getMaintenance()) && reqBody.getStatus() != 0 ? reqBody.getMaintenance() : "{}";
            boolean update = gameListServiceImpl.lambdaUpdate().set(nonNull(reqBody.getSort()), GameList::getSort, reqBody.getSort())
                    .set(nonNull(reqBody.getStatus()), GameList::getStatus, reqBody.getStatus())
                    .set(GameList::getMaintenance, maintenance)
                    .set(GameList::getUpdatedAt, DateNewUtils.now())
                    .eq(GameList::getId, reqBody.getId())
                    .update();

            if (update) {
                noticeBase.writeMaintainInfo(reqBody.getId());
                gameCache.delGameListCache();
                gameCache.updateGroupGameListCache();
                gameCache.delGameListCache();
                gameCache.delGamePropCache(reqBody.getId());
            } else {
                throw new BusinessException(CodeInfo.UPDATE_ERROR);
            }

        }
    }

    /**
     * 平台子游戏管理-列表
     *
     * @param dto 入参
     * @return 子游戏列表
     */
    @Override
    public ResPage<PlatManager.ListSubGameResBody> listSubGame(ReqPage<PlatManager.ListSubGameReqBody> dto) {
        if (null == dto) {
            throw new BusinessException(CodeInfo.PARAMETERS_INVALID);
        }

        BaseParams.HeaderInfo headerInfo = ThreadHeaderLocalData.HEADER_INFO_THREAD_LOCAL.get();

        LambdaQueryChainWrapper<GameSlot> query = gameSlotServiceImpl.lambdaQuery();
        // 状态过滤
        if (dto.getData().getStatus() != null) {
            query.eq(GameSlot::getStatus, dto.getData().getStatus());
        }
        // 显示过滤
        if (dto.getData().getDevice() != null && dto.getData().getDevice() != 0) {
            query.eq(GameSlot::getDevice, dto.getData().getDevice());
        }
        // 游戏类型过滤
        if (dto.getData().getGameId() != null && dto.getData().getGameId() != 0) {
            query.eq(GameSlot::getGameId, dto.getData().getGameId());
        }
        // 游戏名称过滤
        if (StringUtils.isNotBlank(dto.getData().getName())) {
            if (headerInfo.getLang().equals(LangEnum.ZH.getValue())) {
                query.likeRight(GameSlot::getNameZh, dto.getData().getName());
            } else {
                query.likeRight(GameSlot::getName, dto.getData().getName());
            }
        }


        // 获取老虎机游戏ID->名字
        List<Platform.GameListInfo> gameListResCache = gameCache.getGameListResCache();
        List<Platform.PlatListResInfo> gamePlatCache = gameCache.getPlatListResCache();
        Map<Integer, String> collect = gameListResCache.stream()
                .collect(Collectors.toMap(Platform.GameListInfo::getId, Platform.GameListInfo::getName));
        Map<Integer, String> platMap = gamePlatCache.stream()
                .collect(Collectors.toMap(Platform.PlatListResInfo::getId, Platform.PlatListResInfo::getName));

        // 分页数据结果集
        Page<GameSlot> page = query.page(dto.getPage());
        Page<PlatManager.ListSubGameResBody> resData = BeanConvertUtils.copyPageProperties(
                page,
                PlatManager.ListSubGameResBody::new,
                (ori, dest) -> {
                    dest.setName(headerInfo.getLang().equals(LangEnum.ZH.getValue()) && StringUtils.isNotBlank(ori.getNameZh()) ? ori.getNameZh() : ori.getName());
                    dest.setGamePlat(collect.getOrDefault(ori.getGameId(), ""));
                    dest.setPlatName(platMap.getOrDefault(ori.getPlatId(), ""));
                }
        );

        return ResPage.get(resData);
    }

    @Override
    public void updateSubGame(PlatManager.UpdateSubGameReqBody reqBody) {
        gameSlotServiceImpl.lambdaUpdate()
                .set(Objects.nonNull(reqBody.getStatus()), GameSlot::getStatus, reqBody.getStatus())
                .set(Objects.nonNull(reqBody.getIsNew()), GameSlot::getIsNew, reqBody.getIsNew())
                .set(StringUtils.isNotBlank(reqBody.getImg()), GameSlot::getImg, reqBody.getImg())
                .set(Objects.nonNull(reqBody.getIsCasino()), GameSlot::getIsCasino, reqBody.getIsCasino())
                .set(Objects.nonNull(reqBody.getFavoriteStar()), GameSlot::getFavoriteStar, reqBody.getFavoriteStar())
                .set(Objects.nonNull(reqBody.getHotStar()), GameSlot::getHotStar, reqBody.getHotStar())
                .set(Objects.nonNull(reqBody.getDevice()), GameSlot::getDevice, reqBody.getDevice())
                .set(Objects.nonNull(reqBody.getSort()), GameSlot::getSort, reqBody.getSort())
                .set(GameSlot::getUpdatedAt, DateUtils.getCurrentTime())
                .eq(GameSlot::getId, reqBody.getId())
                .eq(GameSlot::getPlatId, reqBody.getPlatId())
                .update();
    }

    @Override
    public List<PlatManager.GameListResBody> gameList() {
        LambdaQueryWrapper<GameList> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(GameList::getStatus, 1);
        wrapper.orderByAsc(GameList::getId);
        return BeanConvertUtils.copyListProperties(gameListServiceImpl.list(wrapper), PlatManager.GameListResBody::new);
    }

    @Override
    public List<Platform.GameListInfo> gameList(List<Integer> groups) {
        List<Platform.GameListInfo> list = gameCache.getGameListResCache();
        if (CollectionUtil.isEmpty(list) || CollectionUtil.isEmpty(groups)) {
            return list;
        }
        return list.stream().filter(s -> groups.contains(s.getGroupId())).collect(Collectors.toList());
    }
}
