package com.example.jommakan;

import androidx.room.Entity;
import androidx.room.TypeConverter;

import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.ArrayList;

@Entity(tableName = "Order", primaryKeys = {"order_id"})
public class Order {

    protected String user_email_address;
    protected int order_id;
    protected String location;
    protected String stall;
    protected ArrayList<CartFood> cart_food_list;
    protected String date;
    protected String time;

    public Order(String user_email_address, int order_id, String location, String stall, ArrayList<CartFood> cart_food_list, String date, String time) {
        this.user_email_address = user_email_address;
        this.order_id = order_id;
        this.location = location;
        this.stall = stall;
        this.cart_food_list = cart_food_list;
        this.date = date;
        this.time = time;
    }

    public String getUser_email_address() {
        return user_email_address;
    }

    public void setUser_email_address(String user_email_address) {
        this.user_email_address = user_email_address;
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
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

    public ArrayList<CartFood> getCart_food_list() {
        return cart_food_list;
    }

    public void setCart_food_list(ArrayList<CartFood> cart_food_list) {
        this.cart_food_list = cart_food_list;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
