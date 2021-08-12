package com.example.colmapsrxjavatask4.presenters.collectionspresenter;

import android.util.Log;

import com.example.colmapsrxjavatask4.model.collectionsmodel.FillingCollections;
import com.example.colmapsrxjavatask4.presenters.ResultClass;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import io.reactivex.rxjava3.subjects.Subject;

public class FillingCollectionsCallable implements Callable<Integer> {

    private final Function function;
    private final Subject<ResultClass.TimeResult> subjectTime;
    private final Subject<ResultClass.PbStatus> subjectStatus;
    private final FillingCollections fillingCollections;

    public FillingCollectionsCallable(FillingCollections fillingCollections, Function function, Subject<ResultClass.TimeResult> subjectTime, Subject<ResultClass.PbStatus> subjectStatus) {
        this.function = function;
        this.subjectTime = subjectTime;
        this.subjectStatus = subjectStatus;
        this.fillingCollections = fillingCollections;
    }

    @Override
    public Integer call() throws Exception {

        int collections = fillingCollections.getOperationsFillingList().indexOf(function);
        int index = collections * 8;
        subjectStatus.onNext(new ResultClass.PbStatus(index, true));
        long startTime = System.currentTimeMillis();

        function.apply(fillingCollections.getCollectionsList().get(collections));

        Log.v("MyApp", "Size " + fillingCollections.getCollectionsList().get(collections).size());

        long operationTime = System.currentTimeMillis() - startTime;

        Log.v("MyApp", " index = " + index + "  collection = " + collections + "  it = " + "  time = " + operationTime);

        TimeUnit.SECONDS.sleep(1);
        subjectStatus.onNext(new ResultClass.PbStatus(index, false));
        subjectTime.onNext(new ResultClass.TimeResult(index, operationTime));
        return index;
    }
}
