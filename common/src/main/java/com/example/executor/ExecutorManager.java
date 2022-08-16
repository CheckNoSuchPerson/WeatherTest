package com.example.executor;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Create by Hastur
 * on 2022/8/7
 */
public class ExecutorManager {
    private static int cpuCount = Runtime.getRuntime().availableProcessors();
    public static ExecutorService mIoExecutor;
    public static Executor mMainExecutor;

    static {
        mIoExecutor = new ThreadPoolExecutor(cpuCount, 2 * cpuCount + 1, 10, TimeUnit.SECONDS, new SynchronousQueue<>()
                , Executors.defaultThreadFactory(), new ThreadPoolExecutor.CallerRunsPolicy());
        mMainExecutor = new Executor (){
            Handler handler = new Handler(Looper.getMainLooper());
            @Override
            public void execute(Runnable command) {
                handler.post(command);
            }
        };
    }
}
