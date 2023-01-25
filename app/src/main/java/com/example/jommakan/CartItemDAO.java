package com.example.jommakan;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.ArrayList;
import java.util.List;

/**
 * Create the DAO to access to CartItem database
 */
@Dao
public interface CartItemDAO {

    /**
     * A method is used to add cart items to database.
     * @param cartItems cart items
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertCartItem(CartItem cartItems);

    /**
     * A method to return a list of all items in cart
     * @param user_email_address user's email address
     * @return a list of all items in cart
     */
    @Query("SELECT * FROM CartItem WHERE user_email_address = :user_email_address")
    List<CartItem> getAllCartItems(String user_email_address);

    /**
     * A method to return a list of items in cart
     * @param user_email_address user's email address
     * @param location location
     * @param stall stall
     * @return a list of items in cart
     */
    @Query("SELECT * FROM CartItem WHERE user_email_address = :user_email_address AND location = :location AND stall = :stall")
    CartItem getCartItem(String user_email_address, String location, String stall);

    /**
     * A method is used to update cart items to database.
     * @param user_email_address user's email address
     * @param location location
     * @param stall stall
     * @param cart_food_list a list of food
     */
    @Query("UPDATE CartItem SET cart_food_list = :cart_food_list WHERE user_email_address = :user_email_address AND location = :location AND stall = :stall")
    void updateCartItem(String user_email_address, String location, String stall, ArrayList<CartFood> cart_food_list);

    /**
     * A method is used to delete cart items to database.
     * @param user_email_address user's email address
     * @param location location
     * @param stall stall
     */
    @Query("DELETE FROM CartItem WHERE user_email_address = :user_email_address AND location = :location AND stall = :stall")
    void deleteCartItem(String user_email_address, String location, String stall);
}
