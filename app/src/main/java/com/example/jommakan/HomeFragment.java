package com.example.jommakan;

import android.database.sqlite.SQLiteException;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.google.android.material.imageview.ShapeableImageView;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

/**
 * A fragment that is responsible for displaying and managing homepage information
 */
public class HomeFragment extends Fragment {

    /**
     * An array list that is used to store 5 food
     */
    ArrayList<Food> top_5_food_list;

    /**
     * An slider adapter that is used by top 5 image slider
     */
    SliderAdapter sliderAdapter;

    /**
     * An slider view that is used to display 5 food
     */
    SliderView top_5_image_slider;

    /**
     * An array list that is used to store food
     */
    ArrayList<Food> menu_food_list;

    /**
     * A recycler view that is used to display a list of food
     */
    RecyclerView home_menu_recycle_view;

    /**
     * An adapter that is used by home menu recycler view
     */
    HomeMenuItemAdapter homeMenuItemAdapter;

    /**
     * An image button that will navigate users to menu page after clicking on it
     */
    ImageButton menu_view_all_button;

    /**
     * An instance of the class foodDatabase
     */
    FoodDatabase foodDatabase;

    /**
     * A recycler view that is used to display a list of locations
     */
    RecyclerView home_location_recycle_view;

    /**
     * An array list that is used to store locations
     */
    ArrayList<Location> menu_location_list;

    /**
     * An adapter that is used by home location recycler view
     */
    HomeLocationItemAdapter homeLocationItemAdapter;

    /**
     * An image button that will navigate users to menu page after clicking on it
     */
    ImageButton location_view_all_button;

    /**
     * An instance of the class LocationDatabase
     */
    LocationDatabase locationDatabase;

    /**
     * A shape-able image view that is used to display random food picker
     */
    ShapeableImageView random_food_picker;

    /**
     * A food object that is used to store a random food
     */
    Food random_food;

    /**
     * A cart item object that is used to store cart item
     */
    CartItem cartItem;

    /**
     * An instance of the class CartItemDatabase
     */
    CartItemDatabase cartItemDatabase;

    /**
     * An array list that is used to store cart food
     */
    ArrayList<CartFood> cart_food_list;

    /**
     * A text view that is used to display username
     */
    TextView welcome_text_with_name;

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
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        return view;
    }

    /**
     * Called immediately after onCreateView has returned, but before any saved state has been restored in to the view
     * @param view The View returned by onCreateView
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous saved state
     */
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

        // Display username
        welcome_text_with_name = view.findViewById(R.id.welcome_text_with_name);
        welcome_text_with_name.setText(UserInstance.getUsername());

        // Database connection
        foodDatabase = Room.databaseBuilder(getActivity(), FoodDatabase.class, "FoodDB").allowMainThreadQueries().build();
        locationDatabase = Room.databaseBuilder(getActivity(), LocationDatabase.class, "LocationDB").allowMainThreadQueries().build();
        cartItemDatabase = Room.databaseBuilder(getActivity(), CartItemDatabase.class, "CartItemDB").allowMainThreadQueries().build();

        top_5_food_list = new ArrayList<>();
        menu_food_list = new ArrayList<>();
        menu_location_list = new ArrayList<>();

        try {
            // Randomly get 5 food from database
            getTop5Food();

            // Randomly get 3 food from database
            getThreeFood();

            // Randomly get 3 locations from database
            getThreeLocations();

            // Get random food from database
            random_food = foodDatabase.foodDAO().getAllFood().get(new Random().nextInt(foodDatabase.foodDAO().getAllFood().size()));
        } catch (SQLiteException e) {
            // Handle errors
            e.printStackTrace();
        } finally {
            // Close the database connection
            foodDatabase.close();
            locationDatabase.close();
            cartItemDatabase.close();
        }

        // Display 5 food in the image slider
        sliderAdapter = new SliderAdapter(top_5_food_list, getActivity());
        top_5_image_slider = view.findViewById(R.id.top_5_image_slider);
        top_5_image_slider.setSliderAdapter(sliderAdapter);
        top_5_image_slider.setIndicatorAnimation(IndicatorAnimationType.WORM);
        top_5_image_slider.setSliderTransformAnimation(SliderAnimations.DEPTHTRANSFORMATION);
        top_5_image_slider.startAutoCycle();

        // Display 3 food in a horizontal list view
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

        // Display 5 locations in a horizontal list view
        homeLocationItemAdapter = new HomeLocationItemAdapter(getActivity(), menu_location_list);
        home_location_recycle_view = view.findViewById(R.id.home_location_recycle_view);
        home_location_recycle_view.setAdapter(homeLocationItemAdapter);
        home_location_recycle_view.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
        home_location_recycle_view.setHasFixedSize(true);
        home_location_recycle_view.setNestedScrollingEnabled(false);

        location_view_all_button = view.findViewById(R.id.location_view_all_button);
        location_view_all_button.setOnClickListener(new View.OnClickListener() {
            /**
             * Direct user to menu page after clicking on it
             * @param v view
             */
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.DestMenu);
            }
        });

        // Random Food Picker
        random_food_picker = view.findViewById(R.id.random_food_picker);
        random_food_picker.setOnClickListener(new View.OnClickListener() {

            /**
             * Pass food object and its information to Food Fragment using bundle
             * @param v view
             */
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("food", (Serializable) random_food);
                getCartFoodList(random_food.getLocation(), random_food.getStall());
                bundle.putSerializable("cart_food_list", (Serializable) cart_food_list);
                bundle.putString("stall", random_food.getStall());
                bundle.putString("location", random_food.getLocation());
                Navigation.findNavController(v).navigate(R.id.DestFood, bundle);
            }
        });
    }

    /**
     * Randomly get 5 food from database
     */
    private void getTop5Food() {
        top_5_food_list.addAll(foodDatabase.foodDAO().getTop5Food());
    }

    /**
     * Randomly get 3 food from database
     */
    private void getThreeFood() {
        menu_food_list.addAll(foodDatabase.foodDAO().getThreeFood());
    }

    /**
     * Randomly get 3 locations from database
     */
    private void getThreeLocations() {
        menu_location_list.addAll(locationDatabase.locationDAO().getRandomLocations());
    }

    /**
     * Get the food of that stall that user has added to cart from database
     * @param location location name
     * @param stall stall name
     */
    private void getCartFoodList(String location, String stall) {
        try {
            cartItem = cartItemDatabase.cartItemDAO().getCartItem(UserInstance.getUser_email_address(), location, stall);
        } catch (SQLiteException e) {
            // Handle errors
            e.printStackTrace();
        } finally {
            // Close connection
            cartItemDatabase.close();
        }

        if (cartItem == null) {
            cart_food_list = new ArrayList<>();
        } else {
            cart_food_list = cartItem.getCart_food_list();
        }
    }
}

