package com.example.colmapsrxjavatask4.collectionsmodel;

import com.example.colmapsrxjavatask4.Singletone;
import com.example.colmapsrxjavatask4.di.InjectSingletoneInterface;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.concurrent.CopyOnWriteArrayList;
import dagger.hilt.EntryPoints;
import dagger.hilt.internal.GeneratedComponent;

public class FillingCollections implements InjectSingletoneInterface, GeneratedComponent {

    public static ArrayList<Integer> colArrayList = new ArrayList<>();
    public static LinkedList<Integer> colLinkedList = new LinkedList<>();
    public static CopyOnWriteArrayList<Integer> colCopyOnWriteArrayList =
            new CopyOnWriteArrayList<>();
    InjectSingletoneInterface mInterface= EntryPoints.get(this,InjectSingletoneInterface.class);
    Singletone s=mInterface.getSingletone();

    public void A_FillArrayList() {
        colArrayList.clear();
        for (int i = 0; i < s.numElementsCollection; i++) {
            colArrayList.add(GenerateRandomElement.randomInt());
        }
    }

    public void B_FillLinkedList() {
        colLinkedList.clear();
        for (int i = 0; i < s.numElementsCollection; i++) {
            colLinkedList.add(GenerateRandomElement.randomInt());
        }

    }
    public void C_FillCopyOnWriteArrayList() {
        colCopyOnWriteArrayList.clear();
        Integer[] copy = new Integer[s.numElementsCollection];
        for (int i = 0; i < s.numElementsCollection; i++) {
            copy[i] = GenerateRandomElement.randomInt();
        }
        colCopyOnWriteArrayList = new CopyOnWriteArrayList<>(copy);
    }

}
