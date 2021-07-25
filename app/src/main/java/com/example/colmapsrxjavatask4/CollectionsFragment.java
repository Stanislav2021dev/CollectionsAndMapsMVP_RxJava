package com.example.colmapsrxjavatask4;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ObservableField;
import androidx.fragment.app.Fragment;

import com.example.colmapsrxjavatask4.databinding.CollectionsBinding;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableTransformer;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

class GenerateRandomElement {
    private static final Random element = new Random();

    public static int randomInRange(int numEl) {
        return (int) (Math.random() * numEl);
    }

    public static Integer randomInt() {
        return element.nextInt();
    }
}

class FillingCollections {
    public static ArrayList<Integer> colArrayList = new ArrayList<>();
    public static LinkedList<Integer> colLinkedList = new LinkedList<>();
    public static CopyOnWriteArrayList<Integer> colCopyOnWriteArrayList =
            new CopyOnWriteArrayList<>();

    private Singletone s;

    public void A_FillArrayList() {
        s = Singletone.getInstance();
        colArrayList.clear();
        for (int i = 0; i < s.numElementsCollection; i++) {
            colArrayList.add(GenerateRandomElement.randomInt());
        }

    }

    public void B_FillLinkedList() {
        s = Singletone.getInstance();
        colLinkedList.clear();
        for (int i = 0; i < s.numElementsCollection; i++) {
            colLinkedList.add(GenerateRandomElement.randomInt());
        }


    }

    public void C_FillCopyOnWriteArrayList() {
        s = Singletone.getInstance();
        colCopyOnWriteArrayList.clear();
        Integer[] copy = new Integer[s.numElementsCollection];
        for (int i = 0; i < s.numElementsCollection; i++) {
            copy[i] = GenerateRandomElement.randomInt();
        }
        colCopyOnWriteArrayList = new CopyOnWriteArrayList<>(copy);

    }
}

class OperationsWithArrayList {

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

class OperationsWithLinkedList {

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

class OperationsWithCopyOnWriteArrayList {

    public void B_AddInBeginning(CopyOnWriteArrayList<Integer> copyCol) {
        copyCol.add(0, GenerateRandomElement.randomInt());
    }

    public void C_AddInMiddle(CopyOnWriteArrayList<Integer> copyCol) {
        copyCol.add(GenerateRandomElement.randomInRange(copyCol.size()), GenerateRandomElement.randomInt());

    }

    public void D_AddInEnd(CopyOnWriteArrayList<Integer> copyCol) {
        copyCol.add(GenerateRandomElement.randomInt());
    }

    public void E_Search(CopyOnWriteArrayList<Integer> copyCol) {

        Integer searchEL = GenerateRandomElement.randomInt();

        for (Integer val : copyCol) {
            if (searchEL.equals(val)) {
                break;
            }
        }
    }

    public void F_RemoveFromBegining(CopyOnWriteArrayList<Integer> copyCol) {
        copyCol.remove(0);
    }

    public void G_RemoveInMiddle(CopyOnWriteArrayList<Integer> copyCol) {
        copyCol.remove(GenerateRandomElement.randomInRange(copyCol.size()));
    }

    public void H_RemoveInEnd(CopyOnWriteArrayList<Integer> copyCol) {
        copyCol.remove(copyCol.size() - 1);
    }
}


public class CollectionsFragment extends Fragment {

    public static CollectionsBinding binding;
    public static ObservableField<String> amountEl = new ObservableField<>("");
    public static Boolean[] pbStatus = new Boolean[24];
    private final Integer[] indexFillingCollectionsArray = {0, 8, 16};
    private final Integer[]
            indexOperationsArray =
            {1, 9, 17, 2, 10, 18, 3, 11, 19, 4, 12, 20, 5, 13, 21, 6, 14, 22, 7, 15, 23};
    private Singletone s;
    private int pageNumber;
    private Scheduler scheduler;

    public static CollectionsFragment newInstance(int page) {
        CollectionsFragment collectionsFragment = new CollectionsFragment();
        Bundle args = new Bundle();
        args.putInt("num", page);
        collectionsFragment.setArguments(args);
        return collectionsFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        Log.v("MyApp", "on create");
        pageNumber = getArguments() != null ? getArguments().getInt("num") : 1;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.collections, container, false);
        View view = binding.getRoot();
        s = Singletone.getInstance();

        CollectionsFragment butTest = new CollectionsFragment();
        binding.setButTest(butTest);

        if (!(savedInstanceState == null)) {
            binding.setTimeResult(s.timeResult);
        }
        binding.setButStatus(s.butStatus);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }

