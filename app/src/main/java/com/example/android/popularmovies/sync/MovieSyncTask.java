package com.example.android.popularmovies.sync;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.android.popularmovies.R;
import com.example.android.popularmovies.repositiory.MovieRepository;

public class MovieSyncTask {

    synchronized public static void syncMovie(Context context) {

        new MovieRepository(context).rePopulateTables(getSortPreference(context));
    }

    public static String getSortPreference(Context context){
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);

        //get the prefered sort value
        String sortBy  = sharedPrefs.getString(
                context.getString(R.string.settings_sort_key),
                context.getString(R.string.settings_sort_default_value)
        );

        return sortBy;

    }
}
