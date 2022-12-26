package com.example.jommakan;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;

public class MenuStallFragment extends Fragment {

    MenuStallItemAdapter menuStallItemAdapter;
    RecyclerView stall_name_recycle_view;
    TextView stall_name;
    ArrayList<Stall> stall_list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_menu_stall, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        TextView toolbar_title = getActivity().findViewById(R.id.toolbar_title);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            toolbar_title.setTextAppearance(R.style.toolbar_text_tertiary);
            toolbar_title.setText("MENU");
        }
        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        ((MainActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((MainActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        SimpleDateFormat format = new SimpleDateFormat("hh.mm a");
        String open = "";
        String close = "";
        open = "10.00 AM";
        close = "10.00 PM";

        stall_name_recycle_view = view.findViewById(R.id.stall_name_recycle_view);
        stall_name = view.findViewById(R.id.stall_name);
        stall_list = new ArrayList<>();

        ArrayList<String> open_close_list = new ArrayList<>();
        open_close_list.add(open);
        open_close_list.add(close);
        ArrayList<String> open_close_list1 = new ArrayList<>();
        open_close_list1.add(open);
        open_close_list1.add("11.30 pm");

        ArrayList<String> description_list = new ArrayList<>();
        description_list.add("Local delight");
        description_list.add("Spicy");
        description_list.add("Contains prawn");

        Food food = new Food("Nasi Goreng", "Faculty of Computer Science and Information Technology", "Restoran Famidah", 8.00, description_list, R.drawable.nasi_goreng_image, open_close_list);
        ArrayList<Food> food_list = new ArrayList<>();
        food_list.add(food);
        food_list.add(food);
        food_list.add(food);

        stall_list.add(new Stall("Restoran Famidah", "Faculty of Computer Science and Information Technology", food_list, "Good for gathering", R.drawable.restoran_famidah_image, open_close_list));
        stall_list.add(new Stall("Restoran Famidah", "Faculty of Computer Science and Information Technology", food_list, "Nice atmosphere", R.drawable.restoran_famidah_image, open_close_list1));
        stall_list.add(new Stall("Restoran Famidah", "Faculty of Computer Science and Information Technology", food_list, "Good for gathering", R.drawable.restoran_famidah_image, open_close_list));

        menuStallItemAdapter = new MenuStallItemAdapter(getContext(), stall_list);

        stall_name_recycle_view.setAdapter(menuStallItemAdapter);
        stall_name_recycle_view.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
        stall_name_recycle_view.setHasFixedSize(true);
        stall_name_recycle_view.setNestedScrollingEnabled(true);
    }
}