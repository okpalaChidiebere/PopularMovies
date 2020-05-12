package com.example.android.popularmovies.database;

import com.example.android.popularmovies.model.Favourite;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface FavouriteDao {

    @Query("SELECT * FROM favourite")
    List<Favourite> getAllFavourite();

    @Query("SELECT COUNT(movie_id) FROM favourite WHERE movie_id = :id LIMIT 1")
    LiveData<Integer> getFavouriteById(int id);

    @Query("DELETE FROM favourite WHERE movie_id = :id")
    void deleteFavouriteByMovieId(int id);

    @Insert
    void insertFavourite(Favourite favourite);

    @Query("DELETE FROM favourite")
    void deleteAll();

}
