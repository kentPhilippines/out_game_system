package com.lt.win.apiend.base;

import org.springframework.stereotype.Component;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @description: 自定义线程池
 * @author: andy
 * @date: 2020/8/6
 */
@Component
public class ThreadPoolExecutorFactory {
    public static final ThreadPoolExecutor THREAD_POOL = new ThreadPoolExecutor(
            32,
            64,
            1L,
            TimeUnit.SECONDS,
            new LinkedBlockingQueue<>());
}
