package com.cjianhui.android.popularmovies.utilities;

import java.util.List;

public class GenreUtils {
    public static String getGenre(int genreId) {

        String name = "";

        switch (genreId) {

            case 28:
                name = "Action";
                break;
            case 12:
                name = "Adventure";
                break;
            case 16:
                name = "Animation";
                break;
            case 35:
                name = "Comedy";
                break;
            case 80:
                name = "Crime";
                break;
            case 99:
                name = "Documentary";
                break;
            case 18:
                name = "Drama";
                break;
            case 10751:
                name = "Family";
                break;
            case 14:
                name = "Fantasy";
                break;
            case 10769:
                name = "Foreign";
                break;
            case 36:
                name = "History";
                break;
            case 27:
                name = "Horror";
                break;
            case 10402:
                name = "Music";
                break;
            case 9648:
                name = "Mystery";
                break;
            case 10749:
                name = "Romance";
                break;
            case 878:
                name = "Science Fiction";
                break;
            case 10770:
                name = "TV Movie";
                break;
            case 53:
                name = "Thriller";
                break;
            case 10752:
                name = "War";
                break;
            case 37:
                name = "Western";
                break;
        }
        return name;
    }

    public static String getGenres(List<Integer> genreIds) {

        if (genreIds.size() <= 0 || genreIds == null) {
            return "";
        }

        StringBuilder genre = new StringBuilder();
        for (int i = 0; i < genreIds.size(); i++) {
            int id = genreIds.get(i);
            genre.append(", ").append(getGenre(id));
        }
        String genreString = genre.toString();
        return genreString.substring(genreString.length() > 0 ? 1 : 0).trim();
    }
}
