package com.example.jommakan;

public class UserHolder {

    private static String user_email_address;
    private static String username, password, phone_number;
    private static double wallet_balance;

    public static String getUser_email_address() {
        return user_email_address;
    }

    public static void setUser_email_address(String user_email_address) {
        UserHolder.user_email_address = user_email_address;
    }

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        UserHolder.username = username;
    }

    public static String getPassword() {
        return password;
    }

    public static void setPassword(String password) {
        UserHolder.password = password;
    }

    public static String getPhone_number() {
        return phone_number;
    }

    public static void setPhone_number(String phone_number) {
        UserHolder.phone_number = phone_number;
    }

    public static double getWallet_balance() {
        return wallet_balance;
    }

    public static void setWallet_balance(double wallet_balance) {
        UserHolder.wallet_balance = wallet_balance;
    }
}
