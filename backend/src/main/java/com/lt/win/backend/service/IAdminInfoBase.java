package com.lt.win.backend.service;

import com.lt.win.service.io.dto.BaseParams;
import com.lt.win.service.thread.ThreadHeaderLocalData;

/**
 * @description:
 * @author: andy
 * @date: 2020/8/6
 */
public interface IAdminInfoBase {
    /**
     * 获取Header信息
     *
     * @return 实体
     */
    static BaseParams.HeaderInfo getHeadLocalData() {
        return ThreadHeaderLocalData.HEADER_INFO_THREAD_LOCAL.get();
    }
}
