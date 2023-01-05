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
import androidx.room.Room;

import com.google.android.material.imageview.ShapeableImageView;

import java.io.Serializable;
import java.util.ArrayList;

public class StallFoodItemAdapter extends RecyclerView.Adapter<StallFoodItemAdapter.ViewHolder>{

    Context context;
    LayoutInflater layoutInflater;
    ArrayList<Food> food_list;
    ArrayList<CartFood> cart_food_list;
    CartItemDatabase cartItemDatabase;
    CartItem cartItem;

    public StallFoodItemAdapter(Context context, ArrayList<Food> food_list) {
        this.context = context;
        this.food_list = food_list;
        this.layoutInflater = LayoutInflater.from(context);

        // Database connection
        cartItemDatabase = Room.databaseBuilder(context, CartItemDatabase.class, "CartItemDB").allowMainThreadQueries().build();
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
                // Pass data between fragments using bundle
                Bundle bundle = new Bundle();
                bundle.putSerializable("food", (Serializable) food_list.get(position));
                getCartFoodList(food_list.get(position).getLocation(), food_list.get(position).getStall());
                bundle.putSerializable("cart_food_list", (Serializable) cart_food_list);
                bundle.putString("stall", food_list.get(position).getStall());
                bundle.putString("location", food_list.get(position).getLocation());
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

    // Get the food of that stall that user has added to cart from database
    private void getCartFoodList(String location, String stall) {
        cartItem = cartItemDatabase.cartItemDAO().getCartItem(UserInstance.getUser_email_address(), location, stall);
        if (cartItem == null) {
            cart_food_list = new ArrayList<>();
        } else {
            cart_food_list = cartItem.getCart_food_list();
        }
    }
}
