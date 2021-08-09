package com.example.colmapsrxjavatask4.di;

import com.example.colmapsrxjavatask4.Singletone;

import dagger.hilt.EntryPoint;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@EntryPoint
@InstallIn(SingletonComponent.class)
public interface InjectSingletoneInterface {
    Singletone s = Singletone.getInstance();
    default Singletone getSingletone(){
        return s;
    }
}