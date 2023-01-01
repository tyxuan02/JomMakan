package com.example.jommakan;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.room.Room;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class CartFragment extends Fragment {

    CartItemDatabase cartItemDatabase;
    ArrayList<CartItem> cart_item_list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cart, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        // Declare and assign views here
        TextView toolbar_title = getActivity().findViewById(R.id.toolbar_title);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            toolbar_title.setTextAppearance(R.style.toolbar_text_secondary);
            toolbar_title.setText("CART");
        }

        // Database connection
        cartItemDatabase = Room.databaseBuilder(getActivity(), CartItemDatabase.class, "CartItemDB").allowMainThreadQueries().build();

        // Get all cart items from database
        getCartItems("user@gmail.com");
        if (cart_item_list.size() != 0) {
            // ToDo
        }
    }

    // Get all cart items from database
    private void getCartItems(String email_address) {
        cart_item_list = (ArrayList<CartItem>) cartItemDatabase.cartItemDAO().getAllCartItems(email_address);
//        CartItem cartItem = cartItemDatabase.cartItemDAO().getCartItem("sdjkfh", "KK8", "Restoran");
//        cart_food_list = cartItem.getCart_food_list();
//        Toast.makeText(this, cartItem.getUser_email_address(), Toast.LENGTH_SHORT).show();
    }
}