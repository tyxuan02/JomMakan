package com.example.jommakan;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

/**
 * Create a Room Database for Location - a database abstraction layer on top of sqlite
 */
@Database(entities = {Location.class}, version = 1)
@TypeConverters({StringArrayListConverter.class})
public abstract class LocationDatabase extends RoomDatabase {
    public abstract LocationDAO locationDAO();
}
