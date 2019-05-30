package com.cjianhui.android.popularmovies;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ProgressBar;

import com.cjianhui.android.popularmovies.models.Movie;
import com.cjianhui.android.popularmovies.utilities.MovieDBJsonUtils;
import com.cjianhui.android.popularmovies.utilities.NetworkUtils;

import java.net.URL;


public class MainActivity extends AppCompatActivity {

    private enum Sort {
        popular, top_rated
    }

    private static final String TAG = MainActivity.class.getSimpleName();

    private ProgressBar mLoadingIndicator;
    private String sortBy = Sort.top_rated.name();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);

        mLoadingIndicator = (ProgressBar) findViewById(R.id.pb_loading_indicator);
        new FetchMoviesTask().execute(sortBy);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        switch (sortBy) {
            case "popular":
                menu.findItem(R.id.sort_by_popularity).setChecked(true);
                return true;
            case "top_rated":
                menu.findItem(R.id.sort_by_rating).setChecked(true);
                return true;
            default:
                return super.onPrepareOptionsMenu(menu);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.movies_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.sort_by_popularity:
                item.setChecked(!item.isChecked());
                updateSort(Sort.popular.name());
                return true;

            case R.id.sort_by_rating:
                item.setChecked(!item.isChecked());
                updateSort(Sort.top_rated.name());
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void updateSort(String sortBy) {
        this.sortBy = sortBy;
    }

    public class FetchMoviesTask extends AsyncTask<String, Void, Movie[]> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Movie[] doInBackground(String... params) {

            String sortBy = params[0];
            URL moviesRequestUrl = NetworkUtils.buildUrl(sortBy);

            try {
                String jsonMoviesResponse = NetworkUtils
                        .getResponseFromHttpUrl(moviesRequestUrl);
                Log.v(TAG, "Movies Json Response: " + jsonMoviesResponse);

                Movie[] movies = MovieDBJsonUtils
                        .parseMovieJson(jsonMoviesResponse);

                return movies;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(Movie[] movies) {
            super.onPostExecute(movies);
        }
    }


}
