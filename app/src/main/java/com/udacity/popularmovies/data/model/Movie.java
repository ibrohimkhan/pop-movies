package com.udacity.popularmovies.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.udacity.popularmovies.ui.movielist.Type;

import java.util.List;

/*
 * There is no need to implementing Parcelable for sharing it btween components, since it is done
 * through shared viewmodel.
 */
public class Movie {

    @Expose
    @SerializedName("id")
    public long id;

    @Expose
    @SerializedName("title")
    public String title;

    @Expose
    @SerializedName("original_title")
    public final String originalTitle;

    @Expose
    @SerializedName("original_language")
    public String originalLanguage;

    @Expose
    @SerializedName("overview")
    public final String overview;

    @Expose
    @SerializedName("release_date")
    public final String releaseDate;

    @Expose
    @SerializedName("popularity")
    public double popularity;

    @Expose
    @SerializedName("vote_count")
    public int voteCount;

    @Expose
    @SerializedName("vote_average")
    public final double voteAverage;

    @Expose
    @SerializedName("poster_path")
    public final String posterPath;

    @Expose
    @SerializedName("backdrop_path")
    public String backdropPath;

    @Expose
    @SerializedName("videos")
    public boolean hasVideo;

    @Expose
    @SerializedName("adult")
    public boolean isAdult;

    @Expose
    @SerializedName("genre_ids")
    public List<Integer> genreIds;

    public List<Video> trailers;

    public List<Review> reviews;

    private Type type;

    public Movie(String originalTitle, String releaseDate, String overview, String posterPath, double voteAverage, List<Video> trailers, List<Review> reviews) {
        this.originalTitle = originalTitle;
        this.releaseDate = releaseDate;
        this.overview = overview;
        this.posterPath = posterPath;
        this.voteAverage = voteAverage;
        this.trailers = trailers;
        this.reviews = reviews;
    }

    public Movie(long id, String title, String originalTitle, String originalLanguage, String overview, String releaseDate, double popularity, int voteCount, double voteAverage, String posterPath, String backdropPath, boolean hasVideo, boolean isAdult, List<Integer> genreIds) {
        this.id = id;
        this.title = title;
        this.originalTitle = originalTitle;
        this.originalLanguage = originalLanguage;
        this.overview = overview;
        this.releaseDate = releaseDate;
        this.popularity = popularity;
        this.voteCount = voteCount;
        this.voteAverage = voteAverage;
        this.posterPath = posterPath;
        this.backdropPath = backdropPath;
        this.hasVideo = hasVideo;
        this.isAdult = isAdult;
        this.genreIds = genreIds;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Movie movie = (Movie) o;

        if (originalTitle.compareTo(movie.originalTitle) != 0) return false;
        return overview.equals(movie.overview);
    }

    @Override
    public int hashCode() {
        int result = originalTitle.hashCode();
        result = 31 * result + overview.hashCode();
        return result;
    }
}
