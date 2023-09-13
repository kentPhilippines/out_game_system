package com.lt.win.apiend.io.dto.mapper;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>
 *
 * </p>
 *
 * @author andy
 * @since 2020/7/25
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RewardsInviteReq {
    /**
     * 好友username
     */
    private String username;
    /**
     * 上级UID
     */
    private Integer uid;

    /**
     * 类型:0-被邀请奖金1-邀请奖金 2-充值返利
     */
    private Integer category;

    private Integer startTime;
    private Integer endTime;
}
