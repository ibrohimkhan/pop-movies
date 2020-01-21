package com.udacity.popularmovies.ui.moviedetails.review;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.udacity.popularmovies.R;
import com.udacity.popularmovies.data.model.Review;
import com.udacity.popularmovies.ui.moviedetails.MovieDetailsViewModel;

import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewViewHolder> {

    private List<Review> reviews;
    private MovieDetailsViewModel viewModel;

    public ReviewAdapter(List<Review> reviews, MovieDetailsViewModel viewModel) {
        this.reviews = reviews;
        this.viewModel = viewModel;
    }

    @NonNull
    @Override
    public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.review_list_item, parent, false);
        return new ReviewViewHolder(view, viewModel);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewViewHolder holder, int position) {
        holder.bind(reviews.get(position));
    }

    @Override
    public int getItemCount() {
        return reviews.size();
    }
}
