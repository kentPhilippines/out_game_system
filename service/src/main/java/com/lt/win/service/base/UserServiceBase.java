package com.lt.win.service.base;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lt.win.dao.generator.po.AuthGroupAccess;
import com.lt.win.dao.generator.po.GameSlotFavorite;
import com.lt.win.service.cache.redis.ConfigCache;
import com.lt.win.service.cache.redis.UserCache;
import com.lt.win.service.common.Constant;
import com.lt.win.service.io.dto.BaseParams;
import com.lt.win.service.thread.ThreadHeaderLocalData;
import com.lt.win.dao.generator.service.*;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static com.alibaba.fastjson.JSON.parseObject;

/**
 * @author: David
 * @date: 15/05/2020
 * @description:
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserServiceBase {
    private final GameSlotFavoriteService gameSlotFavoriteServiceImpl;
    private final AuthGroupService authGroupServiceImpl;
    private final AuthGroupAccessService authGroupAccessServiceImpl;

    /**
     * 获取用户收藏的指定老虎机游戏的游戏列表
     *
     * @param uid UID
     * @return 游戏列表集
     */
    public List<String> slotFavoriteByUid(Integer uid) {
        if (uid == 0) {
            return CollectionUtil.newArrayList("");
        }
        List<String> collect = gameSlotFavoriteServiceImpl.lambdaQuery()
                .eq(GameSlotFavorite::getUid, uid)
                .list()
                .stream()
                .map(GameSlotFavorite::getGameSlotIdAndId)
                .collect(Collectors.toList());
        // 要是游戏列表是空列表,则默认添加空字符串
        if (collect.isEmpty()) {
            collect.add("");
        }
        return collect;
    }

    public List<GameSlotFavorite> gameFavoriteByUid(Integer uid) {
        return gameSlotFavoriteServiceImpl.lambdaQuery()
                .eq(GameSlotFavorite::getUid, uid)
                .list();
    }

    /**
     * 获取用户收藏的指定老虎机游戏的游戏列表
     *
     * @param uid    UID
     * @param gameId GameId
     * @return 游戏列表集
     */
    public List<String> slotFavoriteByUid(Integer uid, Integer gameId) {
        List<String> collect = gameSlotFavoriteServiceImpl.lambdaQuery()
                .select(GameSlotFavorite::getGameSlotId)
                .eq(GameSlotFavorite::getUid, uid)
                .eq(GameSlotFavorite::getGameId, gameId)
                .list()
                .stream()
                .map(GameSlotFavorite::getGameSlotId)
                .collect(Collectors.toList());

        // 要是游戏列表是空列表,则默认添加空字符串
        if (collect.isEmpty()) {
            collect.add("");
        }

        return collect;
    }

    /**
     * 验证手机号码是否隐藏
     *
     * @param fieldName fieldName
     * @return res
     */
    public UnaryOperator<String> checkShow(String fieldName) {
        UnaryOperator<String> function = x -> x;
        BaseParams.HeaderInfo currentLoginUser = ThreadHeaderLocalData.HEADER_INFO_THREAD_LOCAL.get();
        var authGroupAccess = authGroupAccessServiceImpl.getOne(new LambdaQueryWrapper<AuthGroupAccess>()
                        .eq(AuthGroupAccess::getUid, currentLoginUser.getId()),
                false);
        // 查看当前用户是否有手机号码的隐藏权限
        if (Objects.nonNull(authGroupAccess)) {
            var authGroup = authGroupServiceImpl.getById(authGroupAccess.getGroupId());
            var dataPermission = authGroup.getDataPermission();
            if (StringUtils.isNotEmpty(dataPermission)) {
                var fieldStatus = parseObject(dataPermission).getInteger(fieldName);
                // 0:全隐藏，1：半隐藏：2:全展示
                if (fieldStatus == 0) {
                    function = x -> "******";
                } else if (fieldStatus == 1) {
                    function = x -> isHalfShow(x, fieldName);
                }
            }
        }
        return function;
    }

    /**
     * @param value     原字符
     * @param fieldName 隐藏字段
     * @return 新字符
     */
    public String isHalfShow(String value, String fieldName) {
        if (StringUtils.isEmpty(value)) {
            return value;
        }
        var midStr = "****";
        if (fieldName.equals(Constant.MOBILE) && value.length() > 2) {
            value = value.substring(0, 3) + midStr;
        } else if (fieldName.equals(Constant.REAL_NAME) && value.length() > 1) {
            var pattern = "[\u4E00-\u9FA5]";
            if (Pattern.matches(pattern, value)) {
                value = value.charAt(0) + midStr;
            } else {
                value = value.substring(0, 2) + midStr;
            }
        } else if (fieldName.equals(Constant.BANK_ACCOUNT) && value.length() > 6) {
            value = value.substring(0, 3) + midStr + value.substring(value.length() - 3);
        }
        return value;
    }
}
