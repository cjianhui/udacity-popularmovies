package com.cjianhui.android.popularmovies;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cjianhui.android.popularmovies.models.ImageSize;
import com.cjianhui.android.popularmovies.models.Movie;
import com.cjianhui.android.popularmovies.utilities.NetworkUtils;
import com.squareup.picasso.Picasso;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MovieViewHolder> {

    private Movie[] mMovieData;


    private final MoviesAdapterOnClickHandler mClickHandler;

    public interface MoviesAdapterOnClickHandler {
        void onClick(Movie selectedMovie);
    }

    public MoviesAdapter (MoviesAdapterOnClickHandler clickHandler) {
        mClickHandler = clickHandler;
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView title;
        public ImageView thumbnail;


        public MovieViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.tv_movie_title);
            thumbnail = view.findViewById(R.id.iv_thumbnail);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            Movie selectedMovie = mMovieData[adapterPosition];
            mClickHandler.onClick(selectedMovie);
        }
    }

    public void setMoviesData(Movie[] moviesData) {
        mMovieData = moviesData;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.movie_card;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder movieViewHolder, int position) {
        Movie movie = mMovieData[position];
        movieViewHolder.title.setText(movie.getTitle());
        String moviePosterPath = NetworkUtils.buildImageLink(movie.getPosterPath(), ImageSize.THUMBNAIL.value());
        Picasso.get().load(moviePosterPath).into(movieViewHolder.thumbnail);
    }

    @Override
    public int getItemCount() {
        if (null == mMovieData) return 0;
        return mMovieData.length;
    }
}
