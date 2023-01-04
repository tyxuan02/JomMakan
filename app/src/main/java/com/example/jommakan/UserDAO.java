package com.example.jommakan;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface UserDAO {

    @Insert (onConflict = OnConflictStrategy.IGNORE)
    void insertUser(User... users);

    @Query("SELECT * FROM Users where user_email_address = :user_email_address")
    User checkIfUserExist(String user_email_address);

    @Query("SELECT * FROM Users where user_email_address =:user_email_address and password =:password")
    User getUser(String user_email_address, String password);
}
