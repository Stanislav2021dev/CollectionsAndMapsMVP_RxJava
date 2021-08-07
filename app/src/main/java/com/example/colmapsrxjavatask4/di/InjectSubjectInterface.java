package com.example.colmapsrxjavatask4.di;


import com.example.colmapsrxjavatask4.presenters.CallableTask;
import com.example.colmapsrxjavatask4.presenters.SubjectResult;
import io.reactivex.rxjava3.subjects.PublishSubject;
import io.reactivex.rxjava3.subjects.Subject;
import dagger.hilt.EntryPoint;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@EntryPoint
@InstallIn(SingletonComponent.class)
public interface InjectSubjectInterface {

    default Subject<CallableTask.TimeResult> getSubjectTime() {
        SubjectResult subjectResult=new SubjectResult();
       return subjectResult.subjectTime= PublishSubject.<CallableTask.TimeResult>create().toSerialized();
    }
    default Subject<CallableTask.PbStatus> getSubjectPbStatus(){
        SubjectResult subjectResult=new SubjectResult();
        return subjectResult.subjectStatus=PublishSubject.<CallableTask.PbStatus>create().toSerialized();
    }

}
