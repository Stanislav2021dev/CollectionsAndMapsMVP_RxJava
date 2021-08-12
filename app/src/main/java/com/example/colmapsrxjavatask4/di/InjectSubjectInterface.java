package com.example.colmapsrxjavatask4.di;


import com.example.colmapsrxjavatask4.presenters.ResultClass;

import dagger.hilt.EntryPoint;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import io.reactivex.rxjava3.subjects.PublishSubject;
import io.reactivex.rxjava3.subjects.Subject;

@EntryPoint
@InstallIn(SingletonComponent.class)
public interface InjectSubjectInterface {
    default Subject<ResultClass.TimeResult> getSubjectTimeCol() {
        ResultClass.CollectionsSubjectResult
                subjectResult =
                new ResultClass.CollectionsSubjectResult();
        return
                subjectResult.subjectTime =
                        PublishSubject.<ResultClass.TimeResult>create().toSerialized();
    }

    default Subject<ResultClass.PbStatus> getSubjectPbStatusCol() {
        ResultClass.CollectionsSubjectResult
                subjectResult =
                new ResultClass.CollectionsSubjectResult();
        return
                subjectResult.subjectStatus =
                        PublishSubject.<ResultClass.PbStatus>create().toSerialized();
    }

    default Subject<ResultClass.TimeResult> getSubjectTimeMap() {
        ResultClass.MapsSubjectResult subjectResult = new ResultClass.MapsSubjectResult();
        return
                subjectResult.subjectTime =
                        PublishSubject.<ResultClass.TimeResult>create().toSerialized();
    }

    default Subject<ResultClass.PbStatus> getSubjectPbStatusMap() {
        ResultClass.MapsSubjectResult subjectResult = new ResultClass.MapsSubjectResult();
        return
                subjectResult.subjectStatus =
                        PublishSubject.<ResultClass.PbStatus>create().toSerialized();
    }


}
