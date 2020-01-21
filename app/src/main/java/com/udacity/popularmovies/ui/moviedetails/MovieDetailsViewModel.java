package com.udacity.popularmovies.ui.moviedetails;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.udacity.popularmovies.common.Event;
import com.udacity.popularmovies.common.NetworkHelper;
import com.udacity.popularmovies.data.local.MovieEntity;
import com.udacity.popularmovies.data.model.Movie;
import com.udacity.popularmovies.data.model.Review;
import com.udacity.popularmovies.data.model.Video;
import com.udacity.popularmovies.data.remote.response.ReviewResponse;
import com.udacity.popularmovies.data.remote.response.VideoResponse;
import com.udacity.popularmovies.data.repository.MovieRepository;
import com.udacity.popularmovies.ui.movielist.Type;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class MovieDetailsViewModel extends ViewModel {

    private static final String TAG = MovieDetailsViewModel.class.getSimpleName();
    private CompositeDisposable disposable = new CompositeDisposable();

    MutableLiveData<Movie> movie = new MutableLiveData<>();
    MutableLiveData<Event<List<Video>>> trailers = new MutableLiveData<>();
    MutableLiveData<Event<List<Review>>> reviews = new MutableLiveData<>();

    MutableLiveData<Event<Boolean>> networkingError = new MutableLiveData<>();
    MutableLiveData<Event<Boolean>> databaseError = new MutableLiveData<>();

    MutableLiveData<Event<Boolean>> isSaved = new MutableLiveData<>();
    MutableLiveData<Event<Boolean>> isDeleted = new MutableLiveData<>();

    MutableLiveData<Event<Video>> selectedTrailer = new MutableLiveData<>();
    MutableLiveData<Event<Review>> selectedReview = new MutableLiveData<>();

    public MovieDetailsViewModel() {}

    @Override
    protected void onCleared() {
        if (disposable != null)
            disposable.dispose();

        super.onCleared();
    }

    public void onTrailerSelected(Video video) {
        selectedTrailer.setValue(new Event<>(video));
    }

    public void onReviewSelected(Review review) {
        selectedReview.setValue(new Event<>(review));
    }

    public void receive(Movie movie) {
        this.movie.setValue(movie);
        loadTrailers(movie);
        loadReviews(movie);
    }

    public void saveMovie() {
        Movie myMovie = movie.getValue();
        if (myMovie == null) return;

        if (trailers != null && trailers.getValue() != null) {
            List<Video> videos = trailers.getValue().peek();

            if (videos != null && !videos.isEmpty())
                myMovie.trailers = videos;
        }

        if (reviews != null && reviews.getValue() != null) {
            List<Review> listOfReviews = reviews.getValue().peek();

            if (listOfReviews != null && !listOfReviews.isEmpty())
                myMovie.reviews = listOfReviews;
        }

        disposable.add(
                MovieRepository.save(myMovie)
                        .subscribeOn(Schedulers.io())
                        .subscribe(this::insertQueryResult, this::handleDatabaseError)
        );
    }

    public void deleteMovie() {
        if (movie.getValue() == null) return;

        disposable.add(
                MovieRepository.delete(movie.getValue())
                        .subscribeOn(Schedulers.io())
                        .subscribe(this::deleteQueryResult, this::handleDatabaseError)
        );
    }

    private void deleteQueryResult() {
        isDeleted.postValue(new Event<>(true));
    }

    private void insertQueryResult() {
        isSaved.postValue(new Event<>(true));
    }

    private void loadReviews(Movie movie) {
        if (movie.getType() == Type.FAVORITE) {
            reviews.setValue(new Event<>(movie.reviews));

        } else if (NetworkHelper.isOnline()) {
            disposable.add(
                    MovieRepository.fetchMovieReviews(movie.id)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(this::notifyUI, this::handleNetworkingError)
            );
        }
    }

    private void loadTrailers(Movie movie) {
        if (movie.getType() == Type.FAVORITE) {
            trailers.setValue(new Event<>(movie.trailers));

        } else if (NetworkHelper.isOnline()) {
            disposable.add(
                    MovieRepository.fetchMovieVideos(movie.id)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(this::notifyUI, this::handleNetworkingError)
            );
        }
    }

    private void notifyUI(ReviewResponse reviewResponse) {
        reviews.setValue(new Event<>(reviewResponse.reviews));
    }

    private void notifyUI(VideoResponse videoResponse) {
        List<Video> videos = videoResponse.videos.stream()
                .filter(it -> it.type.equalsIgnoreCase("Trailer"))
                .collect(Collectors.toList());

        trailers.setValue(new Event<>(videos));
    }

    private void handleNetworkingError(Throwable throwable) {
        if (throwable != null)
            Log.e(TAG, throwable.getLocalizedMessage());

        networkingError.setValue(new Event<>(true));
    }

    private void handleDatabaseError(Throwable throwable) {
        if (throwable != null)
            Log.e(TAG, throwable.getLocalizedMessage());

        databaseError.postValue(new Event<>(true));
    }
}
