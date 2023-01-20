package com.example.jommakan;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.sqlite.SQLiteException;
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

/**
 * An adapter class that is used to display a list of food in a stall in Stall Page
 */
public class StallFoodItemAdapter extends RecyclerView.Adapter<StallFoodItemAdapter.ViewHolder>{

    /**
     * Provides access to global information about an application environment.
     */
    Context context;

    /**
     * A layout inflater that is used to instantiate layout XML file into its corresponding View objects
     */
    LayoutInflater layoutInflater;

    /**
     * An array list that is used to store food
     */
    ArrayList<Food> food_list;

    /**
     * An array list that is used to store food in a cart item
     */
    ArrayList<CartFood> cart_food_list;

    /**
     * An instance of CartItemDatabase
     */
    CartItemDatabase cartItemDatabase;

    /**
     * A cart item that is used to store information about a cart item
     */
    CartItem cartItem;

    /**
     * Constructor of StallFoodItemAdapter class
     * @param context context
     * @param food_list a list of food
     */
    public StallFoodItemAdapter(Context context, ArrayList<Food> food_list) {
        this.context = context;
        this.food_list = food_list;
        this.layoutInflater = LayoutInflater.from(context);

        // Database connection
        cartItemDatabase = Room.databaseBuilder(context, CartItemDatabase.class, "CartItemDB").allowMainThreadQueries().build();
    }

    /**
     * Inflates a layout file (R.layout.stall_food_item) and creates a new instance of the class ChildCartItemAdapter.ViewHolder
     */
    @NonNull
    @Override
    public StallFoodItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.stall_food_item, parent, false);
        return new StallFoodItemAdapter.ViewHolder(view);
    }

    /**
     * This method binds the data to the views, which is at the position passed as an argument.
     */
    @Override
    public void onBindViewHolder(@NonNull StallFoodItemAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.food_image.setImageResource(food_list.get(position).getImage());
        holder.food_name.setText(food_list.get(position).getName());
        holder.stall_food_item_card_view.setOnClickListener(new View.OnClickListener() {

            /**
             * Pass food object, cart food array list, location name and stall name to Food Page after clicking on it
             * @param v
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
    public int getItemCount() {
        return food_list.size();
    }

    /**
     * This class is used to hold references to the various views that make up an item in a RecyclerView
     */
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

    /**
     * Get the food from a stall that user has added to cart from database
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
