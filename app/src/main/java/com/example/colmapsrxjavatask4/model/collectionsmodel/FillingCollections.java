package com.example.colmapsrxjavatask4.model.collectionsmodel;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Function;

public class FillingCollections {

    private final ArrayList<Integer> colArrayList = new ArrayList<>();
    private final LinkedList<Integer> colLinkedList = new LinkedList<>();
    private final CopyOnWriteArrayList<Integer> colCopyOnWriteArrayList =
            new CopyOnWriteArrayList<>();
    private final List<Function> operationsFillingList = new ArrayList<>();
    private final List<List> collectionsList = new ArrayList<>();

    public FillingCollections() {
        createCollectionsList();
    }

    public Function<ArrayList<Integer>, ArrayList<Integer>> fillArrayList(int numElementsCollection) {
        return arrayList -> {
            arrayList.clear();
            for (int i = 0; i < numElementsCollection; i++) {
                arrayList.add(GenerateRandomElement.randomInt());
            }
            return arrayList;
        };
    }

    public Function<LinkedList<Integer>, LinkedList<Integer>> fillLinkedList(int numElementsCollection) {
        return linkedList -> {
            linkedList.clear();
            for (int i = 0; i < numElementsCollection; i++) {
                linkedList.add(GenerateRandomElement.randomInt());
            }
            return linkedList;
        };
    }

    public Function<CopyOnWriteArrayList<Integer>, CopyOnWriteArrayList<Integer>> fillCopyOnWriteArrayList(int numElementsCollection) {
        return copyOnWriteArrayList -> {
            ArrayList<Integer> copy = new ArrayList<>();
            for (int i = 0; i < numElementsCollection; i++) {
                copy.add(GenerateRandomElement.randomInt());
            }
            copyOnWriteArrayList.addAll(copy);
            return copyOnWriteArrayList;
        };
    }

    public void createFillingCollectionsOperationsList(int numElementsCollection) {
        operationsFillingList.add(fillArrayList(numElementsCollection));
        operationsFillingList.add(fillLinkedList(numElementsCollection));
        operationsFillingList.add(fillCopyOnWriteArrayList(numElementsCollection));
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
