package com.example.jommakan;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface FoodDAO {

    @Insert (onConflict = OnConflictStrategy.IGNORE)
    void insertAll(Food... foods);

    @Query("SELECT * FROM Food")
    List<Food> getAllFood();

    @Query("SELECT * FROM Food LIMIT 5")
    List<Food> getTop5Food();

    @Query("SELECT * FROM Food LIMIT 3")
    List<Food> getThreeFood();

    @Query("SELECT * FROM Food WHERE stall = :stall AND location = :location")
    List<Food> getStallFood(String stall, String location);
}
