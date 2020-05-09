package com.example.android.popularmovies.database;

import android.content.Context;
import android.util.Log;

import com.example.android.popularmovies.model.Movies;
import com.example.android.popularmovies.repositiory.MovieRepository;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class MovieViewModel extends ViewModel {

    // Constant for logging
    private static final String LOG_TAG = MovieViewModel.class.getSimpleName();

    private LiveData<List<Movies>> movies;

    public LiveData<List<Movies>> getUsers(Context context) {
        if (movies == null) {
            movies = loadUsers(context);
        }
        return movies;
    }

    private LiveData<List<Movies>> loadUsers(Context context) {

        Log.d(LOG_TAG, "Actively retrieving the tasks from the DataBase");
         LiveData<List<Movies>> lMovies;
        // Do an asynchronous operation to fetch users.
        AppDatabase db = AppDatabase.getInstance(context);
        lMovies = db.movieDao().getAllMovies();

        return lMovies;
    }

    //initially used to trigger onsharedPreferenceChanged in MainActivity. i was causing some runtime error
    //i had to move it to the setting fragment itself which is the correct way to do it
    public void triggerPrefsChanged(Context context, String sortKeyValue){
        new MovieRepository(context).rePopulateTables(sortKeyValue);

    }

}
