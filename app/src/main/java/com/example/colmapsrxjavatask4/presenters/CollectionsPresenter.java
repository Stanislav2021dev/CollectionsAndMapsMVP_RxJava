package com.example.colmapsrxjavatask4.presenters;

import android.os.Build;

import java.util.function.Function;
import androidx.annotation.RequiresApi;

import com.example.colmapsrxjavatask4.collectionsmodel.FillingCollections;
import com.example.colmapsrxjavatask4.collectionsmodel.OperationsWithArrayList;
import com.example.colmapsrxjavatask4.collectionsmodel.OperationsWithCopyOnWriteArrayList;
import com.example.colmapsrxjavatask4.collectionsmodel.OperationsWithLinkedList;
import com.example.colmapsrxjavatask4.di.InjectCallableTaskInterface;
import com.example.colmapsrxjavatask4.di.InjectOperationsInterface;
import com.example.colmapsrxjavatask4.di.InjectSchedulerInterface;
import com.example.colmapsrxjavatask4.di.InjectSubjectInterface;
import com.example.colmapsrxjavatask4.view.CollectionView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.function.Consumer;

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


public class CollectionsPresenter extends MvpPresenter<CollectionView> implements InjectSubjectInterface,
        InjectSchedulerInterface, InjectCallableTaskInterface,InjectOperationsInterface, GeneratedComponent {

    private Scheduler scheduler;
    private ExecutorService executor;
    private Subject<ResultClass.TimeResult> subjectTime;
    private Subject<ResultClass.PbStatus> subjectPbStatus;
    private Boolean[] pbStatusResArray = new Boolean[24];
    private String[] timeResArray =new String[24];
    private final CompositeDisposable disposables=new CompositeDisposable();


    @RequiresApi(api = Build.VERSION_CODES.N)
    public void start() {

        InjectCallableTaskInterface callableTaskInterface=EntryPoints.get(this,InjectCallableTaskInterface.class);
        InjectSchedulerInterface schedulerInterface=EntryPoints.get(this,InjectSchedulerInterface.class);
        InjectOperationsInterface operationsInterface=EntryPoints.get(this,InjectOperationsInterface.class);

        FillingCollections fillingCollections=operationsInterface.getFillingCollection();
        OperationsWithArrayList operationsWithArrayList =
                operationsInterface.getOperationsWithArrayList();
        OperationsWithLinkedList operationsWithLinkedList =
                operationsInterface.getOperationsWithLinkedList();
        OperationsWithCopyOnWriteArrayList operationsWithCopyOnWriteArrayList =
                operationsInterface.getOperationsWithCopyOnWriteArrayList();


        executor=schedulerInterface.createExecutor();
        scheduler=schedulerInterface.createScheduler(executor);

        ArrayList<Consumer<?>> operationsWithCollectionsList=new ArrayList<>();
        operationsWithCollectionsList.addAll(operationsWithArrayList.getOperationsWithArrayList());
        operationsWithCollectionsList.addAll(operationsWithLinkedList.getOperationsWithLinkedList());
        operationsWithCollectionsList.addAll(operationsWithCopyOnWriteArrayList.getOperationsWithCopyOnWriteArrayList());

        Consumer<?> [] operationsWithCollectionsArray= new Consumer[21];
        for (Consumer<?> consumer: operationsWithCollectionsList){
            operationsWithCollectionsArray[operationsWithCollectionsList.indexOf(consumer)]=consumer;
        }

        Function [] operationsFillingArray=new Function[3];
        for (Function function:fillingCollections.getOperationsFillingList())
        {
            operationsFillingArray[fillingCollections.getOperationsFillingList().indexOf(function)]=
                    function;
        }


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

         Observable<Integer> fillingCollectionsObservable = Observable.fromArray(operationsFillingArray)
                .flatMap(function -> Observable
                        .fromCallable(callableTaskInterface.getFillingOperationsCallable(fillingCollections,
                                function,subjectTime,subjectPbStatus))
                        .subscribeOn(scheduler))
                .observeOn(AndroidSchedulers.mainThread());

        Observable<Integer> operationsWithCollectionsObservable = Observable.fromArray(operationsWithCollectionsArray)
                .flatMap(consumer-> Observable
                .fromCallable(callableTaskInterface.getOperationsCallable(fillingCollections, consumer,
                        operationsWithCollectionsList,subjectTime,subjectPbStatus))
                .subscribeOn(scheduler))
                .observeOn(AndroidSchedulers.mainThread());

        Observable<Integer> mainObservable=Observable.concat(fillingCollectionsObservable,operationsWithCollectionsObservable);
        disposables.add(mainObservable.subscribe());
    }


    public Observer<ResultClass.TimeResult> timeResults() {
        return new Observer<ResultClass.TimeResult>() {
            @Override
            public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {
                timeResArray=new String[24];
                getViewState().showButStatus(false);
                getViewState().showTimeResult(timeResArray);
            }

            @Override
            public void onNext(ResultClass.@NonNull TimeResult timeResult) {
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

    public Observer<ResultClass.PbStatus> pbStatus() {
        return new Observer<ResultClass.PbStatus>() {
            @Override
            public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {
                pbStatusResArray= new Boolean[24];
            }

            @Override
            public void onNext(ResultClass.@NonNull PbStatus pbStatus) {
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
