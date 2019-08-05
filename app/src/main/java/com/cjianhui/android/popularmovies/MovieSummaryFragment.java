package com.cjianhui.android.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.cjianhui.android.popularmovies.models.ImageSize;
import com.cjianhui.android.popularmovies.models.Movie;
import com.cjianhui.android.popularmovies.utilities.GenreUtils;
import com.cjianhui.android.popularmovies.utilities.NetworkUtils;
import com.squareup.picasso.Picasso;

public class MovieSummaryFragment extends Fragment {
    Movie movie;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.movie_summary_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            movie = bundle.getParcelable("Movie");
        }

        ImageView mMoviePoster = view.findViewById(R.id.iv_movie_poster);
        TextView mMovieTitle = view.findViewById(R.id.tv_detail_movie_title);
        TextView mMovieGenre = view.findViewById(R.id.tv_detail_movie_genre);
        RatingBar mMovieRating = view.findViewById(R.id.rb_movie_rating);
        TextView mMovieRatingTotal = view.findViewById(R.id.tv_movie_rating);
        TextView mMovieOverview = view.findViewById(R.id.tv_movie_overview);
        TextView mMovieReleaseDate = view.findViewById(R.id.tv_movie_release_date);

        Picasso.get().load(NetworkUtils.buildImageLink(movie.getPosterPath(),
                ImageSize.POSTER.value())).into(mMoviePoster);

        mMovieTitle.setText(movie.getTitle());
        mMovieGenre.setText(GenreUtils.getGenres(movie.getGenreIds()));

        String ratingScoreDisplay = movie.getVoteAverage() + getString(R.string.total_rating_score);
        mMovieRatingTotal.setText(ratingScoreDisplay);
        mMovieRating.setRating((float) Math.round(movie.getVoteAverage() * 10) / 10);
        mMovieReleaseDate.setText(movie.getReleaseDate());
        mMovieOverview.setText(movie.getOverview());

    }
}
