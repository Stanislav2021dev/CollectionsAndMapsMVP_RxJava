package com.example.colmapsrxjavatask4.presenters;

import android.util.Log;

import com.example.colmapsrxjavatask4.collectionsmodel.FillingCollections;
import com.example.colmapsrxjavatask4.collectionsmodel.OperationsWithArrayList;
import com.example.colmapsrxjavatask4.collectionsmodel.OperationsWithCopyOnWriteArrayList;
import com.example.colmapsrxjavatask4.collectionsmodel.OperationsWithLinkedList;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.concurrent.Callable;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.subjects.Subject;

public class MyCallableTask implements Callable<Integer> {

    private final int index;
    private final Subject<TimeResult> subjectTime;
    private final Subject<PbStatus> subjectStatus;
    private final FillingCollections fillingCollections = new FillingCollections();
    private final OperationsWithArrayList operationsWithArrayList = new OperationsWithArrayList();
    private final OperationsWithLinkedList operationsWithLinkedList = new OperationsWithLinkedList();
    private final OperationsWithCopyOnWriteArrayList operationsWithCopyOnWriteArrayList =
            new OperationsWithCopyOnWriteArrayList();
    private final Method[] fillCol = FillingCollections.class.getDeclaredMethods();
    private final Method[] operations0 = OperationsWithArrayList.class.getDeclaredMethods();
    private final Method[] operations1 = OperationsWithLinkedList.class.getDeclaredMethods();
    private final Method[] operations2 = OperationsWithCopyOnWriteArrayList.class.getDeclaredMethods();
    private int it;
    private int collections;
    private long startTime;


    public MyCallableTask(int index, Subject<TimeResult> subjectTime, Subject<PbStatus> subjectStatus) {
        this.index = index;
        this.subjectTime = subjectTime;
        this.subjectStatus = subjectStatus;
    }

    public Integer call() {
        try {
            subjectStatus.onNext(new PbStatus(index, true));
            collections = index / 8;
            it = index % 8;
            if (it == 0) {
                startTime = System.currentTimeMillis();
                fillCol[collections].invoke(fillingCollections);
            } else {
                switch (collections) {
                    case (0):
                        ArrayList<Integer> copyCol0 =
                                new ArrayList<>(FillingCollections.colArrayList);
                        startTime = System.currentTimeMillis();
                        operations0[it - 1].invoke(operationsWithArrayList, copyCol0);
                        break;
                    case (1):
                        LinkedList<Integer> copyCol1 =
                                new LinkedList<>(FillingCollections.colLinkedList);
                        startTime = System.currentTimeMillis();
                        operations1[it - 1].invoke(operationsWithLinkedList, copyCol1);
                        break;
                    case (2):
                        CopyOnWriteArrayList<Integer> copyCol2 =
                                new CopyOnWriteArrayList<>(FillingCollections.colCopyOnWriteArrayList);
                        startTime = System.currentTimeMillis();
                        operations2[it - 1].invoke(operationsWithCopyOnWriteArrayList, copyCol2);
                        break;
                }
            }
            long operationTime = System.currentTimeMillis() - startTime;
            Log.v("MyApp", " index = " + index + "  collection = " + collections + "  it = " + it + "  time = "+operationTime );
            TimeUnit.SECONDS.sleep(1);

            subjectStatus.onNext(new PbStatus(index, false));
            subjectTime.onNext(new TimeResult(index, operationTime));

        } catch (IllegalAccessException | InvocationTargetException | InterruptedException e) {
            e.printStackTrace();
            Log.v("MyApp", "Catch");
        }
        return index;
    }

    static class PbStatus {
        int index;
        Boolean status;

        public PbStatus(int index, Boolean status) {
            this.index = index;
            this.status = status;
        }
    }

    static class TimeResult {
        int index;
        long operationTime;

        public TimeResult(int index, long operationTime) {
            this.index = index;
            this.operationTime = operationTime;
        }
    }
}






