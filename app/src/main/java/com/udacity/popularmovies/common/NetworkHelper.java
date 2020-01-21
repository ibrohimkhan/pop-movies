package com.udacity.popularmovies.common;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.udacity.popularmovies.MovieApplication;

public final class NetworkHelper {

    public static boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) MovieApplication.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}
