package com.example.colmapsrxjavatask4.presenters;

import android.util.Log;

import com.example.colmapsrxjavatask4.Singletone;
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

import io.reactivex.rxjava3.subjects.PublishSubject;
import io.reactivex.rxjava3.subjects.ReplaySubject;

public class MyCallableTask  implements Callable<Integer> {

    private final int index;
    FillingCollections fillingCollections = new FillingCollections();
    OperationsWithArrayList operationsWithArrayList = new OperationsWithArrayList();
    OperationsWithLinkedList operationsWithLinkedList = new OperationsWithLinkedList();
    OperationsWithCopyOnWriteArrayList operationsWithCopyOnWriteArrayList =
            new OperationsWithCopyOnWriteArrayList();
    Method[] fillCol = FillingCollections.class.getDeclaredMethods();
    Method[] operations0 = OperationsWithArrayList.class.getDeclaredMethods();
    Method[] operations1 = OperationsWithLinkedList.class.getDeclaredMethods();
    Method[] operations2 = OperationsWithCopyOnWriteArrayList.class.getDeclaredMethods();
    private int it;
    private int collections;
    private long startTime;
    private final PublishSubject<String[]> subjectTime;
    private final PublishSubject<Boolean[]> subjectStatus;
    private Boolean pbStatus[];
    private String timeResult[];

    public MyCallableTask(int index, PublishSubject<String[]> subjectTime, PublishSubject<Boolean[]> subjectStatus, Boolean[] pbStatus,
                          String[] timeResult) {
        this.index = index;
        this.subjectTime=subjectTime;
        this.subjectStatus=subjectStatus;
        this.pbStatus=pbStatus;
        this.timeResult=timeResult;
    }

    public Integer call() {
        try {
             synchronized (subjectStatus){
              pbStatus[index] = true;
              subjectStatus.onNext(pbStatus);
            }

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

            synchronized (subjectStatus){
                pbStatus[index] = false;
                subjectStatus.onNext(pbStatus);
            }
           synchronized (subjectTime){
               timeResult[index]=String.valueOf(operationTime);
               subjectTime.onNext(timeResult);
           }
            if (index==23){
                subjectTime.onComplete();
            }

        } catch (IllegalAccessException | InvocationTargetException | InterruptedException e) {
            e.printStackTrace();
            Log.v("MyApp", "Catch");
        }
        return index;
    }
}
