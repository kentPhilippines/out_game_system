package com.lt.win.dao.generator.service.impl;

import com.lt.win.dao.generator.po.GameList;
import com.lt.win.dao.generator.mapper.GameListMapper;
import com.lt.win.dao.generator.service.GameListService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 游戏类型表 服务实现类
 * </p>
 *
 * @author David
 * @since 2022-09-20
 */
@Service
@CacheConfig(cacheNames = {"GameList"})
public class GameListServiceImpl extends ServiceImpl<GameListMapper, GameList> implements GameListService {

    @Override
    @Cacheable(key = "#id", unless = "#result == null")
    public GameList queryGameListById(Integer id) {
        return getById(id);
    }
}
