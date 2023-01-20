package com.example.jommakan;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

/**
 * An adapter class that is used to display different tabs in Menu Page
 */
public class MenuViewPagerAdapter extends FragmentStateAdapter {

    /**
     * Constructor of MenuViewPagerAdapter
     * @param fragmentManager FragmentManager
     * @param lifecycle Lifecycle
     */
    public MenuViewPagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    /**
     * Switch between two different tabs (food tab and location tab) depending on the tab that is chosen by users
     * @param position int
     * @return food tab fragment or location tab fragment
     */
    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 1) {
            return new MenuLocationFragment();
        }
        return new MenuFoodFragment();
    }

    /**
     * Get the number of tabs (2)
     * @return int
     */
    @Override
    public int getItemCount() {
        return 2;
    }
}
