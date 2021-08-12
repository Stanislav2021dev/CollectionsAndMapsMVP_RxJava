package com.example.colmapsrxjavatask4.model.collectionsmodel;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class OperationsWithArrayList {
    private final List<Consumer<ArrayList<Integer>>> operationsWithArrayList = new ArrayList<>();

    public OperationsWithArrayList() {
        createListOfOperationsWithArrayList();
    }

    public Consumer<ArrayList<Integer>> addInBeginning() {
        return input -> input.add(0, GenerateRandomElement.randomInt());
    }

    public Consumer<ArrayList<Integer>> addInMiddle() {
        return input -> input.add(GenerateRandomElement.randomInRange(input.size()), GenerateRandomElement.randomInt());
    }

    public Consumer<ArrayList<Integer>> addInEnd() {
        return input -> input.add(GenerateRandomElement.randomInt());
    }

    public Consumer<ArrayList<Integer>> search() {
        return input -> {
            Integer searchEL = GenerateRandomElement.randomInt();
            for (Integer val : input) {
                if (searchEL.equals(val)) {
                    break;
                }
            }
        };
    }

    public Consumer<ArrayList<Integer>> removeFromBegining() {
        return input -> input.remove(0);
    }

    public Consumer<ArrayList<Integer>> removeInMiddle() {
        return input -> input.remove(GenerateRandomElement.randomInRange(input.size()));
    }

    public Consumer<ArrayList<Integer>> removeInEnd() {
        return input -> input.remove(input.size() - 1);
    }

    private void createListOfOperationsWithArrayList() {
        operationsWithArrayList.add(addInBeginning());
        operationsWithArrayList.add(addInMiddle());
        operationsWithArrayList.add(addInEnd());
        operationsWithArrayList.add(search());
        operationsWithArrayList.add(removeFromBegining());
        operationsWithArrayList.add(removeInMiddle());
        operationsWithArrayList.add(removeInEnd());
    }

    public List<Consumer<ArrayList<Integer>>> getOperationsWithArrayList() {
        return operationsWithArrayList;
    }
}
