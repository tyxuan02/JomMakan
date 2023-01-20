package com.example.jommakan;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.google.android.material.imageview.ShapeableImageView;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * An adapter class that is used to display a list of food in menu-food tab
 */
public class MenuFoodItemAdapter extends RecyclerView.Adapter<MenuFoodItemAdapter.ViewHolder> implements Filterable {

    /**
     * Provides access to global information about an application environment.
     */
    Context context;

    /**
     * An array list that is used to store all food get from database
     */
    ArrayList<Food> food_list_all;

    /**
     * An array list that is used to store food search by users
     */
    ArrayList<Food> food_list;

    /**
     * A layout inflater that is used to instantiate layout XML file into its corresponding View objects
     */
    LayoutInflater layoutInflater;

    /**
     * A date that is used to store current time
     */
    Date currentTime;

    /**
     * A date that is used to store stall open time
     */
    Date open;

    /**
     * A date that is used to store stall close time
     */
    Date close;

    /**
     * A cart item object that is used to store information about a cart item
     */
    CartItem cartItem;

    /**
     * An array list that is used to store cart food in a cart item
     */
    ArrayList<CartFood> cart_food_list;

    /**
     * An instance of CartItemDatabase
     */
    CartItemDatabase cartItemDatabase;

    /**
     * Constructor of MenuFoodItemAdapter class
     * @param food_list a list of food
     * @param context context
     */
    public MenuFoodItemAdapter(ArrayList<Food> food_list, Context context) {
        this.food_list = food_list;
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.food_list_all = new ArrayList<>(food_list);

        // Database connection
        cartItemDatabase = Room.databaseBuilder(context, CartItemDatabase.class, "CartItemDB").allowMainThreadQueries().build();
    }

    /**
     * Inflates a layout file (R.layout.menu_food_item) and creates a new instance of the class MenuFoodItemAdapter.ViewHolder
     */
    @NonNull
    @Override
    public MenuFoodItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.menu_food_item, parent, false);
        return new ViewHolder(view);
    }

    /**
     * This method binds the data to the views, which is at the position passed as an argument.
     */
    @Override
    public void onBindViewHolder(@NonNull MenuFoodItemAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.menu_food_card_view_image.setImageResource(food_list.get(position).getImage());
        holder.food_name.setText(food_list.get(position).getName());
        holder.location_name.setText(food_list.get(position).getLocation());
        holder.stall_name.setText(food_list.get(position).getStall());
        holder.operation_time.setText(food_list.get(position).getOpenAndClose().get(0) + " to " + food_list.get(position).getOpenAndClose().get(1));
        holder.food_price.setText("RM " + String.format("%.2f", food_list.get(position).getPrice()));

         // Check whether the stall is open
         // If the stall is open, hide shadow background and not available text
         // If the stall is close, display shadow background and not available text
        checkOpenClose(position);
        if (currentTime.after(open) && currentTime.before(close)) {
            holder.shadow_background.setVisibility(View.INVISIBLE);
            holder.not_available_text.setVisibility(View.INVISIBLE);
        } else {
            holder.shadow_background.setVisibility(View.VISIBLE);
            holder.not_available_text.setVisibility(View.VISIBLE);
        }

        holder.menu_food_item_card_view.setOnClickListener(new View.OnClickListener() {

            /**
             * Pass food object, cart food array list, stall name and location name to Food Fragment using bundle
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
     * This class is used to hold references to the various views that make up an item in a RecyclerView
     */
    public class ViewHolder extends RecyclerView.ViewHolder {

        ShapeableImageView menu_food_card_view_image;
        TextView food_name, location_name, stall_name, operation_time, food_price;
        CardView menu_food_item_card_view;
        View shadow_background;
        TextView not_available_text;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            menu_food_card_view_image = itemView.findViewById(R.id.menu_food_card_view_image);
            food_name = itemView.findViewById(R.id.food_name);
            location_name = itemView.findViewById(R.id.location_name);
            stall_name = itemView.findViewById(R.id.stall_name);
            operation_time = itemView.findViewById(R.id.operation_time);
            food_price = itemView.findViewById(R.id.food_price);
            menu_food_item_card_view = itemView.findViewById(R.id.menu_food_item_card_view);
            shadow_background = itemView.findViewById(R.id.shadow_background);
            not_available_text = itemView.findViewById(R.id.not_available_text);
        }
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
     * Filter out the food that doesn't match with the food that searched by users
     * Therefore, it will only display the food that match with the food that searched by users
     * @return
     */
    @Override
    public Filter getFilter() {
        return filter;
    }

    Filter filter = new Filter() {

        /**
         * Filter out the food that doesn't match with the food that searched by users
         * @param keyword CharSequence
         * @return FilterResults
         */
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

        /**
         * Add the filtered results to food array list
         * @param constraint CharSequence
         * @param results FilterResults
         */
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            food_list.clear();
            food_list.addAll((ArrayList<Food>) results.values);
            notifyDataSetChanged();
        }
    };

    /**
     * Check whether the stall is open
     * @param position int
     */
    private void checkOpenClose(int position) {
        SimpleDateFormat format = new SimpleDateFormat("h.mm a");
        Calendar time = Calendar.getInstance();
        Calendar time1 = Calendar.getInstance();
        Calendar time2 = Calendar.getInstance();
        try {
            time.setTime(format.parse(format.format(new Date())));
            time.add(Calendar.DATE, 1);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        try {
            time1.setTime(format.parse(food_list.get(position).getOpenAndClose().get(0)));
            time2.setTime(format.parse(food_list.get(position).getOpenAndClose().get(1)));
            time1.add(Calendar.DATE, 1);
            time2.add(Calendar.DATE, 1);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        currentTime = time.getTime();
        open = time1.getTime();
        close = time2.getTime();
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
