package com.example.jommakan;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

// Adapter for menu on homepage
public class HomeLocationItemAdapter extends RecyclerView.Adapter<HomeLocationItemAdapter.ViewHolder> {

    Context context;
    ArrayList<Location> location_list;
    LayoutInflater layoutInflater;


    public HomeLocationItemAdapter (Context context, ArrayList<Location> location_list) {
        this.context = context;
        this.location_list = location_list;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.home_location_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeLocationItemAdapter.ViewHolder holder, int position) {
        holder.home_location_card_view_image.setImageResource(location_list.get(position).getLocation_image());
        holder.home_location_card_view_location_name.setText(location_list.get(position).getLocation());
    }

    @Override
    public int getItemCount() {
        return location_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView home_location_card_view_image;
        TextView home_location_card_view_location_name;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            home_location_card_view_image = itemView.findViewById(R.id.home_location_card_view_image);
            home_location_card_view_location_name = itemView.findViewById(R.id.home_location_card_view_location_name);
        }
    }
}