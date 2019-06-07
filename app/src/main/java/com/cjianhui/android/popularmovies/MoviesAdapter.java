package com.cjianhui.android.popularmovies;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cjianhui.android.popularmovies.models.Movie;
import com.cjianhui.android.popularmovies.utilities.NetworkUtils;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MovieViewHolder> {

    private Context mContext;
    private List<Movie> movieList;

    public class MovieViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public ImageView thumbnail;


        public MovieViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.tv_movie_title);
            thumbnail = (ImageView) view.findViewById(R.id.iv_thumbnail);
        }

    }

    public MoviesAdapter(Context mContext, List<Movie> movieList) {
        this.mContext = mContext;
        this.movieList = movieList;
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
        Movie movie = movieList.get(position);
        movieViewHolder.title.setText(movie.getTitle());
        String moviePosterPath = NetworkUtils.buildImageLink(movie.getPosterPath());
        Picasso.get().load(moviePosterPath).into(movieViewHolder.thumbnail);
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }
}
