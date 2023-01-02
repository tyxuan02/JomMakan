package com.example.jommakan;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class StallFragment extends Fragment {

    ImageView stall_image;
    ImageButton back_button;
    TextView location_name, stall_name, open_or_close, operation_time, stall_description;
    FoodDatabase foodDatabase;
    ArrayList<Food> food_list;
    StallFoodItemAdapter stallFoodItemAdapter;
    RecyclerView food_recycle_view;
    Stall stall;
    StallDatabase stallDatabase;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_stall, container, false);
        return view;
    }

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
        getAllStalls(stall.getStall_name(), stall.getLocation());

        // Prepare recycle view and adapter to display all food in a stall
        stallFoodItemAdapter = new StallFoodItemAdapter(getContext(), food_list);
        food_recycle_view = view.findViewById(R.id.food_recycle_view);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2, GridLayoutManager.VERTICAL, false);
        food_recycle_view.setLayoutManager(gridLayoutManager);
        food_recycle_view.setAdapter(stallFoodItemAdapter);
    }

    private void getAllStalls(String location, String stall_name) {
        food_list.addAll(foodDatabase.foodDAO().getStallFood(location, stall_name));
    }

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

    private void getStall(String location_name, String stall_name) {
        stall = stallDatabase.stallDAO().getStall(location_name, stall_name);
    }
}