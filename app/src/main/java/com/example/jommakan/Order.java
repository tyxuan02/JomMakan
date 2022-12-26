package com.example.jommakan;

import java.util.ArrayList;

public class Order {

    protected int order_id;
    protected String location;
    protected String stall;
    protected ArrayList<Food> food_list;
    protected ArrayList<Integer> food_quantity;
    protected String date;
    protected String time;

    public Order(int order_id, String location, String stall, ArrayList<Food> food_list, ArrayList<Integer> food_quantity, String date, String time) {
        this.order_id = order_id;
        this.location = location;
        this.stall = stall;
        this.food_list = food_list;
        this.food_quantity = food_quantity;
        this.date = date;
        this.time = time;
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

    public ArrayList<Food> getFood_list() {
        return food_list;
    }

    public void setFood_list(ArrayList<Food> food_list) {
        this.food_list = food_list;
    }

    public ArrayList<Integer> getFood_quantity() {
        return food_quantity;
    }

    public void setFood_quantity(ArrayList<Integer> food_quantity) {
        this.food_quantity = food_quantity;
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
