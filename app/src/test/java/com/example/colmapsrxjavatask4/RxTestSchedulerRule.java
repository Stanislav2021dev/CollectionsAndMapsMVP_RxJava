package com.example.colmapsrxjavatask4;


import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import java.util.concurrent.Callable;

import io.reactivex.rxjava3.android.plugins.RxAndroidPlugins;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class RxTestSchedulerRule implements TestRule {

    private final Scheduler SCHEDULER_INSTANCE = Schedulers.trampoline();

   // private final Function<Scheduler, Scheduler> schedulerFunction = scheduler -> SCHEDULER_INSTANCE;

    private final Function<Callable<Scheduler>, Scheduler> schedulerFunctionLazy =
            schedulerCallable -> SCHEDULER_INSTANCE;

    @Override
    public Statement apply(final Statement base, Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                RxAndroidPlugins.reset();
                RxAndroidPlugins.setInitMainThreadSchedulerHandler(schedulerFunctionLazy);
                RxJavaPlugins.reset();
               // RxJavaPlugins.setIoSchedulerHandler(schedulerFunction);
               // RxJavaPlugins.setNewThreadSchedulerHandler(schedulerFunction);
               // RxJavaPlugins.setComputationSchedulerHandler(schedulerFunction);
                base.evaluate();
                RxAndroidPlugins.reset();
                RxJavaPlugins.reset();
            }
        };
    }
}
