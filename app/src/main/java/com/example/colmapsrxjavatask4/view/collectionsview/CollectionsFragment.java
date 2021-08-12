package com.example.colmapsrxjavatask4.view.collectionsview;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import com.example.colmapsrxjavatask4.R;
import com.example.colmapsrxjavatask4.databinding.CollectionsBinding;
import com.example.colmapsrxjavatask4.presenters.collectionspresenter.CollectionsPresenter;

import org.jetbrains.annotations.NotNull;

import moxy.MvpAppCompatFragment;
import moxy.presenter.InjectPresenter;

public class CollectionsFragment extends MvpAppCompatFragment implements CollectionView {

    @InjectPresenter
    public CollectionsPresenter mCollectionsPresenter;
    private CollectionsBinding binding;

    private int pageNumber;
    private int numElementsCollection;

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
            if (String.valueOf(binding.numCol.getText()).isEmpty()) {
                binding.numCol.setHint(R.string.warning);
                return;
            }
            numElementsCollection
                    = Integer.parseInt(String.valueOf(binding.numCol.getText()));
            mCollectionsPresenter.start(numElementsCollection);
        });
        return binding.getRoot();
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