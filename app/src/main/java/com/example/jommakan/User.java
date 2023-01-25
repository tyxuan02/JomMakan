package com.example.jommakan;

import androidx.annotation.NonNull;
import androidx.room.Entity;

import java.io.Serializable;

/**
 * To create User object
 */
@Entity(tableName = "Users", primaryKeys = {"user_email_address"})
public class User implements Serializable {

    /**
     * A field named user_email_address with String data type
     */
    @NonNull
    protected String user_email_address;

    /**
     * A field named username with String data type
     */
    protected String username;

    /**
     * A field named password with String data type
     */
    protected String password;

    /**
     * A field named phone_number with String data type
     */
    protected String phone_number;

    /**
     * A field named wallet_balance with double data type
     */
    protected double wallet_balance;

    /**
     * A constructor of User class
     * @param user_email_address user's email address
     * @param username username
     * @param password password
     * @param phone_number phone number
     */
    public User(@NonNull String user_email_address, String username, String password, String phone_number) {
        this.user_email_address = user_email_address;
        this.username = username;
        this.password = password;
        this.phone_number = phone_number;
    }

    /**
     * A method to return user's email address in String
     * @return user's email address
     */
    @NonNull
    public String getUser_email_address() {
        return user_email_address;
    }

    /**
     * A method to receive a String parameter and set it as user_email_address
     * @param user_email_address user's email address
     */
    public void setUser_email_address(@NonNull String user_email_address) {
        this.user_email_address = user_email_address;
    }

    /**
     * A method to return username in String
     * @return username
     */
    public String getUsername() {
        return username;
    }

    /**
     * A method to receive a String parameter and set it as username
     * @param username username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * A method to return password in String
     * @return password
     */
    @NonNull
    public String getPassword() {
        return password;
    }

    /**
     * A method to receive a String parameter and set it as password
     * @param password password
     */
    public void setPassword(@NonNull String password) {
        this.password = password;
    }

    /**
     * A method to return phone number in String
     * @return phone number
     */
    @NonNull
    public String getPhone_number() {
        return phone_number;
    }

    /**
     * A method to receive a String parameter and set it as phone_number
     * @param phone_number phone number
     */
    public void setPhone_number(@NonNull String phone_number) {
        this.phone_number = phone_number;
    }

    /**
     * A method to return wallet balance in double
     * @return wallet balance
     */
    public double getWallet_balance() {
        return wallet_balance;
    }

    /**
     * A method to receive a double parameter and set it as wallet_balance
     * @param wallet_balance wallet balance
     */
    public void setWallet_balance(double wallet_balance) {
        this.wallet_balance = wallet_balance;
    }
}
