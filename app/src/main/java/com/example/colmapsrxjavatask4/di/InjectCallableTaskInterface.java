package com.example.colmapsrxjavatask4.di;


import com.example.colmapsrxjavatask4.model.collectionsmodel.FillingCollections;
import com.example.colmapsrxjavatask4.model.mapsmodel.FillingMaps;
import com.example.colmapsrxjavatask4.presenters.ResultClass;
import com.example.colmapsrxjavatask4.presenters.collectionspresenter.FillingCollectionsCallable;
import com.example.colmapsrxjavatask4.presenters.collectionspresenter.OperationsCollectionsCallable;
import com.example.colmapsrxjavatask4.presenters.mapspresenter.FillingMapsCallable;
import com.example.colmapsrxjavatask4.presenters.mapspresenter.OperationsMapsCallable;

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

    default FillingCollectionsCallable getFillingOperationsCollections(FillingCollections fillingCollections, Function function,
                                                                       Subject<ResultClass.TimeResult> subjectTime,
                                                                       Subject<ResultClass.PbStatus> subjectStatus) {
        return new FillingCollectionsCallable(fillingCollections, function, subjectTime, subjectStatus);
    }

    default OperationsCollectionsCallable getOperationsCollections(FillingCollections fillingCollections, Consumer consumer,
                                                                   ArrayList<Consumer<?>> operations,
                                                                   Subject<ResultClass.TimeResult> subjectTime,
                                                                   Subject<ResultClass.PbStatus> subjectStatus) {
        return new OperationsCollectionsCallable(fillingCollections, operations, consumer, subjectTime, subjectStatus);
    }

    default FillingMapsCallable getFillingOperationsMaps(FillingMaps fillingMaps, Function function,
                                                         Subject<ResultClass.TimeResult> subjectTime,
                                                         Subject<ResultClass.PbStatus> subjectStatus) {
        return new FillingMapsCallable(fillingMaps, function, subjectTime, subjectStatus);
    }

    default OperationsMapsCallable getOperationsMaps(FillingMaps fillingMaps, Consumer consumer, ArrayList<Consumer<?>> operations,
                                                     Subject<ResultClass.TimeResult> subjectTime,
                                                     Subject<ResultClass.PbStatus> subjectStatus) {
        return new OperationsMapsCallable(fillingMaps, operations, consumer, subjectTime, subjectStatus);
    }

}
