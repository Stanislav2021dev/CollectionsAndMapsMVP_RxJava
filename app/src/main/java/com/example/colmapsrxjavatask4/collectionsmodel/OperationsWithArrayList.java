package com.example.colmapsrxjavatask4.collectionsmodel;

import java.util.ArrayList;

public class OperationsWithArrayList {
    public void B_AddInBeginning(ArrayList<Integer> copyCol) {
        copyCol.add(0, GenerateRandomElement.randomInt());
    }

    public void C_AddInMiddle(ArrayList<Integer> copyCol) {
        copyCol.add(GenerateRandomElement.randomInRange(copyCol.size()), GenerateRandomElement.randomInt());
    }

    public void D_AddInEnd(ArrayList<Integer> copyCol) {
        copyCol.add(GenerateRandomElement.randomInt());
    }

    public void E_Search(ArrayList<Integer> copyCol) {
        Integer searchEL = GenerateRandomElement.randomInt();
        for (Integer val : copyCol) {
            if (searchEL.equals(val)) {
                break;
            }
        }
    }

    public void F_RemoveFromBegining(ArrayList<Integer> copyCol) {
        copyCol.remove(0);
    }

    public void G_RemoveInMiddle(ArrayList<Integer> copyCol) {
        copyCol.remove(GenerateRandomElement.randomInRange(copyCol.size()));
    }

    public void H_RemoveInEnd(ArrayList<Integer> copyCol) {
        copyCol.remove(copyCol.size() - 1);
    }
}
