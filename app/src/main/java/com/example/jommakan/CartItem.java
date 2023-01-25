package com.example.jommakan;

import androidx.annotation.NonNull;
import androidx.room.Entity;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * To create CartItem object
 */
@Entity(tableName = "CartItem", primaryKeys = {"user_email_address", "location", "stall"})
public class CartItem implements Serializable {

    /**
     * A field named cart_food_list with ArrayList of CartFood
     */
    protected ArrayList<CartFood> cart_food_list;

    /**
     * A field named location with String data type
     */
    @NonNull
    protected String location;

    /**
     * A field named stall with String data type
     */
    @NonNull
    protected String stall;

    /**
     * A field named user_email_address with String data type
     */
    @NonNull
    protected String user_email_address;

    /**
     * A constructor of CartItem class
     * @param user_email_address user's email address
     * @param location location
     * @param stall stall
     * @param cart_food_list a list of food
     */
    public CartItem(@NonNull String user_email_address, @NonNull String location, @NonNull String stall, ArrayList<CartFood> cart_food_list) {
        this.cart_food_list = cart_food_list;
        this.location = location;
        this.stall = stall;
        this.user_email_address = user_email_address;
    }

    /**
     * A method to return a list of food
     * @return a list of food
     */
    public ArrayList<CartFood> getCart_food_list() {
        return cart_food_list;
    }

    /**
     * A method to receive an ArrayList of CartFood parameter and set it as cart_food_list
     * @param cart_food_list a list of food
     */
    public void setCart_food_list(ArrayList<CartFood> cart_food_list) {
        this.cart_food_list = cart_food_list;
    }

    /**
     * A method to return location in String
     * @return location
     */
    public String getLocation() {
        return location;
    }

    /**
     * A method to receive a String parameter and set it as location
     * @param location location
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * A method to return stall in String
     * @return stall
     */
    public String getStall() {
        return stall;
    }

    /**
     * A method to receive a String parameter and set it as stall
     * @param stall stall
     */
    public void setStall(String stall) {
        this.stall = stall;
    }

    /**
     * A method to return user_email_address in String
     * @return user's email address
     */
    public String getUser_email_address() {
        return user_email_address;
    }

    /**
     * A method to receive a String parameter and set it as user_email_address
     * @param user_email_address user's email address
     */
    public void setUser_email_address(String user_email_address) {
        this.user_email_address = user_email_address;
    }
}
