package com.lt.win.backend.base;

import org.springframework.stereotype.Component;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @description: 自定义线程池
 * @author: andy
 * @date: 2020/8/6
 */
@Component
public class ThreadPoolExecutorFactory {
    /**
     * 操作日志AOP线程池
     */
    private static final AtomicInteger LOG_AOP_THREAD_POOL_ID = new AtomicInteger();
    public static final ThreadPoolExecutor LOG_AOP_THREAD_POOL = new ThreadPoolExecutor(
            16,
            32,
            10L,
            TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(),
            x -> new Thread(x, "LOG_AOP_THREAD_POOL_" + LOG_AOP_THREAD_POOL_ID.getAndIncrement()));
}
