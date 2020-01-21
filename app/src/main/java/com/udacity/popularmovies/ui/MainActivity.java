package com.udacity.popularmovies.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import com.udacity.popularmovies.R;
import com.udacity.popularmovies.common.SharedViewModel;
import com.udacity.popularmovies.ui.moviedetails.MovieDetailsFragment;
import com.udacity.popularmovies.ui.movielist.MovieListFragment;

public class MainActivity extends AppCompatActivity {

    private SharedViewModel sharedViewModel;
    private Fragment activeFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedViewModel = ViewModelProviders.of(this).get(SharedViewModel.class);
        sharedViewModel.getSelected().observe(this, movie -> {
            showMovieDetail();
        });

        if (savedInstanceState == null) {
            showMovies();
        }
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            if (getSupportFragmentManager().popBackStackImmediate())
                activeFragment =
                        getSupportFragmentManager()
                                .getFragments()
                                .get(getSupportFragmentManager().getBackStackEntryCount() - 1);

        } else {
            finish();
        }
    }

    private void showMovies() {
        if (activeFragment instanceof MovieListFragment) return;

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        MovieListFragment fragment = (MovieListFragment) getSupportFragmentManager().findFragmentByTag(MovieListFragment.TAG);

        if (fragment == null) {
            fragment = MovieListFragment.newInstance();
            transaction
                    .add(R.id.fragment_container, fragment)
                    .addToBackStack(MovieListFragment.TAG);
        } else {
            transaction.show(fragment);
        }

        if (activeFragment != null) transaction.hide(activeFragment);
        transaction.commit();

        activeFragment = fragment;
    }

    private void showMovieDetail() {
        if (activeFragment instanceof MovieDetailsFragment) return;

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        MovieDetailsFragment fragment = (MovieDetailsFragment) getSupportFragmentManager().findFragmentByTag(MovieDetailsFragment.TAG);

        if (fragment == null) {
            fragment = MovieDetailsFragment.newInstance();
            transaction
                    .add(R.id.fragment_container, fragment)
                    .addToBackStack(MovieDetailsFragment.TAG);
        } else {
            transaction.show(fragment);
        }

        if (activeFragment != null) transaction.hide(activeFragment);
        transaction.commit();

        activeFragment = fragment;
    }
}
