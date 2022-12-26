package com.example.jommakan;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

        SimpleDateFormat format = new SimpleDateFormat("hh.mm a");
        String open = "";
        String close = "";
        String close1 = "";
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

        food_list = new ArrayList<>();
        food_list.add(new Food("Nasi Goreng Kampung", "Kolej Kediaman Kinabalu", "Restoran Famidah", 6.00, description_list, R.drawable.nasi_goreng_image, open_close_list));
        food_list.add(new Food("Nasi Goreng", "Faculty of Computer Science and Information Technology", "Restoran Famidah", 8.00, description_list, R.drawable.nasi_goreng_image, open_close_list));
        food_list.add(new Food("Nasi Goreng Ayam", "Kolej Kediaman Kinabalu", "Restoran Famidah", 10.00, description_list, R.drawable.nasi_goreng_image, open_close_list1));
        food_list.add(new Food("Roti Canai", "Kolej Kediaman Kinabalu", "Restoran Famidah", 6.00, description_list, R.drawable.nasi_goreng_image, open_close_list));
        food_list.add(new Food("Nasi Goreng Cina", "Kolej Kediaman Kinabalu", "Restoran Famidah", 8.00, description_list, R.drawable.nasi_goreng_image, open_close_list));
        food_list.add(new Food("Nasi Kukus", "Kolej Kediaman Kinabalu", "Restoran Famidah", 10.00, description_list, R.drawable.nasi_goreng_image, open_close_list));

        menuFoodItemAdapter = new MenuFoodItemAdapter(food_list, getActivity());

        menu_food_recycle_view.setAdapter(menuFoodItemAdapter);
        menu_food_recycle_view.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
        menu_food_recycle_view.setHasFixedSize(true);
        menu_food_recycle_view.setNestedScrollingEnabled(true);

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

}