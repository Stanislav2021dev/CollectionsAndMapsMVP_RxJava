package com.example.colmapsrxjavatask4.di;


import com.example.colmapsrxjavatask4.collectionsmodel.FillingCollections;
import com.example.colmapsrxjavatask4.presenters.FillingCollecltionsCallable;
import com.example.colmapsrxjavatask4.presenters.OperationsCallable;
import com.example.colmapsrxjavatask4.presenters.ResultClass;

import java.util.ArrayList;
import java.util.function.Consumer;
import java.util.function.Function;

import dagger.hilt.EntryPoint;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import io.reactivex.rxjava3.subjects.Subject;

@EntryPoint
@InstallIn(SingletonComponent.class)
public interface InjectCallableTaskInterface {

    default FillingCollecltionsCallable getFillingOperationsCallable(FillingCollections fillingCollections, Function function,
                                                        Subject<ResultClass.TimeResult> subjectTime,
                                                        Subject<ResultClass.PbStatus> subjectStatus ){
        return new FillingCollecltionsCallable(fillingCollections, function,subjectTime,subjectStatus);
    }
    default OperationsCallable getOperationsCallable(FillingCollections fillingCollections, Consumer consumer,
                                                            ArrayList<Consumer<?>> operations,
                                                            Subject<ResultClass.TimeResult> subjectTime,
                                                            Subject<ResultClass.PbStatus> subjectStatus ){
        return new OperationsCallable(fillingCollections, operations, consumer,subjectTime,subjectStatus);
    }

}
