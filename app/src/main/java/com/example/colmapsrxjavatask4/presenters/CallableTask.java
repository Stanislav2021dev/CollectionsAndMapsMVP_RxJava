package com.example.colmapsrxjavatask4.presenters;

import android.util.Log;

import com.example.colmapsrxjavatask4.collectionsmodel.FillingCollections;
import com.example.colmapsrxjavatask4.collectionsmodel.OperationsWithArrayList;
import com.example.colmapsrxjavatask4.collectionsmodel.OperationsWithCopyOnWriteArrayList;
import com.example.colmapsrxjavatask4.collectionsmodel.OperationsWithLinkedList;
import com.example.colmapsrxjavatask4.di.InjectOperationsInterface;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.concurrent.Callable;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.hilt.EntryPoint;
import dagger.hilt.EntryPoints;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;
import dagger.hilt.internal.GeneratedComponent;
import hilt_aggregated_deps._dagger_hilt_android_internal_modules_ApplicationContextModule;
import io.reactivex.rxjava3.subjects.Subject;

public class CallableTask implements Callable<Integer>, InjectOperationsInterface, GeneratedComponent {

    public   Integer index;
    private final Subject<TimeResult> subjectTime;
    private final Subject<PbStatus> subjectStatus;
    private final Method[] fillCol = FillingCollections.class.getDeclaredMethods();
    private final Method[] operations0 = OperationsWithArrayList.class.getDeclaredMethods();
    private final Method[] operations1 = OperationsWithLinkedList.class.getDeclaredMethods();
    private final Method[] operations2 = OperationsWithCopyOnWriteArrayList.class.getDeclaredMethods();
    private long startTime;


    public CallableTask(Integer index, Subject<TimeResult> subjectTime, Subject<PbStatus> subjectStatus) {
        this.index = index;
        this.subjectTime = subjectTime;
        this.subjectStatus = subjectStatus;
    }

    InjectOperationsInterface mInterface= EntryPoints.get(this,InjectOperationsInterface.class);
    FillingCollections fillingCollections=mInterface.getFillingCollection();
    OperationsWithArrayList operationsWithArrayList=mInterface.getOperationsWithArrayList();
    OperationsWithLinkedList operationsWithLinkedList=mInterface.getOperationsWithLinkedList();
    OperationsWithCopyOnWriteArrayList operationsWithCopyOnWriteArrayList =mInterface.getOperationsWithCopyOnWriteArrayList();

    public Integer call() {
        try {
            subjectStatus.onNext(new PbStatus(index, true));
            int collections = index / 8;
            int iteration = index % 8;
            if (iteration == 0) {
                startTime = System.currentTimeMillis();
                fillCol[collections].invoke(fillingCollections);
            } else {
                switch (collections) {
                    case (0):

                        ArrayList<Integer> copyCol0 =
                                new ArrayList<>(FillingCollections.colArrayList);
                        startTime = System.currentTimeMillis();
                        operations0[iteration - 1].invoke(operationsWithArrayList, copyCol0);
                        break;
                    case (1):
                        LinkedList<Integer> copyCol1 =
                                new LinkedList<>(FillingCollections.colLinkedList);
                        startTime = System.currentTimeMillis();
                        operations1[iteration - 1].invoke(operationsWithLinkedList, copyCol1);
                        break;
                    case (2):
                        CopyOnWriteArrayList<Integer> copyCol2 =
                                new CopyOnWriteArrayList<>(FillingCollections.colCopyOnWriteArrayList);
                        startTime = System.currentTimeMillis();
                        operations2[iteration - 1].invoke(operationsWithCopyOnWriteArrayList, copyCol2);
                        break;
                }
            }
            long operationTime = System.currentTimeMillis() - startTime;
            Log.v("MyApp", " index = " + index + "  collection = " + collections + "  it = " + iteration + "  time = "+operationTime );
            TimeUnit.SECONDS.sleep(1);

            subjectStatus.onNext(new PbStatus(index, false));
            subjectTime.onNext(new TimeResult(index, operationTime));

        } catch (IllegalAccessException | InvocationTargetException | InterruptedException e) {
            e.printStackTrace();
            Log.v("MyApp", "Catch");
        }
        return index;
    }

    public static class PbStatus {
        int index;
        Boolean status;
        public PbStatus(int index, Boolean status) {
            this.index = index;
            this.status = status;
        }
    }

    public static class TimeResult {
        int index;
        long operationTime;
        public TimeResult(int index, long operationTime) {
            this.index = index;
            this.operationTime = operationTime;
        }
    }
}






