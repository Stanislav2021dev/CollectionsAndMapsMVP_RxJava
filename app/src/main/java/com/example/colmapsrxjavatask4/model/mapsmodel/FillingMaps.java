package com.example.colmapsrxjavatask4.model.mapsmodel;

import com.example.colmapsrxjavatask4.model.collectionsmodel.GenerateRandomElement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Function;

public class FillingMaps {

    private final HashMap<Integer, Integer> hashMap = new HashMap<>();
    private final TreeMap<Integer, Integer> treeMap = new TreeMap<>();
    private final List<Function> operationsFillingList = new ArrayList<>();
    private final List<Map<Integer, Integer>> mapsList = new ArrayList<>();

    public FillingMaps() {
        createMapsList();
    }

    public Function<HashMap<Integer, Integer>, HashMap<Integer, Integer>> fillHashMap(int numElementsMap) {
        return hashMap -> {
            hashMap.clear();
            for (int i = 0; i < numElementsMap; i++) {
                hashMap.put(i, GenerateRandomElement.randomInt());
            }
            return hashMap;
        };
    }

    public Function<TreeMap<Integer, Integer>, TreeMap<Integer, Integer>> fillTreeMap(int numElementsMap) {
        treeMap.clear();
        return treeMap -> {
            treeMap.clear();
            for (int i = 0; i < numElementsMap; i++) {
                treeMap.put(i, GenerateRandomElement.randomInt());
            }
            return treeMap;
        };
    }


    public void createFillingMapsOperationsList(int numElementsMap) {
        operationsFillingList.add(fillHashMap(numElementsMap));
        operationsFillingList.add(fillTreeMap(numElementsMap));
    }

    public void createMapsList() {
        mapsList.add(hashMap);
        mapsList.add(treeMap);
    }

    public HashMap<Integer, Integer> getHashMap() {
        return hashMap;
    }

    public TreeMap<Integer, Integer> getTreeMap() {
        return treeMap;
    }

    public List<Function> getOperationsFillingList() {
        return operationsFillingList;
    }

    public List<Map<Integer, Integer>> getMapsList() {
        return mapsList;
    }
}

