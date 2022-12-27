package com.example.jommakan;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.smarteist.autoimageslider.SliderViewAdapter;

import java.util.ArrayList;

// Slider Adapter for Top 5 Food on homepage
public class SliderAdapter extends SliderViewAdapter<SliderAdapter.Holder> {

    ArrayList<Integer> images;
    public SliderAdapter (ArrayList<Integer> images) {
        this.images = images;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.top_5_slider_item, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder viewHolder, int position) {
        viewHolder.imageView.setImageResource(images.get(position));
    }

    @Override
    public int getCount() {
        return images.size();
    }

    public class Holder extends SliderViewAdapter.ViewHolder {

        ImageView imageView;

        public Holder(View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.top_5_image_view);
        }
    }
}