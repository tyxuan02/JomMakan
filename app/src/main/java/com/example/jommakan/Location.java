package com.example.jommakan;

import androidx.annotation.NonNull;
import androidx.room.Entity;

import java.util.ArrayList;

/**
 * To create Location object
 */
@Entity(tableName = "Location", primaryKeys = {"location"})
public class Location {

    /**
     * A field named location with String data type
     */
    @NonNull
    protected String location;

    /**
     * A field named location_image with int data type
     */
    protected int location_image;

    /**
     * A field named stall with ArrayList of String
     */
    protected ArrayList<String> stall;

    /**
     * A constructor of Location class
     * @param location location
     * @param location_image location image
     * @param stall stall
     */
    public Location(@NonNull String location, int location_image,  ArrayList<String> stall) {
        this.location = location;
        this.location_image = location_image;
        this.stall = stall;
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
     * A method to return location image in int
     * @return location image
     */
    public int getLocation_image() {
        return location_image;
    }

    /**
     * A method to receive an int parameter and set it as location_image
     * @param location_image location image
     */
    public void setLocation_image(int location_image) {
        this.location_image = location_image;
    }

    /**
     * A method to return a list of stall
     * @return a list of stall
     */
    public ArrayList<String> getStall() {
        return stall;
    }

    /**
     * A method to receive an ArrayList of String parameter and set it stall
     * @param stall stall
     */
    public void setStall(ArrayList<String> stall) {
        this.stall = stall;
    }
}
