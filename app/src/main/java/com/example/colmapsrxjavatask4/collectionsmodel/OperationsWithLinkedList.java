package com.example.colmapsrxjavatask4.collectionsmodel;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

public class OperationsWithLinkedList {

    public final List<Consumer<LinkedList<Integer>>> operationsWithLinkedList=new ArrayList<>();

    public OperationsWithLinkedList(){
        createListOfOperationsWithLinkedList();
    }

    public Consumer<LinkedList<Integer>> addInBeginning() {
        return input -> input.addFirst(GenerateRandomElement.randomInt());
    }

    public Consumer<LinkedList<Integer>> addInMiddle() {
        return input -> input.add(GenerateRandomElement.randomInRange(input.size()), GenerateRandomElement.randomInt());
    }

    public Consumer<LinkedList<Integer>> addInEnd() {
        return input -> input.addLast(GenerateRandomElement.randomInt());
    }

    public Consumer<LinkedList<Integer>> search() {
        return input -> {
            Integer searchEL = GenerateRandomElement.randomInt();
            for (Integer val : input) {
                if (searchEL.equals(val)) {
                    break;
                }
            }
        };
    }

    public Consumer<LinkedList<Integer>> removeFromBegining() {
        return LinkedList::removeFirst;
    }

    public Consumer<LinkedList<Integer>> removeInMiddle() {
        return input -> input.remove(GenerateRandomElement.randomInRange(input.size()));
    }

    public Consumer<LinkedList<Integer>> removeInEnd() {
        return LinkedList::removeLast;
    }

    private void createListOfOperationsWithLinkedList() {
        operationsWithLinkedList.add(addInBeginning());
        operationsWithLinkedList.add(addInMiddle());
        operationsWithLinkedList.add(addInEnd());
        operationsWithLinkedList.add(search());
        operationsWithLinkedList.add(removeFromBegining());
        operationsWithLinkedList.add(removeInMiddle());
        operationsWithLinkedList.add(removeInEnd());
    }
    public List<Consumer<LinkedList<Integer>>> getOperationsWithLinkedList(){
        return operationsWithLinkedList;
    }
}
