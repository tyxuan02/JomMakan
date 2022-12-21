package com.example.jommakan;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
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
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.menu_location_card_view_image.setImageResource(location_list.get(position).getLocation_image());
        holder.menu_location_card_view_location_name.setText(location_list.get(position).getLocation());
    }

    @Override
    public int getItemCount() {
        return location_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ShapeableImageView menu_location_card_view_image;
        TextView menu_location_card_view_location_name;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            menu_location_card_view_image = itemView.findViewById(R.id.menu_location_card_view_image);
            menu_location_card_view_location_name = itemView.findViewById(R.id.menu_location_card_view_location_name);
        }
    }
}
