package com.example.colmapsrxjavatask4.di;

import com.example.colmapsrxjavatask4.model.collectionsmodel.FillingCollections;
import com.example.colmapsrxjavatask4.model.collectionsmodel.OperationsWithArrayList;
import com.example.colmapsrxjavatask4.model.collectionsmodel.OperationsWithCopyOnWriteArrayList;
import com.example.colmapsrxjavatask4.model.collectionsmodel.OperationsWithLinkedList;
import com.example.colmapsrxjavatask4.model.mapsmodel.FillingMaps;
import com.example.colmapsrxjavatask4.model.mapsmodel.OperationsWithHashMap;
import com.example.colmapsrxjavatask4.model.mapsmodel.OperationsWithTreeMap;

import dagger.hilt.EntryPoint;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@EntryPoint
@InstallIn(SingletonComponent.class)

public interface InjectOperationsInterface {

    default FillingCollections getFillingCollection() {
        return new FillingCollections();
    }

    default OperationsWithArrayList getOperationsWithArrayList() {
        return new OperationsWithArrayList();
    }

    default OperationsWithLinkedList getOperationsWithLinkedList() {
        return new OperationsWithLinkedList();
    }

    default OperationsWithCopyOnWriteArrayList getOperationsWithCopyOnWriteArrayList() {
        return new OperationsWithCopyOnWriteArrayList();
    }

    default FillingMaps getFillingMaps() {
        return new FillingMaps();
    }

    default OperationsWithHashMap getOperationsWithHashMap() {
        return new OperationsWithHashMap();
    }

    default OperationsWithTreeMap getOperationsWithTreeMap() {
        return new OperationsWithTreeMap();
    }
}
