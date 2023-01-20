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

/**
 * An adapter class that is used to display a list of foods in homepage
 */
public class HomeMenuItemAdapter extends RecyclerView.Adapter<HomeMenuItemAdapter.ViewHolder> {

    /**
     * Provides access to global information about an application environment.
     */
    Context context;

    /**
     * An array list that is used to store a list of food
     */
    ArrayList<Food> food_list;

    /**
     * A layout inflater that is used to instantiate layout XML file into its corresponding View objects
     */
    LayoutInflater layoutInflater;

    /**
     * A cart item object that is used to store a cart item
     */
    CartItem cartItem;

    /**
     * An array list that is used to store food in a cart item
     */
    ArrayList<CartFood> cart_food_list;

    /**
     * An instance of cartItemDatabase
     */
    CartItemDatabase cartItemDatabase;

    /**
     * Constructor of HomeMenuItemAdapter
     * @param context context
     * @param food_list a list of food
     */
    public HomeMenuItemAdapter (Context context, ArrayList<Food> food_list) {
        this.context = context;
        this.food_list = food_list;
        this.layoutInflater = LayoutInflater.from(context);

        // Database connection
        cartItemDatabase = Room.databaseBuilder(context, CartItemDatabase.class, "CartItemDB").allowMainThreadQueries().build();
    }

    /**
     * Inflates a layout file (R.layout.home_menu_item) and creates a new instance of the class HomeLocationItemAdapter.ViewHolder
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.home_menu_item, parent, false);
        return new ViewHolder(view);
    }

    /**
     * This method binds the data to the views, which is at the position passed as an argument.
     */
    @Override
    public void onBindViewHolder(@NonNull HomeMenuItemAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.home_menu_card_view_image.setImageResource(food_list.get(position).getImage());
        holder.home_menu_card_view_food_name.setText(food_list.get(position).getName());
        holder.home_menu_card_view_food_stall.setText(food_list.get(position).getStall());
        holder.home_menu_card_view_food_price.setText("RM " + String.format("%.2f", food_list.get(position).getPrice()));

        holder.home_menu_item_card_view.setOnClickListener(new View.OnClickListener() {

            /**
             * Pass food object and its information to Food Fragment using bundle
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
     * Get the size of the food array list
     * @return int
     */
    @Override
    public int getItemCount() {
        return food_list.size();
    }

    /**
     * This class is used to hold references to the various views that make up an item in a RecyclerView
     */
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

    /**
     * Get the food of that stall that user has added to cart from database
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