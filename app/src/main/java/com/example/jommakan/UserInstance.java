package com.example.jommakan;

import java.util.ArrayList;

public class UserInstance {

    private static String user_email_address;
    private static String username, password, phone_number;
    private static double wallet_balance;
    private static ArrayList<Integer> order_id;

    public static String getUser_email_address() {
        return user_email_address;
    }

    public static void setUser_email_address(String user_email_address) {
        UserInstance.user_email_address = user_email_address;
    }

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        UserInstance.username = username;
    }

    public static String getPassword() {
        return password;
    }

    public static void setPassword(String password) {
        UserInstance.password = password;
    }

    public static String getPhone_number() {
        return phone_number;
    }

    public static void setPhone_number(String phone_number) {
        UserInstance.phone_number = phone_number;
    }

    public static double getWallet_balance() {
        return wallet_balance;
    }

    public static void setWallet_balance(double wallet_balance) {
        UserInstance.wallet_balance = wallet_balance;
    }

    public static ArrayList<Integer> getOrder_id() {
        return order_id;
    }

    public static void setOrder_id(ArrayList<Integer> order_id) {
        UserInstance.order_id = order_id;
    }
}
