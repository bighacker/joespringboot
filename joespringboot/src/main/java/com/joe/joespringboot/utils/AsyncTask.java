package com.joe.joespringboot.utils;

import javax.annotation.PreDestroy;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

public class AsyncTask {
    private static ExecutorService threadpool = Executors.newFixedThreadPool(4);//newCachedThreadPool();

    @PreDestroy
    public static void destroyExecutor(){
        threadpool.shutdown();
    }

    public static void addThread(FutureTask<String> futureTask){
        threadpool.execute(futureTask);
    }
}