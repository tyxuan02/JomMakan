package com.example.jommakan;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class CartFood implements Serializable {

    protected String food_name;
    protected int quantity;
    protected double price;

    public CartFood(String food_name, int quantity, double price) {
        this.food_name = food_name;
        this.quantity = quantity;
        this.price = price;
    }

    public String getFood_name() {
        return food_name;
    }

    public void setFood_name(String food_name) {
        this.food_name = food_name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}

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
