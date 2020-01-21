package com.udacity.popularmovies.data.local;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.udacity.popularmovies.data.model.Review;
import com.udacity.popularmovies.data.model.Video;

import java.util.Collections;
import java.util.List;

public class CustomTypeConverter {

    private CustomTypeConverter() {}

    @TypeConverter
    public static String fromListOfTrailers(List<Video> values) {
        if (values == null || values.isEmpty()) return null;
        String json = new Gson().toJson(values);

        return json;
    }

    @TypeConverter
    public static List<Video> toListOfTrailers(String value) {
        if (value == null) return Collections.emptyList();

        return new Gson().fromJson(value, new TypeToken<List<Video>>(){}.getType());
    }

    @TypeConverter
    public static String fromListOfReviews(List<Review> values) {
        if (values == null || values.isEmpty()) return null;
        String json = new Gson().toJson(values);

        return json;
    }

    @TypeConverter
    public static List<Review> toListOfReviews(String value) {
        if (value == null) return Collections.emptyList();

        return new Gson().fromJson(value, new TypeToken<List<Review>>(){}.getType());
    }
}
