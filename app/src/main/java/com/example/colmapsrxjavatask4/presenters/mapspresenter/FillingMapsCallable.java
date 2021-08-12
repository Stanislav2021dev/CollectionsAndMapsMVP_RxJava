package com.example.colmapsrxjavatask4.presenters.mapspresenter;

import android.util.Log;

import com.example.colmapsrxjavatask4.model.mapsmodel.FillingMaps;
import com.example.colmapsrxjavatask4.presenters.ResultClass;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import io.reactivex.rxjava3.subjects.Subject;

public class FillingMapsCallable implements Callable<Integer> {

    private final Function function;
    private final Subject<ResultClass.TimeResult> subjectTime;
    private final Subject<ResultClass.PbStatus> subjectStatus;
    private final FillingMaps fillingMaps;

    public FillingMapsCallable(FillingMaps fillingMaps, Function function, Subject<ResultClass.TimeResult> subjectTime, Subject<ResultClass.PbStatus> subjectStatus) {
        this.function = function;
        this.subjectTime = subjectTime;
        this.subjectStatus = subjectStatus;
        this.fillingMaps = fillingMaps;
    }

    @Override
    public Integer call() throws Exception {

        int maps = fillingMaps.getOperationsFillingList().indexOf(function);
        int index = maps * 4;
        subjectStatus.onNext(new ResultClass.PbStatus(index, true));
        long startTime = System.currentTimeMillis();

        function.apply(fillingMaps.getMapsList().get(maps));

        Log.v("MyApp", "Size " + fillingMaps.getMapsList().get(maps).size());

        long operationTime = System.currentTimeMillis() - startTime;

        Log.v("MyApp", "Map " + " index = " + index + "  collection = " + maps + "  it = " + "  time = " + operationTime);

        TimeUnit.SECONDS.sleep(1);
        subjectStatus.onNext(new ResultClass.PbStatus(index, false));
        subjectTime.onNext(new ResultClass.TimeResult(index, operationTime));
        return index;
    }
}
