package com.example.jommakan;

import android.annotation.SuppressLint;
import android.content.Context;
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
    public void onBindViewHolder(@NonNull HomeLocationItemAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.home_location_card_view_image.setImageResource(location_list.get(position).getLocation_image());
        holder.home_location_card_view_location_name.setText(location_list.get(position).getLocation());
        holder.home_location_item_card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Pass data between fragments using bundle
                Bundle bundle = new Bundle();
                bundle.putString("location_name", location_list.get(position).getLocation());
                Navigation.findNavController(v).navigate(R.id.DestMenuStall, bundle);
            }
        });
    }

    @Override
    public int getItemCount() {
        return location_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView home_location_card_view_image;
        TextView home_location_card_view_location_name;
        CardView home_location_item_card_view;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            home_location_card_view_image = itemView.findViewById(R.id.home_location_card_view_image);
            home_location_card_view_location_name = itemView.findViewById(R.id.home_location_card_view_location_name);
            home_location_item_card_view = itemView.findViewById(R.id.home_location_item_card_view);
        }
    }
}