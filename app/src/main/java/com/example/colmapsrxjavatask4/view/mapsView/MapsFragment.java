package com.example.colmapsrxjavatask4.view.mapsView;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.colmapsrxjavatask4.R;
import com.example.colmapsrxjavatask4.databinding.MapsBinding;
import com.example.colmapsrxjavatask4.presenters.mapspresenter.MapsPresenter;

import dagger.hilt.EntryPoints;
import dagger.hilt.internal.GeneratedComponent;
import moxy.MvpAppCompatFragment;
import moxy.presenter.InjectPresenter;

public class MapsFragment extends MvpAppCompatFragment implements MapView  {

    @InjectPresenter
    public MapsPresenter mapsPresenter;
    private MapsBinding binding;
    private int numElementsMap;
    private int pageNumber;

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

        binding = MapsBinding.inflate(inflater, container, false);
        binding.setButStatus(true);
        binding.testMap.setOnClickListener(v -> {
            if (String.valueOf(binding.numMap.getText()).isEmpty()) {
                binding.numMap.setHint(R.string.warning);
                return;
            }
            numElementsMap = Integer.parseInt(String.valueOf(binding.numMap.getText()));
            mapsPresenter.start(numElementsMap);
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



