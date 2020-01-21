package com.udacity.popularmovies.ui.moviedetails.trailer;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.udacity.popularmovies.R;
import com.udacity.popularmovies.data.model.Video;
import com.udacity.popularmovies.ui.moviedetails.MovieDetailsViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TrailerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    @BindView(R.id.tv_trailer_name)
    TextView trailerName;

    private Video trailer;
    private MovieDetailsViewModel viewModel;

    public TrailerViewHolder(@NonNull View itemView, MovieDetailsViewModel viewModel) {
        super(itemView);
        this.viewModel = viewModel;

        ButterKnife.bind(this, itemView);
        itemView.setOnClickListener(this);
    }

    public void bind(Video trailer) {
        this.trailer = trailer;

        if (trailerName == null) return;
        trailerName.setText(trailer.name);
    }

    @Override
    public void onClick(View view) {
        viewModel.onTrailerSelected(trailer);
    }
}
