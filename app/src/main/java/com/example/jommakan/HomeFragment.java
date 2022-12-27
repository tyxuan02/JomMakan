package com.example.jommakan;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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

    ArrayList<Food> food_list;
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
        TextView toolbar_title = getActivity().findViewById(R.id.toolbar_title);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            toolbar_title.setTextAppearance(R.style.toolbar_text_main);
            toolbar_title.setText("JomMakan");
        }

        // Database connection
        foodDatabase = Room.databaseBuilder(getActivity(), FoodDatabase.class, "FoodDB").allowMainThreadQueries().build();
        locationDatabase = Room.databaseBuilder(getActivity(), LocationDatabase.class, "LocaitonDB").allowMainThreadQueries().build();

        // Add all food into database
        food_list = new ArrayList<>();
        addFood();

        // Randomly get 5 food from database
        top_5_food_list = new ArrayList<>();
        getTop5Food();
        // Top 5 Slider
        ArrayList<Integer> top_5_image_list = new ArrayList<>();
        top_5_image_list.add(top_5_food_list.get(0).getImage());
        top_5_image_list.add(top_5_food_list.get(1).getImage());
        top_5_image_list.add(top_5_food_list.get(2).getImage());
        top_5_image_list.add(top_5_food_list.get(3).getImage());
        top_5_image_list.add(top_5_food_list.get(4).getImage());
        sliderAdapter = new SliderAdapter(top_5_image_list);
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


        // Add all locations into database
        location_list = new ArrayList<>();
        addLocation();

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

    // Add food into database
    private void addFood() {
        String open = "";
        String close = "";
        open = "10.00 AM";
        close = "10.00 PM";

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

        Food food1 = new Food("Nasi Goreng", "Kolej Kediaman Kinabalu", "Restoran Famidah", 5.00, description_list, R.drawable.nasi_goreng_image, open_close_list);
        Food food2 = new Food("Nasi Goreng Kampung", "Kolej Kediaman Abdul Rahman", "Restoran Ali", 5.00, description_list, R.drawable.top_5_image, open_close_list1);
        Food food3 = new Food("Nasi Goreng Cina", "Kolej Kediaman Pertama", "Restoran Abu", 5.00, description_list, R.drawable.nasi_goreng_image, open_close_list);
        Food food4 = new Food("Nasi Lemak", "Faculty of Computer Science and Information Technology", "UM Bumbung", 7.00, description_list, R.drawable.top_5_image, open_close_list1);
        Food food5 = new Food("Roti Canai", "Kolej Kediaman Ke-12", "Ali Food Corner", 2.00, description_list, R.drawable.nasi_goreng_image, open_close_list);
        Food food6 = new Food("Roti Telur", "Faculty of Engineering", "Restoran Bistro", 3.00, description_list, R.drawable.top_5_image, open_close_list1);
        foodDatabase.foodDAO().insertAll(food1, food2, food3, food4, food5, food6);
    }

    // Get all food from database
    private void getAllFood() {
        food_list.addAll(foodDatabase.foodDAO().getAllFood());
    }

    // Randomly get 5 food from database
    private void getTop5Food() {
        top_5_food_list.addAll(foodDatabase.foodDAO().getTop5Food());
    }

    // Randomly get 3 food from database
    private void getThreeFood() {
        menu_food_list.addAll(foodDatabase.foodDAO().getRandomFood());
    }

    // Add locations into database
    private void addLocation() {
        String open = "";
        String close = "";
        open = "10.00 AM";
        close = "10.00 PM";

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

        ArrayList<String> stall_name_list = new ArrayList<>();
        stall_name_list.add("Restoran Famidah");
        stall_name_list.add("Restoran Bistro");
        stall_name_list.add("Restoran Ali");
        stall_name_list.add("Restoran Abu");
        stall_name_list.add("Ali Food Corner");
        stall_name_list.add("UM Bumbung");
        Location location1 = new Location("Kolej Kediaman Kinabalu", R.drawable.fsktm_image, stall_name_list);
        Location location2 = new Location("Kolej Kediaman Abdul Rahman", R.drawable.fsktm_image, stall_name_list);
        Location location3 = new Location("Kolej Kediaman Pertama", R.drawable.fsktm_image, stall_name_list);
        Location location4 = new Location("Kolej Kediaman Ke-12", R.drawable.fsktm_image, stall_name_list);
        Location location5 = new Location("Faculty of Computer Science and Information Technology", R.drawable.fsktm_image, stall_name_list);
        Location location6 = new Location("Faculty of Engineering", R.drawable.fsktm_image, stall_name_list);
        locationDatabase.locationDAO().insertAll(location1, location2, location3, location4, location5, location6);
    }

    // Get all locations from database
    private void getAllLocation() {
        location_list.addAll(locationDatabase.locationDAO().getAllLocations());
    }

    // Randomly get 3 locations from database
    private void getThreeLocations() {
        menu_location_list.addAll(locationDatabase.locationDAO().getRandomLocations());
    }
}

