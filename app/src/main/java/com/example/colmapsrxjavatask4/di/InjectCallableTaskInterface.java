package com.example.colmapsrxjavatask4.di;

import com.example.colmapsrxjavatask4.presenters.CallableTask;

import dagger.hilt.EntryPoint;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import io.reactivex.rxjava3.subjects.Subject;

@EntryPoint
@InstallIn(SingletonComponent.class)
public interface InjectCallableTaskInterface {

    default CallableTask getCallableTask(int index, Subject<CallableTask.TimeResult> subjectTime,
                                         Subject<CallableTask.PbStatus> subjectStatus ){
        return new CallableTask(index,subjectTime,subjectStatus);
    }
}
