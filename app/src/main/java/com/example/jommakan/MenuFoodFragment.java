package com.example.jommakan;

import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import java.util.ArrayList;

/**
 * A fragment that is responsible for displaying and managing food tab in menu page
 */
public class MenuFoodFragment extends Fragment {

    /**
     * A recycler view that is used to display a list of food
     */
    RecyclerView menu_food_recycle_view;

    /**
     * An array list that is used to store food
     */
    ArrayList<Food> food_list;

    /**
     * An adapter that is used by menu food recycler view
     */
    MenuFoodItemAdapter menuFoodItemAdapter;

    /**
     * A search bar that allows users to search for food
     */
    SearchView search_bar;

    /**
     * A text view that is used to total of searched results
     */
    TextView result_text_view;

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
        View view =  inflater.inflate(R.layout.fragment_menu_food, container, false);
        return view;
    }

    /**
     * Called immediately after onCreateView has returned, but before any saved state has been restored in to the view
     * @param view The View returned by onCreateView
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous saved state
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        search_bar = view.findViewById(R.id.search_bar);
        result_text_view = view.findViewById(R.id.result_text_view);
        menu_food_recycle_view = view.findViewById(R.id.menu_food_recycle_view);

        // Database connection
        foodDatabase = Room.databaseBuilder(getActivity(), FoodDatabase.class, "FoodDB").allowMainThreadQueries().build();

        // Get all food from database
        food_list = new ArrayList<>();
        getAllFood();

        // Display all food in a vertical list view
        menuFoodItemAdapter = new MenuFoodItemAdapter(food_list, getActivity());
        menu_food_recycle_view.setAdapter(menuFoodItemAdapter);
        menu_food_recycle_view.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
        menu_food_recycle_view.setHasFixedSize(true);
        menu_food_recycle_view.setNestedScrollingEnabled(true);

        // Search bar
        search_bar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            /**
             * This method updates the text of the result_text_view with the total number of results and the query submitted by the user when the user submits a query in the search bar.
             * @param query String
             * @return boolean
             */
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (!search_bar.getQuery().toString().isEmpty()) {
                    result_text_view.setVisibility(View.VISIBLE);
                    result_text_view.setText(Integer.toString(menuFoodItemAdapter.getItemCount()) + " result(s) with " + search_bar.getQuery());
                }
                return false;
            }

            /**
             * This method filters the data of the menuFoodItemAdapter based on the new text entered by the user in the search bar and if the search bar is empty it makes the result_text_view invisible.
             * @param newText String
             * @return boolean
             */
            @Override
            public boolean onQueryTextChange(String newText) {
                menuFoodItemAdapter.getFilter().filter(newText);
                if (search_bar.getQuery().toString().isEmpty()){
                    result_text_view.setVisibility(View.INVISIBLE);
                }
                return false;
            }
        });
    }

    /**
     * Get all food from database
     */
    private void getAllFood() {
        try {
            food_list.addAll(foodDatabase.foodDAO().getAllFood());
        } catch (SQLiteException e) {
            // Handle errors
            e.printStackTrace();
        } finally {
            // Close the database connection
            foodDatabase.close();
        }
    }
}