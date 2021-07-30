package com.example.colmapsrxjavatask4;

    public class Singletone {
        private static Singletone ourInstance = new Singletone();

        public int numElementsCollection;
        public int numElementsMap;
        //public String[] timeResult;


        public static Singletone getInstance() {
            return ourInstance;
        }
    }
