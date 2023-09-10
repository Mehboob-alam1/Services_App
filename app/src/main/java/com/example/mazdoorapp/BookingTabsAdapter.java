package com.example.mazdoorapp;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.mazdoorapp.fragments.ActiveFragment;
import com.example.mazdoorapp.fragments.CancelledFragment;
import com.example.mazdoorapp.fragments.CompletedFragment;


public class BookingTabsAdapter extends FragmentStateAdapter {

    public BookingTabsAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {

        switch (position) {
            case 0:
                return new ActiveFragment();
            case 1:
                return new CompletedFragment();
            case 2:
                return new CancelledFragment();
            default:
                return new ActiveFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
