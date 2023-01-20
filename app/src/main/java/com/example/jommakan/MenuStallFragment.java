package com.example.jommakan;

import android.database.sqlite.SQLiteException;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import java.util.ArrayList;

/**
 * A fragment that is responsible for displaying stalls in menu stall page
 */
public class MenuStallFragment extends Fragment {

    /**
     * An adapter that is used by stall name recycler view
     */
    MenuStallItemAdapter menuStallItemAdapter;

    /**
     * A recycler view that is used to display a list of stalls
     */
    RecyclerView stall_name_recycle_view;

    /**
     * A text view that is used to display location name
     */
    TextView location_name;

    /**
     * An array list that is used to store stalls
     */
    ArrayList<Stall> stall_list;

    /**
     * An instance of StallDatabase
     */
    StallDatabase stallDatabase;

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
        return inflater.inflate(R.layout.fragment_menu_stall, container, false);
    }

    /**
     * Called immediately after onCreateView has returned, but before any saved state has been restored in to the view
     * @param view The View returned by onCreateView
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous saved state
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        location_name = view.findViewById(R.id.location_name);
        stall_name_recycle_view = view.findViewById(R.id.stall_name_recycle_view);

        // Toolbar title
        TextView toolbar_title = getActivity().findViewById(R.id.toolbar_title);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            toolbar_title.setTextAppearance(R.style.toolbar_text_tertiary);
            toolbar_title.setText("MENU");
        }

        // Toolbar
        Toolbar toolbar = ((MainActivity) getActivity()).findViewById(R.id.toolbar);
        toolbar.setVisibility(View.VISIBLE);
        ((MainActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((MainActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        // Database connection
        stallDatabase = Room.databaseBuilder(getActivity(), StallDatabase.class, "StallDB").allowMainThreadQueries().build();

        // Extract the data from the bundle (Determine which location user has chosen)
        Bundle arguments = getArguments();
        String chosen_location = arguments.getString("location_name");

        // Get all stalls in a location from database
        stall_list = new ArrayList<>();
        getAllStalls(chosen_location);

        // Display location name
        location_name.setText(chosen_location);

        // Display all stalls from that location in a vertical list view
        menuStallItemAdapter = new MenuStallItemAdapter(getContext(), stall_list);
        stall_name_recycle_view.setAdapter(menuStallItemAdapter);
        stall_name_recycle_view.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
        stall_name_recycle_view.setHasFixedSize(true);
        stall_name_recycle_view.setNestedScrollingEnabled(true);
    }

    /**
     * Get all locations from database
     * @param location location name
     */
    private void getAllStalls(String location) {
        try {
            stall_list.addAll(stallDatabase.stallDAO().getAllStalls(location));
        } catch (SQLiteException e) {
            // Handle errors
            e.printStackTrace();
        } finally {
            // Close the database connection
            stallDatabase.close();
        }
    }
}