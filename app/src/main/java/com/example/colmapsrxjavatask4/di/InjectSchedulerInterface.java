package com.example.colmapsrxjavatask4.di;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.schedulers.Schedulers;

public interface InjectSchedulerInterface {
    default ExecutorService createExecutor(){
        int numberOfThreads = Runtime.getRuntime().availableProcessors() - 1;
        return Executors.newFixedThreadPool(numberOfThreads);
    }
    default @NonNull Scheduler createScheduler(ExecutorService executor){
        @NonNull Scheduler scheduler = Schedulers.from(executor);
        return scheduler;
    }
}
