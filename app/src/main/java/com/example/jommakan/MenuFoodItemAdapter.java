package com.example.jommakan;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.imageview.ShapeableImageView;

import java.text.ParseException;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MenuFoodItemAdapter extends RecyclerView.Adapter implements Filterable {

    Context context;
    ArrayList<Food> food_list_all;
    ArrayList<Food> food_list;
    LayoutInflater layoutInflater;

    public MenuFoodItemAdapter(ArrayList<Food> food_list, Context context) {
        this.food_list = food_list;
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.food_list_all = new ArrayList<>(food_list);
    }

    public int getItemViewType(int position) {
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
            time1.setTime(format.parse(food_list.get(position).getOpenAndClose()[0]));
            time2.setTime(format.parse(food_list.get(position).getOpenAndClose()[1]));
            time1.add(Calendar.DATE, 1);
            time2.add(Calendar.DATE, 1);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Date currentTime = time.getTime();
        Date open = time1.getTime();
        Date close = time2.getTime();

        if (currentTime.after(open) && currentTime.before(close)) {
            return 0;
        } else {
            return 1;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;

        if (viewType == 0) {
            view = layoutInflater.inflate(R.layout.menu_food_item, parent, false);
            return new ViewHolderOne(view);
        } else {
            view = layoutInflater.inflate(R.layout.menu_food_item_not_available, parent, false);
            return new ViewHolderTwo(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
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
            time1.setTime(format.parse(food_list.get(position).getOpenAndClose()[0]));
            time2.setTime(format.parse(food_list.get(position).getOpenAndClose()[1]));
            time1.add(Calendar.DATE, 1);
            time2.add(Calendar.DATE, 1);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Date currentTime = time.getTime();
        Date open = time1.getTime();
        Date close = time2.getTime();
        System.out.println(format.format(new Date()));

        if (currentTime.after(open) && currentTime.before(close)) {
            ViewHolderOne viewHolderOne = (ViewHolderOne) holder;

            viewHolderOne.menu_food_card_view_image.setImageResource(R.drawable.nasi_goreng_image);
            viewHolderOne.food_name.setText(food_list.get(position).getName());
            viewHolderOne.location_name.setText(food_list.get(position).getLocation());
            viewHolderOne.stall_name.setText(food_list.get(position).getStall());
            viewHolderOne.operation_time.setText(food_list.get(position).getOpenAndClose()[0] + " to " + food_list.get(position).getOpenAndClose()[1]);
            viewHolderOne.food_price.setText("RM " + String.format("%.2f", food_list.get(position).getPrice()));
        } else {
            ViewHolderTwo viewHolderTwo = (ViewHolderTwo) holder;

            viewHolderTwo.menu_food_card_view_image.setImageResource(R.drawable.nasi_goreng_image);
            viewHolderTwo.food_name.setText(food_list.get(position).getName());
            viewHolderTwo.location_name.setText(food_list.get(position).getLocation());
            viewHolderTwo.stall_name.setText(food_list.get(position).getStall());
            viewHolderTwo.operation_time.setText(food_list.get(position).getOpenAndClose()[0] + " to " + food_list.get(position).getOpenAndClose()[1]);
            viewHolderTwo.food_price.setText("RM " + String.format("%.2f", food_list.get(position).getPrice()));
        }
    }

    class ViewHolderOne extends RecyclerView.ViewHolder {

        ShapeableImageView menu_food_card_view_image;
        TextView food_name, location_name, stall_name, operation_time, food_price;

        public ViewHolderOne(@NonNull View itemView) {
            super(itemView);

            menu_food_card_view_image = itemView.findViewById(R.id.menu_food_card_view_image);
            food_name = itemView.findViewById(R.id.food_name);
            location_name = itemView.findViewById(R.id.location_name);
            stall_name = itemView.findViewById(R.id.stall_name);
            operation_time = itemView.findViewById(R.id.operation_time);
            food_price = itemView.findViewById(R.id.food_price);
        }
    }

    class ViewHolderTwo extends RecyclerView.ViewHolder {

        ShapeableImageView menu_food_card_view_image;
        TextView food_name, location_name, stall_name, operation_time, food_price;

        public ViewHolderTwo(@NonNull View itemView) {
            super(itemView);

            menu_food_card_view_image = itemView.findViewById(R.id.menu_food_card_view_image);
            food_name = itemView.findViewById(R.id.food_name);
            location_name = itemView.findViewById(R.id.location_name);
            stall_name = itemView.findViewById(R.id.stall_name);
            operation_time = itemView.findViewById(R.id.operation_time);
            food_price = itemView.findViewById(R.id.food_price);
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
}
