package com.example.colmapsrxjavatask4.presenters;

import io.reactivex.rxjava3.subjects.Subject;

public class ResultClass {

    public static class PbStatus {
        int index;
        Boolean status;
        public PbStatus(int index, Boolean status) {
            this.index = index;
            this.status = status;
        }
    }
    public static class TimeResult {
        int index;
        long operationTime;

        public TimeResult(int index, long operationTime) {
            this.index = index;
            this.operationTime = operationTime;
        }
    }
    public static class SubjectResult {
        public Subject<TimeResult> subjectTime;
        public Subject<PbStatus> subjectStatus;
    }
}
