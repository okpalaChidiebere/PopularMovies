package com.example.android.popularmovies.database;

import com.example.android.popularmovies.model.Movies;

import java.util.List;

import androidx.room.Insert;
import androidx.room.Query;

public interface Trailer_DescDao {

    @Query("SELECT * FROM trailer_desc WHERE movie_id = :id")
    List<Trailer_Desc> getAllTrailersByMovieID(int id);

    @Insert
    void insertTrailer(Trailer_Desc trailer);

    @Query("DELETE FROM trailer_desc")
    void deleteAll();
}
