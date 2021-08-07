package com.example.colmapsrxjavatask4.presenters;

import io.reactivex.rxjava3.subjects.Subject;

public class SubjectResult {
    public Subject<CallableTask.TimeResult> subjectTime;
    public Subject<CallableTask.PbStatus> subjectStatus;
}
