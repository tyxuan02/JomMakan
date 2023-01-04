package com.example.jommakan;

import androidx.annotation.NonNull;
import androidx.room.Entity;

import java.io.Serializable;
import java.util.ArrayList;

@Entity(tableName = "Users", primaryKeys = {"user_email_address"})
public class User implements Serializable {
    @NonNull
    protected String user_email_address;
    protected String username, password, phone_number;
    protected double wallet_balance;
    //protected ArrayList<Integer> order_id;

    User(@NonNull String user_email_address, String username, String password, String phone_number,
         double wallet_balance) {
        this.user_email_address = user_email_address;
        this.username = username;
        this.password = password;
        this.phone_number = phone_number;
        this.wallet_balance = wallet_balance;
        //this.order_id = order_id;
    }
/*
    public User(@NonNull String user_email_address, String username, String password, String confirm_password, String phone_number) {
        this.user_email_address = user_email_address;
        this.username = username;
        this.password = password;
        this.confirm_password = confirm_password;
        this.phone_number = phone_number;
    }*/

    @NonNull
    public String getUser_email_address() {
        return user_email_address;
    }

    public void setUser_email_address(@NonNull String user_email_address) {
        this.user_email_address = user_email_address;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @NonNull
    public String getPassword() {
        return password;
    }

    public void setPassword(@NonNull String password) {
        this.password = password;
    }

    @NonNull
    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(@NonNull String phone_number) {
        this.phone_number = phone_number;
    }

    public double getWallet_balance() {
        return wallet_balance;
    }

    public void setWallet_balance(double wallet_balance) {
        this.wallet_balance = wallet_balance;
    }
/*
    public ArrayList<Integer> getOrder_id() {
        return order_id;
    }

    public void setOrder_id(ArrayList<Integer> order_id) {
        this.order_id = order_id;
    }*/
}
