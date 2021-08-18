package com.example.colmapsrxjavatask4.view.collectionsview;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;

import com.example.colmapsrxjavatask4.R;
import com.example.colmapsrxjavatask4.databinding.CollectionsBinding;
import com.example.colmapsrxjavatask4.presenters.collectionspresenter.CollectionsPresenter;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import moxy.MvpAppCompatFragment;
import moxy.presenter.InjectPresenter;

public class CollectionsFragment extends MvpAppCompatFragment implements CollectionView {

    @InjectPresenter
    public CollectionsPresenter mCollectionsPresenter;
    private CollectionsBinding binding;

    private int pageNumber;
    private int numElementsCollection;
    @VisibleForTesting
    private final List<TextView> tvList = new ArrayList<>();
    private final List<ProgressBar> pbList=new ArrayList<>();


    public static CollectionsFragment newInstance(int page) {
        CollectionsFragment
                collectionsFragment = new CollectionsFragment();
        Bundle args = new Bundle();
        args.putInt("num", page);
        collectionsFragment.setArguments(args);
        return collectionsFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageNumber = getArguments() != null ? getArguments().getInt("num") : 1;
    }

    @Nullable
    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = CollectionsBinding.inflate(inflater, container, false);
        binding.setButStatus(true);
        binding.testCol.setOnClickListener(v -> {

            String val=String.valueOf(binding.numCol.getText());

            if (val.isEmpty() || val.equals("0")|| !(val.matches("\\d+"))) {
                    binding.numCol.setText("");
                    binding.numCol.setHint(R.string.warning);
                    return;
            }
            binding.numCol.setHint(R.string.number_elements);
            numElementsCollection = Integer.parseInt(val);
            mCollectionsPresenter.start(numElementsCollection);
        });
        return binding.getRoot();
    }
    @VisibleForTesting
    public List<TextView> initTvList() {
        tvList.addAll(Arrays.asList(binding.time1,binding.time2,binding.time3,binding.time4,binding.time5,binding.time6,
                binding.time7,binding.time8,binding.time9,binding.time10,binding.time11,binding.time12,
                binding.time13,binding.time14,binding.time15,binding.time16,binding.time17,binding.time18,
                binding.time19,binding.time20,binding.time21,binding.time22,binding.time23,binding.time24));
        return tvList;
    }
    @VisibleForTesting
    public List<ProgressBar> inintPbList(){ pbList.addAll(Arrays.asList(binding.pb1,binding.pb2,binding.pb3,binding.pb4,binding.pb5,binding.pb6,
            binding.pb7,binding.pb8,binding.pb9,binding.pb10,binding.pb11,binding.pb12,
            binding.pb13,binding.pb14,binding.pb15,binding.pb16,binding.pb17,binding.pb18,
            binding.pb19,binding.pb20,binding.pb21,binding.pb22,binding.pb23,binding.pb24));
        return pbList;
    }


    public int getNumElementsCollection(){
        return numElementsCollection;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void showButStatus(boolean butStatus) {
        binding.setButStatus(butStatus);
    }

    @Override
    public void showTimeResult(String[] timeResult) {
        binding.setTimeResult(timeResult);
    }

    @Override
    public void showPbStatus(Boolean[] pbStatus) {
        binding.setPbStatus(pbStatus);
    }



}