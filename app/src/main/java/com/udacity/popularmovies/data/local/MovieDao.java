package com.udacity.popularmovies.data.local;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

@Dao
public interface MovieDao {

    @Query("select * from movies")
    Single<List<MovieEntity>> selectAll();

    @Insert
    Completable insert(MovieEntity movie);

    @Query("delete from movies where title = :title and overview = :overview and release_date = :releaseDate and poster_path = :posterPath")
    Completable delete(String title, String overview, String releaseDate, String posterPath);
}
