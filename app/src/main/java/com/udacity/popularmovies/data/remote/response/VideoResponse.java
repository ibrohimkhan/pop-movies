package com.udacity.popularmovies.data.remote.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.udacity.popularmovies.data.model.Video;

import java.util.List;

public class VideoResponse {
    @Expose
    @SerializedName("id")
    public final long id;

    @Expose
    @SerializedName("results")
    public final List<Video> videos;

    public VideoResponse(long id, List<Video> videos) {
        this.id = id;
        this.videos = videos;
    }
}
