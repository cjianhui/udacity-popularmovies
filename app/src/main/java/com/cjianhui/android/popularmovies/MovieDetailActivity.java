package com.cjianhui.android.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.cjianhui.android.popularmovies.models.ImageSize;
import com.cjianhui.android.popularmovies.utilities.NetworkUtils;
import com.squareup.picasso.Picasso;

public class MovieDetailActivity extends AppCompatActivity {

    String movieTitle;
    String movieBackdropPath;
    String moviePosterPath;
    double movieRating;
    String movieOverview;
    String movieReleaseDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_details);

        Toolbar toolbar = findViewById(R.id.toolbar);
        this.setSupportActionBar(toolbar);
        if (this.getSupportActionBar() != null) {
            this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        ImageView mMovieBackdropImage = findViewById(R.id.iv_movie_backdrop);
        CollapsingToolbarLayout mCollapsingToolBarLayout = findViewById(R.id.collapsing_toolbar);
        ImageView mMoviePoster = findViewById(R.id.iv_movie_poster);
        TextView mMovieTitle = findViewById(R.id.tv_movie_title);
        RatingBar mMovieRating = findViewById(R.id.rb_movie_rating);
        TextView mMovieRatingTotal = findViewById(R.id.tv_movie_rating);
        TextView mMovieOverview = findViewById(R.id.tv_movie_overview);
        TextView mMovieReleaseDate = findViewById(R.id.tv_movie_release_date);

        Intent moviesIntent = getIntent();

        if (moviesIntent != null) {

            if (moviesIntent.hasExtra(MainActivity.MOVIE_TITLE)) {
                movieTitle = moviesIntent.getStringExtra(MainActivity.MOVIE_TITLE);
                mCollapsingToolBarLayout.setTitle(movieTitle);
                mMovieTitle.setText(movieTitle);
            }


            if (moviesIntent.hasExtra(MainActivity.MOVIE_BACKDROP_PATH)) {
                movieBackdropPath = moviesIntent.getStringExtra(MainActivity.MOVIE_BACKDROP_PATH);
                Picasso.get().load(NetworkUtils.buildImageLink(movieBackdropPath, ImageSize.BACKDROP.value())).into(mMovieBackdropImage);
            }


            if (moviesIntent.hasExtra(MainActivity.MOVIE_POSTER_PATH)) {
                moviePosterPath = moviesIntent.getStringExtra(MainActivity.MOVIE_POSTER_PATH);
                Picasso.get().load(NetworkUtils.buildImageLink(moviePosterPath, ImageSize.POSTER.value())).into(mMoviePoster);
            }


            if (moviesIntent.hasExtra(MainActivity.MOVIE_RATING)) {
                movieRating = moviesIntent.getDoubleExtra(MainActivity.MOVIE_RATING, 0);
                String ratingScoreDisplay = movieRating + getString(R.string.total_rating_score);
                mMovieRatingTotal.setText(ratingScoreDisplay);
                mMovieRating.setRating((float) Math.round(movieRating * 10) / 10);
            }

            if (moviesIntent.hasExtra(MainActivity.MOVIE_RELEASE_DATE)) {
                movieReleaseDate = moviesIntent.getStringExtra(MainActivity.MOVIE_RELEASE_DATE);
                mMovieReleaseDate.setText(movieReleaseDate);
            }

            if (moviesIntent.hasExtra(MainActivity.MOVIE_OVERVIEW)) {
                movieOverview = moviesIntent.getStringExtra(MainActivity.MOVIE_OVERVIEW);
                mMovieOverview.setText(movieOverview);
            }
        }
    }
}
