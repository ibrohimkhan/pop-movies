package com.udacity.popularmovies.ui.movielist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.udacity.popularmovies.R;
import com.udacity.popularmovies.data.model.Movie;

import java.util.List;

public class MovieListAdapter extends RecyclerView.Adapter<MoviesViewHolder> {

    private List<Movie> movies;
    private MovieListViewModel viewModel;

    public MovieListAdapter(MovieListViewModel viewModel, List<Movie> movies) {
        this.viewModel = viewModel;
        this.movies = movies;
    }

    @NonNull
    @Override
    public MoviesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_list_item, parent, false);
        return new MoviesViewHolder(view, viewModel);
    }

    @Override
    public void onBindViewHolder(@NonNull MoviesViewHolder holder, int position) {
        holder.bind(movies.get(position));
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public void append(List<Movie> newMovies) {
        int position = movies.size();
        movies.addAll(newMovies);
        notifyItemRangeInserted(position, newMovies.size());
    }

    public void clear() {
        movies.clear();
        notifyDataSetChanged();
    }
}
