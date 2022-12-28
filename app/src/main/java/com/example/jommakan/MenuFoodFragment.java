package com.example.jommakan;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MenuFoodFragment extends Fragment {

    RecyclerView menu_food_recycle_view;
    ArrayList<Food> food_list;
    MenuFoodItemAdapter menuFoodItemAdapter;
    SearchView search_bar;
    TextView result_text_view;
    FoodDatabase foodDatabase;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_menu_food, container, false);
        return view;
    }

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

        // Prepare recycle view and adapter to display all food
        menuFoodItemAdapter = new MenuFoodItemAdapter(food_list, getActivity());

        menu_food_recycle_view.setAdapter(menuFoodItemAdapter);
        menu_food_recycle_view.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
        menu_food_recycle_view.setHasFixedSize(true);
        menu_food_recycle_view.setNestedScrollingEnabled(true);

        // Implementation of search bar
        search_bar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (!search_bar.getQuery().toString().isEmpty()) {
                    result_text_view.setVisibility(View.VISIBLE);
                    result_text_view.setText(Integer.toString(menuFoodItemAdapter.getItemCount()) + " result(s) with " + search_bar.getQuery());
                }
                return false;
            }

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

    // Get all food from database
    private void getAllFood() {
        food_list.addAll(foodDatabase.foodDAO().getAllFood());
    }

}