package com.example.jommakan;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

/**
 * Create the DAO to access to Location database
 */
@Dao
public interface LocationDAO {

    /**
     * A method is used to add all location to database.
     * @param locations location
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertAll(Location... locations);

    /**
     * A method to return a list of all location
     * @return a list of all location
     */
    @Query("SELECT * FROM Location")
    List<Location> getAllLocations();

    /**
     * A method to return a list of random location
     * @return a list of random location
     */
    @Query("SELECT * FROM Location LIMIT 3")
    List<Location> getRandomLocations();
}
