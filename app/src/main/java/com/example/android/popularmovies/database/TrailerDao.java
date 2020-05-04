package com.example.android.popularmovies.database;

import com.example.android.popularmovies.model.Trailer;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface TrailerDao {

    @Query("SELECT * FROM trailer WHERE movie_id = :id")
    List<Trailer> getAllTrailersByMovieID(int id);

    @Insert
    void insertTrailer(Trailer trailer);

    @Query("DELETE FROM trailer")
    void deleteAll();
}
