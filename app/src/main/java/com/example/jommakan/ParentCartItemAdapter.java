package com.example.jommakan;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import java.util.ArrayList;

/**
 * An adapter class that is used to display a list of cart items in Cart Page
 */
public class ParentCartItemAdapter extends RecyclerView.Adapter<ParentCartItemAdapter.MyViewHolder> {

    /**
     * Provides access to global information about an application environment.
     */
    Context context;

    /**
     * An array list that is used to store cart items
     */
    ArrayList<CartItem> cart_item_list;

    /**
     * An array list that is used to store food in a cart item
     */
    ArrayList<CartFood> cart_food_list;

    /**
     * A layout inflater that is used to instantiate layout XML file into its corresponding View objects
     */
    LayoutInflater layoutInflater;

    /**
     * A double that is used to store the total price of all food in a cart item
     */
    double total_price;

    /**
     * An instance of CartItemDatabase
     */
    CartItemDatabase cartItemDatabase;

    /**
     * A fragment manager that is used for managing fragments and their transactions.
     */
    FragmentManager fragmentManager;

    /**
     * An array list that is used to store ChildCartItemAdapters
     */
    private ArrayList<ChildCartItemAdapter> childCartItemAdapterList = new ArrayList<>();

    /**
     * Constructor of ParentCartItemAdapter class
     * @param context context
     * @param cart_item_list a list of cart item
     * @param fragmentManager FragmentManager
     */
    public ParentCartItemAdapter(Context context, ArrayList<CartItem> cart_item_list, FragmentManager fragmentManager) {
        this.context = context;
        this.cart_item_list = cart_item_list;
        this.layoutInflater = LayoutInflater.from(context);
        childCartItemAdapterList.clear();
        this.fragmentManager = fragmentManager;

        // Database connection
        cartItemDatabase = Room.databaseBuilder(context, CartItemDatabase.class, "CartItemDB").allowMainThreadQueries().build();
    }

    /**
     * Inflates a layout file (R.layout.cart_item) and creates a new instance of the class ChildCartItemAdapter.MyViewHolder
     */
    @NonNull
    @Override
    public ParentCartItemAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.cart_item, parent, false);
        return new ParentCartItemAdapter.MyViewHolder(view);
    }

    /**
     * This method binds the data to the views, which is at the position passed as an argument.
     */
    @Override
    public void onBindViewHolder(@NonNull ParentCartItemAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        if (checkCartFood(position)) {
            // Display cart items that users haven't made payment
            holder.location_and_stall_name.setText(cart_item_list.get(position).getLocation() + " - " + cart_item_list.get(position).getStall());

            // Display all food from a cart item in vertical list view
            ChildCartItemAdapter cartItemAdapter = new ChildCartItemAdapter(cart_item_list.get(position).getCart_food_list(), cart_item_list.get(position).getLocation(), cart_item_list.get(position).getStall(), context);
            childCartItemAdapterList.add(cartItemAdapter);
            holder.cart_food_recycle_view.setAdapter(cartItemAdapter);
            holder.cart_food_recycle_view.setLayoutManager(new LinearLayoutManager(context, RecyclerView.VERTICAL, false));
            holder.cart_food_recycle_view.setHasFixedSize(true);
            holder.cart_food_recycle_view.setNestedScrollingEnabled(true);

            holder.edit_button.setOnClickListener(new View.OnClickListener() {

                /**
                 * Update the new total price of a cart item after clicking on it
                 * @param v view
                 */
                @Override
                public void onClick(View v) {
                    updateTotalPrice(position);
                    notifyDataSetChanged();
                }
            });

            cart_food_list = cart_item_list.get(position).getCart_food_list();
            calcTotalPrice();
            holder.total_price.setText(String.format("%.2f", total_price));

            holder.cart_pay_button.setOnClickListener(new View.OnClickListener() {

                /**
                 * A payment confirmation pop up window will be display after clicking on it
                 * Pass cart item and total price of the cart item to Payment Confirmation Fragment
                 * @param v view
                 */
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("cart_item", cart_item_list.get(position));
                    bundle.putDouble("total_price", total_price);

                    PaymentConfirmationDialogFragment paymentConfirmationDialogFragment = new PaymentConfirmationDialogFragment();
                    paymentConfirmationDialogFragment.setArguments(bundle);
                    paymentConfirmationDialogFragment.show(fragmentManager, "Payment Confirmation Fragment");
                }
            });
        } else {
            // Remove the cart item from Cart Page after users have made payment
            holder.cart_item_card_view.setVisibility(View.GONE);
        }
    }

    /**
     * Get the size of the cart item array list
     * @return int
     */
    @Override
    public int getItemCount() {
        return cart_item_list.size();
    }

    /**
     * This class is used to hold references to the various views that make up an item in a RecyclerView
     */
    public static class MyViewHolder extends RecyclerView.ViewHolder{
        CardView cart_item_card_view;
        TextView location_and_stall_name;
        RecyclerView cart_food_recycle_view;
        TextView edit_button;
        TextView total_price;
        Button cart_pay_button;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            cart_item_card_view = itemView.findViewById(R.id.cart_item_card_view);
            location_and_stall_name = itemView.findViewById(R.id.location_and_stall_name);
            cart_food_recycle_view = itemView.findViewById(R.id.cart_food_recycle_view);
            edit_button = itemView.findViewById(R.id.edit_button);
            total_price = itemView.findViewById(R.id.total_price);
            cart_pay_button = itemView.findViewById(R.id.cart_pay_button);
        }
    }

    /**
     * This method is used to calculate the total price of all food in a cart item
     */
    private void calcTotalPrice () {
        total_price = 0;
        for (int i = 0; i < cart_food_list.size(); i++) {
            total_price += cart_food_list.get(i).getQuantity() * cart_food_list.get(i).getPrice();
        }
    }

    /**
     * This method is used to update the total price of all food in a cart item
     * @param position int
     */
    public void updateTotalPrice(int position) {
        ChildCartItemAdapter childCartItemAdapter = childCartItemAdapterList.get(position);
        childCartItemAdapter.updateQuantity();
    }

    /**
     * This method is used to check whether the cart item still exist in the Cart Page
     * This method is used to ensure that the cart item is removed whenever users have made payment for it
     * @param position int
     * @return boolean
     */
    private boolean checkCartFood(int position) {
        CartItem checkCartItem = null;
        try {
            checkCartItem = cartItemDatabase.cartItemDAO().getCartItem(UserInstance.getUser_email_address(), cart_item_list.get(position).getLocation(), cart_item_list.get(position).getStall());
        } catch (SQLiteException e) {
            // Handle errors
            e.printStackTrace();
        } finally {
            // Close the database connection
            cartItemDatabase.close();
        }

        if (checkCartItem == null || checkCartItem.getCart_food_list().size() == 0) {
            return false;
        }
        return true;
    }
}
