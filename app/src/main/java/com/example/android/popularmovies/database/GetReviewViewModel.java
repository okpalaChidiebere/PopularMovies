package com.example.android.popularmovies.database;

import android.content.Context;
import android.util.Log;

import com.example.android.popularmovies.model.Review;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class GetReviewViewModel extends ViewModel{

    // Constant for logging
    private static final String LOG_TAG = GetReviewViewModel.class.getSimpleName();

    private LiveData<List<Review>> reviews;

    public GetReviewViewModel(Context context, int movieApiID) {
        Log.d(LOG_TAG, "Actively retrieving the movie reviews from the DataBase");
        AppDatabase db = AppDatabase.getInstance(context);
        reviews = db.reviewDao().getAllReviewByMovieID(movieApiID);
    }

    public LiveData<List<Review>> getReviews() {
        return reviews;
    }
}
