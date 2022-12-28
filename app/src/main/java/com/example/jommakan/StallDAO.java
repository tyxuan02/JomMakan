package com.example.jommakan;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface StallDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertAll(Stall... stalls);

    @Query("SELECT * FROM Stall WHERE location = :location")
    List<Stall> getAllStalls(String location);
}
