package com.example.android.popularmovies.database;

import android.content.Context;

import com.example.android.popularmovies.repositiory.MovieRepository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class FavouriteViewModel extends ViewModel {

    // Constant for logging
    private static final String LOG_TAG = FavouriteViewModel.class.getSimpleName();
    private LiveData<Integer> rows;

    public FavouriteViewModel(Context context, int MovieID){
        rows =  new MovieRepository(context).checkMovieFavourites(MovieID);
    }

    public LiveData<Integer> getFinal(){
        return rows;
    }
}
