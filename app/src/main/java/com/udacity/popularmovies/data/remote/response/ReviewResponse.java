package com.udacity.popularmovies.data.remote.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.udacity.popularmovies.data.model.Review;

import java.util.List;

public class ReviewResponse {
    @Expose
    @SerializedName("id")
    public final long id;

    @Expose
    @SerializedName("page")
    public final int page;

    @Expose
    @SerializedName("results")
    public final List<Review> reviews;

    @Expose
    @SerializedName("total_pages")
    public final int totalPages;

    @Expose
    @SerializedName("total_results")
    public final int totalReviews;

    public ReviewResponse(long id, int page, List<Review> reviews, int totalPages, int totalReviews) {
        this.id = id;
        this.page = page;
        this.reviews = reviews;
        this.totalPages = totalPages;
        this.totalReviews = totalReviews;
    }
}
