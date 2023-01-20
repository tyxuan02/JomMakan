package com.example.jommakan;

import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * A fragment that is responsible for displaying stall information and its food
 */
public class StallFragment extends Fragment {

    /**
     * An image view that is used to display stall image
     */
    ImageView stall_image;

    /**
     * An image button that will direct users to previous page after clicking on it
     */
    ImageButton back_button;

    /**
     * A text view that is used to display location name
     */
    TextView location_name;

    /**
     * A text view that is used to display stall name
     */
    TextView stall_name;

    /**
     * A text view that is used to display whether the stall is open or close
     */
    TextView open_or_close;

    /**
     * A text view that is used to display stall operation time
     */
    TextView operation_time;

    /**
     * A text view that is used to display stall description
     */
    TextView stall_description;

    /**
     * An array list that is used to store food
     */
    ArrayList<Food> food_list;

    /**
     * A adapter that is used by food recycler view
     */
    StallFoodItemAdapter stallFoodItemAdapter;

    /**
     * A recycler view that is used to display a list of food
     */
    RecyclerView food_recycle_view;

    /**
     * A stall object that is used to store information about a stall
     */
    Stall stall;

    /**
     * An instance of StallDatabase
     */
    StallDatabase stallDatabase;

    /**
     * An instance of FoodDatabase
     */
    FoodDatabase foodDatabase;

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
        View view = inflater.inflate(R.layout.fragment_stall, container, false);
        return view;
    }

    /**
     * Called immediately after onCreateView has returned, but before any saved state has been restored in to the view
     * @param view The View returned by onCreateView
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous saved state
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        stall_image = view.findViewById(R.id.stall_image);
        back_button = view.findViewById(R.id.back_button);
        location_name = view.findViewById(R.id.location_name);
        stall_name = view.findViewById(R.id.stall_name);
        open_or_close = view.findViewById(R.id.open_or_close);
        operation_time = view.findViewById(R.id.operation_time);
        stall_description = view.findViewById(R.id.stall_description);

        // Hide top navigation bar
        Toolbar toolbar = ((MainActivity) getActivity()).findViewById(R.id.toolbar);
        toolbar.setVisibility(View.GONE);

        // Back button
        back_button.setOnClickListener(new View.OnClickListener() {

            /**
             * Direct users to previous page after clicking on it
             * @param v
             */
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        // Database connection
        foodDatabase = Room.databaseBuilder(getActivity(), FoodDatabase.class, "FoodDB").allowMainThreadQueries().build();
        stallDatabase = Room.databaseBuilder(getActivity(), StallDatabase.class, "StallDB").allowMainThreadQueries().build();

        // Extract the data from the bundle (Determine which location user has chosen)
        String selected_stall_name = getArguments().getString("selected_stall_name");
        String selected_location_name = getArguments().getString("selected_location_name");
        getStall(selected_location_name, selected_stall_name);

        stall_image.setImageResource(stall.getStall_image());
        location_name.setText(stall.getLocation());
        stall_name.setText(stall.getStall_name());
        open_or_close.setText(checkOpenOrClose(stall.getOpenAndClose().get(0), stall.getOpenAndClose().get(1)));
        operation_time.setText(stall.getOpenAndClose().get(0) + " to " + stall.getOpenAndClose().get(1));
        stall_description.setText(stall.getDescription());

        // Get all stalls in a location from database
        food_list = new ArrayList<>();
        getAllFood(stall.getStall_name(), stall.getLocation());

        // Display all food in a stall in grid view
        stallFoodItemAdapter = new StallFoodItemAdapter(getContext(), food_list);
        food_recycle_view = view.findViewById(R.id.food_recycle_view);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2, GridLayoutManager.VERTICAL, false);
        food_recycle_view.setLayoutManager(gridLayoutManager);
        food_recycle_view.setAdapter(stallFoodItemAdapter);
    }

    /**
     * Get all food in a stall from database
     * @param location location name
     * @param stall_name stall name
     */
    private void getAllFood(String location, String stall_name) {
        try {
            food_list.addAll(foodDatabase.foodDAO().getStallFood(location, stall_name));
        } catch (SQLiteException e) {
            // Handle errors
            e.printStackTrace();
        } finally {
            // Close the database connection
            foodDatabase.close();
        }
    }

    /**
     * Check whether a stall is open
     * @param open_time stall open time
     * @param close_time stall close time
     * @return String
     */
    private String checkOpenOrClose(String open_time, String close_time) {
        String result = "";

        SimpleDateFormat format = new SimpleDateFormat("h.mm a");
        Calendar time = Calendar.getInstance();
        Calendar time1 = Calendar.getInstance();
        Calendar time2 = Calendar.getInstance();
        try {
            time.setTime(format.parse(format.format(new Date())));
            time.add(Calendar.DATE, 1);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        try {
            time1.setTime(format.parse(open_time));
            time2.setTime(format.parse(close_time));
            time1.add(Calendar.DATE, 1);
            time2.add(Calendar.DATE, 1);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Date currentTime = time.getTime();
        Date open = time1.getTime();
        Date close = time2.getTime();
        System.out.println(format.format(new Date()));

        if (currentTime.after(open) && currentTime.before(close)) {
            result = "Open now";
        } else {
            result = "Closed";
        }

        return result;
    }

    /**
     * Get the stall information in a location from database
     * @param location_name location name
     * @param stall_name stall name
     */
    private void getStall(String location_name, String stall_name) {
        try {
            stall = stallDatabase.stallDAO().getStall(location_name, stall_name);
        } catch (SQLiteException e) {
            // Handle errors
            e.printStackTrace();
        } finally {
            // Close the database connection
            stallDatabase.close();
        }
    }
}