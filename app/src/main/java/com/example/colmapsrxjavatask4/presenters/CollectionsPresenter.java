package com.example.colmapsrxjavatask4.presenters;

import com.example.colmapsrxjavatask4.view.CollectionView;


import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableTransformer;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.reactivex.rxjava3.subjects.PublishSubject;
import io.reactivex.rxjava3.subjects.Subject;
import moxy.InjectViewState;
import moxy.MvpPresenter;

@InjectViewState
public class CollectionsPresenter extends MvpPresenter<CollectionView> {

    private Scheduler scheduler;
    public SubjectResult subjectResult;
    private Boolean[] pbStatusResArray = new Boolean[24];
    private String[] timeResArray =new String[24];
    private ExecutorService executor;
    private final CompositeDisposable disposables=new CompositeDisposable();
    private final Integer[] indexFillingCollectionsArray =new Integer[3];
    private final Integer[] indexOperationsArray=new Integer[21];

    public CollectionsPresenter(){
        fillIndexArrays();
        createScheduler();
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

    public void createScheduler(){
        int numberOfThreads = Runtime.getRuntime().availableProcessors() - 1;
        executor = Executors.newFixedThreadPool(numberOfThreads);
        scheduler = Schedulers.from(executor);
    }

    public void start(){

        subjectResult= new CollectionsPresenter.SubjectResult();
        subjectResult.subjectTime = PublishSubject.<MyCallableTask.TimeResult>create().toSerialized();
        subjectResult.subjectStatus = PublishSubject.<MyCallableTask.PbStatus>create().toSerialized();

        subjectResult.subjectTime
                .doOnSubscribe(disposables::add)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(timeResults());
        subjectResult.subjectStatus
                .doOnSubscribe(disposables::add)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(pbStatus());

        ObservableTransformer<Integer, Integer> transformer = integers -> integers
                                .flatMap(index -> Observable
                                .fromCallable(new MyCallableTask(index,subjectResult.subjectTime,
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
                pbStatusResArray= new Boolean[24];
            }

            @Override
            public void onNext(MyCallableTask.@NonNull PbStatus pbStatus) {
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

    public static class SubjectResult {
        Subject<MyCallableTask.TimeResult> subjectTime;
        Subject<MyCallableTask.PbStatus> subjectStatus;
    }
}
