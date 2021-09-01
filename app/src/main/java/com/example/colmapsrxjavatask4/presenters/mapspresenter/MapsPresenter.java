package com.example.colmapsrxjavatask4.presenters.mapspresenter;

import com.example.colmapsrxjavatask4.di.InjectCallableTaskInterface;
import com.example.colmapsrxjavatask4.di.InjectOperationsInterface;
import com.example.colmapsrxjavatask4.di.InjectSchedulerInterface;
import com.example.colmapsrxjavatask4.di.InjectSubjectInterface;
import com.example.colmapsrxjavatask4.model.mapsmodel.FillingMaps;
import com.example.colmapsrxjavatask4.model.mapsmodel.OperationsWithHashMap;
import com.example.colmapsrxjavatask4.model.mapsmodel.OperationsWithTreeMap;
import com.example.colmapsrxjavatask4.presenters.ResultClass;
import com.example.colmapsrxjavatask4.view.mapsView.MapView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.function.Consumer;
import java.util.function.Function;

import dagger.hilt.EntryPoints;
import dagger.hilt.internal.GeneratedComponent;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.subjects.Subject;
import moxy.MvpPresenter;

public class MapsPresenter extends MvpPresenter<MapView> implements InjectSubjectInterface,
        InjectSchedulerInterface, InjectCallableTaskInterface, InjectOperationsInterface, GeneratedComponent {

    private final CompositeDisposable disposables = new CompositeDisposable();
    private Scheduler scheduler;
    private ExecutorService executor;
    private Subject<ResultClass.TimeResult> subjectTime;
    private Subject<ResultClass.PbStatus> subjectPbStatus;
    private Boolean[] pbStatusResArray = new Boolean[8];
    private String[] timeResArray = new String[8];
    private Observable<Integer> mainObservable;

    public MapsPresenter(){
        initExecutor();
    }

    public void start(int numElementsMap) {
        disposables.clear();
        mainObservable=createMainObservable(numElementsMap);
        disposables.add(mainObservable.subscribe());
    }

    public Observer<ResultClass.TimeResult> timeResults() {
        return new Observer<ResultClass.TimeResult>() {
            @Override
            public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {
                timeResArray = new String[8];
                getViewState().showButStatus(false);
                getViewState().showTimeResult(timeResArray);
            }

            @Override
            public void onNext(ResultClass.@NonNull TimeResult timeResult) {
                timeResArray[timeResult.getIndex()] = String.valueOf(timeResult.getOperationTime());
                getViewState().showTimeResult(timeResArray);
                if (!Arrays.asList(timeResArray).contains(null)) {
                    subjectTime.onComplete();
                }
            }

            @Override
            public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
            }

            @Override
            public void onComplete() {
                getViewState().showButStatus(true);
            }
        };
    }

    public Observer<ResultClass.PbStatus> pbStatus() {
        return new Observer<ResultClass.PbStatus>() {
            @Override
            public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {
                pbStatusResArray = new Boolean[8];
            }

            @Override
            public void onNext(ResultClass.@NonNull PbStatus pbStatus) {
                pbStatusResArray[pbStatus.getIndex()] = pbStatus.getStatus();
                getViewState().showPbStatus(pbStatusResArray);
                if (!Arrays.asList(pbStatusResArray).contains(null)&&
                        !Arrays.asList(pbStatusResArray).contains(true)){
                    subjectPbStatus.onComplete();
                }
            }

            @Override
            public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
            }

            @Override
            public void onComplete() {
            }
        };
    }


    public Observable<Integer> createMainObservable (int numElementsMap){

        InjectCallableTaskInterface callableTaskInterface =
                EntryPoints.get(this, InjectCallableTaskInterface.class);

        InjectOperationsInterface operationsInterface =
                EntryPoints.get(this, InjectOperationsInterface.class);

        FillingMaps fillingMaps = operationsInterface.getFillingMaps();
        OperationsWithHashMap operationsWithHashMap =
                operationsInterface.getOperationsWithHashMap();
        OperationsWithTreeMap operationsWithTreeMap =
                operationsInterface.getOperationsWithTreeMap();
        fillingMaps.createFillingMapsOperationsList(numElementsMap);

        ArrayList<Consumer<?>> operationsWithMapsList = new ArrayList<>();
        operationsWithMapsList.addAll(operationsWithHashMap.getOperationsWithHashMap());
        operationsWithMapsList.addAll(operationsWithTreeMap.getOperationsWithTreeMap());

        Consumer<?>[] operationsWithCollectionsArray = new Consumer[6];
        for (Consumer<?> consumer : operationsWithMapsList) {
            operationsWithCollectionsArray[operationsWithMapsList.indexOf(consumer)] = consumer;
        }

        Function[] operationsFillingArray = new Function[2];
        for (Function function : fillingMaps.getOperationsFillingList()) {
            operationsFillingArray[fillingMaps.getOperationsFillingList().indexOf(function)] =
                    function;
        }

        InjectSubjectInterface mInterface = EntryPoints.get(this, InjectSubjectInterface.class);
        subjectTime = mInterface.getSubjectTimeMap();
        subjectPbStatus = mInterface.getSubjectPbStatusMap();
        subjectTime
                .doOnSubscribe(disposables::add)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(timeResults());

        subjectPbStatus
                .doOnSubscribe(disposables::add)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(pbStatus());

        Observable<Integer>
                fillingCollectionsObservable =
                Observable.fromArray(operationsFillingArray)
                        .flatMap(function -> Observable
                                .fromCallable(callableTaskInterface.getFillingOperationsMaps(fillingMaps,
                                        function, subjectTime, subjectPbStatus))
                                .subscribeOn(scheduler))
                        .observeOn(AndroidSchedulers.mainThread());

        Observable<Integer>
                operationsWithCollectionsObservable =
                Observable.fromArray(operationsWithCollectionsArray)
                        .flatMap(consumer -> Observable
                                .fromCallable(callableTaskInterface.getOperationsMaps(fillingMaps,
                                        consumer,operationsWithMapsList, subjectTime, subjectPbStatus))
                                .subscribeOn(scheduler))
                        .observeOn(AndroidSchedulers.mainThread());

        mainObservable =
                Observable.concat(fillingCollectionsObservable, operationsWithCollectionsObservable);
        return mainObservable;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        executor.shutdown();
        disposables.clear();
    }
    private void initExecutor() {
        InjectSchedulerInterface schedulerInterface =
                EntryPoints.get(this, InjectSchedulerInterface.class);
        executor = schedulerInterface.createExecutor();
        scheduler = schedulerInterface.createScheduler(executor);
    }
    public String[] getTimeResArray() {
        return timeResArray;
    }
    public Boolean[] getPbStatusResArray() {
        return pbStatusResArray;
    }
    public Subject<ResultClass.TimeResult> getSubjectTime(){
        return subjectTime;
    }
    public Subject<ResultClass.PbStatus> getSubjectPbStatus(){
        return subjectPbStatus;
    }

}