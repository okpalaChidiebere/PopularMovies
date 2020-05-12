package com.example.android.popularmovies.database;

import com.example.android.popularmovies.model.Favourite;
import com.example.android.popularmovies.model.Movies;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface FavouriteDao {

    @Query("SELECT movies.movie_id, movies.title, movies.thumbnail, movies.overview, movies.rating, movies.release_date, movies.api_movie_id\n" +
            "FROM favourite\n" +
            "INNER JOIN movies ON favourite.movie_id = movies.movie_id\n" +
            "ORDER BY favourite.movie_id DESC")
    LiveData<List<Movies>> getAllFavourite();

    @Query("SELECT COUNT(movie_id) FROM favourite WHERE movie_id = :id LIMIT 1")
    LiveData<Integer> getFavouriteById(int id);

    @Query("DELETE FROM favourite WHERE movie_id = :id")
    void deleteFavouriteByMovieId(int id);

    @Insert
    void insertFavourite(Favourite favourite);

    @Query("DELETE FROM favourite")
    void deleteAll();

}
