package com.example.jommakan;

import androidx.annotation.NonNull;
import androidx.room.Entity;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * To create Stall object
 */
@Entity(tableName = "Stall", primaryKeys = {"stall_name", "location"})
public class Stall implements Serializable {

    /**
     * A field named stall_name with String data type
     */
    @NonNull
    protected String stall_name;

    /**
     * A field named location with String data type
     */
    @NonNull
    protected String location;

    /**
     * A field named food_name_list with ArrayList of String
     */
    protected ArrayList<String> food_name_list;

    /**
     * A field named description with String data type
     */
    protected String description;

    /**
     * A field named stall_image with int data type
     */
    protected int stall_image;

    /**
     * A field named openAndClose with ArrayList of String
     */
    protected ArrayList<String> openAndClose;

    /**
     * A constructor of Stall class
     * @param stall_name stall name
     * @param location location
     * @param food_name_list a list of food name
     * @param description food description
     * @param stall_image stall image
     * @param openAndClose stall open and close time
     */
    public Stall(@NonNull String stall_name, @NonNull String location, ArrayList<String> food_name_list, String description, int stall_image, ArrayList<String> openAndClose) {
        this.stall_name = stall_name;
        this.location = location;
        this.food_name_list = food_name_list;
        this.description = description;
        this.stall_image = stall_image;
        this.openAndClose = openAndClose;
    }

    /**
     * A method to return stall name in String
     * @return stall name
     */
    public String getStall_name() {
        return stall_name;
    }

    /**
     * A method to receive a String parameter and set it as stall_name
     * @param stall_name stall name
     */
    public void setStall_name(String stall_name) {
        this.stall_name = stall_name;
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
     * A method to return a list of food name
     * @return a list of food name
     */
    public ArrayList<String> getFood_list() {
        return food_name_list;
    }

    /**
     * A method to receive an ArrayList of String parameter and set it as food_list
     * @param food_list a list of food name
     */
    public void setFood_list(ArrayList<String> food_list) {
        this.food_name_list = food_list;
    }

    /**
     * A method to return food description in String
     * @return food description
     */
    public String getDescription() {
        return description;
    }

    /**
     * A method to receive a String parameter and set it as description
     * @param description food description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * A method to return stall image in int
     * @return stall image
     */
    public int getStall_image() {
        return stall_image;
    }

    /**
     * A method to receive an int parameter and set it as stall_image
     * @param stall_image stall image
     */
    public void setStall_image(int stall_image) {
        this.stall_image = stall_image;
    }

    /**
     * A method to return a list stall open and close time
     * @return a list stall open and close time
     */
    public ArrayList<String> getOpenAndClose() {
        return openAndClose;
    }

    /**
     * A method to receive an ArrayList of String parameter and set it as openAndClose
     * @param openAndClose stall open and close time
     */
    public void setOpenAndClose(ArrayList<String> openAndClose) {
        this.openAndClose = openAndClose;
    }
}
