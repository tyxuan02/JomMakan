package com.example.jommakan;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import java.io.Serializable;
import java.util.ArrayList;

// Adapter for menu on homepage
public class HomeMenuItemAdapter extends RecyclerView.Adapter<HomeMenuItemAdapter.ViewHolder> {

    Context context;
    ArrayList<Food> food_list;
    LayoutInflater layoutInflater;
    CartItem cartItem;
    ArrayList<CartFood> cart_food_list;
    CartItemDatabase cartItemDatabase;

    public HomeMenuItemAdapter (Context context, ArrayList<Food> food_list) {
        this.context = context;
        this.food_list = food_list;
        this.layoutInflater = LayoutInflater.from(context);

        // Database connection
        cartItemDatabase = Room.databaseBuilder(context, CartItemDatabase.class, "CartItemDB").allowMainThreadQueries().build();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.home_menu_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeMenuItemAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.home_menu_card_view_image.setImageResource(food_list.get(position).getImage());
        holder.home_menu_card_view_food_name.setText(food_list.get(position).getName());
        holder.home_menu_card_view_food_stall.setText(food_list.get(position).getStall());
        holder.home_menu_card_view_food_price.setText("RM " + String.format("%.2f", food_list.get(position).getPrice()));

        holder.home_menu_item_card_view.setOnClickListener(new View.OnClickListener() {
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

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView home_menu_card_view_image;
        TextView home_menu_card_view_food_name;
        TextView home_menu_card_view_food_stall;
        TextView home_menu_card_view_food_price;
        CardView home_menu_item_card_view;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            home_menu_card_view_image = itemView.findViewById(R.id.home_menu_card_view_image);
            home_menu_card_view_food_name = itemView.findViewById(R.id.home_menu_card_view_food_name);
            home_menu_card_view_food_stall = itemView.findViewById(R.id.home_menu_card_view_food_stall);
            home_menu_card_view_food_price = itemView.findViewById(R.id.home_menu_card_view_food_price);
            home_menu_item_card_view = itemView.findViewById(R.id.home_menu_item_card_view);
        }
    }

    // Get the food of that stall that user has added to cart from database
    private void getCartFoodList(String location, String stall) {
        try {
            cartItem = cartItemDatabase.cartItemDAO().getCartItem(UserHolder.getUser_email_address(), location, stall);
        } catch (SQLiteException e) {
            // Handle errors
            e.printStackTrace();
        } finally {
            // Close the database connection
            cartItemDatabase.close();
        }

        if (cartItem == null) {
            cart_food_list = new ArrayList<>();
        } else {
            cart_food_list = cartItem.getCart_food_list();
        }
    }
}