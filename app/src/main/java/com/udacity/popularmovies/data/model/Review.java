package com.udacity.popularmovies.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Review {
    @Expose
    @SerializedName("id")
    public final String id;

    @Expose
    @SerializedName("author")
    public final String author;

    @Expose
    @SerializedName("content")
    public final String content;

    @Expose
    @SerializedName("url")
    public final String url;

    public Review(String id, String author, String content, String url) {
        this.id = id;
        this.author = author;
        this.content = content;
        this.url = url;
    }
}
