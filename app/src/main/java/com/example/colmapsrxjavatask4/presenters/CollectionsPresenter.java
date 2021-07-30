package com.example.colmapsrxjavatask4.presenters;

import android.util.Log;

import com.example.colmapsrxjavatask4.Operation;
import com.example.colmapsrxjavatask4.Singletone;
import com.example.colmapsrxjavatask4.view.CollectionView;


import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableTransformer;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.reactivex.rxjava3.subjects.PublishSubject;
import io.reactivex.rxjava3.subjects.ReplaySubject;
import moxy.InjectViewState;
import moxy.MvpPresenter;

@InjectViewState
public class CollectionsPresenter extends MvpPresenter<CollectionView> {

    private Scheduler scheduler;
    public SubjectResult subjectResult;
    private Singletone s;
    private Boolean[] pbStatus =new Boolean[24];
    private String[] timeResult =new String[24];

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
                    Operation.REMOVE_IN_END_COPY_ON_WRITE_ARRAYLIST.getValue(),
            };



    public void start(){

        int numberOfThreads = Runtime.getRuntime().availableProcessors() - 1;
        ExecutorService executor = Executors.newFixedThreadPool(numberOfThreads);
        scheduler = Schedulers.from(executor);

        subjectResult= new CollectionsPresenter.SubjectResult();
        subjectResult.subjectTime = PublishSubject.create();
        subjectResult.subjectStatus = PublishSubject.create();
        subjectResult.subjectTime.subscribe(timeResults());
        subjectResult.subjectStatus.subscribe(pbStatus());



        ObservableTransformer<Integer, Integer> transformer = integers -> integers
                                .flatMap(integer1 -> Observable
                                .fromCallable(new MyCallableTask(integer1,subjectResult.subjectTime,
                                        subjectResult.subjectStatus,pbStatus,timeResult))
                                .subscribeOn(scheduler)
                ).observeOn(AndroidSchedulers.mainThread());

        Observable<Integer>  fillingCollections = Observable.fromArray(indexFillingCollectionsArray)
                .compose(transformer);

        Observable<Integer> operationsWithCollections = Observable.fromArray(indexOperationsArray)
                .compose(transformer);

        Observable<Integer> observable = Observable.concat(fillingCollections, operationsWithCollections);

        observable.subscribe();
    }

    public Observer<String[]> timeResults() {
        return new Observer<String[]>() {

            @Override
            public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {
                s = Singletone.getInstance();
                timeResult=new String[24];
                getViewState().showButStatus(false);
                getViewState().showTimeResult(timeResult);

            }

            @Override
            public void onNext(@NotNull @io.reactivex.rxjava3.annotations.NonNull String[] strings) {
                getViewState().showTimeResult(timeResult);

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

    public Observer<Boolean[]> pbStatus() {
        return new Observer<Boolean[]>() {
            @Override
            public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {
                s = Singletone.getInstance();

            }

            @Override
            public void onNext(@NotNull @io.reactivex.rxjava3.annotations.NonNull Boolean[] booleans) {
                getViewState().showPbStatus(booleans);
            }

            @Override
            public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {

            }

            @Override
            public void onComplete() {
            }
        };
    }

    public static class SubjectResult {
        PublishSubject<String[]> subjectTime;
        PublishSubject<Boolean[]> subjectStatus;
    }
}

