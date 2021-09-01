package com.example.colmapsrxjavatask4.view.mapsView;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;

import com.example.colmapsrxjavatask4.R;
import com.example.colmapsrxjavatask4.databinding.MapsBinding;
import com.example.colmapsrxjavatask4.presenters.mapspresenter.MapsPresenter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import dagger.hilt.EntryPoints;
import dagger.hilt.internal.GeneratedComponent;
import moxy.MvpAppCompatFragment;
import moxy.presenter.InjectPresenter;

public class MapsFragment extends MvpAppCompatFragment implements MapView  {
    @VisibleForTesting
    private final List<TextView> tvList = new ArrayList<>();
    private final List<ProgressBar> pbList=new ArrayList<>();
    @InjectPresenter
    public MapsPresenter mapPresenter;
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


            String val=String.valueOf(binding.numMap.getText());

            if (val.isEmpty() || val.equals("0")|| !(val.matches("\\d+"))) {
                binding.numMap.setText("");
                binding.numMap.setHint(R.string.warning);
                return;
            }
            binding.numMap.setHint(R.string.number_elements);
            numElementsMap = Integer.parseInt(val);
            mapPresenter.start(numElementsMap);

            if (String.valueOf(binding.numMap.getText()).isEmpty()) {
                binding.numMap.setHint(R.string.warning);
                return;
            }
            numElementsMap = Integer.parseInt(String.valueOf(binding.numMap.getText()));
            mapPresenter.start(numElementsMap);
        });
        return binding.getRoot();
    }
    @VisibleForTesting
    public List<TextView> initTvList() {
        tvList.addAll(Arrays.asList(binding.time1,binding.time2,binding.time3,binding.time4,
                binding.time5,binding.time6,binding.time7,binding.time8));
        return tvList;
    }
    @VisibleForTesting
    public List<ProgressBar> inintPbList(){
        pbList.addAll(Arrays.asList(binding.pb1,binding.pb2,binding.pb3,binding.pb4,binding.pb5,
                binding.pb6,binding.pb7,binding.pb8));
        return pbList;
    }
    public int getNumElementsMap(){
        return numElementsMap;
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



