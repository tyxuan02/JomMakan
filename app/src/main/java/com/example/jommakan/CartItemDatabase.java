package com.example.jommakan;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities =  {CartItem.class}, version = 1)
@TypeConverters({StringArrayListConverter.class, CartFoodListTypeConverter.class})
public abstract class CartItemDatabase extends RoomDatabase {
    public abstract CartItemDAO cartItemDAO();
}


