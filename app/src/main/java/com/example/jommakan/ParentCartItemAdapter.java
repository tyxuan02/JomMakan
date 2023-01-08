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

public class ParentCartItemAdapter extends RecyclerView.Adapter<ParentCartItemAdapter.MyViewHolder> {

    Context context;
    ArrayList<CartItem> cart_item_list;
    ArrayList<CartFood> cart_food_list;
    LayoutInflater layoutInflater;
    double total_price;
    CartItemDatabase cartItemDatabase;
    FragmentManager fragmentManager;

    private ArrayList<ChildCartItemAdapter> childCartItemAdapterList = new ArrayList<>();

    public ParentCartItemAdapter(Context context, ArrayList<CartItem> cart_item_list, FragmentManager fragmentManager) {
        this.context = context;
        this.cart_item_list = cart_item_list;
        this.layoutInflater = LayoutInflater.from(context);
        childCartItemAdapterList.clear();
        this.fragmentManager = fragmentManager;

        // Database connection
        cartItemDatabase = Room.databaseBuilder(context, CartItemDatabase.class, "CartItemDB").allowMainThreadQueries().build();
    }

    @NonNull
    @Override
    public ParentCartItemAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.cart_item, parent, false);
        return new ParentCartItemAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ParentCartItemAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {

        if (checkCartFood(position)) {
            holder.location_and_stall_name.setText(cart_item_list.get(position).getLocation() + " - " + cart_item_list.get(position).getStall());

            ChildCartItemAdapter cartItemAdapter = new ChildCartItemAdapter(cart_item_list.get(position).getCart_food_list(), cart_item_list.get(position).getLocation(), cart_item_list.get(position).getStall(), context);
            childCartItemAdapterList.add(cartItemAdapter);
            holder.cart_food_recycle_view.setAdapter(cartItemAdapter);
            holder.cart_food_recycle_view.setLayoutManager(new LinearLayoutManager(context, RecyclerView.VERTICAL, false));
            holder.cart_food_recycle_view.setHasFixedSize(true);
            holder.cart_food_recycle_view.setNestedScrollingEnabled(true);

            holder.edit_button.setOnClickListener(new View.OnClickListener() {
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
            holder.cart_item_card_view.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return cart_item_list.size();
    }

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

    private void calcTotalPrice () {
        total_price = 0;
        for (int i = 0; i < cart_food_list.size(); i++) {
            total_price += cart_food_list.get(i).getQuantity() * cart_food_list.get(i).getPrice();
        }
    }

    public void updateTotalPrice(int position) {
        ChildCartItemAdapter childCartItemAdapter = childCartItemAdapterList.get(position);
        childCartItemAdapter.updateQuantity();
    }

    private boolean checkCartFood(int position) {
        CartItem checkCartItem = null;
        try {
            checkCartItem = cartItemDatabase.cartItemDAO().getCartItem(UserHolder.getUser_email_address(), cart_item_list.get(position).getLocation(), cart_item_list.get(position).getStall());
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
