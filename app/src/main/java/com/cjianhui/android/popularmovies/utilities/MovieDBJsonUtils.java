package com.cjianhui.android.popularmovies.utilities;

import com.cjianhui.android.popularmovies.models.Movie;

import org.json.JSONException;
import org.json.JSONObject;

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

        JSONObject moviesJson = new JSONObject(json);
     }
}
