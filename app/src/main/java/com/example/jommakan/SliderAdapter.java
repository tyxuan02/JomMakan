package com.example.jommakan;

import android.content.Context;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.navigation.Navigation;
import androidx.room.Room;

import com.smarteist.autoimageslider.SliderViewAdapter;

import java.io.Serializable;
import java.util.ArrayList;

// Slider Adapter for Top 5 Food on homepage
public class SliderAdapter extends SliderViewAdapter<SliderAdapter.Holder> {

    CartItemDatabase cartItemDatabase;
    ArrayList<Food> food_list;
    Context context;
    CartItem cartItem;
    ArrayList<CartFood> cart_food_list;

    public SliderAdapter (ArrayList<Food> food_list, Context context) {
        this.food_list = food_list;
        this.context = context;

        // Database connection
        cartItemDatabase = Room.databaseBuilder(context, CartItemDatabase.class, "CartItemDB").allowMainThreadQueries().build();
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.top_5_slider_item, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder viewHolder, int position) {
        viewHolder.imageView.setImageResource(food_list.get(position).getImage());
        viewHolder.imageView.setOnClickListener(new View.OnClickListener() {
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
    public int getCount() {
        return food_list.size();
    }

    public class Holder extends SliderViewAdapter.ViewHolder {
        ImageView imageView;

        public Holder(View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.top_5_image_view);
        }
    }

    // Get the food of that stall that user has added to cart from database
    private void getCartFoodList(String location, String stall) {
        try {
            cartItem = cartItemDatabase.cartItemDAO().getCartItem(UserInstance.getUser_email_address(), location, stall);
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