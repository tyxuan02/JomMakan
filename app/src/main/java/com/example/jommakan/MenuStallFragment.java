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

public class MenuStallFragment extends Fragment {

    MenuStallItemAdapter menuStallItemAdapter;
    RecyclerView stall_name_recycle_view;
    TextView location_name;
    ArrayList<Stall> stall_list;
    StallDatabase stallDatabase;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_menu_stall, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        location_name = view.findViewById(R.id.location_name);
        stall_name_recycle_view = view.findViewById(R.id.stall_name_recycle_view);

        TextView toolbar_title = getActivity().findViewById(R.id.toolbar_title);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            toolbar_title.setTextAppearance(R.style.toolbar_text_tertiary);
            toolbar_title.setText("MENU");
        }

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

        location_name.setText(chosen_location);

        // Prepare recycle view and adapter to display all stalls in a location
        menuStallItemAdapter = new MenuStallItemAdapter(getContext(), stall_list);

        stall_name_recycle_view.setAdapter(menuStallItemAdapter);
        stall_name_recycle_view.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
        stall_name_recycle_view.setHasFixedSize(true);
        stall_name_recycle_view.setNestedScrollingEnabled(true);
    }

    // Get all locations from database
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