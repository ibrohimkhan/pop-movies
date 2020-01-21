package com.udacity.popularmovies.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Video {
    @Expose
    @SerializedName("id")
    public final String id;

    @Expose
    @SerializedName("iso_639_1")
    public final String iso_639_1;

    @Expose
    @SerializedName("iso_3166_1")
    public final String iso_3166_1;

    @Expose
    @SerializedName("key")
    public final String key;

    @Expose
    @SerializedName("name")
    public final String name;

    @Expose
    @SerializedName("site")
    public final String site;

    @Expose
    @SerializedName("size")
    public final int size;

    @Expose
    @SerializedName("type")
    public final String type;

    public Video(String id, String iso_639_1, String iso_3166_1, String key, String name, String site, int size, String type) {
        this.id = id;
        this.iso_639_1 = iso_639_1;
        this.iso_3166_1 = iso_3166_1;
        this.key = key;
        this.name = name;
        this.site = site;
        this.size = size;
        this.type = type;
    }
}
