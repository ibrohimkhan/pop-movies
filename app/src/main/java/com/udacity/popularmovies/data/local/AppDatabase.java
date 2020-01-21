package com.udacity.popularmovies.data.local;

import android.util.Log;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.udacity.popularmovies.MovieApplication;

@Database(entities = {MovieEntity.class}, version = 1, exportSchema = false)
@TypeConverters({CustomTypeConverter.class})
public abstract class AppDatabase extends RoomDatabase {

    private static final String DB_NAME = "movies.db";
    private static final String TAG = AppDatabase.class.getSimpleName();

    private static volatile AppDatabase instance = null;

    public static synchronized AppDatabase getInstance() {
        if (instance == null) {
            instance = Room.databaseBuilder(
                    MovieApplication.getContext(),
                    AppDatabase.class,
                    DB_NAME
            ).build();
        }

        Log.d(TAG, "Getting the database instance");
        return instance;
    }

    public abstract MovieDao movieDao();
}
