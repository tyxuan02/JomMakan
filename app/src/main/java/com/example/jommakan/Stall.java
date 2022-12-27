package com.example.jommakan;

import androidx.annotation.NonNull;
import androidx.room.Entity;

import java.util.ArrayList;

@Entity(tableName = "Stall", primaryKeys = {"stall_name", "location"})
public class Stall {

    @NonNull
    protected String stall_name, location;
    protected ArrayList<String> food_name_list;
    protected String description;
    protected int stall_image;
    protected ArrayList<String> openAndClose;

    public Stall(@NonNull String stall_name, @NonNull String location, ArrayList<String> food_name_list, String description, int stall_image, ArrayList<String> openAndClose) {
        this.stall_name = stall_name;
        this.location = location;
        this.food_name_list = food_name_list;
        this.description = description;
        this.stall_image = stall_image;
        this.openAndClose = openAndClose;
    }

    public String getStall_name() {
        return stall_name;
    }

    public void setStall_name(String stall_name) {
        this.stall_name = stall_name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public ArrayList<String> getFood_list() {
        return food_name_list;
    }

    public void setFood_list(ArrayList<String> food_list) {
        this.food_name_list = food_list;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getStall_image() {
        return stall_image;
    }

    public void setStall_image(int stall_image) {
        this.stall_image = stall_image;
    }

    public ArrayList<String> getOpenAndClose() {
        return openAndClose;
    }

    public void setOpenAndClose(ArrayList<String> openAndClose) {
        this.openAndClose = openAndClose;
    }
}
