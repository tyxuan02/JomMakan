package com.example.jommakan;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.imageview.ShapeableImageView;

import java.util.ArrayList;

public class MenuLocationItemAdapter extends RecyclerView.Adapter<MenuLocationItemAdapter.ViewHolder> {

    ArrayList<Location> location_list;
    Context context;
    LayoutInflater layoutInflater;

    public MenuLocationItemAdapter(ArrayList<Location> location_list, Context context) {
        this.location_list = location_list;
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.menu_location_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.menu_location_card_view_image.setImageResource(location_list.get(position).getLocation_image());
        holder.menu_location_card_view_location_name.setText(location_list.get(position).getLocation());

        holder.menu_location_item_card_view.setOnClickListener(new View.OnClickListener() {
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

    public class ViewHolder extends RecyclerView.ViewHolder {

        ShapeableImageView menu_location_card_view_image;
        TextView menu_location_card_view_location_name;
        CardView menu_location_item_card_view;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            menu_location_card_view_image = itemView.findViewById(R.id.menu_location_card_view_image);
            menu_location_card_view_location_name = itemView.findViewById(R.id.menu_location_card_view_location_name);
            menu_location_item_card_view = itemView.findViewById(R.id.menu_location_item_card_view);
        }
    }


}
