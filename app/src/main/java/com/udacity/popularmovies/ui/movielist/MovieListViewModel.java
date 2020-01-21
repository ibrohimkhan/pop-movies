package com.udacity.popularmovies.ui.movielist;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.udacity.popularmovies.data.local.MovieEntity;
import com.udacity.popularmovies.data.model.Movie;
import com.udacity.popularmovies.data.remote.response.MovieResponse;
import com.udacity.popularmovies.data.repository.MovieRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class MovieListViewModel extends ViewModel {

    private static final String TAG = MovieListViewModel.class.getSimpleName();

    private int currentPage;
    private int totalPages;
    private int totalMovies;

    private CompositeDisposable disposable = new CompositeDisposable();

    MutableLiveData<List<Movie>> movies = new MutableLiveData<>();
    MutableLiveData<List<Movie>> localMovies = new MutableLiveData<>();
    MutableLiveData<Boolean> loading = new MutableLiveData<>();

    MutableLiveData<Boolean> networkError = new MutableLiveData<>();
    MutableLiveData<Boolean> databaseError = new MutableLiveData<>();

    MutableLiveData<Movie> onMovieSelected = new MutableLiveData<>();

    public MovieListViewModel() {
        movieLoader(Type.POPULAR, 1);
    }

    public void reset() {
        currentPage = 0;
        totalPages = 1;
        totalMovies = 0;
    }

    public int getTotalMovies() {
        return totalMovies;
    }

    public void loadMoreMovies(Type type) {
        if (loading.getValue() || currentPage >= totalPages) return;

        movieLoader(type, currentPage + 1);
    }

    public void onItemSelected(Movie movie) {
        onMovieSelected.setValue(movie);
    }

    @Override
    protected void onCleared() {
        disposable.dispose();
        super.onCleared();
    }

    private void handleNetworkingError(Throwable throwable) {
        if (throwable != null)
            Log.e(TAG, throwable.getLocalizedMessage());

        loading.postValue(false);
        networkError.postValue(true);
    }

    private void handleDatabaseError(Throwable throwable) {
        if (throwable != null)
            Log.e(TAG, throwable.getLocalizedMessage());

        databaseError.postValue(true);
    }

    private void notifyUI(MovieResponse response) {
        currentPage = response.page;
        totalMovies = response.totalMovies;
        totalPages = response.totalPages;

        loading.setValue(false);
        networkError.setValue(false);
        movies.setValue(response.movies);
    }

    private void movieLoader(Type type, int page) {
        loading.setValue(true);

        if (type == Type.POPULAR) {
            disposable.add(
                    MovieRepository.fetchPopularMovies(page)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(this::notifyUI, this::handleNetworkingError)
            );

        } else if (type == Type.TOP_RATED) {
            disposable.add(
                    MovieRepository.fetchTopRatedMovies(page)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(this::notifyUI, this::handleNetworkingError)
            );

        } else if (type == Type.FAVORITE) {
            disposable.add(
                    MovieRepository.selectAll()
                            .subscribeOn(Schedulers.io())
                            .subscribe(this::selectQueryResult, this::handleDatabaseError)
            );
        }
    }

    private void selectQueryResult(List<MovieEntity> movieEntities) {
        if (movieEntities == null || movieEntities.isEmpty()) return;

        List<Movie> movies = movieEntities.stream()
                .map(it -> {
                            Movie movie = new Movie(
                                    it.originalTitle,
                                    it.releaseDate,
                                    it.overview,
                                    it.posterPath,
                                    it.voteAverage,
                                    it.trailers,
                                    it.reviews
                            );

                            movie.setType(Type.FAVORITE);
                            return movie;
                        }
                ).collect(Collectors.toList());

        localMovies.postValue(movies);
        loading.postValue(false);
    }
}
