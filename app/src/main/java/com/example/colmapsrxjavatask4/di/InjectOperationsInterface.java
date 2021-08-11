package com.example.colmapsrxjavatask4.di;

import com.example.colmapsrxjavatask4.collectionsmodel.FillingCollections;
import com.example.colmapsrxjavatask4.collectionsmodel.OperationsWithArrayList;
import com.example.colmapsrxjavatask4.collectionsmodel.OperationsWithCopyOnWriteArrayList;
import com.example.colmapsrxjavatask4.collectionsmodel.OperationsWithLinkedList;

import dagger.hilt.EntryPoint;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@EntryPoint
@InstallIn(SingletonComponent.class)

public interface InjectOperationsInterface  {

    default  FillingCollections getFillingCollection(){
        return new FillingCollections();
    }
    default  OperationsWithArrayList getOperationsWithArrayList(){
        return new OperationsWithArrayList();
    }
    default OperationsWithLinkedList getOperationsWithLinkedList(){
        return new OperationsWithLinkedList();
    }
    default OperationsWithCopyOnWriteArrayList getOperationsWithCopyOnWriteArrayList(){
        return new OperationsWithCopyOnWriteArrayList();
    }
}
