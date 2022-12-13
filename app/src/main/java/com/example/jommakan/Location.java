package com.example.jommakan;

// To create location object
public class Location {

    protected String location;
    protected int location_image;
    protected String[] stall;

    public Location(String location, int location_image,  String [] stall) {
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

    public String[] getStall() {
        return stall;
    }

    public void setStall(String[] stall) {
        this.stall = stall;
    }
}
