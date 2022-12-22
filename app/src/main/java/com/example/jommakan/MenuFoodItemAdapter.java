package com.example.jommakan;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.imageview.ShapeableImageView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class MenuFoodItemAdapter extends RecyclerView.Adapter<MenuFoodItemAdapter.ViewHolder> implements Filterable {

    Context context;
    ArrayList<Food> food_list_all;
    ArrayList<Food> food_list;
    LayoutInflater layoutInflater;

    public MenuFoodItemAdapter(ArrayList<Food> food_list, Context context) {
        this.food_list = food_list;
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.food_list_all = new ArrayList<>(food_list);
    }

    @NonNull
    @Override
    public MenuFoodItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.menu_food_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MenuFoodItemAdapter.ViewHolder holder, int position) {
        SimpleDateFormat format = new SimpleDateFormat("hh.mm a");

        holder.menu_food_card_view_image.setImageResource(R.drawable.nasi_goreng_image);
        holder.food_name.setText(food_list.get(position).getName());
        holder.location_name.setText(food_list.get(position).getLocation());
        holder.stall_name.setText(food_list.get(position).getStall());
        holder.operation_time.setText(format.format(food_list.get(position).getOpenAndClose()[0]) + " to " + format.format(food_list.get(position).getOpenAndClose()[1]));
        holder.food_price.setText("RM " + String.format("%.2f", food_list.get(position).getPrice()));
    }

    @Override
    public int getItemCount() {
        return food_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ShapeableImageView menu_food_card_view_image;
        TextView food_name, location_name, stall_name, operation_time, food_price;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            menu_food_card_view_image = itemView.findViewById(R.id.menu_food_card_view_image);
            food_name = itemView.findViewById(R.id.food_name);
            location_name = itemView.findViewById(R.id.location_name);
            stall_name = itemView.findViewById(R.id.stall_name);
            operation_time = itemView.findViewById(R.id.operation_time);
            food_price = itemView.findViewById(R.id.food_price);
        }
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence keyword) {
            ArrayList<Food> filter_list = new ArrayList<>();
            if (keyword.toString().isEmpty()) {
                filter_list.addAll(food_list_all);
            } else {
                for (Food food: food_list_all) {
                    if (food.getName().toLowerCase().contains(keyword.toString().toLowerCase())) {
                        filter_list.add(food);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filter_list;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            food_list.clear();
            food_list.addAll((ArrayList<Food>) results.values);
            notifyDataSetChanged();
        }
    };
}
