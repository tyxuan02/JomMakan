package com.example.jommakan;

import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import java.util.ArrayList;

/**
 * A fragment that is responsible for displaying and managing location tab in menu page
 */
public class MenuLocationFragment extends Fragment {

    /**
     * A recycler view that is used to display a list of locations
     */
    RecyclerView menu_location_recycle_view;

    /**
     * An array list that is used to store locations
     */
    ArrayList<Location> location_list;

    /**
     * An adapter that is used by menu location recycler view
     */
    MenuLocationItemAdapter menuLocationItemAdapter;

    /**
     * An instance of LocationDatabase
     */
    LocationDatabase locationDatabase;

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
        View view = inflater.inflate(R.layout.fragment_menu_location, container, false);
        return view;
    }

    /**
     * Called immediately after onCreateView has returned, but before any saved state has been restored in to the view
     * @param view The View returned by onCreateView
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous saved state
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        menu_location_recycle_view = view.findViewById(R.id.menu_location_recycle_view);

        // Database connection
        locationDatabase = Room.databaseBuilder(getActivity(), LocationDatabase.class, "LocationDB").allowMainThreadQueries().build();

        // Get all food from database
        location_list = new ArrayList<>();
        getAllLocation();

        // Display all location in a grid view
        menuLocationItemAdapter = new MenuLocationItemAdapter(location_list, getActivity());
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2, GridLayoutManager.VERTICAL, false);
        menu_location_recycle_view.setLayoutManager(gridLayoutManager);
        menu_location_recycle_view.setAdapter(menuLocationItemAdapter);
    }

    /**
     * Get all locations from database
     */
    private void getAllLocation() {
        try {
            location_list.addAll(locationDatabase.locationDAO().getAllLocations());
        } catch (SQLiteException e) {
            // Handle errors
            e.printStackTrace();
        } finally {
            // Close the database connection
            locationDatabase.close();
        }
    }
}