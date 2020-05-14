package com.example.android.popularmovies.sync;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.example.android.popularmovies.R;
import com.example.android.popularmovies.repositiory.MovieRepository;

public class MovieSyncTask {
    private static final String LOG_TAG = MovieSyncTask.class.getSimpleName();

    synchronized public static void syncMovie(Context context) {

        new MovieRepository(context).rePopulateTables(getSortPreference(context));
    }

    public static String getSortPreference(Context context){
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);

        //get the prefered sort value
        String sortBy  = sharedPrefs.getString(
                context.getString(R.string.settings_sort_key),
                context.getString(R.string.pref_sort_default_value)
        );

        Log.d(LOG_TAG, "SortMoviedefault: " + sortBy); //it still shows popularity.desc on app install???


        return sortBy;

    }
}
