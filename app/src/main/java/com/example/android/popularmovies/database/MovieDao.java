package com.example.android.popularmovies.database;

import android.graphics.Movie;

import com.example.android.popularmovies.model.Movies;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface MovieDao {

    @Query("SELECT * FROM movies")
    List<Movies> getAllMovies();

    @Query("SELECT * FROM movies WHERE movie_id = :id LIMIT 1")
    Movies findMovieById(int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertMovie(Movies movie);

    @Query("DELETE FROM movies")
    void deleteAll();

}