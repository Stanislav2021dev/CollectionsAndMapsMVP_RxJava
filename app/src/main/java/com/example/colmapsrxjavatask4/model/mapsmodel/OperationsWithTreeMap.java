package com.example.colmapsrxjavatask4.model.mapsmodel;

import com.example.colmapsrxjavatask4.model.collectionsmodel.GenerateRandomElement;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import java.util.function.Consumer;

public class OperationsWithTreeMap {


    private final List<Consumer<TreeMap<Integer, Integer>>>
            operationsWithTreeMap =
            new ArrayList<>();

    public OperationsWithTreeMap() {
        createListOfOperationsWithTreeMap();
    }

    public Consumer<TreeMap<Integer, Integer>> addElement() {
        return input -> input.put(GenerateRandomElement.randomInt(), GenerateRandomElement.randomInt());
    }

    public Consumer<TreeMap<Integer, Integer>> searchElement() {
        return input -> input.containsKey(GenerateRandomElement.randomInRange(input.size()));
    }

    public Consumer<TreeMap<Integer, Integer>> removeElement() {
        return input -> input.remove(GenerateRandomElement.randomInRange(input.size()));
    }

    private void createListOfOperationsWithTreeMap() {
        operationsWithTreeMap.add(addElement());
        operationsWithTreeMap.add(searchElement());
        operationsWithTreeMap.add(removeElement());
    }

    public List<Consumer<TreeMap<Integer, Integer>>> getOperationsWithTreeMap() {
        return operationsWithTreeMap;
    }
}
