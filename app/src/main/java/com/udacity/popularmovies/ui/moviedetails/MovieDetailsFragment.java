package com.udacity.popularmovies.ui.moviedetails;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.udacity.popularmovies.BuildConfig;
import com.udacity.popularmovies.R;
import com.udacity.popularmovies.common.SharedViewModel;
import com.udacity.popularmovies.data.model.Review;
import com.udacity.popularmovies.data.model.Video;
import com.udacity.popularmovies.ui.moviedetails.review.ReviewAdapter;
import com.udacity.popularmovies.ui.moviedetails.trailer.TrailersAdapter;
import com.udacity.popularmovies.ui.movielist.Type;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieDetailsFragment extends Fragment {

    public static final String TAG = MovieDetailsFragment.class.getSimpleName();

    private SharedViewModel sharedViewModel;
    private MovieDetailsViewModel viewModel;

    @BindView(R.id.tv_original_title)
    TextView title;

    @BindView(R.id.tv_release_date)
    TextView date;

    @BindView(R.id.tv_vote_average)
    TextView vote;

    @BindView(R.id.tv_movie_overview)
    TextView overview;

    @BindView(R.id.iv_movie_detail_poster)
    ImageView poster;

    @BindView(R.id.tb_movie_detail)
    Toolbar toolbar;

    @BindView(R.id.rv_movie_trailers)
    RecyclerView rvTrailers;

    @BindView(R.id.rv_movie_reviews)
    RecyclerView rvReviews;

    @BindView(R.id.v_devider)
    View vDevider;

    @BindView(R.id.tv_trailer_header)
    TextView tvTrailerHeader;

    @BindView(R.id.tv_review_header)
    TextView tvReviewHeader;

    @BindView(R.id.tv_fav_movie)
    TextView favoriteMovie;

    private TrailersAdapter trailersAdapter;
    private ReviewAdapter reviewAdapter;

    public static MovieDetailsFragment newInstance() {
        Bundle args = new Bundle();
        MovieDetailsFragment fragment = new MovieDetailsFragment();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getActivity() != null) {
            sharedViewModel = ViewModelProviders.of(getActivity()).get(SharedViewModel.class);
            viewModel = ViewModelProviders.of(getActivity()).get(MovieDetailsViewModel.class);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie_details, container, false);
        ButterKnife.bind(this, view);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getActivity() != null)
                    getActivity().onBackPressed();
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        rvTrailers.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvReviews.setLayoutManager(new LinearLayoutManager(getActivity()));

        sharedViewModel.getSelected().observe(this, movie -> {
            viewModel.receive(movie);

            title.setText(movie.originalTitle);
            date.setText(movie.releaseDate.substring(0, movie.releaseDate.indexOf("-")));
            vote.setText(String.valueOf(movie.voteAverage).concat("/10"));
            overview.setText(movie.overview);

            if (movie.getType() == Type.FAVORITE) {
                favoriteMovie.setText(R.string.unmark_movie);
            }

            Picasso.get()
                    .load(BuildConfig.IMAGE_URL + movie.posterPath)
                    .fit()
                    .into(poster);
        });

        viewModel.favoriteMovie.observe(this, result -> {
            if (result == null || result.getIfNotHandled() == null) return;
            favoriteMovie.setText(R.string.unmark_movie);
        });

        viewModel.trailers.observe(this, data -> {
            if (data.getIfNotHandled() != null) {
                List<Video> trailers = data.peek();

                trailersAdapter = new TrailersAdapter(trailers, viewModel);
                rvTrailers.setAdapter(trailersAdapter);

                if (!trailers.isEmpty()) {
                    vDevider.setVisibility(View.VISIBLE);
                    tvTrailerHeader.setVisibility(View.VISIBLE);
                }
            }
        });

        viewModel.selectedTrailer.observe(this, videoEvent -> {
            if (videoEvent.getIfNotHandled() != null) {
                Uri uri = Uri.parse(BuildConfig.YOUTUBE_URL + videoEvent.peek().key);

                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                Intent chooser = Intent.createChooser(intent, getString(R.string.open_with));

                if (getActivity() != null && intent.resolveActivity(getActivity().getPackageManager()) != null) {
                    startActivity(chooser);
                }
            }
        });

        viewModel.reviews.observe(this, data -> {
            if (data.getIfNotHandled() != null) {
                List<Review> reviews = data.peek();

                reviewAdapter = new ReviewAdapter(reviews, viewModel);
                rvReviews.setAdapter(reviewAdapter);

                if (!reviews.isEmpty()) {
                    tvReviewHeader.setVisibility(View.VISIBLE);
                    vDevider.setVisibility(View.VISIBLE);
                }
            }
        });

        viewModel.selectedReview.observe(this, reviewEvent -> {
            if (reviewEvent.getIfNotHandled() != null) {
                Uri uri = Uri.parse(reviewEvent.peek().url);

                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                Intent chooser = Intent.createChooser(intent, getString(R.string.open_with));

                if (getActivity() != null && intent.resolveActivity(getActivity().getPackageManager()) != null) {
                    startActivity(chooser);
                }
            }
        });

        viewModel.networkingError.observe(this, error -> {
            if (error.getIfNotHandled() != null && error.peek())
                Toast.makeText(getContext(), getString(R.string.network_error), Toast.LENGTH_SHORT).show();
        });

        viewModel.databaseError.observe(this, error -> {
            if (error.getIfNotHandled() != null && error.peek())
                Toast.makeText(getContext(), getString(R.string.db_error), Toast.LENGTH_SHORT).show();
        });

        viewModel.isSaved.observe(this, isSaved -> {
            if (isSaved.getIfNotHandled() != null && isSaved.peek())
                Toast.makeText(getContext(), getString(R.string.saved_in_db), Toast.LENGTH_SHORT).show();
        });

        viewModel.isDeleted.observe(this, isDeleted -> {
            if (isDeleted.getIfNotHandled() != null && isDeleted.peek()) {
                Toast.makeText(getContext(), getString(R.string.removed_from_db), Toast.LENGTH_SHORT).show();
                sharedViewModel.updateUI(true);
                getActivity().onBackPressed();
            }
        });

        if (favoriteMovie != null) {
            favoriteMovie.setOnClickListener(view1 -> {

                if (favoriteMovie.getText().equals(getString(R.string.mark_movie))) {
                    favoriteMovie.setText(R.string.unmark_movie);
                    viewModel.saveMovie();

                } else if (favoriteMovie.getText().equals(getString(R.string.unmark_movie))) {
                    favoriteMovie.setText(R.string.mark_movie);
                    viewModel.deleteMovie();
                }
            });
        }
    }
}
