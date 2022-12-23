package com.example.jommakan;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;

public class MenuFragment extends Fragment {

    FragmentManager fragmentManager;
    MenuViewPagerAdapter menuViewPagerAdapter;
    TabLayout menu_tab_layout;
    ViewPager2 menu_view_pager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_menu, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        // Declare and assign views here
        TextView toolbar_title = getActivity().findViewById(R.id.toolbar_title);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            toolbar_title.setTextAppearance(R.style.toolbar_text_secondary);
            toolbar_title.setText("MENU");
        }

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