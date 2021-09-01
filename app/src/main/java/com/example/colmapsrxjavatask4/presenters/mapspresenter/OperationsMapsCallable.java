package com.example.colmapsrxjavatask4.presenters.mapspresenter;

import android.util.Log;

import com.example.colmapsrxjavatask4.model.mapsmodel.FillingMaps;
import com.example.colmapsrxjavatask4.presenters.ResultClass;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

import io.reactivex.rxjava3.subjects.Subject;

public class OperationsMapsCallable implements Callable<Integer> {

    private final Consumer consumer;
    private final Subject<ResultClass.TimeResult> subjectTime;
    private final Subject<ResultClass.PbStatus> subjectStatus;
    private final ArrayList<Consumer<?>> operations;
    private final FillingMaps fillingMaps;

    public OperationsMapsCallable(FillingMaps fillingMaps, ArrayList<Consumer<?>> operations,
                                  Consumer consumer, Subject<ResultClass.TimeResult> subjectTime,
                                  Subject<ResultClass.PbStatus> subjectStatus) {
        this.operations = operations;
        this.consumer = consumer;
        this.subjectTime = subjectTime;
        this.subjectStatus = subjectStatus;
        this.fillingMaps = fillingMaps;
    }

    @Override
    public Integer call() throws Exception {

        int collections = (operations.indexOf(consumer)) / 3;
        int iteration = (operations.indexOf(consumer)) % 3 + 1;
        int index = (iteration + 1) + (collections * 4) - 1;
        subjectStatus.onNext(new ResultClass.PbStatus(index, true));
        long startTime = System.currentTimeMillis();

        switch (collections) {
            case 0:
                HashMap<Integer, Integer> copyHashMap =
                        new HashMap<Integer, Integer>(fillingMaps.getHashMap());
                consumer.accept(copyHashMap);
                break;
            case 1:
                TreeMap<Integer, Integer> copyTreeMap =
                        new TreeMap<Integer, Integer>(fillingMaps.getTreeMap());
                consumer.accept(copyTreeMap);
                break;
        }
        long operationTime = System.currentTimeMillis() - startTime;
        TimeUnit.SECONDS.sleep(1);
        subjectStatus.onNext(new ResultClass.PbStatus(index, false));
        subjectTime.onNext(new ResultClass.TimeResult(index, operationTime));
        return index;
    }
}
