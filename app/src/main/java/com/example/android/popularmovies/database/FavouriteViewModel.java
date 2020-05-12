package com.example.android.popularmovies.database;

import android.content.Context;
import android.util.Log;

import com.example.android.popularmovies.model.Movies;
import com.example.android.popularmovies.repositiory.MovieRepository;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class FavouriteViewModel extends ViewModel {

    // Constant for logging
    private static final String LOG_TAG = FavouriteViewModel.class.getSimpleName();
    private LiveData<Integer> rows;
    private LiveData<List<Movies>> movies;

    public FavouriteViewModel(){

    }

    public FavouriteViewModel(Context context, int MovieID){
        rows =  new MovieRepository(context).checkMovieFavourites(MovieID);
    }

    public LiveData<Integer> getFinal(){
        return rows;
    }


    public LiveData<List<Movies>> getFavourites(Context context) {
        if (movies == null) {
            movies = loadFavourites(context);
        }
        return movies;
    }

    private LiveData<List<Movies>> loadFavourites(Context context) {

        Log.d(LOG_TAG, "Actively retrieving the favourites from the DataBase");
        LiveData<List<Movies>> lMovies;
        // Do an asynchronous operation to fetch users.
        AppDatabase db = AppDatabase.getInstance(context);
        lMovies = db.favouriteDao().getAllFavourite();

        return lMovies;
    }
}
