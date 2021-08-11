package com.example.colmapsrxjavatask4.collectionsmodel;

import com.example.colmapsrxjavatask4.Singletone;
import com.example.colmapsrxjavatask4.di.InjectSingletoneInterface;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Function;

import dagger.hilt.EntryPoints;
import dagger.hilt.internal.GeneratedComponent;

public class FillingCollections implements InjectSingletoneInterface, GeneratedComponent {

    private final ArrayList<Integer> colArrayList = new ArrayList<>();
    private final LinkedList<Integer> colLinkedList = new LinkedList<>();
    private final CopyOnWriteArrayList<Integer> colCopyOnWriteArrayList =
            new CopyOnWriteArrayList<>();
    private final List<Function> operationsFillingList = new ArrayList<>();
    private final List<List> collectionsList = new ArrayList<>();


    InjectSingletoneInterface mInterface = EntryPoints.get(this, InjectSingletoneInterface.class);
    Singletone s = mInterface.getSingletone();

    public FillingCollections() {
        createCollectionsList();
        createFillingCollectionsOperationsList();
    }


    public Function<ArrayList<Integer>, ArrayList<Integer>> fillArrayList() {
        return arrayList -> {
            arrayList.clear();
            for (int i = 0; i < s.numElementsCollection; i++) {
                arrayList.add(GenerateRandomElement.randomInt());
            }
            return arrayList;
        };
    }


    public Function<LinkedList<Integer>, LinkedList<Integer>> fillLinkedList() {
        return linkedList -> {
            linkedList.clear();
            for (int i = 0; i < s.numElementsCollection; i++) {
                linkedList.add(GenerateRandomElement.randomInt());
            }
            return linkedList;
        };
    }

    public Function<CopyOnWriteArrayList<Integer>, CopyOnWriteArrayList<Integer>> fillCopyOnWriteArrayList() {
        return copyOnWriteArrayList -> {
            ArrayList<Integer> copy = new ArrayList<>();
            for (int i = 0; i < s.numElementsCollection; i++) {
                copy.add(GenerateRandomElement.randomInt());
            }
            copyOnWriteArrayList.addAll(copy);
            return copyOnWriteArrayList;
        };
    }

    public void createFillingCollectionsOperationsList() {
        operationsFillingList.add(fillArrayList());
        operationsFillingList.add(fillLinkedList());
        operationsFillingList.add(fillCopyOnWriteArrayList());
    }

    public void createCollectionsList() {
        collectionsList.add(colArrayList);
        collectionsList.add(colLinkedList);
        collectionsList.add(colCopyOnWriteArrayList);
    }

    public ArrayList<Integer> getArrayList() {
        return colArrayList;
    }

    public LinkedList<Integer> getLinkedList() {
        return colLinkedList;
    }

    public CopyOnWriteArrayList<Integer> getCopyOnWriteArrayList() {
        return colCopyOnWriteArrayList;
    }

    public List<List> getCollectionsList() {
        return collectionsList;
    }

    public List<Function> getOperationsFillingList() {
        return operationsFillingList;
    }

}
