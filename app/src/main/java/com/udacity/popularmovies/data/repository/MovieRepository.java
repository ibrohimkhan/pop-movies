package com.udacity.popularmovies.data.repository;

import com.udacity.popularmovies.BuildConfig;
import com.udacity.popularmovies.data.local.AppDatabase;
import com.udacity.popularmovies.data.local.MovieEntity;
import com.udacity.popularmovies.data.model.Movie;
import com.udacity.popularmovies.data.remote.NetworkService;
import com.udacity.popularmovies.data.remote.Networking;
import com.udacity.popularmovies.data.remote.response.MovieResponse;
import com.udacity.popularmovies.data.remote.response.ReviewResponse;
import com.udacity.popularmovies.data.remote.response.VideoResponse;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

public final class MovieRepository {

    private MovieRepository() {}

    private static NetworkService service = Networking.createService(NetworkService.class);
    private static final AppDatabase database = AppDatabase.getInstance();

    public static Single<MovieResponse> fetchPopularMovies(int page) {
        return service.getPopularMovies(BuildConfig.API_KEY, page);
    }

    public static Single<MovieResponse> fetchTopRatedMovies(int page) {
        return service.getTopRatedMovies(BuildConfig.API_KEY, page);
    }

    public static Single<VideoResponse> fetchMovieVideos(long movieId) {
        return service.getMovieVideos(movieId, BuildConfig.API_KEY);
    }

    public static Single<ReviewResponse> fetchMovieReviews(long movieId) {
        return service.getMovieReviews(movieId, BuildConfig.API_KEY);
    }

    public static Completable save(Movie movie) {
        MovieEntity entity = new MovieEntity(
                movie.originalTitle,
                movie.releaseDate,
                movie.overview,
                movie.posterPath,
                movie.voteAverage,
                movie.trailers,
                movie.reviews
        );

        return database.movieDao()
                .insert(entity);
    }

    public static Completable delete(Movie movie) {
        return database.movieDao()
                .delete(movie.originalTitle, movie.overview, movie.releaseDate, movie.posterPath);
    }

    public static Single<List<MovieEntity>> selectAll() {
        return database.movieDao().selectAll();
    }
}
