package com.example.android.popularmovies.database;

import android.content.Context;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class GetTrailersViewModelFactory extends ViewModelProvider.NewInstanceFactory{

    //private final AppDatabase mDb;
    private final int mMovieApiID;
    private final Context context;

    public GetTrailersViewModelFactory(Context mContext, int movieId) {
        //public GetTrailersViewModelFactory(AppDatabase database, int movieId) {
        //mDb = database;
        mMovieApiID = movieId;
        context = mContext;


    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        //noinspection unchecked
        //return (T) new GetTrailerViewModel(mDb, movieId);
        return (T) new GetTrailerViewModel(context, mMovieApiID);
    }
}
