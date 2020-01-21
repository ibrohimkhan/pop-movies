package com.udacity.popularmovies.common;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.udacity.popularmovies.data.model.Movie;

public class SharedViewModel extends ViewModel {
    private final MutableLiveData<Movie> liveMovie = new MutableLiveData<>();
    private final MutableLiveData<Boolean> updatedUI = new MutableLiveData<>();

    public void select(Movie movie) {
        liveMovie.setValue(movie);
    }

    public MutableLiveData<Movie> getSelected() {
        return liveMovie;
    }

    public void updateUI(boolean updated) {
        updatedUI.setValue(updated);
    }

    public MutableLiveData<Boolean> getUpdatedUI() {
        return updatedUI;
    }
}
