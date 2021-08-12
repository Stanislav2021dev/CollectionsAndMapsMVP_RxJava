package com.example.colmapsrxjavatask4.presenters;

import io.reactivex.rxjava3.subjects.Subject;

public class ResultClass {

    public static class PbStatus {
        private final int index;
        private final Boolean status;

        public PbStatus(int index, Boolean status) {
            this.index = index;
            this.status = status;
        }

        public int getIndex() {
            return index;
        }

        public Boolean getStatus() {
            return status;
        }
    }

    public static class TimeResult {
        private final int index;
        private final long operationTime;

        public TimeResult(int index, long operationTime) {
            this.index = index;
            this.operationTime = operationTime;
        }

        public int getIndex() {
            return index;
        }

        public long getOperationTime() {
            return operationTime;
        }
    }

    public static class CollectionsSubjectResult {
        public Subject<TimeResult> subjectTime;
        public Subject<PbStatus> subjectStatus;

    }

    public static class MapsSubjectResult {
        public Subject<TimeResult> subjectTime;
        public Subject<PbStatus> subjectStatus;

    }
}
