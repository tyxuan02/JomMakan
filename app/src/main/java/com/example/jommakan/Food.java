package com.example.jommakan;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.TypeConverter;

import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;

// To create food object
@Entity(tableName = "Food", primaryKeys = {"name", "location", "stall"})
public class Food implements Serializable {

    @NonNull
    protected String name, location, stall;
    protected double price;
    protected ArrayList<String> description;
    protected int image;
    protected ArrayList<String> openAndClose;

    public Food(@NonNull String name, @NonNull String location, @NonNull String stall, Double price, ArrayList<String> description, int image, ArrayList<String> openAndClose) {
        this.name = name;
        this.location = location;
        this.stall = stall;
        this.price = price;
        this.description = description;
        this.image = image;
        this.openAndClose = openAndClose;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public void setPrice(double price) {
        this.price = price;
    }

    public double getPrice() {
        return price;
    }

    public ArrayList<String> getDescription() {
        return description;
    }

    public void setDescription(ArrayList<String> description) {
        this.description = description;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public ArrayList<String> getOpenAndClose() {
        return openAndClose;
    }

    public void setOpenAndClose(ArrayList<String> openAndClose) {
        this.openAndClose = openAndClose;
    }
}