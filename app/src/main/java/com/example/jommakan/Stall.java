package com.example.jommakan;

public class Stall {

    protected String stall_name, location;
    protected Food [] food_list;
    protected String description;
    protected int stall_image;
    protected String [] openAndClose;

    public Stall(String stall_name, String location, Food [] food_list, String description, int stall_image, String [] openAndClose) {
        this.stall_name = stall_name;
        this.location = location;
        this.food_list = food_list;
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

    public Food[] getFood_list() {
        return food_list;
    }

    public void setFood_list(Food[] food_list) {
        this.food_list = food_list;
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

    public String[] getOpenAndClose() {
        return openAndClose;
    }

    public void setOpenAndClose(String [] openAndClose) {
        this.openAndClose = openAndClose;
    }
}
