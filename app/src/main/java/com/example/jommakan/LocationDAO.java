package com.example.jommakan;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface LocationDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertAll(Location... locations);

    @Query("SELECT * FROM Location")
    List<Location> getAllLocations();

    @Query("SELECT * FROM Location ORDER BY random() LIMIT 3")
    List<Location> getRandomLocations();
}
