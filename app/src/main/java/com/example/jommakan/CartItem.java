package com.example.jommakan;

import androidx.annotation.NonNull;
import androidx.room.Entity;

import java.util.ArrayList;

@Entity(tableName = "CartItem", primaryKeys = {"user_email_address", "location", "stall"})
public class CartItem {

    protected ArrayList<String> selected_food_name;
    protected ArrayList<Integer> selected_food_quantity;
    protected ArrayList<Double> selected_food_price;

    @NonNull
    protected String location;

    @NonNull
    protected String stall;

    @NonNull
    protected String user_email_address;

    public CartItem(ArrayList<String> selected_food_name, ArrayList<Integer> selected_food_quantity, ArrayList<Double> selected_food_price, @NonNull String location, @NonNull String stall, @NonNull String user_email_address) {
        this.selected_food_name = selected_food_name;
        this.selected_food_quantity = selected_food_quantity;
        this.selected_food_price = selected_food_price;
        this.location = location;
        this.stall = stall;
        this.user_email_address = user_email_address;
    }

    public ArrayList<String> getSelected_food_name() {
        return selected_food_name;
    }

    public void setSelected_food_name(ArrayList<String> selected_food_name) {
        this.selected_food_name = selected_food_name;
    }

    public ArrayList<Integer> getSelected_food_quantity() {
        return selected_food_quantity;
    }

    public void setSelected_food_quantity(ArrayList<Integer> selected_food_quantity) {
        this.selected_food_quantity = selected_food_quantity;
    }

    public ArrayList<Double> getSelected_food_price() {
        return selected_food_price;
    }

    public void setSelected_food_price(ArrayList<Double> selected_food_price) {
        this.selected_food_price = selected_food_price;
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
