package com.example.recepti;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class AppExecutors {
    private static AppExecutors instance;

    public static AppExecutors getInstance(){
        if(instance == null){
            instance = new AppExecutors();
        }
        return instance;
    }

    private final ScheduledExecutorService networkIO = Executors.newScheduledThreadPool(3);

    public ScheduledExecutorService networkIO(){
        return networkIO;
    }

//    private Executor backgroundExecutor = Executors.newFixedThreadPool(3);
//
//    public AppExecutors(Executor backgroundExecutor) {
//        this.backgroundExecutor = backgroundExecutor;
//    }
}
