package com.example.jommakan;

import java.util.ArrayList;

// To create location object
public class Location {

    protected String location;
    protected int location_image;
    protected ArrayList<String> stall;

    public Location(String location, int location_image,  ArrayList<String> stall) {
        this.location = location;
        this.location_image = location_image;
        this.stall = stall;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getLocation_image() {
        return location_image;
    }

    public void setLocation_image(int location_image) {
        this.location_image = location_image;
    }

    public ArrayList<String> getStall() {
        return stall;
    }

    public void setStall(ArrayList<String> stall) {
        this.stall = stall;
    }
}
