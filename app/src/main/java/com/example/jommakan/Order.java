package com.example.jommakan;

import androidx.room.Entity;

import java.util.ArrayList;

/**
 * To create Order object
 */
@Entity(tableName = "Order", primaryKeys = {"order_id"})
public class Order {

    /**
     * A field named user_email_address with String data type
     */
    protected String user_email_address;

    /**
     * A field named order_id with int data type
     */
    protected int order_id;

    /**
     * A field named location with String data type
     */
    protected String location;

    /**
     * A field named stall with String data type
     */
    protected String stall;

    /**
     * A field named cart_food_list with ArrayList of CartFood
     */
    protected ArrayList<CartFood> cart_food_list;

    /**
     * A field named date with String data type
     */
    protected String date;

    /**
     * A field named time with String data type
     */
    protected String time;

    /**
     * A constructor of Order class
     * @param user_email_address user's email address
     * @param order_id order ID
     * @param location location
     * @param stall stall
     * @param cart_food_list a list of food
     * @param date order date
     * @param time order time
     */
    public Order(String user_email_address, int order_id, String location, String stall, ArrayList<CartFood> cart_food_list, String date, String time) {
        this.user_email_address = user_email_address;
        this.order_id = order_id;
        this.location = location;
        this.stall = stall;
        this.cart_food_list = cart_food_list;
        this.date = date;
        this.time = time;
    }

    /**
     * A method to return user's email address in String
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

    /**
     * A method to return order ID in int
     * @return order ID
     */
    public int getOrder_id() {
        return order_id;
    }

    /**
     * A method to receive an int parameter and set it as order_id
     * @param order_id order ID
     */
    public void setOrder_id(int order_id) {
        this.order_id = order_id;
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
     * A method to return date in String
     * @return order date
     */
    public String getDate() {
        return date;
    }

    /**
     * A method to receive a String parameter and set it as date
     * @param date order date
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * A method to return time in String
     * @return order time
     */
    public String getTime() {
        return time;
    }

    /**
     * A method to receive a String parameter and set it as time
     * @param time oder time
     */
    public void setTime(String time) {
        this.time = time;
    }
}
