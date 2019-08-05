package com.cjianhui.android.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.cjianhui.android.popularmovies.models.ImageSize;
import com.cjianhui.android.popularmovies.models.Movie;
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

        ImageView mMovieBackdropImage = findViewById(R.id.iv_movie_backdrop);
        CollapsingToolbarLayout mCollapsingToolBarLayout = findViewById(R.id.collapsing_toolbar);


        Toolbar toolbar = findViewById(R.id.toolbar);
        this.setSupportActionBar(toolbar);
        if (this.getSupportActionBar() != null) {
            this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        Fragment movieSummary = new MovieSummaryFragment();

        Intent moviesIntent = getIntent();
        if (moviesIntent != null) {
            Movie movie = moviesIntent.getParcelableExtra("Movie");
            NetworkUtils.buildReviewUrl(movie.getId());
            Picasso.get().load(NetworkUtils.buildImageLink(movie.getBackdropPath(),
                    ImageSize.BACKDROP.value())).into(mMovieBackdropImage);
            mCollapsingToolBarLayout.setTitle(movie.getTitle());
            Bundle bundle = new Bundle();
            bundle.putParcelable("Movie", moviesIntent.getParcelableExtra("Movie"));
            movieSummary.setArguments(bundle);
        }

        TabLayout tabLayout = findViewById(R.id.movie_details_tab_layout);
        ViewPager viewPager = findViewById(R.id.movie_details_view_pager);
        MoviesTabAdapter adapter = new MoviesTabAdapter(getSupportFragmentManager());
        adapter.addFragment(movieSummary, "Summary");
        //adapter.addFragment(movieSummary, "Trailers");
        //adapter.addFragment(movieSummary, "Reviews");

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);


    }
}
