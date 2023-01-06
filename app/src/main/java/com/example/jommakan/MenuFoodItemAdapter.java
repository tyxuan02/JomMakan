package com.example.jommakan;

import android.annotation.SuppressLint;
import android.content.Context;
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
import java.util.Calendar;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MenuFoodItemAdapter extends RecyclerView.Adapter<MenuFoodItemAdapter.ViewHolder> implements Filterable {

    Context context;
    ArrayList<Food> food_list_all;
    ArrayList<Food> food_list;
    LayoutInflater layoutInflater;
    Date currentTime, open, close;
    CartItem cartItem;
    ArrayList<CartFood> cart_food_list;
    CartItemDatabase cartItemDatabase;

    public MenuFoodItemAdapter(ArrayList<Food> food_list, Context context) {
        this.food_list = food_list;
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.food_list_all = new ArrayList<>(food_list);

        // Database connection
        cartItemDatabase = Room.databaseBuilder(context, CartItemDatabase.class, "CartItemDB").allowMainThreadQueries().build();
    }

    @NonNull
    @Override
    public MenuFoodItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.menu_food_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MenuFoodItemAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.menu_food_card_view_image.setImageResource(food_list.get(position).getImage());
        holder.food_name.setText(food_list.get(position).getName());
        holder.location_name.setText(food_list.get(position).getLocation());
        holder.stall_name.setText(food_list.get(position).getStall());
        holder.operation_time.setText(food_list.get(position).getOpenAndClose().get(0) + " to " + food_list.get(position).getOpenAndClose().get(1));
        holder.food_price.setText("RM " + String.format("%.2f", food_list.get(position).getPrice()));

        checkOpenClose(position);
        if (currentTime.after(open) && currentTime.before(close)) {
            holder.shadow_background.setVisibility(View.INVISIBLE);
            holder.not_available_text.setVisibility(View.INVISIBLE);
        } else {
            holder.shadow_background.setVisibility(View.VISIBLE);
            holder.not_available_text.setVisibility(View.VISIBLE);
        }

        holder.menu_food_item_card_view.setOnClickListener(new View.OnClickListener() {
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

    @Override
    public int getItemCount() {
        return food_list.size();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    Filter filter = new Filter() {
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

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            food_list.clear();
            food_list.addAll((ArrayList<Food>) results.values);
            notifyDataSetChanged();
        }
    };

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
