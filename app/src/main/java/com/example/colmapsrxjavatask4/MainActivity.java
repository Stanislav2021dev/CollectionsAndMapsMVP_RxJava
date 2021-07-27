package com.example.colmapsrxjavatask4;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.v("MyApp", "on create activity ");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewPager2 pager = (ViewPager2) findViewById(R.id.viewpager);
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