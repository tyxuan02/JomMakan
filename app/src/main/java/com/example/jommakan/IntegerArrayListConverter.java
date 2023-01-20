package com.example.jommakan;

import androidx.room.TypeConverter;

import java.util.ArrayList;

/**
 * Convert integer list to String
 */
public class IntegerArrayListConverter {
    @TypeConverter
    public ArrayList<Integer> gettingListFromString(String genreIds) {
        ArrayList<Integer> list = new ArrayList<>();

        String[] array = genreIds.split(",");

        for (String s : array) {
            if (!s.isEmpty()) {
                list.add(Integer.parseInt(s));
            }
        }
        return list;
    }

    @TypeConverter
    public String writingStringFromList(ArrayList<Integer> list) {
        String genreIds = "";
        for (int i : list) {
            genreIds += "," + i;
        }
        return genreIds;
    }
}
