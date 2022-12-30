package com.example.jommakan;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CartItemAdapter extends RecyclerView.Adapter<CartItemAdapter.MyViewHolder> {

    protected Context context;
    protected ArrayList<CartItem> cartItem;
    protected double totalPrice;

    public CartItemAdapter (Context context, ArrayList<CartItem> cartItemModels) {
        this.context = context;
        this.cartItem = cartItemModels;
        this.totalPrice = totalPrice;
    }

    private void calcTotalPrice (CartItem cartItem) {
        double tempTotalPrice = 0;
        for (int i=0; i<getItemCount(); i++) {
            tempTotalPrice = cartItem.getSelected_food_quantity().get(i) * cartItem.getSelected_food_price().get(i);
            totalPrice += tempTotalPrice;
        }
    }
    @NonNull
    @Override
    public CartItemAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recyclerview_cart, parent, false);
        return new CartItemAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartItemAdapter.MyViewHolder holder, int position) {
        holder.tvRestaurantName.setText(cartItem.get(position).getStall());
        holder.tvItemName.setText((CharSequence) cartItem.get(position).getSelected_food_name());
        holder.tvItemQuantity.setText((CharSequence) cartItem.get(position).getSelected_food_quantity());
        holder.tvItemPrice.setText((CharSequence) cartItem.get(position).getSelected_food_price());
        holder.tvTotalPrice.setText((int) totalPrice);
    }

    @Override
    public int getItemCount() {
        return cartItem.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView tvRestaurantName, tvItemName, tvItemQuantity, tvItemPrice, tvTotalPrice;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tvRestaurantName = itemView.findViewById(R.id.TVRestaurantName);
            tvItemName = itemView.findViewById(R.id.TVItemName);
            tvItemQuantity = itemView.findViewById(R.id.TVItemQuantity);
            tvItemPrice = itemView.findViewById(R.id.TVItemPrice);
            tvTotalPrice = itemView.findViewById(R.id.TVTotalPrice);
        }
    }
}
