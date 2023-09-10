package com.example.mazdoorapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.example.mazdoorapp.databinding.ActivityBookingBinding;
import com.google.android.material.tabs.TabLayout;

public class BookingActivity extends AppCompatActivity {
private ActivityBookingBinding binding;
    BookingTabsAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityBookingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        adapter = new BookingTabsAdapter(this);

        binding.tabViewpager.setAdapter(adapter);


        binding.bookingTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                binding.tabViewpager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        binding.tabViewpager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                binding.bookingTabLayout.getTabAt(position).select();


            }
        });

    }
}