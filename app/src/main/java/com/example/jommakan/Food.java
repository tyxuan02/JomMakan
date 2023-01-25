package com.example.jommakan;

import androidx.annotation.NonNull;
import androidx.room.Entity;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * To create Food object
 */
@Entity(tableName = "Food", primaryKeys = {"name", "location", "stall"})
public class Food implements Serializable {

    /**
     * A field named name with String data type
     */
    @NonNull
    protected String name;

    /**
     * A field named location with String data type
     */
    @NonNull
    protected String location;

    /**
     * A field named stall with String data type
     */
    @NonNull
    protected String stall;

    /**
     * A field named price with double data type
     */
    protected double price;

    /**
     * A field named description with ArrayList of String
     */
    protected ArrayList<String> description;

    /**
     * A field named image with int data type
     */
    protected int image;

    /**
     * A field named openAndClose with ArrayList of String
     */
    protected ArrayList<String> openAndClose;

    /**
     * A constructor of Food class
     * @param name food name
     * @param location location
     * @param stall stall
     * @param price food price
     * @param description description
     * @param image food image
     * @param openAndClose stall open and close time
     */
    public Food(@NonNull String name, @NonNull String location, @NonNull String stall, Double price, ArrayList<String> description, int image, ArrayList<String> openAndClose) {
        this.name = name;
        this.location = location;
        this.stall = stall;
        this.price = price;
        this.description = description;
        this.image = image;
        this.openAndClose = openAndClose;
    }

    /**
     * A method to return name in String
     * @return food name
     */
    public String getName() {
        return name;
    }

    /**
     * A method to receive a String parameter and set it as name
     * @param name food name
     */
    public void setName(String name) {
        this.name = name;
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
     * A method to receive a double parameter and set it as price
     * @param price food price
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * A method to return price in double
     * @return food price
     */
    public double getPrice() {
        return price;
    }

    /**
     * A method to return a list of description
     * @return a list of description
     */
    public ArrayList<String> getDescription() {
        return description;
    }

    /**
     * A method to receive an ArrayList of String parameter and set it description
     * @param description food description
     */
    public void setDescription(ArrayList<String> description) {
        this.description = description;
    }

    /**
     * A method to return image in int
     * @return food image
     */
    public int getImage() {
        return image;
    }

    /**
     * A method to receive a int parameter and set it as image
     * @param image food image
     */
    public void setImage(int image) {
        this.image = image;
    }

    /**
     * A method to return a list of stall open and close time
     * @return a list of stall open and close time
     */
    public ArrayList<String> getOpenAndClose() {
        return openAndClose;
    }

    /**
     * A method to receive an ArrayList of String parameter and set it openAndClose
     * @param openAndClose stall open and close time
     */
    public void setOpenAndClose(ArrayList<String> openAndClose) {
        this.openAndClose = openAndClose;
    }
}