package com.cjianhui.android.popularmovies.data.database;

import android.arch.persistence.room.TypeConverter;

import java.util.ArrayList;
import java.util.List;

public class GenreListConverter {
    @TypeConverter
    public static String toString(List<Integer> genreIds) {
        String genreIdsString = "";
        if (genreIds.size() == 1) {
            return Integer.toString(genreIds.get(0));
        }
        for (int i : genreIds) {
            genreIdsString += "," + i;
        }
        return genreIdsString;
    }

    @TypeConverter
    public static List<Integer> fromString(String genreIdsString) {
        List<Integer> genreIds = new ArrayList<>();

        String[] array = genreIdsString.split(",");

        for (String s : array) {
            if (!s.isEmpty()) {
                genreIds.add(Integer.parseInt(s));
            }
        }
        return genreIds;
    }
}
