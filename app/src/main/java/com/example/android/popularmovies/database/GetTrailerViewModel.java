package com.example.android.popularmovies.database;

import android.content.Context;
import android.util.Log;

import com.example.android.popularmovies.model.Trailer;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class GetTrailerViewModel extends ViewModel {

    // Constant for logging
    private static final String LOG_TAG = GetTrailerViewModel.class.getSimpleName();

    private LiveData<List<Trailer>> trailers;

    public GetTrailerViewModel(Context context, int movieApiID) {
        Log.d(LOG_TAG, "Actively retrieving the movie trailers from the DataBase");
        AppDatabase db = AppDatabase.getInstance(context);
        trailers = db.trailerDao().getAllTrailersByMovieID(movieApiID);
    }

    public LiveData<List<Trailer>> getTrailers() {
        return trailers;
    }

}
