package com.example.jommakan;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities =  {Stall.class}, version = 1)
@TypeConverters({StringArrayListConverter.class})
public abstract class StallDatabase extends RoomDatabase {
    public abstract StallDAO stallDAO();
}
