package com.zkyy.icecream.utils;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * 异步线程池，所有异步执行都需调用此类
 * Created by Adam_Lee on 2019/1/17.
 */
public class ThreadPool {

    private ThreadPool() {}

    private static final ScheduledExecutorService mThreadPool;

    static {
        mThreadPool = Executors.newScheduledThreadPool(Runtime.getRuntime()
                .availableProcessors() * 2);
    }

    /**
     * 异步执行任务调用此方法
     * @param command
     */
    public static void run(Runnable command) {
        mThreadPool.execute(command);
    }

}
