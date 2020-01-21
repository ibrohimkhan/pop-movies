package com.udacity.popularmovies.ui.moviedetails.review;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.udacity.popularmovies.R;
import com.udacity.popularmovies.data.model.Review;
import com.udacity.popularmovies.ui.moviedetails.MovieDetailsViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ReviewViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    @BindView(R.id.tv_author)
    TextView author;

    @BindView(R.id.tv_content)
    TextView content;

    private Review review;
    private MovieDetailsViewModel viewModel;

    public ReviewViewHolder(@NonNull View itemView, MovieDetailsViewModel viewModel) {
        super(itemView);
        this.viewModel = viewModel;

        ButterKnife.bind(this, itemView);
        itemView.setOnClickListener(this);
    }

    public void bind(Review review) {
        this.review = review;

        if (author != null) author.setText(review.author);
        if (content != null) content.setText(review.content);
    }

    @Override
    public void onClick(View view) {
        viewModel.onReviewSelected(review);
    }
}