    public void onTest(View view) {

        s = Singletone.getInstance();
        CollectionsFragment amountElements = new CollectionsFragment();
        s.numElementsCollection = Integer.parseInt(String.valueOf(binding.numCol.getText()));
        binding.setAmountElements(amountElements);
        Log.v("MyApp", "amount elements=" + (s.numElementsCollection));


        int numberOfThreads = Runtime.getRuntime().availableProcessors() - 1;
        ExecutorService executor = Executors.newFixedThreadPool(numberOfThreads);
        scheduler = Schedulers.from(executor);
        ObservableTransformer<Integer, Integer[]> transformer = integers -> integers
                .flatMap(integer -> Observable.just(integer)
                        .subscribeOn(scheduler)
                        .flatMap(integer1 -> Observable.fromCallable(new MyCallableTask(integer)))
                )
                .observeOn(AndroidSchedulers.mainThread());

        Observable<Integer[]>  fillingCollections = Observable.fromArray(indexFillingCollectionsArray)
                .compose(transformer);

        Observable<Integer[]> operationsWithCollections = Observable.fromArray(indexOperationsArray)
                .compose(transformer);

        Observable<Integer[]> observable = Observable.concat(fillingCollections, operationsWithCollections);

        observable.subscribe(new Observer<Integer[]>() {
            @Override
            public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {

                binding.setButStatus(s.butStatus = false);
                s.timeResult = new String[24];
                binding.setTimeResult(s.timeResult);
            }

            @Override
            public void onNext(Integer @io.reactivex.rxjava3.annotations.NonNull [] result) {
                s.timeResult[result[0]] = String.valueOf(result[1]);
                binding.setTimeResult(s.timeResult);
                binding.setIndex(result[0]);
                pbStatus[result[0]] = false;
                binding.setPbStatus(pbStatus);
            }

            @Override
            public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                Log.v("MyApp", "Error" + e);
            }

            @Override
            public void onComplete() {
                binding.setButStatus(s.butStatus = true);
                executor.shutdown();
            }
        });

    }

    static class MyCallableTask implements Callable<Integer[]> {

        private final int index;
        private final Integer[] results = new Integer[2];
        FillingCollections fillingCollections = new FillingCollections();
        OperationsWithArrayList operationsWithArrayList = new OperationsWithArrayList();
        OperationsWithLinkedList operationsWithLinkedList = new OperationsWithLinkedList();
        OperationsWithCopyOnWriteArrayList operationsWithCopyOnWriteArrayList =
                new OperationsWithCopyOnWriteArrayList();
        Method[] fillCol = FillingCollections.class.getDeclaredMethods();
        Method[] operations0 = OperationsWithArrayList.class.getDeclaredMethods();
        Method[] operations1 = OperationsWithLinkedList.class.getDeclaredMethods();
        Method[] operations2 = OperationsWithCopyOnWriteArrayList.class.getDeclaredMethods();
        private int it;
        private int collections;
        private long startTime;
        private int operationTime;

        public MyCallableTask(int index) {
            this.index = index;
        }

        @Override
        public Integer[] call() {
            try {
                collections = index / 8;
                it = index % 8;
                pbStatus[index] = true;
                binding.setPbStatus(pbStatus);

                if (it == 0) {
                    startTime = System.currentTimeMillis();
                    fillCol[collections].invoke(fillingCollections);
                } else {
                    switch (collections) {
                        case (0):
                            ArrayList<Integer> copyCol0 =
                                    new ArrayList<>(FillingCollections.colArrayList);
                            startTime = System.currentTimeMillis();
                            operations0[it - 1].invoke(operationsWithArrayList, copyCol0);
                            break;
                        case (1):
                            LinkedList<Integer> copyCol1 =
                                    new LinkedList<>(FillingCollections.colLinkedList);
                            startTime = System.currentTimeMillis();
                            operations1[it - 1].invoke(operationsWithLinkedList, copyCol1);
                            break;
                        case (2):
                            CopyOnWriteArrayList<Integer> copyCol2 =
                                    new CopyOnWriteArrayList<>(FillingCollections.colCopyOnWriteArrayList);
                            startTime = System.currentTimeMillis();
                            operations2[it - 1].invoke(operationsWithCopyOnWriteArrayList, copyCol2);
                            break;
                    }
                }
                long duration = System.currentTimeMillis() - startTime;
                operationTime = Integer.parseInt(String.valueOf(duration));
                results[0] = index;
                results[1] = operationTime;
                Log.v("MyApp", " index = " + index + "  collection = " + collections + "  it = " + it + "  time = " + +operationTime);
                TimeUnit.SECONDS.sleep(1);

            } catch (IllegalAccessException | InvocationTargetException | InterruptedException e) {
                e.printStackTrace();
                Log.v("MyApp", "Catch");
            }
            return results;
        }
    }
}

