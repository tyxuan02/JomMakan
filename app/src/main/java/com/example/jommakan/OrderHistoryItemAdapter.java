package com.example.jommakan;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

/**
 * An adapter class that is used to display a list of order items in Order History Page
 */
public class OrderHistoryItemAdapter extends RecyclerView.Adapter<OrderHistoryItemAdapter.ViewHolder> {

    /**
     * Provides access to global information about an application environment.
     */
    Context context;

    /**
     * A layout inflater that is used to instantiate layout XML file into its corresponding View objects
     */
    LayoutInflater layoutInflater;

    /**
     * An array list that is used to store a orders
     */
    ArrayList<Order> order_list;

    /**
     * A constructor of OrderHistoryItemAdapter class
     * @param context context
     * @param order_list a list of orders
     */
    public OrderHistoryItemAdapter(Context context, ArrayList<Order> order_list) {
        this.context = context;
        this.order_list = order_list;
        this.layoutInflater = LayoutInflater.from(context);
    }

    /**
     * Inflates a layout file (R.layout.order_history_item) and creates a new instance of the class OrderHistoryItemAdapter.ViewHolder
     */
    @NonNull
    @Override
    public OrderHistoryItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.order_history_item, parent, false);
        return new ViewHolder(view);
    }

    /**
     * This method binds the data to the views, which is at the position passed as an argument.
     */
    @Override
    public void onBindViewHolder(@NonNull OrderHistoryItemAdapter.ViewHolder holder, int position) {
        holder.order_id.setText(String.valueOf(order_list.get(position).getOrder_id()));
        holder.location_and_stall_text_view.setText(order_list.get(position).getLocation() + " - " + order_list.get(position).getStall());

        String str = "";
        for (int i = 0; i < order_list.get(position).getCart_food_list().size(); i++) {
            str += (i+1) + ". " + order_list.get(position).getCart_food_list().get(i).getFood_name() + " x" + order_list.get(position).getCart_food_list().get(i).getQuantity() + "\n";
        }
        holder.ordered_food_text_view.setText(str);

        holder.price.setText("RM " + String.format("%.2f", getTotalPrice(order_list.get(position))));
        holder.order_date_time.setText(order_list.get(position).getDate() + ", " + order_list.get(position).getTime());
    }

    /**
     * Get the size of the order array list
     * @return int
     */
    @Override
    public int getItemCount() {
        return order_list.size();
    }

    /**
     * This class is used to hold references to the various views that make up an item in a RecyclerView
     */
    public class ViewHolder extends RecyclerView.ViewHolder {
        CardView order_history_item_card_view;
        TextView order_id, location_and_stall_text_view, ordered_food_text_view, price, order_date_time;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            order_history_item_card_view = itemView.findViewById(R.id.order_history_item_card_view);
            order_id = itemView.findViewById(R.id.order_id);
            location_and_stall_text_view = itemView.findViewById(R.id.location_and_stall_text_view);
            ordered_food_text_view = itemView.findViewById(R.id.ordered_food_text_view);
            price = itemView.findViewById(R.id.price);
            order_date_time = itemView.findViewById(R.id.order_date_time);
        }
    }

    /**
     * Get the total price of an order
     * @param order user's order
     * @return double
     */
    private double getTotalPrice(Order order) {
        double sum = 0;
        for (int i = 0; i < order.getCart_food_list().size(); i++) {
            sum += order.getCart_food_list().get(i).getPrice() * ((double) order.getCart_food_list().get(i).getQuantity());
        }
        return sum;
    }
}
