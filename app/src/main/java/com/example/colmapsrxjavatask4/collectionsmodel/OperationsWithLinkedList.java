package com.example.colmapsrxjavatask4.collectionsmodel;

import java.util.LinkedList;

public class OperationsWithLinkedList {
    public void B_AddInBeginning(LinkedList<Integer> copyCol) {
        copyCol.addFirst(GenerateRandomElement.randomInt());
    }

    public void C_AddInMiddle(LinkedList<Integer> copyCol) {
        copyCol.add(GenerateRandomElement.randomInRange(copyCol.size()), GenerateRandomElement.randomInt());
    }

    public void D_AddInEnd(LinkedList<Integer> copyCol) {
        copyCol.addLast(GenerateRandomElement.randomInt());
    }

    public void E_Search(LinkedList<Integer> copyCol) {
        Integer searchEL = GenerateRandomElement.randomInt();
        for (Integer val : copyCol) {
            if (searchEL.equals(val)) {
                break;
            }
        }
    }

    public void F_RemoveFromBegining(LinkedList<Integer> copyCol) {
        copyCol.removeFirst();
    }

    public void G_RemoveInMiddle(LinkedList<Integer> copyCol) {
        copyCol.remove(GenerateRandomElement.randomInRange(copyCol.size()));
    }

    public void H_RemoveInEnd(LinkedList<Integer> copyCol) {
        copyCol.removeLast();
    }
}
