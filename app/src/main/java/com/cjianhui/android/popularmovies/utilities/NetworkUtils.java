package com.cjianhui.android.popularmovies.utilities;

import android.net.Uri;
import android.util.Log;

import com.cjianhui.android.popularmovies.BuildConfig;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * These utilities will be used to communicate with themoviedb.org API.
 */
public class NetworkUtils {
    private static final String TAG = NetworkUtils.class.getSimpleName();

    private static final String API_KEY = BuildConfig.MOVIEDB_API_KEY;

    private static final String MOVIEDB_BASE_URL =
            "http://api.themoviedb.org/3/movie";

    /* Query Parameters */
    private final static String API_KEY_PARAM = "api_key";

    /* Endpoints */
    private final static String POPULAR_ENDPOINT = "popular";
    private final static String TOP_RATED_ENDPOINT = "top_rated";


    /**
     * Builds the URL used to talk to the moviedb server using a sorting criteria (popular/top rated)..
     *
     * @param endpoint The endpoint with which we query the moviedb with.
     * @return The URL to use to query the weather server.
     */
    public static URL buildUrl(String endpoint) {

        /* Top rated endpoint used by default */
        String ENDPOINT = TOP_RATED_ENDPOINT;

        if (endpoint.equals("popular")) {
            ENDPOINT = POPULAR_ENDPOINT;
        }

        Uri builtUri = Uri.parse(MOVIEDB_BASE_URL).buildUpon()
                .appendPath(ENDPOINT)
                .appendQueryParameter(API_KEY_PARAM, API_KEY)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Log.v(TAG, "Built URI " + url);

        return url;
    }

    /**
     * This method returns the entire result from the HTTP response.
     *
     * @param url The URL to fetch the HTTP response from.
     * @return The contents of the HTTP response.
     * @throws IOException Related to network and stream reading
     */
    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }
}
