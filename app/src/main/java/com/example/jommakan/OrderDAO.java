package com.example.jommakan;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

/**
 * Create the DAO to access to Order database
 */
@Dao
public interface OrderDAO {

    /**
     * A method is used to add order to database.
     * @param order order
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Order order);

    /**
     * A method to return a list of order from user
     * @param user_email_address user's email address
     * @return
     */
    @Query("SELECT * FROM `Order` WHERE user_email_address = :user_email_address ORDER BY time DESC")
    List<Order> getUserOrder(String user_email_address);
}
