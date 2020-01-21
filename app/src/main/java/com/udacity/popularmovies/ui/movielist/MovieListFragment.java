package com.udacity.popularmovies.ui.movielist;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.udacity.popularmovies.R;
import com.udacity.popularmovies.common.SharedViewModel;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieListFragment extends Fragment {

    public static final String TAG = MovieListFragment.class.getSimpleName();

    @BindView(R.id.tv_movie_type)
    TextView toolbarTextView;

    @BindView(R.id.rv_movies)
    RecyclerView recyclerView;

    @BindView(R.id.pb_movies_loader)
    ProgressBar progressBar;

    @BindView(R.id.tb_movies)
    Toolbar toolbar;

    @BindString(R.string.error_message)
    String error_message;

    @BindString(R.string.pop_movies)
    String pop_movies;

    @BindString(R.string.top_movies)
    String top_movies;

    @BindString(R.string.favorite_movies)
    String fav_movies;

    private SharedViewModel sharedViewModel;
    private MovieListViewModel viewModel;

    private GridLayoutManager gridLayoutManager;
    private MovieListAdapter adapter;
    private Type movieType = Type.POPULAR;

    public static MovieListFragment newInstance() {
        Bundle args = new Bundle();
        MovieListFragment fragment = new MovieListFragment();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getActivity() != null) {
            viewModel = ViewModelProviders.of(getActivity()).get(MovieListViewModel.class);
            sharedViewModel = ViewModelProviders.of(getActivity()).get(SharedViewModel.class);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie_list, container, false);
        ButterKnife.bind(this, view);

        toolbar.inflateMenu(R.menu.settings);
        toolbar.setOnMenuItemClickListener(this::onOptionsItemSelected);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        gridLayoutManager = new GridLayoutManager(getContext(), getResources().getInteger(R.integer.span_count));
        recyclerView.setLayoutManager(gridLayoutManager);

        sharedViewModel.getUpdatedUI().observe(this, updated -> {
            if (updated) {
                viewModel.loadMoreMovies(Type.FAVORITE);
            }
        });

        viewModel.loading.observe(this, loading -> {
            if (loading) progressBar.setVisibility(View.VISIBLE);
            else progressBar.setVisibility(View.GONE);
        });

        viewModel.networkError.observe(this, error -> {
            if (error)
                Toast.makeText(getContext(), error_message, Toast.LENGTH_LONG).show();
        });

        viewModel.movies.observe(this, movies -> {
            if (adapter == null) {
                adapter = new MovieListAdapter(viewModel, movies);
                recyclerView.setAdapter(adapter);

            } else {
                adapter.append(movies);
            }
        });

        viewModel.localMovies.observe(this, movies -> {
            adapter.clear();
            adapter.append(movies);
        });

        viewModel.onMovieSelected.observe(this, movie -> {
            sharedViewModel.select(movie);
        });

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (gridLayoutManager.getItemCount() < viewModel.getTotalMovies() && gridLayoutManager.getItemCount() == gridLayoutManager.findLastVisibleItemPosition() + 1) {
                    viewModel.loadMoreMovies(movieType);
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (adapter == null)
            return super.onOptionsItemSelected(item);

        switch (item.getItemId()) {
            case R.id.popular:
                adapter.clear();
                updateMovies(pop_movies, Type.POPULAR);
                return true;

            case R.id.top_rated:
                adapter.clear();
                updateMovies(top_movies, Type.TOP_RATED);
                return true;

            case R.id.favorites:
                adapter.clear();
                updateMovies(fav_movies, Type.FAVORITE);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void updateMovies(String msg, Type type) {
        toolbarTextView.setText(msg);
        movieType = type;

        if (adapter != null) adapter.clear();

        viewModel.reset();
        viewModel.loadMoreMovies(movieType);
    }
}
