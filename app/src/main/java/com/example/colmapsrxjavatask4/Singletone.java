package com.example.colmapsrxjavatask4;

    public class Singletone {
        private static Singletone ourInstance = new Singletone();


        int numElementsCollection;

        int numElementsMap;
        String[] timeResult;
        Boolean butStatus=true;
        public static Singletone getInstance() {
            return ourInstance;
        }
    }
