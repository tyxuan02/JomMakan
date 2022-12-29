package com.example.jommakan;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class FoodFragment extends Fragment {

    ImageButton back_button;
    ImageView food_image;
    TextView food_name;
    TextView food_price;
    TextView food_description;
    Date currentTime, open, close;
    View shadow_background;
    TextView not_available_text;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_food, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        back_button = view.findViewById(R.id.back_button);
        food_image = view.findViewById(R.id.food_image);
        food_name = view.findViewById(R.id.food_name);
        food_price = view.findViewById(R.id.food_price);
        food_description = view.findViewById(R.id.food_description);
        shadow_background = view.findViewById(R.id.shadow_background);
        not_available_text = view.findViewById(R.id.not_available_text);

        // Hide top navigation bar
        Toolbar toolbar = ((MainActivity) getActivity()).findViewById(R.id.toolbar);
        toolbar.setVisibility(View.GONE);

        // Back button
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        // Extract the data from the bundle (Determine which food user has chosen)
        Food food = (Food) getArguments().getSerializable("food");

        food_image.setImageResource(food.getImage());
        food_name.setText(food.getName());
        food_price.setText("RM " + String.format("%.2f", food.getPrice()));

        String description = "";
        for (String desc: food.getDescription()) {
            description += "- " + desc + "\n";
        }
        food_description.setText(description);

        checkOpenClose(food.getOpenAndClose().get(0), food.getOpenAndClose().get(1));
        if (currentTime.after(open) && currentTime.before(close)) {
            shadow_background.setVisibility(View.INVISIBLE);
            not_available_text.setVisibility(View.INVISIBLE);
        } else {
            shadow_background.setVisibility(View.VISIBLE);
            not_available_text.setVisibility(View.VISIBLE);
        }
    }

    private void checkOpenClose(String openTime, String closeTime) {
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
            time1.setTime(format.parse(openTime));
            time2.setTime(format.parse(closeTime));
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