package com.example.android.popularmovies.database;

import android.app.Application;
import android.util.Log;

import com.example.android.popularmovies.model.Movies;

import java.util.List;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class MovieViewModel extends AndroidViewModel {

    // Constant for logging
    private static final String LOG_TAG = MovieViewModel.class.getSimpleName();

    private LiveData<List<Movies>> movies;

    public MovieViewModel(Application application) {
        super(application);
        AppDatabase database = AppDatabase.getInstance(this.getApplication());
        Log.d(LOG_TAG, "Actively retrieving the tasks from the DataBase");
        movies = database.movieDao().getAllMovies();
    }

    // COMPLETED (3) Create a getter for the tasks variable
    public LiveData<List<Movies>> getMovies() {
        return movies;
    }
}
