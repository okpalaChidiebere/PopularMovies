package com.example.android.popularmovies.database;

import java.util.List;

import androidx.room.Insert;
import androidx.room.Query;

public interface Review_DescDao {

    @Query("SELECT * FROM review_desc WHERE movie_id = :id")
    List<Review_Desc> getAllReviewByMovieID(int id);

    @Insert
    void insertReview(Review_Desc reviews);

    @Query("DELETE FROM trailer_desc")
    void deleteAll();
}
