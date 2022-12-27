package com.example.jommakan;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities =  {Food.class}, version = 1)
@TypeConverters({StringArrayListConverter.class})
public abstract class FoodDatabase extends RoomDatabase {
    public abstract FoodDAO foodDAO();
}
