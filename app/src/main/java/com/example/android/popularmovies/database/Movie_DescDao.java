package com.example.android.popularmovies.database;

import android.graphics.Movie;

import com.example.android.popularmovies.model.Movies;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface Movie_DescDao {

    @Query("SELECT * FROM movie_desc")
    List<Movie_Desc> getAllMovies();

    @Query("SELECT * FROM movie_desc WHERE movie_id = :id LIMIT 1")
    Movie_Desc findMovieById(int id);

    @Insert
    int insertMovie(Movies movie);

    @Query("DELETE FROM movie_desc")
    void deleteAll();

}
