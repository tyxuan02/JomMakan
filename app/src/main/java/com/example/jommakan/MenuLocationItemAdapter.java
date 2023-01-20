package com.example.jommakan;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.imageview.ShapeableImageView;

import java.util.ArrayList;

/**
 * An adapter class that is used to display a list of location in menu-location tab
 */
public class MenuLocationItemAdapter extends RecyclerView.Adapter<MenuLocationItemAdapter.ViewHolder> {

    /**
     * An array list that is used to store locations
     */
    ArrayList<Location> location_list;

    /**
     * Provides access to global information about an application environment.
     */
    Context context;

    /**
     * A layout inflater that is used to instantiate layout XML file into its corresponding View objects
     */
    LayoutInflater layoutInflater;

    /**
     * Constructor of MenuLocationItemAdapter
     * @param location_list a list of locations
     * @param context context
     */
    public MenuLocationItemAdapter(ArrayList<Location> location_list, Context context) {
        this.location_list = location_list;
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
    }

    /**
     * Inflates a layout file (R.layout.menu_location_item) and creates a new instance of the class MenuLocationItemAdapter.ViewHolder
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.menu_location_item, parent, false);
        return new ViewHolder(view);
    }

    /**
     * This method binds the data to the views, which is at the position passed as an argument.
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.menu_location_card_view_image.setImageResource(location_list.get(position).getLocation_image());
        holder.menu_location_card_view_location_name.setText(location_list.get(position).getLocation());

        holder.menu_location_item_card_view.setOnClickListener(new View.OnClickListener() {

            /**
             * Pass location name to Menu Stall Fragment using bundle
             * @param v view
             */
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("location_name", location_list.get(position).getLocation());
                Navigation.findNavController(v).navigate(R.id.DestMenuStall, bundle);
            }
        });
    }

    /**
     * Get the size of the food array list
     * @return int
     */
    @Override
    public int getItemCount() {
        return location_list.size();
    }

    /**
     * This class is used to hold references to the various views that make up an item in a RecyclerView
     */
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
