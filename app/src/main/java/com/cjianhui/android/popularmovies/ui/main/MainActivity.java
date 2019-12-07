package com.cjianhui.android.popularmovies.ui.main;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.DimenRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.cjianhui.android.popularmovies.R;
import com.cjianhui.android.popularmovies.models.Movie;
import com.cjianhui.android.popularmovies.ui.detail.MovieDetailActivity;
import com.cjianhui.android.popularmovies.utilities.MovieDBJsonUtils;
import com.cjianhui.android.popularmovies.utilities.NetworkUtils;

import java.net.URL;


public class MainActivity extends AppCompatActivity implements MoviesAdapter.MoviesAdapterOnClickHandler,
        LoaderManager.LoaderCallbacks<Movie[]> {

    private enum Sort {
        popular, top_rated
    }

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int NUM_COLUMNS = 2;

    private TextView mErrorMessageDisplay;
    private RecyclerView mRecyclerView;
    private ProgressBar mLoadingIndicator;

    private static final int MOVIES_LOADER_ID = 0;

    private MoviesAdapter mMovieAdapter;
    private String sortBy = Sort.popular.name();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);

        mErrorMessageDisplay = findViewById(R.id.tv_error_message_display);
        mRecyclerView = findViewById(R.id.rv_movies);
        mLoadingIndicator = findViewById(R.id.pb_loading_indicator);

        mMovieAdapter = new MoviesAdapter(this);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, NUM_COLUMNS);
        mRecyclerView.setLayoutManager(mLayoutManager);
        ItemOffsetDecoration itemOffsetDecoration = new ItemOffsetDecoration(this, R.dimen.movie_item_offset);
        mRecyclerView.addItemDecoration(itemOffsetDecoration);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mMovieAdapter);

        int loaderId = MOVIES_LOADER_ID;
        LoaderManager.LoaderCallbacks<Movie[]> callback = MainActivity.this;
        Bundle bundleForLoader = null;
        getSupportLoaderManager().initLoader(loaderId, bundleForLoader, callback);
    }

    @Override
    public Loader<Movie[]> onCreateLoader(int i, final Bundle loaderArgs) {
        return new AsyncTaskLoader<Movie[]>(this) {

            Movie[] mMoviesData = null;

            @Override
            protected void onStartLoading() {
                if (mMoviesData != null) {
                    deliverResult(mMoviesData);
                } else {
                    mLoadingIndicator.setVisibility(View.VISIBLE);
                    forceLoad();
                }
            }

            @Override
            public Movie[] loadInBackground() {
                URL moviesRequestUrl = NetworkUtils.buildUrl(sortBy);

                try {
                    String jsonMoviesResponse = NetworkUtils
                            .getResponseFromHttpUrl(moviesRequestUrl);
                    Log.v(TAG, "Movies Json Response: " + jsonMoviesResponse);

                    return MovieDBJsonUtils
                            .parseMovieJson(jsonMoviesResponse);
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }

            @Override
            public void deliverResult(@Nullable Movie[] data) {
                mMoviesData = data;
                super.deliverResult(data);
            }
        };
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Movie[]> loader, Movie[] movies) {
        mLoadingIndicator.setVisibility(View.INVISIBLE);
        mMovieAdapter.setMoviesData(movies);
        if (null == movies) {
            showErrorMessage();
        } else {
            showMoviesView();
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Movie[]> loader) {
    }

    private void invalidateData() {
        mMovieAdapter.setMoviesData(null);
    }

    @Override
    public void onClick(Movie selectedMovie) {
        Context context = this;
        Class destinationClass = MovieDetailActivity.class;
        Intent intentToStartDetailActivity = new Intent(context, destinationClass);
        intentToStartDetailActivity.putExtra("Movie", selectedMovie);
        startActivity(intentToStartDetailActivity);
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

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    /**
     * This method will get the user's preferred sorted listing for movies, and then tell some
     * background method to get the movie data in the background.
     */
    private void loadMoviesData() {
        if (!isOnline()) {
            showErrorMessage();
        } else {
            showMoviesView();

            getSupportLoaderManager().restartLoader(MOVIES_LOADER_ID, null, this);
        }
    }

    private void updateSort(String sortBy) {
        /* Update only if sort preference was different from the previous one */
        if (!this.sortBy.equals(sortBy)) {
            this.sortBy = sortBy;
            if (this.sortBy.equals(Sort.top_rated.name()))  {
                setTitle(R.string.top_rated_movies_title);
            } else {
                setTitle(R.string.popular_movies_title);
            }
            loadMoviesData();
        }
    }

    private void showMoviesView() {
        /* First, make sure the error is invisible */
        mErrorMessageDisplay.setVisibility(View.INVISIBLE);
        /* Then, make sure the movie data is visible */
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    private void showErrorMessage() {
        /* First, hide the currently visible data */
        mRecyclerView.setVisibility(View.INVISIBLE);
        /* Then, show the error */
        if (isOnline()) {
            mErrorMessageDisplay.setText(R.string.error_message);
        }
        mErrorMessageDisplay.setVisibility(View.VISIBLE);
    }

    /*
        Equal column spacing for Android RecyclerView GridLayoutManager by using custom ItemDecoration
        Source: https://gist.github.com/yqritc/ccca77dc42f2364777e1
    */
    public class ItemOffsetDecoration extends RecyclerView.ItemDecoration {

        private int mItemOffset;

        ItemOffsetDecoration(int itemOffset) {
            mItemOffset = itemOffset;
        }

        ItemOffsetDecoration(@NonNull Context context, @DimenRes int itemOffsetId) {
            this(context.getResources().getDimensionPixelSize(itemOffsetId));
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                                   RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
            outRect.set(mItemOffset, mItemOffset, mItemOffset, mItemOffset);
        }
    }

}
