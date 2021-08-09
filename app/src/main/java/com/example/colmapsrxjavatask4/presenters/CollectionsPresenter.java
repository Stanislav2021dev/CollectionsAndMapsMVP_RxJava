package com.example.colmapsrxjavatask4.presenters;

import android.util.Log;

import androidx.annotation.VisibleForTesting;

import com.example.colmapsrxjavatask4.di.InjectCallableTaskInterface;
import com.example.colmapsrxjavatask4.di.InjectSchedulerInterface;
import com.example.colmapsrxjavatask4.di.InjectSubjectInterface;
import com.example.colmapsrxjavatask4.view.CollectionView;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import dagger.hilt.EntryPoints;
import dagger.hilt.internal.GeneratedComponent;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableTransformer;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.reactivex.rxjava3.subjects.Subject;
import moxy.MvpPresenter;


public class CollectionsPresenter extends MvpPresenter<CollectionView> implements InjectSubjectInterface,
        InjectSchedulerInterface, InjectCallableTaskInterface, GeneratedComponent {

    private Scheduler scheduler;
    private ExecutorService executor;
    private Subject<CallableTask.TimeResult> subjectTime;
    private Subject<CallableTask.PbStatus> subjectPbStatus;
    private Boolean[] pbStatusResArray = new Boolean[24];
    private String[] timeResArray =new String[24];
    private final CompositeDisposable disposables=new CompositeDisposable();
    private final Integer[] indexFillingCollectionsArray =new Integer[3];
    private final Integer[] indexOperationsArray=new Integer[21];

    public CollectionsPresenter(){
        fillIndexArrays();
    }

    public void fillIndexArrays(){
        for (int collections = 0; collections <= 2; collections++) {
            int index =  collections * 8 ;
            indexFillingCollectionsArray[collections]=index;
        }
        int count=0;
        for (int iterationNumber = 1; iterationNumber <= 7; iterationNumber++) {
            for (int collections = 0; collections <= 2; collections++) {
                int index = (iterationNumber + 1) + (collections * 8) - 1;
                indexOperationsArray[count]=index;
                count++;
            }
        }
    }

    public void start(){

        InjectCallableTaskInterface callableTaskInterface=EntryPoints.get(this,InjectCallableTaskInterface.class);
        InjectSchedulerInterface schedulerInterface=EntryPoints.get(this,InjectSchedulerInterface.class);
        executor=schedulerInterface.createExecutor();
        scheduler=schedulerInterface.createScheduler(executor);

        InjectSubjectInterface mInterface= EntryPoints.get(this, InjectSubjectInterface.class);
        subjectTime = mInterface.getSubjectTime();
        subjectPbStatus=mInterface.getSubjectPbStatus();
        subjectTime
                .doOnSubscribe(disposables::add)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(timeResults());

        subjectPbStatus
                .doOnSubscribe(disposables::add)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(pbStatus());

        ObservableTransformer<Integer, Integer> transformer = integer -> integer
                                .flatMap(index -> {
                                            return Observable
                                                .fromCallable(callableTaskInterface.getCallableTask(index,subjectTime,subjectPbStatus))
                                                .subscribeOn(scheduler);
                                        }
                                );

        Observable<Integer> fillingCollections = Observable.fromArray(indexFillingCollectionsArray)
                .compose(transformer);

        Observable<Integer> operationsWithCollections = Observable.fromArray(indexOperationsArray)
                .compose(transformer);

        Observable<Integer> observable = Observable.concat(fillingCollections, operationsWithCollections);
        disposables.add(observable.subscribe());
    }



    public Observer<CallableTask.TimeResult> timeResults() {
        return new Observer<CallableTask.TimeResult>() {
            @Override
            public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {
                timeResArray=new String[24];
                getViewState().showButStatus(false);
                getViewState().showTimeResult(timeResArray);
            }

            @Override
            public void onNext(CallableTask.@NonNull TimeResult timeResult) {
                timeResArray[timeResult.index]=String.valueOf(timeResult.operationTime);
                getViewState().showTimeResult(timeResArray);
                if (!Arrays.asList(timeResArray).contains(null)){
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

    public Observer<CallableTask.PbStatus> pbStatus() {
        return new Observer<CallableTask.PbStatus>() {
            @Override
            public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {
                pbStatusResArray= new Boolean[24];
            }

            @Override
            public void onNext(CallableTask.@NonNull PbStatus pbStatus) {
                pbStatusResArray[pbStatus.index]=pbStatus.status;
                getViewState().showPbStatus(pbStatusResArray);
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
}
