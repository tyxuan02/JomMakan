package com.example.jommakan;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
    ImageButton decrement_button, increment_button;
    TextView quantity;
    Button add_to_cart_button;
    TextView quantity_text;

    int count = 1;

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
        decrement_button = view.findViewById(R.id.decrement_button);
        increment_button = view.findViewById(R.id.increment_button);
        quantity = view.findViewById(R.id.quantity);
        add_to_cart_button = view.findViewById(R.id.add_to_cart_button);
        quantity_text = view.findViewById(R.id.quantity_text);

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

            // Display increment, decrement and add to cart buttons
            increment_button.setVisibility(View.VISIBLE);
            decrement_button.setVisibility(View.VISIBLE);
            add_to_cart_button.setVisibility(View.VISIBLE);
            quantity.setVisibility(View.VISIBLE);
            quantity_text.setVisibility(View.VISIBLE);
            quantity.setText("1");
        } else {
            shadow_background.setVisibility(View.VISIBLE);
            not_available_text.setVisibility(View.VISIBLE);

            // Hide increment, decrement and add to cart buttons
            increment_button.setVisibility(View.INVISIBLE);
            decrement_button.setVisibility(View.INVISIBLE);
            add_to_cart_button.setVisibility(View.INVISIBLE);
            quantity.setVisibility(View.INVISIBLE);
            quantity_text.setVisibility(View.INVISIBLE);
        }

        // Increment, decrement and add to cart buttons
        increment_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count++;
                quantity.setText(String.valueOf(count));
            }
        });

        decrement_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (count <= 0) {
                    count = 0;
                } else {
                    count--;
                }
                quantity.setText(String.valueOf(count));
            }
        });

        add_to_cart_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: Add to cart after user clicks it
            }
        });
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