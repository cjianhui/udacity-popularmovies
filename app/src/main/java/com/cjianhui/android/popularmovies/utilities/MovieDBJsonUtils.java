package com.cjianhui.android.popularmovies.utilities;

import com.cjianhui.android.popularmovies.models.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Utility functions to handle the themoviedb.org JSON data.
 */
public final class MovieDBJsonUtils {

    /**
     * This method parses JSON from a web response and returns an array of Movie objects
     * containing the details for each movie.
     * <p/>
     *
     * @param json JSON response from server
     *
     * @return Array of Movies containing movie details
     *
     * @throws JSONException If JSON data cannot be properly parsed
     */
    public static Movie[] parseMovieJson(String json) throws JSONException {
        /*
            Constants for obtaining values from MovieDBJson
         */

        /* Movie information. Each movie is an element of the "results" array */
        final String MOVIE_RESULTS = "results";

        /* Movie details */
        final String MOVIE_ID = "id";
        final String MOVIE_VOTE_COUNT = "vote_count";
        final String MOVIE_IS_VIDEO = "video";
        final String MOVIE_VOTE_AVG = "vote_average";
        final String MOVIE_TITLE = "title";
        final String MOVIE_POPULARITY = "popularity";
        final String MOVIE_POSTER_PATH = "poster_path";
        final String MOVIE_ORIGINAL_LANG = "original_language";
        final String MOVIE_ORIGINAL_TITLE = "original_title";
        final String MOVIE_GENRE_IDS = "genre_ids";
        final String MOVIE_BACKDROP_PATH = "backdrop_path";
        final String MOVIE_IS_ADULT = "adult";
        final String MOVIE_OVERVIEW = "overview";
        final String MOVIE_RELEASE_DATE = "release_date";

        Movie[] parsedMovies = null;
        JSONObject moviesJson = new JSONObject(json);


        JSONArray moviesArray = moviesJson.getJSONArray(MOVIE_RESULTS);
        parsedMovies = new Movie[moviesArray.length()];

        for (int i = 0; i < moviesArray.length(); i++) {
            JSONObject movieObject = moviesArray.getJSONObject(i);
            int id = movieObject.getInt(MOVIE_ID);
            int voteCount = movieObject.getInt(MOVIE_VOTE_COUNT);
            boolean isVideo = movieObject.getBoolean(MOVIE_IS_VIDEO);
            double voteAverage = movieObject.getDouble(MOVIE_VOTE_AVG);
            String title = movieObject.getString(MOVIE_TITLE);
            double popularity = movieObject.getDouble(MOVIE_POPULARITY);
            String posterPath = movieObject.getString(MOVIE_POSTER_PATH);
            String originalLanguage = movieObject.getString(MOVIE_ORIGINAL_LANG);
            String originalTitle = movieObject.getString(MOVIE_ORIGINAL_TITLE);
            JSONArray genreIdsArray = movieObject.getJSONArray(MOVIE_GENRE_IDS);
            List<Integer> genreIds = new ArrayList<>();

            for (int j = 0; i < genreIdsArray.length(); j++) {
                   genreIds.add(genreIdsArray.getInt(i));
            }

            String movieBackdropPath = movieObject.getString(MOVIE_BACKDROP_PATH);
            boolean isAdult = movieObject.getBoolean(MOVIE_IS_ADULT);
            String overview = movieObject.getString(MOVIE_OVERVIEW);
            String releaseDate = movieObject.getString(MOVIE_RELEASE_DATE);

            Movie movie = new Movie(id, voteCount, isVideo, voteAverage, title, popularity, posterPath, originalLanguage, originalTitle, genreIds,
                    movieBackdropPath, isAdult, overview, releaseDate);

            parsedMovies[i] = movie;

        }

        return parsedMovies;
     }
}
