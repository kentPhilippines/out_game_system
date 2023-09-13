package com.lt.win.service.io.bo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 用户缓存实体
 * </p>
 *
 * @author andy
 * @since 2020/7/24
 */
public interface GameCommonBo {
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    class GameConfig {
        private String model;
        private String betslips;
        private String gameIdField;
        private String playerIdField;
        private String parent;
    }

}
