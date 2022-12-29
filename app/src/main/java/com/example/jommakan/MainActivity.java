package com.example.jommakan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentContainerView;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.room.Room;

import android.app.Fragment;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    TextView toolbar_title;
    FragmentContainerView fragment_container;

    NavController navController;
    AppBarConfiguration appBarConfiguration;

    // Database class
    FoodDatabase foodDatabase;
    LocationDatabase locationDatabase;
    StallDatabase stallDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        // TextView toolbar_title = findViewById(R.id.toolbar_title);
        setSupportActionBar(toolbar);
        // toolbar_title.setText(toolbar.getTitle());
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        NavHostFragment host = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        navController = host.getNavController();

        appBarConfiguration = new AppBarConfiguration.Builder(R.id.DestHome, R.id.DestMenu, R.id.DestCart, R.id.DestAccount)
                .build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        setupBottomNavMenu(navController);

        // Database connection
        foodDatabase = Room.databaseBuilder(this, FoodDatabase.class, "FoodDB").allowMainThreadQueries().build();
        locationDatabase = Room.databaseBuilder(this, LocationDatabase.class, "LocationDB").allowMainThreadQueries().build();
        stallDatabase = Room.databaseBuilder(this, StallDatabase.class, "StallDB").allowMainThreadQueries().build();

        // Add necessary data into database
        addFood();
        addLocation();
        addStall();
    }

    // Bottom Navigation Bar
    private void setupBottomNavMenu (NavController navController) {
        BottomNavigationView bottom_navigation_bar = findViewById(R.id.bottom_navigation_bar);
        NavigationUI.setupWithNavController(bottom_navigation_bar, navController, false);
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
        Food food100 = new Food("Nasi Kukus", "Kolej Kediaman Kinabalu", "Restoran Famidah", 6.00, description_list, R.drawable.nasi_goreng_image, open_close_list);

        // For testing purpose
//        Food food7 = new Food("Nasi Goreng a", "Kolej Kediaman Kinabalu", "Restoran Famidah", 5.00, description_list, R.drawable.nasi_goreng_image, open_close_list);
//        Food food8 = new Food("Nasi Goreng Kampung b", "Kolej Kediaman Abdul Rahman", "Restoran Ali", 5.00, description_list, R.drawable.top_5_image, open_close_list1);
//        Food food9 = new Food("Nasi Goreng Cina c", "Kolej Kediaman Pertama", "Restoran Abu", 5.00, description_list, R.drawable.nasi_goreng_image, open_close_list);
//        Food food10 = new Food("Nasi Lemak d", "Faculty of Computer Science and Information Technology", "UM Bumbung", 7.00, description_list, R.drawable.top_5_image, open_close_list1);
//        Food food11 = new Food("Roti Canai e", "Kolej Kediaman Ke-12", "Ali Food Corner", 2.00, description_list, R.drawable.nasi_goreng_image, open_close_list);
//        Food food12 = new Food("Roti Telur f", "Faculty of Engineering", "Restoran Bistro", 3.00, description_list, R.drawable.top_5_image, open_close_list1);
//
//        Food food13 = new Food("Nasi Goreng g", "Kolej Kediaman Kinabalu", "Restoran Famidah", 5.00, description_list, R.drawable.nasi_goreng_image, open_close_list);
//        Food food14= new Food("Nasi Goreng Kampung h", "Kolej Kediaman Abdul Rahman", "Restoran Ali", 5.00, description_list, R.drawable.top_5_image, open_close_list1);
//        Food food15 = new Food("Nasi Goreng Cina i", "Kolej Kediaman Pertama", "Restoran Abu", 5.00, description_list, R.drawable.nasi_goreng_image, open_close_list);
//        Food food16 = new Food("Nasi Lemak j", "Faculty of Computer Science and Information Technology", "UM Bumbung", 7.00, description_list, R.drawable.top_5_image, open_close_list1);
//        Food food17 = new Food("Roti Canai k", "Kolej Kediaman Ke-12", "Ali Food Corner", 2.00, description_list, R.drawable.nasi_goreng_image, open_close_list);
//        Food food18 = new Food("Roti Telur l", "Faculty of Engineering", "Restoran Bistro", 3.00, description_list, R.drawable.top_5_image, open_close_list1);
//
//        Food food19 = new Food("Nasi Goreng m", "Kolej Kediaman Kinabalu", "Restoran Famidah", 5.00, description_list, R.drawable.nasi_goreng_image, open_close_list);
//        Food food20= new Food("Nasi Goreng Kampung n", "Kolej Kediaman Abdul Rahman", "Restoran Ali", 5.00, description_list, R.drawable.top_5_image, open_close_list1);
//        Food food21 = new Food("Nasi Goreng Cina o", "Kolej Kediaman Pertama", "Restoran Abu", 5.00, description_list, R.drawable.nasi_goreng_image, open_close_list);
//        Food food22 = new Food("Nasi Lemak p", "Faculty of Computer Science and Information Technology", "UM Bumbung", 7.00, description_list, R.drawable.top_5_image, open_close_list1);
//        Food food23 = new Food("Roti Canai q", "Kolej Kediaman Ke-12", "Ali Food Corner", 2.00, description_list, R.drawable.nasi_goreng_image, open_close_list);
//        Food food24 = new Food("Roti Telur r", "Faculty of Engineering", "Restoran Bistro", 3.00, description_list, R.drawable.top_5_image, open_close_list1);

        foodDatabase.foodDAO().insertAll(food1, food2, food3, food4, food5, food6, food100);
        // food7, food8, food9, food10, food11, food12, food12, food14, food15, food16, food17, food18, food19, food20, food21, food22, food23, food24);
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

    // Add stalls into database
    private void addStall() {
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

        ArrayList<String> food_name_list = new ArrayList<>();
        food_name_list.add("Nasi Goreng");
        food_name_list.add("Nasi Goreng Kampung");
        food_name_list.add("Nasi Goreng Cina");
        food_name_list.add("Nasi Lemak");
        food_name_list.add("Roti Canai");
        food_name_list.add("Roti Telur");

        Stall stall1 = new Stall("Restoran Famidah", "Kolej Kediaman Kinabalu", food_name_list, "Good for gathering", R.drawable.restoran_famidah_image, open_close_list);
        Stall stall2 = new Stall("Restoran Bistro", "Kolej Kediaman Kinabalu", food_name_list, "Good for gathering", R.drawable.restoran_famidah_image, open_close_list1);
        Stall stall3 = new Stall("Restoran Ali", "Kolej Kediaman Kinabalu", food_name_list, "Good for gathering", R.drawable.restoran_famidah_image, open_close_list);
        Stall stall4 = new Stall("Restoran Abu", "Kolej Kediaman Kinabalu", food_name_list, "Good for gathering", R.drawable.restoran_famidah_image, open_close_list1);
        Stall stall5 = new Stall("Ali Food Corner", "Faculty of Computer Science and Information Technology", food_name_list, "Nerd gathering place", R.drawable.restoran_famidah_image, open_close_list);
        Stall stall6 = new Stall("UM Bumbung", "Faculty of Computer Science and Information Technology", food_name_list, "Nerd gathering place", R.drawable.restoran_famidah_image, open_close_list1);

        stallDatabase.stallDAO().insertAll(stall1, stall2, stall3, stall4, stall5, stall6);
    }
}