package com.example.android.popularmovies.sync;

import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;

public class MovieSyncUtils {

    private static boolean sInitialized;

    synchronized public static void initialize(@NonNull final Context context) {

        /*
         * Only perform initialization once per app lifetime. If initialization has already been
         * performed, we have nothing to do in this method.
         */
        if (sInitialized) return;

        sInitialized = true; //If the method body is executed, set sInitialized to true
        Intent intentToSyncImmediately = new Intent(context, MovieSyncIntentService.class);
        context.startService(intentToSyncImmediately);
    }

    public static void startImmediateSync(@NonNull final Context context) {
        //Within that method, start the MovieSyncIntentService
        Intent intentToSyncImmediately = new Intent(context, MovieSyncIntentService.class);
        context.startService(intentToSyncImmediately);
    }
}
