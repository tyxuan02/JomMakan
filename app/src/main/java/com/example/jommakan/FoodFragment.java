package com.example.jommakan;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
import androidx.room.Room;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

public class FoodFragment extends Fragment {

    ImageButton back_button;
    ImageView food_image;
    TextView food_name;
    TextView food_price;
    TextView food_description;
    Date currentTime, open, close;
    ImageButton decrement_button, increment_button;
    TextView quantity;
    Button add_to_cart_button;
    TextView quantity_text;
    TextView unavailable_text_view;
    ArrayList<CartFood> cart_food_list;
    CartItemDatabase cartItemDatabase;
    Food chosen_food;
    String chosen_location_name;
    String chosen_stall_name;

    int count;

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
        decrement_button = view.findViewById(R.id.decrement_button);
        increment_button = view.findViewById(R.id.increment_button);
        quantity = view.findViewById(R.id.quantity);
        add_to_cart_button = view.findViewById(R.id.add_to_cart_button);
        quantity_text = view.findViewById(R.id.quantity_text);
        unavailable_text_view = view.findViewById(R.id.unavailable_text_view);

        // Database connection
        cartItemDatabase = Room.databaseBuilder(getActivity(), CartItemDatabase.class, "CartItemDB").allowMainThreadQueries().build();

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
        chosen_food = (Food) getArguments().getSerializable("food");
        cart_food_list = (ArrayList<CartFood>) getArguments().getSerializable("cart_food_list");
        chosen_location_name = getArguments().getString("location");
        chosen_stall_name = getArguments().getString("stall");

        food_image.setImageResource(chosen_food.getImage());
        food_name.setText(chosen_food.getName());
        food_price.setText("RM " + String.format("%.2f", chosen_food.getPrice()));

        String description = "";
        for (String desc: chosen_food.getDescription()) {
            description += "- " + desc + "\n";
        }
        food_description.setText(description);

        count = getFoodQuantityInCartItem();
        checkOpenClose(chosen_food.getOpenAndClose().get(0), chosen_food.getOpenAndClose().get(1));
        if (currentTime.after(open) && currentTime.before(close)) {
            // Display increment, decrement and add to cart buttons
            increment_button.setVisibility(View.VISIBLE);
            decrement_button.setVisibility(View.VISIBLE);
            add_to_cart_button.setVisibility(View.VISIBLE);
            quantity.setVisibility(View.VISIBLE);
            quantity_text.setVisibility(View.VISIBLE);
            quantity.setVisibility(View.VISIBLE);
            quantity.setText(String.valueOf(count));
            unavailable_text_view.setVisibility(View.INVISIBLE);
        } else {
            // Hide increment, decrement and add to cart buttons
            increment_button.setVisibility(View.INVISIBLE);
            decrement_button.setVisibility(View.INVISIBLE);
            add_to_cart_button.setVisibility(View.INVISIBLE);
            quantity.setVisibility(View.INVISIBLE);
            quantity_text.setVisibility(View.INVISIBLE);
            unavailable_text_view.setVisibility(View.VISIBLE);
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

        // Check if the food from that stall is already in the cart item
        // -1 means the food from that stall is not in the cart item
        int index = checkIfFoodExistInCartItem();
        if (index != -1) {
            add_to_cart_button.setText("Update");
        } else {
            add_to_cart_button.setText("Add To Cart");
        }
        add_to_cart_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkNumberOfFoodInCartItem() == 0) {
                    // If there are no food from that stall in the cart item
                    if (Integer.parseInt(quantity.getText().toString()) == 0) {
                        Toast.makeText(getActivity(), "Please make sure to select a quantity for the food that is greater than zero", Toast.LENGTH_SHORT).show();
                    } else {
                        changeFoodQuantityInCartItem(index);
                        cartItemDatabase.cartItemDAO().insertCartItem(new CartItem(UserInstance.getUser_email_address(), chosen_food.getLocation(), chosen_food.getStall(), cart_food_list));
                        Toast.makeText(getActivity(), "The food has been added to the cart", Toast.LENGTH_SHORT).show();
                    }
                    getActivity().onBackPressed();
                } else if (checkNumberOfFoodInCartItem() < 3) {
                    // If there are 1 to 2 food from that stall in the cart
                    if (Integer.parseInt(quantity.getText().toString()) == 0) {
                        if (index != -1) {
                            removeFoodFromCartItem(index);
                            Toast.makeText(getActivity(), "The food has been removed from the cart", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getActivity(), "Please make sure to select a quantity for the food that is greater than zero", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getActivity(), "The food has been added to the cart", Toast.LENGTH_SHORT).show();
                        changeFoodQuantityInCartItem(index);
                    }

                    if (cart_food_list.size() == 0) {
                        cartItemDatabase.cartItemDAO().deleteCartItem(UserInstance.getUser_email_address(), chosen_food.getLocation(), chosen_food.getStall());
                    } else {
                        cartItemDatabase.cartItemDAO().updateCartItem(UserInstance.getUser_email_address(), chosen_food.getLocation(), chosen_food.getStall(), cart_food_list);
                    }
                    getActivity().onBackPressed();
                } else if (checkNumberOfFoodInCartItem() == 3) {
                    // If there are 3 food from that stall in the cart
                    if (index != -1) {
                        // If the food from that stall is already in the cart
                        if (Integer.parseInt(quantity.getText().toString()) == 0) {
                            removeFoodFromCartItem(index);
                            Toast.makeText(getActivity(), "The food has been removed from the cart", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getActivity(), "The food has been added to the cart", Toast.LENGTH_SHORT).show();
                            changeFoodQuantityInCartItem(index);
                        }
                        cartItemDatabase.cartItemDAO().updateCartItem(UserInstance.getUser_email_address(), chosen_food.getLocation(), chosen_food.getStall(), cart_food_list);
                        getActivity().onBackPressed();
                    } else {
                        // If the user wants to add more food of that stall to cart item
                        Toast.makeText(getActivity(), "You can only add 3 different food from each store to cart", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    // Check if the stall is open or close
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

    // Check if the number of food from that stall in the cart item
    private int checkNumberOfFoodInCartItem() {
        return cart_food_list.size();
    }

    // Check if the food from that stall is already in the cart item
    private int checkIfFoodExistInCartItem() {
        for (int i = 0; i < cart_food_list.size(); i++) {
            if (cart_food_list.get(i).food_name.equals(chosen_food.getName())) {
                return i;
            }
        }
        return -1;
    }

    private int getFoodQuantityInCartItem() {
        int index = checkIfFoodExistInCartItem();
        if (index == -1) {
            return 1;
        } else {
            return cart_food_list.get(index).getQuantity();
        }
    }

    // Change the quantity of a food from that stall in the cart item
    private void changeFoodQuantityInCartItem(int index) {
        if (index == -1) {
            cart_food_list.add(new CartFood(chosen_food.getName(), Integer.parseInt(String.valueOf(quantity.getText())), chosen_food.price));
        } else {
            cart_food_list.get(index).setQuantity(Integer.parseInt(String.valueOf(quantity.getText())));
        }
    }

    // Remove the food from that stall from cart item
    private void removeFoodFromCartItem(int index) {
        cart_food_list.remove(index);
    }
}