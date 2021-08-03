package com.example.colmapsrxjavatask4.presenters;

import android.util.Log;

import com.example.colmapsrxjavatask4.Operation;
import com.example.colmapsrxjavatask4.Singletone;
import com.example.colmapsrxjavatask4.view.CollectionView;


import org.jetbrains.annotations.NotNull;

import java.nio.Buffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.BackpressureStrategy;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.FlowableTransformer;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableTransformer;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.reactivex.rxjava3.subjects.PublishSubject;
import io.reactivex.rxjava3.subjects.ReplaySubject;
import io.reactivex.rxjava3.subjects.Subject;
import moxy.InjectViewState;
import moxy.MvpPresenter;

@InjectViewState
public class CollectionsPresenter extends MvpPresenter<CollectionView> {

    private Scheduler scheduler;
    public SubjectResult subjectResult;
    private Boolean[] pbStatusRes = new Boolean[24];
    private String[] timeResArray =new String[24];
    private ExecutorService executor;
    private final CompositeDisposable disposables=new CompositeDisposable();
    private final Integer[] indexFillingCollectionsArray = {
            Operation.FILLING_ARRAYLIST.getValue(),
            Operation.FILLING_LINKEDLIST.getValue(),
            Operation.FILLING_COPY_ON_WRITE_ARRAYLIST.getValue()};

    private final Integer[] indexOperationsArray =
            {Operation.ADD_IN_BEG_ARRAYLIST.getValue(),
                    Operation.ADD_IN_BEG_LINKEDLIST.getValue(),
                    Operation.ADD_IN_BEG_COPY_ON_WRITE_ARRAYLIST.getValue(),
                    Operation.ADD_IN_MID_ARRAYLIST.getValue(),
                    Operation.ADD_IN_MID_LINKEDLIST.getValue(),
                    Operation.ADD_IN_MID_COPY_ON_WRITE_ARRAYLIST.getValue(),
                    Operation.ADD_IN_END_ARRAYLIST.getValue(),
                    Operation.ADD_IN_END_LINKEDLIST.getValue(),
                    Operation.ADD_IN_END_COPY_ON_WRITE_ARRAYLIST.getValue(),
                    Operation.SEARCH_IN_ARRAYLIST.getValue(),
                    Operation.SEARCH_IN_LINKEDLIST.getValue(),
                    Operation.SEARCH_IN_COPY_ON_WRITE_ARRAYLIST.getValue(),
                    Operation.REMOVE_IN_BEG_ARRAYLIST.getValue(),
                    Operation.REMOVE_IN_BEG_LINKEDLIST.getValue(),
                    Operation.REMOVE_IN_BEG_COPY_ON_WRITE_ARRAYLIST.getValue(),
                    Operation.REMOVE_IN_MID_ARRAYLIST.getValue(),
                    Operation.REMOVE_IN_MID_LINKEDLIST.getValue(),
                    Operation.REMOVE_IN_MID_COPY_ON_WRITE_ARRAYLIST.getValue(),
                    Operation.REMOVE_IN_END_ARRAYLIST.getValue(),
                    Operation.REMOVE_IN_END_LINKEDLIST.getValue(),
                    Operation.REMOVE_IN_END_COPY_ON_WRITE_ARRAYLIST.getValue()};

    public void start(){

        int numberOfThreads = Runtime.getRuntime().availableProcessors() - 1;
        executor = Executors.newFixedThreadPool(numberOfThreads);
        scheduler = Schedulers.from(executor);

        subjectResult= new CollectionsPresenter.SubjectResult();
        subjectResult.subjectTime = PublishSubject.<MyCallableTask.TimeResult>create().toSerialized();
        subjectResult.subjectStatus = PublishSubject.<MyCallableTask.PbStatus>create().toSerialized();
        subjectResult.subjectTime.observeOn(AndroidSchedulers.mainThread()).subscribe(timeResults());
        subjectResult.subjectStatus.observeOn(AndroidSchedulers.mainThread()).subscribe(pbStatus());

        ObservableTransformer<Integer, Integer> transformer = integers -> integers
                                .flatMap(integer1 -> Observable
                                .fromCallable(new MyCallableTask(integer1,subjectResult.subjectTime,
                                        subjectResult.subjectStatus))
                                        .subscribeOn(scheduler)
                                .doOnSubscribe(disposables::add));

        Observable<Integer> fillingCollections = Observable.fromArray(indexFillingCollectionsArray)
                .compose(transformer);

        Observable<Integer> operationsWithCollections = Observable.fromArray(indexOperationsArray)
                .compose(transformer);

        Observable<Integer> observable = Observable.concat(fillingCollections, operationsWithCollections);
        observable.subscribe();
    }

    public Observer<MyCallableTask.TimeResult> timeResults() {
        return new Observer<MyCallableTask.TimeResult>() {
            @Override
            public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {
                timeResArray=new String[24];
                getViewState().showButStatus(false);
                getViewState().showTimeResult(timeResArray);
            }

            @Override
            public void onNext(MyCallableTask.@NonNull TimeResult timeResult) {
                timeResArray[timeResult.index]=String.valueOf(timeResult.operationTime);
                getViewState().showTimeResult(timeResArray);
                if (!Arrays.asList(timeResArray).contains(null)){
                    subjectResult.subjectTime.onComplete();
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

    public Observer<MyCallableTask.PbStatus> pbStatus() {
        return new Observer<MyCallableTask.PbStatus>() {
            @Override
            public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {
                pbStatusRes= new Boolean[24];
            }

            @Override
            public void onNext(MyCallableTask.@NonNull PbStatus pbStatus) {
                pbStatusRes[pbStatus.index]=pbStatus.status;
                getViewState().showPbStatus(pbStatusRes);
            }
            @Override
            public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
            }
            @Override
            public void onComplete() {
            }
        };
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        executor.shutdown();
        disposables.clear();

    }

    public static class SubjectResult {
        Subject<MyCallableTask.TimeResult> subjectTime;
        Subject<MyCallableTask.PbStatus> subjectStatus;
    }
}
