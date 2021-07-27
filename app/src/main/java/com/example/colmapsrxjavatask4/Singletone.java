package com.example.colmapsrxjavatask4;

    public class Singletone {
        private static Singletone ourInstance = new Singletone();


        int numElementsCollection=10000;

        int numElementsMap;
        String[] timeResult;
        Boolean[] pbStatus;
        Boolean butStatus=true;
        public static Singletone getInstance() {
            return ourInstance;
        }
    }
