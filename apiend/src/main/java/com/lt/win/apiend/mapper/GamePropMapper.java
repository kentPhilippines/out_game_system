package com.lt.win.apiend.mapper;

import com.lt.win.apiend.io.dto.platform.GameModelDto;

import java.util.List;

/**
 * @author: David
 * @date: 19/04/2020
 * @description:
 */
public interface GamePropMapper {
    GameModelDto getGameProp(Integer gameId);

    List<GameModelDto> getGamePropList(Integer gameId);
}
