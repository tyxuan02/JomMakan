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
//import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    TextView toolbar_title;
    FragmentContainerView fragment_container;

    NavController navController;
    AppBarConfiguration appBarConfiguration;

    // Database class
    FoodDatabase foodDatabase;
    LocationDatabase locationDatabase;
    StallDatabase stallDatabase;
    CartItemDatabase cartItemDatabase;

    ArrayList<CartFood> cart_food_list;

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
//        addCartItem();
//        getCartItems();
//        updateCartItems();
//        delete();
//        deleteWhole();
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
        Food food2 = new Food("Nasi Goreng Kampung", "Kolej Kediaman Abdul Rahman", "Restoran Bistro", 5.00, description_list, R.drawable.top_5_image, open_close_list1);
        Food food3 = new Food("Nasi Goreng Cina", "Kolej Kediaman Kinabalu", "Restoran Famidah", 5.00, description_list, R.drawable.nasi_goreng_image, open_close_list);
        Food food4 = new Food("Nasi Lemak", "Kolej Kediaman Abdul Rahman", "Restoran Bistro", 7.00, description_list, R.drawable.top_5_image, open_close_list1);
        Food food5 = new Food("Roti Canai", "Kolej Kediaman Kinabalu", "Restoran Famidah", 2.00, description_list, R.drawable.nasi_goreng_image, open_close_list);
        Food food6 = new Food("Roti Telur", "Kolej Kediaman Abdul Rahman", "Restoran Bistro", 3.00, description_list, R.drawable.top_5_image, open_close_list1);
        Food food100 = new Food("Nasi Kukus", "Kolej Kediaman Kinabalu", "Restoran Famidah", 6.00, description_list, R.drawable.nasi_goreng_image, open_close_list);

        foodDatabase.foodDAO().insertAll(food1, food2, food3, food4, food5, food6, food100);
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

        Location location1 = new Location("Kolej Kediaman Kinabalu", R.drawable.fsktm_image, new ArrayList<>(Arrays.asList("Restoran Famidah")));
        Location location2 = new Location("Kolej Kediaman Abdul Rahman", R.drawable.fsktm_image, new ArrayList<>(Arrays.asList("Restoran Bistro")));
        Location location3 = new Location("Kolej Kediaman Pertama", R.drawable.fsktm_image, new ArrayList<>());
        Location location4 = new Location("Kolej Kediaman Ke-12", R.drawable.fsktm_image, new ArrayList<>());
        Location location5 = new Location("Faculty of Computer Science and Information Technology", R.drawable.fsktm_image, new ArrayList<>());
        Location location6 = new Location("Faculty of Engineering", R.drawable.fsktm_image, new ArrayList<>());
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

        Stall stall1 = new Stall("Restoran Famidah", "Kolej Kediaman Kinabalu", new ArrayList<>(Arrays.asList("Nasi Goreng", "Nasi Goreng Cina", "Roti Canai", "Nasi Kukus")), "Good for gathering", R.drawable.restoran_famidah_image, open_close_list);
        Stall stall2 = new Stall("Restoran Bistro", "Kolej Kediaman Abdul Rahman", new ArrayList<>(Arrays.asList("Nasi Goreng Kampung", "Nasi Lemak", "Telur")), "Good for gathering", R.drawable.restoran_famidah_image, open_close_list1);


        stallDatabase.stallDAO().insertAll(stall1, stall2);
    }

//    private void addCartItem() {
//        String email = "sdjkfh";
//        String stall = "Restoran";
//        String location = "KK8";
//        ArrayList<CartFood> cartFoodArrayList = new ArrayList<>();
//        CartFood cartFood1 = new CartFood("nasi", 1, 2.00);
//        CartFood cartFood2 = new CartFood("nasi1", 2, 3.00);
//        CartFood cartFood3 = new CartFood("nasi2", 3, 5.00);
//        cartFoodArrayList.add(cartFood1);
//        cartFoodArrayList.add(cartFood2);
//        cartFoodArrayList.add(cartFood3);
//
//        cartItemDatabase.cartItemDAO().insertCartItem(new CartItem(email, location, stall, cartFoodArrayList));
//        cartItemDatabase.cartItemDAO().insertCartItem(new CartItem("email", location, stall, cartFoodArrayList));
//    }
//

//
//    private void updateCartItems() {
//        CartItem cartItem = cartItemDatabase.cartItemDAO().getCartItem("sdjkfh", "KK8", "Restoran");
//        String food = "nasi2";
//        for (int i = 0; i < cart_food_list.size(); i++) {
//            if (cart_food_list.get(i).getFood_name().equals(food)) {
//                cart_food_list.get(i).setQuantity(222);
//                break;
//            }
//        }
//        cartItemDatabase.cartItemDAO().updateCartItem("sdjkfh", "KK8", "Restoran", cart_food_list);
//    }
//
//    private void delete() {
//        CartItem cartItem = cartItemDatabase.cartItemDAO().getCartItem("sdjkfh", "KK8", "Restoran");
//        cartItem.getCart_food_list().remove(1);
//        cartItemDatabase.cartItemDAO().updateCartItem("sdjkfh", "KK8", "Restoran", cartItem.getCart_food_list());
//    }
//
//    private void deleteWhole() {
//        cartItemDatabase.cartItemDAO().deleteCartItem("email", "KK8", "Restoran");
//    }
}
