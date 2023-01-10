package com.example.jommakan;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * A class that is used to store the food in cart item
 */
public class CartFood implements Serializable {

    /**
     * Food name
     */
    protected String food_name;

    /**
     * Food quantity
     */
    protected int quantity;

    /**
     * Food price
     */
    protected double price;

    /**
     *
     * @param food_name Food name
     * @param quantity Food quantity
     * @param price Food price
     */
    public CartFood(String food_name, int quantity, double price) {
        this.food_name = food_name;
        this.quantity = quantity;
        this.price = price;
    }

    /**
     * Get the food name
     * @return food name
     */
    public String getFood_name() {
        return food_name;
    }

    /**
     * Set the food name
     * @param food_name food name
     */
    public void setFood_name(String food_name) {
        this.food_name = food_name;
    }

    /**
     * Get the food quantity
     * @return food quantity
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Set the food quantiy
     * @param quantity food quantity
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * Get the food price
     * @return food price
     */
    public double getPrice() {
        return price;
    }

    /**
     * Set the food price
     * @param price food price
     */
    public void setPrice(double price) {
        this.price = price;
    }
}

/**
 * Convert cart food list type to String
 */
class CartFoodListTypeConverter {
    @TypeConverter
    public static ArrayList<CartFood> fromString(String value) {
        Type listType = new TypeToken<ArrayList<CartFood>>() {}.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String fromArrayList(ArrayList<CartFood> list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }
}
