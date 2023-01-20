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

/**
 * An adapter class that is used to display 5 food in slider view in Homepage
 */
public class SliderAdapter extends SliderViewAdapter<SliderAdapter.Holder> {

    /**
     * An instance of CartItemDatabase
     */
    CartItemDatabase cartItemDatabase;

    /**
     * An array list that is used to store 5 food
     */
    ArrayList<Food> food_list;

    /**
     * Provides access to global information about an application environment.
     */
    Context context;

    /**
     * A cart item object that is used to store information about a cart item
     */
    CartItem cartItem;

    /**
     * An array list that is used to store cart food
     */
    ArrayList<CartFood> cart_food_list;

    /**
     * Constructor of SliderAdapter class
     * @param food_list a list of food
     * @param context context
     */
    public SliderAdapter (ArrayList<Food> food_list, Context context) {
        this.food_list = food_list;
        this.context = context;

        // Database connection
        cartItemDatabase = Room.databaseBuilder(context, CartItemDatabase.class, "CartItemDB").allowMainThreadQueries().build();
    }

    /**
     * Inflates a layout file (R.layout.top_5_slider_item) and creates a new instance of the class SliderAdapter.ViewHolder
     */
    @Override
    public Holder onCreateViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.top_5_slider_item, parent, false);
        return new Holder(view);
    }

    /**
     * This method binds the data to the views, which is at the position passed as an argument.
     */
    @Override
    public void onBindViewHolder(Holder viewHolder, int position) {
        viewHolder.imageView.setImageResource(food_list.get(position).getImage());
        viewHolder.imageView.setOnClickListener(new View.OnClickListener() {

            /**
             * Pass food object, cart food array list, location name and stall name to Food Page after clicking on it
             * @param v view
             */
            @Override
            public void onClick(View v) {
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

    /**
     * Get the size of food array list
     * @return int
     */
    @Override
    public int getCount() {
        return food_list.size();
    }

    /**
     * This class is used to hold references to the various views that make up an item in a SliderView
     */
    public class Holder extends SliderViewAdapter.ViewHolder {
        ImageView imageView;

        public Holder(View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.top_5_image_view);
        }
    }

    /**
     * Get the food from a stall that users have added to cart from database
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