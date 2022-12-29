package com.example.jommakan;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.navigation.Navigation;

import com.smarteist.autoimageslider.SliderViewAdapter;

import java.io.Serializable;
import java.util.ArrayList;

// Slider Adapter for Top 5 Food on homepage
public class SliderAdapter extends SliderViewAdapter<SliderAdapter.Holder> {

    ArrayList<Food> food_list;
    public SliderAdapter (ArrayList<Food> food_list) {
        this.food_list = food_list;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.top_5_slider_item, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder viewHolder, int position) {
        viewHolder.imageView.setImageResource(food_list.get(position).getImage());
        viewHolder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Pass data between fragments using bundle
                Bundle bundle = new Bundle();
                bundle.putSerializable("food", (Serializable) food_list.get(position));
                Navigation.findNavController(v).navigate(R.id.DestFood, bundle);
            }
        });
    }

    @Override
    public int getCount() {
        return food_list.size();
    }

    public class Holder extends SliderViewAdapter.ViewHolder {

        ImageView imageView;

        public Holder(View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.top_5_image_view);
        }
    }
}