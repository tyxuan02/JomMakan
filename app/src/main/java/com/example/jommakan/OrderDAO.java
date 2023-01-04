package com.example.jommakan;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface OrderDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Order order);

    @Query("SELECT * FROM `Order` WHERE user_email_address = :user_email_address ORDER BY date DESC")
    List<Order> getUserOrder(String user_email_address);
}
