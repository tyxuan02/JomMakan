package com.example.jommakan;

import androidx.annotation.NonNull;
import androidx.room.Entity;

import java.io.Serializable;
import java.util.ArrayList;

@Entity(tableName = "CartItem", primaryKeys = {"user_email_address", "location", "stall"})
public class CartItem implements Serializable {

    protected ArrayList<CartFood> cart_food_list;

    @NonNull
    protected String location;

    @NonNull
    protected String stall;

    @NonNull
    protected String user_email_address;

    public CartItem(@NonNull String user_email_address, @NonNull String location, @NonNull String stall, ArrayList<CartFood> cart_food_list) {
        this.cart_food_list = cart_food_list;
        this.location = location;
        this.stall = stall;
        this.user_email_address = user_email_address;
    }

    public ArrayList<CartFood> getCart_food_list() {
        return cart_food_list;
    }

    public void setCart_food_list(ArrayList<CartFood> cart_food_list) {
        this.cart_food_list = cart_food_list;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getStall() {
        return stall;
    }

    public void setStall(String stall) {
        this.stall = stall;
    }

    public String getUser_email_address() {
        return user_email_address;
    }

    public void setUser_email_address(String user_email_address) {
        this.user_email_address = user_email_address;
    }
}
