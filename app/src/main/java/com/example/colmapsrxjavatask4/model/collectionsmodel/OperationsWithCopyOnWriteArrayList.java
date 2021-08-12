package com.example.colmapsrxjavatask4.model.collectionsmodel;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;

public class OperationsWithCopyOnWriteArrayList {

    private final List<Consumer<CopyOnWriteArrayList<Integer>>>
            operationsWithCopyOnWriteArrayList =
            new ArrayList<>();

    public OperationsWithCopyOnWriteArrayList() {
        createListOfOperationsWithCopyOnWriteArrayList();
    }

    public Consumer<CopyOnWriteArrayList<Integer>> addInBeginning() {
        return input -> input.add(0, GenerateRandomElement.randomInt());
    }

    public Consumer<CopyOnWriteArrayList<Integer>> addInMiddle() {
        return input -> input.add(GenerateRandomElement.randomInRange(input.size()), GenerateRandomElement.randomInt());
    }

    public Consumer<CopyOnWriteArrayList<Integer>> addInEnd() {
        return input -> input.add(GenerateRandomElement.randomInt());
    }

    public Consumer<CopyOnWriteArrayList<Integer>> search() {
        return input -> {
            Integer searchEL = GenerateRandomElement.randomInt();
            for (Integer val : input) {
                if (searchEL.equals(val)) {
                    break;
                }
            }
        };
    }

    public Consumer<CopyOnWriteArrayList<Integer>> removeFromBegining() {
        return input -> input.remove(0);
    }

    public Consumer<CopyOnWriteArrayList<Integer>> removeInMiddle() {
        return input -> input.remove(GenerateRandomElement.randomInRange(input.size()));
    }

    public Consumer<CopyOnWriteArrayList<Integer>> removeInEnd() {
        return input -> input.remove(input.size() - 1);
    }

    private void createListOfOperationsWithCopyOnWriteArrayList() {
        operationsWithCopyOnWriteArrayList.add(addInBeginning());
        operationsWithCopyOnWriteArrayList.add(addInMiddle());
        operationsWithCopyOnWriteArrayList.add(addInEnd());
        operationsWithCopyOnWriteArrayList.add(search());
        operationsWithCopyOnWriteArrayList.add(removeFromBegining());
        operationsWithCopyOnWriteArrayList.add(removeInMiddle());
        operationsWithCopyOnWriteArrayList.add(removeInEnd());
    }

    public List<Consumer<CopyOnWriteArrayList<Integer>>> getOperationsWithCopyOnWriteArrayList(int numElementsCollection) {
        return operationsWithCopyOnWriteArrayList;
    }

}



