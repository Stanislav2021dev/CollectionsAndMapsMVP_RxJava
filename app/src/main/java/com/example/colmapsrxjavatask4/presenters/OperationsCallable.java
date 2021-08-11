package com.example.colmapsrxjavatask4.presenters;

import android.util.Log;

import com.example.colmapsrxjavatask4.collectionsmodel.FillingCollections;
import com.example.colmapsrxjavatask4.di.InjectOperationsInterface;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.concurrent.Callable;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

import dagger.hilt.internal.GeneratedComponent;
import io.reactivex.rxjava3.subjects.Subject;

public class OperationsCallable implements Callable<Integer>, InjectOperationsInterface, GeneratedComponent {

    private final Consumer consumer;
    private final Subject<ResultClass.TimeResult> subjectTime;
    private final Subject<ResultClass.PbStatus> subjectStatus;
    private final ArrayList<Consumer<?>> operations;
    private final FillingCollections fillingCollections;

    public OperationsCallable(FillingCollections fillingCollections, ArrayList<Consumer<?>> operations,
                              Consumer consumer, Subject<ResultClass.TimeResult> subjectTime,
                              Subject<ResultClass.PbStatus> subjectStatus) {
        this.operations=operations;
        this.consumer=consumer;
        this.subjectTime = subjectTime;
        this.subjectStatus = subjectStatus;
        this.fillingCollections=fillingCollections;
    }

    @Override
    public Integer call() throws Exception {

            int collections=(operations.indexOf(consumer))/7;
            int iteration =(operations.indexOf(consumer))%7+1;
            int index = (iteration+ 1) + (collections * 8) - 1;
            subjectStatus.onNext(new ResultClass.PbStatus(index, true));
            long startTime = System.currentTimeMillis();

        switch(collections) {
            case 0:
                ArrayList<Integer> copyArrayList =
                        new ArrayList<>(fillingCollections.getArrayList());
                consumer.accept(copyArrayList);
                break;
            case 1:
                LinkedList<Integer> copyLinkedList =
                        new LinkedList<>(fillingCollections.getLinkedList());
                consumer.accept(copyLinkedList);
                break;
            case 2:
                CopyOnWriteArrayList<Integer> copyOnWriteArrayList =
                        new CopyOnWriteArrayList<>(fillingCollections.getCopyOnWriteArrayList());
                consumer.accept(copyOnWriteArrayList);
                break;
        }
            long operationTime = System.currentTimeMillis() - startTime;
            TimeUnit.SECONDS.sleep(1);
            subjectStatus.onNext(new ResultClass.PbStatus(index, false));
            subjectTime.onNext(new ResultClass.TimeResult(index, operationTime));

            Log.v("MyApp", " index = " + index + "  collection = " + collections+ "  it = " + iteration + "  time = " );

        return index;
    }
}
