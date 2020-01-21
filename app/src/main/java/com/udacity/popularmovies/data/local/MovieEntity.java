package com.udacity.popularmovies.data.local;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.udacity.popularmovies.data.model.Review;
import com.udacity.popularmovies.data.model.Video;

import java.util.List;

@Entity(tableName = "movies")
public class MovieEntity {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "title")
    public final String originalTitle;

    @ColumnInfo(name = "overview")
    public final String overview;

    @ColumnInfo(name = "release_date")
    public final String releaseDate;

    @ColumnInfo(name = "poster_path")
    public final String posterPath;

    @ColumnInfo(name = "vote_average")
    public final double voteAverage;

    @ColumnInfo(name = "trailers")
    public final List<Video> trailers;

    @ColumnInfo(name = "reviews")
    public final List<Review> reviews;

    public MovieEntity(int id, String originalTitle, String releaseDate, String overview, String posterPath, double voteAverage, List<Video> trailers, List<Review> reviews) {
        this.id = id;
        this.originalTitle = originalTitle;
        this.releaseDate = releaseDate;
        this.overview = overview;
        this.posterPath = posterPath;
        this.voteAverage = voteAverage;
        this.trailers = trailers;
        this.reviews = reviews;
    }

    @Ignore
    public MovieEntity(String originalTitle, String releaseDate, String overview, String posterPath, double voteAverage, List<Video> trailers, List<Review> reviews) {
        this.originalTitle = originalTitle;
        this.releaseDate = releaseDate;
        this.overview = overview;
        this.posterPath = posterPath;
        this.voteAverage = voteAverage;
        this.trailers = trailers;
        this.reviews = reviews;
    }

    public int getId() {
        return id;
    }
}
