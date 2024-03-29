package com.example.jommakan;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;

/**
 * A fragment that is responsible for displaying and managing menu page information
 */
public class MenuFragment extends Fragment {

    /**
     * A fragment manager that is used for managing fragments and their transactions.
     */
    FragmentManager fragmentManager;

    /**
     * An adapter that is used by menu view pager
     */
    MenuViewPagerAdapter menuViewPagerAdapter;

    /**
     * A table layout that acts as a layout for menu view pager
     */
    TabLayout menu_tab_layout;

    /**
     * A view pager2 that is used to display different tabs (food tab and location tab)
     */
    ViewPager2 menu_view_pager;

    /**
     * Called to have the fragment instantiate its user interface view
     * @param inflater The LayoutInflater object that can be used to inflate any views in the fragment
     * @param container If non-null, this is the parent view that the fragment's UI should be attached to
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous saved state
     * @return view
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu, container, false);
        return view;
    }

    /**
     * Called immediately after onCreateView has returned, but before any saved state has been restored in to the view
     * @param view The View returned by onCreateView
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous saved state
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        // Toolbar
        Toolbar toolbar = ((MainActivity) getActivity()).findViewById(R.id.toolbar);
        toolbar.setVisibility(View.VISIBLE);
        TextView toolbar_title = getActivity().findViewById(R.id.toolbar_title);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            toolbar_title.setTextAppearance(R.style.toolbar_text_secondary);
            toolbar_title.setText("MENU");
        }

        // Display different tabs in menu page
        // Users can switch between two different tabs (food tab and location tab)
        fragmentManager = getChildFragmentManager();
        menuViewPagerAdapter = new MenuViewPagerAdapter(fragmentManager, getLifecycle());
        menu_tab_layout = view.findViewById(R.id.menu_table_layout);
        menu_view_pager = view.findViewById(R.id.menu_view_pager);
        menu_view_pager.setAdapter(menuViewPagerAdapter);
        menu_view_pager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                menu_tab_layout.getTabAt(position).select();
            }
        });
        menu_tab_layout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            /**
             * Display the tab that is currently being selected
             * @param tab
             */
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                menu_view_pager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
}