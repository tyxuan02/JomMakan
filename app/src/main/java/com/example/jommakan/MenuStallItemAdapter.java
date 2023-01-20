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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * An adapter class that is used to display a list of stalls in Menu Stall page
 */
public class MenuStallItemAdapter extends RecyclerView.Adapter<MenuStallItemAdapter.ViewHolder>{

    /**
     * Provides access to global information about an application environment.
     */
    Context context;

    /**
     * An array list that is used to store a list of stalls
     */
    ArrayList<Stall> stall_list;

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
     * A constructor of MenuStallItemAdapter
     * @param context context
     * @param stall_list a list of stalls
     */
    public MenuStallItemAdapter(Context context, ArrayList<Stall> stall_list) {
        this.context = context;
        this.stall_list = stall_list;
        this.layoutInflater = LayoutInflater.from(context);
    }

    /**
     * Inflates a layout file (R.layout.menu_stall_item) and creates a new instance of the class MenuStallItemAdapter.ViewHolder
     */
    @NonNull
    @Override
    public MenuStallItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_stall_item, parent, false);
        return new MenuStallItemAdapter.ViewHolder(view);
    }

    /**
     * This method binds the data to the views, which is at the position passed as an argument.
     */
    @Override
    public void onBindViewHolder(@NonNull MenuStallItemAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.menu_food_card_view_image.setImageResource(stall_list.get(position).getStall_image());
        holder.stall_name.setText(stall_list.get(position).getStall_name());

        // Check whether the stall is open
        // If the stall is open, hide shadow background and not available text
        // If the stall is close, display shadow background and not available text
        checkOpenClose(position);
        if (currentTime.after(open) && currentTime.before(close)) {
            holder.shadow_background.setVisibility(View.INVISIBLE);
            holder.temporarily_closed_text.setVisibility(View.INVISIBLE);
        } else {
            holder.shadow_background.setVisibility(View.VISIBLE);
            holder.temporarily_closed_text.setVisibility(View.VISIBLE);
        }

        holder.operation_time.setText(stall_list.get(position).getOpenAndClose().get(0) + " to " + stall_list.get(position).getOpenAndClose().get(1));
        holder.menu_stall_item_card_view.setOnClickListener(new View.OnClickListener() {

            /**
             * Pass location name and location name to Stall Fragment using bundle
             * @param v view
             */
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("selected_stall_name", stall_list.get(position).getStall_name());
                bundle.putString("selected_location_name", stall_list.get(position).getLocation());
                Navigation.findNavController(v).navigate(R.id.DestStall, bundle);
            }
        });
    }

    /**
     * Get the size of the food array list
     * @return int
     */
    @Override
    public int getItemCount() {
        return stall_list.size();
    }

    /**
     * This class is used to hold references to the various views that make up an item in a RecyclerView
     */
    public class ViewHolder extends RecyclerView.ViewHolder {

        CardView menu_stall_item_card_view;
        ShapeableImageView menu_food_card_view_image;
        TextView stall_name;
        TextView operation_time;
        View shadow_background;
        TextView temporarily_closed_text;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            menu_stall_item_card_view = itemView.findViewById(R.id.menu_stall_item_card_view);
            menu_food_card_view_image = itemView.findViewById(R.id.menu_food_card_view_image);
            stall_name = itemView.findViewById(R.id.stall_name);
            operation_time = itemView.findViewById(R.id.operation_time);
            shadow_background = itemView.findViewById(R.id.shadow_background);
            temporarily_closed_text = itemView.findViewById(R.id.temporarily_closed_text);
        }
    }

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
            time1.setTime(format.parse(stall_list.get(position).getOpenAndClose().get(0)));
            time2.setTime(format.parse(stall_list.get(position).getOpenAndClose().get(1)));
            time1.add(Calendar.DATE, 1);
            time2.add(Calendar.DATE, 1);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        currentTime = time.getTime();
        open = time1.getTime();
        close = time2.getTime();
    }
}
