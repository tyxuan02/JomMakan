package com.example.jommakan;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

/**
 * Create a Room Database for CartItem - a database abstraction layer on top of sqlite
 */
@Database(entities = {CartItem.class}, version = 1)
@TypeConverters({StringArrayListConverter.class, CartFoodListTypeConverter.class})
public abstract class CartItemDatabase extends RoomDatabase {
    public abstract CartItemDAO cartItemDAO();
}


