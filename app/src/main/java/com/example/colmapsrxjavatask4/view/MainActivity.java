package com.example.colmapsrxjavatask4.view;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.example.colmapsrxjavatask4.R;
import com.example.colmapsrxjavatask4.view.collectionsview.CollectionsFragment;
import com.example.colmapsrxjavatask4.view.mapsView.MapsFragment;

import dagger.hilt.android.AndroidEntryPoint;
import moxy.MvpAppCompatActivity;


@AndroidEntryPoint
public class MainActivity extends MvpAppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.v("MyApp", "on create activity ");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewPager2 pager = findViewById(R.id.viewpager);
        FragmentStateAdapter pageAdapter = new MyAdapter(this);
        pager.setAdapter(pageAdapter);
    }

    public static class MyAdapter extends FragmentStateAdapter {
        public MyAdapter(FragmentActivity fragmentActivity) {
            super(fragmentActivity);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            switch (position) {
                case 0:
                    return (CollectionsFragment.newInstance(position));

                case 1:
                    return (MapsFragment.newInstance(position));

                default:
                    return (CollectionsFragment.newInstance(position));
            }
        }

        @Override
        public int getItemCount() {
            return 2;
        }
    }
}
