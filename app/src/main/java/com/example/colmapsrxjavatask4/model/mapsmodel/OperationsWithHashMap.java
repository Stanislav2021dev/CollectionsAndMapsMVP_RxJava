package com.example.colmapsrxjavatask4.model.mapsmodel;

import com.example.colmapsrxjavatask4.model.collectionsmodel.GenerateRandomElement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;

public class OperationsWithHashMap {
    private final List<Consumer<HashMap<Integer, Integer>>>
            operationsWithHashMap =
            new ArrayList<>();

    public OperationsWithHashMap() {
        createListOfOperationsWithHashMap();
    }

    public Consumer<HashMap<Integer, Integer>> addElement() {
        return input -> input.put(GenerateRandomElement.randomInt(), GenerateRandomElement.randomInt());
    }

    public Consumer<HashMap<Integer, Integer>> searchElement() {
        return input -> input.containsKey(GenerateRandomElement.randomInRange(input.size()));
    }

    public Consumer<HashMap<Integer, Integer>> removeElement() {
        return input -> input.remove(GenerateRandomElement.randomInRange(input.size()));
    }

    private void createListOfOperationsWithHashMap() {
        operationsWithHashMap.add(addElement());
        operationsWithHashMap.add(searchElement());
        operationsWithHashMap.add(removeElement());
    }

    public List<Consumer<HashMap<Integer, Integer>>> getOperationsWithHashMap() {
        return operationsWithHashMap;
    }
}
