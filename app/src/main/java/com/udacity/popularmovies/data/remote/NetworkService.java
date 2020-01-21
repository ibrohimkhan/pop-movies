package com.udacity.popularmovies.data.remote;

import com.udacity.popularmovies.BuildConfig;
import com.udacity.popularmovies.data.remote.response.MovieResponse;
import com.udacity.popularmovies.data.remote.response.ReviewResponse;
import com.udacity.popularmovies.data.remote.response.VideoResponse;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface NetworkService {

    @GET(BuildConfig.BASE_URL + Endpoints.POPULAR_MOVIE)
    Single<MovieResponse> getPopularMovies(
        @Query("api_key") String apiKey,
        @Query("page") int page
    );

    @GET(BuildConfig.BASE_URL + Endpoints.TOP_RATED_MOVIE)
    Single<MovieResponse> getTopRatedMovies(
            @Query("api_key") String apiKey,
            @Query("page") int page
    );

    @GET(BuildConfig.BASE_URL + Endpoints.MOVIE_VIDEOS)
    Single<VideoResponse> getMovieVideos(
        @Path("movie_id") long movieId,
        @Query("api_key") String apiKey
    );

    @GET(BuildConfig.BASE_URL + Endpoints.MOVIE_REVIEWS)
    Single<ReviewResponse> getMovieReviews(
            @Path("movie_id") long movieId,
            @Query("api_key") String apiKey
    );
}
