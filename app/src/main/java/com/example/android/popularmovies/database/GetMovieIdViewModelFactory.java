package com.example.android.popularmovies.database;

import android.content.Context;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class GetMovieIdViewModelFactory extends ViewModelProvider.NewInstanceFactory{

    //private final AppDatabase mDb;
    private final int mMovieApiID;
    private final Context context;
    private final boolean mIsReview;

    public GetMovieIdViewModelFactory(Context mContext, int movieId, boolean isReview) {
        //public GetMovieIdViewModelFactory(AppDatabase database, int movieId) {
        //mDb = database;
        mMovieApiID = movieId;
        context = mContext;
        mIsReview = isReview;


    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        //noinspection unchecked
        //return (T) new GetTrailerViewModel(mDb, movieId);
        if(mIsReview){
            return (T) new GetReviewViewModel(context, mMovieApiID);
        }else {
            return (T) new GetTrailerViewModel(context, mMovieApiID);
        }
    }
}
