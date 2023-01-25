package com.example.jommakan;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

/**
 * Create the DAO to access to Food database
 */
@Dao
public interface FoodDAO {

    /**
     * A method is used to add all food to database.
     * @param foods food
     */
    @Insert (onConflict = OnConflictStrategy.IGNORE)
    void insertAll(Food... foods);

    /**
     * A method to return a list of all food
     * @return a list of all food
     */
    @Query("SELECT * FROM Food")
    List<Food> getAllFood();

    /**
     * A method to return a list of top five food
     * @return a list of top five food
     */
    @Query("SELECT * FROM Food LIMIT 5")
    List<Food> getTop5Food();

    /**
     * A method to return a list of three food
     * @return a list of three food
     */
    @Query("SELECT * FROM Food LIMIT 3")
    List<Food> getThreeFood();

    /**
     * A method to return a list of the stall food
     * @param stall stall
     * @param location location
     * @return a list of the stall food
     */
    @Query("SELECT * FROM Food WHERE stall = :stall AND location = :location")
    List<Food> getStallFood(String stall, String location);
}
