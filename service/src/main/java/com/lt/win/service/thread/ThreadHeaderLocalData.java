package com.lt.win.service.thread;

import com.lt.win.service.io.dto.BaseParams;

/**
 * @author: David
 * @date: 06/04/2020
 * @description:
 */
public interface ThreadHeaderLocalData {
    ThreadLocal<BaseParams.HeaderInfo> HEADER_INFO_THREAD_LOCAL = new ThreadLocal<>();
}

