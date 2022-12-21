package com.example.jommakan;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class MenuViewPagerAdapter extends FragmentStateAdapter {

    public MenuViewPagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 1) {
            return new MenuLocationFragment();
        }
        return new MenuFoodFragment();
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
