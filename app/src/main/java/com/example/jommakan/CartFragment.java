package com.example.jommakan;

import android.database.sqlite.SQLiteException;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import java.util.ArrayList;

/**
 * A fragment that is responsible for display and managing cart item
 * It allows the users to edit item in their carts
 */
public class CartFragment extends Fragment {

    /**
     * An instance of the class CartItemDatabase
     */
    CartItemDatabase cartItemDatabase;

    /**
     * An array list that is used to store cart items
     */
    ArrayList<CartItem> cart_item_list;

    /**
     * A recycle view that is used to display a list of cart items
     */
    RecyclerView cart_item_recycle_view;

    /**
     * Called to have the fragment instantiate its user interface view
     * @param inflater The LayoutInflater object that can be used to inflate any views in the fragment
     * @param container If non-null, this is the parent view that the fragment's UI should be attached to
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous saved state
     * @return view
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_cart, container, false);
        return view;
    }

    /**
     * Called immediately after onCreateView has returned, but before any saved state has been restored in to the view
     * @param view The View returned by onCreateView
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous saved state
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        // Toolbar
        Toolbar toolbar = ((MainActivity) getActivity()).findViewById(R.id.toolbar);
        toolbar.setVisibility(View.VISIBLE);
        TextView toolbar_title = getActivity().findViewById(R.id.toolbar_title);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            toolbar_title.setTextAppearance(R.style.toolbar_text_secondary);
            toolbar_title.setText("CART");
        }

        // Database connection
        cartItemDatabase = Room.databaseBuilder(getActivity(), CartItemDatabase.class, "CartItemDB").allowMainThreadQueries().build();

        // Get all cart items from database
        getCartItems(UserInstance.getUser_email_address());

        // Display cart items that get from the database
        cart_item_recycle_view = view.findViewById(R.id.cart_item_recycle_view);
        if (cart_item_list.size() != 0) {
            ParentCartItemAdapter parentCartItemAdapter = new ParentCartItemAdapter(getActivity(), cart_item_list, getChildFragmentManager());
            cart_item_recycle_view.setAdapter(parentCartItemAdapter);
            cart_item_recycle_view.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
            cart_item_recycle_view.setHasFixedSize(true);
            cart_item_recycle_view.setNestedScrollingEnabled(true);
        }
    }

    /**
     * Get all cart items from database
     * @param email_address user email address
     */
    private void getCartItems(String email_address) {
        try {
            cart_item_list = (ArrayList<CartItem>) cartItemDatabase.cartItemDAO().getAllCartItems(email_address);        } catch (SQLiteException e) {
            // Handle errors
            e.printStackTrace();
        } finally {
            // Close the database connection
            cartItemDatabase.close();
        }
    }
}