package com.example.colmapsrxjavatask4.di;


import com.example.colmapsrxjavatask4.presenters.ResultClass;

import io.reactivex.rxjava3.subjects.PublishSubject;
import io.reactivex.rxjava3.subjects.Subject;
import dagger.hilt.EntryPoint;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@EntryPoint
@InstallIn(SingletonComponent.class)
public interface InjectSubjectInterface {
    default Subject<ResultClass.TimeResult> getSubjectTime() {
        ResultClass.SubjectResult subjectResult=new ResultClass.SubjectResult();
       return subjectResult.subjectTime= PublishSubject.<ResultClass.TimeResult>create().toSerialized();
    }
    default Subject<ResultClass.PbStatus> getSubjectPbStatus(){
        ResultClass.SubjectResult subjectResult=new ResultClass.SubjectResult();
        return subjectResult.subjectStatus=PublishSubject.<ResultClass.PbStatus>create().toSerialized();
    }
}
