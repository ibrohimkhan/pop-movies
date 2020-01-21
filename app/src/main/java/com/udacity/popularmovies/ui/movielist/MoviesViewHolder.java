package com.udacity.popularmovies.ui.movielist;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.udacity.popularmovies.BuildConfig;
import com.udacity.popularmovies.R;
import com.udacity.popularmovies.data.model.Movie;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MoviesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    @BindView(R.id.iv_poster)
    ImageView posterImage;

    private MovieListViewModel viewModel;
    private Movie movie;

    public MoviesViewHolder(@NonNull View itemView, MovieListViewModel viewModel) {
        super(itemView);
        this.viewModel = viewModel;

        ButterKnife.bind(this, itemView);
        itemView.setOnClickListener(this);
    }

    public void bind(Movie movie) {
        this.movie = movie;

        if (posterImage == null) return;
        Picasso.get()
                .load(BuildConfig.IMAGE_URL + movie.posterPath)
                .fit()
                .into(posterImage);
    }

    @Override
    public void onClick(View view) {
        viewModel.onItemSelected(movie);
    }
}
