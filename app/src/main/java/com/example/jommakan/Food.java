package com.example.jommakan;

// To create food object
public class Food {

    protected String name, location, stall;
    protected double price;
    protected String [] description;
    protected int image;
    //private String operation_hour;

    public Food (String name, String location, String stall, Double price, String [] description, int image) {
        this.name = name;
        this.location = location;
        this.stall = stall;
        this.price = price;
        this.description = description;
        this.image = image;
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

    public String[] getDescription() {
        return description;
    }

    public void setDescription(String[] description) {
        this.description = description;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
