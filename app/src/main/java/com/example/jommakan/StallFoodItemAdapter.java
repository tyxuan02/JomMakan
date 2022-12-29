package com.example.jommakan;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.imageview.ShapeableImageView;

import java.io.Serializable;
import java.util.ArrayList;

public class StallFoodItemAdapter extends RecyclerView.Adapter<StallFoodItemAdapter.ViewHolder>{

    Context context;
    LayoutInflater layoutInflater;
    ArrayList<Food> food_list;

    public StallFoodItemAdapter(Context context, ArrayList<Food> food_list) {
        this.context = context;
        this.food_list = food_list;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public StallFoodItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.stall_food_item, parent, false);
        return new StallFoodItemAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StallFoodItemAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.food_image.setImageResource(food_list.get(position).getImage());
        holder.food_name.setText(food_list.get(position).getName());
        holder.stall_food_item_card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("food", (Serializable) food_list.get(position));
                Navigation.findNavController(v).navigate(R.id.DestFood, bundle);
            }
        });
    }

    @Override
    public int getItemCount() {
        return food_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        CardView stall_food_item_card_view;
        ShapeableImageView food_image;
        TextView food_name;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            stall_food_item_card_view = itemView.findViewById(R.id.stall_food_item_card_view);
            food_image = itemView.findViewById(R.id.food_image);
            food_name = itemView.findViewById(R.id.food_name);
        }
    }
}
