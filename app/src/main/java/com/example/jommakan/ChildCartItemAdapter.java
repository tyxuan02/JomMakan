package com.example.jommakan;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.sqlite.SQLiteException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import java.util.ArrayList;

/**
 * An adapter class that is used to display a list of items in a cart item in cart page
 */
public class ChildCartItemAdapter extends RecyclerView.Adapter<ChildCartItemAdapter.MyViewHolder> {

    /**
     * An array list that is used to store a list of food in a cart item
     */
    ArrayList<CartFood> cart_food_list;

    /**
     * A string that is used to store location name
     */
    String location;

    /**
     * A string that is used to store stall name
     */
    String stall;

    /**
     * Provides access to global information about an application environment.
     */
    Context context;

    /**
     * A layout inflater that is used to instantiate layout XML file into its corresponding View objects
     */
    LayoutInflater layoutInflater;

    /**
     * A double that is used to store total price of all food in a cart item
     */
    double sum_of_price;

    /**
     * An instance of the class CartItemDatabase
     */
    CartItemDatabase cartItemDatabase;

    /**
     * Constructor of ChildCartItemAdapter class
     * @param cart_food_list a list of food
     * @param location location name
     * @param stall stall name
     * @param context context
     */
    public ChildCartItemAdapter(ArrayList<CartFood> cart_food_list, String location, String stall, Context context) {
        this.cart_food_list = cart_food_list;
        this.location = location;
        this.stall = stall;
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);

        // Database connection
        cartItemDatabase = Room.databaseBuilder(context, CartItemDatabase.class, "CartItemDB").allowMainThreadQueries().build();
    }

    /**
     * Inflates a layout file (R.layout.cart_food_item) and creates a new instance of the class ChildCartItemAdapter.MyViewHolder
     */
    @NonNull
    @Override
    public ChildCartItemAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.cart_food_item, parent, false);
        return new ChildCartItemAdapter.MyViewHolder(view);
    }

    /**
     * This method binds the data to the views, which is at the position passed as an argument.
     */
    @Override
    public void onBindViewHolder(@NonNull ChildCartItemAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.cart_food_name.setText(cart_food_list.get(position).getFood_name());

        final int[] count = {cart_food_list.get(position).getQuantity()};
        holder.quantity.setText(String.valueOf(count[0]));

        sum_of_price = cart_food_list.get(position).getPrice() * count[0];
        holder.cart_food_price.setText(String.format("%.2f", sum_of_price));

        holder.increment_button.setOnClickListener(new View.OnClickListener() {

            /**
             * Increase the quantity of a food after clicking on it
             * Maximum quantity of a food is 10
             */
            @Override
            public void onClick(View v) {
                if (count[0] < 10) {
                    count[0]++;
                } else {
                    count[0] = 10;
                }
                holder.quantity.setText(String.valueOf(count[0]));
                sum_of_price = count[0] * cart_food_list.get(position).getPrice();
                holder.cart_food_price.setText(String.format("%.2f", sum_of_price));
                cart_food_list.get(position).setQuantity(count[0]);
            }
        });

        holder.decrement_button.setOnClickListener(new View.OnClickListener() {

            /**
             * Decrease the quantity of a food after clicking on it
             * Minimum quantity of a food is 0
             */
            @Override
            public void onClick(View v) {
                if (count[0] <= 0) {
                    count[0] = 0;
                } else {
                    count[0] = count[0] - 1;
                }
                holder.quantity.setText(String.valueOf(count[0]));
                sum_of_price = count[0] * cart_food_list.get(position).getPrice();
                holder.cart_food_price.setText(String.format("%.2f", sum_of_price));
                cart_food_list.get(position).setQuantity(count[0]);
            }
        });
    }

    /**
     * Get the size of the cart food array list
     * @return int
     */
    @Override
    public int getItemCount() {
        return cart_food_list.size();
    }

    /**
     * This class is used to hold references to the various views that make up an item in a RecyclerView
     */
    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView cart_food_name;
        TextView decrement_button;
        TextView increment_button;
        TextView quantity;
        TextView cart_food_price;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            cart_food_name = itemView.findViewById(R.id.cart_food_name);
            decrement_button = itemView.findViewById(R.id.decrement_button);
            increment_button = itemView.findViewById(R.id.increment_button);
            quantity = itemView.findViewById(R.id.quantity);
            cart_food_price = itemView.findViewById(R.id.cart_food_price);
        }
    }

    /**
     * Update quantity in the cart food array list
     */
    public void updateQuantity() {
        for (int i = 0; i < cart_food_list.size(); i++) {
            if (cart_food_list.get(i).getQuantity() <= 0) {
                cart_food_list.remove(i);
            }
        }

        // Close connection and handle errors in room database
        try {
            if (cart_food_list.size() <= 0) {
                cartItemDatabase.cartItemDAO().deleteCartItem(UserInstance.getUser_email_address(), location, stall);
            } else {
                cartItemDatabase.cartItemDAO().updateCartItem(UserInstance.getUser_email_address(), location, stall, cart_food_list);
            }
        } catch (SQLiteException e) {
            // Handle errors
            e.printStackTrace();
        }
        notifyDataSetChanged();
    }
}
