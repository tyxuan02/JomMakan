package com.example.jommakan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class OrderHistoryPage extends AppCompatActivity {

    OrderHistoryItemAdapter orderHistoryItemAdapter;
    RecyclerView order_history_recycle_view;
    ArrayList<Order> order_list;
    ArrayList<Food> food_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history_page);

        Toolbar toolbarActivity = findViewById(R.id.toolbarActivity);
        setSupportActionBar(toolbarActivity);

        // Remove default title text
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        // Get access to the custom title view
        TextView toolbar_title = (TextView) toolbarActivity.findViewById(R.id.toolbar_title);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            toolbar_title.setTextAppearance(R.style.toolbar_text_quaternary);
            toolbar_title.setText("Order history");
        }

        toolbarActivity.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               onBackPressed();
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // View and adapter
        food_list = new ArrayList<>();
        food_list.add(new Food("Nasi Goreng", "Faculty of Computer Science and Information Technology", "Restoran Famidah", 6.00, new String[]{"spicy", "no pawn"}, R.drawable.nasi_goreng_image, new String[]{"10.00 am", "10.00pm"}));
        food_list.add(new Food("Nasi Goreng Cina", "Faculty of Computer Science and Information Technology", "Restoran Famidah", 6.00, new String[]{"spicy", "no pawn"}, R.drawable.nasi_goreng_image, new String[]{"10.00 am", "10.00pm"}));
        food_list.add(new Food("Nasi Goreng Kampung", "Faculty of Computer Science and Information Technology", "Restoran Famidah", 6.00, new String[]{"spicy", "no pawn"}, R.drawable.nasi_goreng_image, new String[]{"10.00 am", "10.00pm"}));

        order_list = new ArrayList<>();
        Order order = new Order(123458789, "Faculty of Computer Science and Information Technology", "Restoran Famidah", food_list, new int[]{1,2,3}, "25 Dec 2022", "10.30 am");
        order_list.add(order);
        order_list.add(order);
        order_list.add(order);
        order_list.add(order);

        orderHistoryItemAdapter = new OrderHistoryItemAdapter(this, order_list);
        order_history_recycle_view = findViewById(R.id.order_history_recycle_view);
        order_history_recycle_view.setAdapter(orderHistoryItemAdapter);
        order_history_recycle_view.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        order_history_recycle_view.setHasFixedSize(true);
        order_history_recycle_view.setNestedScrollingEnabled(true);
    }

}