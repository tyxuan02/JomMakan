package com.example.jommakan;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    ArrayList<Location> location_list;

    // Top 5
    ArrayList<Food> top_5_food_list;
    SliderAdapter sliderAdapter;
    SliderView top_5_image_slider;

    // Home Menu
    ArrayList<Food> menu_food_list;
    RecyclerView home_menu_recycle_view;
    HomeMenuItemAdapter homeMenuItemAdapter;
    ImageButton menu_view_all_button;
    FoodDatabase foodDatabase;

    // Home Location
    RecyclerView home_location_recycle_view;
    ArrayList<Location> menu_location_list;
    HomeLocationItemAdapter homeLocationItemAdapter;
    ImageButton location_view_all_button;
    LocationDatabase locationDatabase;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        return view;
    }

    // Declare and assign views here
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        // Toolbar
        Toolbar toolbar = ((MainActivity) getActivity()).findViewById(R.id.toolbar);
        toolbar.setVisibility(View.VISIBLE);
        TextView toolbar_title = getActivity().findViewById(R.id.toolbar_title);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            toolbar_title.setTextAppearance(R.style.toolbar_text_main);
            toolbar_title.setText("JomMakan");
        }

        // Database connection
        foodDatabase = Room.databaseBuilder(getActivity(), FoodDatabase.class, "FoodDB").allowMainThreadQueries().build();
        locationDatabase = Room.databaseBuilder(getActivity(), LocationDatabase.class, "LocationDB").allowMainThreadQueries().build();

        // Randomly get 5 food from database
        top_5_food_list = new ArrayList<>();
        getTop5Food();
        // Top 5 Slider
        sliderAdapter = new SliderAdapter(top_5_food_list, getActivity());
        top_5_image_slider = view.findViewById(R.id.top_5_image_slider);
        top_5_image_slider.setSliderAdapter(sliderAdapter);
        top_5_image_slider.setIndicatorAnimation(IndicatorAnimationType.WORM);
        top_5_image_slider.setSliderTransformAnimation(SliderAnimations.DEPTHTRANSFORMATION);
        top_5_image_slider.startAutoCycle();

        // Randomly get 3 food from database
        menu_food_list = new ArrayList<>();
        getThreeFood();
        // Home Menu
        homeMenuItemAdapter = new HomeMenuItemAdapter(getActivity(), menu_food_list);
        home_menu_recycle_view = view.findViewById(R.id.home_menu_recycle_view);
        home_menu_recycle_view.setAdapter(homeMenuItemAdapter);
        home_menu_recycle_view.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
        home_menu_recycle_view.setHasFixedSize(true);
        home_menu_recycle_view.setNestedScrollingEnabled(false);

        menu_view_all_button = view.findViewById(R.id.menu_view_all_button);
        menu_view_all_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.DestMenu);
            }
        });

        // Randomly get 3 locations from database
        menu_location_list = new ArrayList<>();
        getThreeLocations();

        homeLocationItemAdapter = new HomeLocationItemAdapter(getActivity(), menu_location_list);
        home_location_recycle_view = view.findViewById(R.id.home_location_recycle_view);
        home_location_recycle_view.setAdapter(homeLocationItemAdapter);
        home_location_recycle_view.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
        home_location_recycle_view.setHasFixedSize(true);
        home_location_recycle_view.setNestedScrollingEnabled(false);

        location_view_all_button = view.findViewById(R.id.location_view_all_button);
        location_view_all_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.DestMenu);
            }
        });
    }

    // Randomly get 5 food from database
    private void getTop5Food() {
        top_5_food_list.addAll(foodDatabase.foodDAO().getTop5Food());
    }

    // Randomly get 3 food from database
    private void getThreeFood() {
        menu_food_list.addAll(foodDatabase.foodDAO().getRandomFood());
    }

    // Randomly get 3 locations from database
    private void getThreeLocations() {
        menu_location_list.addAll(locationDatabase.locationDAO().getRandomLocations());
    }
}

