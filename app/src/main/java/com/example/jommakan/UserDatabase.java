package com.example.jommakan;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities =  {User.class}, version = 1)
@TypeConverters({IntegerArrayListConverter.class})
public abstract class UserDatabase extends RoomDatabase {
    public abstract UserDAO userDAO();
}
