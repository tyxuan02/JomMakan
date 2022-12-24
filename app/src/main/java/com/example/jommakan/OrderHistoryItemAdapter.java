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

public class OrderHistoryItemAdapter extends RecyclerView.Adapter<OrderHistoryItemAdapter.ViewHolder> {

    Context context;
    LayoutInflater layoutInflater;
    ArrayList<Order> order_list;

    public OrderHistoryItemAdapter(Context context, ArrayList<Order> order_list) {
        this.context = context;
        this.order_list = order_list;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public OrderHistoryItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.order_history_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderHistoryItemAdapter.ViewHolder holder, int position) {
        holder.order_id.setText(String.valueOf(order_list.get(position).getOrder_id()));
        holder.location_and_stall_text_view.setText(order_list.get(position).getLocation() + " - " + order_list.get(position).getStall());

        String str = "";
        for (int i = 0; i < order_list.get(position).getFood_list().size(); i++) {
            str += (i+1) + ". " + order_list.get(position).getFood_list().get(i).getName() + " x" + order_list.get(position).getFood_quantity()[i] + "\n";
        }
        holder.ordered_food_text_view.setText(str);

        holder.price.setText("RM " + String.format("%.2f", getTotalPrice(order_list.get(position))));
        holder.order_date.setText(order_list.get(position).getTime());
    }

    private double getTotalPrice(Order order) {
        double sum = 0;
        for (int i = 0; i < order.getFood_list().size(); i++) {
            sum += order.getFood_list().get(i).getPrice() * ((double) order.getFood_quantity()[i]);
        }
        return sum;
    }

    @Override
    public int getItemCount() {
        return order_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CardView order_history_item_card_view;
        TextView order_id, location_and_stall_text_view, ordered_food_text_view, price, order_date;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            order_history_item_card_view = itemView.findViewById(R.id.order_history_item_card_view);
            order_id = itemView.findViewById(R.id.order_id);
            location_and_stall_text_view = itemView.findViewById(R.id.location_and_stall_text_view);
            ordered_food_text_view = itemView.findViewById(R.id.ordered_food_text_view);
            price = itemView.findViewById(R.id.price);
            order_date = itemView.findViewById(R.id.order_date);
        }
    }
}
