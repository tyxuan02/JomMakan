package com.example.jommakan;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class CartFragment extends Fragment {

    CartItemDatabase cartItemDatabase;
    ArrayList<CartItem> cart_item_list;
    RecyclerView cart_item_recycle_view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_cart, container, false);
        return view;
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
        getCartItems(UserInstance.getUser_email_address());


        cart_item_recycle_view = view.findViewById(R.id.cart_item_recycle_view);
        if (cart_item_list.size() != 0) {
            ParentCartItemAdapter parentCartItemAdapter = new ParentCartItemAdapter(getActivity(), cart_item_list, getChildFragmentManager());
            cart_item_recycle_view.setAdapter(parentCartItemAdapter);
            cart_item_recycle_view.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
            cart_item_recycle_view.setHasFixedSize(true);
            cart_item_recycle_view.setNestedScrollingEnabled(true);
        }
    }

    // Get all cart items from database
    private void getCartItems(String email_address) {
        cart_item_list = (ArrayList<CartItem>) cartItemDatabase.cartItemDAO().getAllCartItems(email_address);
    }
}