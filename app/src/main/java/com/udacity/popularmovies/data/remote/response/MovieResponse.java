package com.udacity.popularmovies.data.remote.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.udacity.popularmovies.data.model.Movie;

import java.util.List;

public class MovieResponse {
    @Expose
    @SerializedName("page")
    public final int page;

    @Expose
    @SerializedName("total_results")
    public final int totalMovies;

    @Expose
    @SerializedName("total_pages")
    public final int totalPages;

    @Expose
    @SerializedName("results")
    public final List<Movie> movies;

    public MovieResponse(int page, int totalMovies, int totalPages, List<Movie> movies) {
        this.page = page;
        this.totalMovies = totalMovies;
        this.totalPages = totalPages;
        this.movies = movies;
    }
}
