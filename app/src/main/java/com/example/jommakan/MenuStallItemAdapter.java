package com.example.jommakan;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.imageview.ShapeableImageView;

import java.sql.Struct;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MenuStallItemAdapter extends RecyclerView.Adapter<MenuStallItemAdapter.ViewHolder>{

    Context context;
    ArrayList<Stall> stall_list;
    LayoutInflater layoutInflater;

    public MenuStallItemAdapter(Context context, ArrayList<Stall> stall_list) {
        this.context = context;
        this.stall_list = stall_list;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public MenuStallItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_stall_item, parent, false);
        return new MenuStallItemAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MenuStallItemAdapter.ViewHolder holder, int position) {
        holder.menu_food_card_view_image.setImageResource(R.drawable.restoran_famidah_image);
        holder.stall_name.setText(stall_list.get(position).getStall_name());

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
            time1.setTime(format.parse(stall_list.get(position).getOpenAndClose()[0]));
            time2.setTime(format.parse(stall_list.get(position).getOpenAndClose()[1]));
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
            holder.shadow_background.setVisibility(View.INVISIBLE);
            holder.temporarily_closed_text.setVisibility(View.INVISIBLE);
        } else {
            holder.shadow_background.setVisibility(View.VISIBLE);
            holder.temporarily_closed_text.setVisibility(View.VISIBLE);
        }

        holder.operation_time.setText(stall_list.get(position).getOpenAndClose()[0] + " to " + stall_list.get(position).getOpenAndClose()[1]);
        holder.menu_stall_item_card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return stall_list.size();
    }

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
}
