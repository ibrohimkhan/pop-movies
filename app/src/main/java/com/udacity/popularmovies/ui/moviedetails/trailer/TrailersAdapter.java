package com.udacity.popularmovies.ui.moviedetails.trailer;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.udacity.popularmovies.R;
import com.udacity.popularmovies.data.model.Video;
import com.udacity.popularmovies.ui.moviedetails.MovieDetailsViewModel;

import java.util.List;

public class TrailersAdapter extends RecyclerView.Adapter<TrailerViewHolder> {

    private List<Video> trailers;
    private MovieDetailsViewModel viewModel;

    public TrailersAdapter(List<Video> trailers, MovieDetailsViewModel viewModel) {
        this.trailers = trailers;
        this.viewModel = viewModel;
    }

    @NonNull
    @Override
    public TrailerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.trailer_list_item, parent, false);
        return new TrailerViewHolder(view, viewModel);
    }

    @Override
    public void onBindViewHolder(@NonNull TrailerViewHolder holder, int position) {
        holder.bind(trailers.get(position));
    }

    @Override
    public int getItemCount() {
        return trailers.size();
    }
}
