package com.example.android.popularmovies.database;

import com.example.android.popularmovies.model.Review;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface ReviewDao {

    @Query("SELECT * FROM review WHERE movie_id = :id")
    LiveData<List<Review>> getAllReviewByMovieID(int id);

    @Insert
    void insertReview(Review reviews);

    @Query("DELETE FROM review")
    void deleteAll();
}
