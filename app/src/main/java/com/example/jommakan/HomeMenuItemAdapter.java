package com.example.jommakan;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

// Adapter for menu on homepage
public class HomeMenuItemAdapter extends RecyclerView.Adapter<HomeMenuItemAdapter.ViewHolder> {

    Context context;
    ArrayList<Food> food_list;

    public HomeMenuItemAdapter (Context context, ArrayList<Food> food_list) {
        this.context = context;
        this.food_list = food_list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_menu_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeMenuItemAdapter.ViewHolder holder, int position) {
        holder.home_menu_card_view_image.setImageResource(food_list.get(position).getImage());
        holder.home_menu_card_view_food_name.setText(food_list.get(position).getName());
        holder.home_menu_card_view_food_stall.setText(food_list.get(position).getStall());
        holder.home_menu_card_view_food_price.setText("RM " + String.format("%.2f", food_list.get(position).getPrice()));
    }

    @Override
    public int getItemCount() {
        return food_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView home_menu_card_view_image;
        TextView home_menu_card_view_food_name;
        TextView home_menu_card_view_food_stall;
        TextView home_menu_card_view_food_price;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            home_menu_card_view_image = itemView.findViewById(R.id.home_menu_card_view_image);
            home_menu_card_view_food_name = itemView.findViewById(R.id.home_menu_card_view_food_name);
            home_menu_card_view_food_stall = itemView.findViewById(R.id.home_menu_card_view_food_stall);
            home_menu_card_view_food_price = itemView.findViewById(R.id.home_menu_card_view_food_price);
        }
    }
}