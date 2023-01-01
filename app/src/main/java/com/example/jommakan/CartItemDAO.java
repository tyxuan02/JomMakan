package com.example.jommakan;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface CartItemDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertCartItem(CartItem cartItems);

    @Query("SELECT * FROM CartItem WHERE user_email_address = :user_email_address")
    List<CartItem> getAllCartItems(String user_email_address);

    @Query("SELECT * FROM CartItem WHERE user_email_address = :user_email_address AND location = :location AND stall = :stall")
    CartItem getCartItem(String user_email_address, String location, String stall);

    @Query("UPDATE CartItem SET cart_food_list = :cart_food_list WHERE user_email_address = :user_email_address AND location = :location AND stall = :stall")
    void updateCartItem(String user_email_address, String location, String stall, ArrayList<CartFood> cart_food_list);

    @Query("DELETE FROM CartItem WHERE user_email_address = :user_email_address AND location = :location AND stall = :stall")
    void deleteCartItem(String user_email_address, String location, String stall);
}
