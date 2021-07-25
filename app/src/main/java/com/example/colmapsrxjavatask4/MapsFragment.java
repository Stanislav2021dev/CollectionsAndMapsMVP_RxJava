package com.example.colmapsrxjavatask4;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


class FillingMaps {
    public static HashMap<Integer, Integer> hashMap = new HashMap<>();
    public static TreeMap<Integer, Integer> treeMap = new TreeMap<>();
    public static CountDownLatch countDownLatch1 = new CountDownLatch(1);
    public static CountDownLatch countDownLatch2 = new CountDownLatch(1);

    private Singletone s;

    public void A_FillHashMap() {
        s = Singletone.getInstance();
        for (int i = 0; i < s.numElementsMap; i++) {
            hashMap.put(i, GenerateRandomElement.randomInt());
        }
        countDownLatch1.countDown();
    }

    public void B_fillTreeMap() {
        s = Singletone.getInstance();
        for (int i = 0; i < s.numElementsMap; i++) {
            treeMap.put(i, GenerateRandomElement.randomInt());
        }
        countDownLatch2.countDown();
    }
}

class OperationsWithHashMap {

    public void B_addElement(HashMap<Integer, Integer> copyMap) {
        copyMap.put(GenerateRandomElement.randomInt(), GenerateRandomElement.randomInt());
    }

    public void C_searchElement(HashMap<Integer, Integer> copyMap) {

        copyMap.containsKey(GenerateRandomElement.randomInRange(copyMap.size()));
    }

    public void D_removeElement(HashMap<Integer, Integer> copyMap) {
        copyMap.remove(GenerateRandomElement.randomInRange(copyMap.size()));
    }
}

class OperationsWithTreeMap {

    public void B_addElement(TreeMap<Integer, Integer> copyMap) {
        copyMap.put(GenerateRandomElement.randomInt(), GenerateRandomElement.randomInt());
    }

    public void C_searchElement(TreeMap<Integer, Integer> copyMap) {
        copyMap.containsKey(GenerateRandomElement.randomInRange(copyMap.size()));
    }

    public void D_removeElement(TreeMap<Integer, Integer> copyMap) {
        copyMap.remove(GenerateRandomElement.randomInRange(copyMap.size()));
    }
}

public class MapsFragment extends Fragment {



    private int pageNumber;
    private Singletone s;

    private ExecutorService executorService;

    public static MapsFragment newInstance(int page) {
        MapsFragment mapsFragment = new MapsFragment();
        Bundle args = new Bundle();
        args.putInt("num", page);
        mapsFragment.setArguments(args);
        return mapsFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v("MyApp", "on create");
        pageNumber = getArguments() != null ? getArguments().getInt("num") : 2;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.maps, container, false);

        s = Singletone.getInstance();

        return view;
    }





}






