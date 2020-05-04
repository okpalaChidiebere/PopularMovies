package com.example.android.popularmovies.database;

import com.example.android.popularmovies.model.Favourite;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface FavouriteDao {

    @Query("SELECT * FROM favourite")
    List<Favourite> getAllFavourite();

    /*@Query("SELECT * FROM favourite WHERE movie_id = :id LIMIT 1")
    Favourite getFavouriteById(int id);*/

    @Insert
    void insertFavourite(Favourite favourite);

    @Query("DELETE FROM favourite")
    void deleteAll();

    @Delete
    void deleteFavourite(Favourite favourite);
}
