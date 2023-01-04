package com.example.jommakan;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities =  {Order.class}, version = 1)
@TypeConverters({CartFoodListTypeConverter.class})
public abstract class OrderDatabase extends RoomDatabase {
    public abstract OrderDAO orderDAO();
}
