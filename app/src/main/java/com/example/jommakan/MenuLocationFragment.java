package com.example.jommakan;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;

public class MenuLocationFragment extends Fragment {

    RecyclerView menu_location_recycle_view;
    ArrayList<Location> location_list;
    MenuLocationItemAdapter menuLocationItemAdapter;
    LocationDatabase locationDatabase;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu_location, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        menu_location_recycle_view = view.findViewById(R.id.menu_location_recycle_view);

        // Database connection
        locationDatabase = Room.databaseBuilder(getActivity(), LocationDatabase.class, "LocationDB").allowMainThreadQueries().build();

        // Get all food from database
        location_list = new ArrayList<>();
        getAllLocation();

        // Prepare recycle view and adapter to display all locations
        menuLocationItemAdapter = new MenuLocationItemAdapter(location_list, getActivity());

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2, GridLayoutManager.VERTICAL, false);
        menu_location_recycle_view.setLayoutManager(gridLayoutManager);
        menu_location_recycle_view.setAdapter(menuLocationItemAdapter);
    }

    // Get all locations from database
    private void getAllLocation() {
        location_list.addAll(locationDatabase.locationDAO().getAllLocations());
    }
}