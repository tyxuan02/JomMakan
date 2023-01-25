package com.example.jommakan;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

/**
 * Create the DAO to access to Stall database
 */
@Dao
public interface StallDAO {

    /**
     * A method is used to add all stall to database.
     * @param stalls stall
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertAll(Stall... stalls);

    /**
     * A method to return a list of all stall
     * @param location location
     * @return a list of all stall
     */
    @Query("SELECT * FROM Stall WHERE location = :location")
    List<Stall> getAllStalls(String location);

    /**
     * A method to return a list of stall
     * @param location location
     * @param stall_name stall name
     * @return a list of stall
     */
    @Query("SELECT * FROM Stall WHERE location = :location AND stall_name = :stall_name")
    Stall getStall(String location, String stall_name);
}
