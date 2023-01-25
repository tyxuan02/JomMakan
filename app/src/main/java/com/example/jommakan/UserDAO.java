package com.example.jommakan;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

/**
 * Create the DAO to access to User database
 */
@Dao
public interface UserDAO {

    /**
     * A method is used to add user to database.
     * @param users user
     */
    @Insert (onConflict = OnConflictStrategy.IGNORE)
    void insertUser(User... users);

    /**
     * A method is used to check the validation of the user
     * @param user_email_address user's email address
     * @return the validation of the user
     */
    @Query("SELECT * FROM Users where user_email_address = :user_email_address")
    User checkIfUserExist(String user_email_address);

    /**
     * A method to return user
     * @param user_email_address user's email address
     * @param password password
     * @return user
     */
    @Query("SELECT * FROM Users where user_email_address =:user_email_address and password =:password")
    User getUser(String user_email_address, String password);

    /**
     * A method is used to update changing password to database.
     * @param user_email_address user's email address
     * @param password password
     */
    @Query("UPDATE Users SET password = :password WHERE user_email_address = :user_email_address")
    void changePassword(String user_email_address, String password);

    /**
     * A method is used to update user's wallet balance to database.
     * @param wallet_balance wallet balance
     * @param user_email_address user's email address
     */
    @Query("UPDATE Users SET wallet_balance = :wallet_balance WHERE user_email_address = :user_email_address")
    void updateWalletBalance(double wallet_balance, String user_email_address);
}
