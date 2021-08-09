package com.example.colmapsrxjavatask4;

public class Singletone {

    private static final Singletone ourInstance = new Singletone();
        public int numElementsCollection;
        public int numElementsMap;
        public static Singletone getInstance() {
            return ourInstance;
        }
    }
