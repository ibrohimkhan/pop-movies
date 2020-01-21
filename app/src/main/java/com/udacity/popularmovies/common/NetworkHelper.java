package com.udacity.popularmovies.common;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;

import com.udacity.popularmovies.MovieApplication;

public final class NetworkHelper {

    public static boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) MovieApplication.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        if (cm == null) return false;

        Network network = cm.getActiveNetwork();
        NetworkCapabilities capabilities = cm.getNetworkCapabilities(network);

        if (capabilities == null) return false;

        if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
                || capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR))
            return true;

        return false;
    }
}
