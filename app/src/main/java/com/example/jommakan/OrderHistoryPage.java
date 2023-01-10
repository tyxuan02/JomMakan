package com.example.jommakan;

import android.database.sqlite.SQLiteException;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import java.util.ArrayList;

public class OrderHistoryPage extends AppCompatActivity {

    OrderHistoryItemAdapter orderHistoryItemAdapter;
    RecyclerView order_history_recycle_view;
    ArrayList<Order> order_list;
    OrderDatabase orderDatabase;

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

        // Database connection
        orderDatabase = Room.databaseBuilder(this, OrderDatabase.class, "OrderDB").allowMainThreadQueries().build();

        try {
            order_list = (ArrayList<Order>) orderDatabase.orderDAO().getUserOrder(UserHolder.getUser_email_address());
        } catch (SQLiteException e) {
            // Handle errors
            e.printStackTrace();
        } finally {
            // Close the database connection
            orderDatabase.close();
        }

        orderHistoryItemAdapter = new OrderHistoryItemAdapter(this, order_list);
        order_history_recycle_view = findViewById(R.id.order_history_recycle_view);
        order_history_recycle_view.setAdapter(orderHistoryItemAdapter);
        order_history_recycle_view.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        order_history_recycle_view.setHasFixedSize(true);
        order_history_recycle_view.setNestedScrollingEnabled(true);
    }

}