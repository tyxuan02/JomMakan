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
    void insertUserData(User... users);

    @Insert
    void registerUser (User user);

    @Query("SELECT * FROM Users where user_email_address = :user_email_address")
    User checkIfExist(String user_email_address);

    @Query("SELECT * FROM Users where user_email_address =:user_email_address and password =:password")
    User login(String user_email_address, String password);

    @Query("select * from users")
    List<User> getAllUser();

    @Insert
    void addUser(User user);

    @Update
    void updateUser(User user);

    @Delete
    void deleteUser(User user);


}
